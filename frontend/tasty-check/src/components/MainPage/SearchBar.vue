<template>
  <div class="w-full max-w-[800px] mx-auto relative" ref="dropdownRoot">
    <form @submit.prevent="searchRestaurants" class="w-full flex items-center bg-[#e9ecef] border border-[#ced4da] rounded-[0.75rem] h-[55px] overflow-hidden p-0 relative z-[10]">
      <div
        class="flex items-center px-4 h-full border-r border-[#dee2e6] font-semibold text-[0.9rem] text-[#6c757d] cursor-pointer relative z-[50]"
        @click.stop="toggleDropdown"
      >
        <i class="fas fa-filter mr-2"></i>
        <span>Filtrar</span>
        <i class="fas fa-chevron-down ml-2"></i>
      </div>

      <div class="flex items-center flex-grow h-full bg-transparent pr-4 pl-3">
        <i class="fas fa-search mr-2 text-[#adb5bd] text-[1.1rem]"></i>
        <input
          v-model="searchQuery"
          type="text"
          class="w-full max-w-[400px] h-full py-1 px-2 border-none outline-none bg-transparent text-sm placeholder-[#6c757d]"
          placeholder="Nome do Restaurante"
        />
      </div>

      <button type="submit" class="search-button">
        PROCURAR
      </button>
    </form>

    <transition name="dropdown-fade">
      <div
        v-if="showMainDropdown"
        class="filter-dropdown-modern"
      >
        <div class="dropdown-arrow-modern"></div>
        <ul class="dropdown-menu-list">
          <li @mouseenter="openSubmenu('location')" class="dropdown-item-modern">Localização</li>
          <li @mouseenter="openSubmenu('cuisine')" class="dropdown-item-modern">Tipo de Cozinha</li>
          <li @mouseenter="openSubmenu('rating')" class="dropdown-item-modern">Avaliação</li>
        </ul>

        <div v-if="activeSubmenu === 'location'" class="submenu-dropdown">
          <ul>
            <li
              v-for="option in locationOptions"
              :key="option"
              class="dropdown-item-modern"
              @click.stop="toggleFilter('location', option)"
            >
              <span class="checkmark" :class="{ selected: selectedFilters.location.includes(option) }">✔</span>
              {{ option }}
            </li>
          </ul>
        </div>

        <div v-if="activeSubmenu === 'cuisine'" class="submenu-dropdown">
          <ul>
            <li
              v-for="option in cuisineOptions"
              :key="option"
              class="dropdown-item-modern"
              @click.stop="toggleFilter('cuisine', option)"
            >
              <span class="checkmark" :class="{ selected: selectedFilters.cuisine.includes(option) }">✔</span>
              {{ option }}
            </li>
          </ul>
        </div>

        <div v-if="activeSubmenu === 'rating'" class="submenu-dropdown">
          <ul>
            <li
              v-for="option in ratingOptions"
              :key="option"
              class="dropdown-item-modern"
              @click.stop="toggleFilter('rating', option)"
            >
              <span class="checkmark" :class="{ selected: selectedFilters.rating.includes(option) }">✔</span>
              {{ option }}
            </li>
          </ul>
        </div>
      </div>
    </transition>

    <div v-if="showResults" class="mt-4 bg-white/80 backdrop-blur-lg rounded-lg p-4 relative">
      <button class="absolute top-2 right-2 text-red-500 text-xl" @click="showResults = false">×</button>
      <RestaurantCarousel :title="'Resultados da Pesquisa'" :restaurants="searchResults" :visibleCount="3" ref="carousel" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import RestaurantService from '@/services/restaurant.service.js'
import RestaurantCarousel from '../RestaurantBlock/RestauranteCarousel.vue'

const showMainDropdown = ref(false)
const activeSubmenu = ref(null)
const dropdownRoot = ref(null)
const showResults = ref(false)
const searchQuery = ref('')
const searchResults = ref([])
const carousel = ref(null)

const cuisineOptions = ['Italiana', 'Japonesa', 'Portuguesa']
const locationOptions = ['Braga', 'Porto', 'Lisboa']
const ratingOptions = ['★ 5', '★ 4+', '★ 3+']

const selectedFilters = ref({
  cuisine: [],
  location: [],
  rating: []
})

const toggleDropdown = () => {
  showMainDropdown.value = !showMainDropdown.value
  if (!showMainDropdown.value) activeSubmenu.value = null
}

const openSubmenu = (menu) => {
  activeSubmenu.value = menu
}

const closeDropdown = () => {
  showMainDropdown.value = false
  activeSubmenu.value = null
}

const toggleFilter = (category, option) => {
  const list = selectedFilters.value[category]
  const index = list.indexOf(option)
  if (index >= 0) {
    list.splice(index, 1)
  } else {
    list.push(option)
  }
}

const cuisineMap = {
  'Italiana': 'Italian',
  'Japonesa': 'Japanese',
  'Portuguesa': 'Portuguese'
}

const getMinRating = () => {
  if (selectedFilters.value.rating.includes('★ 3+')) return 3.0
  if (selectedFilters.value.rating.includes('★ 4+')) return 4.0
  if (selectedFilters.value.rating.includes('★ 5')) return 5.0
  return 0.0
}

const searchRestaurants = async () => {
  const service = new RestaurantService()
  const name = searchQuery.value.trim()
  const location = selectedFilters.value.location[0] || ''
  const cuisine = cuisineMap[selectedFilters.value.cuisine[0]] || ''
  const rating = getMinRating()

  try {
    const data = await service.getRestaurantsWithFilter(name, location, cuisine, rating)
    searchResults.value = data
    showResults.value = true
    if (carousel.value) {
      carousel.value.currentStart = 0
    }
  } catch (error) {
    console.error('Erro ao carregar restaurantes:', error)
  }
}

const handleClickOutside = (event) => {
  if (dropdownRoot.value && !dropdownRoot.value.contains(event.target)) {
    closeDropdown()
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
})

onBeforeUnmount(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>

<style scoped>
.search-button {
  background-color: #095243;
  border: none;
  height: 80%;
  padding: 0.5rem 1.5rem;
  font-weight: bold;
  font-size: 0.9rem;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 0.5rem;
  border-radius: 0.5rem;
  color: white;
  transition: background-color 0.3s ease;
}

.search-button:hover {
  background-color: #073b31;
}

.filter-dropdown-modern {
  position: absolute;
  top: 60px;
  left: 0;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(16, 185, 129, 0.2);
  border-radius: 16px;
  box-shadow:
    0 20px 25px -5px rgba(0, 0, 0, 0.1),
    0 10px 10px -5px rgba(0, 0, 0, 0.04),
    0 0 0 1px rgba(16, 185, 129, 0.05);
  z-index: 1000;
  min-width: 200px;
  display: flex;
  padding: 8px 0;
}

.dropdown-arrow-modern {
  position: absolute;
  top: -6px;
  left: 20px;
  width: 12px;
  height: 12px;
  background: rgba(255, 255, 255, 0.95);
  border: 1px solid rgba(16, 185, 129, 0.2);
  border-bottom: none;
  border-right: none;
  transform: rotate(45deg);
  backdrop-filter: blur(20px);
  z-index: -1;
}

.submenu-dropdown {
  position: absolute;
  top: 0;
  left: 100%;
  margin-left: 8px;
  background: rgba(255, 255, 255, 0.95);
  border: 1px solid rgba(16, 185, 129, 0.2);
  border-radius: 12px;
  backdrop-filter: blur(20px);
  padding: 8px 0;
  box-shadow:
    0 10px 15px -5px rgba(0, 0, 0, 0.08),
    0 4px 6px -4px rgba(0, 0, 0, 0.03);
}

.dropdown-menu-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.dropdown-item-modern {
  padding: 10px 20px 10px 16px;
  font-size: 0.9rem;
  color: #374151;
  cursor: pointer;
  transition: all 0.2s ease;
  white-space: nowrap;
  position: relative;
  display: flex;
  align-items: center;
}

.dropdown-item-modern:hover {
  background: linear-gradient(90deg, rgba(16, 185, 129, 0.08), rgba(16, 185, 129, 0.04));
  color: #065f46;
  transform: translateX(4px);
}

.dropdown-item-modern:hover::before {
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  width: 3px;
  background: linear-gradient(180deg, #10b981, #059669);
  border-radius: 0 2px 2px 0;
}

.checkmark {
  display: inline-block;
  width: 18px;
  margin-right: 10px;
  font-size: 0.85rem;
  color: #cbd5e1;
  transition: color 0.3s ease;
  z-index: 1;
}

.checkmark.selected {
  color: #10b981;
}

.dropdown-fade-enter-active,
.dropdown-fade-leave-active {
  transition: all 0.3s ease;
}

.dropdown-fade-enter-from {
  opacity: 0;
  transform: translateY(-10px) scale(0.95);
}

.dropdown-fade-leave-to {
  opacity: 0;
  transform: translateY(-5px) scale(0.98);
}
</style>
