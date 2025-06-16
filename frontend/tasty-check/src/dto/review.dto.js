/**
 * Review DTO
 * Data Transfer Object structure for Reviews
 */

export class ReviewDTO {
    constructor({
        id = null,
        text = '',
        rating = 0.0,
        userId = null,
        username = '',
        restaurantId = null,
        date = null,
        replies = [],
        reviewImages = [] // <---- ADICIONAR ISTO
    } = {}) {
        this.id = id;
        this.text = text;
        this.rating = rating;
        this.userId = userId;
        this.username = username;
        this.restaurantId = restaurantId;
        this.date = date;
        this.replies = replies;
        this.reviewImages = reviewImages; // <---- ADICIONAR ISTO
    }

    /**
     * Creates a ReviewDTO instance from API response data
     * @param {Object} data - Review data from API
     * @returns {ReviewDTO} A new ReviewDTO instance
     */
// review.dto.js

    static fromAPI(data) {
        return new ReviewDTO({
            id: data.id,
            text: data.text,
            rating: data.rating,
            userId: data.userId,
            username: data.username,
            restaurantId: data.restaurantId,
            date: data.date,
            replies: data.replies || [],
            reviewImages: data.reviewImages || [] // <---- ADICIONAR ISTO
        });
    }


    /**
     * Converts the DTO to a format suitable for API create requests
     * @returns {Object} Object formatted for API requests
     */
    toCreateRequest() {
        return {
            text: this.text,
            rating: this.rating,
            userId: this.userId,
            restaurantId: this.restaurantId
        };
    }

    /**
     * Converts the DTO to a format suitable for API update requests
     * @returns {Object} Object formatted for API update requests
     */
toUpdateRequest() {
  return {
    text: this.text,
    rating: this.rating
    // reviewImages só se for para PUT JSON, mas estamos a usar multipart/form-data
  }
}

}

export default ReviewDTO;
