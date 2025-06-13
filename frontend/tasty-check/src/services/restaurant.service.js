/**
 * Restaurant Service
 * Handles all API requests for restaurants
 */

import { API_CONFIG } from '../config/api.config.js';
import { RestaurantDTO } from '../dto/restaurant.dto.js';

export class RestaurantService {
    constructor() {
        this.baseUrl = API_CONFIG.baseUrl;
        this.endpoint = 'restaurant';
    }

    /**
     * Get all restaurants
     * @returns {Promise<RestaurantDTO[]>} Array of RestaurantDTO objects
     */
    async getAllRestaurants() {
        try {
            const response = await fetch(`${this.baseUrl}${this.endpoint}`);
            
            if (!response.ok) {
                throw new Error(`Failed to fetch restaurants: ${response.status}`);
            }
            
            const data = await response.json();
            return data.map(restaurant => RestaurantDTO.fromAPI(restaurant));
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
            const response = await fetch(`${this.baseUrl}${this.endpoint}/${id}`);
            
            if (!response.ok) {
                throw new Error(`Failed to fetch restaurant: ${response.status}`);
            }
            
            const data = await response.json();
            return RestaurantDTO.fromAPI(data);
        } catch (error) {
            console.error(`Error fetching restaurant with id ${id}:`, error);
            throw error;
        }
    }

    /**
     * Create a new restaurant
     * @param {RestaurantDTO} restaurantDTO - Restaurant data
     * @returns {Promise<RestaurantDTO>} Created RestaurantDTO
     */
    async createRestaurant(restaurantDTO) {
        try {
            const response = await fetch(`${this.baseUrl}${this.endpoint}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(restaurantDTO.toAPIRequest())
            });

            if (!response.ok) {
                throw new Error(`Failed to create restaurant: ${response.status}`);
            }

            const data = await response.json();
            return RestaurantDTO.fromAPI(data);
        } catch (error) {
            console.error('Error creating restaurant:', error);
            throw error;
        }
    }

    /**
     * Update an existing restaurant
     * @param {string} id - Restaurant ID
     * @param {RestaurantDTO} restaurantDTO - Updated restaurant data
     * @returns {Promise<RestaurantDTO>} Updated RestaurantDTO
     */
    async updateRestaurant(id, restaurantDTO) {
        try {
            const response = await fetch(`${this.baseUrl}${this.endpoint}/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(restaurantDTO.toAPIRequest())
            });

            if (!response.ok) {
                throw new Error(`Failed to update restaurant: ${response.status}`);
            }

            const data = await response.json();
            return RestaurantDTO.fromAPI(data);
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
            const response = await fetch(`${this.baseUrl}${this.endpoint}/${id}`, {
                method: 'DELETE'
            });

            if (!response.ok) {
                throw new Error(`Failed to delete restaurant: ${response.status}`);
            }

            return true;
        } catch (error) {
            console.error(`Error deleting restaurant with id ${id}:`, error);
            throw error;
        }
    }


    /**^
     * Put method that gets restaurants with filter

     */
    async getRestaurantsWithFilter(name, location, cuisineType, rating) {
        try {
            const response = await fetch(`${this.baseUrl}${this.endpoint}/filters`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name: name || '',
                    location: location || '',
                    cuisineType: cuisineType || '',
                    rating: rating || 0.0
                })
            });

            if (!response.ok) {
                throw new Error(`Failed to fetch restaurants with filter: ${response.status}`);
            }

            const data = await response.json();
            return data.map(restaurant => RestaurantDTO.fromAPI(restaurant));
        } catch (error) {
            console.error('Error fetching restaurants with filter:', error);
            throw error;
        }
    }
}

export default RestaurantService;
