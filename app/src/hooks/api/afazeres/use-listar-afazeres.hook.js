import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request";
import { AFAZERES_API_PREFIX } from "../../../helpers/constraints";

export function useListarAfazeres() {
  const { handleRequest, data } = useRequest();

  function listarAfazeres(text, page) {
    const response = handleRequest(
      axiosInstance.get(
        `${AFAZERES_API_PREFIX}?text=${text}&page=${page}&=id,desc`
      )
    );
    return response.data;
  }

  function refreshAfazeres(filter, page) {
    listarAfazeres(filter, page);
  }

  return { afazeres: data?.content, listarAfazeres, refreshAfazeres };
}
