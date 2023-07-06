import "./index.css";

export function CosmeticosEquipados({ fundo, planta, roupa, pet }) {

  return (
    <section
      className="papel-de-parede-perfil"
      style={{ backgroundImage: `url(${fundo})` }}
    >
      <img className="planta-equipada" src={planta} alt="Planta" />
      <img className="roupa-equipada" src={roupa} alt="Roupa" />
      <img className="pet-equipado" src={pet} alt="Pet" />
    </section>
  );
}
