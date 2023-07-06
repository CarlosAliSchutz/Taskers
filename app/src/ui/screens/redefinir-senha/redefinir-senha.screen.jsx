import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useRedefinirSenha } from "../../../hooks/api";
import { ButtonPrimary, ButtonSecondary, Input } from "../../components";
import "./index.css";

export function MudarSenhaScreen() {
  const { mudarSenha } = useRedefinirSenha();
  const [senha, setSenha] = useState("");
  const { token } = useParams();
  const navigate = useNavigate();
  const [alterado, setAlterado] = useState(false);

  async function handleSenha(event) {
    event.preventDefault();

    const response = mudarSenha({ senha: senha }, token);

    setAlterado(response);
  }

  function handleSair() {
    navigate("/");
  }

  return (
    <>
      <div className="form-redefinir-senha">
        <h1>Mudar Senha</h1>
        <form onSubmit={handleSenha}>
          <Input
            label="Senha:"
            name="senha"
            value={senha}
            required
            onChange={(event) => setSenha(event.target.value)}
            type="password"
          />

          {alterado && (
            <div className="redefinir-senha-retorno">Senha alterada</div>
          )}

          <div className="form-button">
            <ButtonPrimary type="submit">Trocar Senha</ButtonPrimary>

            <ButtonSecondary
              onClick={handleSair}
              extraClasses={"redefinir-senha-logout"}
            >
              Sair
            </ButtonSecondary>
          </div>
        </form>
      </div>
      <ToastContainer />
    </>
  );
}
