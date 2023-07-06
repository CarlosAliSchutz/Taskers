import { useState } from "react";
import Modal from "react-modal";
import { ButtonSecondary } from "../";
import { EDITAR_SENHA_MODAL_STYLE } from "../../../constants/modal-styles.constant";
import { useEditarSenha } from "../../../hooks/api";
import "./index.css";

export function EditarSenha({ isOpen, close }) {
  const [senhaAtual, setSenhaAtual] = useState("");
  const [novaSenha, setNovaSenha] = useState("");
  const { editarSenha } = useEditarSenha();
  const [alterado, setAlterado] = useState(false)

  async function handleSenha(event) {
    event.preventDefault();

    const response = await editarSenha(senhaAtual, novaSenha);

    setAlterado(response)
  }

  return (
    <>
      <Modal
        isOpen={isOpen}
        onRequestClose={close}
        style={EDITAR_SENHA_MODAL_STYLE}
      >
        <div className="editar-senha">
          <header>
            <button onClick={close} />
          </header>
          <h1>Alterar senha</h1>
          <form onSubmit={handleSenha}>

          <input
            type="password"
            name="Senha"
            placeholder="Senha atual"
            required
            value={senhaAtual}
            onChange={(event) => setSenhaAtual(event.target.value)}
          />

          <input
            type="password"
            name="Senha"
            placeholder="Senha nova"
            required
            value={novaSenha}
            onChange={(event) => setNovaSenha(event.target.value)}
          />
            {alterado && (
              <div className="editar-senha-retorno">Senha alterada</div>
            )}
          <ButtonSecondary type="submit">Enviar</ButtonSecondary>
          </form>
        </div>
      </Modal>
    </>
  );
}
