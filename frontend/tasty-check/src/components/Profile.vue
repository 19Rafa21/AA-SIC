<script setup>
import ProfileHeader from './Profile/ProfileHeader.vue'
import ProfileInfo from './Profile/ProfileInfo.vue'
import FavoriteRestaurants from './Profile/FavoriteRestaurants.vue'
import Footer from './Footer.vue'
import TopNav from './Layout/TopNav.vue'
</script>

<template>
  <div class="profile-wrapper ">
    <TopNav />
    <ProfileHeader />
    <ProfileInfo />
    <FavoriteRestaurants />
    <Footer />
  </div>
</template>

<style scoped>

.profile-wrapper {
  width: 100%;
}


</style>
