<script setup>
import { onMounted, onUnmounted } from "vue";
import HeaderComponent from './HeaderComponent.vue';
import TopNavbar from './TopNavbar.vue';
import SearchBar from './SearchBar.vue';

const headerBgImage = '/img/header-bg.webp';

const body = document.getElementsByTagName("body")[0];
onMounted(() => {
  body.classList.add("presentation-page");
  body.classList.add("bg-gray-200");
});
onUnmounted(() => {
  body.classList.remove("presentation-page");
  body.classList.remove("bg-gray-200");
});
</script>

<template>
  <TopNavbar/>
  <HeaderComponent>
    <div class="page-header min-vh-100"
         :style="`background-image: url(${headerBgImage})`"
         loading="lazy">

        <div class="container h-100 flex flex-col justify-center items-center">
            <div class="hero-brand flex items-center justify-center">
                <h1 class="text-3xl font-bold flex items-center">
                <span class="text-white">Tasty</span>
                <span class="flex items-center">
                    <span class="text-[#095243]">Check</span>
                    <img src="../../public/img/logo.png" alt="Logo" class="w-[100px] h-[100px] hero-logo ms-2" />
                </span>
                </h1>
            </div>

            <p class="lead text-white px-5 mt-3" :style="{ fontWeight: '500' }">
                Sabores incríveis, momentos inesquecíveis.
            </p>

            <SearchBar class="mt-4"/>
        </div>
    </div>
  </HeaderComponent>


</template>

<style scoped>
/* Mantendo o estilo existente para compatibilidade, mas usando primariamente Tailwind nas classes */

.container {
  height: 100%;
}

/* Replicando exatamente o estilo do PresentationView original */
.min-h-screen {
  min-height: 100vh;
}
</style>