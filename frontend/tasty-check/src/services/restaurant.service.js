/**
 * Restaurant Service
 * Handles all API requests for restaurants
 */

import axios from 'axios';
import { API_CONFIG } from '../config/api.config.js';
import { RestaurantDTO, RestaurantDetailedDTO } from '../dto/restaurant.dto.js';
import { ReviewDTO } from '../dto/review.dto.js';

export class RestaurantService {
    constructor() {
        this.baseUrl = API_CONFIG.baseUrl;
        this.endpoint = 'restaurant';
        this.axiosInstance = axios.create({
            baseURL: this.baseUrl,
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

    /**
     * Get all restaurants
     * @returns {Promise<RestaurantDTO[]>} Array of RestaurantDTO objects
     */
    async getAllRestaurants() {
        try {
            const response = await this.axiosInstance.get(`${this.endpoint}`);
            return response.data;
        } catch (error) {
            console.error('Error fetching restaurants:', error);
            throw error;
        }
    }

    /**
     * Get a restaurant by ID
     * @param {string} id - Restaurant ID
     * @returns {Promise<RestaurantDTO>} RestaurantDTO object
     */
    async getRestaurantById(id) {
        try {
            const response = await this.axiosInstance.get(`${this.endpoint}/${id}`);
            return RestaurantDTO.fromAPI(response.data);
        } catch (error) {
            console.error(`Error fetching restaurant with id ${id}:`, error);
            throw error;
        }
    }

    /**
     * Create a new restaurant
     * @param {RestaurantDetailedDTO} restaurantDetailedDTO - Detailed restaurant data (without ID)
     * @returns {Promise<RestaurantDTO>} Created RestaurantDTO
     */
async createRestaurant(restaurantDetailedDTO, coverImageFile, menuImageFiles = [], foodImageFiles = []) {
  try {
    const formData = new FormData();

    // Parte textual: JSON com os dados do restaurante
    const restaurantData = restaurantDetailedDTO.toAPIRequest();
    delete restaurantData.id;
    formData.append('restaurant', JSON.stringify(restaurantData));

    // Imagem de capa
    if (coverImageFile) {
      formData.append('coverImage', coverImageFile);
    }

    // Imagens do menu
    for (const file of menuImageFiles) {
      formData.append('menuImages', file);
    }

    // Imagens da comida
    for (const file of foodImageFiles) {
      formData.append('foodImages', file);
    }

    // Faz o request com headers corretos (axios faz isso automaticamente com FormData)
    const response = await axios.post(`${this.baseUrl}/${this.endpoint}`, formData);
    return RestaurantDTO.fromAPI(response.data);
  } catch (error) {
    console.error('❌ Erro ao criar restaurante:', error.response?.data || error.message);
    throw error;
  }
}



    /**
     * Update an existing restaurant
     * @param {string} id - Restaurant ID
     * @param {RestaurantDetailedDTO} restaurantDetailedDTO - Updated detailed restaurant data
     * @returns {Promise<RestaurantDTO>} Updated RestaurantDTO
     */
    async updateRestaurant(id, restaurantDetailedDTO) {
        try {
            // Make sure the ID is set in the DTO
            restaurantDetailedDTO.id = id;
            
            const response = await this.axiosInstance.put(
                `${this.endpoint}/${id}`, 
                restaurantDetailedDTO.toAPIRequest(true) // Include ID for updates
            );
            return RestaurantDTO.fromAPI(response.data);
        } catch (error) {
            console.error(`Error updating restaurant with id ${id}:`, error);
            throw error;
        }
    }

    /**
     * Delete a restaurant by ID
     * @param {string} id - Restaurant ID
     * @returns {Promise<boolean>} Success status
     */
    async deleteRestaurant(id) {
        try {
            await this.axiosInstance.delete(`${this.endpoint}/${id}`);
            return true;
        } catch (error) {
            console.error(`Error deleting restaurant with id ${id}:`, error);
            throw error;
        }
    }


    /**
     * Post method that gets restaurants with filter
     * @param {string} name - Restaurant name filter
     * @param {string} location - Location filter
     * @param {string} cuisineType - Cuisine type filter
     * @param {number} rating - Minimum rating filter
     * @returns {Promise<RestaurantDTO[]>} Filtered restaurants
     */
    async getRestaurantsWithFilter(name, location, cuisineType, rating) {
        try {
            const response = await this.axiosInstance.post(`${this.endpoint}/filters`, {
                name: name || '',
                location: location || '',
                cuisineType: cuisineType || '',
                rating: rating || 0.0
            });
            
            // Handle case where response.data is a string
            let restaurantsData = response.data;
            if (typeof response.data === 'string') {
                try {
                    restaurantsData = JSON.parse(response.data);
                } catch (parseError) {
                    console.error('Error parsing response data:', parseError);
                    return [];
                }
            }
            
            // Now check if we have an array
            if (Array.isArray(restaurantsData)) {
                return restaurantsData.map(restaurant => RestaurantDTO.fromAPI(restaurant));
            } else {
                console.error('Parsed data is not an array:', restaurantsData);
                return [];
            }
        } catch (error) {
            console.error('Error fetching restaurants with filter:', error);
            throw error;
        }
    }

    /**
         * Get reviews by restaurant ID
         * @param {string} restaurantId - Restaurant ID
         * @returns {Promise<ReviewDTO[]>} Array of ReviewDTO objects
         */
        async getReviewsByRestaurant(restaurantId) {
            try {
                const response = await this.axiosInstance.get(`${this.endpoint}/${restaurantId}/reviews`);
                return response.data.map(review => ReviewDTO.fromAPI(review));
            } catch (error) {
                console.error(`Error fetching reviews for restaurant ${restaurantId}:`, error);
                throw error;
            }
        }
}

export default RestaurantService;
