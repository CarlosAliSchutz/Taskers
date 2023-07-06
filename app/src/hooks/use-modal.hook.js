export function useModal() {
  function openModal(setIsOpen) {
    setIsOpen(true);
  }

  function closeModal(setIsOpen) {
    setIsOpen(false);
  }

  return { openModal, closeModal };
}
