<script setup>
import { ref } from 'vue'
import ProfileHeader from './Profile/ProfileHeader.vue'
import ProfileInfo from './Profile/ProfileInfo.vue'
import FavoriteRestaurants from './Profile/FavoriteRestaurants.vue'
import EditProfileModal from './Profile/EditProfileModal.vue'
import Footer from './Footer.vue'
import TopNav from './Layout/TopNav.vue'

const showModal = ref(false)

const openModal = () => {
  showModal.value = true
}

const closeModal = () => {
  showModal.value = false
}
</script>

<template>
  <div class="profile-wrapper">
    <TopNav />
    <ProfileHeader @edit="openModal" />
    <ProfileInfo />
    <FavoriteRestaurants />
    <Footer />

    <EditProfileModal v-if="showModal" @close="closeModal" />
  </div>
</template>
