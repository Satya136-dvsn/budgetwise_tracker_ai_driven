import api from './api';

const financialHealthService = {
    getHealthScore: () => api.get('/financial-health/score'),
};

export default financialHealthService;
