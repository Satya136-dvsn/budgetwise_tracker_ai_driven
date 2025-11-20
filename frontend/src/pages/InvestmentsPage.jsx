import { Container, Typography, Box } from '@mui/material';
import { TrendingUp } from '@mui/icons-material';

const InvestmentsPage = () => {
    return (
        <Container maxWidth="lg" sx={{ py: 4 }}>
            <Box textAlign="center" py={8}>
                <TrendingUp sx={{ fontSize: 80, color: 'success.main', mb: 2 }} />
                <Typography variant="h4" fontWeight="bold" gutterBottom>
                    Investment Portfolio
                </Typography>
                <Typography variant="body2" color="text.secondary" mb={3}>
                    Track your stocks, crypto, and other investments in one place
                </Typography>
                <Typography variant="caption" color="text.secondary">
                    Coming soon - Complete portfolio management with performance tracking
                </Typography>
            </Box>
        </Container>
    );
};

export default InvestmentsPage;
