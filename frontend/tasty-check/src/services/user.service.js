import axios from 'axios';
import { API_CONFIG } from '../config/api.config.js';
class UserService {
  constructor() {
    this.baseUrl = API_CONFIG.baseUrl;
    this.endpoint = 'user';
    this.axiosInstance = axios.create({
      baseURL: this.baseUrl,
      headers: {
        'Content-Type': 'application/json',
      },
      withCredentials: true,
    });
  }

    async getCurrentUser() {
    try {
        const local = localStorage.getItem('user')
        if (!local) throw new Error('Utilizador não autenticado')

        const { id } = JSON.parse(local)
        const response = await this.axiosInstance.get(`${this.endpoint}/${id}`)
        return response.data
    } catch (error) {
        console.error('Erro ao obter perfil:', error)
        throw error
    }
    }


    async updateUser(data) {
        const local = localStorage.getItem('user');
        if (!local) throw new Error('Utilizador não autenticado');

        const { id } = JSON.parse(local);
        const response = await this.axiosInstance.put(`${this.endpoint}/${id}`, data);
        return response.data;
    }

    async getFavorites(userId) {
        try {
            // console.log('Obtendo favoritos para o usuário:', userId);
            const response = await this.axiosInstance.get(`${this.endpoint}/${userId}/favorites`);
            return response.data;
        } catch (error) {
            console.error('Erro ao obter favoritos:', error);
            throw error;
        }
    }
    
    /**
     * Add restaurant to user's favorites
     * @param {string} userId - User ID
     * @param {string} restaurantId - Restaurant ID to add to favorites
     * @returns {Promise<Object>} Response data
     */
    async addToFavorites(userId, restaurantId) {
        try {
            // console.log(`Adicionando restaurante ${restaurantId} aos favoritos do usuário ${userId}`);
            const response = await this.axiosInstance.post(`${this.endpoint}/${userId}/favorites/${restaurantId}`);
            return response.data;
        } catch (error) {
            console.error('Erro ao adicionar aos favoritos:', error);
            throw error;
        }
    }

    /**
     * Remove restaurant from user's favorites
     * @param {string} userId - User ID
     * @param {string} restaurantId - Restaurant ID to remove from favorites
     * @returns {Promise<Object>} Response data
     */
    async removeFromFavorites(userId, restaurantId) {
        try {
            const response = await this.axiosInstance.delete(`${this.endpoint}/${userId}/favorites/${restaurantId}`);
            return response.data;
        } catch (error) {
            console.error('Erro ao remover dos favoritos:', error);
            throw error;
        }
    }
}

export default UserService;
