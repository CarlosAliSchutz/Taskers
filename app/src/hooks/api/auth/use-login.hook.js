import { axiosInstance } from "../../../api/_base/axios-instance";
import { AUTH_API_PREFIX } from "../../../helpers/constraints";

export function useLogin() {
  async function login({ username, password }) {
    const response = await axiosInstance.post(
      `${AUTH_API_PREFIX}/login`,
      {},
      {
        auth: {
          username,
          password,
        },
      }
    );
    return response.data;
  }

  return { login };
}
