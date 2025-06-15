<template>
  <teleport to="body">
    <div class="fixed inset-0 bg-black bg-opacity-50 flex flex-col items-center justify-center z-50">
      <div class="bg-white rounded-lg p-6 min-w-[700px] shadow-lg max-h-[90vh] overflow-y-auto">

        <div class="flex items-center justify-between mb-4">
          <span class="text-2xl font-semibold text-emerald-800">O que vai na sua mente?</span>
          <button @click="cancelar" class="text-2xl text-red-500 hover:text-red-800">
            <i class="fa-regular fa-circle-xmark"></i>
          </button>
        </div>
          
        <!-- Replies section -->
        <div v-if="loading" class="text-center py-12">
          <Spinner class="mx-auto mt-4 mb-1"/>    
        </div>
        
        <div v-else-if="replies.length === 0" class="text-center py-4 text-gray-500">
          Não existem respostas para esta avaliação. Seja o primeiro a responder!
        </div>
        
        <div v-else class="space-y-4 mb-6 max-h-[40vh] overflow-y-auto pr-2">
          <div v-for="reply in replies" :key="reply.id" class="bg-gray-50 p-4 rounded-lg border border-gray-200">
            <div class="flex items-center justify-between mb-2">
              <div class="flex items-center">
                <span class="font-medium text-emerald-800">{{ reply.username }}</span>
                <span class="text-gray-500 text-sm ml-2">{{ formatDate(reply.date) }}</span>
              </div>
            </div>
            <p class="text-gray-700 font-serif">{{ reply.text }}</p>
          </div>
          
          <div v-if="hasMoreReplies" class="text-center pt-2">
            <button 
              @click="loadMoreReplies" 
              class="text-emerald-600 hover:text-emerald-800 font-medium"
              :disabled="loadingMore"
            >
              <span v-if="!loadingMore">Carregar mais respostas</span>
              <span v-else>A carregar...</span>
            </button>
          </div>
        </div>

        <div class="flex flex-col items-start">
          <div class="mt-4 mb-4 w-full"></div>
            <label for="reviewText" class="block text-md text-emerald-800 mb-2 font-semibold">O que vai na sua mente?</label>
            <textarea
              id="reviewText"
              v-model="text"
              class="w-full border border-gray-300 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
              rows="4"
              placeholder="Partilhe o que lhe vai na alma...">
            </textarea>
          </div>
        
          <div class="mt-4 flex items-end gap-3 justify-end">
            <button 
              @click="submeter" 
              class="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
              :disabled="submitting"
            >
              {{ submitting ? 'A submeter...' : 'Submeter' }}
            </button>
          </div>

      </div>
    </div>
  </teleport>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ReviewService, ReplyService } from '../../services'
import { useAuthStore } from '../../stores/auth'
import Spinner from '../utils/Spinner.vue'

const reviewService = new ReviewService()
const replyService = new ReplyService()
const authStore = useAuthStore()

const props = defineProps({
  reviewId: {
    type: String,
    required: true
  }
})

const emit = defineEmits(['close', 'reply-submitted'])

// State variables
const text = ref('')
const replies = ref([])
const loading = ref(true)
const loadingMore = ref(false)
const submitting = ref(false)
const page = ref(1)
const pageSize = ref(10)
const hasMoreReplies = ref(false)

// Fetch replies when component is mounted
onMounted(async () => {
  await fetchReplies()
})

// Function to fetch replies
async function fetchReplies() {
  try {
    loading.value = true
    const fetchedReplies = await reviewService.getReviewReplies(props.reviewId)
    replies.value = fetchedReplies

    // For demonstration purposes, we're assuming there might be more replies
    // In a real implementation, this would depend on the API's pagination
    hasMoreReplies.value = fetchedReplies.length >= pageSize.value
  } catch (error) {
    console.error('Error fetching replies:', error)
  } finally {
    loading.value = false
  }
}

// Function to load more replies (for infinite scroll)
async function loadMoreReplies() {
  try {
    loadingMore.value = true
    page.value++
    
    // In a real implementation, this would use pagination params
    // This is a placeholder for demonstration
    const moreReplies = await reviewService.getReviewReplies(props.reviewId)
    
    if (moreReplies.length === 0) {
      hasMoreReplies.value = false
    } else {
      replies.value = [...replies.value, ...moreReplies]
    }
  } catch (error) {
    console.error('Error loading more replies:', error)
  } finally {
    loadingMore.value = false
  }
}

// Format date for display
function formatDate(dateString) {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleDateString('pt-PT', {
    year: 'numeric',
    month: 'long', 
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

function cancelar() {
  emit('close')
}

async function submeter() {
  if (!text.value.trim()) return
  
  try {
    submitting.value = true
    
    const replyData = {
      text: text.value,
      userId: authStore.user?.id,
      reviewId: props.reviewId
    }
    
    await replyService.createReply(replyData)
    text.value = ''
    
    // Refresh replies list
    await fetchReplies()
    
    emit('reply-submitted')
  } catch (error) {
    console.error('Error submitting reply:', error)
  } finally {
    submitting.value = false
  }
}
</script>

