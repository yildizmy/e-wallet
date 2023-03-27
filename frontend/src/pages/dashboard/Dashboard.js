import { Container, Grid, Typography } from '@mui/material';
import { Helmet } from 'react-helmet-async';
import { AppWidgetSummary } from '../../sections/@dashboard/app';

export default function Dashboard() {
  return (
    <>
      <Helmet>
        <title> Dashboard | Minimal UI </title>
      </Helmet>
      <Container maxWidth="xl">
        <Typography variant="h4" sx={{ mb: 5 }}>
          Dashboard
        </Typography>
        <Grid container spacing={3}>
          <Grid item xs={12} sm={6} md={3}>
            <AppWidgetSummary title="Wallets" total={714000} icon={'ant-design:wallet-outlined'} />
            <Typography variant="body2" sx={{ my: 2 }}>
              (*) based on mock data
            </Typography>
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <AppWidgetSummary title="Users" total={253000} color="warning" icon={'ant-design:user-outlined'} />
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <AppWidgetSummary
              title="Transaction Amount (total)"
              total={1352831}
              color="info"
              icon={'ant-design:transaction-outlined'}
            />
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <AppWidgetSummary
              title="Transactions (monthly)"
              total={123000}
              color="error"
              icon={'ant-design:euro-outlined'}
            />
          </Grid>
        </Grid>
      </Container>
    </>
  );
}
