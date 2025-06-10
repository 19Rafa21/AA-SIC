<template>
  <div>
    <!-- Só renderiza quando center tiver valor -->
    <GoogleMap
      v-if="center"
      :api-key="googleMapsApiKey"
      :center="center"
      :zoom="zoom"
      class=""
      style="width: 1000px; height: 1000px;"
      >
      <Marker :options="{ position: center }" />
    </GoogleMap>
  </div>
</template>

<script>
import { ref, onMounted, watch } from 'vue'
import { GoogleMap, Marker } from 'vue3-google-map'
import { Loader } from '@googlemaps/js-api-loader'

export default {
  name: 'RestaurantGoogleMap',
  components: { GoogleMap, Marker },
  props: {
    location: { type: String, required: true },
    zoom:     { type: Number, default: 15 }
  },
  setup(props) {
    const center = ref(null)
    // Lê a chave diretamente das env vars
    const googleMapsApiKey = import.meta.env.VITE_GOOGLE_MAPS_API_KEY

    async function geocodeAddress(address) {
      // Usa a variável correta no loader
      const loader = new Loader({
        apiKey: googleMapsApiKey,
        libraries: ['places'],
        version: 'weekly'
      })
      await loader.load()

      const geocoder = new google.maps.Geocoder()
      try {
        const { results } = await new Promise((resolve, reject) => {
          geocoder.geocode({ address }, (results, status) => {
            if (status === 'OK') resolve({ results, status })
            else reject(status)
          })
        })
        const loc = results[0].geometry.location
        center.value = { lat: loc.lat(), lng: loc.lng() }
      } catch (status) {
        console.error('Geocode falhou:', status)
      }
    }
    
    onMounted(() => {
      if (props.location) geocodeAddress(props.location)
    })

    watch(
      () => props.location,
      (newLoc) => {
        if (newLoc) geocodeAddress(newLoc)
      }
    )

    return { center, googleMapsApiKey }
  }
}
</script>

<style scoped>
/* podes adicionar estilos se necessário */
</style>
