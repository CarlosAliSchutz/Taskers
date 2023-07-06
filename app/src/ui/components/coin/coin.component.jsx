import Coin from "../../../assets/icon/taskcoin.svg";
import "./index.css";

export function TaskCoin({ valor, extraClassImg }) {
  return (
    <div className="taskcoin">
      <img
        className={`${extraClassImg}`}
        src={Coin}
        alt="Taskcoin moeda do game"
      />
      <p>{valor}</p>
    </div>
  );
}
