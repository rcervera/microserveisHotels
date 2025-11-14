
import { create } from "zustand";
import axios from "axios";

const API_URL = "/api/auth"; 

export const useAuthStore = create((set) => ({
  user: null,
  token: null,
  roles: [],
  loading: false,
  error: null,

  login: async (username, password) => {
    try {
      set({ loading: true, error: null });
      const res = await axios.post(`${API_URL}/login`, { username, password });
      const { token, username: user, roles } = res.data;

      localStorage.setItem("token", token);
      set({ user, token, roles, loading: false });
    } catch (err) {
      set({
        error: err.response?.data || "Error d'autenticació",
        loading: false,
      });
    }
  },

  logout: () => {
    localStorage.removeItem("token");
    set({ user: null, token: null, roles: [] });
  },
}));
