import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';
import GlobalStyles from './styles/globalStyles.js';
import { router } from './routes/index.jsx';
import { ToastContainer } from 'react-toastify';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <RouterProvider router={router}/>
    <GlobalStyles/>
    <ToastContainer autoClose={2000} theme='colored'/>
  </StrictMode>,
);
