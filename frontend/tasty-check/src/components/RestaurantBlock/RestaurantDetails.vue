<template>
    <div v-if="loading" class="text-center py-12">
        <p class="text-lg">Carregando detalhes do restaurante...</p>
    </div>
    <div v-else-if="error" class="text-center py-12">
        <p class="text-lg text-red-500">{{ error }}</p>
        <button @click="$router.push('/')" class="mt-4 px-4 py-2 bg-green-600 text-white rounded">Voltar para a página
            inicial</button>
    </div>
    <div v-else>

        <!-- Imagem em cima -->
        <div class="m-4 pl-16 pr-16 rounded-3xl">
            <img :src="restaurant.image" :alt="restaurant.name" class="w-full h-64 object-cover rounded-3xl" />
        </div>

        <div class="p-4 space-y-4 m-6">
            <!-- Título  e like -->
            <div class="flex justify-between items-center">
                <h1 class="text-2xl font-bold text-black">{{ restaurant.name }}</h1>
                <button class="text-2xl text-gray-400 hover:text-red-500">
                    <i class="far fa-heart"></i>
                </button>
            </div>

            <!-- Rating -->
            <div class="flex flex-col flex-wrap gap-2 text-sm text-gray-600 items-start">
                <div class="flex items-center">
                    <star-rating :rating="restaurant.rating" />
                </div>
                <!--  Localização -->
                <div class="flex items-center gap-2 text-xl text-black">
                    <i class="fa-solid fa-location-dot"></i>
                    <span>{{ restaurant.location }}</span>
                </div>
                <!-- Tipo de cozinha -->
                <div class="flex items-center gap-2 text-xl text-black">
                    <i class="fa-solid fa-utensils"></i>
                    {{ restaurant.cuisineType }}
                </div>
                <!-- Preço médio -->
                <div class="flex items-center gap-2 text-xl text-black">
                    <i class="fa-solid fa-euro-sign"></i>
                    {{ formatPrice(restaurant.averagePrice) }}
                </div>
            </div>
        </div>


        <div class="max-w-4xl mx-auto mt-8 p-4 space-y-6">

                <!-- 1) Card principal: imagem + info básica -->
                <div class="bg-white rounded-lg shadow overflow-hidden">

                    <!-- 2) Área dividida: esquerda fotos+menu / direita mapa -->
                    <div class="md:flex md:space-x-6">
                        <!-- Esquerda -->
                        <div class="md:w-2/3 space-y-6">
                            <!-- Fotos -->
                            <div>
                                <h2 class="font-semibold mb-2">Fotos</h2>
                                <div class="grid grid-cols-2 gap-2">
                                    <img v-for="(photo, i) in photos" :key="i" :src="photo"
                                        class="w-full h-24 object-cover rounded" alt="Foto do restaurante" />
                                </div>
                            </div>
                            <!-- Menu -->
                            <div>
                                <h2 class="font-semibold mb-2">Menu</h2>
                                <ul class="space-y-2">
                                    <li v-for="(item, i) in menu" :key="i" class="flex justify-between text-gray-700">
                                        <span>{{ item.name }}</span>
                                        <span>{{ formatPrice(item.price) }}</span>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <!-- Direita (mapa-placeholder) -->
                        <div class="md:w-1/3">
                            <h2 class="font-semibold mb-2">Mapa</h2>
                            <div class="w-full h-48 bg-gray-200 rounded flex items-center justify-center text-gray-500">
                                MAPA
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 3) Card de avaliações -->
            <div class="bg-white rounded-lg shadow p-4">
                <div class="md:flex md:justify-between">
                    <!-- Lista de reviews à esquerda -->
                    <div class="md:w-2/3 space-y-4">
                        <div v-for="(rev, i) in reviews" :key="i" class="border-b border-gray-200 pb-2">
                            <h3 class="font-semibold">{{ rev.title }}</h3>
                            <div class="flex items-center text-yellow-500 mb-1">
                                <span class="mr-1">{{ rev.score.toFixed(1) }}</span>
                                <span>★</span>
                            </div>
                            <p class="text-sm text-gray-600">{{ rev.comment }}</p>
                        </div>
                    </div>
                    <!-- Média das reviews à direita -->
                    <div class="md:w-1/3 flex flex-col items-center justify-center mt-4 md:mt-0">
                        <span class="text-4xl font-bold">{{ averageScore.toFixed(1) }}</span>
                        <div class="flex items-center text-yellow-500 text-xl">
                            <span class="mr-1">{{ averageScore.toFixed(1) }}</span><span>★</span>
                        </div>
                        <span class="text-sm text-gray-500">{{ reviews.length }} avaliações</span>
                    </div>
                </div>
            </div>
        </div>
</template>

<script>
import StarRating from '../utils/Stars.vue';

export default {
    name: 'RestaurantDetails',
    components: {
        StarRating
    },
    props: {
        name: {
            type: String,
            required: true
        }
    },
    data() {
        return {
            restaurant: null,
            photos: [
                'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80',
                'https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80',
                'https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80',
                'https://images.unsplash.com/photo-1498837167922-ddd27525d352?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80',
            ],
            menu: [
                { name: 'Menu 1', price: 12 },
                { name: 'Menu 2', price: 15 },
                { name: 'Menu 3', price: 10 },
            ],
            reviews: [
                { title: 'Excelente comida', score: 4.5, comment: 'Muito saboroso e bem servido.' },
                { title: 'Ótimo ambiente', score: 4.0, comment: 'Espaço agradável e acolhedor.' },
            ],
            loading: true,
            error: null
        };
    },
    created() {
        // Tenta obter o restaurante do state da rota
        if (this.$route.state && this.$route.state.restaurant) {
            this.restaurant = this.$route.state.restaurant;
            this.loading = false;
        }
        // Se não encontrar no state, busca do JSON usando o nome
        else {
            import('@/dataTesting/restaurantes.json')
                .then(data => {
                    const restaurant = data.default.find(r => r.name === this.name);
                    if (restaurant) {
                        this.restaurant = restaurant;
                    } else {
                        this.error = 'Restaurante não encontrado';
                    }
                    this.loading = false;
                })
                .catch(err => {
                    this.error = 'Erro ao carregar dados do restaurante';
                    this.loading = false;
                    console.error(err);
                });
        }
    },
    computed: {
        averageScore() {
            if (!this.reviews.length) return 0;
            return this.reviews.reduce((sum, r) => sum + r.score, 0) / this.reviews.length;
        },
    },
    methods: {
        formatPrice(v) {
            return `${v} €`;
        },
    },
};
</script>

<style scoped>
/* Nenhum CSS extra por agora — tudo feito via Tailwind */
</style>
