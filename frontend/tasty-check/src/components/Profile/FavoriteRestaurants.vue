<script setup>
import { ref, onMounted } from 'vue'

const favoritos = ref([])

onMounted(() => {
  // Exemplo com mock
  const dadosMock = [
    {
      nome: 'Yakisake',
      tipo: 'JAPANESE',
      imagem: '/img/top100.png',
      rating: 4.8
    },
    {
      nome: 'O Pescador',
      tipo: 'PORTUGUESE',
      imagem: '/img/avatar.jpg',
      rating: 4.5
    },
    {
      nome: 'O Italiano',
      tipo: 'ITALIAN',
      imagem: '/img/avatar.jpg',
      rating: 4.5
    },
    {
      nome: 'Sushi House',
      tipo: 'JAPANESE',
      imagem: '/img/top100.png',
      rating: 4.7
    },
    {
      nome: 'Pizza Planet',
      tipo: 'ITALIAN',
      imagem: '/img/top100.png',
      rating: 4.6
    }
  ]

  // Ou se já tiveres em localStorage:
  // const stored = localStorage.getItem('favoritos')
  // favoritos.value = stored ? JSON.parse(stored) : []

  favoritos.value = dadosMock
})
</script>

<template>
  <div class="mt-5 bg-gray-100 p-4 rounded-xl">
    <h2 class="text-2xl font-bold mb-4">Restaurantes Favoritos</h2>

    <div class="grid grid-cols-2 gap-4">
      <div
        v-for="rest in favoritos"
        :key="rest.nome"
        class="w-full"
      >
        <div class="mb-2">
          <img
            :src="rest.imagem"
            class="w-full h-28 object-cover rounded-md mb-2"
          />
        </div>
        <div class="text-xs text-gray-500">{{ rest.tipo }}</div>
        <div class="font-semibold">{{ rest.nome }}</div>
        <div class="text-sm text-gray-500">{{ rest.rating }} ★</div>
      </div>
    </div>
  </div>
</template>
