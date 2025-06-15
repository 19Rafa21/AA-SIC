/**
 * Dish DTO
 * Data Transfer Object structure for Dishes
 */

export class DishDTO {
    constructor({
        id = null,
        name = '',
        description = '',
        price = 0.0,
        image = '',
        restaurantId = null,
    } = {}) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.restaurantId = restaurantId;
    }

    /**
     * Creates a DishDTO instance from API response data
     * @param {Object} data - Dish data from API
     * @returns {DishDTO} A new DishDTO instance
     */
    static fromAPI(data) {
        return new DishDTO({
            id: data.id,
            name: data.name,
            description: data.description,
            price: data.price,
            image: data.image,
            restaurantId: data.restaurantId,
        });
    }

    /**
     * Converts the DTO to a format suitable for API requests
     * @returns {Object} Object formatted for API requests
     */
    toAPIRequest() {
        return {
            name: this.name,
            description: this.description,
            price: this.price,
            image: this.image,
            restaurantId: this.restaurantId
        };
    }
}

export default DishDTO;
