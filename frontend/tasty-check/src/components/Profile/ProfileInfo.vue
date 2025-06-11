<script setup>
const user = JSON.parse(localStorage.getItem('user') || '{}')
</script>

<template>
  <div class="bg-gray-100 rounded-xl p-6 flex gap-8">
    <img :src="user.avatar" alt="Avatar" class="w-40 h-40 rounded-xl object-cover" />

    <div class="grid grid-cols-2 gap-y-3 text-sm flex-1">
      <div><strong>Nome Completo</strong><div>{{ user.name }}</div></div>
      <div><strong>Data de Nascimento</strong><div>19/06/1997</div></div>
      <div><strong>País</strong><div>Estados Unidos</div></div>
      <div><strong>Contacto</strong><div>+91 8003936321</div></div>
      <div class="col-span-2"><strong>Email</strong><div>{{ user.email }}</div></div>
    </div>
  </div>
</template>
