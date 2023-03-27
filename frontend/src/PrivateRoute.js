import { Navigate, Outlet } from 'react-router-dom';
import AuthService from './services/AuthService';

const PrivateRoute = () => {
  const isAuthenticated = AuthService.getCurrentUser() !== null && AuthService.getCurrentUser() !== undefined;

  // if authorized, return an outlet that will render child elements; if not, return element that will navigate to login page
  return isAuthenticated ? <Outlet /> : <Navigate to="/login" />;
};

export default PrivateRoute;
