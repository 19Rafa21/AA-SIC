<template>
  <div class="avatar-container">
    <!-- Dropdown do utilizador (quando logado) -->
    <div
      v-if="isLoggedIn"
      class="user-dropdown-container"
      @mouseenter="handleMouseEnter"
      @mouseleave="handleMouseLeave"
    >
      <!-- Avatar redondo com hover -->
      <div class="user-avatar-wrapper">
        <div v-if="avatarLoading" class="user-avatar">
          <Spinner class="mx-auto mr-2"/>
        </div>
        <img
          v-else
          :src="userAvatarSrc"
          alt="Avatar"
          class="user-avatar"
          @error="handleImageError"
        />
        <div class="avatar-status-indicator"></div>
      </div>

      <!-- Dropdown moderno com animação -->
      <transition name="dropdown-fade">
        <div
          v-show="showDropdown"
          class="dropdown-menu-modern"
        >
          <div class="dropdown-arrow-modern"></div>
          <div class="dropdown-content">
            <div class="dropdown-header">
              <div class="user-info">
                <div class="user-name">{{ user.userName }}</div>
                <div class="user-email">{{ user.email }}</div>
              </div>
            </div>
            <div class="dropdown-divider-modern"></div>
            <ul class="dropdown-menu-list">
              <li>
                <router-link to="/profile" class="dropdown-item-modern" @click="closeDropdown">
                  <svg class="dropdown-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
                  </svg>
                  Ver Perfil
                </router-link>
              </li>
              <li>
                <router-link to="/" class="dropdown-item-modern" @click="closeDropdown">
                  <svg class="dropdown-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-4 0h4"></path>
                  </svg>
                  Ir para Home
                </router-link>
              </li>
            </ul>
            <div class="dropdown-divider-modern"></div>
            <div class="dropdown-footer">
              <button class="dropdown-item-modern logout-item" @click="logout">
                <svg class="dropdown-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
                </svg>
                Terminar Sessão
              </button>
            </div>
          </div>
        </div>
      </transition>
    </div>

    <!-- Botão de login (quando não logado) -->
    <router-link v-else to="/login" class="login-button">
      Iniciar Sessão
    </router-link>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, watch } from 'vue'
import { useRouter } from 'vue-router'
import { AuthService, ImageService } from '@/services'
import Spinner from '@/components/utils/Spinner.vue'

const isLoggedIn = ref(false)
const showDropdown = ref(false)
const router = useRouter()
let hoverTimeout = null
const avatarLoading = ref(false)
const avatarError = ref(false)
const userAvatarSrc = ref('/img/avatar.png')

// Create an instance of the ImageService
const imageService = new ImageService()

// Handle mouse interactions for dropdown
const handleMouseEnter = () => {
  clearTimeout(hoverTimeout)
  showDropdown.value = true
}

const handleMouseLeave = () => {
  hoverTimeout = setTimeout(() => {
    showDropdown.value = false
  }, 150)
}

const closeDropdown = () => {
  showDropdown.value = false
  clearTimeout(hoverTimeout)
}

const logout = async () => {
  try {
    await AuthService.logout()
    closeDropdown()
    isLoggedIn.value = false
    router.push('/')
    setTimeout(() => location.reload(), 100)
  } catch (error) {
    console.error('Erro ao terminar sessão:', error)
    localStorage.removeItem('isLoggedIn')
    localStorage.removeItem('user')
    closeDropdown()
    isLoggedIn.value = false
    router.push('/')
  }
}

const handleClickOutside = (event) => {
  const dropdown = document.querySelector('.user-dropdown-container')
  if (dropdown && !dropdown.contains(event.target)) {
    showDropdown.value = false
  }
}

// Fetch user profile image using the ImageService
const fetchUserProfileImage = async () => {
  const userData = JSON.parse(localStorage.getItem('user') || '{}')
  
  if (!userData.id) {
    userAvatarSrc.value = '/img/avatar.png'
    return
  }
  
  // Check if user has an image name property
  if (!userData.imageName) {
    console.log('No image name found for user, using default avatar')
    userAvatarSrc.value = '/img/avatar.png'
    return
  }
  
  try {
    avatarLoading.value = true
    // Using imageName from user data
    // console.log('Fetching image with name:', userData.imageName)
    const imageBlob = await imageService.getImage(userData.imageName)
    if (imageBlob) {
      userAvatarSrc.value = URL.createObjectURL(imageBlob)
    }
  } catch (error) {
    console.error('Error loading profile image:', error)
    userAvatarSrc.value = '/img/avatar.png'
  } finally {
    avatarLoading.value = false
  }
}

// Handle image loading error
const handleImageError = () => {
  userAvatarSrc.value = '/img/avatar.png'
  avatarError.value = true
}

const originalUser = JSON.parse(localStorage.getItem('user') || '{}')
const user = ref({ ...originalUser })

const props = defineProps({
  editable: Boolean
})

watch(() => props.editable, (isEditing) => {
  if (!isEditing) {
    user.value = { ...JSON.parse(localStorage.getItem('user') || '{}') }
  }
})

// Initialize component
onMounted(() => {
  isLoggedIn.value = localStorage.getItem('isLoggedIn') === 'true'
  document.addEventListener('click', handleClickOutside)
  
  // Try to load the user's profile image
  fetchUserProfileImage()
})

onBeforeUnmount(() => {
  document.removeEventListener('click', handleClickOutside)
  clearTimeout(hoverTimeout)
})
</script>


<style scoped>
.avatar-container {
  position: absolute;
  right: 2rem;
  top: 50%;
  transform: translateY(-50%);
  z-index: 9999;
}

/* Botão de login - removido position fixed */
.login-button {
  background-color: #095243;
  color: white;
  font-weight: bold;
  font-size: 0.75rem;
  padding: 8px 24px;
  border-radius: 0.5rem;
  transition: background-color 0.3s;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  white-space: nowrap;
  border: 1px solid #1A2D29;
}

.login-button:hover {
  background-color: #073b31;
}

/* Container do dropdown - removido position fixed e top negativo */
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
  display: block;
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

/* Dropdown moderno - ajustado z-index */
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
  z-index: 1000;
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

/* Loading container styles */
.avatar-loading-container {
  position: absolute;
  top: 0;
  left: 0;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1;
}

.avatar-loading-container :deep(.animate-spin) {
  height: 24px !important;
  width: 24px !important;
}
</style>