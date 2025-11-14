import { useAuthStore } from "../store/auth";

export default function LlistaHotels() {
  const { logout } = useAuthStore();

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Llista d'Hotels (ADMIN)</h1>
      <p>Aquí es mostraran els hotels disponibles.</p>
      <button
        onClick={logout}
        className="mt-4 bg-gray-600 text-white px-4 py-2 rounded"
      >
        Tancar sessió
      </button>
    </div>
  );
}
