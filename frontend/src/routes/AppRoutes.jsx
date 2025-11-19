import { Routes, Route } from 'react-router-dom'
import HomePage from '../pages/HomePage'
import SignIn from '../pages/SignIn'
import SignUp from '../pages/SignUp'
import Dashboard from '../pages/Dashboard'
import Transactions from '../pages/Transactions'
import Categories from '../pages/Categories'
import Budgets from '../pages/Budgets'
import SavingsGoals from '../pages/SavingsGoals'
import DesignSystemDemo from '../pages/DesignSystemDemo'
import ProtectedRoute from '../components/ProtectedRoute'
import DashboardLayout from '../components/layout/DashboardLayout'

function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/login" element={<SignIn />} />
      <Route path="/register" element={<SignUp />} />
      <Route path="/design-demo" element={<DesignSystemDemo />} />
      <Route
        element={
          <ProtectedRoute>
            <DashboardLayout />
          </ProtectedRoute>
        }
      >
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/transactions" element={<Transactions />} />
        <Route path="/categories" element={<Categories />} />
        <Route path="/budgets" element={<Budgets />} />
        <Route path="/goals" element={<SavingsGoals />} />
      </Route>
    </Routes>
  )
}

export default AppRoutes
