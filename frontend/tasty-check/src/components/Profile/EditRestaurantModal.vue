<script setup>
import { ref } from 'vue'

const props = defineProps({
  restaurante: Object
})

const emit = defineEmits(['close', 'save'])

// campos editáveis
const nome = ref(props.restaurante.name)
const localizacao = ref(props.restaurante.location)
const tipo = ref(props.restaurante.cuisineType)
const horario = ref(props.restaurante.schedule)
const imagem = ref(props.restaurante.image) // URL ou file
const imagemPreview = ref(props.restaurante.image) // para mostrar no <img>

const onImagemChange = (e) => {
  const file = e.target.files[0]
  if (file) {
    imagem.value = file
    imagemPreview.value = URL.createObjectURL(file)
  }
}

const guardar = () => {
  const restauranteEditado = {
    ...props.restaurante,
    id: props.restaurante.id, // mantém o id intacto
    name: nome.value,
    location: localizacao.value,
    cuisineType: tipo.value,
    schedule: horario.value,
    image: imagem.value
  }

  // ⚡ Atualiza no localStorage
  const stored = localStorage.getItem('restaurants')
  if (stored) {
    try {
      const lista = JSON.parse(stored)
      const index = lista.findIndex(r => r.name === restauranteEditado.name)
      if (index !== -1) {
        lista[index] = restauranteEditado
        localStorage.setItem('restaurants', JSON.stringify(lista))
      }
    } catch (e) {
      console.error('Erro ao guardar no localStorage:', e)
    }
  }

  emit('save', restauranteEditado)
  emit('close')
}


const cancelar = () => emit('close')
</script>

<template>
  <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
    <div class="bg-white rounded-lg p-6 w-full max-w-md shadow-lg">
      <h2 class="text-xl font-bold mb-4">Editar Restaurante</h2>

      <div class="space-y-4">
        <div>
          <label class="block text-sm font-medium">Nome</label>
          <input v-model="nome" class="input-style" type="text" />
        </div>

        <div>
          <label class="block text-sm font-medium">Localização</label>
          <input v-model="localizacao" class="input-style" type="text" />
        </div>

        <div>
          <label class="block text-sm font-medium">Tipo de Cozinha</label>
          <input v-model="tipo" class="input-style" type="text" />
        </div>

        <div>
          <label class="block text-sm font-medium">Horário</label>
          <input v-model="horario" class="input-style" type="text" />
        </div>

        <div>
          <label class="block text-sm font-medium">Imagem de Capa</label>
          <input type="file" accept="image/*" @change="onImagemChange" />
          <div v-if="imagemPreview" class="mt-2">
            <img :src="imagemPreview" class="w-full max-h-48 object-cover rounded" />
          </div>
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
