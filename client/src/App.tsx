import './App.css';
import mensaImage from './assets/meet@mensa_transparent.svg';

function App() {
  return (
    <div className="app-container">
      <img src={mensaImage} alt="Meet@Mensa" className="mensa-image" width={600} />
      <h1 className="coming-soon">Coming Soon</h1>
    </div>
  );
}

export default App;
