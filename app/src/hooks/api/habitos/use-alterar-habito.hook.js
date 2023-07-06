import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request";
import { HABITOS_API_PREFIX } from "../../../helpers/constraints";

export function useAlterarHabito() {
  const { handleRequest } = useRequest();

  function alterarHabito(id, nome, descricao, dificuldade) {
    const response = handleRequest(
      axiosInstance.put(`${HABITOS_API_PREFIX}/${id}`, {
        nome: nome,
        descricao: descricao,
        dificuldade: dificuldade,
      })
    );
    return response;
  }

  return { alterarHabito };
}
