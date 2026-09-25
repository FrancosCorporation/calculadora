// UI da calculadora — port do AppCalc.java (Swing, "Calcu By Dolfim").
import { formatarDisplay, calcular, anexarDigito, anexarVirgula, apagarUltimo, parsePainel } from './calc-core.js';

const display = document.getElementById('display');

let painel = '0';        // o que está digitado
let acumulador = 0;      // resultado corrente
let operacao = '';       // operação pendente
let aguardandoNumero = false; // true depois de uma operação (próximo dígito inicia novo número)

const mostrar = (texto) => { display.value = texto; };

function digito(n) {
  painel = aguardandoNumero ? '0' : painel;
  aguardandoNumero = false;
  painel = anexarDigito(painel, n);
  mostrar(painel);
}

function virgula() {
  if (aguardandoNumero) { painel = '0'; aguardandoNumero = false; }
  painel = anexarVirgula(painel);
  mostrar(painel);
}

function operador(op) {
  const valor = parsePainel(painel);
  acumulador = operacao ? calcular(acumulador, operacao, valor) : valor;
  operacao = op;
  aguardandoNumero = true;
  mostrar(formatarDisplay(acumulador));
}

function igual() {
  if (!operacao) return;
  const valor = parsePainel(painel);
  acumulador = calcular(acumulador, operacao, valor);
  operacao = '';
  aguardandoNumero = true;
  painel = formatarDisplay(acumulador);
  mostrar(painel);
}

function limparEntrada() { // "C" do original: zera o que está digitado
  painel = '0';
  mostrar(painel);
}

function limparTudo() { // "CE" do original: reset completo
  painel = '0';
  acumulador = 0;
  operacao = '';
  aguardandoNumero = false;
  mostrar(painel);
}

function backspace() {
  painel = apagarUltimo(painel);
  mostrar(painel);
}

document.querySelectorAll('.num[data-n]').forEach((b) =>
  b.addEventListener('click', () => digito(Number(b.dataset.n))));
document.getElementById('btn-virgula').addEventListener('click', virgula);
document.querySelectorAll('.op[data-op]').forEach((b) =>
  b.addEventListener('click', () => operador(b.dataset.op)));
document.getElementById('btn-igual').addEventListener('click', igual);
document.getElementById('btn-c').addEventListener('click', limparEntrada);
document.getElementById('btn-ce').addEventListener('click', limparTudo);
document.getElementById('btn-backspace').addEventListener('click', backspace);

// Teclado físico, como o KeyListener do original
document.addEventListener('keydown', (e) => {
  if (/^[0-9]$/.test(e.key)) digito(Number(e.key));
  else if (e.key === ',' || e.key === '.') virgula();
  else if (['+', '-', '*', '/'].includes(e.key)) operador(e.key);
  else if (e.key === 'Enter' || e.key === '=') { e.preventDefault(); igual(); }
  else if (e.key === 'Backspace') backspace();
  else if (e.key === 'Escape') limparTudo();
});
