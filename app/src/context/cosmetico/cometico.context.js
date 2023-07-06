import createGlobalState from "react-create-global-state";
import { COSMETICOS_KEY } from "../../constants";

const stateInStorage = localStorage.getItem(COSMETICOS_KEY);

const initialState = stateInStorage ? JSON.parse(stateInStorage) : null;

const [_useGlobalCosmetico, Provider] = createGlobalState(initialState);

function useGlobalCosmetico() {
  const [_cosmetico, _setCosmetico] = _useGlobalCosmetico();

  function setCosmetico(cosmetico) {
    localStorage.setItem(COSMETICOS_KEY, JSON.stringify(cosmetico));
    _setCosmetico(cosmetico);
  }

  return [_cosmetico, setCosmetico];
}

export const GlobalCosmeticoProvider = Provider;

export default useGlobalCosmetico;
