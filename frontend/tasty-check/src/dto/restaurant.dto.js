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

/**
 * Restaurant Detailed DTO
 * Extended Data Transfer Object structure for Restaurants with additional details
 * Used by createRestaurant and updateRestaurant services
 */
export class RestaurantDetailedDTO {
    constructor({
        id = null,
        name = '',
        owner = '',
        location = '',
        cuisineType = '',
        rating = 0.0,
        image = '',
        menuImages = [],
        foodImages = []
    } = {}) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.location = location;
        this.cuisineType = cuisineType;
        this.rating = rating;
        this.image = image;
        this.menuImages = menuImages;
        this.foodImages = foodImages;
    }

    /**
     * Creates a RestaurantDetailedDTO instance from API response data
     * @param {Object} data - Restaurant detailed data from API
     * @returns {RestaurantDetailedDTO} A new RestaurantDetailedDTO instance
     */
    static fromAPI(data) {
        return new RestaurantDetailedDTO({
            id: data.id,
            name: data.name,
            owner: data.owner,
            location: data.location,
            cuisineType: data.cuisineType,
            rating: data.rating,
            image: data.image,
            menuImages: data.menuImages || [],
            foodImages: data.foodImages || []
        });
    }

    /**
     * Converts the DTO to a format suitable for API requests
     * @param {boolean} includeId - Whether to include the ID in the request (true for updates, false for creation)
     * @returns {Object} Object formatted for API requests
     */
    toAPIRequest(includeId = false) {
        const requestData = {
            name: this.name,
            owner: this.owner,
            location: this.location,
            cuisineType: this.cuisineType,
            rating: this.rating,
            image: this.image,
            menuImages: this.menuImages,
            foodImages: this.foodImages
        };
        
        // Only include ID for updates, not for creation
        if (includeId && this.id) {
            requestData.id = this.id;
        }
        
        return requestData;
    }
}

export default RestaurantDTO;
