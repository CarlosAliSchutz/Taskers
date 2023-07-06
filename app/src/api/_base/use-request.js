import { useState } from "react";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export function useRequest() {
  const [data, setData] = useState(null);
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  async function handleRequest(promise) {
    let responseData;
    try {
      setError(null);
      setIsLoading(true);
      const response = await promise;
      responseData = response.data;
    } catch (error) {
      setError(error.response);
      toast.error(error.response.data.message, {
        position: "top-right",
        autoClose: 2000,
        hideProgressBar: true,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "colored",
      });
      responseData = null;
    } finally {
      setIsLoading(false);
      setData(responseData);
    }
    return responseData;
  }

  return { handleRequest, data, error, isLoading };
}
