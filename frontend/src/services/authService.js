import apiClient from './axiosConfig';

const authService = {
    login: async (email, password) => {
        const response = await apiClient.post('/auth/login', { email, password });
        const { accessToken, refreshToken, user } = response.data;

        if (accessToken) {
            localStorage.setItem('token', accessToken);
            localStorage.setItem('user', JSON.stringify(user));

            if (refreshToken) {
                localStorage.setItem('refreshToken', refreshToken);
            }
        }

        return response.data;
    },

    register: async (userData) => {
        const response = await apiClient.post('/auth/register', userData);
        const { accessToken, refreshToken, user } = response.data;

        if (accessToken) {
            localStorage.setItem('token', accessToken);
            localStorage.setItem('user', JSON.stringify(user));

            if (refreshToken) {
                localStorage.setItem('refreshToken', refreshToken);
            }
        }

        return response.data;
    },

    logout: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('user');
    },

    getCurrentUser: () => {
        const userStr = localStorage.getItem('user');
        return userStr ? JSON.parse(userStr) : null;
    },

    getToken: () => {
        return localStorage.getItem('token');
    },
};

export default authService;
