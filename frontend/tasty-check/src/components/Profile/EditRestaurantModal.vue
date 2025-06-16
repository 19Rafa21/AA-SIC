<template>
  <teleport to="body">
    <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 overflow-auto">
      <div class="bg-white rounded-lg p-6 w-full max-w-2xl shadow-lg max-h-[90vh] overflow-y-auto">
        <div class="flex items-center justify-between mb-4">
          <span class="text-2xl font-semibold text-emerald-800">Editar Restaurante</span>
          <button @click="cancelar" class="text-2xl text-red-500 hover:text-red-800">
            <i class="fa-regular fa-circle-xmark"></i>
          </button>
        </div>

        <div class="space-y-4">
          <div>
            <label class="block text-sm font-medium">Nome</label>
            <input v-model="nome" class="input-style" type="text" />
          </div>

          <div>
            <label class="block text-sm font-medium">Localização</label>
            <input v-model="localizacao" class="input-style" type="text" />
          </div>

          <div>
            <label class="block text-sm font-medium">Tipo de Cozinha</label>
            <input v-model="tipo" class="input-style" type="text" />
          </div>

          <div>
            <label class="block text-sm font-medium">Imagem de Capa</label>
            <input type="file" accept="image/*" @change="onImagemChange" />
            <div v-if="imagemPreview" class="mt-2">
              <img :src="imagemPreview" class="w-full max-h-48 object-cover rounded" />
            </div>
          </div>

          <div>
            <label class="block text-sm font-medium">Imagens do Menu</label>
            <input type="file" multiple accept="image/*" @change="handleMenuUpload" />
            <div class="image-list mt-2">
              <div v-for="(img, i) in menuImages" :key="'menu-' + i" class="image-thumb">
                <img :src="getObjectURL(img)" class="thumb" />
                <button type="button" class="remove-btn" @click="removeMenuImage(i)">×</button>
              </div>
            </div>
          </div>

          <div>
            <label class="block text-sm font-medium">Imagens de Pratos</label>
            <input type="file" multiple accept="image/*" @change="handleFoodUpload" />
            <div class="image-list mt-2">
              <div v-for="(img, i) in foodImages" :key="'food-' + i" class="image-thumb">
                <img :src="getObjectURL(img)" class="thumb" />
                <button type="button" class="remove-btn" @click="removeFoodImage(i)">×</button>
              </div>
            </div>
          </div>

          <div class="mt-6 flex justify-end gap-3">
            <button @click="cancelar" class="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400">Cancelar</button>
            <button @click="guardar" class="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700">Guardar</button>
          </div>

          <div v-if="erro" class="alert alert-danger py-2 text-sm text-center text-white mt-4 bg-red-500 rounded">
            {{ erro }}
          </div>
        </div>
      </div>
    </div>
  </teleport>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import RestaurantService from '@/services/restaurant.service'
import ImageService from '@/services/image.service.js'
const imageService = new ImageService()


const props = defineProps({ restaurante: Object })
const emit = defineEmits(['close', 'save', 'refresh-owned'])

const nome = ref('')
const localizacao = ref('')
const tipo = ref('')
const imagem = ref(null)
imagem.value = ref(null)
const imagemPreview = ref(null)
const menuImages = ref([])
const foodImages = ref([])
const erro = ref(null)

onMounted(async () => {
  console.log('RESTAURANTE RECEBIDO PARA EDIÇÃO:', props.restaurante)
  console.log('Menu Images:', props.restaurante.menuImages)
console.log('Food Images:', props.restaurante.foodImages)

  nome.value = props.restaurante.name || ''
  localizacao.value = props.restaurante.location || ''
  tipo.value = props.restaurante.cuisineType || ''

  // Imagem de capa
  try {
    if (props.restaurante.image) {
      const blob = await imageService.getImage(props.restaurante.image)
      imagemPreview.value = URL.createObjectURL(blob)
      imagem.value = props.restaurante.image
    }
  } catch (e) {
    imagemPreview.value = '/img/placeholder-restaurant.png'
  }

  // Menu Images
  menuImages.value = await Promise.all(
    (props.restaurante.menuImages || []).map(async (img) => {
      try {
        const blob = await imageService.getImage(img)
        return Object.assign(blob, { originalName: img })
      } catch (e) {
        return null
      }
    })
  ).then(imgs => imgs.filter(Boolean))

  // Food Images
  foodImages.value = await Promise.all(
    (props.restaurante.foodImages || []).map(async (img) => {
      try {
        const blob = await imageService.getImage(img)
        return Object.assign(blob, { originalName: img })
      } catch (e) {
        return null
      }
    })
  ).then(imgs => imgs.filter(Boolean))
})


const restaurantService = new RestaurantService()

const onImagemChange = (e) => {
  const file = e.target.files[0]
  if (file) {
    imagem.value = file
    imagemPreview.value = URL.createObjectURL(file)
  }
}

const handleMenuUpload = (e) => {
  const files = Array.from(e.target.files)
  menuImages.value.push(...files)
}

const handleFoodUpload = (e) => {
  const files = Array.from(e.target.files)
  foodImages.value.push(...files)
}

const removeMenuImage = (i) => menuImages.value.splice(i, 1)
const removeFoodImage = (i) => foodImages.value.splice(i, 1)
const getObjectURL = (f) => (typeof f === 'string' ? f : URL.createObjectURL(f))

const guardar = async () => {
  erro.value = null

  if (!imagem.value) {
    erro.value = 'A imagem de capa é obrigatória.'
    return
  }

  try {
    const dto = {
      id: props.restaurante.id,
      name: nome.value,
      location: localizacao.value,
      cuisineType: tipo.value,
      image: '', // ignorado, vai no form
      rating: props.restaurante.rating || 0,
      owner: props.restaurante.owner
    }

    const formData = new FormData()
    formData.append('restaurant', JSON.stringify(dto))

    // Capa
    if (typeof imagem.value === 'string') {
      try {
        const blob = await imageService.getImage(imagem.value)
        const file = new File([blob], imagem.value, { type: blob.type || 'image/jpeg' })
        formData.append('coverImage', file)
      } catch (e) {
        console.error('Erro ao converter imagem antiga em ficheiro:', e)
      }
    } else if (imagem.value) {
      formData.append('coverImage', imagem.value)
    }



    // Menu Images (envia array vazio se não houver novas imagens)
    const novasMenu = menuImages.value.filter(img => typeof img !== 'string')
    if (novasMenu.length > 0) {
      novasMenu.forEach(img => formData.append('menuImages', img))
    } else {
      formData.append('menuImages', new Blob([])) // array vazio
    }

    // Food Images (idem)
    const novasFood = foodImages.value.filter(img => typeof img !== 'string')
    if (novasFood.length > 0) {
      novasFood.forEach(img => formData.append('foodImages', img))
    } else {
      formData.append('foodImages', new Blob([])) // array vazio
    }

    const updated = await restaurantService.updateRestaurant(dto.id, formData)
    window.location.reload()
  } catch (err) {
    console.error('Erro ao atualizar restaurante:', err)
    erro.value = 'Erro ao guardar alterações. Tenta novamente.'
  }
}



const cancelar = () => emit('close')
</script>

<style scoped>
.input-style {
  background-color: white;
  border: 1px solid #ccc;
  padding: 6px 10px;
  border-radius: 0.375rem;
  width: 100%;
  font-size: 0.875rem;
}

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
