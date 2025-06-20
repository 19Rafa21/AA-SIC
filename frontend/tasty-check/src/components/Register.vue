<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import MaterialInput from "@/material/MaterialInput.vue"
import MaterialButton from "@/material/MaterialButton.vue"
import { useAuthStore } from "@/stores"

const router = useRouter()
const authStore = useAuthStore()

// Campos do formulário
const name = ref('')
const email = ref('')
const password = ref('')
const confirmPassword = ref('')
const errorMessage = ref('')
const profileImage = ref(null)  // Referência para o arquivo de imagem
const imagePreview = ref('')    // Para mostrar preview da imagem

// Computed properties from the store
const isLoading = computed(() => authStore.isLoading)

// Role selecionado
const role = ref('cliente')

// Handler para seleção de arquivo de imagem
const handleImageUpload = (event) => {
  const file = event.target.files[0]
  if (file) {
    profileImage.value = file
    
    // Criar uma URL para preview
    imagePreview.value = URL.createObjectURL(file)
  }
}

// Remover imagem selecionada
const removeImage = () => {
  profileImage.value = null
  imagePreview.value = ''
  // Reset input do tipo file
  const fileInput = document.getElementById('profile-image')
  if (fileInput) fileInput.value = ''
}

// Lógica de registo
const register = async () => {
  // Validação do formulário
  if (!name.value || !email.value || !password.value || !confirmPassword.value) {
    errorMessage.value = 'Por favor, preencha todos os campos.'
    return
  }
  
  if (password.value !== confirmPassword.value) {
    errorMessage.value = 'As passwords não coincidem.'
    return
  }

  // Clear any previous error
  authStore.clearError()
  errorMessage.value = ''
  
  // Mapear o papel para o valor do discriminador
  const discriminator = role.value === 'proprietario' ? 'Owner' : 'User'
  
  // Register using the auth store
  const registerSuccess = await authStore.register(
    email.value, 
    password.value, 
    name.value, 
    discriminator,
    profileImage.value // Passar o arquivo de imagem se existir
  )
  
  if (registerSuccess) {
    // After successful registration, login the user
    const loginSuccess = await authStore.login(email.value, password.value)
    
    if (loginSuccess) {
      console.log("Conta criada com sucesso!")
      router.push('/')
    } else {
      errorMessage.value = authStore.error || 'Erro ao iniciar sessão. Por favor, tente fazer login manualmente.'
    }
  } else {
    errorMessage.value = authStore.error || 'Ocorreu um erro ao criar a conta. Por favor, tente novamente.'
  }
}

const home = () => {
  router.push('/')
}

</script>

<template>
  <!-- <DefaultNavbar transparent /> -->
  <!-- <Header> -->
    <div
      class="page-header align-items-start min-vh-100"
      :style="{ backgroundImage: 'url(/img/login-bg.png)' }"
      loading="lazy"
    >
      <MaterialButton class="!fixed top-4 right-4 z-[100] max-w-[150px]" variant="gradient" color="success" fullWidth @click="home">
        <span class="text-md mr-2">Voltar</span> 
        <i class="fa-solid fa-turn-up pl-2 !mt-3 -rotate-90"></i>
      </MaterialButton> 

      <span class="mask opacity-6"></span>
      <div class="container my-auto">
        <div class="row">
          <div class="col-lg-4 col-md-8 col-12 mx-auto">
            <div class="card z-index-0 fadeIn3 fadeInBottom">
              <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2 tasty-header">
                <h1 class="hero-title">
                  <span class="tasty-text">Tasty</span>
                  <span class="check-group">
                    <span class="check-text">Check</span>
                    <img src="/img/logo.png" alt="Logo" class="hero-logo" />
                  </span>
                </h1>
              </div>

              <div class="card-body">
                <form role="form" class="text-start">
                  <MaterialInput
                    id="name"
                    class="input-group-outline my-3"
                    placeholder="Nome"
                    type="text"
                    v-model="name"
                  />
                  <MaterialInput
                    id="email"
                    class="input-group-outline mb-3"
                    placeholder="Email"
                    type="email"
                    v-model="email"
                  />
                  <MaterialInput
                    id="password"
                    class="input-group-outline mb-3"
                    placeholder="Password"
                    type="password"
                    v-model="password"
                  />
                  <MaterialInput
                    id="confirm-password"
                    class="input-group-outline mb-3"
                    placeholder="Confirmar Password"
                    type="password"
                    v-model="confirmPassword"
                  />

                  <!-- Avatar Imagem -->
                  <div class="mb-3">
                    <p class="text-sm text-dark mb-1">Imagem de Perfil (opcional)</p>
                    <div v-if="imagePreview" class="mb-2">
                      <div class="preview-container">
                        <img :src="imagePreview" alt="Pré-visualização" class="img-preview" />
                        <button 
                          type="button" 
                          class="btn-remove-image" 
                          @click="removeImage"
                          title="Remover imagem"
                        >
                          <i class="fas fa-times"></i>
                        </button>
                      </div>
                    </div>
                    <div class="input-group input-group-outline">
                      <input
                        type="file"
                        id="profile-image"
                        class="form-control"
                        @change="handleImageUpload"
                        accept="image/*"
                      />
                    </div>
                    <span class="text-xs text-muted">Formatos aceites: JPG, PNG, GIF (máx. 5MB)</span>
                  </div>

                  <!-- Nav pills para selecionar tipo de utilizador -->
                  <div class="nav-wrapper position-relative end-0 my-3">
                    <ul class="nav nav-pills nav-fill p-1 bg-gray-100 rounded" role="tablist">
                      <li class="nav-item">
                        <a
                          class="nav-link mb-0 px-0 py-1"
                          :class="{ active: role === 'cliente' }"
                          @click.prevent="role = 'cliente'"
                          role="tab"
                        >
                          Cliente
                        </a>
                      </li>
                      <li class="nav-item">
                        <a
                          class="nav-link mb-0 px-0 py-1"
                          :class="{ active: role === 'proprietario' }"
                          @click.prevent="role = 'proprietario'"
                          role="tab"
                        >
                          Proprietário
                        </a>
                      </li>
                    </ul>
                  </div>

                  <div v-if="errorMessage" class="alert alert-danger py-2 text-sm text-center mb-3">
                    {{ errorMessage }}
                  </div>

                  <div class="text-center">
                    <MaterialButton
                      class="my-4 mb-2"
                      variant="gradient"
                      color="success"
                      fullWidth
                      @click.prevent="register"
                      :disabled="isLoading"
                    >
                      <span v-if="isLoading">A registar...</span>
                      <span v-else>Criar Conta</span>
                    </MaterialButton>
                  </div>
                  <p class="mt-4 text-sm text-center text-black">
                    Já tem conta?
                    <router-link to="/login" class="text-success text-gradient font-weight-bold">Iniciar Sessão</router-link>
                  </p>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  <!-- </Header> -->
</template>

<style scoped>
.hero-title {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2.25rem;
  font-weight: 700;
  border-radius: 0.5rem;
  background-color: #095243 !important;
}

.tasty-text {
  color: white;
  margin-right: 0.5rem;
}

.check-group {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.check-text {
  color: #1A2D29;
}

.hero-logo {
  width: 100px;
  height: 100px;
  margin-left: 0;
  margin-right: -50px;
  vertical-align: middle;
}

.nav-pills .nav-link {
  transition: background-color 0.2s ease, color 0.2s ease;
  border-radius: 0.5rem;
  color: #095243;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 40px;
}

.nav-pills .nav-item {
  flex: 1; /* Força tamanho igual */
}

.nav-pills .nav-link.active {
  background-color: #095243;
  color: white;
}

.nav-pills .nav-link.active:hover {
  background-color: #073b31;
}

.nav-pills .nav-link:hover {
  background-color: #d1e7dd;
}

.moving-tab {
  background: #095243;
  color: white !important;
  z-index: 1;
  border-radius: 0.5rem;
}

.img-preview {
  max-width: 100px;
  max-height: 100px;
  border-radius: 8px;
  object-fit: cover;
}

.preview-container {
  position: relative;
  display: inline-block;
  margin-bottom: 10px;
}

.btn-remove-image {
  position: absolute;
  top: -8px;
  right: -8px;
  background: #dc3545;
  color: white;
  border: none;
  border-radius: 50%;
  width: 24px;
  height: 24px;
  font-size: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: background-color 0.2s;
}

.btn-remove-image:hover {
  background: #c82333;
}

input[type="file"] {
  padding: 10px;
  margin-bottom: 5px;
}

.text-muted {
  color: #6c757d;
}
</style>
