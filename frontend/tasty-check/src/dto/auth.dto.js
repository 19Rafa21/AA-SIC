/**
 * Authentication DTO
 * Data Transfer Object structure for Authentication
 */

export class UserDTO {
    constructor({
        email = '',
        username = '',
        password = '',
        discriminator = 'User'
    } = {}) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.discriminator = discriminator;
    }

    /**
     * Creates a UserDTO instance for registration
     * @param {string} email - User email
     * @param {string} username - Username
     * @param {string} password - User password
     * @param {string} discriminator - User type ('User' or 'Owner')
     * @returns {UserDTO} A new UserDTO instance
     */
    static forRegistration(email, username, password, discriminator = 'User') {
        return new UserDTO({
            email,
            username,
            password,
            discriminator
        });
    }

    /**
     * Creates a UserDTO instance for login
     * @param {string} email - User email
     * @param {string} password - User password
     * @returns {Object} Login credentials
     */
    static forLogin(email, password) {
        return {
            email,
            password
        };
    }

    /**
     * Creates a UserDTO instance from API response data
     * @param {Object} data - User data from API
     * @returns {UserDTO} A new UserDTO instance
     */
    static fromAPI(data) {
        return new UserDTO({
            email: data.email,
            username: data.username,
            discriminator: data.discriminator
        });
    }
}

export default UserDTO;
