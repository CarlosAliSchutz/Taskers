import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request";
import { NOTIFICACOES_API_PREFIX } from "../../../helpers/constraints";

export function useAlterarNotificacao() {
  const { handleRequest } = useRequest();

  function alterarNotificacao() {
    const response = handleRequest(
      axiosInstance.put(`${NOTIFICACOES_API_PREFIX}/email`)
    );
    return response;
  }

  return { alterarNotificacao };
}
