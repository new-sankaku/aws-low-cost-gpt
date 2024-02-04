import { route } from "quasar/wrappers";
import {
  createRouter,
  createMemoryHistory,
  createWebHistory,
  createWebHashHistory,
} from "vue-router";
import routes from "./routes";

function isTokenValid() {
  const token = localStorage.getItem("user-token");
  if (!token) return false;

  try {
    const payload = JSON.parse(atob(token.split(".")[1]));
    return payload.exp > Date.now() / 1000;
  } catch (error) {
    console.error("Token parsing error:", error);
    return false;
  }
}

export default route(function (/* { store, ssrContext } */) {
  const createHistory = process.env.SERVER
    ? createMemoryHistory
    : process.env.VUE_ROUTER_MODE === "history"
    ? createWebHistory
    : createWebHashHistory;

  const Router = createRouter({
    scrollBehavior: () => ({ left: 0, top: 0 }),
    routes,

    history: createHistory(process.env.VUE_ROUTER_BASE),
  });

  Router.beforeEach((to, from, next) => {
    console.log("Navigating to:", to.name);

    if (to.matched.some((record) => record.meta.requiresAuth)) {
      if (!isTokenValid()) {
        next({ name: "UserLogin" });
      } else {
        next();
      }
    } else {
      next();
    }
  });

  return Router;
});
