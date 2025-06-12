<template>
  <div class="mt-5 bg-gray-100 p-5 rounded-xl">
    <div class="flex justify-between items-center mb-4">
      <h2 class="text-2xl font-bold">Os Meus Restaurantes</h2>
      <button class="text-sm bg-gray-200 px-3 py-1 rounded hover:bg-gray-300">+ Adicionar</button>
    </div>

    <div class="flex gap-4 flex-wrap">
      <!-- Exemplo de restaurante -->
      <div class="w-30 relative group">
        <img src="/img/top100.png" class="w-full h-32 object-cover rounded-md mb-2" />

        <div class="text-xs text-gray-500">JAPANESE</div>
        <div class="font-semibold text-sm">Yakisake</div>

        <!-- Botão editar no estilo mockup -->
        <button
          class="absolute bottom-0 right-0 mb-1 mr-1 px-2 py-1 text-sm border border-gray-400 rounded flex items-center gap-1 bg-white hover:bg-gray-100"
        >
          <i class="fas fa-pen text-xs"></i>
          Editar
        </button>
      </div>
    </div>
  </div>
</template>
