<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

import MaterialInput from "@/material/MaterialInput.vue"
import MaterialSwitch from "@/material/MaterialSwitch.vue"
import MaterialButton from "@/material/MaterialButton.vue"
import { AuthService } from "@/services"

const router = useRouter()
const email = ref('')
const password = ref('')
const errorMessage = ref('')
const isLoading = ref(false)

const login = async () => {
  if (!email.value || !password.value) {
    errorMessage.value = 'Por favor, preencha todos os campos.'
    return
  }
  
  try {
    isLoading.value = true
    errorMessage.value = ''
    
    // Call the authentication service
    const userData = await AuthService.login(email.value, password.value)
    console.log('Login successful', userData)
    
    router.push('/')
    setTimeout(() => location.reload(), 100)
  } catch (error) {
    console.error('Erro ao fazer login:', error)
    errorMessage.value = error.response?.data?.message || 'Email ou password incorretos.'
  } finally {
    isLoading.value = false
  }
}

const home = () => {
  router.push('/')
}
</script>


<template>
  <DefaultNavbar transparent />
  <!-- <Header> -->
    <div
  class="page-header min-vh-100 d-flex align-items-center justify-content-center px-3"
  :style="{ backgroundImage: 'url(/img/login-bg.png)' }"
  loading="lazy"
>
  <MaterialButton class="!fixed top-4 right-4 z-[100] max-w-[150px]" variant="gradient" color="success" fullWidth @click="home">
    <span class="text-md mr-2">Voltar</span> 
    <i class="fa-solid fa-turn-up pl-2 !mt-3 -rotate-90"></i>
  </MaterialButton>

  <div class="w-100" style="max-width: 400px;">
    <div class="card z-index-0 fadeIn3 fadeInBottom">
      <router-link to="/" class="card-header p-0 position-relative mt-2 mx-3 z-index-2 tasty-header">
        <h1 class="hero-title">
          <span class="tasty-text">Tasty</span>
          <span class="check-group">
            <span class="check-text">Check</span>
            <img src="/img/logo.png" alt="Logo" class="hero-logo" />
          </span>
        </h1>
      </router-link>

      <div class="card-body">
        <form role="form" class="text-start">
          <MaterialInput
            id="email"
            placeholder="Email"
            class="input-group-outline my-3"
            v-model="email"
          />
          <MaterialInput
            id="password"
            class="input-group-outline mb-3"
            placeholder="Password"
            type="password"
            v-model="password"
          />
          <MaterialSwitch
            class="d-flex align-items-center mb-3"
            id="rememberMe"
            labelClass="mb-0 ms-3"
            checked
          >Lembrar-me</MaterialSwitch>
          
          <div v-if="errorMessage" class="alert alert-danger py-2 text-sm text-center">
            {{ errorMessage }}
          </div>

          <div class="text-center">
            <MaterialButton
              class="my-4 mb-2"
              variant="gradient"
              color="success"
              fullWidth
              @click="login"
              :disabled="isLoading"
              :loading="isLoading"
            >
              Iniciar Sessão
            </MaterialButton>
          </div>

          <p v-if="errorMessage" class="text-danger text-center">{{ errorMessage }}</p>

          <p class="mt-4 text-sm text-center">
            Não tem conta?
            <router-link to="/register" class="text-success text-gradient font-weight-bold">
              Registar
            </router-link>
          </p>
        </form>
      </div>
    </div>
  </div>
</div>

  <!-- </Header> -->
</template>

<style>

.hero-title {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2.25rem; /* equivalente a text-3xl */
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

@media (max-width: 480px) {
  .hero-logo {
    width: 60px;
    height: 60px;
    margin-right: -30px;
  }

  .hero-title {
    font-size: 1.5rem;
    flex-direction: column;
  }
}

</style>