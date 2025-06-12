<script setup>
import { onMounted, ref } from 'vue'

const emit = defineEmits(['edit'])
const user = ref({})

onMounted(() => {
  const stored = localStorage.getItem('user')
  user.value = stored ? JSON.parse(stored) : {}
})
</script>

<template>
  <div class="flex justify-between items-center mt-3 mb-3 px-6">
    <h2 class="text-3xl font-bold">{{ user.name || 'Utilizador' }}</h2>
    <button @click="emit('edit')" class="login-button flex items-center gap-2">
      <i class="fas fa-pen"></i>
      Editar
    </button>
  </div>
</template>

<style scoped>
.login-button {
  background-color: #095243;
  color: white;
  font-weight: bold;
  font-size: 0.75rem;
  padding: 8px 24px;
  border-radius: 0.5rem;
  transition: background-color 0.3s;
  height: 38px;
}

.login-button:hover {
  background-color: #073b31;
}
</style>
