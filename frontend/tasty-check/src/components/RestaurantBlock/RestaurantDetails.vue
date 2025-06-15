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
                <div class="flex flex-col items-end">
                    <div class="flex items-center">
                        <Spinner v-if="isAddingToFavorites" class="mr-2 text-lg" />
                        <button class="text-2xl hover:text-red-500" 
                                :class="{ 'text-red-500': isFavorite, 'text-gray-400': !isFavorite }" 
                                @click="toggleFavorite"
                                :disabled="isAddingToFavorites">
                            <i :class="isFavorite ? 'fas fa-heart' : 'far fa-heart'"></i>
                        </button>
                    </div>
                    <p v-if="showLoginWarning" class="text-red-500 text-sm mt-1">
                        É necessário fazer login para adicionar aos favoritos
                    </p>
                </div>
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
                    <i class="fa-solid fa-money-bill"></i>
                    {{ formatPrice(averagePrice()) }}
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
                <div class="bg-white mb-2 rounded-[26px] shadow min-h-[200px]">
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
                    <div v-if="!dishes || dishes.length === 0" class="flex flex-col items-center justify-center p-8 text-gray-500">
                        <i class="fa-solid fa-utensils text-5xl mb-4"></i>
                        <span class="text-lg">Sem pratos adicionados</span>
                    </div>
                    <ul v-else class="space-y-3 pt-4 !pl-0">
                        <li v-for="(item, i) in dishes" :key="i" class="flex justify-between items-center text-gray-700">
                            <img :src="item.image" class="w-28 h-24 object-cover rounded ml-4" alt="Foto do prato" />
                            <div class="ml-10 flex flex-col flex-1">
                                <span class="text-base text-emerald-800 font-semibold">{{ item.name }}</span>
                                <span class="text-sm text-emerald-800">{{ item.ingredients }}</span>
                            </div>
                            <span class="text-base text-emerald-800 font-semibold mr-4">{{ formatPrice(item.price) }}</span>
                        </li>
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

            <!-- Reviews -->
            <div v-if="loadingReviews" class="text-center py-12">
                <Spinner class="mx-auto mt-4 mb-1"/>    
                <span class="text-lg text-emerald-500">Carregando reviews...</span>
            </div>

            <div v-else-if="!loadingReviews && reviews.length > 0" class="mt-6 grid grid-cols-4 gap-3">
                <div v-for="(c, idx) in reviews" :key="idx" class="bg-white rounded-[26px] shadow pl-4 pr-4 pb-4 pt-4">
                    <!-- Header: avatar + nome + total reviews -->
                    <div class="flex items-center mb-4">
                        <div v-if="c.photoLoading" class="w-12 h-12 rounded-full mr-3 flex items-center justify-center bg-gray-100">
                            <Spinner class="mx-auto" />
                        </div>
                        <img v-else :src="c.photoUrl || '/img/avatar.png'" :alt="c.username" 
                             class="w-12 h-12 rounded-full mr-3 object-cover"
                             @error="handleUserImageError(c)" />
                        <div class="flex flex-col items-start">
                            <span class="font-semibold text-gray-800">{{ c.username }}</span>
                            <span class="text-sm text-gray-500"> {{ c.date }}</span>
                        </div>
                    </div>

                    <!-- Rating -->
                    <star-rating :rating="c.rating" starColor="#065f46" :hideText="true" size="lg" class="pb-2"/>

                    <!-- Texto do comentário -->
                    <span class="text-base text-gray-700">{{ c.text }}</span>

                    <!-- Comentar -->
                    <div class="flex justify-end gap-2 mt-4">
                        <button @click="openReplyModal(c.id)" class="px-4 py-2 bg-gray-500 text-white rounded hover:bg-gray-700">
                            <span class="mr-2">Comentários</span>
                            <i class="fa-solid fa-comment"></i>
                        </button>
                    </div>

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

            <div v-else-if="reviews.length === 0 && !loadingReviews" class="flex items-center gap-2 justify-center">
                <i class="fa-regular fa-comment-dots text-5xl text-emerald-500"></i>
                <span class="text-xl text-gray-600 font-medium">Ainda sem reviews, seja o primeiro a avaliar!</span>
                <button @click="openModal" class="px-6 min-h-[35px] bg-emerald-600 text-white rounded-full hover:bg-emerald-700 transition-colors flex items-center">
                    <i class="fa-solid fa-star mr-2"></i>
                    Avaliar agora
                </button>
            </div>
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
            :reviewId="currentReviewId"
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
import { RestaurantService, ReviewService, DishService, UserService, ImageService } from '@/services';
import PhotosCarousel from '../utils/PhotosCarousel.vue';
import { useAuthStore } from '@/stores/auth';

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
            DishService: new DishService(),
            UserService: new UserService(),
            ImageService: new ImageService(),
            showModal: false,
            showReplyModal: false,
            restaurant: null,
            reviews: [],
            loadingReviews: false,
            errorReviews: null,
            dishes: [],
            loadindDishes: false,
            errorDishes: null,
            loading: true,
            error: null,
            restaurant: null,
            currentReviewId: null,
            isFavorite: false,
            authStore: useAuthStore(),
            showLoginWarning: false,
            isAddingToFavorites: false,
        };
    },
    created() {
        this.getUserInfo();
        this.fetchRestaurantDetails();
        this.fetchReviews();
        this.fetchDishes();
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
                // console.log("Restaurant details fetched:", this.restaurant.menuImages);
                // Check if restaurant is in favorites after loading details
                if (this.authStore.isAuthenticated) {
                    this.checkIfFavorite();
                }
            } catch (err) {
                this.error = err.message;
            } finally {
                this.loading = false;
            }
        },
        async fetchReviews() {
            this.loadingReviews = true;
            try {
                const reviews = await this.RestaurantService.getReviewsByRestaurant(this.id);
                // Process each review to handle user photos
                this.reviews = await Promise.all(reviews.map(async review => {
                    // Add photoLoading flag
                    review.photoLoading = true;
                    review.photoUrl = null;

                    try {
                        // Fetch user details for the review author
                        const userData = await this.UserService.getUserById(review.userId);
                        
                        // If user has an imageName, fetch the image
                        if (userData && userData.imageName) {
                            try {
                                const imageBlob = await this.ImageService.getImage(userData.imageName);
                                if (imageBlob) {
                                    review.photoUrl = URL.createObjectURL(imageBlob);
                                } else {
                                    review.photoUrl = '/img/avatar.png';
                                }
                            } catch (error) {
                                console.error('Error loading user image for review:', error);
                                review.photoUrl = '/img/avatar.png';
                            }
                        } else {
                            review.photoUrl = '/img/avatar.png';
                        }
                    } catch (error) {
                        console.error('Error fetching user details for review:', error);
                        review.photoUrl = '/img/avatar.png';
                    } finally {
                        review.photoLoading = false;
                    }
                    
                    return review;
                }));
            } catch (err) {
                this.errorReviews = err.message;
            } finally {
                this.loadingReviews = false;
            }
        },
        async fetchDishes() {
            this.loadindDishes = true;
            try {
                const dishes = await this.DishService.getDishesByRestaurant(this.id);
                this.dishes = dishes;
            } catch (err) {
                this.errorDishes = err.message;
            } finally {
                this.loadindDishes = false;
            }
        },
        formatPrice(v) {
            return `${v} €`;
        },
        averagePrice() {
            if (!this.dishes || !this.dishes.length) return 0;
            const total = this.dishes.reduce((sum, dish) => sum + dish.price, 0);
            return (total / this.dishes.length).toFixed(2);
        },
        openModal() {
            this.showModal = true;
        },
        closeModal() {
            this.showModal = false;
        },
        async handleReviewSubmitted(review) {
            // Refresh the reviews list and restaurant details
            await Promise.all([
                this.fetchRestaurantDetails(),
                this.fetchReviews()
            ]);
            // Show success message
            alert("Avaliação recebida com sucesso!");
        },
        openReplyModal(reviewId) {
            this.showReplyModal = true;
            this.currentReviewId = reviewId;
        },
        closeReplyModal() {
            this.showReplyModal = false;
        },
        handleReplySubmitted(reply) {
            alert("Comentário enviado com sucesso!");
        },
        getUserInfo() {
            // Check if user is authenticated using Pinia store
            if (this.authStore.isAuthenticated && this.authStore.currentUser) {
                this.checkIfFavorite();
            }
        },
        async checkIfFavorite() {
            // Make sure user is authenticated
            if (!this.authStore.isAuthenticated || !this.authStore.currentUser) return;
            
            try {
                const userId = this.authStore.currentUser.id;
                const favorites = await this.UserService.getFavorites(userId);
                if (favorites && Array.isArray(favorites)) {
                    // Convert both to strings for accurate comparison
                    this.isFavorite = favorites.some(fav => String(fav.id) === String(this.id));
                    // console.log('Is favorite?', this.isFavorite, 'Restaurant ID:', this.id);
                }
            } catch (error) {
                console.error('Erro ao verificar favoritos:', error);
            }
        },
        async toggleFavorite() {
            // Check if user is authenticated
            if (!this.authStore.isAuthenticated) {
                // Show the warning message instead of alert
                this.showLoginWarning = true;
                
                // Hide the warning after 3 seconds
                setTimeout(() => {
                    this.showLoginWarning = false;
                }, 3000);
                return;
            }

            try {
                this.isAddingToFavorites = true;
                const userId = this.authStore.currentUser.id;
                
                if (this.isFavorite) {
                    // If already a favorite, remove from favorites
                    await this.UserService.removeFromFavorites(userId, this.id);
                    this.isFavorite = false;
                    alert("Restaurante removido dos favoritos!");
                } else {
                    // If not a favorite, add to favorites
                    await this.UserService.addToFavorites(userId, this.id);
                    this.isFavorite = true;
                    alert("Restaurante adicionado aos favoritos!");
                }
                
                // Re-check favorites to ensure UI is in sync
                this.checkIfFavorite();
            } catch (error) {
                console.error('Erro ao atualizar favoritos:', error);
                alert("Ocorreu um erro ao atualizar favoritos.");
            } finally {
                this.isAddingToFavorites = false;
            }
        },
        handleUserImageError(review) {
            if (review) {
                review.photoUrl = '/img/avatar.png';
                review.photoLoading = false;
            }
        },
    },
    watch: {
        'authStore.isAuthenticated': {
            immediate: true,
            handler(newValue) {
                if (newValue && this.restaurant) {
                    this.checkIfFavorite();
                }
            }
        },
        restaurant: {
            immediate: false,
            handler(newValue) {
                if (newValue && this.authStore.isAuthenticated) {
                    this.checkIfFavorite();
                }
            }
        }
    },
};
</script>

<style scoped>
/* Nenhum CSS extra por agora — tudo feito via Tailwind */
</style>
