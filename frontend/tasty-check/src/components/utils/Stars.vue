<template>
  <div class="flex items-center">
    <!-- Estrelas -->
    <template v-for="(star, index) in stars" :key="index">
      <i :class="star" class="text-yellow-500 mr-1 text-2xl"></i>
    </template>
    <!-- Valor e etiqueta -->
    <span class="ml-2 font-semibold text-black text-xl" >
      {{ rating.toFixed(1) }} {{ label }}
    </span>
  </div>
</template>

<script>
export default {
  name: 'StarRating',
  props: {
    rating: {
      type: Number,
      required: true
    }
  },
  computed: {
    stars() {
      const full   = Math.floor(this.rating);
      const frac   = this.rating - full;
      const half   = frac >= 0.5 && frac < 1 ? 1 : 0;
      const extra  = frac >= 0.75 ? 1 : 0;
      const totalFull = full + extra;
      const arr = [];
      // Cheias
      for (let i = 0; i < totalFull; i++) {
        arr.push('fas fa-star');
      }
      // Meia
      if (half && extra === 0) {
        arr.push('fas fa-star-half-alt');
      }
      // Vazias até perfazer 5
      while (arr.length < 5) {
        arr.push('far fa-star');
      }
      return arr;
    },
    label() {
      if (this.rating >= 4) return 'Excelente';
      if (this.rating >= 3) return 'Bom';
      if (this.rating >= 2) return 'Médio';
      return 'Fraco';
    }
  }
};
</script>

<style scoped>
/* Remove margem da última estrela */
i:last-child { margin-right: 0; }
</style>
