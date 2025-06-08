<template>
  <div class="carousel-container">
    <!-- Header com título e nav agrupada -->
    <div class="carousel-header">
      <h2 class="carousel-title">{{ title }}</h2>
      <div class="carousel-nav-group">
        <button
          class="carousel__nav"
          @click="prev"
          :disabled="!canPrev"
        >
          ‹
        </button>
        <button
          class="carousel__nav"
          @click="next"
          :disabled="!canNext"
        >
          ›
        </button>
      </div>
    </div>

    <!-- Zona dos cards -->
    <div class="carousel">
      <div class="carousel__viewport">
        <div class="carousel__track">
          <RestaurantCard
            v-for="rest in visibleRestaurants"
            :key="rest.id"
            :restaurant="rest"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import RestaurantCard from './RestaurantCard.vue';

export default {
  name: 'RestaurantCarousel',
  components: { RestaurantCard },
  props: {
    title: {
      type: String,
      default: ''
    },
    restaurants: {
      type: Array,
      required: true,
      default: () => []
    },
    visibleCount: {
      type: Number,
      default: 4
    }
  },
  data() {
    return {
      currentStart: 0
    };
  },
  computed: {
    visibleRestaurants() {
      return this.restaurants.slice(
        this.currentStart,
        this.currentStart + this.visibleCount
      );
    },
    canPrev() {
      return this.currentStart > 0;
    },
    canNext() {
      return this.currentStart + this.visibleCount < this.restaurants.length;
    }
  },
  methods: {
    prev() {
      if (this.canPrev) {
        this.currentStart = Math.max(0, this.currentStart - this.visibleCount);
      }
    },
    next() {
      if (this.canNext) {
        this.currentStart += this.visibleCount;
      }
    }
  }
};
</script>

<style scoped>
.carousel-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.carousel-header {
  width: 100%;
  max-width: 1200px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.carousel-title {
  font-size: 1.5rem;
  margin: 0;
}

.carousel-nav-group {
  display: flex;
  gap: 8px;
}

.carousel__nav {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  padding: 4px 8px;
}

.carousel__nav:disabled {
  opacity: 0.3;
  cursor: default;
}

.carousel {
  display: flex;
  justify-content: center;
  width: 100%;
}

.carousel__viewport {
  overflow: hidden;
  flex: none;
  width: 100%;
  max-width: 1200px;
}

.carousel__track {
  display: flex;
  gap: 24px;
}
</style>
