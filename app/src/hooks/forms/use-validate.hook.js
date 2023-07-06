import { useState } from "react";

export function useValidate() {
  const [requestError, setRequestError] = useState();

  function validateRequestError(error) {
    if (!error) {
      setRequestError("");
    }
    setRequestError(error.response.data.message);
  }

  return { requestError, validateRequestError };
}
