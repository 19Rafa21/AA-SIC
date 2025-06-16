<script setup>
import { ref, onMounted } from 'vue'
import Spinner from '../utils/Spinner.vue'
import { useAuthStore } from '@/stores/auth'
import authService from '@/services/auth.service' // usamos o authService diretamente
import { ImageService } from '@/services'

const emit = defineEmits(['close', 'profileUpdated'])
const isSaving = ref(false)
const erro = ref(null)
const authStore = useAuthStore()
const imageService = new ImageService()

const user = ref(authStore.user ? { ...authStore.user } : {})
const selectedFile = ref(null)
const previewImage = ref(null)

onMounted(async () => {
  // Carregar imagem de perfil se existir
  try {
    if (user.value.imageName) {
      const imageBlob = await imageService.getImage(user.value.imageName)
      previewImage.value = URL.createObjectURL(imageBlob)
    }
  } catch (err) {
    console.error('Erro ao carregar imagem do perfil:', err)
  }
})

const guardar = async () => {
  erro.value = null
  isSaving.value = true

  try {
    const userId = user.value.id // ou _id, dependendo do nome da propriedade no authStore

    const updatedUserData = {
      username: user.value.username,
      email: user.value.email,
      discriminator: user.value.discriminator
    }

    // Chamar a função do auth.service.js
    await authService.updateUser(userId, updatedUserData, selectedFile.value)

    emit('profileUpdated') // notificar componente pai
    window.location.reload()
  } catch (err) {
    console.error('Erro ao atualizar perfil:', err)
    erro.value = 'Erro ao guardar alterações. Tenta novamente.'
  } finally {
    isSaving.value = false
  }
}

const cancelar = () => {
  emit('close')
}

const handleImageUpload = (event) => {
  const file = event.target.files[0]
  if (!file) return

  selectedFile.value = file
  previewImage.value = URL.createObjectURL(file)
}
</script>


<template>
  <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
    <div class="relative bg-white rounded-lg p-6 w-full max-w-xl shadow-lg">
      <h2 class="text-2xl font-bold mb-4">Editar Perfil</h2>
      <div class="absolute top-4 right-4">
        <button @click="cancelar" class="text-2xl text-red-500 hover:text-red-800">
          <i class="fa-regular fa-circle-xmark"></i>
        </button>
      </div>
      <div class="flex flex-col items-center mb-6">
        <img
          :src="previewImage || '/img/avatar.png'"
          alt="Avatar"
          class="w-28 h-28 rounded-xl object-cover mb-2"
        />
        <input type="file" accept="image/*" @change="handleImageUpload" />
      </div>

      <div class="grid grid-cols-1 gap-4 text-sm">
        <div>
          <label class="block font-semibold">Nome de Usuário</label>
          <input v-model="user.username" type="text" class="input-style" />
        </div>

        <div>
          <label class="block font-semibold">Email</label>
          <input v-model="user.email" type="email" class="input-style" />
        </div>

        <!-- <div>
          <label class="block font-semibold">Password</label>
          <input v-model="user.password" type="text" class="input-style" />
        </div> -->


      </div>

      <div class="mt-6 flex justify-end gap-3 items-center">
        <Spinner v-if="isSaving" class="mx-auto mt-4 mb-1" />
        <span v-if="isSaving" class="text-emerald-500 mr-2">A guardar...</span>
        <p v-if="erro" class="text-red-500 text-sm mt-4">{{ erro }}</p>
        <button @click="cancelar" class="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400" :disabled="isSaving">Cancelar</button>
        <button @click="guardar" class="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700" :disabled="isSaving">Guardar</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.input-style {
  background-color: white;
  border: 1px solid #ccc;
  padding: 6px 10px;
  border-radius: 0.375rem;
  width: 100%;
  font-size: 0.875rem;
}

input[type="file"] {
  margin-top: 6px;
  font-size: 0.75rem;
}
</style>