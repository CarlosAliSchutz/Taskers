import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request";
import { AUTH_API_PREFIX } from "../../../helpers/constraints";

export function useLogout() {
  const { handleRequest, error, isLoading } = useRequest();

  function logout() {
    handleRequest(axiosInstance.post(`${AUTH_API_PREFIX}/logout`));
  }

  return { logout, error, isLoading };
}
