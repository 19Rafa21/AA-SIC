<script setup>
import { ref } from 'vue'
const emit = defineEmits(['close'])

const user = ref(JSON.parse(localStorage.getItem('user') || '{}'))

const guardar = () => {
  localStorage.setItem('user', JSON.stringify(user.value))
  location.reload() // força refresh da página
}

const cancelar = () => {
  emit('close')
}

const handleImageUpload = (event) => {
  const file = event.target.files[0]
  if (!file) return

  const reader = new FileReader()
  reader.onload = () => {
    user.value.avatar = reader.result // imagem em base64
  }
  reader.readAsDataURL(file)
}

</script>

<template>
  <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
    <div class="bg-white rounded-lg p-6 w-full max-w-xl shadow-lg">
      <h2 class="text-2xl font-bold mb-4">Editar Perfil</h2>

    <div class="flex flex-col items-center mb-6">
        <img
            :src="user.avatar || '/img/default-avatar.png'"
            alt="Avatar"
            class="w-28 h-28 rounded-xl object-cover mb-2"
        />
        <input type="file" accept="image/*" @change="handleImageUpload" />
    </div>


      <div class="grid grid-cols-2 gap-4 text-sm">
        <div>
          <label class="block font-semibold">Nome</label>
          <input v-model="user.name" type="text" class="input-style" />
        </div>

        <div>
          <label class="block font-semibold">Data de Nascimento</label>
          <input v-model="user.birthdate" type="date" class="input-style" />
        </div>

        <div>
          <label class="block font-semibold">País</label>
          <input v-model="user.country" type="text" class="input-style" />
        </div>

        <div>
          <label class="block font-semibold">Contacto</label>
          <input v-model="user.phone" type="text" class="input-style" />
        </div>

        <div class="col-span-2">
          <label class="block font-semibold">Email</label>
          <input v-model="user.email" type="email" class="input-style" />
        </div>
      </div>

      <div class="mt-6 flex justify-end gap-3">
        <button @click="cancelar" class="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400">Cancelar</button>
        <button @click="guardar" class="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700">Guardar</button>
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
</style>
