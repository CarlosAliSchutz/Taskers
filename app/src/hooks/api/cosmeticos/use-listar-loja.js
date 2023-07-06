import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request";
import { COSMETICOS_API_PREFIX } from "../../../helpers/constraints";

export function useLoja() {
  const { handleRequest, data, isLoading } = useRequest();

  function listaCosmeticos() {
    handleRequest(axiosInstance.get(`${COSMETICOS_API_PREFIX}/disponiveis?size=44`));
  }

  return { listaCosmeticos, itens: data?.content, loadingItem: isLoading };
}
