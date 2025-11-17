import { useState } from "react";
import { useAuthStore } from "../store/auth";
import { useNavigate, Link } from "react-router-dom";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const { login, loading, error } = useAuthStore();

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    await login(username, password);

    if (useAuthStore.getState().isAuthenticated) {
      navigate("/dashboard");
    }
  };

  return (
    <div className="relative min-h-screen flex items-center justify-center font-inter">
      {/* Imatge de fons + Overlay */}
      <div className="absolute inset-0 z-0">
        <img
          src="https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=2070&q=80"
          alt="Hotel de luxe"
          className="w-full h-full object-cover"
          onError={(e) => {
            e.target.onerror = null;
            e.target.src =
              "https://placehold.co/1920x1080/333/FFF?text=Hotel+Image";
          }}
        />
        <div className="absolute inset-0 bg-black/60"></div>
      </div>

      {/* Contenidor del formulari */}
      <div className="relative z-10 w-full max-w-sm bg-white/90 backdrop-blur-xl rounded-xl shadow-2xl p-8 border border-white/20">
        <h2 className="text-3xl font-bold mb-6 text-center text-gray-900 tracking-tight">
          Inici de sessió
        </h2>

        <form onSubmit={handleSubmit}>
          <label className="block mb-4">
            <span className="text-gray-700 font-medium">Usuari</span>
            <input
              type="text"
              placeholder="Introdueix el teu usuari"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="mt-1 w-full px-3 py-2 rounded-lg border border-gray-300 bg-white text-gray-900 
                         focus:outline-none focus:ring-2 focus:ring-white focus:border-white shadow-sm"
            />
          </label>

          <label className="block mb-4">
            <span className="text-gray-700 font-medium">Contrasenya</span>
            <input
              type="password"
              placeholder="•••••••••"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="mt-1 w-full px-3 py-2 rounded-lg border border-gray-300 bg-white text-gray-900 
                         focus:outline-none focus:ring-2 focus:ring-white focus:border-white shadow-sm"
            />
          </label>

          {error && (
            <p className="text-red-500 text-sm font-semibold mb-3 text-center">
              {error}
            </p>
          )}

          <button
            type="submit"
            disabled={loading}
            className="w-full py-3 bg-white text-gray-900 font-semibold rounded-lg shadow-lg border border-gray-200
                       hover:bg-gray-200 transition-colors duration-300 active:scale-95"
          >
            {loading ? "Entrant..." : "Entrar"}
          </button>

          <p className="text-center text-sm mt-4 text-gray-700">
            Encara no tens un compte?
            <Link
              to="/register"
              className="ml-1 font-semibold text-gray-900 underline hover:text-gray-700"
            >
              Registra't
            </Link>
          </p>

          <p className="text-center text-sm mt-2">
            <Link
              to="/"
              className="text-gray-300 hover:text-white underline transition"
            >
              Tornar a l'inici
            </Link>
          </p>
        </form>
      </div>
    </div>
  );
}
