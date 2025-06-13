/**
 * Restaurant DTO
 * Data Transfer Object structure for Restaurants
 */

export class RestaurantDTO {
    constructor({
        id = null,
        name = '',
        location = '',
        cuisineType = '',
        rating = 0.0,
        image = '',
    } = {}) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.cuisineType = cuisineType;
        this.rating = rating;
        this.image = image;
    }

    /**
     * Creates a RestaurantDTO instance from API response data
     * @param {Object} data - Restaurant data from API
     * @returns {RestaurantDTO} A new RestaurantDTO instance
     */
    static fromAPI(data) {
        return new RestaurantDTO({
            id: data.id,
            name: data.name,
            location: data.location,
            cuisineType: data.cuisineType,
            rating: data.rating,
            image: data.image,
        });
    }

    /**
     * Converts the DTO to a format suitable for API requests
     * @returns {Object} Object formatted for API requests
     */
    toAPIRequest() {
        return {
            name: this.name,
            location: this.location,
            cuisineType: this.cuisineType,
            rating: this.rating
        };
    }
}

export default RestaurantDTO;
