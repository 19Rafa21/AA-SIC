<template>
    <nav class="top-navbar">
      <div class="container-fluid d-flex justify-content-end p-3">
        <a href="/login" class="btn iniciar-sessao-button">Iniciar Sessão</a>
      </div>
    </nav>
  </template>
  
  <script setup>
  </script>
  
  <style scoped>
  .top-navbar {
    position: absolute;
    top: 0;
    width: 100%;
    z-index: 10;
    background: transparent;
  }
  
  .iniciar-sessao-button {
    background-color: #095243;
    border: none;
    padding: 0.5rem 1.5rem;
    font-weight: bold;
    font-size: 0.9rem;
    color: white;
    border-radius: 0.5rem;
    transition: background-color 0.3s ease;
  }
  
  .iniciar-sessao-button:hover {
    background-color: #073b31;
  }
  </style>
  