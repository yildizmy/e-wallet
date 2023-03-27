import { Navigate, Outlet } from 'react-router-dom';
import AuthService from './services/AuthService';

const ProtectedRoute = ({ roles = [] }) => {
  const userRoles = AuthService.getCurrentUser()?.roles;
  const isAllowed = !roles.length || roles.some((r) => userRoles.includes(r));

  return isAllowed ? <Outlet /> : <Navigate to="/unauthorized" replace />;
};

export default ProtectedRoute;
