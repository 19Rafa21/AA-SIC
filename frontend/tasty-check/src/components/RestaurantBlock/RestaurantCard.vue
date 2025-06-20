<template>
  <div class="restaurant-card" @click="navigateToDetails">
    <img
      class="restaurant-card__image"
      :src="imageUrl"
      :alt="restaurant.name"
      @error="handleImageError"
    />
    <div class="restaurant-card__info">
      <span class="restaurant-card__category">{{ restaurant.category || restaurant.cuisineType }}</span>
      <h2 class="restaurant-card__name">{{ restaurant.name }}</h2>
      <div class="restaurant-card__meta">
        <span class="restaurant-card__rating">
          <span class="star">★</span> {{ restaurant.rating.toFixed(1) }}
        </span>
        <span class="restaurant-card__location">{{ restaurant.location }}</span>
      </div>
      <div class="restaurant-card__price" v-if="restaurant.averagePrice">
        Preço médio: {{ formatPrice(restaurant.averagePrice) }}
      </div>
    </div>
  </div>
</template>

<script>
import ImageService from '@/services/image.service.js'

export default {
  name: 'RestaurantCard',
  props: {
    restaurant: {
      type: Object,
      required: true,
      validator(obj) {
        return (
          (typeof obj.id === 'string' || typeof obj.id === 'number') &&
          typeof obj.name === 'string' &&
          (typeof obj.category === 'string' || typeof obj.cuisineType === 'string') &&
          typeof obj.rating === 'number' &&
          typeof obj.location === 'string' &&
          typeof obj.image === 'string'
        )
      }
    }
  },
  data() {
    return {
      imageUrl: '/img/placeholder-restaurant.png'
    }
  },
  async mounted() {
    if (this.restaurant.image) {
      try {
        const blob = await new ImageService().getImage(this.restaurant.image)
        if (blob) {
          this.imageUrl = URL.createObjectURL(blob)
        }
      } catch (e) {
        console.warn('Erro ao carregar imagem do restaurante:', e)
        this.imageUrl = '/img/placeholder-restaurant.png'
      }
    }
  },
  methods: {
    formatPrice(value) {
      return value + ' €'
    },
    navigateToDetails() {
      this.$router.push({
        name: 'RestaurantDetails',
        params: { id: this.restaurant.id },
        state: { restaurant: this.restaurant }
      })
    },
    handleImageError() {
      this.imageUrl = '/img/placeholder-restaurant.png'
    }
  }
}
</script>

<style scoped>
.restaurant-card {
  width: 100%;
  max-width: 280px;
  background: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  display: flex;
  flex-direction: column;
  cursor: pointer;
  transition: box-shadow 0.3s ease;
}

.restaurant-card:hover {
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
}

.restaurant-card__image {
  width: 100%;
  height: 160px;
  object-fit: cover;
  background-color: #f3f4f6;
}

.restaurant-card__info {
  padding: 16px;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.restaurant-card__category {
  font-size: 0.75rem;
  color: #757575;
  text-transform: uppercase;
}

.restaurant-card__name {
  font-size: 1.125rem;
  margin: 0;
  color: #212121;
}

.restaurant-card__meta {
  display: flex;
  align-items: center;
  gap: 8px;
}

.restaurant-card__rating .star {
  color: #ffc107;
  margin-right: 4px;
  flex-shrink: 0;
}

.restaurant-card__price {
  font-size: 0.875rem;
  color: #424242;
  margin-top: auto;
}

.restaurant-card__location {
  flex: 1;
  min-width: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
