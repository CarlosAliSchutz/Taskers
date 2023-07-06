import ReactDOM from "react-dom";
import App from "./app";
import "./index.css";
import * as serviceWorkerRegistration from "./serviceWorkerRegistration";

ReactDOM.render(<App />, document.getElementById("root"));
serviceWorkerRegistration.register();
