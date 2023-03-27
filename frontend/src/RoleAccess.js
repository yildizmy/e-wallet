import { Navigate, Outlet } from 'react-router-dom';
import AuthService from './services/AuthService';

const RoleAccess = ({ roles = [] }) => {
  const userRoles = AuthService.getCurrentUser()?.roles;

  return !roles.length || roles.some((r) => userRoles.includes(r)) ? (
    <Outlet />
  ) : (
    <Navigate to="/unauthorized" replace />
  );
};

export default RoleAccess;
