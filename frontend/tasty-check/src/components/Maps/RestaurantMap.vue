<template>
  <GoogleMap
    ref="mapRef"
    :api-key="apiKey"
    :center="userPos"
    :zoom="zoom"
    class="w-full h-full rounded-lg"
    style="width:100%; height:100%;"
  >
    <!-- Agrupa marcadores em clusters -->
    <MarkerCluster>
      <!-- Marcador do utilizador: ícone padrão azul do Google Maps -->
      <Marker
        v-if="enabledState && userIconOptions"
        :options="{ position: userPos, icon: userIconOptions }"
      />

      <!-- Marcadores de restaurantes: círculo colorido com label de rating -->
      <Marker
        v-for="r in restaurants"
        :key="r.id"
        :options="{
          position: { lat: r.lat, lng: r.lng },
          icon: getIcon(r.rating),
          label: {
            text: r.rating.toFixed(1),
            color: '#fff',
            fontSize: '12px',
            fontWeight: 'bold'
          }
        }"
        @click="infoOpen = r.id"
      >
        <InfoWindow
          v-if="infoOpen === r.id"
          :options="{ position: { lat: r.lat, lng: r.lng } }"
          @closeclick="infoOpen = null"
        >
          <div class="p-2 text-sm">
            <img
              v-if="r.image"
              :src="r.image"
              alt="Imagem do restaurante"
              class="w-full h-24 object-cover rounded mb-2"
            />
            <strong>{{ r.name }}</strong><br/>
            ⭐ {{ r.rating.toFixed(1) }}<br/>
            {{ r.cuisineType || r.category }}<br/>
            {{ r.location }}
          </div>
        </InfoWindow>
      </Marker>
    </MarkerCluster>
  </GoogleMap>
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue'
import { GoogleMap, Marker, InfoWindow, MarkerCluster } from 'vue3-google-map'

const props = defineProps({
  center:      { type: Array, required: true },
  restaurants: { type: Array, required: true }
})

const apiKey       = import.meta.env.VITE_GOOGLE_MAPS_API_KEY
const mapRef       = ref(null)
const enabledState = ref(false)
const infoOpen     = ref(null)
const zoom         = ref(12)
const userPos      = ref({ lat: props.center[0], lng: props.center[1] })

const userIconOptions = computed(() => {
  if (!mapRef.value?.ready) return null
  const SymbolPath = mapRef.value.api.SymbolPath
  return {
    path: SymbolPath.CIRCLE,
    fillColor: '#4285F4',
    fillOpacity: 0.9,
    strokeColor: '#fff',
    strokeOpacity: 1,
    strokeWeight: 2,
    scale: 8
  }
})

onMounted(() => {
  watch(() => mapRef.value?.ready, (ready) => {
    if (ready) {
      enabledState.value = true
      fitBounds()
    }
  }, { immediate: true })
})

watch([
  () => props.restaurants,
  () => userPos.value
], () => {
  if (enabledState.value) fitBounds()
}, { deep: true })

function fitBounds() {
  const map = mapRef.value?.map
  if (!map) return
  const bounds = new mapRef.value.api.LatLngBounds()
  props.restaurants.forEach(r => bounds.extend({ lat: r.lat, lng: r.lng }))
  if (userPos.value.lat && userPos.value.lng) bounds.extend(userPos.value)
  map.fitBounds(bounds, { padding: 50 })
}

function getIcon(rating) {
  if (typeof window === 'undefined' ||
      !window.google ||
      !window.google.maps ||
      !window.google.maps.SymbolPath) {
    return {}
  }
  const symbolPath = window.google.maps.SymbolPath
  let color
  if (rating >= 4.5) color = '#1E8449'
  else if (rating >= 4.0) color = '#58D68D'
  else if (rating >= 3.5) color = '#F4D03F'
  else if (rating >= 3.0) color = '#F39C12'
  else color = '#E74C3C'
  return {
    path: symbolPath.CIRCLE,
    fillColor: color,
    fillOpacity: 1,
    strokeColor: '#fff',
    strokeWeight: 2,
    scale: 12
  }
}
</script>

<style scoped>
.vue3-google-map { width: 100%; height: 100%; }
</style>