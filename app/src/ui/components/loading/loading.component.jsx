import LoadingImg from "../../../assets/icon/loading.png";
import "./index.css";

export function Loading({ get, image }) {
  if (get === false) {
    return <img src={image} alt="Imagem a ser mostrada" />;
  } else if (get === true) {
    return (
      <div className="loading">
        <img src={LoadingImg} alt="Loading rodando em sentido horário" />
      </div>
    );
  }
}
