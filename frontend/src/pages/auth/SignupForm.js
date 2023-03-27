import { LoadingButton } from '@mui/lab';
import { IconButton, InputAdornment, Stack, TextField } from '@mui/material';
import { useSnackbar } from 'notistack';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Iconify from '../../components/iconify';
import AuthService from '../../services/AuthService';

export default function SignupForm() {
  const defaultValues = {
    firstName: '',
    lastName: '',
    username: '',
    email: '',
    password: '',
    roles: ['ROLE_USER'],
  };

  const navigate = useNavigate();
  const [showPassword, setShowPassword] = useState(false);
  const { enqueueSnackbar } = useSnackbar();
  const [formValues, setFormValues] = useState(defaultValues);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormValues({
      ...formValues,
      [name]: value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    AuthService.signup(formValues)
      .then((response) => {
        enqueueSnackbar('Signed up successfully', { variant: 'success' });
        navigate('/login');
      })
      .catch((error) => {
        if (error.response?.data?.errors) {
          error.response?.data?.errors.map((e) => enqueueSnackbar(`${e.field} ${e.message}`, { variant: 'error' }));
        } else if (error.response?.data?.message) {
          enqueueSnackbar(error.response?.data?.message, { variant: 'error' });
        } else {
          enqueueSnackbar(error.message, { variant: 'error' });
        }
      });
  };

  return (
    <>
      <Stack spacing={3}>
        <TextField
          id="firstName"
          name="firstName"
          label="First Name"
          autoComplete="given-name"
          autoFocus
          required
          value={formValues.firstName}
          onChange={handleInputChange}
        />
        <TextField
          id="lastName"
          name="lastName"
          label="Last Name"
          autoComplete="lastName"
          required
          value={formValues.lastName}
          onChange={handleInputChange}
        />
        <TextField
          id="username"
          name="username"
          label="Username"
          autoComplete="username"
          required
          value={formValues.username}
          onChange={handleInputChange}
        />
        <TextField
          id="email"
          name="email"
          label="email"
          autoComplete="email"
          required
          value={formValues.email}
          onChange={handleInputChange}
        />
        <TextField
          id="password"
          name="password"
          label="Password"
          autoComplete="current-password"
          type={showPassword ? 'text' : 'password'}
          required
          value={formValues.password}
          onChange={handleInputChange}
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <IconButton onClick={() => setShowPassword(!showPassword)} edge="end">
                  <Iconify icon={showPassword ? 'eva:eye-fill' : 'eva:eye-off-fill'} />
                </IconButton>
              </InputAdornment>
            ),
          }}
        />
      </Stack>
      <Stack direction="row" alignItems="center" justifyContent="space-between" sx={{ my: 2 }} />
      <LoadingButton fullWidth size="large" type="submit" variant="contained" onClick={handleSubmit}>
        Sign up
      </LoadingButton>
    </>
  );
}
