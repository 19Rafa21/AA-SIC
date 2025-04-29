<template>
    <div class="search-bar-container">
      <form class="search-bar d-flex">
        <!-- Filtro: Cozinha -->
        <div class="filter-field d-flex align-items-center px-4">
          <!-- Ícone do filtro -->
          <i class="fas fa-filter me-2"></i>
          <span class="filter-label">Filtrar</span>
          <!-- Ícone seta para baixo -->
          <i class="fas fa-chevron-down ms-2"></i>
        </div>
  
        <!-- Input: Nome do Restaurante -->
        <div class="input-with-icon d-flex align-items-center flex-grow-1">
          <i class="fas fa-search me-2 ms-3"></i>
          <input
            type="text"
            class="form-control search-input"
            placeholder="Cozinha, Restaurante, Localização ..."
          />
        </div>
  
        <!-- Botão: Procurar -->
        <button type="submit" class="btn search-button">
          PROCURAR
        </button>
      </form>
    </div>
  </template>
  
  
  <script setup>
  </script>
  
  <style scoped>
  
  .input-with-icon {
  height: 100%;
  background: transparent; /* 🔥 */
}

.input-with-icon i {
  color: #adb5bd;
  font-size: 1.1rem;
}

.input-with-icon input {
  border: none;
  outline: none;
  width: 100%;
  height: 100%;
  padding: 0.75rem 1rem;
  background: transparent; /* já tinhas e está certo */
}


  .search-bar-container {
    width: 100%;
    max-width: 800px;
    margin: 0 auto;
  }
  
  .search-bar {
  width: 100%;
  background: #e9ecef; /* 🔥 em vez de white, metemos um cinzento claro */
  border: 1px solid #ced4da;
  border-radius: 0.75rem;
  overflow: hidden;
  padding: 0;
  height: 55px;
  display: flex;
  align-items: center;
}
  
  .filter-field {
    background: transparent;
    border-right: 1px solid #dee2e6;
    font-weight: 600;
    font-size: 0.9rem;
    color: #6c757d;
    height: 100%;
  }
  
  .filter-label {
    white-space: nowrap;
  }
  
  .search-input {
    border: none;
    padding: 0.75rem 1rem;
    font-size: 1rem;
    width: 100%;
    height: 100%;
    outline: none;
    background: transparent; /* 🔥 fundo transparente para o input */
  }
  
  .search-input::placeholder {
    background: transparent;
    color: #adb5bd;
  }
  
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

.search-button:hover {
  background-color: #073b31; /* um verde escuro ao passar o rato */
}




  </style>
  