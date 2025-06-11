<template>
  <div class="flex items-center w-full">
    <!-- Barra de progresso que ocupa todo o espaço disponível -->
    <div class="relative flex-1 min-w-0 h-2 bg-gray-500 rounded overflow-hidden">
      <div
        class="h-full"
        :style="{ width: percent + '%', backgroundColor: barColor }"
      ></div>
    </div>
    <!-- Valor da frequência -->
    <span class="ml-2 text-sm text-gray-700">{{ frequency }}</span>
  </div>
</template>

<script>
export default {
  name: 'FrequencyBar',
  props: {
    frequency: {
      type: Number,
      required: true
    },
    total: {
      type: Number,
      required: true
    },
    color: {
      type: String,
      default: '#065f46'
    }
  },
  computed: {
    percent() {
      // Calcula percentagem e limita entre 0 e 100
      if (!this.total) return 0;
      const p = (this.frequency / this.total) * 100;
      return p > 100 ? 100 : p < 0 ? 0 : p;
    },
    barColor() {
      return this.color;
    }
  }
};
</script>

<style scoped>
/* Garante que o Flex-item pode encolher abaixo do tamanho de seu conteúdo */
.min-w-0 {
  min-width: 0;
}
</style>
