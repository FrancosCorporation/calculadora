// Explica falhas de carregamento ou execução sem substituir o erro no console.
(() => {
  const aviso = document.getElementById('erro-js');
  if (!aviso) return;

  const mostrar = () => {
    aviso.textContent = 'A calculadora não conseguiu carregar corretamente. Verifique a página e tente recarregar.';
    aviso.hidden = false;
  };

  window.addEventListener('error', mostrar, true);
  window.addEventListener('unhandledrejection', mostrar);
})();
