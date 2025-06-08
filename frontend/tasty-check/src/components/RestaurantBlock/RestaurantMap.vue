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
        :icon="getRestaurantIcon(rest.rating)"
      >
        <l-popup>
          <strong>{{ rest.name }}</strong><br/>
          <div class="rating">
            <span class="stars">{{ getRatingStars(rest.rating) }}</span>
            <span class="rating-text">{{ rest.rating.toFixed(1) }}</span>
          </div>
          <div>{{ rest.cuisineType || rest.category }}</div>
          <div>{{ rest.location }}</div>
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
import { LatLngBounds, Icon, DivIcon } from 'leaflet'

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
        iconUrl: '/img/user-marker.png', // Você precisará adicionar esta imagem ou usar URL externa
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        className: 'user-marker' // Adicionamos uma classe para estilizar via CSS
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
    },

    // Retorna um ícone do Leaflet baseado no rating do restaurante
    getRestaurantIcon(rating) {
      // Determinar a cor com base no rating
      let color;
      if (rating >= 4.5) color = '#1E8449'; // Verde escuro para excelente
      else if (rating >= 4.0) color = '#58D68D'; // Verde para muito bom
      else if (rating >= 3.5) color = '#F4D03F'; // Amarelo para bom
      else if (rating >= 3.0) color = '#F39C12'; // Laranja para médio
      else color = '#E74C3C'; // Vermelho para baixo
      
      // Criar HTML para o ícone personalizado
      const markerHtml = `
        <div class="custom-marker" style="background-color: ${color};">
          <span>${rating.toFixed(1)}</span>
        </div>
      `;
      
      return new DivIcon({
        html: markerHtml,
        className: 'restaurant-marker-icon',
        iconSize: [32, 32],
        iconAnchor: [16, 32]
      });
    },
    
    // Converte rating numérico em estrelas visuais
    getRatingStars(rating) {
      const fullStars = Math.floor(rating);
      const halfStar = rating % 1 >= 0.5;
      
      let stars = '★'.repeat(fullStars);
      if (halfStar) stars += '½';
      
      return stars;
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

/* Estilizando o ícone do usuário */
:deep(.user-marker) {
  filter: hue-rotate(330deg) brightness(1.2) !important; /* Isso dará um tom vermelho ao ícone azul padrão */
}

/* Estilos para o ícone personalizado de restaurante */
:deep(.restaurant-marker-icon) {
  background: transparent;
  border: none;
}

:deep(.custom-marker) {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  color: white;
  font-weight: bold;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
  border: 2px solid white;
  font-size: 12px;
}

/* Estilos para o popup */
:deep(.leaflet-popup-content) {
  min-width: 180px;
  padding: 5px;
}

:deep(.leaflet-popup-content .rating) {
  display: flex;
  align-items: center;
  margin: 6px 0;
}

:deep(.leaflet-popup-content .stars) {
  color: #f39c12;
  margin-right: 6px;
}

:deep(.leaflet-popup-content .rating-text) {
  font-weight: 600;
}
</style>
