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

      <!-- Centro: barra de pesquisa -->
      <div class="flex-1 flex justify-center">
        <SearchBar />
      </div>

      <!-- Direita: links e avatar -->
        <Avatar />
    </div>
  </nav>
</template>

<script setup>
import router from '@/router';
import SearchBar from './SearchBar.vue'
import Avatar from '../MainPage/TopNavbar.vue';  
</script>

<style scoped>
.top-nav {
  background-color: #095243;
  height: 64px;
  display: flex;
  align-items: center;
  padding: 0 2rem;
  position: sticky;
  top: 0;
  z-index: 50;
  width: 100%;
}

.nav-content {
  display: flex;
  justify-content: space-between;
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
  border-radius: 50%;         /* círculo */
  border: 2px solid white;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
  object-fit: cover;
}

</style>
