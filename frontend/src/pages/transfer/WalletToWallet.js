import { LoadingButton } from '@mui/lab';
import { Autocomplete, Button, Card, Grid, Stack, TextField } from '@mui/material';
import { useSnackbar } from 'notistack';
import { useEffect, useState } from 'react';
import { Helmet } from 'react-helmet-async';
import { useNavigate } from 'react-router-dom';
import AuthService from '../../services/AuthService';
import HttpService from '../../services/HttpService';

export default function WalletToWallet() {
  const defaultValues = {
    amount: '',
    fromWalletIban: '',
    toWalletIban: '',
    description: '',
    typeId: 1, // set as Transfer by default
  };

  const navigate = useNavigate();
  const { enqueueSnackbar } = useSnackbar();
  const [formValues, setFormValues] = useState(defaultValues);
  const [fromWalletIbans, setFromWalletIbans] = useState([]);
  const [fromWalletIban, setFromWalletIban] = useState();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormValues({
      ...formValues,
      [name]: value,
    });
  };

  useEffect(() => {
    const userId = AuthService.getCurrentUser()?.id;
    HttpService.getWithAuth(`/wallets/users/${userId}`).then((result) => {
      setFromWalletIbans(result.data);
    });
  }, []);

  const handleWalletChange = (event) => {
    setFromWalletIban(event.iban);
    setFormValues({
      ...formValues,
      fromWalletIban: event.iban,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    HttpService.postWithAuth('/wallets/transfer', formValues)
      .then((response) => {
        enqueueSnackbar('Transfer completed successfully', { variant: 'success' });
        navigate('/transactions');
      })
      .catch((error) => {
        if (error.response?.data?.errors) {
          error.response?.data?.errors.map((e) => enqueueSnackbar(e.message, { variant: 'error' }));
        } else if (error.response?.data?.message) {
          enqueueSnackbar(error.response?.data?.message, { variant: 'error' });
        } else {
          enqueueSnackbar(error.message, { variant: 'error' });
        }
      });
  };

  return (
    <>
      <Helmet>
        <title> Wallet to Wallet | e-Wallet </title>
      </Helmet>
      <Card>
        <Grid container alignItems="left" justify="left" direction="column" sx={{ width: 400, padding: 5 }}>
          <Stack spacing={3}>
            <TextField
              id="amount"
              name="amount"
              label="Amount"
              autoFocus
              required
              value={formValues.amount}
              onChange={handleInputChange}
            />
            <Autocomplete
              ListboxProps={{ style: { maxHeight: 200, overflow: 'auto' } }}
              required
              disablePortal
              id="fromWalletIban"
              noOptionsText="no records"
              options={fromWalletIbans}
              getOptionLabel={(fromWalletIban) => fromWalletIban.name}
              isOptionEqualToValue={(option, value) => option.name === value.name}
              onChange={(event, newValue) => {
                handleWalletChange(newValue);
              }}
              renderInput={(params) => <TextField {...params} label="Sender Wallet" />}
            />
            <TextField
              id="toWalletIban"
              name="toWalletIban"
              label="IBAN of Receiver Wallet"
              autoComplete="toWalletIban"
              required
              value={formValues.toWalletIban}
              onChange={handleInputChange}
            />
            <TextField
              id="description"
              name="description"
              label="Description"
              autoComplete="description"
              required
              value={formValues.description}
              onChange={handleInputChange}
            />
          </Stack>
          <Stack spacing={2} direction="row" alignItems="right" justifyContent="end" sx={{ mt: 4 }}>
            <Button sx={{ width: 120 }} variant="outlined" onClick={() => navigate('/wallets')}>
              Cancel
            </Button>
            <LoadingButton sx={{ width: 120 }} size="large" type="submit" variant="contained" onClick={handleSubmit}>
              Save
            </LoadingButton>
          </Stack>
        </Grid>
      </Card>
    </>
  );
}
