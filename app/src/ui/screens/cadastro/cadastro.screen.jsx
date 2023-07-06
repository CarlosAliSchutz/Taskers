import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useCadastro } from "../../../hooks/api";
import { ButtonPrimary, Input, LogoTaskers } from "../../components";
import "./index.css";

export function CadastroScreen() {
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [foto, setFoto] = useState("");
  const { cadastro, error } = useCadastro();
  const [usuario, setUsuario] = useState();
  const navigate = useNavigate();

  async function handleSubmit(event) {
    event.preventDefault();

    const cadastrado = await cadastro(nome, email, foto, senha);

    setUsuario(cadastrado);
  }

  useEffect(() => {
    if (usuario === true) {
      if (usuario === "Cadastrado") {
        toast.success("Cadastrado com sucesso!", {
          position: "top-right",
          autoClose: 5000,
          hideProgressBar: true,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
          theme: "colored",
        });
      }
      navigate("/");
    }
  }, [usuario]);

  return (
    <section className="cadastro">
      <LogoTaskers className="image-logo" />
      <form onSubmit={handleSubmit}>
        <div className="input-cadastro">
          <Input
            type="text"
            label="Nome"
            name="nome"
            value={nome}
            required
            onChange={(event) => setNome(event.target.value)}
          />

          <Input
            type="text"
            label="E-mail"
            name="email"
            value={email}
            required
            onChange={(event) => setEmail(event.target.value)}
          />
        </div>
        <div className="input-cadastro">
          <Input
            type="password"
            label="Senha"
            name="senha"
            value={senha}
            required
            onChange={(event) => setSenha(event.target.value)}
          />

          <Input
            type="text"
            label="Foto (opcional)"
            name="foto"
            value={foto}
            onChange={(event) => setFoto(event.target.value)}
          />
        </div>
        <div className="input-cadastro">
          <ButtonPrimary type="submit">Cadastrar</ButtonPrimary>
        </div>
        <div className="cadastro-link">
          <a onClick={() => navigate("/")} className="link">
            Já possui cadastro? <span>Login</span>
          </a>
        </div>
        {error && <div className="cadastro-error">{error}</div>}
      </form>
      <span
        className="cadastro-ilustracao"
        alt="Ilustração de um cachorro na tela de cadastro"
      />
    </section>
  );
}
