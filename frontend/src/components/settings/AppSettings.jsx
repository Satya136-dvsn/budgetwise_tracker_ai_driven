import { useState } from 'react';
import { Grid, TextField, MenuItem, Typography, Box } from '@mui/material';
import ProfessionalCard from '../ui/ProfessionalCard';

const AppSettings = () => {
    const [settings, setSettings] = useState({
        currency: 'INR',
        language: 'en',
        dateFormat: 'DD/MM/YYYY',
        timezone: 'Asia/Kolkata',
    });

    const handleChange = (e) => {
        setSettings({
            ...settings,
            [e.target.name]: e.target.value,
        });
    };

    return (
        <ProfessionalCard title="Application Preferences" subheader="Customize your experience">
            <Grid container spacing={3}>
                <Grid item xs={12} md={6}>
                    <TextField
                        select
                        fullWidth
                        label="Currency"
                        name="currency"
                        value={settings.currency}
                        onChange={handleChange}
                        helperText="Default: Indian Rupees (₹)"
                    >
                        <MenuItem value="INR">INR (₹)</MenuItem>
                        <MenuItem value="USD">USD ($)</MenuItem>
                        <MenuItem value="EUR">EUR (€)</MenuItem>
                        <MenuItem value="GBP">GBP (£)</MenuItem>
                    </TextField>
                </Grid>
                <Grid item xs={12} md={6}>
                    <TextField
                        select
                        fullWidth
                        label="Language"
                        name="language"
                        value={settings.language}
                        onChange={handleChange}
                    >
                        <MenuItem value="en">English</MenuItem>
                        <MenuItem value="es">Spanish</MenuItem>
                        <MenuItem value="fr">French</MenuItem>
                        <MenuItem value="hi">Hindi</MenuItem>
                    </TextField>
                </Grid>
                <Grid item xs={12} md={6}>
                    <TextField
                        select
                        fullWidth
                        label="Date Format"
                        name="dateFormat"
                        value={settings.dateFormat}
                        onChange={handleChange}
                    >
                        <MenuItem value="DD/MM/YYYY">DD/MM/YYYY</MenuItem>
                        <MenuItem value="MM/DD/YYYY">MM/DD/YYYY</MenuItem>
                        <MenuItem value="YYYY-MM-DD">YYYY-MM-DD</MenuItem>
                    </TextField>
                </Grid>
                <Grid item xs={12} md={6}>
                    <TextField
                        select
                        fullWidth
                        label="Timezone"
                        name="timezone"
                        value={settings.timezone}
                        onChange={handleChange}
                        helperText="Default: Indian Standard Time (IST)"
                    >
                        <MenuItem value="Asia/Kolkata">Asia/Kolkata (IST)</MenuItem>
                        <MenuItem value="America/New_York">America/New_York (EST)</MenuItem>
                        <MenuItem value="Europe/London">Europe/London (GMT)</MenuItem>
                        <MenuItem value="Asia/Tokyo">Asia/Tokyo (JST)</MenuItem>
                    </TextField>
                </Grid>
            </Grid>
        </ProfessionalCard>
    );
};

export default AppSettings;
