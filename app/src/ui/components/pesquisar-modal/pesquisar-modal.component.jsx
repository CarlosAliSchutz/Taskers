import { Input } from "..";
import "./index.css";

export function PesquisarModal({ tipo, formData, handleChange }) {
  function renderInput() {
    if (tipo === "HABITO") {
      return (
        <Input
          type="text"
          name="habito"
          value={formData.habito.value}
          onChange={handleChange}
        />
      );
    }
    if (tipo === "DIARIA") {
      return (
        <Input
          type="text"
          name="diaria"
          value={formData.diaria.value}
          onChange={handleChange}
        />
      );
    }
    if (tipo === "AFAZER") {
      return (
        <Input
          type="text"
          name="afazer"
          value={formData.afazer.value}
          onChange={handleChange}
        />
      );
    }
    return;
  }

  return <section className="pesquisar-tarefa-modal">{renderInput()}</section>;
}
