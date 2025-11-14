import { Link } from "react-router-dom";
import { useAuthStore } from "../store/auth";

export default function Navbar({ roles }) {
  const { logout } = useAuthStore();

  return (
    <nav className="bg-white shadow p-4 flex gap-4 items-center">
      <h1 className="font-bold text-xl">Dashboard</h1>

      {roles.includes("ADMIN") && (
        <Link to="/dashboard/hotels" className="text-blue-600">
          Hotels
        </Link>
      )}

      {roles.includes("USER") && (
        <Link to="/dashboard/reserva" className="text-blue-600">
          Reserva
        </Link>
      )}

      <button
        onClick={logout}
        className="ml-auto text-red-600 font-semibold"
      >
        Tancar sessió
      </button>
    </nav>
  );
}
