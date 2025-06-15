import axios from 'axios';
import { API_CONFIG } from '../config/api.config.js';

class ImageService {
  constructor() {
    this.baseUrl = API_CONFIG.baseUrl;
    this.endpoint = 'image';
    this.axiosInstance = axios.create({
      baseURL: this.baseUrl,
      withCredentials: true,
    });
  }

  /**
   * Get an image by name
   * @param {string} imageName - Name of the image to retrieve
   * @returns {Promise<Blob>} Image blob that can be used with URL.createObjectURL
   */
  async getImage(imageName) {
    try {
      const response = await this.axiosInstance.get(`${this.endpoint}?imageName=${imageName}`, {
        responseType: 'blob', // Important: This tells axios to handle the response as a binary blob
      });
      return response.data;
    } catch (error) {
      console.error('Error fetching image:', error);
      throw error;
    }
  }

  /**
   * Upload an image with user data
   * @param {File} file - The image file to upload
   * @param {Object} userData - User data object
   * @param {string} userData.username - Username
   * @param {string} userData.email - User email
   * @param {string} userData.password - User password
   * @param {string} userData.discriminator - User discriminator
   * @returns {Promise<Object>} Response data
   */
  async uploadImage(file, userData) {
    try {
      // Create FormData object to send files and data
      const formData = new FormData();
      
      // Add user data as a JSON string to the "user" key
      formData.append('user', JSON.stringify(userData));
      
      // Add the file to the "file" key
      formData.append('file', file);

      // Configure the request with correct headers for form-data
      const response = await this.axiosInstance.post(this.endpoint, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          // Note: axios will automatically set the correct Content-Type with boundary
        }
      });
      
      return response.data;
    } catch (error) {
      console.error('Error uploading image:', error);
      throw error;
    }
  }
}

export default ImageService;
