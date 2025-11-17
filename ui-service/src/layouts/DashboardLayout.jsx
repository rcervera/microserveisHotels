import Navbar from "../components/Navbar";
import { Outlet, Navigate } from "react-router-dom";
import { useAuthStore } from "../store/auth";

export default function DashboardLayout({ roles }) {

  const { isAuthenticated } = useAuthStore();
  if (!isAuthenticated) return <Navigate to="/login" replace />;

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col">
      <Navbar roles={roles} />
      <main className="flex-1 p-6 max-w-6xl mx-auto w-full">
        <Outlet />
      </main>
    </div>
  );
}
