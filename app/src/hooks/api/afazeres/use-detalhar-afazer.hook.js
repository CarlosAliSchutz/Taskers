import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request";
import { AFAZERES_API_PREFIX } from "../../../helpers/constraints";

export function useDetalharAfazer() {
  const { handleRequest, data } = useRequest();

  function detalharAfazer(id) {
    const response = handleRequest(
      axiosInstance.get(`${AFAZERES_API_PREFIX}/${id}`)
    );
    return response;
  }

  return { detalharAfazer, afazer: data };
}
