<script setup>
import { ref, onMounted } from 'vue'
import Spinner from '../utils/Spinner.vue'
import { useAuthStore } from '@/stores/auth'
import { UserService, ImageService } from '@/services'
import UserDTO from '@/dto/user.dto.js'

const emit = defineEmits(['close', 'profileUpdated'])
const isSaving = ref(false)
const authStore = useAuthStore()
const userService = new UserService()
const imageService = new ImageService()

const user = ref(authStore.user ? { ...authStore.user } : {})
const selectedFile = ref(null)
const previewImage = ref(null)

onMounted(async () => {
  // Carregar a imagem atual do usuário, se existir
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
  isSaving.value = true
  try {
    // Se temos um arquivo de imagem selecionado, primeiro enviamos a imagem
    if (selectedFile.value) {
      try {
        // Criar objeto userData para enviar com a imagem
        const userData = {
          username: user.value.username,
          email: user.value.email,
          password: "", // Se necessário, pode ser adicionado
          discriminator: user.value.discriminator
        }
        
        // Enviar a imagem
        const imageResponse = await imageService.uploadImage(selectedFile.value, userData)
        
        // Se o upload for bem-sucedido, atualizamos o imageName no objeto do usuário
        if (imageResponse && imageResponse.imageName) {
          user.value.imageName = imageResponse.imageName
        }
      } catch (imageError) {
        console.error('Erro ao fazer upload da imagem:', imageError)
        alert('Erro ao enviar a imagem. O perfil será atualizado sem a nova imagem.')
      }
    }
    
    // Agora atualizamos o perfil do usuário
    const updatedUser = await userService.updateUser(UserDTO.toAPI(user.value))
    
    // Atualiza o estado da aplicação
    if (updatedUser) {
      // Atualizar o usuário na store
      authStore.user = { ...user.value }
      
      // Notificar que o perfil foi atualizado
      emit('profileUpdated', user.value)
      
      // Fechar o modal
      emit('close')
      
      // Opcional: recarregar a página para refletir todas as alterações
      location.reload()
    }
  } catch (err) {
    console.error('Erro ao guardar alterações:', err)
    alert('Erro ao atualizar perfil. Tente novamente.')
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
  
  // Cria uma prévia da imagem
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
          :src="previewImage || '/img/avatar.jpg'"
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
