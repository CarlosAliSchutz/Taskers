import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request.js";
import { AFAZERES_API_PREFIX } from "../../../helpers/constraints";

export function useAlterarAfazer() {
  const { handleRequest } = useRequest();

  function alterarAfazer(id, nome, descricao, dificuldade) {
    const response = handleRequest(
      axiosInstance.put(`${AFAZERES_API_PREFIX}/${id}`, {
        nome: nome,
        descricao: descricao,
        dificuldade: dificuldade,
      })
    );
    return response;
  }

  return { alterarAfazer };
}
