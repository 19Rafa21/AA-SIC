<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

import MaterialInput from "@/material/MaterialInput.vue"
import MaterialSwitch from "@/material/MaterialSwitch.vue"
import MaterialButton from "@/material/MaterialButton.vue"

const router = useRouter()
const email = ref('')
const password = ref('')

const login = () => {
  console.log('Email:', email.value)
  console.log('Password:', password.value)
  
  if (email.value === 'admin' && password.value === 'admin') {
    localStorage.setItem('isLoggedIn', 'true')
    localStorage.setItem('user', JSON.stringify({
      name: 'admin',
      email: 'admin@tasty.pt',
      avatar: '/img/avatar.png',
      role: 'proprietario'
    }))
    router.push('/')
    setTimeout(() => location.reload(), 100)
  } else if (email.value === 'cliente' && password.value === '123') {
    localStorage.setItem('isLoggedIn', 'true')
    localStorage.setItem('user', JSON.stringify({
      name: 'Maria Cliente',
      email: 'maria@tasty.pt',
      avatar: 'cliente.png',
      role: 'cliente'
    }))
    router.push('/')
    setTimeout(() => location.reload(), 100)
  } else {
    console.error('Credenciais inválidas')
    alert('Email ou password incorretos.')
  }
}
</script>


<template>
  <DefaultNavbar transparent />
  <Header>
    <div
  class="page-header min-vh-100 d-flex align-items-center justify-content-center px-3"
  :style="{ backgroundImage: 'url(/img/login-bg.png)' }"
  loading="lazy"
>
  <div class="w-100" style="max-width: 400px;">
    <div class="card z-index-0 fadeIn3 fadeInBottom">
      <div class="card-header p-0 position-relative mt-2 mx-3 z-index-2 tasty-header">
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

          <div class="text-center">
            <MaterialButton
              class="my-4 mb-2"
              variant="gradient"
              color="success"
              fullWidth
              @click="login"
            >
              Iniciar Sessão
            </MaterialButton>
          </div>

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

  </Header>
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