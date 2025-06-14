 <template>
    <div v-if="loading" class="text-center py-12">
        <Spinner class="mx-auto mt-4 mb-1"/>    
        <span class="text-lg text-emerald-500">Carregando detalhes do restaurante...</span>
    </div>
    <div v-else-if="error !== null" class="text-center py-12">
        <TopNav />
        <p class="text-lg text-red-500">{{ error }}</p>
        <button @click="$router.push('/')" class="mt-4 px-4 py-2 bg-green-600 text-white rounded">Voltar para a página
            inicial</button>
    </div>
    <div v-else>
        <TopNav />
        <!-- Imagem em cima -->
        <div class="m-4 pl-16 pr-16 rounded-3xl mt-7">
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
                <!-- Contacto -->
                <div class="flex items-center gap-2 text-xl text-black">
                    <i class="fa-solid fa-phone"></i>
                    <span>+252 377 921</span>
                </div>
                <!-- Horário de funcionamento -->
                <div class="flex items-center gap-2 text-xl text-black">
                    <i class="fa-solid fa-clock"></i>
                    <span>11:30-15:00 | 19:00-23:00 </span>
                </div>
            </div>
        </div>


        <div class="p-4 m-6 flex justify-between">

            <div>
                <!-- Fotografias Menu -->
                <div class="bg-white rounded-[26px] shadow min-h-[200px]">
                    <span class="ml-4 text-lg text-emerald-800 font-semibold">Fotografias do Menu</span>
                    <!-- <div class="grid grid-cols-4 gap-2 pt-4 mb-14 ml-4 mr-4">
                        <img v-for="(photo, i) in restaurant.menuImages" :key="i" :src="photo"
                            class="w-full h-32 object-cover rounded" alt="Foto do restaurante" />
                    </div> -->
                    <PhotosCarousel :images="restaurant.menuImages" />
                </div>

                <!-- Fotografias Comida -->
                <div class="bg-white rounded-[26px] shadow mt-6 min-h-[200px]">
                    <span class="ml-4 text-lg text-emerald-800 font-semibold">Fotografias da Comida</span>
                    <!-- <div class="grid grid-cols-4 gap-2 pt-4 ml-4 mr-4">
                        <img v-for="(photo, i) in restaurant.foodImages" :key="i" :src="photo"
                            class="w-full h-32 object-cover rounded" alt="Foto do restaurante" />
                    </div> -->
                    <PhotosCarousel :images="restaurant.foodImages" />
                </div>

                <!-- Menu  -->
                <div class="bg-white rounded-[26px] shadow mt-6 min-h-[400px]">
                    <span class="ml-4 text-lg text-emerald-800 font-semibold">Menu</span>
                    <ul class="space-y-3 pt-4 !pl-0">
                        <!-- <div class="flex flex-col items-center justify-between"> -->
                            <li v-for="(item, i) in restaurant.dishes" :key="i" class="flex justify-between items-center text-gray-700">
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
                    <!-- <span class="ml-4 text-lg text-emerald-800 font-semibold justify-between">Avaliações</span> -->
                    <!-- <div class="ml-2 pt-4 flex items-center"> -->
                        <div v-if="loadingReviews" class="text-center py-12">
                            <Spinner class="mx-auto mt-4 mb-1"/>    
                            <span class="text-lg text-emerald-500">Carregando Avaliações...</span>
                        </div>

                        <div v-else-if="errorReviews !== null" class="text-center py-12">
                            <p class="text-lg text-red-500">{{ error }}</p>
                        </div>
                        <div v-else class="flex flex-col items-center space-y-4">
                            <span class="text-lg text-gray-500 font-bold">Avaliação Geral & Avaliações</span>
                            <span class="text-6xl text-emerald-800"> {{ restaurant.rating.toFixed(2) }}</span>    
                            <star-rating :rating="restaurant.rating" starColor="#065f46" :hideText="true" size="xl" />
                            <span class="text-sm text-gray-500 font-bold">
                                Based on {{ allFrequency }} reviews
                                <span class="ml-3 text-emerald-800 underline cursor-pointer" @click="openModal">
                                    Avaliar já
                                </span>
                            </span>
                        </div>


                        <!-- <ul class="ml-6 space-y-4 pt-4 min-w-[500px] max-w-[700px]">
                            <li v-for="(item, i) in reviews" :key="i" class="flex items-center text-emerald-800 font-semibold gap-2">
                                <span class="w-[100px] shrink-0">{{ item.category }}</span>
                                <star-rating :rating="item.score" starColor="#065f46" :hideText="true" size="xl" />
                                <frequency-bar :frequency=item.frequency :total="allFrequency" color="#065f46" />
                            </li>
                        </ul> -->
                    <!-- </div> -->
                </div>              

            </div>

            <div class="ml-20"> 
                <RestaurantGoogleMap :location="restaurant.location" :zoom="14"/>
            </div>

        </div>

        <div class="bg-white rounded-[26px] mt-6 min-h-[200px] m-4">

            <!-- Comentários -->
            <div class="mt-6 grid grid-cols-4 gap-3">
                <div v-for="(c, idx) in reviews" :key="idx" class="bg-white rounded-[26px] shadow pl-4 pr-4 pb-4 pt-4">
                    <!-- Header: avatar + nome + total reviews -->
                    <div class="flex items-center mb-4">
                        <img :src="c.userPhoto" :alt="c.userId" class="w-12 h-12 rounded-full mr-3"/>
                        <div class="flex flex-col items-start">
                            <span class="font-semibold text-gray-800">{{ c.username }}</span>
                            <span class="text-sm text-gray-500"> {{ c.date }}</span>
                        </div>
                    </div>

                    <!-- Rating -->
                    <star-rating :rating="c.rating" starColor="#065f46" :hideText="true" size="lg" class="pb-2"/>

                    <!-- Texto do comentário -->
                    <span class="text-base text-gray-700">{{ c.text }}</span>

                    <!-- Fotos do restaurante, se houver -->
                    <div v-if="false" class="flex gap-2 mt-4">
                        <img v-for="(photo, j) in c.restaurantPhotos" :key="j" :src="photo"
                                                                        alt="Foto prato" class="w-20 h-14 object-cover rounded"/>
                    </div>
                    <div v-else class="flex gap-2 mt-4">
                        <span class="text-gray-500">!!!!! REVER AS FOTOS O IF ESTA FORÇADO</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="flex justify-center items-center mt-6 mb-6">            
            <button @click="openReplyModal" class="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700">
              Comentar
            </button>
        </div>

        <Footer />
            
        </div>

        <!-- Review Modal -->
        <ReviewModal 
            v-if="showModal" 
            :restaurantId="String(restaurant?.id)" 
            @close="closeModal" 
            @review-submitted="handleReviewSubmitted" 
        />

        <!-- Reply Modal -->
        <ReplyModal 
            v-if="showReplyModal" 
            :restaurantId="String(restaurant?.id)" 
            @close="closeReplyModal" 
            @reply-submitted="handleReplySubmitted"
        />

</template>

<script>
import Footer from '../Footer.vue';
import FrequencyBar from '../utils/FrequencyBar.vue';
import StarRating from '../utils/Stars.vue';
import ReviewModal from './ReviewModal.vue';
import RestaurantGoogleMap from '../Maps/RestaurantGoogleMap.vue';
import TopNav from '../Layout/TopNav.vue';
import Spinner from '../utils/Spinner.vue';
import ReplyModal from './ReplyModal.vue';
import { RestaurantService, ReviewService } from '@/services';
import PhotosCarousel from '../utils/PhotosCarousel.vue';

export default {
    name: 'RestaurantDetails',
    components: {
        StarRating,
        ReviewModal,
        ReplyModal,
        FrequencyBar,
        Footer,
        RestaurantGoogleMap,
        TopNav,
        Spinner,
        PhotosCarousel,
    },
    props: {
        id: {
            type: String,
            required: true
        },
    },
    data() {
        return {
            RestaurantService: new RestaurantService(),
            ReviewService: new ReviewService(),
            showModal: false,
            showReplyModal: false,
            restaurant: null,
            reviews: [],
            loadingReviews: false,
            errorReviews: null,
            loading: true,
            error: null,
            restaurant: null,
        };
    },
    created() {
        this.fetchRestaurantDetails();
        this.fetchReviews();
    },
    computed: {
        averageScore() {
            if (!this.reviews) return 0;
            if (!this.reviews.length) return 0;
            return this.reviews.reduce((sum, r) => sum + r.score, 0) / this.reviews.length;
        },
        allFrequency() {
           return 0;
            // if (!this.reviews.length) return 0;
            // return this.reviews.reduce((sum, r) => sum + r.frequency, 0);
        }
    },
    methods: {
        async fetchRestaurantDetails() {
            this.loading = true;
            try {
                const restaurantDTO = await this.RestaurantService.getRestaurantById(this.id);
                this.restaurant = restaurantDTO;    
            } catch (err) {
                this.error = err.message;
            } finally {
                this.loading = false;
            }
        },
        async fetchReviews() {
            this.loadingReviews = true;
            try {
                console.log("Fetching reviews for restaurant ID:", this.id);
                const reviews = await this.RestaurantService.getReviewsByRestaurant(this.id);
                this.reviews = reviews;
            } catch (err) {
                this.errorReviews = err.message;
            } finally {
                this.loadingReviews = false;
            }
        },
        formatPrice(v) {
            return `${v} €`;
        },
        openModal() {
            this.showModal = true;
        },
        closeModal() {
            this.showModal = false;
        },
        handleReviewSubmitted(review) {
            alert("Avaliação recebida com sucesso!");
        },
        openReplyModal() {
            this.showReplyModal = true;
        },
        closeReplyModal() {
            this.showReplyModal = false;
        },
        handleReplySubmitted(reply) {
            alert("Comentário enviado com sucesso!");
        }
    },
};
</script>

<style scoped>
/* Nenhum CSS extra por agora — tudo feito via Tailwind */
</style>
