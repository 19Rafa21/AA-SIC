<template>
    <nav class="top-navbar">
      <div class="navbar-content">
        <div
          v-if="isLoggedIn"
          class="fixed top-1 right-4 z-50"
          @mouseenter="showDropdown = true"
          @mouseleave="showDropdown = false"
        >
          <!-- Avatar -->
          <img
            src="/img/avatar.png"
            alt="Avatar"
            class="user-avatar"
          />

          <!-- Dropdown -->
          <div
            v-show="showDropdown"
            class="absolute right-0 mt-2 w-48 bg-white border rounded-lg shadow-lg z-50"
          >
          <!-- Seta -->
          <div class="absolute top-[-8px] right-4 w-0 h-0 border-l-8 border-r-8 border-b-8 border-transparent border-b-white"></div>

            <ul class="py-2">
              <li>
                <router-link
                  to="/profile"
                  class="block px-4 py-2 hover:bg-gray-100 text-gray-700"
                  @click="closeDropdown"
                >
                  Ver Perfil
                </router-link>
              </li>
              <li>
                <button
                  class="block w-full text-left px-4 py-2 hover:bg-gray-100 text-gray-700"
                  @click="logout"
                >
                  Terminar Sessão
                </button>
              </li>
            </ul>

          </div>
        </div>

        <router-link
          v-else
          to="/login"
          class="login-button"
        >
          Iniciar Sessão
        </router-link>
      </div>
    </nav>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { useRouter } from 'vue-router'
import { AuthService } from '@/services'

const isLoggedIn = ref(false)
const showDropdown = ref(false)
const router = useRouter()

const toggleDropdown = () => {
  showDropdown.value = !showDropdown.value
}

const closeDropdown = () => {
  showDropdown.value = false
}

const logout = async () => {
  try {
    await AuthService.logout()
    closeDropdown()
    router.push('/')
    setTimeout(() => location.reload(), 100)
  } catch (error) {
    console.error('Erro ao terminar sessão:', error)
    // Fallback in case the API call fails
    localStorage.removeItem('isLoggedIn')
    localStorage.removeItem('user')
    closeDropdown()
    router.push('/')
  }
}

// Fecha dropdown se clicar fora
const handleClickOutside = (event) => {
  const dropdown = document.getElementById('user-dropdown')
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
})
</script>


<style scoped>

.dropdown-menu {
  position: absolute;
  top: 100%;
  right: 0;         /* menu aparece para a esquerda */
  left: auto;
  margin-top: 10px;
  background: white;
  border-radius: 6px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  z-index: 1000;
  padding: 0.5rem;
}

/* seta (bico) */
.dropdown-menu::before {
  content: "";
  position: absolute;
  top: -8px; /* sobe a seta para colar ao avatar */
  right: 12px; /* ajusta horizontalmente para alinhar com o avatar */
  width: 0;
  height: 0;
  border-left: 8px solid transparent;
  border-right: 8px solid transparent;
  border-bottom: 8px solid white; /* mesma cor do fundo */
}

.top-navbar {
  position: absolute;
  top: 0;
  width: 100%;
  z-index: 10;
  background-color: transparent;
}

.navbar-content {
  display: flex;
  justify-content: flex-end;
  padding: 0.75rem; /* equivalente a p-3 */
}

.login-button {
  position: fixed !important; 
  background-color: #095243;
  color: white;
  font-weight: bold;
  font-size: 0.75rem; /* text-xs */
  /* padding: 8px 24px; */
  border-radius: 0.5rem;
  transition: background-color 0.3s;
  width: 157px;
  text-align: center;
  top: 0px;
  right: 0px;
  z-index: 1000;
}

.login-button:hover {
  background-color: #073b31;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;         /* círculo */
  border: 2px solid white;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
  object-fit: cover;
}



</style>
