<template>
  <nav class="top-nav">
    <div class="nav-content">

      <!-- Esquerda: logo -->
      <router-link to="/" class="flex items-center gap-2">
        <img src="/img/logo.png" alt="Logo" class="logo-img" />
        <span class="text-white text-xl font-bold">
          <span>Tasty</span><span class="text-[#1A2D29] ml-1">Check</span>
        </span>
      </router-link>

      <!-- Centro: barra de pesquisa (versão compacta) -->
      <div class="flex-1 flex justify-center">
        <SearchBarCompact />
      </div>

      <!-- Direita: avatar -->
      <div class="flex items-center justify-end h-full">
        <Avatar />
      </div>

    </div>
  </nav>
</template>

<script setup>
import SearchBarCompact from './SearchBarCompact.vue'
import Avatar from '../MainPage/Avatar.vue'
</script>

<style scoped>
.top-nav {
  background-color: #095243;
  height: 55px;
  display: flex;
  align-items: center;
  padding: 0 2rem;
  position: fixed;
  top: 0;
  z-index: 50;
  width: 100%;
}

.nav-content {
  display: grid;
  grid-template-columns: auto 1fr auto;
  align-items: center;
  width: 100%;
}

.logo-img {
  width: 60px;
  height: 60px;
  margin-right: -10px;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 9999px;
  border: 2px solid white;
  object-fit: cover;
}
</style>
