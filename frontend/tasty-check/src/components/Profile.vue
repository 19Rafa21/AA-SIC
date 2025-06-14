<script setup>
import { ref, onMounted } from 'vue'
import ProfileHeader from './Profile/ProfileHeader.vue'
import ProfileInfo from './Profile/ProfileInfo.vue'
import FavoriteRestaurants from './Profile/FavoriteRestaurants.vue'
import OwnedRestaurants from './Profile/OwnedRestaurants.vue'
import EditProfileModal from './Profile/EditProfileModal.vue'
import Footer from './Footer.vue'
import TopNav from './Layout/TopNav.vue'

const showModal = ref(false)
const user = ref(null)

onMounted(() => {
  const local = localStorage.getItem('user')
  if (local) {
    user.value = JSON.parse(local)
  }
})

const openModal = () => (showModal.value = true)
const closeModal = () => (showModal.value = false)
</script>

<template>
  <div class="profile-wrapper">
    <TopNav />

    <div class="container mx-auto max-w-6xl px-6 mt-6" v-if="user">
      <ProfileHeader @edit="openModal" />
      <ProfileInfo />
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <FavoriteRestaurants v-if="user.role === 'cliente' || user.role === 'proprietario'" />
        <OwnedRestaurants v-if="user.role === 'proprietario'" />
      </div>
    </div>

    <Footer class="fixed bottom-0 left-0 right-0" />
    <EditProfileModal v-if="showModal" @close="closeModal" />
  </div>
</template>
