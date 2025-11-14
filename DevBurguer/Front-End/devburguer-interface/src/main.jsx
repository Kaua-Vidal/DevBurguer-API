import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';
import GlobalStyles from './styles/globalStyles.js';
import { router } from './routes/index.jsx';
import { ToastContainer } from 'react-toastify';
import AppProvider from './hooks/index.jsx';
import { Elements } from '@stripe/react-stripe-js';
import stripePromise from './config/stripeConfig.js';

createRoot(document.getElementById('root')).render(
  <AppProvider>
    <StrictMode>
      <Elements stripe={stripePromise}>
      <RouterProvider router={router} />
      </Elements >
      <GlobalStyles />
      <ToastContainer autoClose={2000} theme="colored" />
    </StrictMode>
  </AppProvider>,
);
