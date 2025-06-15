<script setup>
import { ref, onMounted } from 'vue'
import ProfileHeader from './Profile/ProfileHeader.vue'
import ProfileInfo from './Profile/ProfileInfo.vue'
import FavoriteRestaurants from './Profile/FavoriteRestaurants.vue'
import OwnedRestaurants from './Profile/OwnedRestaurants.vue'
import EditProfileModal from './Profile/EditProfileModal.vue'
import Footer from './Footer.vue'
import TopNav from './Layout/TopNav.vue'
import Spinner from './utils/Spinner.vue'

const showModal = ref(false)
const user = ref(null)
const isLoading = ref(true)
const error = ref(null)

import UserService from '@/services/user.service.js'
import UserDTO from '@/dto/user.dto.js'
const userService = new UserService()

onMounted(async () => {
  isLoading.value = true
  const local = localStorage.getItem('user')
  if (local) {
    user.value = JSON.parse(local) // carregar rapidamente
  }

  try {
    const data = await userService.getCurrentUser()
    user.value = UserDTO.fromAPI(data) // atualiza com dados do backend
  } catch (err) {
    console.warn("Falha ao atualizar perfil do backend. A usar localStorage.")
    error.value = "Não foi possível carregar todos os dados do perfil"
  } finally {
    isLoading.value = false
  }
})


const openModal = () => (showModal.value = true)
const closeModal = () => (showModal.value = false)

</script>



<template>
  <div class="profile-wrapper">
    <TopNav />
    
    <div v-if="isLoading" class="text-center py-12">
      <Spinner class="mx-auto mt-4 mb-1"/>
      <span class="text-lg text-emerald-500">A carregar perfil...</span>
    </div>
    
    <div v-else-if="error" class="text-center py-12">
      <p class="text-lg text-red-500">{{ error }}</p>
      <button @click="$router.push('/')" class="mt-4 px-4 py-2 bg-green-600 text-white rounded">
        Voltar para a página inicial
      </button>
    </div>

    <div class="container mx-auto max-w-6xl px-6 mt-6" v-else-if="user">
      <ProfileHeader @edit="openModal" />
      <ProfileInfo />
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <FavoriteRestaurants
          v-if="user && (user.discriminator === 'User' || user.discriminator === 'Owner')"
          :userId="user.id"
        />
        <OwnedRestaurants v-if="user.discriminator === 'Owner'" />
      </div>
    </div>

    <Footer class="bottom-0 left-0 right-0" />
    <EditProfileModal v-if="showModal" @close="closeModal" />
  </div>
</template>
