<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import TopNav from '../Layout/TopNav.vue'
import Footer from '../Footer.vue'
import { RestaurantDetailedDTO } from '@/dto/restaurant.dto.js'
import RestaurantService from '@/services/restaurant.service.js'

const router = useRouter()
const service = new RestaurantService()

// Formulário
const name = ref('')
const location = ref('')
const cuisineType = ref('')
const schedule = ref('') // Horário do restaurante (string)
const imageFile = ref(null)
const imagePreview = ref('')
const errorMessage = ref('')
const successMessage = ref('')

// Handler de imagem
const handleImageUpload = (event) => {
  const file = event.target.files[0]
  if (file) {
    imageFile.value = file
    imagePreview.value = URL.createObjectURL(file)
  }
}

const removeImage = () => {
  imageFile.value = null
  imagePreview.value = ''
  const fileInput = document.getElementById('restaurant-image')
  if (fileInput) fileInput.value = ''
}

// Criar restaurante
const createRestaurant = async () => {
  if (!name.value || !location.value || !cuisineType.value || !schedule.value) {
    errorMessage.value = 'Preencha todos os campos obrigatórios.'
    return
  }

  const dto = new RestaurantDetailedDTO({
    name: name.value,
    location: location.value,
    cuisineType: cuisineType.value,
    rating: 0.0, // Avaliação fixa a zero
    image: imageFile.value?.name || '',
    menuImages: [],
    foodImages: []
  })

  try {
    await service.createRestaurant(dto)
    successMessage.value = 'Restaurante criado com sucesso!'
    alert('🎉 Restaurante adicionado com sucesso!')
    setTimeout(() => router.push('/'), 1500)
  } catch (error) {
    errorMessage.value = 'Erro ao criar restaurante. Verifica os dados e tenta novamente.'
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
          <!-- Imagem preview -->
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
            <label class="label">Nome do Restaurante</label>
            <input type="text" class="input" v-model="name" />
          </div>

          <div class="form-group">
            <label class="label">Localização</label>
            <input type="text" class="input" v-model="location" />
          </div>

          <div class="form-group">
            <label class="label">Tipo de Cozinha</label>
            <input type="text" class="input" v-model="cuisineType" />
          </div>

          <div class="form-group">
            <label class="label">Horário</label>
            <input type="text" class="input" v-model="schedule" placeholder="Ex: 10h - 22h" />
          </div>

          <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
          <div v-if="successMessage" class="success">{{ successMessage }}</div>

          <div class="btn-group">
            <button type="button" class="btn cancel" @click="cancel">Cancelar</button>
            <button type="button" class="btn confirm" @click.prevent="createRestaurant">
              Criar Restaurante
            </button>
          </div>
        </form>
      </div>
    </main>

    <Footer />
  </div>
</template>

<style scoped>
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
  background-color: #ffffff;
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

.btn {
  flex: 1;
  padding: 0.8rem 1rem;
  font-size: 1rem;
  border-radius: 9999px;
  font-weight: 600;
  cursor: pointer;
  transition: 0.2s;
  border: none;
}

.btn.cancel {
  background: #e2e8f0;
  color: #1e293b;
}

.btn.cancel:hover {
  background: #cbd5e1;
}

.btn.confirm {
  background: #095243;
  color: white;
}

.btn.confirm:hover {
  background: #073b31;
}
</style>
