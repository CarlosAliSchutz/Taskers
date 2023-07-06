import { useEffect } from "react";
import { COSMETICOS_KEY, USER_KEY } from "../../../constants";
import useGlobalUser from "../../../context/user/user.context";
import { useUsuario } from "../../../hooks/api/user/use-listar-usuario.hook";
import { BoxAfazeres, BoxDiarias, BoxHabitos, Header } from "../../components";
import "./index.css";

export function TarefasScreen() {
  const { listarUsuario, error } = useUsuario();
  const [, setUser] = useGlobalUser();

  useEffect(() => {
    listarUsuario();
  }, []);

  useEffect(() => {
    if (error === 504 || error === 500) {
      setUser(null);
      localStorage.removeItem(USER_KEY);
      localStorage.removeItem(COSMETICOS_KEY);
    }
  }, [error]);

  function renderSetaEsquerda(page, backFunction) {
    if (page > 0) {
      return (
        <span className="tarefa-seta-esquerda">
          <button onClick={backFunction}></button>
        </span>
      );
    }
    return (
      <span className="tarefa-seta-vazia">
        <button></button>
      </span>
    );
  }

  function renderSetaDireita(size, nextFunction) {
    const numeroMaximoItens = 20;
    if (size === numeroMaximoItens) {
      return (
        <span className="tarefa-seta-direita">
          <button onClick={nextFunction}></button>
        </span>
      );
    }
    return (
      <span className="tarefa-seta-vazia">
        <button></button>
      </span>
    );
  }

  function renderPage(size, page) {
    const numeroMaximoItens = 20;
    if (size === numeroMaximoItens || page > 0) {
      const actualPage = Number(page) + 1;
      return (
        <div className="numero-paginacao">
          <h2>{actualPage}</h2>
        </div>
      );
    }
    return <h2> </h2>;
  }

  return (
    <>
      <Header paginaAtual={"tarefas"} />

      <section className="tela-tarefas">
        <BoxHabitos
          renderPage={renderPage}
          renderSetaDireita={renderSetaDireita}
          renderSetaEsquerda={renderSetaEsquerda}
        />
        <BoxDiarias
          renderPage={renderPage}
          renderSetaDireita={renderSetaDireita}
          renderSetaEsquerda={renderSetaEsquerda}
        />
        <BoxAfazeres
          renderPage={renderPage}
          renderSetaDireita={renderSetaDireita}
          renderSetaEsquerda={renderSetaEsquerda}
        />
      </section>
    </>
  );
}
