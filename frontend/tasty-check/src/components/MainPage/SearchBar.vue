<template>
  <div class="w-full max-w-[800px] mx-auto">
    <form class="w-full flex items-center bg-[#e9ecef] border border-[#ced4da] rounded-[0.75rem] h-[55px] overflow-hidden p-0">
      <!-- Filtro: Cozinha -->
      <div class="flex items-center px-4 h-full border-r border-[#dee2e6] font-semibold text-[0.9rem] text-[#6c757d]">
        <!-- Ícone do filtro -->
        <i class="fas fa-filter mr-2"></i>
        <span class="whitespace-nowrap">Filtrar</span>
        <!-- Ícone seta para baixo -->
        <i class="fas fa-chevron-down ml-2"></i>
      </div>

      <!-- Input: Nome do Restaurante -->
      <div class="flex items-center flex-grow h-full bg-transparent">
        <i class="fas fa-search ml-3 mr-2 text-[#adb5bd] text-[1.1rem]"></i>
        <input
          type="text"
          class="w-full h-full py-3 px-4 border-none outline-none bg-transparent text-base"
          placeholder="Cozinha, Restaurante, Localização ..."
        />
      </div>

      <!-- Botão: Procurar -->
      <!-- <button type="submit" class="h-[80%] !bg-[#095243] border-none py-2 px-6 font-bold text-[0.9rem] flex items-center justify-center mr-2 rounded-[0.5rem] text-white transition duration-300 !hover:bg-[#073b31]"> -->
      <button type="submit" class="btn search-button">
        PROCURAR
      </button>
    </form>
  </div>
</template>

<script setup>
</script>

<style scoped>

.search-button {
  background-color: #095243;
  border: none;
  height: 80%;
  padding: 0.5rem 1.5rem;
  font-weight: bold;
  font-size: 0.9rem;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 1rem;
  margin-right: 0.5rem;
  border-radius: 0.5rem;
  color: white;
  transition: background-color 0.3s ease;
}

</style>