import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "./App.vue";
import router from "./router";

// Tailwind CSS import
import "tailwindcss/tailwind.css";

import "./assets/css/nucleo-icons.css";
import "./assets/css/nucleo-svg.css";
import '@fortawesome/fontawesome-free/css/all.css'

import materialKit from "./material-kit";

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(materialKit);
app.mount("#app");
