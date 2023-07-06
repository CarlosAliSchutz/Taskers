import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request";
import { RANKING_API_PREFIX } from "../../../helpers/constraints";

export function useListarRanking() {
  const { handleRequest, data, error } = useRequest();

  function listarRanking(nome) {
    const response = handleRequest(
      axiosInstance.get(`${RANKING_API_PREFIX}?nome=${nome}&sort=experiencia,desc`)
    );
    return response;
  }

  return { listarRanking, ranking: data?.content, error };
}
