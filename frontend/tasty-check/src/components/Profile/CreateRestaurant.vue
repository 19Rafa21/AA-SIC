<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth' // <-- import store
import TopNav from '../Layout/TopNav.vue'
import Footer from '../Footer.vue'
import { RestaurantDetailedDTO } from '@/dto/restaurant.dto.js'
import RestaurantService from '@/services/restaurant.service.js'

const router = useRouter()
const service = new RestaurantService()
const authStore = useAuthStore()

const name = ref('')
const location = ref('')
const cuisineType = ref('')
const imageFile = ref(null)
const imagePreview = ref('')
const menuImageFiles = ref([])
const foodImageFiles = ref([])
const errorMessage = ref('')
const successMessage = ref('')
const isLoading = ref(false)

const handleImageUpload = (event) => {
  const file = event.target.files[0]
  if (file) {
    imageFile.value = file
    imagePreview.value = URL.createObjectURL(file)
  }
}

const handleMenuImages = (event) => {
  const files = Array.from(event.target.files)
  menuImageFiles.value.push(...files)
}

const handleFoodImages = (event) => {
  const files = Array.from(event.target.files)
  foodImageFiles.value.push(...files)
}

const removeMenuImage = (index) => {
  menuImageFiles.value.splice(index, 1)
}

const removeFoodImage = (index) => {
  foodImageFiles.value.splice(index, 1)
}

const removeImage = () => {
  imageFile.value = null
  imagePreview.value = ''
  const fileInput = document.getElementById('restaurant-image')
  if (fileInput) fileInput.value = ''
}

const getObjectURL = (file) => {
  return file ? URL.createObjectURL(file) : ''
}

const createRestaurant = async () => {
  if (!name.value || !location.value || !cuisineType.value) {
    errorMessage.value = 'Preencha todos os campos obrigatórios.'
    return
  }

  if (!authStore.isAuthenticated || !authStore.currentUser?.id) {
    errorMessage.value = 'É necessário estar autenticado para criar um restaurante.'
    return
  }

  isLoading.value = true
  const dto = new RestaurantDetailedDTO({
    name: name.value,
    location: location.value,
    cuisineType: cuisineType.value,
    rating: 0.0,
    image: imageFile.value?.name || '',
    menuImages: [],
    foodImages: [],
    owner: authStore.currentUser.id // <-- agora está correto!
  })

  try {
    await service.createRestaurant(dto, imageFile.value, menuImageFiles.value, foodImageFiles.value)
    successMessage.value = 'Restaurante criado com sucesso!'
    alert('🎉 Restaurante adicionado com sucesso!')
    setTimeout(() => router.push('/'), 1500)
  } catch (error) {
    errorMessage.value = 'Erro ao criar restaurante. Verifica os dados e tenta novamente.'
  } finally {
    isLoading.value = false
  }
}

const cancel = () => {
  router.push('/')
}
</script>

<template>
  <div class="page-container">
    <TopNav />

    <main class="create-restaurant-container">
      <div class="form-card">
        <h2 class="form-title">Criar Novo Restaurante</h2>

        <form class="form">
          <div class="image-preview-wrapper mb-4">
            <img
              :src="imagePreview || 'https://via.placeholder.com/500x200?text=Imagem+Restaurante'"
              alt="Imagem do Restaurante"
              class="image-preview"
            />
          </div>

          <div class="form-group">
            <label class="label">Imagem de Destaque</label>
            <input
              type="file"
              id="restaurant-image"
              class="input-file"
              @change="handleImageUpload"
              accept="image/*"
            />
          </div>

          <div class="form-group">
            <label class="label">Nome do Restaurante *</label>
            <input type="text" class="input" v-model="name" />
          </div>

          <div class="form-group">
            <label class="label">Localização *</label>
            <input type="text" class="input" v-model="location" />
          </div>

          <div class="form-group">
            <label class="label">Tipo de Cozinha *</label>
            <input type="text" class="input" v-model="cuisineType" />
          </div>

          <!-- Imagens Menu -->
          <div class="form-group">
            <label class="label">Imagens do Menu (opcional)</label>
            <input type="file" multiple @change="handleMenuImages" accept="image/*" />
            <div class="image-list">
              <div v-for="(img, i) in menuImageFiles" :key="i" class="image-thumb">
                <img :src="getObjectURL(img)" class="thumb" />
                <button type="button" class="remove-btn" @click="removeMenuImage(i)">×</button>
              </div>
            </div>
          </div>

          <!-- Imagens Comida -->
          <div class="form-group">
            <label class="label">Imagens da Comida (opcional)</label>
            <input type="file" multiple @change="handleFoodImages" accept="image/*" />
            <div class="image-list">
              <div v-for="(img, i) in foodImageFiles" :key="i" class="image-thumb">
                <img :src="getObjectURL(img)" class="thumb" />
                <button type="button" class="remove-btn" @click="removeFoodImage(i)">×</button>
              </div>
            </div>
          </div>

          <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
          <div v-if="successMessage" class="success">{{ successMessage }}</div>

          <div class="btn-group">
            <button type="button" class="search-button cancel-btn" @click="cancel">
              CANCELAR
            </button>
            <button type="button" class="search-button" @click.prevent="createRestaurant" :disabled="isLoading">
              <span v-if="isLoading">
                <i class="fas fa-spinner fa-spin mr-2"></i> A criar...
              </span>
              <span v-else>
                CRIAR RESTAURANTE
              </span>
            </button>
          </div>
        </form>
      </div>
    </main>

    <Footer />
  </div>
</template>

<style scoped>
/* [CSS igual ao teu — não foi modificado] */
.page-container {
  background-color: #ffffff;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.create-restaurant-container {
  flex: 1;
  padding: 3rem 1rem 2rem;
  display: flex;
  justify-content: center;
  align-items: center;
}

.form-card {
  background: #f8f9fa;
  padding: 2rem;
  border-radius: 1rem;
  max-width: 500px;
  width: 100%;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
}

.form-title {
  font-size: 1.8rem;
  font-weight: bold;
  text-align: center;
  margin-bottom: 1.5rem;
  color: #1a2d29;
}

.image-preview-wrapper {
  width: 100%;
  height: 200px;
  overflow: hidden;
  border-radius: 1rem;
  background-color: #e2e8f0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.image-preview {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 1rem;
}

.form-group {
  margin-bottom: 1.2rem;
  display: flex;
  flex-direction: column;
}

.label {
  font-size: 0.95rem;
  color: #4b5563;
  margin-bottom: 0.4rem;
  font-weight: 500;
}

.input,
.input-file {
  padding: 0.6rem 0.8rem;
  border-radius: 0.75rem;
  border: 1px solid #cbd5e1;
  font-size: 0.95rem;
}

.input:focus {
  border-color: #095243;
  outline: none;
}

.image-list {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-top: 0.5rem;
}

.image-thumb {
  position: relative;
  width: 80px;
  height: 80px;
}

.thumb {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 0.5rem;
}

.remove-btn {
  position: absolute;
  top: -6px;
  right: -6px;
  background: #dc3545;
  color: white;
  border: none;
  border-radius: 9999px;
  width: 20px;
  height: 20px;
  font-size: 0.75rem;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.error {
  background: #f8d7da;
  color: #842029;
  padding: 0.5rem;
  border-radius: 0.5rem;
  text-align: center;
  margin-bottom: 1rem;
}

.success {
  background: #d1e7dd;
  color: #0f5132;
  padding: 0.5rem;
  border-radius: 0.5rem;
  text-align: center;
  margin-bottom: 1rem;
}

.btn-group {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  margin-top: 1.5rem;
}

.search-button {
  flex: 1;
  border: none;
  border-radius: 0.75rem;
  padding: 0.65rem 1.5rem;
  font-weight: bold;
  font-size: 0.9rem;
  color: white;
  background-color: #095243;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background-color 0.3s ease;
}

.search-button:hover {
  background-color: #073b31;
}

.search-button:disabled {
  background-color: #adb5bd;
  cursor: not-allowed;
}

.cancel-btn {
  background-color: #dee2e6;
  color: #1e293b;
}

.cancel-btn:hover {
  background-color: #cfd4da;
}
</style>
