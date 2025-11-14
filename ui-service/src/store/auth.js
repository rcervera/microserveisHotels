
import { create } from "zustand";

export const useAuthStore = create((set) => ({
  token: null,
  roles: [],
  isAuthenticated: false,
  loading: false,
  error: null,

  login: async (username, password) => {
    set({ loading: true, error: null });

    try {
      // 👉 EXEMPLE (canvia-ho per la teva API real)
      const res = await fetch("/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });

      if (!res.ok) throw new Error("Credencials incorrectes");

      const data = await res.json(); // { token, roles:["ADMIN"] }

      set({
        token: data.token,
        roles: data.roles,
        isAuthenticated: true,
        loading: false,
      });
    } catch (err) {
      set({ error: err.message, loading: false });
    }
  },

  logout: () =>
    set({
      token: null,
      roles: [],
      isAuthenticated: false,
    }),
}));


// import { create } from "zustand";
// import axios from "axios";

// const API_URL = "/api/auth"; 

// export const useAuthStore = create((set) => ({
//   user: null,
//   token: null,
//   roles: [],
//   loading: false,
//   error: null,

//   login: async (username, password) => {
//     try {
//       set({ loading: true, error: null });
//       const res = await axios.post(`${API_URL}/login`, { username, password });
//       const { token, username: user, roles } = res.data;

//       localStorage.setItem("token", token);
//       set({ user, token, roles, loading: false });
//     } catch (err) {
//       set({
//         error: err.response?.data || "Error d'autenticació",
//         loading: false,
//       });
//     }
//   },

//   logout: () => {
//     localStorage.removeItem("token");
//     set({ user: null, token: null, roles: [] });
//   },
// }));
