import React, { useState, useEffect, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { Search, MapPin } from 'lucide-react'; 
import axios from 'axios'; 

// Color de Marca Personalitzat (Turques elegant)
const ACCENT_COLOR = '#4ECDC4'; 
const BG_IMAGE_URL = 'https://picsum.photos/seed/travel/1920/1080'; // URL més simple i fiable

export default function SearchPanel() {
    // ... (Logica de dades i interacció sense canvis)

  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState('');
  const [allLocations, setAllLocations] = useState([]); 
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isSuggestionsVisible, setIsSuggestionsVisible] = useState(false);
  const [isImageLoaded, setIsImageLoaded] = useState(false); // Nou estat per la imatge

  // --- LOGICA DE DADES ---
  useEffect(() => {
    // Funció que fa la crida a l'API amb Axios (sense canvis)
    const fetchLocations = async () => {
      // ... (càrrega de dades)
      try {
        const response = await axios.get('/api/hotels/cities');
        if (Array.isArray(response.data) && response.data.length > 0 && typeof response.data[0] === 'object') {
            const cityNames = response.data
                .map(item => item.name) 
                .filter(name => typeof name === 'string' && name.trim() !== '');
            setAllLocations(cityNames.sort());
        } else {
            throw new Error("El format de dades de l'API no és un array vàlid.");
        }
      } catch (err) {
        console.error("Error al carregar les localitzacions:", err);
        setError("No s'han pogut carregar les localitzacions. Prova a escriure igualment.");
      } finally {
        setLoading(false);
      }
    };
    
    // Comprovem si la imatge es carrega correctament
    const img = new Image();
    img.src = BG_IMAGE_URL;
    img.onload = () => setIsImageLoaded(true);
    img.onerror = () => setIsImageLoaded(false); // Estableix a false si falla

    fetchLocations();
  }, []); 
    
  const filteredSuggestions = useMemo(() => {
    // ... (lògica de filtre sense canvis)
    if (searchTerm.length === 0) {
      return allLocations.slice(0, 5); 
    }
    const lowerCaseSearch = searchTerm.toLowerCase().trim();
    return allLocations.filter(location => 
      location.toLowerCase().startsWith(lowerCaseSearch)
    ).slice(0, 8); 
  }, [searchTerm, allLocations]);
  // --- FI LOGICA DE DADES ---

  // ... (Gestió d'interacció: handleSearch, handleSelectSuggestion, handleInputChange, handleInputBlur sense canvis)
  const handleSearch = (e) => {
    e.preventDefault();
    const city = searchTerm.trim();
    if (city) {
      setIsSuggestionsVisible(false);
      navigate(`/hotels?city=${encodeURIComponent(city)}`);
    }
  };

  const handleSelectSuggestion = (location) => {
    setSearchTerm(location);
    setIsSuggestionsVisible(false);
    navigate(`/hotels?city=${encodeURIComponent(location)}`);
  };

  const handleInputChange = (e) => {
    setSearchTerm(e.target.value);
    setIsSuggestionsVisible(true);
  };
  
  const handleInputBlur = () => {
    setTimeout(() => setIsSuggestionsVisible(false), 200); 
  }
  
  const shouldShowSuggestions = !loading && !error && isSuggestionsVisible && (
    searchTerm.length > 0 || (searchTerm.length === 0 && filteredSuggestions.length > 0)
  );

  // --- RENDERITZAT ---
  return (
    // CONTENIDOR PRINCIPAL: Ús de classes Tailwind condicionals
    <div className={`p-8 mx-auto max-w-4xl w-full rounded-xl shadow-2xl relative overflow-hidden transition-all duration-500 ${!isImageLoaded ? 'bg-gray-100' : ''}`} 
         style={{ 
            backgroundImage: isImageLoaded ? `url(${BG_IMAGE_URL})` : 'none',
            backgroundSize: 'cover',
            backgroundPosition: 'center',
         }}
    >
        {/* Overlay clar (Sempre present per suavitzar) */}
        {/* Fons: Blanc semi-transparent si la imatge carrega. Degradat de gris si NO carrega. */}
        <div className={`absolute inset-0 z-0 ${isImageLoaded ? 'bg-white/80 backdrop-blur-sm' : 'bg-gradient-to-br from-white to-gray-50'}`}></div>
        
        {/* Contingut del panell */}
        <div className="relative z-10">
            
            {/* Títol Gran i Centrat */}
            <h1 className="text-4xl font-extrabold text-gray-800 mb-8 text-center tracking-tight">
                On vols anar?
            </h1>

            {/* Subtítol més suau */}
            <p className="text-lg text-gray-600 mb-8 text-center max-w-lg mx-auto">
                Utilitza el nostre cercador d'ubicacions per trobar els millors allotjaments.
            </p>
            
            {/* 🚀 FORMULARI */}
            <div className="relative z-10 mx-auto max-w-xl">
                
                <form 
                    onSubmit={handleSearch} 
                    className="flex items-stretch w-full rounded-xl bg-white border-2 border-gray-300 focus-within:border-gray-500 transition-all duration-300 shadow-xl overflow-hidden"
                >
                    {/* Input de Cerca */}
                    <input
                        type="text"
                        value={searchTerm}
                        onChange={handleInputChange}
                        onFocus={() => setIsSuggestionsVisible(true)} 
                        onBlur={handleInputBlur} 
                        placeholder={loading ? "Carregant ciutats..." : "Introdueix la ciutat de la reserva..."}
                        className="flex-grow py-4 px-4 text-lg font-medium text-gray-800 bg-transparent outline-none placeholder-gray-400"
                        disabled={loading || !!error}
                        autoComplete="off"
                        aria-label="Cercar ciutat d'hotel"
                    />

                    {/* Botó de Cerca (Nou Color de Marca) */}
                    <button
                        type="submit"
                        className="flex-shrink-0 px-6 text-white font-bold text-lg transition-colors duration-200 disabled:opacity-50 flex items-center space-x-2"
                        style={{ backgroundColor: ACCENT_COLOR }}
                        disabled={loading || !!error || !searchTerm.trim()}
                    >
                        <Search className="w-5 h-5" />
                        <span className="hidden sm:inline">Cercar</span>
                    </button>
                
                </form>

                {/* Llista de Suggeriments (Sota el formulari) */}
                {shouldShowSuggestions && filteredSuggestions.length > 0 && (
                    <div className="absolute w-full mt-2 bg-white rounded-lg shadow-xl border border-gray-200 max-h-56 overflow-y-auto z-20 text-left">
                        <ul className="py-1">
                            {filteredSuggestions.map((location) => (
                                <li
                                    key={location}
                                    onMouseDown={() => handleSelectSuggestion(location)}
                                    className="px-4 py-2 text-md font-normal text-gray-700 hover:bg-gray-50 cursor-pointer transition-colors duration-150 flex items-center space-x-2"
                                >
                                    <MapPin className="w-4 h-4 text-gray-400" />
                                    <span>{location}</span>
                                </li>
                            ))}
                        </ul>
                    </div>
                )}
                
                {/* Missatges d'Estat */}
                {(loading || error || (searchTerm.length > 0 && filteredSuggestions.length === 0)) && (
                    <div className="mt-4 text-center">
                        {/* ... (Missatges de càrrega i error sense canvis) */}
                        {loading && <p className="text-sm text-gray-600">Carregant localitzacions...</p>}
                        {error && <p className="text-sm text-red-600 font-medium">⚠️ {error}</p>}
                        {!loading && !error && searchTerm.length > 0 && filteredSuggestions.length === 0 && (
                            <p className="text-sm text-gray-500">
                                No s'han trobat ciutats disponibles que coincideixin amb **"{searchTerm}"**.
                            </p>
                        )}
                    </div>
                )}

            </div>
            {/* FI CONTENIDOR DE CERCA */}

        </div>
    </div>
  );
}