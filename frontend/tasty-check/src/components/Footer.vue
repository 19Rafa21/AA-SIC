<template>
  <footer class="bg-[#095243] text-white py-4 mt-24">
    <div class="container mx-auto px-6 flex flex-col md:flex-row items-center justify-between">
      <!-- Logo e slogan -->
      <div class="flex flex-col items-center">
        <span class="text-3xl font-bold mb-2">Tasty<span class="text-[#1A2D29] ml-1">Check</span></span>
        <p class="text-sm">Connect with Us and Explore</p>
      </div>

      <!-- Redes sociais -->
      <div class="flex gap-6 text-2xl">
        <a href="https://twitter.com/seuperfil" target="_blank" rel="noopener" class="text-white hover:text-slate-400">
          <i class="fab fa-twitter"></i>
        </a>
        <a href="https://instagram.com/seuperfil" target="_blank" rel="noopener" class="text-white hover:text-slate-400">
          <i class="fab fa-instagram"></i>
        </a>
        <a href="https://linkedin.com/in/seuperfil" target="_blank" rel="noopener" class="text-white hover:text-slate-400">
          <i class="fab fa-linkedin"></i>
        </a>
      </div>
    </div>

    <!-- Copyright -->
    <div class="border-t border-slate-700 mt-6 pt-4">
      <div class="container mx-auto px-6 text-center text-xs text-slate-400">
        © {{ new Date().getFullYear() }} TastyCheck. Todos os direitos reservados.
      </div>
    </div>
  </footer>
</template>

<script setup>
// Nenhuma lógica específica por agora
</script>

<style scoped>
.container {
  max-width: 1200px;
}
</style>
