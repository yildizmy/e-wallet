import AuthService from './AuthService';

export default function AuthHeader() {
  const token = AuthService.getCurrentUser()?.token;

  return token ? { Accept: 'application/json', Authorization: `Bearer ${token}` } : {};
}
