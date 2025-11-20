import { useState } from 'react';
import { Grid, TextField, Button, Typography, Switch, FormControlLabel, Divider, Box, Snackbar, Alert } from '@mui/material';
import userService from '../../services/userService';
import { Save, Key, PhonelinkLock } from '@mui/icons-material';
import ProfessionalCard from '../ui/ProfessionalCard';

const SecuritySettings = () => {
    const [passwords, setPasswords] = useState({
        current: '',
        new: '',
        confirm: '',
    });
    const [twoFactor, setTwoFactor] = useState(false);

    const handleChange = (e) => {
        setPasswords({
            ...passwords,
            [e.target.name]: e.target.value,
        });
    };

    const [loading, setLoading] = useState(false);
    const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' });

    const handleCloseSnackbar = () => {
        setSnackbar({ ...snackbar, open: false });
    };

    const handleUpdatePassword = async () => {
        if (passwords.new !== passwords.confirm) {
            setSnackbar({ open: true, message: 'New passwords do not match', severity: 'error' });
            return;
        }

        try {
            setLoading(true);
            await userService.changePassword({
                currentPassword: passwords.current,
                newPassword: passwords.new
            });
            setSnackbar({ open: true, message: 'Password updated successfully', severity: 'success' });
            setPasswords({ current: '', new: '', confirm: '' });
        } catch (error) {
            console.error('Failed to update password:', error);
            setSnackbar({ open: true, message: 'Failed to update password', severity: 'error' });
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <ProfessionalCard title="Change Password" icon={<Key />}>
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <TextField
                                    fullWidth
                                    type="password"
                                    label="Current Password"
                                    name="current"
                                    value={passwords.current}
                                    onChange={handleChange}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    fullWidth
                                    type="password"
                                    label="New Password"
                                    name="new"
                                    value={passwords.new}
                                    onChange={handleChange}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    fullWidth
                                    type="password"
                                    label="Confirm New Password"
                                    name="confirm"
                                    value={passwords.confirm}
                                    onChange={handleChange}
                                />
                            </Grid>
                            <Grid item xs={12} display="flex" justifyContent="flex-end">
                                <Button
                                    variant="contained"
                                    startIcon={<Save />}
                                    onClick={handleUpdatePassword}
                                    disabled={loading}
                                >
                                    {loading ? 'Updating...' : 'Update Password'}
                                </Button>
                            </Grid>
                        </Grid>
                    </ProfessionalCard>
                </Grid>

                <Grid item xs={12}>
                    <ProfessionalCard title="Two-Factor Authentication" icon={<PhonelinkLock />}>
                        <Box display="flex" justifyContent="space-between" alignItems="center">
                            <Box>
                                <Typography variant="subtitle1" fontWeight="bold">
                                    Enable 2FA
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    Add an extra layer of security to your account by requiring a code from your phone.
                                </Typography>
                            </Box>
                            <Switch
                                checked={twoFactor}
                                onChange={(e) => setTwoFactor(e.target.checked)}
                                color="primary"
                            />
                        </Box>
                    </ProfessionalCard>
                </Grid>
            </Grid>
            <Snackbar open={snackbar.open} autoHideDuration={6000} onClose={handleCloseSnackbar}>
                <Alert onClose={handleCloseSnackbar} severity={snackbar.severity} sx={{ width: '100%' }}>
                    {snackbar.message}
                </Alert>
            </Snackbar>
        </>
    );
};

export default SecuritySettings;
