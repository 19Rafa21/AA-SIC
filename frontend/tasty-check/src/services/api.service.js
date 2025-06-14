/**
 * API Service with authentication interceptor
 */

import axios from 'axios';
import { API_CONFIG } from '../config/api.config';
import { useAuthStore } from '../stores';

// Create axios instance with base URL
const apiService = axios.create({
  baseURL: API_CONFIG.baseUrl,
  headers: {
    'Content-Type': 'application/json'
  }
});

// Request interceptor for API calls
apiService.interceptors.request.use(
  (config) => {
    // Get the auth store
    const authStore = useAuthStore();
    
    // If user is authenticated, add token to request headers
    if (authStore.isAuthenticated && authStore.authToken) {
      config.headers['Authorization'] = `Bearer ${authStore.authToken}`;
    }
    
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor for API calls
apiService.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    // Handle 401 Unauthorized errors (token expired or invalid)
    if (error.response && error.response.status === 401) {
      // Get the auth store
      const authStore = useAuthStore();
      
      // Log out the user
      authStore.logout();
      
      // You could also redirect to login page here
      // window.location.href = '/login';
    }
    
    return Promise.reject(error);
  }
);

export default apiService;
