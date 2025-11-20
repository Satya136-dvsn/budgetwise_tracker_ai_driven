// Mock banking service for bank account integration
const bankingService = {
    // Get all connected bank accounts
    getAccounts: async () => {
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve({
                    data: [
                        {
                            id: 1,
                            bankName: 'HDFC Bank',
                            bankLogo: '🏦',
                            accountType: 'Savings',
                            accountNumber: '****1234',
                            balance: 125000,
                            currency: 'INR',
                            lastSync: new Date(Date.now() - 3600000).toISOString(),
                            status: 'Connected',
                        },
                        {
                            id: 2,
                            bankName: 'ICICI Bank',
                            bankLogo: '🏦',
                            accountType: 'Current',
                            accountNumber: '****5678',
                            balance: 87500,
                            currency: 'INR',
                            lastSync: new Date(Date.now() - 7200000).toISOString(),
                            status: 'Connected',
                        },
                    ],
                });
            }, 500);
        });
    },

    // Connect a new bank account (mock OAuth flow)
    connectBank: async (bankData) => {
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve({
                    data: {
                        id: Date.now(),
                        bankName: bankData.bankName,
                        bankLogo: '🏦',
                        accountType: bankData.accountType,
                        accountNumber: `****${Math.floor(1000 + Math.random() * 9000)}`,
                        balance: 0,
                        currency: 'INR',
                        lastSync: new Date().toISOString(),
                        status: 'Connected',
                    },
                    message: 'Bank account connected successfully',
                });
            }, 1500);
        });
    },

    // Disconnect a bank account
    disconnectBank: async (accountId) => {
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve({
                    data: { success: true },
                    message: 'Bank account disconnected',
                });
            }, 500);
        });
    },

    // Sync transactions from bank
    syncTransactions: async (accountId) => {
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve({
                    data: {
                        accountId,
                        lastSync: new Date().toISOString(),
                        newTransactions: Math.floor(Math.random() * 10),
                    },
                    message: 'Transactions synced successfully',
                });
            }, 2000);
        });
    },

    // Get available banks list
    getAvailableBanks: async () => {
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve({
                    data: [
                        { id: 1, name: 'HDFC Bank', logo: '🏦' },
                        { id: 2, name: 'ICICI Bank', logo: '🏦' },
                        { id: 3, name: 'SBI', logo: '🏦' },
                        { id: 4, name: 'Axis Bank', logo: '🏦' },
                        { id: 5, name: 'Kotak Mahindra Bank', logo: '🏦' },
                    ],
                });
            }, 300);
        });
    },

    // Import transactions from connected account
    importTransactions: async (accountId, transactions) => {
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve({
                    data: {
                        imported: transactions.length,
                        accountId,
                    },
                    message: `${transactions.length} transactions imported successfully`,
                });
            }, 1000);
        });
    },
};

export default bankingService;
