<template>
  <div>
    <div v-if="safeImages.length === 0" class="flex flex-col items-center justify-center p-8 text-gray-500">
      <i class="fa-regular fa-image text-5xl mb-4"></i>
      <span class="text-lg">Sem imagens adicionadas</span>
    </div>
    <div v-else class="flex items-center">
      <button @click="prev" :disabled="!canPrev" class="p-2 text-gray-500 disabled:text-gray-300">
        <i class="fa-solid fa-chevron-left"></i>
      </button>
      <div class="grid grid-cols-4 gap-2">
        <img v-for="(img, index) in visibleImages" :key="index" :src="img"
             class="w-full h-32 object-cover rounded cursor-pointer"
             @click="openModal(img)" />
      </div>
      <button @click="next" :disabled="!canNext" class="p-2 text-gray-500 disabled:text-gray-300">
        <i class="fa-solid fa-chevron-right"></i>
      </button>
    </div>
    <div v-if="showModal" class="fixed inset-0 bg-black bg-opacity-75 flex items-center justify-center z-[1000]">
      <button @click="closeModal" class="absolute top-4 right-4 text-white text-2xl">
        <i class="fa-regular fa-circle-xmark"></i>
      </button>
      <img :src="currentImage" class="max-w-full max-h-full rounded" />
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
const props = defineProps({
  images: {
    type: Array,
    default: () => []
  }
})
const safeImages = computed(() =>
  Array.isArray(props.images) ? props.images : []
)

const start = ref(0)
const visibleImages = computed(() => safeImages.value.slice(start.value, start.value + 4))
const canPrev = computed(() => start.value > 0)
const canNext = computed(() => start.value + 4 < safeImages.value.length)
function prev() {
  if (canPrev.value) start.value -= 4
}
function next() {
  if (canNext.value) start.value += 4
}
const showModal = ref(false)
const currentImage = ref('')
function openModal(img) {
  currentImage.value = img
  showModal.value = true
}
function closeModal() {
  showModal.value = false
}
</script>
