<script setup>
import { ref } from 'vue'
import EditRestaurantModal from './EditRestaurantModal.vue'

// Lista de restaurantes do proprietário
const restaurantes = ref([
  {
    nome: 'Yakisake',
    tipo: 'JAPANESE',
    imagem: '/img/top100.jpg'
  }
])

// Restaurante atualmente selecionado para edição
const restauranteSelecionado = ref(null)

// Abre o modal com restaurante existente
const abrirModal = (restaurante) => {
  restauranteSelecionado.value = { ...restaurante }
}

// Abre o modal para adicionar novo
const adicionarNovo = () => {
  restauranteSelecionado.value = {
    nome: '',
    tipo: '',
    imagem: ''
  }
}

// Guardar alterações ou novo restaurante
const guardarEdicao = (restauranteEditado) => {
  const i = restaurantes.value.findIndex(r => r.nome === restauranteEditado.nome)
  if (i !== -1) {
    restaurantes.value[i] = restauranteEditado // editar existente
  } else {
    restaurantes.value.push(restauranteEditado) // novo restaurante
  }
}
</script>

<template>
  <div class="mt-5 bg-gray-100 p-4 rounded-xl">
    <div class="flex justify-between items-center mb-4">
      <h2 class="text-2xl font-bold">Os Meus Restaurantes</h2>
      <button
        @click="adicionarNovo"
        class="px-2 py-1 border border-gray-400 rounded flex items-center gap-1 bg-white hover:bg-gray-100 text-sm"
      >
        <i class="fas fa-plus text-xs"></i> Adicionar
      </button>
    </div>

   <div class="grid grid-cols-2 gap-4">
    <div v-for="restaurante in restaurantes" :key="restaurante.nome" class="relative">
        <img
        :src="restaurante.imagem"
        class="w-full h-32 object-cover rounded-md mb-2"
        />
        <div class="text-xs text-gray-500">{{ restaurante.tipo }}</div>
        <div class="font-semibold text-sm">{{ restaurante.nome }}</div>

        <!-- Botão editar -->
        <button
        class="absolute bottom-0 right-0 mb-1 mr-1 px-2 py-1 text-sm border border-gray-400 rounded flex items-center gap-1 bg-white hover:bg-gray-100"
        @click="abrirModal(restaurante)"
        >
        <i class="fas fa-pen text-xs"></i> Editar
        </button>
    </div>
    </div>


    <!-- Modal reutilizado -->
    <EditRestaurantModal
      v-if="restauranteSelecionado"
      :restaurante="restauranteSelecionado"
      @close="restauranteSelecionado = null"
      @save="guardarEdicao"
    />
  </div>
</template>
