<template>
  <teleport to="body">
    <div
      v-if="modelValue"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
    >
      <div class="bg-white rounded-lg p-6 relative">
        <!-- Botão fechar -->
        <button
          @click="$emit('update:modelValue', false)"
          class="absolute top-2 right-2 text-gray-500 hover:text-gray-700"
        >
          X
        </button>
        <!-- Conteúdo do modal -->
        <div class="text-center text-lg font-bold">
          HELLO
        </div>
      </div>
    </div>
  </teleport>
</template>

<script>
export default {
  name: 'ReviewModal',
  props: {
    modelValue: {
      type: Boolean,
      required: true
    }
  }
}
</script>

<style scoped>
/* Podes adicionar estilos adicionais aqui */
</style>