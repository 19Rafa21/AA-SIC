<template>
  <!-- Dropdown do utilizador (quando logado) -->
  <div
    v-if="isLoggedIn"
    class="fixed top-2 right-4 z-[9999]"
    @mouseenter="handleMouseEnter"
    @mouseleave="handleMouseLeave"
  >
   <div class="user-dropdown-container">
    <!-- Avatar redondo com hover -->
    <div class="user-avatar-wrapper">
      <img
        src="/img/avatar.jpg"
        alt="Avatar"
        class="user-avatar"
      />
      <div class="avatar-status-indicator"></div>
    </div>

    <!-- Dropdown moderno com animação -->
    <transition name="dropdown-fade">
      <div
        v-show="showDropdown"
        class="dropdown-menu-modern"
      >
        <!-- Seta melhorada -->
        <div class="dropdown-arrow-modern"></div>

        <div class="dropdown-content">
          <!-- Header do dropdown -->
          <div class="dropdown-header">
            <div class="user-info">
              <div class="user-name">{{ user.name }}</div>
              <div class="user-email">{{ user.email }}</div>
            </div>
          </div>

          <!-- Divisor -->
          <div class="dropdown-divider-modern"></div>

          <!-- Menu items -->
          <ul class="dropdown-menu-list">
            <li>
              <router-link
                to="/profile"
                class="dropdown-item-modern"
                @click="closeDropdown"
              >
                <svg class="dropdown-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
                </svg>
                Ver Perfil
              </router-link>
            </li>
            <li>
              <router-link
                to="/settings"
                class="dropdown-item-modern"
                @click="closeDropdown"
              >
                <svg class="dropdown-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z"></path>
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path>
                </svg>
                Definições
              </router-link>
            </li>
            <li>
              <router-link
                to="/help"
                class="dropdown-item-modern"
                @click="closeDropdown"
              >
                <svg class="dropdown-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8.228 9c.549-1.165 2.03-2 3.772-2 2.21 0 4 1.343 4 3 0 1.4-1.278 2.575-3.006 2.907-.542.104-.994.54-.994 1.093m0 3h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
                Ajuda
              </router-link>
            </li>
          </ul>

          <!-- Divisor -->
          <div class="dropdown-divider-modern"></div>

          <!-- Logout -->
          <div class="dropdown-footer">
            <button
              class="dropdown-item-modern logout-item"
              @click="logout"
            >
              <svg class="dropdown-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"></path>
              </svg>
              Terminar Sessão
            </button>
          </div>
        </div>
      </div>
    </transition>
  </div>
  </div>

  <!-- Botão de login (quando não logado) -->
  <router-link
    v-else
    to="/login"
    class="login-button fixed top-2 right-4 z-[9999]"
  >
    Iniciar Sessão
  </router-link>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { useRouter } from 'vue-router'

const isLoggedIn = ref(false)
const showDropdown = ref(false)
const router = useRouter()
let hoverTimeout = null

const handleMouseEnter = () => {
  if (hoverTimeout) {
    clearTimeout(hoverTimeout)
    hoverTimeout = null
  }
  showDropdown.value = true
}

const handleMouseLeave = () => {
  hoverTimeout = setTimeout(() => {
    showDropdown.value = false
  }, 150) // Pequeno delay para permitir movimento do rato
}

const closeDropdown = () => {
  showDropdown.value = false
  if (hoverTimeout) {
    clearTimeout(hoverTimeout)
    hoverTimeout = null
  }
}

const logout = () => {
  localStorage.removeItem('isLoggedIn')
  closeDropdown()
  isLoggedIn.value = false
  router.push('/')
}

// Fecha dropdown se clicar fora (backup para dispositivos touch)
const handleClickOutside = (event) => {
  const dropdown = document.querySelector('.user-dropdown-container')
  if (dropdown && !dropdown.contains(event.target)) {
    showDropdown.value = false
  }
}

onMounted(() => {
  isLoggedIn.value = localStorage.getItem('isLoggedIn') === 'true'
  document.addEventListener('click', handleClickOutside)
})

onBeforeUnmount(() => {
  document.removeEventListener('click', handleClickOutside)
  if (hoverTimeout) {
    clearTimeout(hoverTimeout)
  }
})

const originalUser = JSON.parse(localStorage.getItem('user') || '{}')
const user = ref({ ...originalUser })

import { watch } from 'vue'

// Recebe prop "editable"
const props = defineProps({
  editable: Boolean
})

watch(() => props.editable, (isEditing) => {
  if (!isEditing) {
    user.value = { ...JSON.parse(localStorage.getItem('user') || '{}') }
  }
})

</script>

<style scoped>
/* Botão de login original */
.login-button {
  background-color: #095243;
  color: white;
  font-weight: bold;
  font-size: 0.75rem;
  padding: 8px 24px;
  border-radius: 0.5rem;
  transition: background-color 0.3s;
  width: 157px;
  text-align: center;
  text-decoration: none;
}

.login-button:hover {
  background-color: #073b31;
}

/* Container do dropdown */
.user-dropdown-container {
  position: relative;
  display: inline-block;
}

/* Container do avatar */
.user-avatar-wrapper {
  position: relative;
  display: inline-block;
  cursor: pointer;
}

/* Avatar melhorado */
.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: 3px solid #ffffff;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  object-fit: cover;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.user-avatar-wrapper:hover .user-avatar {
  transform: scale(1.05);
  border-color: #10b981;
  box-shadow: 0 6px 20px rgba(16, 185, 129, 0.3);
}

/* Indicador de status online */
.avatar-status-indicator {
  position: absolute;
  bottom: 2px;
  right: 2px;
  width: 12px;
  height: 12px;
  background: linear-gradient(135deg, #10b981, #059669);
  border: 2px solid white;
  border-radius: 50%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* Dropdown moderno */
.dropdown-menu-modern {
  position: absolute;
  top: 100%;
  right: 0;
  margin-top: 8px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(16, 185, 129, 0.2);
  border-radius: 16px;
  width: 280px;
  box-shadow: 
    0 20px 25px -5px rgba(0, 0, 0, 0.1),
    0 10px 10px -5px rgba(0, 0, 0, 0.04),
    0 0 0 1px rgba(16, 185, 129, 0.05);
  z-index: 50;
  overflow: hidden;
}

/* Seta melhorada */
.dropdown-arrow-modern {
  position: absolute;
  top: -6px;
  right: 20px;
  width: 12px;
  height: 12px;
  background: rgba(255, 255, 255, 0.95);
  border: 1px solid rgba(16, 185, 129, 0.2);
  border-bottom: none;
  border-right: none;
  transform: rotate(45deg);
  backdrop-filter: blur(20px);
}

/* Conteúdo do dropdown */
.dropdown-content {
  padding: 0;
}

/* Header do dropdown */
.dropdown-header {
  padding: 16px 20px 12px;
  background: linear-gradient(135deg, #f0fdf4, #ecfdf5);
  border-bottom: 1px solid rgba(16, 185, 129, 0.1);
}

.user-info {
  text-align: left;
}

.user-name {
  font-weight: 600;
  font-size: 0.95rem;
  color: #065f46;
  margin-bottom: 2px;
}

.user-email {
  font-size: 0.8rem;
  color: #6b7280;
}

/* Lista de menu */
.dropdown-menu-list {
  list-style: none;
  margin: 0;
  padding: 8px 0;
}

/* Items do menu */
.dropdown-item-modern {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 12px 20px;
  text-align: left;
  color: #374151;
  font-size: 0.9rem;
  font-weight: 500;
  background-color: transparent;
  border: none;
  cursor: pointer;
  text-decoration: none;
  transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
}

.dropdown-item-modern:hover {
  background: linear-gradient(90deg, rgba(16, 185, 129, 0.08), rgba(16, 185, 129, 0.04));
  color: #065f46;
  transform: translateX(4px);
}

.dropdown-item-modern:hover::before {
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  width: 3px;
  background: linear-gradient(180deg, #10b981, #059669);
  border-radius: 0 2px 2px 0;
}

/* Ícones */
.dropdown-icon {
  width: 18px;
  height: 18px;
  margin-right: 12px;
  color: #6b7280;
  transition: color 0.2s ease;
}

.dropdown-item-modern:hover .dropdown-icon {
  color: #10b981;
}

/* Divisor moderno */
.dropdown-divider-modern {
  height: 1px;
  background: linear-gradient(90deg, transparent, rgba(16, 185, 129, 0.2), transparent);
  margin: 8px 20px;
}

/* Footer do dropdown */
.dropdown-footer {
  padding: 8px 0 12px;
  background: rgba(249, 250, 251, 0.8);
}

/* Item de logout */
.logout-item {
  color: #dc2626 !important;
}

.logout-item:hover {
  background: linear-gradient(90deg, rgba(220, 38, 38, 0.08), rgba(220, 38, 38, 0.04)) !important;
  color: #991b1b !important;
}

.logout-item:hover .dropdown-icon {
  color: #dc2626 !important;
}

.logout-item:hover::before {
  background: linear-gradient(180deg, #dc2626, #991b1b) !important;
}

/* Animações */
.dropdown-fade-enter-active,
.dropdown-fade-leave-active {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.dropdown-fade-enter-from {
  opacity: 0;
  transform: translateY(-10px) scale(0.95);
}

.dropdown-fade-leave-to {
  opacity: 0;
  transform: translateY(-5px) scale(0.98);
}



/* Responsividade */
@media (max-width: 640px) {
  .dropdown-menu-modern {
    width: 260px;
    right: -10px;
  }
  
  .user-avatar {
    width: 40px;
    height: 40px;
  }
  
  .dropdown-item-modern {
    padding: 10px 16px;
    font-size: 0.85rem;
  }
  
  .dropdown-header {
    padding: 14px 16px 10px;
  }
}

/* Melhorias para acessibilidade */
@media (prefers-reduced-motion: reduce) {
  .user-avatar,
  .dropdown-item-modern,
  .dropdown-fade-enter-active,
  .dropdown-fade-leave-active {
    transition: none;
  }
}

/* Tema escuro (opcional) */
@media (prefers-color-scheme: dark) {
  .dropdown-menu-modern {
    background: rgba(17, 24, 39, 0.95);
    border-color: rgba(16, 185, 129, 0.3);
  }
  
  .dropdown-arrow-modern {
    background: rgba(17, 24, 39, 0.95);
    border-color: rgba(16, 185, 129, 0.3);
  }
  
  .dropdown-header {
    background: linear-gradient(135deg, rgba(6, 95, 70, 0.2), rgba(6, 95, 70, 0.1));
  }
  
  .user-name {
    color: #10b981;
  }
  
  .user-email {
    color: #9ca3af;
  }
  
  .dropdown-item-modern {
    color: #f3f4f6;
  }
  
  .dropdown-item-modern:hover {
    color: #10b981;
  }
  
  .dropdown-footer {
    background: rgba(31, 41, 55, 0.8);
  }
}
</style>

