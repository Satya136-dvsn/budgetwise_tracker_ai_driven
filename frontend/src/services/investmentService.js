// Mock investment service
const investmentService = {
    getPortfolio: async () => {
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve({
                    data: {
                        totalValue: 550000,
                        totalInvested: 500000,
                        totalGain: 50000,
                        gainPercentage: 10,
                        holdings: [
                            {
                                id: 1,
                                symbol: 'RELIANCE',
                                name: 'Reliance Industries Ltd',
                                type: 'Stock',
                                shares: 50,
                                avgPrice: 2400,
                                currentPrice: 2600,
                                totalValue: 130000,
                                gain: 10000,
                                gainPercentage: 8.33,
                            },
                            {
                                id: 2,
                                symbol: 'TCS',
                                name: 'Tata Consultancy Services',
                                type: 'Stock',
                                shares: 30,
                                avgPrice: 3500,
                                currentPrice: 3800,
                                totalValue: 114000,
                                gain: 9000,
                                gainPercentage: 8.57,
                            },
                            {
                                id: 3,
                                symbol: 'BTC',
                                name: 'Bitcoin',
                                type: 'Crypto',
                                shares: 0.5,
                                avgPrice: 3500000,
                                currentPrice: 4000000,
                                totalValue: 200000,
                                gain: 250000,
                                gainPercentage: 14.29,
                            },
                        ],
                    },
                });
            }, 500);
        });
    },

    addHolding: async (holdingData) => {
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve({
                    data: {
                        id: Date.now(),
                        ...holdingData,
                        currentPrice: holdingData.avgPrice,
                        totalValue: holdingData.shares * holdingData.avgPrice,
                        gain: 0,
                        gainPercentage: 0,
                    },
                    message: 'Holding added successfully',
                });
            }, 500);
        });
    },

    deleteHolding: async (holdingId) => {
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve({ message: 'Holding deleted' });
            }, 500);
        });
    },
};

export default investmentService;
