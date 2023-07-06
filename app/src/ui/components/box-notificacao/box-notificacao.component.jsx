import { useDeletarNotificacao } from "../../../hooks/api";
import "./index.css";

export function BoxNotificacao({ id, titulo, descricao, handleRefresh }) {
  const { deletarNotificacao } = useDeletarNotificacao();

  async function handleDelete() {
    await deletarNotificacao(id);
    handleRefresh()
  }

  return (
    <div className="box-notificacao">
      <section>
        <header>{titulo}</header>
        <section>
          <p>{descricao}</p>
        </section>
      </section>
      <button onClick={handleDelete}></button>
    </div>
  );
}
