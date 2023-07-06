import "./index.css";

export function ButtonSecondary({ children, extraClasses, ...props }) {
  return (
    <button className={`button-secondary ${extraClasses}`} {...props}>
      {children}
    </button>
  );
}
