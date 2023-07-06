import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request";
import { AFAZERES_API_PREFIX } from "../../../helpers/constraints";

export function useIncluirAfazer() {
  const { handleRequest } = useRequest();

  function incluirAfazer(nome, descricao, dificuldade) {
    const response = handleRequest(
      axiosInstance.post(`${AFAZERES_API_PREFIX}`, {
        nome: nome,
        descricao: descricao,
        dificuldade: dificuldade,
      })
    );
    return response;
  }
  return { incluirAfazer };
}
