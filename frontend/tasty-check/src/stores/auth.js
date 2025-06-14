/**
 * Authentication Store
 * Manages authentication state using Pinia
 */

import { defineStore } from 'pinia';
import authService from '../services/auth.service';

// Define a store for authentication
export const useAuthStore = defineStore('auth', {
  // State properties
  state: () => ({
    // User state
    user: null,
    isLoggedIn: false,
    
    // Token
    token: null,
    
    // Error handling
    error: null,
    
    // Loading states
    isLoading: false,
    
    // Remember me option
    rememberMe: false,
  }),
  
  // Getters (computed properties)
  getters: {
    /**
     * Check if user is authenticated
     * @returns {boolean}
     */
    isAuthenticated: (state) => state.isLoggedIn && !!state.token,
    
    /**
     * Get current user
     * @returns {Object|null}
     */
    currentUser: (state) => state.user,
    
    /**
     * Get authentication token
     * @returns {string|null}
     */
    authToken: (state) => state.token,
    
    /**
     * Check if current user is an owner
     * @returns {boolean}
     */
    isOwner: (state) => state.user?.role === 'Owner',
    
    /**
     * Check if there is an authentication error
     * @returns {boolean}
     */
    hasError: (state) => !!state.error,
  },
  
  // Actions (methods that can change the state)
  actions: {
    /**
     * Initialize the auth store (check if user is already logged in from localStorage)
     */
    init() {
      this.isLoading = true;
      try {
        // Check if user is logged in
        if (authService.isLoggedIn()) {
          this.user = authService.getCurrentUser();
          this.isLoggedIn = true;
          
          // Recuperar o token do localStorage
          const token = localStorage.getItem('authToken');
          if (token) {
            this.token = token;
          } else {
            // Se não houver token mas o usuário estiver marcado como logado,
            // isso pode indicar um problema - deslogamos o usuário
            if (this.isLoggedIn) {
              console.warn('No token found for logged in user, logging out');
              this.logout();
            }
          }
        }
      } catch (error) {
        console.error('Error initializing auth store', error);
        this.error = 'Failed to initialize authentication';
        // Em caso de erro, limpar o estado de autenticação
        this.resetAuthState();
      } finally {
        this.isLoading = false;
      }
    },
    
    /**
     * Reset authentication state
     * @private
     */
    resetAuthState() {
      this.user = null;
      this.isLoggedIn = false;
      this.token = null;
      // Não limpamos o erro aqui para que ele possa ser exibido
    },
    
    /**
     * Register a new user
     * @param {string} email - User email
     * @param {string} password - User password
     * @param {string} username - Username
     * @param {string} discriminator - User type ('User' or 'Owner')
     * @returns {Promise<boolean>} Success status
     */
    async register(email, password, username, discriminator = 'User') {
      this.isLoading = true;
      this.error = null;
      
      try {
        await authService.register(email, password, username, discriminator);
        return true;
      } catch (error) {
        console.error('Registration error:', error);
        this.error = error.response?.data?.message || 'Registration failed. Please try again.';
        return false;
      } finally {
        this.isLoading = false;
      }
    },
    
    /**
     * Login user
     * @param {string} email - User email
     * @param {string} password - User password
     * @param {boolean} rememberMe - Whether to remember the user
     * @returns {Promise<boolean>} Success status
     */
    async login(email, password, rememberMe = false) {
      this.isLoading = true;
      this.error = null;
      this.rememberMe = rememberMe;
      
      try {
        const response = await authService.login(email, password);
        
        // A resposta deve ter tanto os dados do usuário quanto o token JWT
        // Em alguns casos, o token pode estar no próprio objeto do usuário
        // ou em um campo separado, dependendo da API

        // Extrair o token - assumindo que ele está na resposta diretamente ou em uma propriedade
        let token = null;
        let userData = null;
        
        if (typeof response === 'string') {
          // Se a resposta for apenas o token em string
          token = response;
          // Neste caso, podemos precisar extrair as informações do usuário do token JWT
          // assumindo que usamos a biblioteca jwt-decode ou similar
          userData = this.extractUserFromToken(token);
        } else if (response && typeof response === 'object') {
          // Se a resposta for um objeto, procuramos pelo token
          if (response.token) {
            token = response.token;
            userData = response; // Pode conter outros dados do usuário além do token
          } else if (response.accessToken) {
            token = response.accessToken;
            userData = response;
          } else {
            // Se não encontrarmos o token em campos comuns, assumimos que a resposta completa é o usuário
            // e o token pode estar em outro lugar na resposta ou não foi retornado
            userData = response;
            
            // Em alguns casos o token pode ser o próprio objeto inteiro
            // Isso é menos comum, mas podemos tentar verificar
            if (typeof response === 'string' && response.split('.').length === 3) {
              token = response;
            }
          }
        }
        
        // Armazenar os dados no estado e no localStorage
        if (userData) {
          this.user = userData;
          this.isLoggedIn = true;
          
          if (token) {
            this.token = token;
            localStorage.setItem('authToken', token);
          }
          
          return true;
        } else {
          this.error = 'Invalid response format from server';
          return false;
        }
      } catch (error) {
        console.error('Login error:', error);
        this.error = error.response?.data?.message || 'Invalid email or password';
        return false;
      } finally {
        this.isLoading = false;
      }
    },
    
    /**
     * Extract user information from JWT token
     * @param {string} token - JWT token
     * @returns {Object} User data from token
     * @private
     */
    extractUserFromToken(token) {
      try {
        // JWT tokens are made of three parts separated by dots
        // The middle part contains the payload (user data) encoded in base64
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(
          atob(base64)
            .split('')
            .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
            .join('')
        );
        
        return JSON.parse(jsonPayload);
      } catch (error) {
        console.error('Error extracting user from token', error);
        return null;
      }
    },
    
    /**
     * Logout user
     * @returns {Promise<void>}
     */
    async logout() {
      this.isLoading = true;
      
      try {
        await authService.logout();
      } catch (error) {
        console.error('Logout error:', error);
      } finally {
        // Always reset state regardless of API response
        this.user = null;
        this.isLoggedIn = false;
        this.token = null;
        this.error = null;
        this.isLoading = false;
        
        // Remove authentication token from localStorage
        localStorage.removeItem('authToken');
      }
    },
    
    /**
     * Clear any authentication errors
     */
    clearError() {
      this.error = null;
    },
    
    /**
     * Get authorization header with JWT token for API requests
     * @returns {Object} Headers object with Authorization
     */
    getAuthHeader() {
      return this.token ? { 
        'Authorization': `Bearer ${this.token}` 
      } : {};
    }
  }
});
