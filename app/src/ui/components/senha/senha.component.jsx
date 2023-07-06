import { useState } from "react";
import Modal from "react-modal";
import { ButtonSecondary } from "../";
import { ESQUECEU_SENHA_MODAL_STYLE } from "../../../constants";
import { useSenha } from "../../../hooks/api";
import "./index.css";

export function EsqueciSenha({ isOpen, close }) {
  const [email, setEmail] = useState("");
  const { esqueciSenha, error } = useSenha();
  const [enviado, setEnviado] = useState(false);

  async function handleSenha(event) {
    event.preventDefault();

    const response = await esqueciSenha(email);

    setEnviado(response);
  }

  return (
    <>
      <Modal
        isOpen={isOpen}
        onRequestClose={close}
        style={ESQUECEU_SENHA_MODAL_STYLE}
      >
        <div className="recuperar-senha">
          <header>
            <button onClick={close} />
          </header>
          <h1>Recuperar a senha</h1>
          <form>
            <input
              type="text"
              name="email"
              placeholder="Email"
              required
              value={email}
              onChange={(event) => setEmail(event.target.value)}
            />
            {enviado && (
              <div className="recuperar-senha-retorno">E-mail enviado</div>
            )}
            {!enviado && error && (
              <div className="recuperar-senha-retorno">E-mail inválido</div>
            )}
            <ButtonSecondary
              extraClasses={"button-recuperar-senha"}
              onClick={handleSenha}
            >
              Enviar
            </ButtonSecondary>
          </form>
        </div>
      </Modal>
    </>
  );
}
