/**
 * Review Service
 * Handles all API requests for reviews
 */

import { API_CONFIG } from '../config/api.config.js';
import { ReviewDTO } from '../dto/review.dto.js';

export class ReviewService {
    constructor() {
        this.baseUrl = API_CONFIG.baseUrl;
        this.endpoint = 'review';
    }

    /**
     * Get all reviews
     * @returns {Promise<ReviewDTO[]>} Array of ReviewDTO objects
     */
    async getAllReviews() {
        try {
            const response = await fetch(`${this.baseUrl}${this.endpoint}`);
            
            if (!response.ok) {
                throw new Error(`Failed to fetch reviews: ${response.status}`);
            }
            
            const data = await response.json();
            return data.map(review => ReviewDTO.fromAPI(review));
        } catch (error) {
            console.error('Error fetching reviews:', error);
            throw error;
        }
    }

    /**
     * Get a review by ID
     * @param {string} id - Review ID
     * @returns {Promise<ReviewDTO>} ReviewDTO object
     */
    async getReviewById(id) {
        try {
            const response = await fetch(`${this.baseUrl}${this.endpoint}/${id}`);
            
            if (!response.ok) {
                throw new Error(`Failed to fetch review: ${response.status}`);
            }
            
            const data = await response.json();
            return ReviewDTO.fromAPI(data);
        } catch (error) {
            console.error(`Error fetching review with id ${id}:`, error);
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
            const response = await fetch(`${this.baseUrl}${this.endpoint}/${restaurantId}/restaurant`);
            
            if (!response.ok) {
                throw new Error(`Failed to fetch reviews for restaurant: ${response.status}`);
            }
            
            const data = await response.json();
            return data.map(review => ReviewDTO.fromAPI(review));
        } catch (error) {
            console.error(`Error fetching reviews for restaurant ${restaurantId}:`, error);
            throw error;
        }
    }

    /**
     * Get reviews by author/user ID
     * @param {string} authorId - Author/User ID
     * @returns {Promise<ReviewDTO[]>} Array of ReviewDTO objects
     */
    async getReviewsByAuthor(authorId) {
        try {
            const response = await fetch(`${this.baseUrl}${this.endpoint}/${authorId}/author`);
            
            if (!response.ok) {
                throw new Error(`Failed to fetch reviews by author: ${response.status}`);
            }
            
            const data = await response.json();
            return data.map(review => ReviewDTO.fromAPI(review));
        } catch (error) {
            console.error(`Error fetching reviews by author ${authorId}:`, error);
            throw error;
        }
    }

    /**
     * Create a new review
     * @param {ReviewDTO} reviewDTO - Review data
     * @returns {Promise<ReviewDTO>} Created ReviewDTO
     */
    async createReview(reviewDTO) {
        try {
            const response = await fetch(`${this.baseUrl}${this.endpoint}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(reviewDTO.toCreateRequest())
            });

            if (!response.ok) {
                throw new Error(`Failed to create review: ${response.status}`);
            }

            const data = await response.json();
            return ReviewDTO.fromAPI(data);
        } catch (error) {
            console.error('Error creating review:', error);
            throw error;
        }
    }

    /**
     * Update an existing review
     * @param {string} id - Review ID
     * @param {ReviewDTO} reviewDTO - Updated review data
     * @returns {Promise<ReviewDTO>} Updated ReviewDTO
     */
    async updateReview(id, reviewDTO) {
        try {
            const response = await fetch(`${this.baseUrl}${this.endpoint}/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(reviewDTO.toUpdateRequest())
            });

            if (!response.ok) {
                throw new Error(`Failed to update review: ${response.status}`);
            }

            const data = await response.json();
            return ReviewDTO.fromAPI(data);
        } catch (error) {
            console.error(`Error updating review with id ${id}:`, error);
            throw error;
        }
    }

    /**
     * Delete a review by ID
     * @param {string} id - Review ID
     * @returns {Promise<boolean>} Success status
     */
    async deleteReview(id) {
        try {
            const response = await fetch(`${this.baseUrl}${this.endpoint}/${id}`, {
                method: 'DELETE'
            });

            if (!response.ok) {
                throw new Error(`Failed to delete review: ${response.status}`);
            }

            return true;
        } catch (error) {
            console.error(`Error deleting review with id ${id}:`, error);
            throw error;
        }
    }
}

export default ReviewService;
