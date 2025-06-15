/**
 * Reply DTO
 * Data Transfer Object structure for Replies
 */

export class ReplyDTO {
    constructor({
        id = null,
        text = '',
        rating = 0.0,
        userId = null,
        username = '',
        restaurantId = null,
        date = null,
        reviewId = null
    } = {}) {
        this.id = id;
        this.text = text;
        this.rating = rating;
        this.userId = userId;
        this.username = username;
        this.restaurantId = restaurantId;
        this.date = date;
        this.reviewId = reviewId;
    }

    /**
     * Creates a ReplyDTO instance from API response data
     * @param {Object} data - Reply data from API
     * @returns {ReplyDTO} A new ReplyDTO instance
     */
    static fromAPI(data) {
        return new ReplyDTO({
            id: data.id,
            text: data.text,
            rating: data.rating,
            userId: data.userId,
            username: data.username,
            restaurantId: data.restaurantId,
            date: data.date,
            reviewId: data.reviewId
        });
    }

    /**
     * Converts the DTO to a format suitable for API create requests
     * @returns {Object} Object formatted for API requests
     */
    toCreateRequest() {
        return {
            text: this.text,
            userId: this.userId,
            reviewId: this.reviewId
        };
    }

    /**
     * Converts the DTO to a format suitable for API update requests
     * @returns {Object} Object formatted for API update requests
     */
    toUpdateRequest() {
        return {
            text: this.text
        };
    }
}

export default ReplyDTO;
