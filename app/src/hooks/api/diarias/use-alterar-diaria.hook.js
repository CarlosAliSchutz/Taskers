import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request";
import { DIARIAS_API_PREFIX } from "../../../helpers/constraints";

export function useAlterarDiaria() {
  const { handleRequest } = useRequest();

  function alterarDiaria(id, nome, descricao, dificuldade, hora, dias) {
    const response = handleRequest(
      axiosInstance.put(`${DIARIAS_API_PREFIX}/${id}`, {
        nome: nome,
        descricao: descricao,
        dificuldade: dificuldade,
        hora: hora,
        dias: dias,
      })
    );
    return response;
  }

  return { alterarDiaria };
}
