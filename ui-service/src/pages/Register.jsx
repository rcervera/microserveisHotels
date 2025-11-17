import { useState } from "react";
import { useAuthStore } from "../store/auth";
import { useNavigate, Link } from "react-router-dom";

export default function Register() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const { register, loading, error } = useAuthStore();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    await register(username, password);

    if (useAuthStore.getState().isAuthenticated) {
      navigate("/dashboard");
    }
  };

  return (
    <div className="relative min-h-screen flex items-center justify-center bg-black">
      {/* Fons amb imatge + overlay */}
      <div className="absolute inset-0 -z-10">
        <img
          src="https://images.unsplash.com/photo-1551776235-dde6d4829808?auto=format&fit=crop&w=2070&q=80"
          alt="Hotel de fons"
          className="w-full h-full object-cover"
          onError={(e) => {
            e.target.onerror = null;
            e.target.src =
              "https://placehold.co/1920x1080/333/FFF?text=Hotel";
          }}
        />
        <div className="absolute inset-0 bg-black/60"></div>
      </div>

      {/* Targeta del formulari */}
      <div className="bg-white/10 backdrop-blur-md border border-white/20 p-8 rounded-2xl shadow-2xl w-80 sm:w-96 text-white">
        <h2 className="text-3xl font-bold mb-6 text-center tracking-tight">
          Crea el teu compte
        </h2>

        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block text-sm mb-1">Usuari</label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="w-full px-3 py-2 rounded-lg bg-white text-gray-900 border border-gray-300 focus:ring-2 focus:ring-white focus:outline-none"
              placeholder="Nom d'usuari"
            />
          </div>

          <div className="mb-4">
            <label className="block text-sm mb-1">Contrasenya</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full px-3 py-2 rounded-lg bg-white text-gray-900 border border-gray-300 focus:ring-2 focus:ring-white focus:outline-none"
              placeholder="••••••••"
            />
          </div>

          {error && (
            <p className="text-red-300 text-sm mb-3">{error}</p>
          )}

          <button
            type="submit"
            disabled={loading}
            className="w-full py-2 mt-2 bg-white text-gray-900 font-semibold rounded-lg shadow-lg hover:bg-gray-200 transition-all"
          >
            {loading ? "Creant compte..." : "Registrat"}
          </button>
        </form>

        <p className="text-center text-sm mt-4 text-gray-200">
          Ja tens compte?{" "}
          <Link to="/login" className="underline hover:text-white">
            Inicia sessió
          </Link>
        </p>
      </div>
    </div>
  );
}
