<script setup>
import { ref, watch, computed } from 'vue'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()

// Recebe props "editable", "profileImage" e "user"
const props = defineProps({
  editable: Boolean,
  profileImage: String,
  user: Object
})

// Fallback to store if no user is provided via props
const userData = computed(() => {
  return props.user || authStore.user || {}
})

const defaultAvatar = '/img/avatar.jpg'
</script>

<template>
  <div class="bg-gray-100 rounded-xl p-6 flex gap-8">
    <img 
      :src="profileImage && profileImage.trim() !== '' ? profileImage : defaultAvatar"
      alt="Avatar" 
      class="w-20 rounded-xl object-cover" 
    />

    <div class="grid grid-cols-2 gap-y-3 text-sm flex-1">
      <div><strong>Nome de Usuário</strong><div>{{ userData.username }}</div></div>
      <div><strong>Email</strong><div>{{ userData.email }}</div></div>
      
      <div v-if="userData.birthDate"><strong>Data de Nascimento</strong><div>{{ userData.birthDate }}</div></div>
      <div v-if="userData.country"><strong>País</strong><div>{{ userData.country }}</div></div>
      <div v-if="userData.contact"><strong>Contacto</strong><div>{{ userData.contact }}</div></div>
    </div>
  </div>
</template>
