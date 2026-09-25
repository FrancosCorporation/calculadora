// Testes da calculadora (Node puro). Uso: npm test
import { formatarDisplay, calcular, anexarDigito, anexarVirgula, apagarUltimo, parsePainel } from '../js/calc-core.js';

let falhas = 0;
function check(nome, cond) {
  console.log(`${cond ? 'PASS' : 'FAIL'} - ${nome}`);
  if (!cond) falhas++;
}

// operações básicas
check('2 + 3 = 5', calcular(2, '+', 3) === 5);
check('10 - 4 = 6', calcular(10, '-', 4) === 6);
check('6 * 7 = 42', calcular(6, '*', 7) === 42);
check('9 / 2 = 4.5', calcular(9, '/', 2) === 4.5);
check('0 / 5 = 0', calcular(0, '/', 5) === 0);
check('5 / 0 = NaN (exibido como Erro)', Number.isNaN(calcular(5, '/', 0)));

// sequência acumulada (2 + 3 + 4 = 9)
check('encadeamento 2+3 depois +4 = 9', calcular(calcular(2, '+', 3), '+', 4) === 9);

// formatação fiel (vírgula decimal pt-BR, inteiro sem casas)
check('formatar 4.5 -> "4,5"', formatarDisplay(4.5) === '4,5');
check('formatar 9 -> "9"', formatarDisplay(9) === '9');
check('formatar NaN -> "Erro"', formatarDisplay(NaN) === 'Erro');

// dígitos e vírgula (regras do setDisplayNumber original)
check('zero à esquerda não repete ("0" + 5 -> "5")', anexarDigito('0', 5) === '5');
check('"12" + 3 -> "123"', anexarDigito('12', 3) === '123');
check('"0" + 0 -> "0"', anexarDigito('0', 0) === '0');
check('vírgula única ("1,5" + "," -> "1,5")', anexarVirgula('1,5') === '1,5');
check('"3" + "," -> "3,"', anexarVirgula('3') === '3,');

// backspace e parse
check('backspace "123" -> "12"', apagarUltimo('123') === '12');
check('backspace "1" -> "0"', apagarUltimo('1') === '0');
check('parse "1,5" = 1.5', parsePainel('1,5') === 1.5);
check('parse vazio = 0', parsePainel('') === 0);

console.log(falhas === 0 ? '\nTODOS OS TESTES PASSARAM ✔' : `\n${falhas} TESTE(S) FALHARAM ✘`);
process.exit(falhas === 0 ? 0 : 1);
