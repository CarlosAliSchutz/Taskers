import { useState } from "react";
import { axiosInstance } from "../../../api/_base/axios-instance";
import { AUTH_API_PREFIX } from "../../../helpers/constraints";

export function useSenha() {
  const [error, setError] = useState(null);

  async function esqueciSenha(email) {
    let enviado = false;
    try {
      await axiosInstance.post(`${AUTH_API_PREFIX}/esqueci-senha`, {
        email,
      });
      enviado = true;
    } catch (error) {
      setError(error.response.data.message);
      enviado = false;
    }
    return enviado;
  }

  return { esqueciSenha, error };
}
