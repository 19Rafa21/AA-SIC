/**
 * Authentication Service
 * Handles all API requests for user authentication
 */

import axios from 'axios';
import { API_CONFIG } from '../config/api.config.js';
import { UserDTO } from '../dto/auth.dto.js';

class AuthenticationService {
    constructor() {
        this.baseUrl = API_CONFIG.baseUrl;
        this.endpoint = 'authentication';
        this.axiosInstance = axios.create({
            baseURL: this.baseUrl,
            headers: {
                'Content-Type': 'application/json'
            },
            withCredentials: true // Important for handling cookies/sessions
        });
    }

    /**
     * Register a new user
     * @param {string} email - User email
     * @param {string} password - User password
     * @param {string} username - Username
     * @param {string} discriminator - User type ('User' or 'Owner')
     * @param {File} [imageFile] - Optional profile image file
     * @returns {Promise<Object>} Registration response
     */
 async register(email, password, username, discriminator = 'User', imageFile = null) {
  try {
    const userData = UserDTO.forRegistration(email, username, password, discriminator);

    // Criar SEMPRE formData
    const formData = new FormData();
    formData.append('user', JSON.stringify(userData));

    // Se houver imagem, envia; senão, envia um Blob vazio para manter a chave
    if (imageFile) {
      formData.append('file', imageFile);
    } else {
      formData.append('file', new Blob()); // <-- truque para evitar erro no backend
    }

    // Enviar sempre como multipart
    const multipartAxios = axios.create({
      baseURL: this.baseUrl,
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      withCredentials: true
    });

    const response = await multipartAxios.post(
      `${this.endpoint}/register`,
      formData
    );

    return response.data;
  } catch (error) {
    console.error('Error registering user:', error);
    throw error;
  }
}


    /**
     * Login user
     * @param {string} email - User email
     * @param {string} password - User password
     * @returns {Promise<Object>} Login response with user data
     */
    async login(email, password) {
        try {
            const loginData = UserDTO.forLogin(email, password);
            const response = await this.axiosInstance.post(
                `${this.endpoint}/login`, 
                loginData
            );
            
            // Store user data in localStorage
            if (response.data) {
                localStorage.setItem('isLoggedIn', 'true');
                localStorage.setItem('user', JSON.stringify(response.data));
            }
            
            return response.data;
        } catch (error) {
            console.error('Error logging in:', error);
            throw error;
        }
    }

    /**
     * Logout user
     * @returns {Promise<void>}
     */
    async logout() {
        try {
            await this.axiosInstance.get(`${this.endpoint}/logout`);
            
            // Clear user data from localStorage
            localStorage.removeItem('isLoggedIn');
            localStorage.removeItem('user');
            
            return true;
        } catch (error) {
            console.error('Error logging out:', error);
            throw error;
        }
    }

    /**
     * Check if user is logged in
     * @returns {boolean}
     */
    isLoggedIn() {
        return localStorage.getItem('isLoggedIn') === 'true';
    }

    /**
     * Get current user data
     * @returns {Object|null} User data or null if not logged in
     */
    getCurrentUser() {
        const userJson = localStorage.getItem('user');
        return userJson ? JSON.parse(userJson) : null;
    }

    /**
     * Check if current user is an owner
     * @returns {boolean}
     */
    isOwner() {
        const user = this.getCurrentUser();
        return user && user.discriminator === 'Owner';
    }

    /**
     * Update user data (including optional profile image)
     * @param {string} userId - ID do utilizador a atualizar
     * @param {Object} userData - Dados atualizados do utilizador (username, email, discriminator)
     * @param {File|null} imageFile - Nova imagem de perfil (opcional)
     * @returns {Promise<Object>} Resposta da API
     */
    async updateUser(userId, userData, imageFile = null) {
    try {
        const formData = new FormData();
        formData.append('user', JSON.stringify(userData));

        if (imageFile) {
        formData.append('file', imageFile);
        } else {
        formData.append('file', new Blob([], { type: 'application/octet-stream' }), 'empty.jpg');
        }

        const multipartAxios = axios.create({
        baseURL: this.baseUrl,
        headers: {
            'Content-Type': 'multipart/form-data'
        },
        withCredentials: true
        });

        const response = await multipartAxios.put(
        `/user/${userId}`,
        formData
        );

        return response.data;
    } catch (error) {
        console.error('Erro ao atualizar utilizador:', error);
        throw error;
    }
    }

}

export default new AuthenticationService();
