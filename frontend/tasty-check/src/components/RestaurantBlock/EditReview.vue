<template>
  <teleport to="body">
    <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 overflow-auto">
      <div class="bg-white rounded-lg p-6 w-full max-w-2xl shadow-lg max-h-[90vh] overflow-y-auto">
        <div class="flex items-center justify-between mb-4">
          <span class="text-2xl font-semibold text-emerald-800">Editar Avaliação</span>
          <button @click="cancelar" class="text-2xl text-red-500 hover:text-red-800">
            <i class="fa-regular fa-circle-xmark"></i>
          </button>
        </div>

        <div class="space-y-4">
          <!-- Rating -->
          <div>
            <label class="block text-md font-medium text-emerald-800 mb-2">Classificação</label>
            <div class="flex items-center space-x-1">
              <button
                v-for="star in 5"
                :key="star"
                @click="setRating(star)"
                :class="['text-2xl', star <= rating ? 'text-emerald-800 hover:text-emerald-600' : 'text-gray-400 hover:text-yellow-400']"
              >
                <i :class="star <= rating ? 'fas fa-star' : 'far fa-star'"></i>
              </button>
            </div>
            <span v-if="ratingError" class="text-sm text-red-600 mt-1">{{ ratingError }}</span>
          </div>

          <!-- Texto -->
          <div>
            <label class="block text-md font-medium text-emerald-800 mb-2">Comentário</label>
            <textarea
              v-model="text"
              class="w-full border border-gray-300 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
              rows="4"
              placeholder="Partilhe a sua experiência...">
            </textarea>
            <span v-if="textError" class="text-sm text-red-600 mt-1">{{ textError }}</span>
          </div>

          <!-- Imagens -->
          <div>
            <label class="block text-md font-medium text-emerald-800 mb-2">Imagens</label>
            <input type="file" multiple accept="image/*" @change="handleImageUpload" />
            <div class="image-list mt-2">
              <div v-for="(img, i) in imageFiles" :key="i" class="image-thumb">
                <img :src="getObjectURL(img)" class="thumb" />
                <button type="button" class="remove-btn" @click="removeImage(i)">×</button>
              </div>
            </div>
          </div>

          <div class="mt-6 flex justify-end gap-3">
            <button @click="cancelar" class="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400">Cancelar</button>
            <button @click="guardar" class="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700">Guardar</button>
          </div>

          <div v-if="errorMessage" class="alert alert-danger py-2 text-sm text-center text-white mt-4 bg-red-500 rounded">
            {{ errorMessage }}
          </div>
        </div>
      </div>
    </div>
  </teleport>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { ReviewService, ImageService } from '@/services'
import { ReviewDTO } from '@/dto/review.dto'

const props = defineProps({
  review: Object, // review existente
})
const emit = defineEmits(['close', 'review-updated'])

const authStore = useAuthStore()
const reviewService = new ReviewService()
const imageService = new ImageService()

const rating = ref(0)
const text = ref('')
const imageFiles = ref([]) // blobs ou novos uploads
const errorMessage = ref(null)
const ratingError = ref('')
const textError = ref('')

const imagensOriginais = ref([])

onMounted(async () => {
  rating.value = props.review.rating || 0
  text.value = props.review.text || ''

  const imagens = props.review.reviewImages || []
  imagensOriginais.value = [...imagens]

  imageFiles.value = await Promise.all(
    imagens.map(async (imgName) => {
      try {
        const blob = await imageService.getImage(imgName)
        console.log('IMAGEM FETCHADA:', imgName, blob)
        const file = new File([blob], imgName, { type: blob.type || 'image/jpeg' })
        return Object.assign(file, { originalName: imgName })
      } catch (e) {
        console.warn('Erro a carregar imagem da review:', imgName, e)
        return null
      }
    })
  ).then(imgs => imgs.filter(Boolean))
})


function setRating(value) {
  rating.value = rating.value === value ? value - 1 : value
  ratingError.value = ''
}

function cancelar() {
  emit('close')
}

function handleImageUpload(event) {
  const files = Array.from(event.target.files)
  imageFiles.value.push(...files)
}

function removeImage(index) {
  imageFiles.value.splice(index, 1)
}

function getObjectURL(file) {
  return typeof file === 'string' ? file : URL.createObjectURL(file)
}

async function guardar() {
  errorMessage.value = ''
  ratingError.value = ''
  textError.value = ''

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

  try {
    const dto = new ReviewDTO({
      id: props.review.id,
      restaurantId: props.review.restaurantId,
      userId: authStore.currentUser.id,
      rating: rating.value,
      text: text.value
    })

    const formData = new FormData()
    formData.append('review', JSON.stringify(dto.toUpdateRequest()))

    // 1. Enviar novas imagens
    const novasImagens = imageFiles.value.filter(img => !img.originalName)
    if (novasImagens.length > 0) {
      novasImagens.forEach(img => formData.append('reviewImages', img))
    } else {
      formData.append('reviewImages', new Blob([])) // array vazio
    }

    // 2. Detetar imagens antigas removidas
    const imagensMantidas = imageFiles.value
      .filter(img => img.originalName)
      .map(img => img.originalName)

    const imagensRemovidas = imagensOriginais.value.filter(orig => !imagensMantidas.includes(orig))
    if (imagensRemovidas.length > 0) {
      formData.append('deletedImages', JSON.stringify(imagensRemovidas))
    }

    // 3. Enviar para o serviço
    const updated = await reviewService.updateReview(dto.id, formData)
    emit('review-updated', updated)
    emit('close')
  } catch (err) {
    console.error('Erro ao atualizar review:', err)
    errorMessage.value = 'Erro ao guardar alterações. Tenta novamente.'
  }
}

</script>

<style scoped>
.image-list {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-top: 0.5rem;
}

.image-thumb {
  position: relative;
  width: 80px;
  height: 80px;
}

.thumb {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 0.5rem;
}

.remove-btn {
  position: absolute;
  top: -6px;
  right: -6px;
  background: #dc3545;
  color: white;
  border: none;
  border-radius: 9999px;
  width: 20px;
  height: 20px;
  font-size: 0.75rem;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}
</style>
