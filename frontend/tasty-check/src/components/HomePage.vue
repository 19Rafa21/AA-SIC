<script setup>

import { onMounted, onUnmounted, ref, watch } from "vue";
import HeaderComponent from './MainPage/HeaderComponent.vue';
import TopNavbar from './MainPage/TopNavbar.vue';
import SearchBar from './MainPage/SearchBar.vue';
import PresentationCounter from "./MainPage/PresentationCounter.vue";
import RestaurantCarousel from "./RestaurantBlock/RestauranteCarousel.vue";
import RestaurantMap from "./RestaurantBlock/RestaurantMap.vue";
import restaurantesData from '../dataTesting/restaurantes.json';

const headerBgImage = '/img/header-bg.webp';
const restaurants = ref(restaurantesData.slice(0, 5)); // Taking the first 5 restaurants
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
  const promises = restaurantesData.slice(0, 5).map(async r => {
    const coords = await geocodeAddress(r.location)
    return coords
      ? { ...r, lat: coords.lat, lng: coords.lng }
      : null
  })
  const results = await Promise.all(promises)
  // filtra eventuais nulos
  restaurantsWithCoords.value = results.filter(r => r)
}

onMounted(async () => {
  document.body.classList.add('presentation-page', 'bg-gray-200')
  loadUserLocation()
  await loadRestaurantsCoords()
})
onUnmounted(() => {
  body.classList.remove("presentation-page");
  body.classList.remove("bg-gray-200");
});
</script>

<template>
  <TopNavbar/>
  <HeaderComponent>
    <div class="page-header min-vh-100"
         :style="`background-image: url(${headerBgImage})`"
         loading="lazy">

        <div class="container h-100 flex flex-col justify-center items-center">
            <div class="hero-brand flex items-center justify-center">
                <h1 class="text-3xl font-bold flex items-center">
                <span class="text-white">Tasty</span>
                <span class="flex items-center">
                    <span class="text-[#095243]">Check</span>
                    <img src="../../public/img/logo.png" alt="Logo" class="w-[100px] h-[100px] hero-logo ms-2" />
                </span>
                </h1>
            </div>

            <p class="lead text-white px-5 mt-3" :style="{ fontWeight: '500' }">
                Sabores incríveis, momentos inesquecíveis.
            </p>

            <SearchBar class="mt-4"/>
        </div>
    </div>
  </HeaderComponent>
  <PresentationCounter />
  
  <RestaurantCarousel :title="'Melhores Sugestões'" :restaurants="restaurants" :visibleCount="4"/>
 
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
    <div class="h-[190px] w-auto mt-2 mb-2 overflow-hidden rounded-lg">
      <img
        src="../../public/img/top100.png"
        alt="Prato delicioso"
        class="h-full w-full object-cover object-center"
      />
    </div>
  </div>

  <!-- MAPA -->
  <div v-if="showMapModal"
       class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 overflow-auto">
   <div class="bg-white rounded-lg overflow-hidden w-[90vw] max-w-5xl">
     <div class="flex justify-between items-center px-6 py-3 border-b">
       <h3 class="text-xl font-semibold">Mapa de Restaurantes</h3>
       <button
         @click="showMapModal = false"
         class="text-gray-600 hover:text-gray-800 text-2xl leading-none"
       >
         &times;
       </button>
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


  

</template>

<style scoped>
/* Mantendo o estilo existente para compatibilidade, mas usando primariamente Tailwind nas classes */

.container {
  height: 100%;
}

/* Replicando exatamente o estilo do PresentationView original */
.min-h-screen {
  min-height: 100vh;
}
</style>