
// import React, { useState, useContext, createContext } from 'react';
// import { BrowserRouter, Routes, Route, Navigate, Link } from 'react-router-dom';
import { Link } from 'react-router-dom';

export default function Landing() {
  return (
    <div className="relative min-h-screen flex flex-col font-inter">
      {/* Imatge de Fons i Overlay */}
      <div className="absolute inset-0 z-0">
        <img
          src="https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=2070&q=80"
          alt="Vestíbul d'un hotel de luxe"
          className="w-full h-full object-cover"
          onError={(e) => {
            e.target.onerror = null;
            e.target.src = 'https://placehold.co/1920x1080/333/FFF?text=Imatge+Hotel';
          }}
        />
        <div className="absolute inset-0 bg-black/60"></div>
      </div>

      {/* Contingut */}
      <div className="relative z-10 flex flex-col flex-grow text-white">
        {/* Header / Navegació */}
        <header className="w-full p-6 sm:p-8">
          <div className="container mx-auto flex justify-between items-center">
            <h1 className="text-3xl font-bold tracking-tight">HotelGrup</h1>
            <nav className="flex space-x-2 sm:space-x-4">
              <Link
                to="/login"
                className="px-4 py-2 text-sm sm:text-base font-medium text-white bg-transparent border border-white rounded-lg hover:bg-white hover:text-gray-900 transition-colors duration-300"
              >
                Inicia Sessió
              </Link>
              <Link
                to="/register"
                className="px-4 py-2 text-sm sm:text-base font-medium text-gray-900 bg-white rounded-lg hover:bg-gray-200 transition-colors duration-300"
              >
                Registra't
              </Link>
            </nav>
          </div>
        </header>

        {/* Hero Principal */}
        <main className="flex-grow flex items-center justify-center text-center px-4">
          <div className="max-w-3xl">
            <h2 className="text-4xl sm:text-5xl md:text-6xl font-extrabold mb-4 tracking-tight">
              La teva escapada perfecta <span className="block">comença aquí.</span>
            </h2>
            <p className="text-lg sm:text-xl md:text-2xl text-gray-200 mb-8 max-w-2xl mx-auto">
              Experimenta un confort i un luxe incomparables a les nostres destinacions de primer nivell.
            </p>
            <Link
              to="/register"
              className="px-8 py-3 text-lg font-semibold text-gray-900 bg-white rounded-lg shadow-lg hover:bg-gray-200 transform hover:scale-105 transition-all duration-300"
            >
              Reserva Ara
            </Link>
          </div>
        </main>
      </div>
    </div>
  );
};