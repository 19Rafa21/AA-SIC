/**
 * Auth Initialization
 * This file initializes the authentication state from localStorage on app startup
 */

import { useAuthStore } from './stores';

// Initialize authentication state
export function initializeAuth() {
  const authStore = useAuthStore();
  authStore.init();
  return authStore;
}

export default initializeAuth;
