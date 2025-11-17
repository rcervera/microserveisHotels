import { create } from "zustand";
import { persist } from "zustand/middleware";

export const useAuthStore = create(
  persist(
    (set) => ({
      user: null,
      token: null,
      roles: [],
      isAuthenticated: false,
      loading: false,
      error: null,

      login: async (username, password) => {
        set({ loading: true, error: null });

        try {
          const res = await fetch("/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password }),
          });

          if (!res.ok) throw new Error("Credencials incorrectes");

          const data = await res.json(); // token, roles, username

          set({
            user: data.username,
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
          user: null,
          token: null,
          roles: [],
          isAuthenticated: false,
        }),
    }),

    {
      name: "auth-storage", // nom del localStorage
    }
  )
);
