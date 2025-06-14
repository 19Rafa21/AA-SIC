/**
 * Review Service
 * Handles all API requests for reviews
 */

import axios from 'axios';
import { API_CONFIG } from '../config/api.config.js';
import { ReviewDTO } from '../dto/review.dto.js';

export class ReviewService {
    constructor() {
        this.baseUrl = API_CONFIG.baseUrl;
        this.endpoint = 'review';
        this.axiosInstance = axios.create({
            baseURL: this.baseUrl,
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

    /**
     * Get all reviews
     * @returns {Promise<ReviewDTO[]>} Array of ReviewDTO objects
     */
    async getAllReviews() {
        try {
            const response = await this.axiosInstance.get(`${this.endpoint}`);
            return response.data.map(review => ReviewDTO.fromAPI(review));
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
            const response = await this.axiosInstance.get(`${this.endpoint}/${id}`);
            return ReviewDTO.fromAPI(response.data);
        } catch (error) {
            console.error(`Error fetching review with id ${id}:`, error);
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
            const response = await this.axiosInstance.get(`${this.endpoint}/${authorId}/author`);
            return response.data.map(review => ReviewDTO.fromAPI(review));
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
            const response = await this.axiosInstance.post(`${this.endpoint}`, reviewDTO.toCreateRequest());
            return ReviewDTO.fromAPI(response.data);
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
            const response = await this.axiosInstance.put(`${this.endpoint}/${id}`, reviewDTO.toUpdateRequest());
            return ReviewDTO.fromAPI(response.data);
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
            await this.axiosInstance.delete(`${this.endpoint}/${id}`);
            return true;
        } catch (error) {
            console.error(`Error deleting review with id ${id}:`, error);
            throw error;
        }
    }
}

export default ReviewService;
