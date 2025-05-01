<template>
  <div class="w-full max-w-3xl mx-auto">
    <form class="flex items-center w-full bg-gray-100 border border-gray-300 rounded-xl overflow-hidden h-14">
      <!-- Filtro: Cozinha -->
      <div class="flex items-center px-4 h-full border-r border-gray-300">
        <!-- Ícone do filtro -->
        <i class="fas fa-filter mr-2 text-gray-600"></i>
        <span class="font-semibold text-sm text-gray-600 whitespace-nowrap">Filtrar</span>
        <!-- Ícone seta para baixo -->
        <i class="fas fa-chevron-down ml-2 text-gray-600"></i>
      </div>

      <!-- Input: Nome do Restaurante -->
      <div class="flex items-center flex-grow h-full">
        <i class="fas fa-search mx-3 text-gray-400 text-lg"></i>
        <input
          type="text"
          class="w-full h-full bg-transparent border-none outline-none px-2 text-base"
          placeholder="Cozinha, Restaurante, Localização ..."
        />
      </div>

      <!-- Botão: Procurar -->
      <button 
        type="submit" 
        class="bg-emerald-800 hover:bg-emerald-900 text-white font-bold text-sm py-2 px-6 mx-2 my-2 h-4/5 rounded-lg transition-colors duration-300 flex items-center justify-center">
        PROCURAR
      </button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'SearchBar'
}
</script>