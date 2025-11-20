import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Grid, Typography, Box, Fade, Paper, ToggleButton, ToggleButtonGroup } from '@mui/material';
import { ShowChart, PieChart, AccountBalanceWallet, CompareArrows } from '@mui/icons-material';
import ProfessionalCard from '../components/ui/ProfessionalCard';
import TrendAnalysisChart from '../components/analytics/TrendAnalysisChart';
import CategoryBreakdownChart from '../components/analytics/CategoryBreakdownChart';
import CashFlowChart from '../components/analytics/CashFlowChart';
import dashboardService from '../services/dashboardService';

const Analytics = () => {
    const navigate = useNavigate();
    const [timeRange, setTimeRange] = useState('6m');
    const [loading, setLoading] = useState(true);
    const [trends, setTrends] = useState([]);
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        loadAnalyticsData();
    }, [timeRange]);

    const loadAnalyticsData = async () => {
        try {
            setLoading(true);
            // Fetch data based on timeRange with proper months calculation
            let months = 6; // default
            if (timeRange === '3m') months = 3;
            else if (timeRange === '6m') months = 6;
            else if (timeRange === '1y') months = 12;

            const [trendsRes, categoryRes] = await Promise.all([
                dashboardService.getMonthlyTrends(months),
                dashboardService.getCategoryBreakdown()
            ]);
            setTrends(trendsRes.data);
            setCategories(categoryRes.data);
        } catch (error) {
            console.error('Failed to load analytics:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleTimeRangeChange = (event, newRange) => {
        if (newRange !== null) {
            setTimeRange(newRange);
        }
    };

    const handleCategoryClick = (data) => {
        if (data && data.categoryId) {
            navigate(`/transactions?category=${data.categoryId}`);
        }
    };

    return (
        <Container maxWidth="xl" sx={{ pb: 4 }}>
            <Fade in timeout={300}>
                <Box mb={4} display="flex" justifyContent="space-between" alignItems="center">
                    <Box>
                        <Typography variant="h4" gutterBottom sx={{ fontWeight: 700, mb: 0.5 }}>
                            Analytics
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            Deep dive into your financial data
                        </Typography>
                    </Box>
                    <ToggleButtonGroup
                        value={timeRange}
                        exclusive
                        onChange={handleTimeRangeChange}
                        aria-label="time range"
                        size="small"
                    >
                        <ToggleButton value="3m">3 Months</ToggleButton>
                        <ToggleButton value="6m">6 Months</ToggleButton>
                        <ToggleButton value="1y">1 Year</ToggleButton>
                    </ToggleButtonGroup>
                </Box>
            </Fade>

            <Grid container spacing={3}>
                {/* Trend Analysis */}
                <Grid item xs={12}>
                    <Fade in timeout={400}>
                        <Box>
                            <ProfessionalCard
                                title="Income vs Expenses Trend"
                                subheader="Track your earning and spending patterns over time"
                                headerAction={<ShowChart color="primary" />}
                            >
                                <TrendAnalysisChart data={trends} loading={loading} />
                            </ProfessionalCard>
                        </Box>
                    </Fade>
                </Grid>

                {/* Category Breakdown */}
                <Grid item xs={12} md={6}>
                    <Fade in timeout={500}>
                        <Box>
                            <ProfessionalCard
                                title="Spending Breakdown"
                                subheader="Where your money goes"
                                headerAction={<PieChart color="secondary" />}
                            >
                                <CategoryBreakdownChart
                                    data={categories}
                                    loading={loading}
                                    onCategoryClick={handleCategoryClick}
                                />
                            </ProfessionalCard>
                        </Box>
                    </Fade>
                </Grid>

                {/* Cash Flow */}
                <Grid item xs={12} md={6}>
                    <Fade in timeout={600}>
                        <Box>
                            <ProfessionalCard
                                title="Cash Flow Analysis"
                                subheader="Net savings and flow"
                                headerAction={<AccountBalanceWallet color="success" />}
                            >
                                <CashFlowChart data={trends} loading={loading} />
                            </ProfessionalCard>
                        </Box>
                    </Fade>
                </Grid>
            </Grid>
        </Container>
    );
};

export default Analytics;
