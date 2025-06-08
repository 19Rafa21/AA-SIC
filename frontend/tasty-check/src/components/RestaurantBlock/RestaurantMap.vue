<template>
  <div class="restaurant-map">
    <l-map
      ref="map"
      @ready="onMapReady"
      :zoom="zoom"
      :center="currentCenter"
      style="height: 100%; width: 100%"
    >
      <l-tile-layer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"/>
      <l-marker
        v-if="showUser"
        :lat-lng="currentCenter"
        :icon="userIcon"
      />
      <l-marker
        v-for="rest in restaurants"
        :key="rest.id"
        :lat-lng="[rest.lat, rest.lng]"
      >
        <l-popup>
          <strong>{{ rest.name }}</strong><br/>
          {{ rest.location }}
        </l-popup>
      </l-marker>
    </l-map>
  </div>
</template>

<script>
import {
  LMap,
  LTileLayer,
  LMarker,
  LPopup
} from '@vue-leaflet/vue-leaflet'
import { LatLngBounds, Icon } from 'leaflet'

export default {
  name: 'RestaurantMap',
  components: { LMap, LTileLayer, LMarker, LPopup },
  props: {
    restaurants: { type: Array, required: true },
    center: {
      type: Array,
      default: () => [0, 0]
    }
  },
  data() {
    return {
      zoom: 13,
      currentCenter: [0, 0],
      userIcon: new Icon({
        iconUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41]
      }),
      mapObject: null
    }
  },
  computed: {
    showUser() {
      // só mostra se centre estiver definido diferente de [0,0]
      return this.currentCenter[0] !== 0 || this.currentCenter[1] !== 0
    }
  },
  mounted() {
    // primeiro define o centro no utilizador
    this.currentCenter = this.center
    this.$nextTick(this.fitBoundsToRestaurants)
  },
  watch: {
    // se mudarem os restaurantes ou o centro, refaz o fitBounds
    restaurants() { this.$nextTick(this.fitBoundsToRestaurants) },
    center(val) { this.currentCenter = val }
  },
  methods: {
    onMapReady(mapInstance) {
     this.mapObject = this.$refs.map.leafletObject
     this.fitBoundsToRestaurants()
   },

    fitBoundsToRestaurants() {
      console.log('Ajustando limites do mapa...')
      if (!this.restaurants.length) return

      const coords = this.restaurants.map(r => [r.lat, r.lng])
      if (this.showUser) coords.push(this.currentCenter)
      const bounds = new LatLngBounds(coords)
      console.log('Limites do mapa:', bounds)
      
      this.mapObject.fitBounds(bounds, { padding: [50, 50] })
    }
  }
}
</script>

<style scoped>
.restaurant-map {
  width: 100%;
  height: 600px;
  border-radius: 8px;
}
</style>
