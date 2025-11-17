import React, { useState, useEffect } from "react";
import axios from "axios";

export default function HotelList() {
  const [pageData, setPageData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(5);
  const [keyword, setKeyword] = useState("");
  const [searchTerm, setSearchTerm] = useState("");

  // Debounce
  useEffect(() => {
    const timer = setTimeout(() => {
      setKeyword(searchTerm);
      setCurrentPage(0);
    }, 400);
    return () => clearTimeout(timer);
  }, [searchTerm]);

  // Fetch hotels
  useEffect(() => {
    const fetchHotels = async () => {
      setLoading(true);
      try {
        const response = await axios.get(
          `/api/hotels?page=${currentPage}&size=${pageSize}&keyword=${keyword}`
        );
        setPageData(response.data);
        setError(null);
      } catch (err) {
        setError(err);
        setPageData(null);
      } finally {
        setLoading(false);
      }
    };
    fetchHotels();
  }, [keyword, currentPage, pageSize]);

  const renderList = () => {
    if (loading && !pageData)
      return <p className="text-gray-600 text-center py-6">Carregant hotels...</p>;

    if (error)
      return (
        <p className="text-red-600 text-center py-6">
          Error carregant dades: {error.message}
        </p>
      );

    if (!pageData || pageData.content.length === 0)
      return <p className="text-gray-600">No s’han trobat hotels.</p>;

    return (
      <ul className="space-y-4">
        {pageData.content.map((hotel) => (
          <li
            key={hotel.id}
            className="bg-white border border-gray-200 p-4 rounded-lg shadow-sm hover:shadow transition"
          >
            <h3 className="text-lg font-semibold text-gray-900">{hotel.name}</h3>
            <p className="text-gray-700 text-sm mt-1">📍 {hotel.location}</p>
            <p className="text-gray-600 text-sm">{hotel.address}</p>
          </li>
        ))}
      </ul>
    );
  };

  return (
    <div className="max-w-3xl mx-auto p-4">

      {/* Títol */}
      <h2 className="text-3xl font-bold text-gray-900 mb-6">🏨 Llista d’Hotels</h2>

      {/* Input cerca */}
      <input
        type="text"
        placeholder="Cerca per nom, adreça, localitat..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        className="w-full p-3 rounded-lg bg-gray-100 border border-gray-300 text-gray-800 placeholder-gray-500 mb-6 focus:outline-none focus:ring-2 focus:ring-blue-400"
      />

      {/* Llista */}
      <div className={loading ? "opacity-50" : "opacity-100"}>
        {renderList()}
      </div>

      {/* Paginació */}
      {!loading && pageData && (
        <div className="flex justify-between items-center mt-6">

          {/* Select de mida */}
          <div className="flex items-center gap-2">
            <span className="text-gray-700 text-sm">Resultats per pàgina:</span>
            <select
              value={pageSize}
              onChange={(e) => setPageSize(Number(e.target.value))}
              className="bg-gray-100 px-3 py-2 border border-gray-300 rounded-lg text-gray-800"
            >
              <option value="5">5</option>
              <option value="10">10</option>
              <option value="15">15</option>
            </select>
          </div>

          {/* Navegació */}
          {pageData.totalPages > 1 && (
            <div className="flex items-center gap-4">

              <button
                onClick={() => !pageData.first && setCurrentPage(currentPage - 1)}
                className="px-4 py-2 bg-blue-500 text-white rounded-lg disabled:opacity-40 hover:bg-blue-600 transition"
                disabled={pageData.first}
              >
                &laquo; Anterior
              </button>

              <span className="text-gray-700 text-sm">
                Pàgina {pageData.number + 1} de {pageData.totalPages}
              </span>

              <button
                onClick={() => !pageData.last && setCurrentPage(currentPage + 1)}
                className="px-4 py-2 bg-blue-500 text-white rounded-lg disabled:opacity-40 hover:bg-blue-600 transition"
                disabled={pageData.last}
              >
                Següent &raquo;
              </button>

            </div>
          )}
        </div>
      )}
    </div>
  );
}
