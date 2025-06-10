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
                <h1 class="text-3xl font-bold text-black">{{ restaurant.name }}</h1>
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


        <div class="p-4 m-6 flex justify-between items-center">

            <div>
                <!-- Fotografias Menu -->
                 <div class="bg-white rounded-[26px] shadow min-h-[200px]">
                    <span class="ml-4 text-lg text-emerald-800 font-semibold">Fotografias do Menu</span>
                    <div class="grid grid-cols-4 gap-2 pt-4 mb-14 ml-4 mr-4">
                        <img v-for="(photo, i) in photos" :key="i" :src="photo"
                            class="w-full h-32 object-cover rounded" alt="Foto do restaurante" />
                    </div>
                </div>

                <!-- Fotografias Comida -->
                <div class="bg-white rounded-[26px] shadow mt-6 min-h-[200px]">
                    <span class="ml-4 text-lg text-emerald-800 font-semibold">Fotografias da Comida</span>
                    <div class="grid grid-cols-4 gap-2 pt-4 ml-4 mr-4">
                        <img v-for="(photo, i) in photos" :key="i" :src="photo"
                            class="w-full h-32 object-cover rounded" alt="Foto do restaurante" />
                    </div>
                </div>

                <!-- Menu  -->
                <div class="bg-white rounded-[26px] shadow mt-6 min-h-[400px]">
                    <span class="ml-4 text-lg text-emerald-800 font-semibold">Menu</span>
                    <ul class="space-y-3 pt-4 !pl-0">
                        <!-- <div class="flex flex-col items-center justify-between"> -->
                            <li v-for="(item, i) in menu" :key="i" class="flex justify-between items-center text-gray-700">
                                <img :src="item.photo" class="w-28 h-24 object-cover rounded ml-4" alt="Foto do prato" />
                                <div class="ml-10 flex flex-col flex-1">
                                    <span class="text-base text-emerald-800 font-semibold">{{ item.name }}</span>
                                    <span class="text-sm text-emerald-800">{{ item.ingredients }}</span>
                                </div>
                                <span class="text-base text-emerald-800 font-semibold mr-4">{{ formatPrice(item.price) }}</span>
                            </li>
                        <!-- </div> -->
                    </ul>
                </div>

                <!-- Avaliações -->
                <div class="bg-white rounded-[26px] shadow mt-6 min-h-[200px]">
                    <span class="ml-4 text-lg text-emerald-800 font-semibold justify-between">Avaliações</span>
                    <div class="ml-2 pt-4 flex items-center">
                        <div class="flex flex-col items-center space-y-4">
                            <span class="text-sm text-gray-500 font-bold">Avaliação Geral & Avaliações</span>
                            <span class="text-6xl text-emerald-800"> {{ restaurant.rating }}</span>    
                            <star-rating :rating="restaurant.rating" starColor="#065f46" :hideText="true" size="xl" />
                            <span class="text-sm text-gray-500 font-bold">
                                Based on {{ allFrequency }} reviews
                                <span class="ml-3 text-emerald-800 underline cursor-pointer" @click="showModal = true">
                                    Avaliar já
                                </span>
                            </span>
                            <ReviewModal v-model="showModal" />
                        </div>


                        <ul class="ml-6 space-y-4 pt-4 min-w-[500px] max-w-[700px]">
                            <li v-for="(item, i) in reviews" :key="i" class="flex items-center text-emerald-800 font-semibold gap-2">
                                <span class="w-[100px] shrink-0">{{ item.category }}</span>
                                <star-rating :rating="item.score" starColor="#065f46" :hideText="true" size="xl" />
                                <frequency-bar :frequency=item.frequency :total="allFrequency" color="#065f46" />
                            </li>
                        </ul>
                    </div>
                </div>


                <!-- Comentários -->
                <div class="mt-6 grid grid-cols-2 gap-3">
                    <div v-for="(c, idx) in comments" :key="idx" class="bg-white rounded-[26px] shadow pl-4 pr-4 pb-4 pt-4">
                        <!-- Header: avatar + nome + total reviews -->
                        <div class="flex items-center mb-4">
                            <img :src="c.userPhoto" :alt="c.user" class="w-12 h-12 rounded-full mr-3"/>
                            <div class="flex flex-col items-start">
                                <span class="font-semibold text-gray-800">{{ c.user }}</span>
                                <span class="text-sm text-gray-500">Reviewed {{ c.totalReviews }} restaurants</span>
                            </div>
                        </div>

                        <!-- Rating -->
                        <star-rating :rating="c.rating" starColor="#065f46" :hideText="true" size="lg" class="pb-2"/>

                        <!-- Texto do comentário -->
                        <span class="text-base text-gray-700">{{ c.text }}</span>

                        <!-- Fotos do restaurante, se houver -->
                        <div v-if="c.restaurantPhotos.length" class="flex gap-2 mt-4">
                            <img v-for="(photo, j) in c.restaurantPhotos" :key="j" :src="photo"
                                                                           alt="Foto prato" class="w-20 h-14 object-cover rounded"/>
                        </div>
                    </div>
                </div>

            </div>

            <div>
                mapa 
            </div>

        </div>


         <Footer />
            
        </div>
</template>

<script>
import Footer from '../Footer.vue';
import FrequencyBar from '../utils/FrequencyBar.vue';
import StarRating from '../utils/Stars.vue';
import ReviewModal from './ReviewModal.vue';
import { ref } from 'vue';

export default {
    name: 'RestaurantDetails',
    components: {
        StarRating,
        ReviewModal,
        FrequencyBar,
        Footer
    },
    props: {
        name: {
            type: String,
            required: true
        }
    },
    data() {
        return {
            showModal: ref(false),
            restaurant: null,
            photos: [
                'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80',
                'https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80',
                'https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80',
                'https://images.unsplash.com/photo-1498837167922-ddd27525d352?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80',
            ],
            menu: [
                { photo: 'https://images.unsplash.com/photo-1513104890138-7c749659a591', name: 'Pizza Margherita', ingredients: 'Massa caseira, molho de tomate, mozzarella, manjericão fresco', price: 12 },
                { photo: 'https://images.unsplash.com/photo-1551024601-bec78aea704b', name: 'Risotto de Cogumelos', ingredients: 'Arroz arbóreo, cogumelos variados, caldo de legumes, parmesão', price: 15 },
                { photo: 'https://images.unsplash.com/photo-1548943487-a2e4e43b4853', name: 'Salada Mediterrânea', ingredients: 'Alface, tomate cereja, pepino, azeitonas, queijo feta, azeite', price: 10 },
            ],
            reviews: [
                { category: 'Espaço', score: 3.7, frequency: 53 },
                { category: 'Atendimento', score: 4.5, frequency: 63 },
                { category: 'Comida', score: 4.8, frequency: 78 },
                { category: 'Preço', score: 4.2, frequency: 45 },
            ],
            comments: [
                {   user: 'João', userPhoto: 'https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0', text: 'Comida deliciosa e atendimento excelente!', totalReviews: 60, rating: 4.8, 
                    restaurantPhotos: [
                        'https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0',
                        'https://images.unsplash.com/photo-1513104890138-7c749659a591'
                    ] 
                },
                {   user: 'Maria', userPhoto: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330', text: 'Ambiente agradável, mas o preço é um pouco alto.', totalReviews: 45, rating: 3.5, 
                    restaurantPhotos: [ ] 
                },
                {  user: 'Pedro', userPhoto: 'https://images.unsplash.com/photo-1519085360753-af0119f7cbe7', text: 'A comida estava boa, mas o serviço demorou muito.', totalReviews: 30, rating: 3.0, 
                                    restaurantPhotos: [
                                        'https://images.unsplash.com/photo-1540189549336-e6e99c3679fe',
                                        'https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445'
                                    ] 
                                }
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
        allFrequency() {
            return this.reviews.reduce((sum, r) => sum + r.frequency, 0);
        }
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
