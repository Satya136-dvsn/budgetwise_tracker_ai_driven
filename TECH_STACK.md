# BudgetWise Tracker - Technology Stack

## Backend Technologies

### Core Framework

| Technology | Version | Purpose | Documentation |
|------------|---------|---------|---------------|
| **Java** | 17+ | Programming Language | [Oracle Java Docs](https://docs.oracle.com/en/java/) |
| **Spring Boot** | 3.2.0 | Application Framework | [Spring Boot Docs](https://spring.io/projects/spring-boot) |
| **Maven** | 3.6+ | Build Tool & Dependency Management | [Maven Docs](https://maven.apache.org/) |

### Spring Ecosystem

| Technology | Version | Purpose |
|------------|---------|---------|
| **Spring Security** | 3.2.0 | Authentication & Authorization |
| **Spring Data JPA** | 3.2.0 | Data Persistence Layer |
| **Spring Web** | 3.2.0 | RESTful Web Services |
| **Spring Cache** | 3.2.0 | Caching Abstraction |

### Database

| Technology | Version | Purpose |
|------------|---------|---------|
| **MySQL** | 8.0+ | Relational Database |
| **Hibernate** | 6.3.1 | ORM Framework |
| **HikariCP** | 5.0.1 | Connection Pooling |

### Security

| Technology | Version | Purpose |
|------------|---------|---------|
| **JWT (jjwt)** | 0.12.3 | JSON Web Tokens |
| **BCrypt** | Included in Spring Security | Password Hashing |

### Report Generation

| Technology | Version | Purpose |
|------------|---------|---------|
| **iText 7** | 7.6.0 | PDF Generation |
| **Apache POI** | 5.2.5 | Excel Generation |
| **JFreeChart** | 1.5.4 | Chart Visualization |
| **jcommon** | 1.0.24 | JFreeChart Dependency |

### Utilities

| Technology | Version | Purpose |
|------------|---------|---------|
| **Lombok** | 1.18.30 | Reduce Boilerplate Code |
| **Jackson** | 2.15.2 | JSON Processing |
| **Validation API** | 3.0.2 | Bean Validation |

---

## Frontend Technologies

### Core Library

| Technology | Version | Purpose | Documentation |
|------------|---------|---------|---------------|
| **React** | 18.2.0 | UI Library | [React Docs](https://react.dev/) |
| **JavaScript (ES6+)** | ES2022 | Programming Language | [MDN Web Docs](https://developer.mozilla.org/) |
| **Vite** | 5.0.8 | Build Tool & Dev Server | [Vite Docs](https://vitejs.dev/) |

### UI Framework & Components

| Technology | Version | Purpose |
|------------|---------|---------|
| **Material-UI (MUI)** | 5.14.20 | Component Library |
| **@mui/icons-material** | 5.14.19 | Material Icons |
| **@mui/x-date-pickers** | 6.18.6 | Date/Time Pickers |
| **@emotion/react** | 11.11.1 | CSS-in-JS |
| **@emotion/styled** | 11.11.0 | Styled Components |

### Data Visualization

| Technology | Version | Purpose |
|------------|---------|---------|
| **Recharts** | 2.10.3 | Charts & Graphs |
| **React Big Calendar** | 1.8.5 | Calendar Component |

### State Management & Routing

| Technology | Version | Purpose |
|------------|---------|---------|
| **React Context API** | Built-in | State Management |
| **React Router DOM** | 6.20.1 | Client-side Routing |

### HTTP & Data Fetching

| Technology | Version | Purpose |
|------------|---------|---------|
| **Axios** | 1.6.2 | HTTP Client |
| **date-fns** | 2.30.0 | Date Utilities |

---

## Development Tools

### IDEs & Editors

- **IntelliJ IDEA** - Java Backend Development
- **Visual Studio Code** - Frontend Development
- **MySQL Workbench** - Database Management

### Testing Tools

- **Postman** - API Testing
- **JUnit 5** - Backend Unit Testing
- **Jest** - Frontend Testing (planned)

### Version Control

- **Git** - Version Control System
- **GitHub** - Repository Hosting

### Build & Deployment

- **Maven Wrapper (mvnw)** - Backend Build
- **npm** - Frontend Package Manager
- **Docker** (planned) - Containerization

---

## Architecture Patterns

### Backend Patterns

- **Layered Architecture**: Controller → Service → Repository → Entity
- **Dependency Injection**: Spring IoC Container
- **DTO Pattern**: Data Transfer Objects for API responses
- **Repository Pattern**: Spring Data JPA repositories
- **Facade Pattern**: ExportService as facade for specialized generators

### Frontend Patterns

- **Component-Based Architecture**: Reusable React components
- **Container/Presentational Pattern**: Smart vs Dump components
- **Context API**: Global state management
- **Custom Hooks**: Reusable stateful logic
- **Service Layer**: API client abstraction

### Design Principles

- **SOLID Principles**: Applied throughout codebase
- **DRY (Don't Repeat Yourself)**: Reusable components and utilities
- **Separation of Concerns**: Clear responsibility boundaries
- **RESTful API Design**: Standard HTTP methods and status codes

---

## Database Schema

### Tables

- **users**: User accounts and profiles
- **transactions**: Financial transactions (income/expense)
- **categories**: Transaction categories
- **budgets**: Budget allocations
- **savings_goals**: Financial goals
- **bills**: Recurring bills
- **investments**: Investment portfolio

### Relationships

- Users → Transactions (One-to-Many)
- Users → Budgets (One-to-Many)
- Users → Savings Goals (One-to-Many)
- Categories → Transactions (One-to-Many)
- Categories → Budgets (One-to-One)

---

## API Architecture

### REST API Design

- **Base URL**: `http://localhost:8080/api`
- **Authentication**: Bearer JWT Token
- **Content-Type**: `application/json`
- **HTTP Methods**: GET, POST, PUT, DELETE

### Endpoint Categories

- `/auth` - Authentication endpoints
- `/dashboard` - Dashboard data
- `/transactions` - Transaction CRUD
- `/budgets` - Budget management
- `/goals` - Savings goals
- `/bills` - Recurring bills
- `/investments` - Portfolio management
- `/export` - Report generation
- `/analytics` - Financial analytics

---

## Performance Optimizations

### Backend

- Spring Cache for dashboard summary
- Database indexing on frequently queried columns
- Connection pooling with HikariCP
- Lazy loading for entity relationships
- Custom optimized JPA queries

### Frontend

- Code splitting with React.lazy()
- Memoization with React.memo()
- Debouncing for search inputs
- Optimized re-renders with useMemo/useCallback
- Responsive image loading

---

## Security Stack

### Authentication

- JWT (JSON Web Tokens)
- BCrypt password hashing
- Token expiration (1 hour access, 7 days refresh)

### Authorization

- Role-based access control (RBAC)
- Spring Security SecurityFilterChain
- Method-level security annotations

### Data Protection

- HTTPS encryption (production)
- SQL injection prevention (Parameterized queries)
- XSS protection (Input sanitization)
- CORS configuration
- CSRF protection

---

## Deployment Stack (Planned)

### Containerization

- **Docker**: Application containerization
- **Docker Compose**: Multi-container orchestration

### Cloud Platform (Options)

- **AWS** (Amazon Web Services)
- **Heroku**: Quick deployment
- **Vercel**: Frontend hosting
- **Railway**: Backend hosting

### CI/CD

- **GitHub Actions**: Automated testing and deployment
- **Jenkins** (alternative): Build automation

---

*For detailed implementation guides, refer to [INTERNSHIP_REPORT.md](INTERNSHIP_REPORT.md)*
