<template>
  <div class="relative w-full max-w-md mx-auto" ref="searchRoot">
    <form @submit.prevent="searchRestaurants" class="relative">
      <input
        v-model="searchQuery"
        type="text"
        placeholder="Pesquisar restaurantes..."
        class="w-full px-4 py-1.5 rounded-full border text-sm focus:outline-none focus:ring-2 focus:ring-emerald-600 pr-10"
      />
      <button type="submit" class="absolute right-3 top-[50%] -translate-y-1/2 text-emerald-600 hover:text-emerald-800">
        <i v-if="!isLoading" class="fas fa-search"></i>
        <i v-else class="fas fa-spinner fa-spin"></i>
      </button>
    </form>

    <!-- Resultados dropdown -->
    <div
      v-if="showResults"
      class="absolute left-0 mt-2 w-full bg-white shadow-lg rounded-lg z-50 p-3 max-h-[220px] overflow-y-auto scrollbar-thin scrollbar-thumb-emerald-400 scrollbar-track-gray-100"
    >
      <div v-if="isLoading" class="text-center py-4">
        <i class="fas fa-spinner fa-spin text-emerald-500 text-xl"></i>
        <p class="text-gray-600 mt-2">A Carregar Restaurantes...</p>
      </div>
      <div v-else-if="searchResults.length === 0" class="text-center text-gray-500 py-3">
        Nenhum restaurante encontrado.
      </div>
      <div v-else>
        <ul class="space-y-2">
          <li
            v-for="r in searchResults"
            :key="r.id"
            class="flex items-center gap-3 cursor-pointer hover:bg-gray-100 p-2 rounded"
            @click="goToRestaurant(r.id)"
          >
            <img :src="r.image" alt="restaurante" class="w-10 h-10 rounded object-cover" />
            <div class="text-sm">
              <p class="text-gray-800 font-semibold">{{ r.name }}</p>
              <p class="text-gray-500">{{ r.location }}</p>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { useRouter } from 'vue-router'
import RestaurantService from '@/services/restaurant.service.js'

const searchQuery = ref('')
const searchResults = ref([])
const isLoading = ref(false)
const showResults = ref(false)
const searchRoot = ref(null)
const router = useRouter()

const knownLocations = ['braga', 'porto', 'lisboa']
const cuisineMap = {
  'italiana': 'Italian',
  'japonesa': 'Japanese',
  'portuguesa': 'Portuguese',
  'indiana': 'Indian',
  'mexicana': 'Mexican',
  'chinesa': 'Chinese',
  'francesa': 'French',
}

const searchRestaurants = async () => {
  const service = new RestaurantService()
  const input = searchQuery.value.toLowerCase().trim()

  let name = ''
  let location = ''
  let cuisineType = ''

  // Localização
  for (const loc of knownLocations) {
    if (input.includes(loc)) {
      location = loc.charAt(0).toUpperCase() + loc.slice(1)
      break
    }
  }

  // Tipo de cozinha
  for (const [pt, eng] of Object.entries(cuisineMap)) {
    if (input.includes(pt)) {
      cuisineType = eng
      break
    }
  }

  // O resto é nome (removendo o que já usámos)
  name = input
    .replace(location.toLowerCase(), '')
    .replace(Object.keys(cuisineMap).find(k => input.includes(k)) || '', '')
    .trim()

  isLoading.value = true
  showResults.value = true

  try {
    const results = await service.getRestaurantsWithFilter(name, location, cuisineType, 0)
    searchResults.value = results
  } catch (error) {
    console.error('Erro ao procurar restaurantes:', error)
    searchResults.value = []
  } finally {
    isLoading.value = false
  }
}

const goToRestaurant = (id) => {
  showResults.value = false
  router.push(`/restaurant/${id}`)
}

const handleClickOutside = (event) => {
  if (searchRoot.value && !searchRoot.value.contains(event.target)) {
    showResults.value = false
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
})

onBeforeUnmount(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>

<style scoped>
/* Scrollbar moderna via Tailwind plugin (scrollbar-thin etc.) */
</style>
