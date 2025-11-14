import { useAuthStore } from "../store/auth";

export default function Reserva() {
  const { logout } = useAuthStore();

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Formulari de Reserva (USER)</h1>
      <form className="flex flex-col w-80">
        <input
          type="text"
          placeholder="Nom de l'hotel"
          className="border p-2 mb-3 rounded"
        />
        <input
          type="date"
          className="border p-2 mb-3 rounded"
        />
        <button
          type="submit"
          className="bg-green-500 text-white py-2 rounded"
        >
          Reservar
        </button>
      </form>
      <button
        onClick={logout}
        className="mt-4 bg-gray-600 text-white px-4 py-2 rounded"
      >
        Tancar sessió
      </button>
    </div>
  );
}
