import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import LoadingImg from "../../../assets/icon/loading.png";
import useGlobalUser from "../../../context/user/user.context";
import { useValidarUsuario } from "../../../hooks/api/user/use-validar-usuario.hook";
import "./index.css";

export function VerificacaoGoogle() {
  const { validarUsuario, usuario } = useValidarUsuario();
  const [, setUser] = useGlobalUser();
  const navigate = useNavigate();

  useEffect(() => {
    validarUsuario();
  }, []);

  useEffect(() => {
    setTimeout(() => {
      if (usuario?.id) {
        setUser(usuario);
        navigate("/tarefas");
      } else {
        navigate("/");
      }
    }, 2500);
  }, [usuario]);

  return (
    <section className="page-verificacao-google">
      <div className="loading-verificacao">
        <img src={LoadingImg} alt="Loading rodando em sentido horário" />
        <p>Verificando...</p>
      </div>
      <span className="ilustracao-verificacao" />
    </section>
  );
}
