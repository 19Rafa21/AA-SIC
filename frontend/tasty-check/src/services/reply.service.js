/**
 * Reply Service
 * Handles all API requests for replies
 */

import axios from 'axios';
import { API_CONFIG } from '../config/api.config.js';
import { ReplyDTO } from '../dto/reply.dto.js';

export class ReplyService {
    constructor() {
        this.baseUrl = API_CONFIG.baseUrl;
        this.endpoint = 'reply';
        this.axiosInstance = axios.create({
            baseURL: this.baseUrl,
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

    /**
     * Get a reply by ID
     * @param {string} id - Reply ID
     * @returns {Promise<ReplyDTO>} ReplyDTO object
     */
    async getReplyById(id) {
        try {
            const response = await this.axiosInstance.get(`${this.endpoint}/${id}`);
            return ReplyDTO.fromAPI(response.data);
        } catch (error) {
            console.error(`Error fetching reply with id ${id}:`, error);
            throw error;
        }
    }

    /**
     * Create a new reply
     * @param {Object} replyData - Reply data containing text, userId, and reviewId
     * @returns {Promise<ReplyDTO>} Created ReplyDTO
     */
    async createReply(replyData) {
        try {
            const replyDTO = new ReplyDTO({
                text: replyData.text,
                userId: replyData.userId,
                reviewId: replyData.reviewId
            });
            
            const response = await this.axiosInstance.post(`${this.endpoint}`, replyDTO.toCreateRequest());
            return ReplyDTO.fromAPI(response.data);
        } catch (error) {
            console.error('Error creating reply:', error);
            throw error;
        }
    }
}

export default ReplyService;
