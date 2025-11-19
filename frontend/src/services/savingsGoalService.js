import api from './api';

const savingsGoalService = {
  getAll: () => api.get('/goals'),
  
  getById: (id) => api.get(`/goals/${id}`),
  
  create: (data) => api.post('/goals', data),
  
  update: (id, data) => api.put(`/goals/${id}`, data),
  
  delete: (id) => api.delete(`/goals/${id}`),
  
  contribute: (id, amount) => api.post(`/goals/${id}/contribute`, { amount }),
};

export default savingsGoalService;
