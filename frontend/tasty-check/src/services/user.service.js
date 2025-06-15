import axios from 'axios';
import { API_CONFIG } from '../config/api.config.js';
import { watch } from 'vue';

class UserService {
  constructor() {
    this.baseUrl = API_CONFIG.baseUrl;
    this.endpoint = 'users'; // ou 'profile', depende do teu backend
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
        const response = await this.axiosInstance.get(`user/${id}`)
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
        const response = await this.axiosInstance.put(`user/${id}`, data);
        return response.data;
    }

    async getFavorites(userId) {
        try {
            console.log('Obtendo favoritos para o usuário:', userId);
            const response = await this.axiosInstance.get(`user/${userId}/favorites`);
            return response.data;
        } catch (error) {
            console.error('Erro ao obter favoritos:', error);
            throw error;
        }
    }


}

export default new UserService();
