export function usePagination() {
  function backPage(setPage) {
    setPage((oldPage) => oldPage - 1);
  }

  function nextPage(setPage) {
    setPage((oldPage) => oldPage + 1);
  }

  return { backPage, nextPage };
}
