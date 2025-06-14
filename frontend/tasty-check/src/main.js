import { createApp } from "vue";
import { createPinia } from "pinia";
import { GoogleMap, Marker } from 'vue3-google-map'
import App from "./App.vue";
import router from "./router";
import L from 'leaflet';
import proj4 from 'proj4';



window.L = L;
window.proj4 = proj4;

import "tailwindcss/tailwind.css";

import 'leaflet/dist/leaflet.css'
import "./assets/css/nucleo-icons.css";
import "./assets/css/nucleo-svg.css";
import '@fortawesome/fontawesome-free/css/all.css'

import materialKit from "./material-kit";

const app = createApp(App);

app.component('GoogleMap', GoogleMap)
app.component('Marker', Marker)
app.use(createPinia());
app.use(router);
app.use(materialKit);
app.mount("#app");
