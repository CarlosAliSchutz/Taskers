import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request";
import { COSMETICOS_API_PREFIX } from "../../../helpers/constraints";

export function useComprar() {
  const { handleRequest, error } = useRequest();

  function comprarItem(idItem) {
    handleRequest(
      axiosInstance.post(`${COSMETICOS_API_PREFIX}/${idItem}/comprar`)
    );
  }

  return { comprarItem, error };
}
