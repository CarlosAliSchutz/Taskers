import "./index.css";

export function Recompensa({ valor, icon, finalizado }) {
  function renderValor() {
    return finalizado ? (
      <div className="recompensa-valor-finalizado"></div>
    ) : (
      <div className="recompensa-valor">{valor}</div>
    );
  }

  return (
    <div className="recompensa" style={{ backgroundImage: `url(${icon})` }}>
      {renderValor()}
    </div>
  );
}
