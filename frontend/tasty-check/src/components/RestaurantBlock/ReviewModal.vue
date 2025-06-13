<template>
  <teleport to="body">
    <div class="fixed inset-0 bg-black bg-opacity-50 flex flex-col items-center justify-center z-50">
      <div class="bg-white rounded-lg p-6 min-w-[700px] shadow-lg">

        <div class="flex items-center justify-between mb-4">
          <span class="text-2xl font-semibold text-emerald-800">Rating & FeedBack</span>
          <button @click="cancelar" class="text-2xl text-red-500 hover:text-red-800">
            <i class="fa-regular fa-circle-xmark"></i>
          </button>
        </div>
          
        <div class="flex flex-col items-start">
          <span class="text-md text-emerald-800 mb-3">A minha experiência foi boa</span>

          <div class="flex items-center space-x-1"> 
            <button v-for="star in 5" :key="star" @click="setRating(star)"
                    :class="[
                      'text-2xl',
                      star <= rating ? 'text-emerald-800 hover:text-emerald-600' : 'text-emerald-800 hover:text-yellow-400'
                    ]"> 
              <i :class="star <= rating ? 'fas fa-star' : 'far fa-star'"></i> 
            </button> 
          </div>

          
          <div class="mt-4 mb-4 w-full"></div>
            <label for="reviewText" class="block text-md text-emerald-800 mb-2">O que o impressionou?</label>
            <textarea
              id="reviewText"
              v-model="text"
              class="w-full border border-gray-300 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
              rows="4"
              placeholder="Partilhe a sua experiência com este restaurante...">
            </textarea>
          </div>
        
          <div class="mt-4 flex items-end gap-3 justify-end">
            <button @click="submeter" class="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700">
              Submeter
            </button>
          </div>

      </div>
    </div>
  </teleport>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  restaurantId: {
    type: [String, Number],
    required: true
  }
})

const emit = defineEmits(['close', 'review-submitted'])

const rating = ref(0)

function setRating(value) {
  rating.value = rating.value === value ? value - 1 : value
}

const text = ref('')

function cancelar() {
  emit('close')
}

function submeter() {
  //to do 
  emit('close')
}
</script>

