// Núcleo puro da calculadora (testável em Node) — comportamento do AppCalc.java
// com a correção dos casts (int) que perdiam decimais no original.

export function formatarDisplay(valor) {
  if (!Number.isFinite(valor)) return 'Erro';
  if (Number.isInteger(valor)) return String(valor);
  return String(parseFloat(valor.toFixed(10))).replace('.', ',');
}

export function calcular(acumulador, operacao, valor) {
  switch (operacao) {
    case '+': return acumulador + valor;
    case '-': return acumulador - valor;
    case '*': return acumulador * valor;
    case '/': return valor === 0 ? NaN : acumulador / valor;
    default: return valor;
  }
}

// Regra de dígito fiel ao setDisplayNumber() original: zero à esquerda não se repete
export function anexarDigito(painel, digito) {
  if (painel === '0') return digito === 0 ? '0' : String(digito);
  return painel + digito;
}

export function anexarVirgula(painel) {
  return painel.includes(',') ? painel : painel + ',';
}

export function apagarUltimo(painel) {
  const novo = painel.slice(0, -1);
  return novo === '' || novo === '-' ? '0' : novo;
}

export function parsePainel(painel) {
  return parseFloat(painel.replace(',', '.')) || 0;
}
