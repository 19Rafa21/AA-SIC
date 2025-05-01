<template>
    <div>
        <header-component 
            :backgroundImage="backgroundImage"
            logoImage="../../public/img/logo.png"
            :useGradient="true"
        />

        <div class="bg-white rounded-lg shadow-xl mx-3 md:mx-4 -mt-24 relative z-10 p-6">
            <div class="container mx-auto px-4">
                <div class="py-16 text-center">
                    <h2 class="text-3xl font-bold mt-8 mb-6">Bem-vindo à Tasty Check</h2>
                    <p class="text-gray-600 mb-8">
                        A plataforma perfeita para os amantes de gastronomia compartilharem suas experiências
                    </p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import HeaderComponent from './HeaderComponent.vue';

export default {
    name: 'HomePage',
    components: {
        HeaderComponent
    },
    data() {
        return {
            backgroundImage: '../../public/img/header-bg.webp', 
        }
    }
}
</script>