import "./index.css";

export function ButtonPrimary({ children, extraClasses, ...props }) {
  return (
    <button className={`button ${extraClasses}`} {...props}>
      {children}
    </button>
  );
}
