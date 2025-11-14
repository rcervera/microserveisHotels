export default function Landing() {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-blue-100">
      <h1 className="text-4xl font-bold mb-4">Benvingut al sistema de reserves</h1>
      <p className="text-lg mb-6">Gestiona hotels i reserves de manera fàcil.</p>

      <a
        href="/login"
        className="bg-blue-600 text-white px-6 py-3 rounded-xl text-lg hover:bg-blue-700"
      >
        Iniciar sessió
      </a>
    </div>
  );
}
