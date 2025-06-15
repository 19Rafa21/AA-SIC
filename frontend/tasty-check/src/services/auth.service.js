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
            
            // If image is provided, send as multipart/form-data
            if (imageFile) {
                const formData = new FormData();
                formData.append('user', JSON.stringify(userData));
                formData.append('file', imageFile);
                
                // Create a new axios instance for this request with multipart/form-data
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
            } else {
                // Regular JSON request without image
                const response = await this.axiosInstance.post(
                    `${this.endpoint}/register`, 
                    userData
                );
                return response.data;
            }
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
}

export default new AuthenticationService();
