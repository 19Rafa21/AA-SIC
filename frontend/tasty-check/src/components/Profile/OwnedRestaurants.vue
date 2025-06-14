<script setup>
import { ref, onMounted } from 'vue'
import EditRestaurantModal from './EditRestaurantModal.vue'

const restaurantes = ref([])

onMounted(() => {
  const stored = localStorage.getItem('restaurants')
  if (stored) {
    try {
      restaurantes.value = JSON.parse(stored)
    } catch (e) {
      console.error('Erro ao ler do localStorage:', e)
    }
  }
})

const restauranteSelecionado = ref(null)

const abrirModal = (restaurante) => {
  restauranteSelecionado.value = { ...restaurante }
}

const guardarEdicao = (restauranteEditado) => {
  const i = restaurantes.value.findIndex(r => r.id === restauranteEditado.id)
  if (i !== -1) {
    restaurantes.value[i] = restauranteEditado
  } else {
    restaurantes.value.push(restauranteEditado)
  }
  localStorage.setItem('restaurants', JSON.stringify(restaurantes.value))
}

const apagarRestaurante = (id) => {
  const confirmar = confirm('Tens a certeza que queres apagar este restaurante?')
  if (!confirmar) return

  restaurantes.value = restaurantes.value.filter(r => r.id !== id)
  localStorage.setItem('restaurants', JSON.stringify(restaurantes.value))
}

</script>

<template>
  <div class="mt-5 bg-gray-100 p-4 rounded-xl">
    <div class="flex justify-between items-center mb-4">
      <h2 class="text-2xl font-bold">Os Meus Restaurantes</h2>
      <router-link
        to="/create-restaurant"
        class="px-2 py-1 border border-gray-400 rounded flex items-center gap-1 bg-white hover:bg-gray-100 text-sm"
      >
        <i class="fas fa-plus text-xs"></i> Adicionar
      </router-link>
    </div>

    <div class="grid grid-cols-2 gap-4">
      <div v-for="restaurante in restaurantes" :key="restaurante.name" class="relative">
        <img
          :src="restaurante.image"
          class="w-full h-32 object-cover rounded-md mb-2"
        />
        <div class="text-xs text-gray-500">{{ restaurante.cuisineType }}</div>
        <div class="font-semibold text-sm">{{ restaurante.name }}</div>

        <!-- Botões apagar e editar -->
        <div class="absolute bottom-0 right-0 mb-1 mr-1 flex gap-2">
          <button
            @click="apagarRestaurante(restaurante.id)"
            class="px-2 py-1 text-sm border border-red-400 text-red-600 rounded flex items-center gap-1 bg-white hover:bg-red-100"
          >
            <i class="fas fa-trash-alt text-xs"></i>
          </button>

          <button
            @click="abrirModal(restaurante)"
            class="px-2 py-1 text-sm border border-gray-400 rounded flex items-center gap-1 bg-white hover:bg-gray-100"
          >
            <i class="fas fa-pen text-xs"></i> Editar
          </button>
        </div>
      </div>
    </div>

    <EditRestaurantModal
      v-if="restauranteSelecionado"
      :restaurante="restauranteSelecionado"
      @close="restauranteSelecionado = null"
      @save="guardarEdicao"
    />
  </div>
</template>
