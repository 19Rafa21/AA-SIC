import { createRouter, createWebHistory } from "vue-router";
import HomePage from "@/components/HomePage.vue";
import Login from "@/components/Login.vue";
import RestaurantDetails from "@/components/RestaurantBlock/RestaurantDetails.vue";
import Register from "@/components/Register.vue";
import Profile from "@/components/Profile.vue";
import CreateRestaurant from "@/components/Profile/CreateRestaurant.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "homePage",
      component: HomePage,
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
    },
    {
      path: '/register',
      name: 'Register',
      component: Register,
    },
    {
      path: '/profile',
      name: 'Profile',
      component: Profile,
    },
    {
      path: '/restaurant/:id',
      name: 'RestaurantDetails',
      component: RestaurantDetails,
      props: true
    },
    {
      path: '/create-restaurant',
      name: 'CreateRestaurant',
      component: CreateRestaurant
    }
  ],
});

router.beforeEach((to, from, next) => {
  const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true'

  // Proteger a rota /profile
  if (to.path === '/profile' && !isLoggedIn) {
    return next('/login') // redireciona se não estiver logado
  }

  next()
})


export default router;
