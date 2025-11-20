import api from './api';

const adminService = {
    getStats: async () => {
        // Mock data
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve({
                    data: {
                        totalUsers: 1250,
                        activeUsers: 850,
                        totalTransactions: 45000,
                        serverStatus: 'Healthy',
                        revenue: 12500
                    }
                });
            }, 500);
        });
    },

    getUsers: async () => {
        // Mock data
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve({
                    data: [
                        { id: 1, name: 'John Doe', email: 'john@example.com', role: 'User', status: 'Active', joined: '2023-01-15' },
                        { id: 2, name: 'Jane Smith', email: 'jane@example.com', role: 'Admin', status: 'Active', joined: '2023-02-20' },
                        { id: 3, name: 'Bob Johnson', email: 'bob@example.com', role: 'User', status: 'Inactive', joined: '2023-03-10' },
                        { id: 4, name: 'Alice Brown', email: 'alice@example.com', role: 'User', status: 'Active', joined: '2023-04-05' },
                        { id: 5, name: 'Charlie Wilson', email: 'charlie@example.com', role: 'User', status: 'Suspended', joined: '2023-05-12' },
                    ]
                });
            }, 600);
        });
    },

    updateUserStatus: async (userId, status) => {
        // Mock call
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve({ data: { success: true, userId, status } });
            }, 300);
        });
    },

    deleteUser: async (userId) => {
        // Mock call
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve({ data: { success: true, userId } });
            }, 300);
        });
    }
};

export default adminService;
