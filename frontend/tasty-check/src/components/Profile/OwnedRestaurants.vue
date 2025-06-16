<script setup>
import { ref, onMounted } from 'vue'
import EditRestaurantModal from './EditRestaurantModal.vue'
import UserService from '@/services/user.service'
import { useAuthStore } from '@/stores/auth' 
import RestaurantService from '@/services/restaurant.service'

const restaurantService = new RestaurantService()


const restaurantes = ref([])
const restauranteSelecionado = ref(null)

const userService = new UserService()

import ImageService from '@/services/image.service'

const imageService = new ImageService()

onMounted(async () => {
  try {
    const authStore = useAuthStore()
    const userId = authStore.currentUser?.id

    if (!userId) throw new Error('Utilizador não autenticado')

    const resposta = await userService.getOwnedRestaurants(userId)

    const comImagens = await Promise.all(
      (resposta || []).map(async (rest) => {
        try {
          const blob = await imageService.getImage(rest.image)
          rest.imageUrl = URL.createObjectURL(blob)
        } catch (e) {
          rest.imageUrl = '/img/placeholder-restaurant.png'
        }
        return rest
      })
    )

    restaurantes.value = comImagens
  } catch (e) {
    console.error('Erro ao carregar restaurantes do utilizador:', e)
  }
})

const abrirModal = async (restaurante) => {
  try {
    const restaurantService = new RestaurantService()
    const detalhado = await restaurantService.getRestaurantById(restaurante.id)
    restauranteSelecionado.value = detalhado
  } catch (e) {
    console.error('Erro ao obter dados detalhados:', e)
  }
}

const guardarEdicao = (restauranteEditado) => {
  const i = restaurantes.value.findIndex(r => r.id === restauranteEditado.id)
  if (i !== -1) {
    restaurantes.value[i] = restauranteEditado // substitui o restaurante antigo pelo novo
  } else {
    restaurantes.value.push(restauranteEditado) // fallback no caso de não encontrar
  }
}

const apagarRestaurante = async (id) => {
  const confirmar = confirm('Tens a certeza que queres apagar este restaurante?')
  if (!confirmar) return

  try {
    await restaurantService.deleteRestaurant(id)
    restaurantes.value = restaurantes.value.filter(r => r.id !== id)
  } catch (error) {
    console.error('Erro ao apagar restaurante no backend:', error)
    alert('Erro ao apagar restaurante. Tenta novamente.')
  }
}

const handleRefreshOwned = () => {
  loadProfile() // recarrega o perfil completo
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

    <!-- Grid ou mensagem quando vazio -->
    <div class="grid grid-cols-2 gap-4 min-h-[100px]">
      <template v-if="restaurantes.length > 0">
        <div
          v-for="restaurante in restaurantes"
          :key="restaurante.id"
          class="relative group"
        >
          <!-- Link ao restaurante -->
          <router-link
            :to="`/restaurant/${restaurante.id}`"
            class="block"
          >
            <img
              :src="restaurante.imageUrl"
              alt="Imagem restaurante"
              class="w-full h-32 object-cover rounded-md mb-2"
            />
            <div class="text-xs text-gray-500">{{ restaurante.cuisineType }}</div>
            <div class="font-semibold text-sm">{{ restaurante.name }}</div>
          </router-link>

          <!-- Botões fora do router-link, mas posicionados por cima -->
          <div class="absolute bottom-0 right-0 mb-1 mr-1 flex gap-2">
            <button
              @click.stop="apagarRestaurante(restaurante.id)"
              class="px-2 py-1 text-sm border border-red-400 text-red-600 rounded flex items-center gap-1 bg-white hover:bg-red-100"
            >
              <i class="fas fa-trash-alt text-xs"></i>
            </button>

            <button
              @click.stop="abrirModal(restaurante)"
              class="px-2 py-1 text-sm border border-gray-400 rounded flex items-center gap-1 bg-white hover:bg-gray-100"
            >
              <i class="fas fa-pen text-xs"></i> Editar
            </button>
          </div>
        </div>
      </template>


      <template v-else>
        <div class="col-span-2 text-center text-gray-600 py-6">
          <i class="fas fa-utensils text-2xl mb-2 text-gray-400"></i>
          <p>Ainda não tens nenhum restaurante criado.</p>
          <p class="text-sm mt-1">Clica em <strong>"Adicionar"</strong> acima para começares!</p>
        </div>
      </template>
    </div>

    <EditRestaurantModal
      v-if="restauranteSelecionado"
      :restaurante="restauranteSelecionado"
      @close="restauranteSelecionado = null"
      @save="guardarEdicao"
    />
  </div>
</template>
