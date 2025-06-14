<script setup>

import { onMounted, onUnmounted, ref, watch, computed } from "vue";
import HeaderComponent from './MainPage/HeaderComponent.vue';
import Avatar from './MainPage/Avatar.vue';
import SearchBar from './MainPage/SearchBar.vue';
import PresentationCounter from "./MainPage/PresentationCounter.vue";
import RestaurantCarousel from "./RestaurantBlock/RestauranteCarousel.vue";
import RestaurantMap from "./Maps/RestaurantMap.vue";
import Footer from "./Footer.vue";
import Spinner  from './utils/Spinner.vue';
import { RestaurantService } from '../services';

const restaurantService = new RestaurantService();
const headerBgImage = '/img/header-bg.webp';

const restaurants = ref([]);
const NearbyRestaurants = ref([]);
const allRestaurants = ref([]);
const loadingRestaurants = ref(true);
const loadingSuggested = ref(true);

// Carregar restaurantes com rating >= 4.0
async function loadFilteredRestaurants() {
  try {
    const data = await restaurantService.getRestaurantsWithFilter(null, null, null, 0.0);
    restaurants.value = data;
  } catch (error) {
    console.error('Error loading filtered restaurants:', error);
  }
  finally{
    loadingRestaurants.value = false;
  }
}

async function loadSuggestedRestaurants() {
  try {
    const data = await restaurantService.getRestaurantsWithFilter(null, "Braga", null, 0.0);
    NearbyRestaurants.value = data;
  } catch (error) {
    console.error('Error loading Suggested restaurants:', error);
  }
  finally{
    loadingSuggested.value = false;
  }
}

async function getAllRestaurants() {
  try {
    const data = await restaurantService.getAllRestaurants();
    allRestaurants.value = data;
  } catch (error) {
    console.error('Error loading restaurants:', error);
  }
}

const body = document.getElementsByTagName("body")[0];

const showMapModal = ref(false)

watch(showMapModal, open => {
  document.body.classList.toggle('overflow-hidden', open);
});

// reactivos
const userLocation = ref({ lat: 0, lng: 0 })
const restaurantsWithCoords = ref([])

// 1) Geolocalizar utilizador
function loadUserLocation() {
  if (!navigator.geolocation) return
  navigator.geolocation.getCurrentPosition(
    pos => {
      userLocation.value = {
        lat: pos.coords.latitude,
        lng: pos.coords.longitude
      }
    },
    err => console.error('Erro geolocalização:', err)
  )
}

// 2) Função para geocodificar um endereço com Nominatim
async function geocodeAddress(address) {
  const url = new URL('https://nominatim.openstreetmap.org/search')
  url.searchParams.set('q', address)
  url.searchParams.set('format', 'json')
  url.searchParams.set('limit', '1')
  const res = await fetch(url.toString(), {
    headers: { 'User-Agent': 'tasty-check/1.0 (email@exemplo.com)' }
  })
  const data = await res.json()
  if (data.length === 0) return null
  return {
    lat: parseFloat(data[0].lat),
    lng: parseFloat(data[0].lon)
  }
}

// 3) Geocodificar todos os restaurantes
async function loadRestaurantsCoords() {
  console.log('allRestaurants:', allRestaurants.value)
  const promises = allRestaurants.value.map(async r => {
    const coords = await geocodeAddress(r.location)
    return coords
      ? { 
          ...r, 
          lat: coords.lat, 
          lng: coords.lng,
          cuisineType: r.cuisineType 
        }
      : null
  })
  const results = await Promise.all(promises)
  // filtra eventuais nulos
  restaurantsWithCoords.value = results.filter(r => r)
}

onMounted(async () => {
  document.body.classList.add('presentation-page', 'bg-gray-200')
  loadUserLocation()
  await loadFilteredRestaurants() // Carrega restaurantes com rating 4.0
  await loadSuggestedRestaurants() // Carrega restaurantes sugeridos
  await getAllRestaurants() // Carrega todos os restaurantes
  await loadRestaurantsCoords()
})
onUnmounted(() => {
  body.classList.remove("presentation-page");
  body.classList.remove("bg-gray-200");
});
</script>

<template>
  <Avatar/>
  <HeaderComponent>
    <div class="page-header min-vh-100"
         :style="`background-image: url(${headerBgImage})`"
         loading="lazy">

        <div class="container h-100 flex flex-col justify-center items-center bg-gradient-to-b from-white/40 to-white/10 backdrop-blur-sm z-0 w-40 rounded-lg">
          <div class="flex flex-col items-center ">
            
            <div class="hero-brand flex items-center justify-center">
                  <h1 class="text-3xl font-bold flex items-center">
                  <span class="text-white">Tasty</span>
                  <span class="flex items-center">
                      <span class="text-[#095243]">Check</span>
                      <img src="../../public/img/logo.png" alt="Logo" class="w-[100px] h-[100px] hero-logo ms-2" />
                  </span>
                  </h1>
              </div>

              <div class="lead text-white px-5 -mt-3 mb-5" :style="{ fontWeight: '800' }">
                  Sabores incríveis, momentos inesquecíveis.
              </div>
          </div>

            <SearchBar class="mt-4 mb-4"/>
          </div>
    </div>
  </HeaderComponent>
  <PresentationCounter />
  
  <div v-if="loadingRestaurants === true" class="text-center py-8">
    <Spinner class="mx-auto mt-4 mb-1"/>    
    <span class="text-emerald-500 text-lg">Loading...</span>
  </div>

  <RestaurantCarousel v-else-if="restaurants.length !== 0 " :title="'Melhores Sugestões'" :restaurants="restaurants" :visibleCount="4"/>
  <div v-else class="flex justify-center items-center">
    <span class="text-center text-lg text-gray-500 py-8">
      Nenhum restaurante encontrado :/
    </span>
  </div>

  <div class="relative bg-[#08342c] rounded-lg w-full max-w-[1200px] mx-auto my-8 flex justify-between items-center text-white h-[220px] overflow-hidden px-4">
    <div class="flex flex-col justify-center pr-6 space-y-2 max-w-md">
      <h2 class="text-3xl font-normal text-white leading-tight">
        Descubra os 100 melhores<br/>
        perto da sua localização
      </h2>
      <button
        @click="showMapModal = true"
        class="flex items-start text-white hover:text-slate-500 font-medium text-sm py-1.5 rounded transition-colors duration-200"
      >
        VEJA A LISTA COMPLETA &rsaquo;
      </button>
    </div>
    <div class="h-[190px] max-w-[50%] mt-2 mb-2 overflow-hidden rounded-lg">
      <img
        src="../../public/img/top100.png"
        alt="Prato delicioso"
        class="h-full w-full object-cover object-center"
      />
    </div>
  </div>

  <!-- MAPA -->
  <div v-if="showMapModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 overflow-auto">
    <div class="bg-white rounded-lg w-[90vw] max-w-5xl">
      <div class="flex justify-between items-center px-6 py-3 border-b">
        <h3 class="text-xl font-semibold">Mapa de Restaurantes</h3>
        <button @click="showMapModal = false" class="text-gray-600 hover:text-gray-800 text-2xl">×</button>
      </div>
      <div class="p-4 h-[75vh]">
        <RestaurantMap
          :center="[userLocation.lat, userLocation.lng]"
          :restaurants="restaurantsWithCoords"
          class="h-full w-full"
        />
      </div>
    </div>
  </div>


  <div v-if="loadingSuggested === true" class="text-center py-8">
    <Spinner class="mx-auto mt-4 mb-1"/>    
    <span class="text-emerald-500 text-lg">Loading...</span>
  </div>

  <RestaurantCarousel v-else-if="NearbyRestaurants.length !== 0 " :title="'Perto de si'" :restaurants="NearbyRestaurants" :visibleCount="4"/>
  <div v-else class="flex justify-center items-center">
    <span class="text-center text-lg text-gray-500 py-8">
      Nenhum restaurante encontrado perto de si, experimente mudar de casa
    </span>
  </div>

 <Footer />

</template>

<style scoped>
/* Mantendo o estilo existente para compatibilidade, mas usando primariamente Tailwind nas classes */

html, body {
  overflow-x: hidden;
  max-width: 100%;
}

.container {
  height: 100%;
}

/* Replicando exatamente o estilo do PresentationView original */
.min-h-screen {
  min-height: 100vh;
}
</style>