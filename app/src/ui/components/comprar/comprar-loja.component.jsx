import { useEffect } from "react";
import Modal from "react-modal";
import { ButtonPrimary, TaskCoin } from "../";
import useGlobalCosmetico from "../../../context/cosmetico/cometico.context";
import useGlobalUser from "../../../context/user/user.context";
import { useComprar } from "../../../hooks/api";
import "./index.css";

export function ComprarItem({ idItem, valor, style, isOpen, close, image }) {
  const { comprarItem } = useComprar();
  const [user, setUser] = useGlobalUser();
  const [cosmetico, setCosmetico] = useGlobalCosmetico();

  useEffect(() => {
    if (user?.id) {
      setUser(user);
    }
  }, [user]);

  useEffect(() => {
    if (cosmetico) {
      setCosmetico(cosmetico);
    }
  }, [cosmetico]);

  function handleComprarItem(event) {
    event.preventDefault();
    comprarItem(idItem);

    const itemRemovido = cosmetico.filter((item) => item.id !== idItem);

    setUser((currentUser) => {
      return {
        ...currentUser,
        taskcoin: currentUser.taskcoin - valor,
      };
    });

    setCosmetico(itemRemovido);
  }

  const VALOR_MINIMO_COMPRA = 0;
  const minhasMoedas = user.taskcoin;

  return (
    <Modal isOpen={isOpen} onRequestClose={close} style={style}>
      <div className="box-item-calculo">
        <header>
          <button onClick={close} />
        </header>
        <div className="calculo-divisoria">
          <div className="calculo">
            <div className="calculo-moedas">
              <p>Suas Moedas </p>
              <TaskCoin valor={minhasMoedas} />
            </div>
            <div className="calculo-moedas">
              <p>Este Item </p>
              <TaskCoin valor={valor} />
            </div>
            <span className="calculo-divisao" />
            <div className="calculo-moedas">
              {minhasMoedas - valor < VALOR_MINIMO_COMPRA && (
                <p className="text-roxo">Moedas insuficientes</p>
              )}
              {minhasMoedas - valor > VALOR_MINIMO_COMPRA && (
                <>
                  <p className="text-roxo">Você ficará com </p>
                  <TaskCoin valor={[minhasMoedas - valor]} />
                </>
              )}
            </div>

            <ButtonPrimary
              disabled={minhasMoedas - valor < VALOR_MINIMO_COMPRA && true}
              onClick={handleComprarItem}
              extraClasses="item-botao-compra"
            >
              Comprar
            </ButtonPrimary>
          </div>
          <div className="item-image-compra">
            <img src={image} alt="Cosmético selecionado" />
          </div>
        </div>
      </div>
    </Modal>
  );
}
