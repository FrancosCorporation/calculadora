# Calcu By Dolfim

![JavaScript](https://img.shields.io/badge/JavaScript-ES2022-yellow?logo=javascript&logoColor=white)
![Node](https://img.shields.io/badge/node-%3E%3D18-green?logo=node.js&logoColor=white)
![Tests](https://img.shields.io/badge/testes-17%2F17%20passando-brightgreen)
![License](https://img.shields.io/badge/license-MIT-green)

Calculadora web — versão (2026) do projeto **Java Swing** `AppCalc.java` ("Calcu By Dolfim", 2022),
com o fonte original preservado em [`java/frontEnd/AppCalc.java`](java/frontEnd/AppCalc.java).

## Instalação e execução

Requer [Node.js 18+](https://nodejs.org/). Sem dependências externas:

```bash
npm ci         # instala a árvore sem dependências externas
npm start     # abre http://localhost:3349
npm run check # verifica a sintaxe dos arquivos JavaScript
npm test      # 17 testes de lógica (operações, formatação, dígitos, backspace)
```

O servidor local publica apenas a calculadora e os arquivos da allowlist em `server.js`; não entrega `.git`, fontes Java, arquivos de configuração ou testes. Por padrão escuta em `127.0.0.1`. Para disponibilizar na rede, defina `HOST=0.0.0.0` conscientemente.

Também é possível executar em container com `docker build -t calcu-by-dolfim .` e `docker run --rm -p 127.0.0.1:3349:3349 calcu-by-dolfim`. A porta fica acessível apenas na própria máquina; para disponibilizar na rede, publique a porta conscientemente.

> Alternativa sem Node: serve `index.html` em qualquer servidor estático (a calculadora é 100% client-side).

## Funcionalidades

- Layout fiel ao Swing original: display alinhado à direita, teclado 4 colunas,
  fundo cinza `SystemColor.inactiveCaption`, fonte **Bahnschrift**.
- Operações `+ − × ÷` com encadeamento acumulado (2 + 3 + 4 = 9) e `=`.
- `C` limpa a entrada · `CE` reseta tudo · `⌫` apaga o último dígito (botão "seta" do original).
- Vírgula decimal pt-BR (única, como no original).
- **Teclado físico** suportado (dígitos, operações, Enter = igual, Backspace, Esc = CE),
  como o `KeyListener` do Java.
- Divisão por zero exibe **Erro** (o original estourava o cast `(int)` — corrigido).

### Fidelidade e correções em relação ao Java de 2022

O `AppCalc.java` original acumulava tudo com **casts `(int)`**, perdendo casas decimais
(`9 ÷ 2` mostrava `4`). A versão web mantém o mesmo comportamento de interação
(dígitos com regra do zero à esquerda, C/CE/backspace, operação imediata acumulada)
com **aritmética de ponto flutuante correta**.

## Estrutura

```
calculadora/
├── index.html          # layout fiel ao GroupLayout do Swing
├── style.css
├── js/
│   ├── calc-core.js    # lógica pura (testável em Node)
│   └── app.js          # UI + teclado físico
├── test/calc-test.mjs  # 17 testes (npm test)
├── server.js           # servidor estático Node puro (zero deps)
└── java/               # ✔ PROJETO ORIGINAL 2022 (workspace Eclipse) preservado
    ├── frontEnd/AppCalc.java
    └── resource/       # seta.png e demais ícones do original
```

### Rodar a versão Java original (histórica)

```bash
cd java
javac frontEnd/AppCalc.java
java -cp .:frontEnd frontEnd.AppCalc   # Windows: -cp ".;frontEnd"
```

## Licença

MIT — veja [LICENSE](LICENSE).
