<template>
  <TopNav />
  <div class="max-w-4xl mx-auto mt-24 bg-white border rounded-xl p-8 shadow">
    <h1 class="text-2xl font-bold mb-6 text-center mt-[-60px]">Criar Restaurante</h1>

    <form @submit.prevent="submitForm" class="grid grid-cols-1 md:grid-cols-2 gap-6">
      <!-- Nome -->
      <div class="md:col-span-2">
        <label class="block font-semibold mb-1">Nome do Restaurante</label>
        <input v-model="name" required class="input-style" />
      </div>

      <!-- Localização -->
      <div class="md:col-span-2">
        <label class="block font-semibold mb-1">Localização</label>
        <input v-model="location" required class="input-style" placeholder="Av. Exemplo 123, Porto" />
      </div>

      <!-- Tipo de Cozinha -->
      <div>
        <label class="block font-semibold mb-1">Tipo de Cozinha</label>
        <input v-model="cuisineType" required class="input-style" />
      </div>

      <!-- Horário -->
      <div>
        <label class="block font-semibold mb-1">Horário de Funcionamento</label>
        <input v-model="schedule" required class="input-style" />
      </div>

      <!-- Imagem de Capa -->
      <div class="md:col-span-2">
        <label class="block font-semibold mb-2">Imagem de Capa</label>
        <input type="file" accept="image/*" @change="onCoverImage" required />
        <div v-if="coverPreview" class="mt-2">
          <img :src="coverPreview" class="w-full max-h-64 object-cover rounded" />
        </div>
      </div>

      <!-- Fotos do Menu -->
      <div class="md:col-span-2">
        <label class="block font-semibold mb-2">Fotos do Menu</label>
        <input type="file" accept="image/*" multiple @change="onMenuImages" />
        <div class="flex flex-wrap gap-2 mt-2">
          <img v-for="(src, i) in menuImagePreviews" :key="i" :src="src" class="w-24 h-24 object-cover rounded" />
        </div>
      </div>

      <!-- Fotos da Comida -->
      <div class="md:col-span-2">
        <label class="block font-semibold mb-2">Fotos da Comida</label>
        <input type="file" accept="image/*" multiple @change="onFoodImages" />
        <div class="flex flex-wrap gap-2 mt-2">
          <img v-for="(src, i) in foodImagePreviews" :key="i" :src="src" class="w-24 h-24 object-cover rounded" />
        </div>
      </div>

      <!-- Botão -->
      <div class="md:col-span-2 flex justify-center mt-4">
        <button
          type="submit"
          class="bg-[#095243] text-white px-6 py-2 rounded hover:bg-[#073b31]"
        >
          Criar Restaurante
        </button>
      </div>

      <div class="md:col-span-2 text-center mt-4">
        <button
          type="button"
          @click="guardarNoLocalStorageTeste"
          class="bg-gray-300 text-gray-800 px-4 py-2 rounded hover:bg-gray-400"
        >
          Guardar para Testes
        </button>
      </div>
    </form>
  </div>
  <Footer />
</template>

<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()
import { ref } from 'vue'
import TopNav from '../Layout/TopNav.vue'
import Footer from '../Footer.vue'

const name = ref('')
const location = ref('')
const cuisineType = ref('')
const schedule = ref('')
const coverImage = ref(null)
const coverPreview = ref(null)
const menuImages = ref([])
const menuImagePreviews = ref([])
const foodImages = ref([])
const foodImagePreviews = ref([])

const onCoverImage = (e) => {
  const file = e.target.files[0]
  if (file) {
    coverImage.value = file
    coverPreview.value = URL.createObjectURL(file)
  }
}

const onMenuImages = (e) => {
  const files = Array.from(e.target.files)
  files.forEach(file => {
    if (!menuImages.value.includes(file)) {
      menuImages.value.push(file)
      menuImagePreviews.value.push(URL.createObjectURL(file))
    }
  })
}

const onFoodImages = (e) => {
  const files = Array.from(e.target.files)
  files.forEach(file => {
    if (!foodImages.value.includes(file)) {
      foodImages.value.push(file)
      foodImagePreviews.value.push(URL.createObjectURL(file))
    }
  })
}

import RestaurantService from '../../services/restaurant.service';
import { RestaurantDetailedDTO } from '../../dto/restaurant.dto.js'


const service = new RestaurantService();

const submitForm = async () => {
  if (!coverImage.value) {
    alert('Imagem de capa é obrigatória!');
    return;
  }

  const user = JSON.parse(localStorage.getItem('user') || '{}');

  // Converter a imagem de capa para base64
  const coverBase64 = await fileToBase64(coverImage.value);

  // Converter todas as imagens do menu para base64
  const menuBase64 = await Promise.all(
    menuImages.value.map(file => fileToBase64(file))
  );

  // Converter todas as imagens da comida para base64
  const foodBase64 = await Promise.all(
    foodImages.value.map(file => fileToBase64(file))
  );

const restaurantDTO = new RestaurantDetailedDTO({
  name: name.value,
  location: location.value,
  cuisineType: cuisineType.value,
  schedule: schedule.value,
  rating: 0,
  image: coverBase64,
  menuImages: menuBase64,
  foodImages: foodBase64,
  owner: user.id || 'anon'
})


  try {
    console.log('📦 Enviando para o backend:', restaurantDTO.toAPIRequest())
    await service.createRestaurant(restaurantDTO);
    alert('✅ Restaurante criado com sucesso!');
    router.push('/profile');
  } catch (error) {
    console.error('❌ Erro ao criar restaurante:', error.response?.data || error.message);
    alert('Erro ao criar restaurante: ' + (error.response?.data?.message || 'ver consola'));
  }

};

function fileToBase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = () => resolve(reader.result); // base64
    reader.onerror = reject;
    reader.readAsDataURL(file);
  });
}

const guardarNoLocalStorageTeste = () => {
  const reader = new FileReader()
  reader.onload = () => {
    const restaurants = {
      id: `rest-${Date.now()}`,
      name: name.value,
      location: location.value,
      cuisineType: cuisineType.value,
      schedule: schedule.value,
      image: reader.result, // imagem de capa em base64
      rating: 0,
      reviews: []
    }

    const guardados = JSON.parse(localStorage.getItem('restaurants')) || []
    guardados.push(restaurants)
    localStorage.setItem('restaurants', JSON.stringify(guardados))

    alert('Guardado no localStorage para testes.')
  }

  if (coverImage.value) {
    reader.readAsDataURL(coverImage.value)
  } else {
    alert('Seleciona uma imagem de capa para testes.')
  }
}


</script>

<style scoped>
.input-style {
  border: 1px solid #ccc;
  padding: 8px 12px;
  border-radius: 6px;
  width: 100%;
  font-size: 0.875rem;
}
</style>
