<script setup>
import { ref, onMounted, computed } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { ImageService } from '@/services'

const authStore = useAuthStore()
const imageService = new ImageService()

// Props opcionais
const props = defineProps({
  editable: Boolean,
  profileImage: String,
  user: Object
})

// Preferência: user vindo por prop > authStore
const userData = computed(() => {
  return props.user || authStore.user || {}
})

// Avatar por defeito
const defaultAvatar = '../public/img/avatar.png'
const profileImageSrc = ref(defaultAvatar)

onMounted(async () => {
  // Se for passada uma imagem por prop, usá-la
  if (props.profileImage && props.profileImage.trim() !== '') {
    profileImageSrc.value = props.profileImage
    return
  }

  // Caso contrário, tenta buscar pelo nome da imagem do utilizador
  const imageName = userData.value?.imageName
  if (!imageName) return

  try {
    const blob = await imageService.getImage(imageName)
    if (blob) {
      profileImageSrc.value = URL.createObjectURL(blob)
    }
  } catch (err) {
    console.error('Erro ao carregar imagem de perfil:', err)
    profileImageSrc.value = defaultAvatar
  }
})
</script>


<template>
  <div class="bg-gray-100 rounded-xl p-6 flex gap-8">
      <img 
        :src="profileImageSrc"
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
