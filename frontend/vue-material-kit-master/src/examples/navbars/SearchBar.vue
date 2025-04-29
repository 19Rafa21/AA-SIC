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
        <button type="submit" class="btn btn-success search-button">
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
  background: white;
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
}

  .search-bar-container {
    width: 100%;
    max-width: 800px;
    margin: 0 auto;
  }
  
  .search-bar {
    width: 100%;
    background: white;
    border: 1px solid #ced4da;
    border-radius: 0.75rem;
    overflow: hidden;
    padding: 0;
    height: 55px; /* ⚡ altura fixa para toda a barra */
    display: flex;
    align-items: center;
    }


  
  .filter-field {
    background-color: #f8f9fa;
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
  }
  
  .search-input::placeholder {
    color: #adb5bd;
  }
  
  .search-button {
  height: 80%; /* ⚡️ botão com a mesma altura da search bar */
  padding: 0.5rem 1.5rem; /* ⚡️ padding cima/baixo para dar espaço interno ao texto */
  font-weight: bold;
  font-size: 0.9rem;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 1rem;
  margin-right: 0.5rem; /* espaço à direita */
  border-radius: 0.5rem;
}



  </style>
  