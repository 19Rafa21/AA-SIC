<script setup>
import { ref, onMounted } from 'vue'
import UserService from '@/services/user.service'
import ImageService from '@/services/image.service'
import Spinner from '../utils/Spinner.vue'

const userService = new UserService()
const imageService = new ImageService()

const favoritos = ref([])
const loading = ref(true)
const erro = ref(null)

const carregarFavoritos = async () => {
  const local = JSON.parse(localStorage.getItem('user') || '{}')
  const userId = local.id

  if (!userId) {
    erro.value = 'Utilizador não autenticado.'
    loading.value = false
    return
  }

  try {
    const data = await userService.getFavorites(userId)

    const favoritosComImagens = await Promise.all(
      (data || []).map(async (rest) => {
        try {
          const blob = await imageService.getImage(rest.image)
          rest.imageUrl = URL.createObjectURL(blob)
        } catch (e) {
          rest.imageUrl = '/img/placeholder-restaurant.png'
        }
        return rest
      })
    )

    favoritos.value = favoritosComImagens
  } catch (e) {
    erro.value = 'Erro ao carregar favoritos.'
  } finally {
    loading.value = false
  }
}

onMounted(carregarFavoritos)
</script>

<template>
  <div class="mt-5 bg-gray-100 p-4 rounded-xl">
    <h2 class="text-2xl font-bold mb-4">Restaurantes Favoritos</h2>

    <!-- Loading -->
    <div v-if="loading" class="py-4 text-center">
      <Spinner class="mx-auto mt-4 mb-1 text-lg" />
      <span class="text-sm text-emerald-500">A carregar favoritos...</span>
    </div>

    <!-- Erro -->
    <div v-else-if="erro" class="text-center py-4 text-sm text-red-500">
      {{ erro }}
    </div>

    <!-- Lista ou mensagem de vazio -->
    <div v-else class="grid grid-cols-2 gap-4 min-h-[100px]">
      <!-- Mensagem quando não há favoritos -->
      <template v-if="favoritos.length === 0">
        <div class="col-span-2 text-center text-gray-600 py-6">
          <i class="fas fa-heart-broken text-2xl mb-2 text-gray-400"></i>
          <p>Ainda não adicionaste nenhum restaurante aos favoritos.</p>
          <p class="text-sm mt-1">Vai a um restaurante e clica no ❤️ para adicioná-lo aqui!</p>
        </div>
      </template>

      <!-- Favoritos -->
      <template v-else>
        <router-link
          v-for="rest in favoritos"
          :key="rest.id"
          :to="`/restaurant/${rest.id}`"
          class="w-full block"
        >
          <img
            :src="rest.imageUrl"
            alt="Imagem restaurante"
            class="w-full h-28 object-cover rounded-md mb-2"
          />
          <div class="text-xs text-gray-500">{{ rest.cuisineType }}</div>
          <div class="font-semibold">{{ rest.name }}</div>
          <div class="text-sm text-gray-500">{{ rest.rating.toFixed(1) }} ★</div>
        </router-link>
      </template>
    </div>
  </div>
</template>
