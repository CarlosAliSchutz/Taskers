import { useEffect, useState } from "react";
import { BoxItemLoja } from "../";
import useGlobalCosmetico from "../../../context/cosmetico/cometico.context.js";
import { useLoja } from "../../../hooks/api/index.js";
import "./index.css";

export function ItemLoja({ filtro }) {
  const { listaCosmeticos, itens } = useLoja();
  const [itemFiltrado, setItemFiltrado] = useState([]);
  const [cosmetico, setCosmetico] = useGlobalCosmetico();
  const [semCosmeticoNoFiltro, setSemCosmeticoNoFiltro] = useState(false);

  useEffect(() => {
    listaCosmeticos();
  }, []);

  useEffect(() => {
    if (itens) {
      setCosmetico(itens);
    }
  }, [itens]);

  const SEM_COSMETICO = 0;

  useEffect(() => {
    if (itemFiltrado.length === SEM_COSMETICO) {
      setSemCosmeticoNoFiltro(true);
    } else if (itemFiltrado.length > SEM_COSMETICO) {
      setSemCosmeticoNoFiltro(false);
    }
  }, [itemFiltrado]);

  useEffect(() => {
    if (cosmetico) {
      if (filtro === "TODOS") {
        setItemFiltrado(cosmetico);
      }
      if (filtro !== "TODOS") {
        const filtrandoItem = cosmetico?.filter((item) =>
          filtro?.includes(item.tipo)
        );
        setItemFiltrado(filtrandoItem);
      }
    }
  }, [cosmetico, filtro]);

  return (
    <div className="itens-loja">
      {itemFiltrado &&
        itemFiltrado?.map((item) => (
          <BoxItemLoja
            key={item.id}
            id={item.id}
            nome={item.nome}
            valor={item.valor}
          />
        ))}

      {semCosmeticoNoFiltro === true && (
        <div className="categoria-sem-cosmetico">
          <h1>
            Não há cosméticos da categoria <span>{filtro}</span> a serem
            comprados
          </h1>
        </div>
      )}
    </div>
  );
}
