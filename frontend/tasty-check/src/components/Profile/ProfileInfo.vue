<script setup>
const originalUser = JSON.parse(localStorage.getItem('user') || '{}')
const user = ref({ ...originalUser })

import { ref, watch, computed } from 'vue'

// Recebe prop "editable"
const props = defineProps({
  editable: Boolean
})

watch(() => props.editable, (isEditing) => {
  if (!isEditing) {
    user.value = { ...JSON.parse(localStorage.getItem('user') || '{}') }
  }
})

</script>

<template>
  <div class="bg-gray-100 rounded-xl p-6 flex gap-8">
    <img src="/img/avatar.jpg" alt="Avatar" class="w-20 rounded-xl object-cover" />

    <div class="grid grid-cols-2 gap-y-3 text-sm flex-1">
      <!-- <div><strong>Nome Completo</strong><div>{{ user.name }}</div></div>
      <div><strong>Data de Nascimento</strong><div>{{ user.birthdate || '—' }}</div></div>
      <div><strong>País</strong><div>{{ user.country || '—' }}</div></div>
      <div><strong>Contacto</strong><div>{{ user.phone || '—' }}</div></div> -->
      <div class="col-span-2"><strong>Email</strong><div>{{ user.email }}</div></div>
    </div>
  </div>
</template>
