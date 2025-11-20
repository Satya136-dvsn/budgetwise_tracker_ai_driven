import api from './api';

const userService = {
    getProfile: async () => {
        // Try to get from localStorage first (mock persistence)
        const savedProfile = localStorage.getItem('userProfile');
        if (savedProfile) {
            return Promise.resolve({ data: JSON.parse(savedProfile) });
        }
        // Return empty mock data if nothing saved yet
        return Promise.resolve({
            data: {
                firstName: '',
                lastName: '',
                email: '',
                phone: '',
                bio: ''
            }
        });
    },

    updateProfile: async (data) => {
        // Mock response and save to localStorage
        return new Promise((resolve) => {
            setTimeout(() => {
                console.log('Profile updated:', data);
                // Save to localStorage for persistence
                localStorage.setItem('userProfile', JSON.stringify(data));
                resolve({
                    data: {
                        success: true,
                        message: 'Profile updated successfully',
                        user: data
                    }
                });
            }, 500);
        });
    },

    changePassword: async (data) => {
        // Mock response
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                if (!data.currentPassword || !data.newPassword) {
                    reject(new Error('Invalid password data'));
                } else {
                    console.log('Password changed');
                    resolve({ data: { success: true, message: 'Password changed successfully' } });
                }
            }, 500);
        });
    },

    updatePreferences: async (data) => {
        return api.put('/users/preferences', data);
    },

    updateNotifications: async (data) => {
        return api.put('/users/notifications', data);
    },

    uploadAvatar: async (formData) => {
        // Mock response for avatar upload
        return new Promise((resolve) => {
            setTimeout(() => {
                console.log('Avatar uploaded');
                // Create a mock URL for the uploaded avatar
                const mockAvatarUrl = URL.createObjectURL(formData.get('avatar'));
                resolve({
                    data: {
                        success: true,
                        message: 'Avatar uploaded successfully',
                        avatarUrl: mockAvatarUrl
                    }
                });
            }, 800);
        });
    }
};

export default userService;
