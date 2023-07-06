import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request.js";
import { CONQUISTAS_API_PREFIX } from "../../../helpers/constraints";

export function useListarConquistas() {
  const { handleRequest, data } = useRequest();

  function listarConquistas() {
    const response = handleRequest(
      axiosInstance.get(`${CONQUISTAS_API_PREFIX}?size=25&sort=concluida,desc`)
    );
    return response.data;
  }

  return { listarConquistas, conquistas: data?.content };
}
