import { useState } from 'react';
import { Grid, Box, Button, Typography, Chip, Snackbar, Alert } from '@mui/material';
import reportService from '../../services/reportService';
import { Description, Assessment, ReceiptLong, TrendingUp, Download } from '@mui/icons-material';
import ProfessionalCard from '../ui/ProfessionalCard';

const templates = [
    {
        id: 1,
        title: 'Monthly Summary',
        description: 'Comprehensive overview of your income, expenses, and savings for the month.',
        icon: <Assessment fontSize="large" color="primary" />,
        tags: ['Popular', 'Monthly'],
    },
    {
        id: 2,
        title: 'Tax Report',
        description: 'Detailed breakdown of deductible expenses and income sources for tax purposes.',
        icon: <ReceiptLong fontSize="large" color="secondary" />,
        tags: ['Tax', 'Annual'],
    },
    {
        id: 3,
        title: 'Expense Analysis',
        description: 'Deep dive into your spending habits by category and merchant.',
        icon: <Description fontSize="large" color="error" />,
        tags: ['Spending', 'Analysis'],
    },
    {
        id: 4,
        title: 'Investment Performance',
        description: 'Track the growth and performance of your investment portfolio.',
        icon: <TrendingUp fontSize="large" color="success" />,
        tags: ['Investments', 'Growth'],
    },
];

const ReportTemplates = () => {
    const [loading, setLoading] = useState(false);
    const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' });

    const handleCloseSnackbar = () => {
        setSnackbar({ ...snackbar, open: false });
    };

    const handleGenerate = async (templateId, title) => {
        try {
            setLoading(true);
            const response = await reportService.generateReport(templateId);

            // Create download link with proper file extension
            const url = window.URL.createObjectURL(response.data);
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', `${title.replace(/\s+/g, '_')}_${new Date().toISOString().split('T')[0]}.txt`);
            document.body.appendChild(link);
            link.click();
            link.remove();
            window.URL.revokeObjectURL(url);

            setSnackbar({ open: true, message: 'Report generated successfully', severity: 'success' });
        } catch (error) {
            console.error('Report generation failed:', error);
            setSnackbar({ open: true, message: 'Failed to generate report', severity: 'error' });
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <Grid container spacing={3}>
                {templates.map((template) => (
                    <Grid item xs={12} md={6} key={template.id}>
                        <ProfessionalCard
                            title={
                                <Box display="flex" alignItems="center" gap={2}>
                                    {template.icon}
                                    <Typography variant="h6">{template.title}</Typography>
                                </Box>
                            }
                            action={
                                <Button
                                    variant="contained"
                                    startIcon={<Download />}
                                    onClick={() => handleGenerate(template.id, template.title)}
                                    disabled={loading}
                                >
                                    {loading ? 'Generating...' : 'Generate'}
                                </Button>
                            }
                        >
                            <Typography variant="body2" color="text.secondary" paragraph>
                                {template.description}
                            </Typography>
                            <Box display="flex" gap={1} mt={2}>
                                {template.tags.map((tag) => (
                                    <Chip key={tag} label={tag} size="small" variant="outlined" />
                                ))}
                            </Box>
                        </ProfessionalCard>
                    </Grid>
                ))}
            </Grid>
            <Snackbar open={snackbar.open} autoHideDuration={6000} onClose={handleCloseSnackbar}>
                <Alert onClose={handleCloseSnackbar} severity={snackbar.severity} sx={{ width: '100%' }}>
                    {snackbar.message}
                </Alert>
            </Snackbar>
        </>
    );
};

export default ReportTemplates;
