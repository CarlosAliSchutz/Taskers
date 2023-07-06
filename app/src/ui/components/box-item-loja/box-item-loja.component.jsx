import { useEffect, useState } from "react";
import { ComprarItem, Loading, TaskCoin } from "../";
import { COSMETICOS } from "../../../constants";
import { EFETUAR_COMPRA_MODAL_STYLE } from "../../../constants/modal-styles.constant.js";
import { useModal } from "../../../hooks";
import { useLoja } from "../../../hooks/api/index.js";
import "./index.css";

export function BoxItemLoja({ id, nome, valor }) {
  const [comprarItemModalIsOpen, comprarItemModalSetIsOpen] = useState(false);
  const { openModal, closeModal } = useModal();
  const { listaCosmeticos, loadingItem } = useLoja();

  useEffect(() => {
    listaCosmeticos();
  }, []);

  return (
    <div className="box-item" key={id}>
      <Loading get={loadingItem} image={COSMETICOS[nome]} />
      <span className="item-divisao" />
      <div className="item-compra">
        <TaskCoin valor={valor} />
        <button
          className="item-loja-botao"
          onClick={() => {
            openModal(comprarItemModalSetIsOpen);
          }}
        >
          +
        </button>
        {
          <ComprarItem
            image={COSMETICOS[nome]}
            idItem={id}
            valor={valor}
            style={EFETUAR_COMPRA_MODAL_STYLE}
            isOpen={comprarItemModalIsOpen}
            close={() => {
              closeModal(comprarItemModalSetIsOpen);
            }}
          />
        }
      </div>
    </div>
  );
}
