import "./index.css";
import { COSMETICOS } from "../../../constants";
import { useEquiparCosmetico } from "../../../hooks/api";
import "./index.css";

export function BoxItem({ id, nome, handleRefresh, disabled }) {
  const { equiparItem } = useEquiparCosmetico();

  async function handleFunction() {
    await equiparItem(id);
    handleRefresh();
  }

  return (
    <button
      disabled={disabled}
      onClick={handleFunction}
      className="box-item-editar"
      key={id}
    >
      <img src={COSMETICOS[nome]} alt={nome} />
    </button>
  );
}
