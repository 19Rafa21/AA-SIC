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
          
        <!-- Rating  -->
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

          <!-- Mensagem de erro do rating -->
          <span v-if="ratingError" class="text-sm text-red-600 mt-1">{{ ratingError }}</span>

          <!-- Texto -->
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

          <!-- Erro Texto -->
          <span v-if="textError" class="text-sm text-red-600 mt-1">{{ textError }}</span>
        
          <div class="mt-4 flex items-end gap-3 justify-end">
            <button @click="submeter" class="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700">
              Submeter
            </button>
          </div>

          <div v-if="errorMessage" class="alert alert-danger py-2 text-sm text-center text-white mt-4">
              {{ errorMessage }}
          </div>

      </div>
    </div>
  </teleport>
</template>

<script setup>
import { ref } from 'vue'
import { ReviewService } from '@/services'
import { useAuthStore } from '@/stores/auth'
import { ReviewDTO } from '@/dto/review.dto'

const props = defineProps({
  restaurantId: {
    type: [String, Number],
    required: true
  }
})

const emit = defineEmits(['close', 'review-submitted'])
const ReviewServiceInstance = new ReviewService()
const authStore = useAuthStore()
const rating = ref(0)
const text = ref('')
const loading = ref(false)
const errorMessage = ref(null)
const ratingError = ref('')
const textError   = ref('')

function setRating(value) {
  rating.value = rating.value === value ? value - 1 : value
  ratingError.value = '' 
}


function cancelar() {
  emit('close')
}

async function submeter() {
  ratingError.value = ''
  textError.value   = ''
  errorMessage.value = null

  let hasError = false
  if (rating.value === 0) {
    ratingError.value = 'Por favor, selecione uma classificação.'
    hasError = true
  }
  if (!text.value.trim()) {
    textError.value = 'O comentário não pode estar vazio.'
    hasError = true
  }
  if (hasError) return  
  
  loading.value = true;
  try {
    // Check if user is logged in
    if (!authStore.isAuthenticated || !authStore.currentUser) {
      errorMessage.value = "Deve iniciar sessão para submeter uma avaliação.";
      loading.value = false;
      return;
    }
    
    const review = new ReviewDTO({
      restaurantId: props.restaurantId,
      userId: authStore.currentUser.id,
      rating: rating.value,
      text: text.value
    });
    
    const createdReview = await ReviewServiceInstance.createReview(review);
    
    // Emit the review-submitted event with the created review data
    emit('review-submitted', createdReview);
    
    // Reset form state
    rating.value = 0;
    text.value   = '';
    errorMessage.value = null;
    
    // Close the modal
    emit('close');
  } 
  catch (err) {
    console.error('Error submitting review:', err);
    errorMessage.value = err.response?.data?.message || err.message || "Ocorreu um erro ao submeter a avaliação.";
  } 
  finally {
    loading.value = false;
  }
}
</script>

