import { createRouter, createWebHistory } from "vue-router";
import HomePage from "@/components/HomePage.vue";
import Login from "@/components/Login.vue";
import RestaurantDetails from "@/components/RestaurantBlock/RestaurantDetails.vue";

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
      path: '/restaurant/:name',
      name: 'RestaurantDetails',
      component: RestaurantDetails,
      props: true
    }
  ],
});

export default router;
