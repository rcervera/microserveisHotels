import React, { useState, useEffect } from 'react';
import axios from 'axios';

// ====================================================================
// 1. ESTILS
// ====================================================================
const styles = {
  searchBar: {
    marginBottom: '20px',
  },
  input: {
    width: '100%',
    padding: '10px',
    fontSize: '1em',
    boxSizing: 'border-box',
  },
  listContainer: {
    minHeight: '200px', 
    position: 'relative',
    opacity: 1,
    transition: 'opacity 0.3s ease-in-out',
  },
  listLoading: { 
    minHeight: '200px',
    position: 'relative',
    opacity: 0.5,
    transition: 'opacity 0.3s ease-in-out',
  },
  paginationControls: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginTop: '20px',
  },
  pageInfo: {
    margin: '0 15px',
    fontSize: '0.9em',
  },
  select: {
    padding: '5px',
    marginLeft: '10px',
  },
  button: {
    padding: '5px 10px',
    cursor: 'pointer',
  }
};

// ====================================================================
// 2. COMPONENT 'HotelList' (Tot en un)
// ====================================================================
function HotelList() {
  
  // --- ESTATS DE DADES I UI ---
  const [pageData, setPageData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // --- ESTATS DE CERCA I PAGINACIÓ ---
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(5);
  const [keyword, setKeyword] = useState('');
  const [searchTerm, setSearchTerm] = useState('');

  // --- EFECTE 1: LÒGICA DEL DEBOUNCE (PER A L'INPUT) ---
  useEffect(() => {
    const timer = setTimeout(() => {
      setKeyword(searchTerm);
      setCurrentPage(0);
    }, 500); 

    return () => {
      clearTimeout(timer);
    };
  }, [searchTerm]); 

  // --- EFECTE 2: ANAR A BUSCAR LES DADES ---
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

  
  // --- GESTORS D'ESDEVENIMENTS ---
  const handleSizeChange = (event) => {
    setPageSize(Number(event.target.value));
    setCurrentPage(0); 
  };
  const handlePrevPage = () => {
    if (pageData && !pageData.first) {
      setCurrentPage(currentPage - 1);
    }
  };
  const handleNextPage = () => {
    if (pageData && !pageData.last) {
      setCurrentPage(currentPage + 1);
    }
  };

  // --- FUNCIÓ AUXILIAR PER RENDERITZAR LA LLISTA ---
  const renderListContent = () => {
    if (loading && !pageData) {
      return <div>Loading hotels...</div>;
    }
    if (error) {
      return <div>Error fetching data: {error.message}</div>;
    }
    if (!pageData || pageData.content.length === 0) {
      return <p>No s'han trobat hotels amb aquests criteris.</p>;
    }
    return (
      <ul>
        {pageData.content.map(hotel => (
          <li key={hotel.id}>
            <strong>{hotel.name}</strong>
            <p>Location: {hotel.location}</p>
            <p>Address: {hotel.address}</p>
          </li>
        ))}
      </ul>
    );
  };

  // --- RENDERITZAT PRINCIPAL ---
  return (
    <div>
      {/* 1. Títol i Caixa de Cerca (Sempre visibles) */}
      <h2>🏨 Hotel List</h2>
      <div style={styles.searchBar}>
        <input
          type="text"
          placeholder="Cerca per nom, adreça, localitat..."
          style={styles.input}
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      {/* 2. Contenidor de la Llista (El contingut canvia) */}
      <div style={loading ? styles.listLoading : styles.listContainer}>
        {renderListContent()}
      </div>

      {/* 3. Controls de Paginació (LÒGICA CORREGIDA) */}
      {/* Mostrem els controls si no estem carregant (inicialment) i hi ha dades */}
      {!loading && pageData && (
        <div style={styles.paginationControls}>
          
          {/* AQUEST ÉS EL CANVI: El 'select' es mostra sempre (si hi ha dades) */}
          <div>
            <label htmlFor="pageSizeSelect">Resultats per pàgina:</label>
            <select 
              id="pageSizeSelect"
              style={styles.select}
              value={pageSize}
              onChange={handleSizeChange}
              disabled={loading} // Es desactiva només si està carregant
            >
              <option value="5">5</option>
              <option value="10">10</option>
              <option value="15">15</option>
            </select>
          </div>
          
          {/* AQUESTA PART NOMÉS ES MOSTRA SI HI HA MÉS D'UNA PÀGINA */}
          {pageData.totalPages > 1 && (
            <div>
              <button 
                style={styles.button}
                onClick={handlePrevPage}
                disabled={pageData.first || loading} 
              >
                &laquo; Anterior
              </button>
              <span style={styles.pageInfo}>
                Pàgina {pageData.number + 1} de {pageData.totalPages}
              </span>
              <button 
                style={styles.button}
                onClick={handleNextPage}
                disabled={pageData.last || loading} 
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

export default HotelList;