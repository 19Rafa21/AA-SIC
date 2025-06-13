<script setup>
import { ref } from 'vue'

const props = defineProps({
  restaurante: Object
})

const emit = defineEmits(['close', 'save'])

const nome = ref(props.restaurante.nome)
const tipo = ref(props.restaurante.tipo)
const imagem = ref(props.restaurante.imagem)

const guardar = () => {
  emit('save', {
    ...props.restaurante,
    nome: nome.value,
    tipo: tipo.value,
    imagem: imagem.value
  })
  emit('close')
}

const cancelar = () => emit('close')
</script>

<template>
  <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
    <div class="bg-white rounded-lg p-6 w-full max-w-md shadow-lg">
      <h2 class="text-xl font-bold mb-4">Restaurante</h2>

      <div class="space-y-4">
        <div>
          <label class="block text-sm font-medium">Nome</label>
          <input v-model="nome" class="input-style" type="text" />
        </div>
        <div>
          <label class="block text-sm font-medium">Tipo de Cozinha</label>
          <input v-model="tipo" class="input-style" type="text" />
        </div>
        <div>
          <label class="block text-sm font-medium">URL da Imagem</label>
          <input v-model="imagem" class="input-style" type="text" />
        </div>
      </div>

      <div class="mt-6 flex justify-end gap-3">
        <button @click="cancelar" class="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400">Cancelar</button>
        <button @click="guardar" class="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700">Guardar</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.input-style {
  background-color: white;
  border: 1px solid #ccc;
  padding: 6px 10px;
  border-radius: 0.375rem;
  width: 100%;
  font-size: 0.875rem;
}
</style>
