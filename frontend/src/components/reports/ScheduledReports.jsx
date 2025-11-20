import { Box, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, IconButton, Chip } from '@mui/material';
import { Edit, Delete, PlayArrow } from '@mui/icons-material';
import ProfessionalCard from '../ui/ProfessionalCard';

const scheduledReports = [
    {
        id: 1,
        name: 'Weekly Spending Summary',
        frequency: 'Weekly',
        nextRun: '2025-11-24',
        recipients: 'me@example.com',
        status: 'Active',
    },
    {
        id: 2,
        name: 'Monthly Budget Review',
        frequency: 'Monthly',
        nextRun: '2025-12-01',
        recipients: 'me@example.com, partner@example.com',
        status: 'Active',
    },
    {
        id: 3,
        name: 'Quarterly Investment Update',
        frequency: 'Quarterly',
        nextRun: '2026-01-01',
        recipients: 'me@example.com',
        status: 'Paused',
    },
];

const ScheduledReports = () => {
    return (
        <ProfessionalCard title="Scheduled Reports" subheader="Manage your automated report deliveries">
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Report Name</TableCell>
                            <TableCell>Frequency</TableCell>
                            <TableCell>Next Run</TableCell>
                            <TableCell>Recipients</TableCell>
                            <TableCell>Status</TableCell>
                            <TableCell align="right">Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {scheduledReports.map((report) => (
                            <TableRow key={report.id}>
                                <TableCell sx={{ fontWeight: 500 }}>{report.name}</TableCell>
                                <TableCell>{report.frequency}</TableCell>
                                <TableCell>{report.nextRun}</TableCell>
                                <TableCell>{report.recipients}</TableCell>
                                <TableCell>
                                    <Chip
                                        label={report.status}
                                        color={report.status === 'Active' ? 'success' : 'default'}
                                        size="small"
                                        variant="outlined"
                                    />
                                </TableCell>
                                <TableCell align="right">
                                    <IconButton size="small" title="Run Now">
                                        <PlayArrow fontSize="small" />
                                    </IconButton>
                                    <IconButton size="small" title="Edit">
                                        <Edit fontSize="small" />
                                    </IconButton>
                                    <IconButton size="small" title="Delete" color="error">
                                        <Delete fontSize="small" />
                                    </IconButton>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </ProfessionalCard>
    );
};

export default ScheduledReports;
