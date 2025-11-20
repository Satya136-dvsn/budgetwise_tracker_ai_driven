import api from './api';

const chatService = {
    // Send a message to the AI chat assistant
    sendMessage: (message, conversationId = null) => {
        return api.post('/chat', {
            message,
            conversationId
        });
    },

    // Start a new conversation
    startConversation: () => {
        return api.post('/chat', {
            message: 'Hello',
            conversationId: null
        });
    },
};

export default chatService;
