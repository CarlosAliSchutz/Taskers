import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "react-toastify/dist/ReactToastify.css";
import useGlobalUser from "../../../context/user/user.context";
import { useModal } from "../../../hooks";
import { useLogin } from "../../../hooks/api";
import {
  ButtonGoogle,
  ButtonPrimary,
  EsqueciSenha,
  Input,
  LogoTaskers,
} from "../../components";
import "./index.css";

export function LoginScreen() {
  const [formInput, setFormInput] = useState({ usuario: "", senha: "" });
  const [, setUser] = useGlobalUser();
  const { login } = useLogin();
  const navigate = useNavigate();
  const [esqueciSenhaAberto, setEsqueciSenhaAberto] = useState(false);
  const { openModal, closeModal } = useModal();
  const [error, setError] = useState();

  function handleChange(event) {
    const { name, value } = event.target;

    setFormInput((oldFormInput) => ({ ...oldFormInput, [name]: value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();

    try {
      const user = await login({
        username: formInput.usuario,
        password: formInput.senha,
      });

      setUser(user);
      navigate("/tarefas");
    } catch (error) {
      setError("Usuário ou senha incorretos");
    }
  }

  function handleCadastro() {
    navigate("/cadastro");
  }

  return (
    <section className="login">
      <form onSubmit={handleSubmit}>
        <LogoTaskers className="image-logo" />
        <Input
          type="text"
          label="Usuário"
          name="usuario"
          autoComplete="off"
          required
          value={formInput.usuario}
          onChange={handleChange}
        />

        <Input
          type="password"
          label="Senha"
          name="senha"
          autoComplete="off"
          required
          value={formInput.senha}
          link={"Esqueceu a senha?"}
          to={() => {
            openModal(setEsqueciSenhaAberto);
          }}
          onChange={handleChange}
        />
        <div className="esqueci-senha">
          <EsqueciSenha
            isOpen={esqueciSenhaAberto}
            close={() => {
              closeModal(setEsqueciSenhaAberto);
            }}
          />
        </div>

        <ButtonPrimary type="submit">Login</ButtonPrimary>

        {error && <div className="login-error">{error}</div>}

        <ButtonGoogle />
        <p className="login-text">
          Não possui conta?{" "}
          <a onClick={handleCadastro} className="link">
            Cadastre-se
          </a>
        </p>
      </form>
      <span className="login-ilustracao" />
    </section>
  );
}
