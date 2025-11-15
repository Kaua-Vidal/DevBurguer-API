import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter} from 'react-router-dom';
import GlobalStyles from './styles/globalStyles.js';
import { ToastContainer } from 'react-toastify';
import AppProvider from './hooks/index.jsx';
import { Elements } from '@stripe/react-stripe-js';
import stripePromise from './config/stripeConfig.js';
import { ThemeProvider } from 'styled-components';
import {standardTheme} from './styles/themes/standard.js'
import { Router } from './routes'

createRoot(document.getElementById('root')).render(
    <ThemeProvider theme={standardTheme}>
      <AppProvider>
        <StrictMode>
          <Elements stripe={stripePromise}>
          <BrowserRouter>
            <Router/>
          </BrowserRouter>
          </Elements >
          <GlobalStyles />
          <ToastContainer autoClose={2000} theme="colored" />
        </StrictMode>
      </AppProvider>
    </ThemeProvider>
);
