<template>
    <header class="relative">
        <top-navbar />

        <div
            class="relative bg-cover bg-center min-h-screen"
            :style="{ backgroundImage: `url(${backgroundImage})` }"
        >
            <!-- <span 
                v-if="useGradient" 
                class="absolute inset-0 bg-gradient-to-r from-emerald-800/60 to-teal-800/60">
            </span> -->
            
            <div class="container mx-auto px-4 h-full flex flex-col justify-center items-center">
                <div class="flex items-center justify-center mb-2">
                    <h1 class="text-6xl font-bold flex items-center">
                        <span class="text-white">Tasty</span>
                        <span class="inline-flex items-center">
                            <span class="text-emerald-200">Check</span>
                            <img :src="logoImage" alt="Logo" class="w-20 h-20 ml-2 -mt-2" />
                        </span>
                    </h1>
                </div>
                
                <p class="text-xl mt-2 mb-8 text-white opacity-90 px-5 text-center font-medium max-w-2xl">
                    Sabores incríveis, momentos inesquecíveis.
                </p>
                
                <search-bar class="mt-2 w-full" />
            </div>
        </div>
    </header>
</template>

<script>
import TopNavbar from './TopNavbar.vue';
import SearchBar from './SearchBar.vue';

export default {
    name: 'HeaderComponent',
    components: {
        TopNavbar,
        SearchBar
    },
    props: {
        backgroundImage: {
            type: String,
            default: '../../public/img/header-bg.webp'
        },
        logoImage: {
            type: String,
            default: '../../public/img/logo.png'
        },
        useGradient: {
            type: Boolean,
            default: true
        }
    }
}
</script>

<style scoped>
/* Classes adicionais podem ser aplicadas aqui se necessário */
</style>