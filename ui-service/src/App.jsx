import HotelList from './components/HotelList'
import './App.css'

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>My Hotel Application</h1>
      </header>
      <main>
        {/* Aquí mostrem el nostre nou component */}
        <HotelList />
      </main>
    </div>
  );
}

export default App
