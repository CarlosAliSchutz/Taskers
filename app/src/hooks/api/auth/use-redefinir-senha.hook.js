import { useState } from "react";
import { axiosInstance } from "../../../api/_base/axios-instance";
import { AUTH_API_PREFIX } from "../../../helpers/constraints";

export function useRedefinirSenha() {
  const [error, setError] = useState(null);

  async function mudarSenha(senha, token) {
    let alterado = false;
    try {
      await axiosInstance.put(
        `${AUTH_API_PREFIX}/recuperar-senha/${token}`,
        senha
      );

      alterado = true;
    } catch (error) {
      setError(error.response.data.message);
      alterado = false;
    }
    return alterado;
  }

  return { mudarSenha, error };
}
