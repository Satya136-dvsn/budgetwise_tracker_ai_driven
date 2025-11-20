import { Container, Typography, Box, Button } from '@mui/material';
import { CalendarMonth } from '@mui/icons-material';

const BillsPage = () => {
    return (
        <Container maxWidth="lg" sx={{ py: 4 }}>
            <Box textAlign="center" py={8}>
                <CalendarMonth sx={{ fontSize: 80, color: 'primary.main', mb: 2 }} />
                <Typography variant="h4" fontWeight="bold" gutterBottom>
                    Bills & Calendar
                </Typography>
                <Typography variant="body2" color="text.secondary" mb={3}>
                    Track your recurring bills and manage payments with calendar view
                </Typography>
                <Typography variant="caption" color="text.secondary">
                    Coming soon - Full bill management system with calendar integration
                </Typography>
            </Box>
        </Container>
    );
};

export default BillsPage;
