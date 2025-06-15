/**
 * Dish Service
 * Handles all API requests for dishes
 */

import axios from 'axios';
import { API_CONFIG } from '../config/api.config.js';
import { DishDTO } from '../dto/dish.dto.js';

export class DishService {
    constructor() {
        this.baseUrl = API_CONFIG.baseUrl;
        this.endpoint = 'dishes';
        this.axiosInstance = axios.create({
            baseURL: this.baseUrl,
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

    /**
     * Get dishes by restaurant ID
     * @param {string} restaurantId - Restaurant ID
     * @returns {Promise<DishDTO[]>} Array of DishDTO objects
     */
    async getDishesByRestaurant(restaurantId) {
        try {
            const response = await this.axiosInstance.get(`${this.endpoint}/restaurant/${restaurantId}`);
            return Array.isArray(response.data) 
                ? response.data.map(dish => DishDTO.fromAPI(dish))
                : [DishDTO.fromAPI(response.data)];
        } catch (error) {
            console.error(`Error fetching dishes for restaurant ${restaurantId}:`, error);
            throw error;
        }
    }

    /**
     * Create a new dish
     * @param {DishDTO} dishDTO - Dish data (without ID)
     * @returns {Promise<DishDTO>} Created DishDTO
     */
    async createDish(dishDTO) {
        try {
            // For creation, ensure we're not sending an ID
            const requestData = dishDTO.toAPIRequest();
            delete requestData.id; // Remove ID if present
            
            const response = await this.axiosInstance.post(
                `${this.endpoint}`, 
                requestData
            );
            return DishDTO.fromAPI(response.data);
        } catch (error) {
            console.error('Error creating dish:', error);
            throw error;
        }
    }

    /**
     * Update an existing dish
     * @param {string} id - Dish ID
     * @param {DishDTO} dishDTO - Updated dish data
     * @returns {Promise<DishDTO>} Updated DishDTO
     */
    async updateDish(id, dishDTO) {
        try {
            // Make sure the ID is set in the DTO
            dishDTO.id = id;
            
            const response = await this.axiosInstance.put(
                `${this.endpoint}/${id}`, 
                dishDTO.toAPIRequest()
            );
            return DishDTO.fromAPI(response.data);
        } catch (error) {
            console.error(`Error updating dish with id ${id}:`, error);
            throw error;
        }
    }

    /**
     * Delete a dish by ID
     * @param {string} id - Dish ID
     * @returns {Promise<boolean>} Success status
     */
    async deleteDish(id) {
        try {
            await this.axiosInstance.delete(`${this.endpoint}/${id}`);
            return true;
        } catch (error) {
            console.error(`Error deleting dish with id ${id}:`, error);
            throw error;
        }
    }
}

export default DishService;
