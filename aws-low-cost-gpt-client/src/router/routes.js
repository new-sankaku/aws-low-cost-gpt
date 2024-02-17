import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/",
    name: "UserLogin",
    component: () => import("pages/UserLogin.vue"),
    meta: {
      title: "Low cost AI. Login",
      structuredData: {
        "@type": "WebPage",
        name: "Low cost AI. Login1",
        description: "Sign-in is currently frozen.",
      },
    },
  },
  {
    path: "/UserChat",
    name: "UserChat",
    component: () => import("pages/MainLayout.vue"),
    meta: {
      requiresAuth: true,
      title: "Low cost AI. test chat view.",
      structuredData: {
        "@type": "WebPage",
        name: "Low cost AI. test chat view!!",
        description: "This is a feature under development.",
      },
    },
    children: [
      {
        path: "",
        name: "Main_Child",
        components: {
          headerComponent: () => import("components/HeaderDrawer.vue"),
          rightComponent: () => import("components/RightDrawer.vue"),
          leftComponent: () => import("components/LeftDrawer.vue"),
          centerComponent: () => import("components/CenterPage.vue"),
          fotterComponent: () => import("components/FotterDrawer.vue"),
        },
      },
    ],
  },
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/error/ErrorNotFound.vue"),
  },
];

export default routes;
