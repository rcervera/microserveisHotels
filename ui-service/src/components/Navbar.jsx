import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { useAuthStore } from "../store/auth";
import { ShoppingCart, CalendarPlus, CalendarCheck, User2, History } from "lucide-react";

export default function Navbar({ roles }) {
  const { logout } = useAuthStore();
  const navigate = useNavigate();

  const isUser = roles.includes("USER");
  const isAdmin = roles.includes("ADMIN");

  const handleLogout = () => {
    logout();
    navigate("/login", { replace: true }); // ➜ redirecció immediata
  };

  return (
    <nav className="bg-white/90 backdrop-blur shadow-md px-6 py-3 flex items-center gap-6 border-b border-gray-200">
      
      {/* Logo */}
      <h1 className="font-bold text-2xl tracking-tight text-gray-800">
        HotelGrup
      </h1>

      {/* Enllaços ADMIN */}
      {isAdmin && (
        <>
          <Link to="/dashboard/hotels" className="nav-link">Hotels</Link>
          <Link to="/dashboard/admin/reserves" className="nav-link">Gestió Reserves</Link>
        </>
      )}

      {/* Enllaços USER */}
      {isUser && (
        <>
          <Link to="/dashboard/reserva" className="nav-link flex items-center gap-2">
            <CalendarPlus size={18} />
            Fer reserva
          </Link>

          <Link to="/dashboard/les-meves-reserves" className="nav-link flex items-center gap-2">
            <CalendarCheck size={18} />
            Les meves reserves
          </Link>

          <Link to="/dashboard/carret" className="nav-link flex items-center gap-2">
            <ShoppingCart size={18} />
            Carret
            {/* Aquí podries mostrar un badge amb la quantitat */}
            {/* <span className="ml-1 bg-blue-600 text-white px-2 py-0.5 rounded text-xs">3</span> */}
          </Link>

          <Link to="/dashboard/historial" className="nav-link flex items-center gap-2">
            <History size={18} />
            Historial d’estades
          </Link>

          <Link to="/dashboard/perfil" className="nav-link flex items-center gap-2">
            <User2 size={18} />
            Perfil
          </Link>
        </>
      )}

      {/* Botó logout */}
      <button
       onClick={handleLogout}
        className="ml-auto text-red-600 font-semibold hover:text-red-700 transition"
      >
        Tancar sessió
      </button>
    </nav>
  );
}
