import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request";
import { NOTIFICACOES_API_PREFIX } from "../../../helpers/constraints";

export function useDeletarNotificacao() {
  const { handleRequest } = useRequest();

  async function deletarNotificacao(id) {
    await handleRequest(
      axiosInstance.delete(`${NOTIFICACOES_API_PREFIX}/${id}`)
    );
  }

  return { deletarNotificacao };
}
