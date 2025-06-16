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
import { useAuthStore } from '@/stores/auth'
import { UserService, ImageService } from '@/services'
import UserDTO from '@/dto/user.dto.js'

const showModal = ref(false)
const user = ref(null)
const isLoading = ref(true)
const error = ref(null)
const authStore = useAuthStore()
const userService = new UserService()
const imageService = new ImageService()
const profileImage = ref(null)
const refreshOwned = ref(0)

const loadProfile = async () => {
  isLoading.value = true
  try {
    const data = await userService.getCurrentUser()
    user.value = UserDTO.fromAPI(data)

    if (user.value.imageName) {
      try {
        const imageBlob = await imageService.getImage(user.value.imageName)
        profileImage.value = URL.createObjectURL(imageBlob)
      } catch (imageError) {
        console.error("Erro ao carregar imagem do perfil:", imageError)
      }
    }
  } catch (err) {
    console.warn("Falha ao atualizar perfil do backend.", err)
    error.value = "Não foi possível carregar todos os dados do perfil"
  } finally {
    isLoading.value = false
  }
}

onMounted(loadProfile)

const handleRefreshOwned = () => {
  loadProfile()
}



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
      <EditProfileModal
        v-if="showModal"
        @close="closeModal"
        @profileUpdated="loadProfile"
      />

      <ProfileInfo :profileImage="profileImage" :user="user" />
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <FavoriteRestaurants
          v-if="user && (user.discriminator === 'User' || user.discriminator === 'Owner')"
          :userId="user.id"
        />
        <OwnedRestaurants
          v-if="user.discriminator === 'Owner'"
          :refresh="refreshOwned"
        />
      </div>
    </div>

    <Footer class="bottom-0 left-0 right-0" />
    <EditRestaurantModal
      v-if="showModal"
      @close="closeModal"
      :key="refreshOwned"
    />

  </div>
</template>
