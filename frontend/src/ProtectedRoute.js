import { Navigate, Outlet } from 'react-router-dom';
import AuthService from './services/AuthService';

const ProtectedRoute = ({ roles = [] }) => {
  const userRoles = AuthService.getCurrentUser()?.roles;
  const isAuthorized = !roles.length || roles.some((r) => userRoles.includes(r));

  return isAuthorized ? <Outlet /> : <Navigate to="/unauthorized" replace />;
};

export default ProtectedRoute;
