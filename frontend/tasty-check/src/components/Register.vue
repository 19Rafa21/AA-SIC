<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import MaterialInput from "@/material/MaterialInput.vue"
import MaterialButton from "@/material/MaterialButton.vue"

const router = useRouter()

// Campos do formulário
const name = ref('')
const email = ref('')
const password = ref('')
const confirmPassword = ref('')

// Role selecionado
const role = ref('cliente')

// Lógica de registo
const register = () => {
  if (password.value !== confirmPassword.value) {
    alert('As passwords não coincidem.')
    return
  }

  // Guardar no localStorage (simulação)
  localStorage.setItem('user', JSON.stringify({
    name: name.value,
    email: email.value,
    avatar: 'avatar.png',
    role: role.value
  }))
  localStorage.setItem('isLoggedIn', 'true')

  console.log("Conta criada com sucesso!")
  router.push('/')
  setTimeout(() => location.reload(), 100)
}


</script>

<template>
  <DefaultNavbar transparent />
  <Header>
    <div
      class="page-header align-items-start min-vh-100"
      :style="{ backgroundImage: 'url(/img/login-bg.png)' }"
      loading="lazy"
    >
      <span class="mask bg-gradient-dark opacity-6"></span>
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

                  <div class="text-center">
                    <MaterialButton
                      class="my-4 mb-2"
                      variant="gradient"
                      color="success"
                      fullWidth
                      @click.prevent="register"
                    >
                      Criar Conta
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
  </Header>
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


</style>
