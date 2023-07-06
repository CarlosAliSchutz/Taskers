import { useEffect } from "react";
import { COSMETICOS_KEY, USER_KEY } from "../../../constants";
import useGlobalUser from "../../../context/user/user.context";
import { useListarConquistas, useUsuario } from "../../../hooks/api";
import { BoxConquista, Header } from "../../components";
import "./index.css";

export function ConquistasScreen() {
  const { listarConquistas, conquistas } = useListarConquistas();
  const [, setUser] = useGlobalUser();
  const { listarUsuario, error } = useUsuario();

  useEffect(() => {
    listarUsuario();
  }, []);

  useEffect(() => {
    listarConquistas();
  }, []);

  useEffect(() => {
    if (error === 504 || error === 500) {
      setUser(null);
      localStorage.removeItem(USER_KEY);
      localStorage.removeItem(COSMETICOS_KEY);
    }
  }, [error]);

  return (
    <>
      <Header />
      <section className="tela-conquistas">
        <header>Conquistas</header>
        <section>
          {conquistas
            ? conquistas.map((conquista) => {
                return (
                  <BoxConquista
                    key={conquista.conquista.id}
                    nome={conquista.conquista.nome}
                    descricao={conquista.conquista.descricao}
                    progresso={conquista.progresso}
                    objetivo={conquista.conquista.objetivo}
                    concluido={conquista.concluida}
                    dificuldade={conquista.conquista.dificuldade}
                  />
                );
              })
            : null}
        </section>
      </section>
    </>
  );
}
