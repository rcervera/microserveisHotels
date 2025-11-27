import { BrowserRouter, Routes, Route } from "react-router-dom";
import DashboardLayout from "./layouts/DashboardLayout";
import Hotels from "./pages/HotelList";
import Reserva from "./pages/Reserva";
import Login from "./pages/Login";
import Landing from "./pages/Landing";
import Register from "./pages/Register";
import { useAuthStore } from "./store/auth";
import SearchPage from "./pages/SearchPage";

export default function App() {
  const roles = useAuthStore((state) => state.roles);

  return (
    <BrowserRouter>
      <Routes>

        {/* Landing Page */}
        <Route path="/" element={<Landing />} />

        {/* Auth */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* Dashboard Layout amb Navbar persistent */}
        <Route
          path="/dashboard"
          element={<DashboardLayout roles={roles} />}
        >
          {/* Ruta per defecte del dashboard */}
          <Route index element={<p className="text-xl">Benvingut al teu panell!</p>} />

          {/* Subrutes */}
          <Route path="hotels" element={<Hotels />} />
          <Route path="reserva" element={<Reserva />} />
          <Route path="search" element={<SearchPage />} />
        </Route>

      </Routes>
    </BrowserRouter>
  );
}




// import React, { useState, useContext, createContext } from 'react';
// import { BrowserRouter, Routes, Route, Navigate, Link } from 'react-router-dom';

// // --- SIMULACIÓ DE 'useAuthStore' ---
// // Com que no tenim accés al fitxer './store/auth', creem una simulació
// // per fer que l'aplicació funcioni.
// // Això fa que 'useAuthStore' estigui disponible a tota l'app.

// const AuthContext = createContext();

// // Hook per accedir a l'estat d'autenticació
// const useAuthStore = () => useContext(AuthContext);

// // Provider que embolcalla l'app i gestiona l'estat
// const AuthProvider = ({ children }) => {
//   const [token, setToken] = useState(null);
//   const [roles, setRoles] = useState([]);
//   const [loading, setLoading] = useState(false);
//   const [error, setError] = useState(null);

//   const login = async (username, password) => {
//     setLoading(true);
//     setError(null);
//     // Simulem una petició a l'API
//     await new Promise(resolve => setTimeout(resolve, 1000));

//     if (username === 'admin' && password === 'admin') {
//       setToken('fake-admin-token');
//       setRoles(['ADMIN', 'USER']);
//       setLoading(false);
//     } else if (username === 'user' && password === 'user') {
//       setToken('fake-user-token');
//       setRoles(['USER']);
//       setLoading(false);
//     } else {
//       setError('Usuari o contrasenya incorrectes');
//       setLoading(false);
//     }
//   };

//   const logout = () => {
//     setToken(null);
//     setRoles([]);
//     setError(null);
//   };

//   return (
//     <AuthContext.Provider value={{ token, roles, loading, error, login, logout }}>
//       {children}
//     </AuthContext.Provider>
//   );
// };



// // --- 2. Components de les Pàgines ---
// // Ara tots els components viuen dins d'aquest fitxer per evitar errors d'importació.

// // --- Component Login (Basat en el teu codi) ---
// const Login = () => {
//   const [username, setUsername] = useState("");
//   const [password, setPassword] = useState("");
//   const { login, error, loading, roles } = useAuthStore(); // Ara funciona!
//   const navigate = useNavigate();

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     await login(username, password);

//     // Després del login, l'estat (roles) del context s'haurà actualitzat.
//     // L'AppLogic s'encarregarà de la redirecció automàticament.
//     // Però per si de cas, podem forçar la navegació aquí si el login ha tingut èxit.
//   };
  
//   // Afegeix un useEffect per redirigir quan 'roles' canviï DESPRÉS del login
//   React.useEffect(() => {
//     if (roles.length > 0) {
//         const authenticatedHome = roles.includes("ADMIN") ? "/hotels" : "/reserva";
//         navigate(authenticatedHome, { replace: true });
//     }
//   }, [roles, navigate]);

//   return (
//     <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
//       <form
//         onSubmit={handleSubmit}
//         className="bg-white p-6 rounded-xl shadow-md w-80 text-gray-800"
//       >
//         <h2 className="text-2xl font-bold mb-4 text-center">Inici de sessió</h2>
        
//         <p className="text-xs text-center text-gray-500 mb-4">
//           (Simulació: admin/admin o user/user)
//         </p>

//         <input
//           type="text"
//           placeholder="Usuari"
//           value={username}
//           onChange={(e) => setUsername(e.target.value)}
//           className="border p-2 w-full mb-3 rounded text-gray-800"
//         />

//         <input
//           type="password"
//           placeholder="Contrasenya"
//           value={password}
//           onChange={(e) => setPassword(e.target.value)}
//           className="border p-2 w-full mb-3 rounded text-gray-800"
//         />

//         {error && <p className="text-red-500 text-sm mb-3">{error}</p>}

//         <button
//           type="submit"
//           disabled={loading}
//           className="bg-blue-500 text-white w-full py-2 rounded hover:bg-blue-600 disabled:bg-gray-400"
//         >
//           {loading ? "Entrant..." : "Entrar"}
//         </button>
        
//         <Link to="/" className="block text-center mt-4 text-blue-600 hover:underline">
//           &larr; Torna a la portada
//         </Link>
//       </form>
//     </div>
//   );
// }

// // --- Component Register (Substitució) ---
// const Register = () => {
//   return (
//     <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
//       <div className="p-8 bg-white rounded-lg shadow-xl max-w-sm w-full text-gray-800">
//         <h2 className="text-3xl font-bold text-center text-gray-800 mb-6">Registra't</h2>
//         <p className="text-center text-gray-600">Aquesta seria la pàgina de registre.</p>
//         <form className="space-y-4 mt-4">
//           <div>
//             <label className="block text-sm font-medium text-gray-700">Email</label>
//             <input type="email" className="w-full px-3 py-2 border border-gray-300 rounded-lg" placeholder="el.teu@email.com" />
//           </div>
//           <div>
//             <label className="block text-sm font-medium text-gray-700">Contrasenya</label>
//             <input type="password" className="w-full px-3 py-2 border border-gray-300 rounded-lg" />
//           </div>
//           <button
//             type="submit"
//             disabled
//             className="w-full px-4 py-3 font-semibold text-white bg-blue-600 rounded-lg cursor-not-allowed"
//           >
//             Crear Compte (Simulat)
//           </button>
//         </form>
//         <Link to="/" className="block text-center mt-6 text-blue-600 hover:underline">
//           &larr; Torna a la portada
//         </Link>
//       </div>
//     </div>
//   );
// };

// // --- Pàgina Protegida Base (Per LlistaHotels i Reserva) ---
// const ProtectedPageBase = ({ title, children }) => {
//   const { logout, roles } = useAuthStore();
//   return (
//     <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 text-gray-800">
//       <div className="p-8 bg-white rounded-lg shadow-xl text-center">
//         <h1 className="text-4xl font-bold mb-4">{title}</h1>
//         <p className="text-lg mb-4">Has entrat correctament.</p>
//         <p className="mb-6">
//           <span className="font-semibold">Rols:</span> {roles.join(', ')}
//         </p>
//         {children}
//         <button
//           onClick={logout}
//           className="mt-6 px-6 py-2 font-semibold text-white bg-red-600 rounded-lg hover:bg-red-700 transition-colors"
//         >
//           Tanca Sessió
//         </button>
//       </div>
//     </div>
//   );
// };

// // --- Component LlistaHotels (Substitució) ---
// const LlistaHotels = () => (
//   <ProtectedPageBase title="Panel d'Admin (Hotels)">
//     <p>Aquí es mostraria la llista d'hotels per a l'ADMIN.</p>
//   </ProtectedPageBase>
// );

// // --- Component Reserva (Substitució) ---
// const Reserva = () => (
//   <ProtectedPageBase title="Les Meves Reserves (User)">
//     <p>Aquí l'usuari (USER) podria veure o fer reserves.</p>
//   </ProtectedPageBase>
// );


// // --- 3. Component App (Lògica de Rutes) ---
// // Aquesta és la lògica principal que fa servir el 'useAuthStore' simulat
// function AppLogic() {
//   const { token, roles } = useAuthStore();

//   // Determina on redirigir si l'usuari ja està loggejat
//   const authenticatedHome = roles.includes("ADMIN") ? "/hotels" : "/reserva";

//   return (
//     <Routes>
//       {/* RUTA ARREL: */}
//       <Route
//         path="/"
//         element={token ? <Navigate to={authenticatedHome} replace /> : <LandingPage />}
//       />

//       {/* RUTA LOGIN: */}
//       <Route
//         path="/login"
//         element={token ? <Navigate to={authenticatedHome} replace /> : <Login />}
//       />
      
//       {/* RUTA REGISTER: */}
//       <Route
//         path="/register"
//         element={token ? <Navigate to={authenticatedHome} replace /> : <Register />}
//       />

//       {/* RUTES PROTEGIDES */}
      
//       {/* RUTA HOTELS (Admin) */}
//       <Route
//         path="/hotels"
//         element={
//           token && roles.includes("ADMIN") ? (
//             <LlistaHotels />
//           ) : (
//             <Navigate to="/" replace />
//           )
//         }
//       />

//       {/* RUTA RESERVA (User) */}
//       <Route
//         path="/reserva"
//         element={
//           token && roles.includes("USER") ? (
//             <Reserva />
//           ) : (
//             <Navigate to="/" replace />
//           )
//         }
//       />

//       {/* Qualsevol altra ruta redirigeix a l'arrel */}
//       <Route path="*" element={<Navigate to="/" replace />} />
//     </Routes>
//   );
// }

// // --- Component Arrel per embolcallar amb Providers ---
// // Ara embolcallem l'app amb el 'BrowserRouter' i el nostre 'AuthProvider' simulat
// export default function App() {
//   return (
//     <BrowserRouter>
//       <AuthProvider>
//         <AppLogic />
//       </AuthProvider>
//     </BrowserRouter>
//   );
// }