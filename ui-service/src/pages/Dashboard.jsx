import { useAuthStore } from "../store/auth";
import { Navigate } from "react-router-dom";
import Navbar from "../components/Navbar";
import Reserva from "./Reserva";
import HotelList from "./HotelList";

export default function Dashboard() {
  const { isAuthenticated, roles } = useAuthStore();

  if (!isAuthenticated) return <Navigate to="/login" />;

  // Component inicial segons rol
  let InitialComponent = null;
  if (roles.includes("ADMIN")) InitialComponent = <HotelList />;
  else InitialComponent = <Reserva />;

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col">
      <Navbar roles={roles} />

      <div className="p-4">
        {InitialComponent}
      </div>
    </div>
  );
}
