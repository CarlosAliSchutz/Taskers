import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request";
import { DIARIAS_API_PREFIX } from "../../../helpers/constraints";

export function useIncluirDiaria() {
  const { handleRequest } = useRequest();

  function incluirDiaria(nome, descricao, dificuldade, hora, dias) {
    const response = handleRequest(
      axiosInstance.post(`${DIARIAS_API_PREFIX}`, {
        nome: nome,
        descricao: descricao,
        dificuldade: dificuldade,
        hora: hora,
        dias: dias,
      })
    );
    return response;
  }

  return { incluirDiaria };
}
