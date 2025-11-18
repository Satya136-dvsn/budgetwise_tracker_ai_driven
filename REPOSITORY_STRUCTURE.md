# BudgetWise - Repository Structure

## 📁 Project Organization

```
budgetwise-tracker/
├── backend/                    # Spring Boot Backend
│   ├── src/main/java/com/budgetwise/
│   │   ├── controller/        # REST API Controllers (17 files)
│   │   ├── service/           # Business Logic Services
│   │   ├── repository/        # Data Access Layer
│   │   ├── entity/            # JPA Entities
│   │   ├── dto/               # Data Transfer Objects
│   │   ├── security/          # JWT & Authentication
│   │   ├── config/            # Configuration Classes
│   │   └── exception/         # Exception Handling
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── application-secrets.properties (create this)
│   └── pom.xml                # Maven Dependencies
│
├── frontend/                   # React Frontend
│   ├── src/
│   │   ├── components/        # Reusable Components (7 files)
│   │   ├── pages/             # Page Components (7 files)
│   │   ├── services/          # API Services (6 files)
│   │   ├── context/           # React Context (Auth)
│   │   ├── routes/            # Route Configuration
│   │   ├── theme/             # Material-UI Theme
│   │   └── utils/             # Utility Functions
│   ├── public/                # Static Assets
│   ├── package.json           # NPM Dependencies
│   └── vite.config.js         # Vite Configuration
│
├── database/                   # Database Scripts
│   ├── init.sql               # Schema Creation
│   └── insert_test_audit_logs.sql
│
├── .kiro/                      # Kiro IDE Specs
│   └── specs/budgetwise-complete-system/
│       ├── requirements.md
│       ├── design.md
│       └── tasks.md
│
└── Documentation Files
    ├── README.md              # Main Documentation
    ├── QUICKSTART.md          # Quick Start Guide
    ├── INSTALLATION_VERIFICATION.md
    ├── DEPLOYMENT_STATUS.md   # Current Status
    ├── Roadmap.md             # Project Roadmap
    └── LICENSE                # MIT License
```

## 📊 Statistics

### Backend
- **Controllers:** 17 files
- **Services:** 15 files
- **Repositories:** 10 files
- **Entities:** 10 files
- **DTOs:** 20+ files
- **Total Backend Files:** 100+

### Frontend
- **Pages:** 7 files (Login, Register, Dashboard, Transactions, Categories, Budgets, Goals)
- **Components:** 7 files (Layout, Dialogs, Protected Route)
- **Services:** 6 files (API, Auth, Transaction, Category, Budget, Goal)
- **Total Frontend Files:** 30+

### Database
- **Tables:** 10 (users, transactions, categories, budgets, savings_goals, etc.)
- **SQL Scripts:** 2 files

## 🎯 Key Features by Module

### Authentication Module
- `AuthController.java` - Login/Register endpoints
- `JwtTokenProvider.java` - JWT token generation
- `AuthContext.jsx` - Frontend auth state
- `Login.jsx`, `Register.jsx` - Auth UI

### Transaction Module
- `TransactionController.java` - CRUD endpoints
- `TransactionService.java` - Business logic
- `Transactions.jsx` - Transaction management UI
- `TransactionDialog.jsx` - Add/Edit form

### Category Module
- `CategoryController.java` - Category endpoints
- `CategoryService.java` - Category logic
- `Categories.jsx` - Category management UI
- `CategoryDialog.jsx` - Add/Edit form

### Budget Module
- `BudgetController.java` - Budget endpoints
- `BudgetService.java` - Budget tracking
- `Budgets.jsx` - Budget UI with progress bars
- `BudgetDialog.jsx` - Budget form

### Savings Goals Module
- `SavingsGoalController.java` - Goal endpoints
- `SavingsGoalService.java` - Goal tracking
- `SavingsGoals.jsx` - Goals UI
- `ContributeDialog.jsx` - Contribution form

### AI Features Module
- `AIController.java` - AI endpoints
- `OpenAIService.java` - OpenAI integration
- `PredictionService.java` - ML predictions
- `BudgetAdvisorService.java` - AI recommendations

### Dashboard Module
- `DashboardController.java` - Summary endpoints
- `DashboardService.java` - Analytics
- `Dashboard.jsx` - Dashboard UI (basic)

### Admin Module
- `AdminController.java` - Admin endpoints
- `AdminService.java` - User management
- Audit logging system

### Forum Module
- `ForumController.java` - Forum endpoints
- `ForumService.java` - Posts/Comments
- Like system

## 📝 Documentation Files

### Essential Documentation
1. **README.md** - Complete project documentation
   - Features overview
   - Installation guide
   - API documentation
   - Tech stack details

2. **QUICKSTART.md** - Quick setup guide
   - Fast installation steps
   - Common commands
   - Troubleshooting

3. **INSTALLATION_VERIFICATION.md** - Setup verification
   - Checklist for installation
   - Testing steps
   - Common issues

4. **DEPLOYMENT_STATUS.md** - Current project status
   - Completed features
   - In-progress work
   - Next steps

5. **Roadmap.md** - Future plans
   - Version roadmap
   - Planned features
   - Timeline

## 🚀 Getting Started

1. **Read:** README.md for complete overview
2. **Setup:** Follow QUICKSTART.md for installation
3. **Verify:** Use INSTALLATION_VERIFICATION.md to check setup
4. **Status:** Check DEPLOYMENT_STATUS.md for current state
5. **Future:** See Roadmap.md for upcoming features

## 🔗 Links

- **Repository:** https://github.com/Satya136-dvsn/draft-budget-tracker
- **Backend API:** http://localhost:8080
- **Frontend UI:** http://localhost:3000

---

**Last Updated:** November 18, 2025
