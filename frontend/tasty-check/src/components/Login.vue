<script setup>
import MaterialInput from "@/material/MaterialInput.vue";
import MaterialSwitch from "@/material/MaterialSwitch.vue";
import MaterialButton from "@/material/MaterialButton.vue";

</script>

<template>
  <DefaultNavbar transparent />
  <Header>
    <div
      class="page-header align-items-start min-vh-100"
      :style="{
        backgroundImage: 'url(/img/login-bg.png)'
        }"
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
                    id="email"
                    class="input-group-outline my-3"
                    :label="{ text: 'Email', class: 'form-label' }"
                    type="email"
                  />
                  <MaterialInput
                    id="password"
                    class="input-group-outline mb-3"
                    :label="{ text: 'Password', class: 'form-label' }"
                    type="password"
                  />
                  <MaterialSwitch
                    class="d-flex align-items-center mb-3"
                    id="rememberMe"
                    labelClass="mb-0 ms-3"
                    checked
                    >Lembrar-me</MaterialSwitch
                  >

                  <div class="text-center">
                    <MaterialButton
                      class="my-4 mb-2"
                      variant="gradient"
                      color="success"
                      fullWidth
                      >Iniciar Sessão</MaterialButton
                    >
                  </div>
                  <p class="mt-4 text-sm text-center">
                    Não tem conta?
                    <a
                      href="#"
                      class="text-success text-gradient font-weight-bold"
                      >Registar</a
                    >
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
  color: #1A2D29; /* Verde mais claro que o fundo */
}

  .hero-logo {
    width: 100px;
    height: 100px;
    margin-left: 0; 
    margin-right: -50px;
    vertical-align: middle;
  }

</style>