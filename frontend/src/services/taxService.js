import api from './api';

const taxService = {
    getEstimate: () => api.get('/tax/estimate'),
};

export default taxService;
