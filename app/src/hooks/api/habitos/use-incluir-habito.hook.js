import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request.js";
import { HABITOS_API_PREFIX } from "../../../helpers/constraints";

export function useIncluirHabito() {
  const { handleRequest } = useRequest();

  function incluirHabito(nome, descricao, dificuldade) {
    const response = handleRequest(
      axiosInstance.post(`${HABITOS_API_PREFIX}`, {
        nome: nome,
        descricao: descricao,
        dificuldade: dificuldade,
      })
    );
    return response;
  }

  return { incluirHabito };
}
