<script setup>
import { ref } from 'vue'

const email = ref('')
const password = ref('')

const handleLogin = () => {
  console.log('Login com:', email.value, password.value)
}
</script>

<template>
  <form @submit.prevent="handleLogin" class="flex flex-col space-y-4">
    <div>
      <label class="block text-sm font-medium text-gray-700">Email</label>
      <input type="email" v-model="email" required
        class="w-full mt-1 px-4 py-2 border rounded-md focus:ring-[#095243] focus:border-[#095243]" />
    </div>

    <div>
      <label class="block text-sm font-medium text-gray-700">Password</label>
      <input type="password" v-model="password" required
        class="w-full mt-1 px-4 py-2 border rounded-md focus:ring-[#095243] focus:border-[#095243]" />
    </div>

    <button type="submit"
      class="w-full bg-[#095243] hover:bg-[#073b31] text-white font-semibold py-2 rounded-md transition duration-200">
      Entrar
    </button>
  </form>
</template>
