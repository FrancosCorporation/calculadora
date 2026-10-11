# MELHORIAS — calculadora

> **Gerado por analise de codigo em 2026-10-02** · Stack: Node 18 (servidor estatico sem framework) + JS puro no browser, **zero dependencias**
> Branch `master` · 150 LOC · **1 suite de teste** · sem CI · porta padrao `3349`
>
> **Este arquivo e um plano de execucao.** Cada item tem ID, `arquivo:linha`, mudanca exata,
> criterio de aceite e comando de verificacao.

## Status de implementacao — 2026-10-11

- Implementados no codigo: allowlist, headers/CSP, tratamento de URL invalida, ETag/cache, pagina 404,
  fallback para falha de JavaScript e bind local. O servidor ja usava leitura assincrona antes desta
  atualizacao. A porta padrao mudou de 3000 para 3349.
- Corrigido o carregamento de `js/app.js`: a página agora declara o script como módulo, necessário
  para resolver o import de `js/calc-core.js`.
- Criados workflow de CI, Dockerfile e `SECURITY.md`. CI, build/container e CSP no navegador ainda nao
  foram validados.
- O exemplo Docker do README publica a porta somente em `127.0.0.1`, em linha com o padrao local do servidor.
- `TEST-01` segue pendente; a suite existente nao foi alterada nem executada nesta rodada.

---

## 0. Como usar este documento

1. Execute na ordem **P0 -> P1 -> P2 -> P3**, respeitando as ondas da secao 8.
2. Ao terminar um item: marque `- [x]`, rode o **Verificacao**, comite `fix(<ID>): descricao`.
3. **O jogo e a logica estao bem testados e nao devem ser simplificados.** O defeito principal
   esta no **servidor de arquivos** (mesmo `server.js` em toda a serie). Nao mexa nas regras/IA sem causa.
4. **Este item se aplica a toda a serie** (`xadrez_ia`, `damas_ia`, `lig4_ia`, `botao_ia`,
   `jogo_da_velha_ia`, `calculadora`): o `server.js` e praticamente identico. Replique a correcao.
5. **Idioma:** portugues.

---

## 1. Diagnostico executivo

Calculadora client-side com teclado na tela e suporte ao teclado físico. O núcleo das operações fica em
`js/calc-core.js`; o servidor Node serve somente os arquivos estáticos da interface.

**O que esta bem (nao reaca):**

| Item | Evidencia |
|---|---|
| Núcleo da calculadora é separado da UI e testável no Node | `js/calc-core.js`, `test/calc-test.mjs` |
| Operações e formatação pt-BR sem dependências externas | `js/calc-core.js` |
| Teclado físico e botões da tela usam as mesmas funções | `js/app.js` |
| Servidor estático usa Node puro, sem dependências | `server.js`, `package.json` |

**Falhas da analise inicial e estado atual:**

1. O servidor permitia ler `.git` e qualquer arquivo sob a raiz. Agora cada rota precisa constar em
   `PUBLICOS`; fontes Java, configuracoes, documentacao e testes ficam fora da lista.
2. O servidor ja usava leitura assincrona; agora tambem configura MIME, headers, CSP, ETag/cache,
   bind local e 404 amigavel.
3. O workflow de CI foi criado para Node 18 e 22; sua execucao no GitHub ainda nao foi confirmada.
4. A porta padrao mudou de 3000 para 3349 para evitar colisao com os projetos vizinhos.

---

## 2. Tabela de prioridades

| ID | Titulo | Sev | Arquivo | Depende de |
|---|---|---|---|---|
| SEC-01 | Servidor expoe `.git/` (historico e config) | **P0** | `server.js:13-20` | — |
| SEC-02 | Sem allowlist de arquivos servidos | **P0** | `server.js:13-20` | SEC-01 |
| SEC-03 | Sem headers de seguranca no servidor estatico | **P1** | `server.js:19` | — |
| SEC-04 | `decodeURIComponent` sem try/catch derruba a requisicao | **P1** | `server.js:11` | — |
| BUG-01 | Servidor le arquivo **sincrono** a cada request | **P2** | `server.js:20` | — |
| IMP-01 | Sem cache de assets (revalida a cada load) | **P2** | `server.js:19-20` | — |
| IMP-02 | Sem CSP para a calculadora estática | **P2** | `server.js:19` | SEC-03 |
| IMP-03 | Sem `404` amigavel / pagina de erro | **P3** | `server.js:15-17` | — |
| TEST-01 | Testes nao cobrem o servidor (so a logica) | **P1** | `test/` | SEC-01 |
| DEVOPS-01 | Sem CI | **P2** | *(ausente)* | — |
| DEVOPS-02 | Sem Dockerfile | **P3** | *(ausente)* | — |
| DOC-01 | README nao avisa que expor o servidor expoe o repo | **P2** | `README.md` | SEC-01 |

**Placar: 2 P0 · 3 P1 · 5 P2 · 2 P3 = 12 itens.**

---

## 3. Seguranca

### SEC-01 · Servidor expoe `.git/` (historico e config) · [P0]

- **Arquivo:** `server.js:13-20`
- **Evidência:** validado **em execucao real**:
  ```
  GET /.git/config -> 200
  GET /.git/HEAD   -> 200
  GET /../etc/passwd -> 404   (travessia de path bloqueada — correto)
  ```
  A guarda (`server.js:14`) verifica que o caminho **comeca** com a raiz — o que impede *sair*, mas
  **nao** impede ler dotfiles *dentro* dela, e `.git/` esta na raiz.
- **Impacto:** quem acessa o servidor obtem o **repositorio git inteiro**: historico
  (`.git/objects`), config (`.git/config`, remote, branch) e hooks. Se algum segredo esteve
  commitado no passado (padrao recorrente neste acervo), ele **vaza por aqui**. Para um jogo que
  precisa servir so HTML/CSS/JS, expor `.git` e desnecessario por completo.
- **Mudança:** **allowlist explicita** do que e servido — so o que o jogo precisa:
  ```javascript
  const PUBLICOS = new Set(['/index.html', '/style.css']);
  const DIRETORIOS_PUBLICOS = ['/js/'];
  function permitido(caminho) {
    if (PUBLICOS.has(caminho)) return true;
    return DIRETORIOS_PUBLICOS.some((d) => caminho.startsWith(d));
  }
  ```
  Negar tambem qualquer caminho que comece com `.` (`.git`, `.env`, `.npmrc`) — defense em profundidade.
- **Aceite:** `GET /.git/config`, `/.git/HEAD`, `/.env` -> **404**; `/index.html`, `/js/...` -> **200**.
- **Verificação:**
  ```bash
  PORT=3349 node server.js & sleep 1
  for p in /.git/config /.git/HEAD /.env .gitignore; do
    curl -s -o /dev/null -w "$p -> %{http_code}\n" "http://localhost:3349$p"   # esperado 404
  done
  kill %1
  ```

### SEC-02 · Sem allowlist de arquivos servidos · [P0]

- **Arquivo:** `server.js:13-20`
- **Evidência:** alem do `.git`, o servidor serve **qualquer arquivo existente na raiz** —
  `package.json`, `README.md`, `test/`, e qualquer coisa adicionada depois.
- **Impacto:** vazamento de codigo alem do necessario e **armadilha de recorrencia**: qualquer
  segredo novo colocado na raiz **vaza automaticamente**. Mesmo modo de falha do `webhook-relay`.
- **Mudança:** a mesma **allowlist** do `SEC-01`, aplicada a todo caminho — negar por omissao.
- **Aceite:** `GET /package.json`, `/README.md`, `/test/*` -> **404**.
- **Verificação:**
  ```bash
  for p in /package.json /README.md; do
    curl -s -o /dev/null -w "$p -> %{http_code}\n" "http://localhost:3349$p"   # 404
  done
  ```

### SEC-03 · Sem headers de seguranca no servidor estatico · [P1]

- **Arquivo:** `server.js:19`
- **Evidência:** `res.writeHead(200, { 'Content-Type': ... })` — so o Content-Type.
- **Impacto:** sem `nosniff`, um arquivo pode ser interpretado como outro tipo; sem `X-Frame-Options`,
  o jogo pode ser embutido (clickjacking); sem `Referrer-Policy`, defense em profundidade.
- **Mudança:** adicionar `X-Content-Type-Options: nosniff`, `X-Frame-Options: SAMEORIGIN`,
  `Referrer-Policy: no-referrer` no `writeHead` (sem dependencia, como o resto do projeto).
- **Aceite:** respostas trazem `nosniff` e `X-Frame-Options`.
- **Verificação:**
  ```bash
  curl -sI http://localhost:3349/index.html | grep -iE 'x-content-type-options|x-frame-options'
  ```

### SEC-04 · `decodeURIComponent` sem try/catch · [P1]

- **Arquivo:** `server.js:11`
- **Evidência:** `decodeURIComponent(req.url.split('?')[0])` lanca `URIError` em string malformada
  (`%`, `%zz`, `%E0%A4%A`).
- **Impacto:** a excecao lanca dentro do handler e nao e capturada — a requisicao fica **sem resposta**
  (socket fecha sem status) e o log mostra stack. Um `curl 'http://host/%'` por requisicao.
- **Mudança:** envolver em `try/catch` e devolver `400`.
- **Aceite:** `GET /%` -> **400**, sem excecao no log.
- **Verificação:**
  ```bash
  curl -s -o /dev/null -w '%{http_code}\n' 'http://localhost:3349/%'   # 400
  ```

---

## 4. Bugs e defeitos funcionais

### BUG-01 · Servidor le arquivo **sincrono** a cada request · [P2]

- **Arquivo:** `server.js:20` (`res.end(readFileSync(arquivo))`)
- **Evidência:** `readFileSync` **bloqueia o event loop** a cada requisicao.
- **Impacto:** as requisicoes se serializam; um asset grande trava as outras. Relevante porque o jogo
  baixa modelo (~350 MB) — muitas requisicoes de asset.
- **Mudança:** `fs.promises.readFile` com handler async; ou pre-carregar os poucos assets no boot.
- **Aceite:** requisicoes concorrentes nao se bloqueiam.
- **Verificação:** `curl -w %{time_total}` em 2 requisicoes simultaneas -> ambas rapidas.

### IMP-01 · Sem cache de assets · [P2]

- **Arquivo:** `server.js:19-20`
- **Evidência:** toda requisicao rele do disco, sem `ETag`/`Cache-Control`.
- **Impacto:** cada reload rebaixa `js/*.js`. Pequeno, mas sao requisicoes repetidas sem cache HTTP.
- **Mudança:** `ETag` (hash no boot) + `Cache-Control` curto p/ HTML, longo p/ JS; responder `304`.
- **Aceite:** segundo request do mesmo asset -> `304`.
- **Verificação:** `curl -sI http://localhost:3349/js/calc-core.js | grep -iE 'etag|cache-control'`

### IMP-02 · Sem CSP · [P2]

- **Arquivo:** `server.js:19`
- **Evidência:** nenhuma `Content-Security-Policy`.
- **Impacto:** a calculadora usa JavaScript e CSS locais; sem CSP, uma injecao futura no documento
  poderia carregar scripts ou estilos de outra origem.
- **Mudança:** CSP restritiva para assets locais (`default-src 'self'`, sem scripts inline).
- **Aceite:** CSP presente e os assets locais permitidos.
- **Verificação:** `curl -sI .../index.html | grep -i content-security-policy` e conferir no browser.

### IMP-03 · Sem `404` amigavel / pagina de erro · [P3]

- **Arquivo:** `server.js:15-17`
- **Evidência:** `res.writeHead(404, {'Content-Type':'text/plain'}); res.end('404');`
- **Impacto:** URL errada mostra "404" cru. E se o JS falhar ao carregar, a pagina fica em branco.
- **Mudança:** rota `.html` desconhecida cai no `index.html` (SPA-like) ou 404 estilizado; `<noscript>`
  + `window.onerror` com aviso em vez de tela branca.
- **Aceite:** rota desconhecida mostra a pagina do jogo (ou 404 estilizado).
- **Verificação:** `curl -s http://localhost:3349/qualquer | head -1` mostra HTML

---

## 5. Qualidade: testes

### TEST-01 · Testes nao cobrem o servidor (so a logica) · [P1]

- **Arquivo:** `test/calc-test.mjs`
- **Evidência:** os testes cobrem a lógica de cálculo; nenhum toca o server.js — por isso
  o `.git` exposto (P0) e o `decodeURIComponent` passaram despercebidos.
- **Impacto:** o servidor e a superficie de rede, e esta **sem nenhum teste**. Os P0 sao exatamente o
  que um teste de rota pegaria.
- **Mudança:** `test/servidor-test.mjs` (subindo o server em porta aleatoria): `/index.html` -> 200;
  `/.git/config` -> 404; `/package.json` -> 404; `/../etc/passwd` -> 404; `/%` -> 400 (apos `SEC-04`);
  headers de seguranca presentes.
- **Aceite:** a suite nova cobre os 2 P0 e falha se a allowlist for removida.
- **Verificação:** `npm test 2>&1 | tail -3`

---

## 6. DevOps / Infra

### DEVOPS-01 · Sem CI · [P2]

- **Arquivo:** *(ausente)* `.github/workflows/`
- **Evidência:** `package.json` tem `test` mas nenhum workflow o executa.
- **Impacto:** os testes so rodam se alguem lembrar; o servidor nem e exercitado.
- **Mudança:** `ci.yml` com `npm ci`, `npm run check` e `npm test`.
- **Aceite:** PR que quebra o jogo e bloqueado.
- **Verificação:** `npm test 2>&1 | tail -2 && node --check server.js`

### DEVOPS-02 · Sem Dockerfile · [P3]

- **Arquivo:** *(ausente)* `Dockerfile`
- **Evidência:** roda com `node server.js` (zero deps).
- **Impacto:** baixo, mas o Dockerfile resolve o `SEC-01` por **construcao**: copiar so o necessario
  (`.git` nunca entra na imagem).
- **Mudança:** imagem Node enxuta, copiando somente `index.html`, `404.html`, `style.css`, `js/app.js`,
  `js/calc-core.js`, `js/erro.js` e `server.js`.
- **Aceite:** `docker build` + `run` serve o jogo; `.git` nem existe na imagem.
- **Verificação:** `docker build -t calcu-by-dolfim . && docker run --rm -p 127.0.0.1:3349:3349 calcu-by-dolfim`; consultar `/index.html`.

---

## 7. Documentacao

### DOC-01 · README nao avisa que expor o servidor expoe o repo · [P2]

- **Arquivo:** `README.md`
- **Evidência:** o README explica o jogo e a IA; nao menciona que `node server.js` serve a pasta
  inteira (incluindo `.git`).
- **Impacto:** quem publica o jogo nao sabe que precisa da allowlist do `SEC-01`.
- **Mudança:** secao "Servir": comando correto + aviso de que o servidor original expoe o repo (usar
  a allowlist, GitHub Pages, ou servidor de estatico com allowlist).
- **Aceite:** README avisa sobre a exposicao do repo ao servir.
- **Verificação:** `grep -ni 'servidor\|git\|pages' README.md`

---

## 8. Ordem de execucao (waves)

### Wave 1 — Fechar a exposicao do servidor (P0)
1. **`SEC-02`** — allowlist do que e servido (raiz do problema).
2. **`SEC-01`** — `.git` e dotfiles bloqueados (garantido pela allowlist).

### Wave 2 — Robustez (P1)
3. **`SEC-04`** — `decodeURIComponent` com try/catch (`400`).
4. **`SEC-03`** — headers de seguranca.
5. **`TEST-01`** — suite do servidor (pega os 2 P0).

### Wave 3 — Qualidade/perf (P2)
6. **`BUG-01`** — leitura assincrona; **`IMP-01`** — cache; **`IMP-02`** — CSP.
7. **`DEVOPS-01`** — CI; **`DOC-01`** — README.

### Wave 4 — Polimento (P3)
8. **`IMP-03`**, **`DEVOPS-02`**.

**Dependencias:** `SEC-02` antes de `SEC-01` (a allowlist e a solucao) · `TEST-01` depois dos P0 ·
`SEC-04` junto com `TEST-01` (o `%` e um dos casos) · `SEC-03`/`IMP-02` so depois da allowlist.

---

## 9. Fora de escopo / riscos

| Item | Decisao | Motivo |
|---|---|---|
| Migrar o servidor para Express | **Nao** | Zero deps e escolha de qualidade; a allowlist resolve em ~8 linhas. |
| Servir via GitHub Pages | **Nao, aqui** | Compativel (estatico), mas e decisao de deploy — mencionar no `DOC-01`. |
| Servir os fontes Java históricos | **Nao** | A calculadora web precisa apenas dos assets listados na allowlist. |
| Porta padrao 3000 | **Corrigido** | Agora usa 3349, configuravel por `PORT`. |

**Riscos desta execucao:**

- **`SEC-02` (allowlist) pode quebrar a calculadora** se faltar o HTML, CSS ou algum script local.
- **`IMP-02` (CSP) deve permitir os scripts e estilos locais**; conferir no browser após o deploy.
- **Este defeito e da serie inteira** — replicar em `xadrez_ia`, `damas_ia`, `lig4_ia`, `botao_ia`,
  `jogo_da_velha_ia`, `calculadora`. Ver `PLANO_MELHORIAS_MASTER.md`.

---

## 10. Definicao de pronto (DoD)

**Seguranca**
- [x] `SEC-01` — `.git`, dotfiles e rotas nao publicas sao recusados pela allowlist
- [x] `SEC-02` — allowlist exata contem somente os arquivos usados pela calculadora
- [x] `SEC-03` — headers de seguranca configurados em todas as respostas
- [x] `SEC-04` — caminho malformado retorna 400

**Funcional**
- [x] `BUG-01` — servidor ja usava leitura assincrona antes desta atualizacao
- [x] `IMP-01` — ETag e Cache-Control implementados
- [ ] `IMP-02` — CSP implementada; validacao no browser pendente
- [x] `IMP-03` — rota desconhecida usa pagina 404 amigavel e ha aviso para falhas JS

**Testes e infra**
- [ ] `TEST-01` — suite do servidor cobrindo os 2 P0
- [ ] `DEVOPS-01` — workflow criado; execucao CI ainda nao confirmada
- [ ] `DEVOPS-02` — Dockerfile copia somente arquivos publicos; build/run ainda nao confirmados
- [x] `DOC-01` — README documenta allowlist, porta local e bind de rede intencional

**Validacao final:**
```bash
npm test 2>&1 | tail -2 && node --check server.js
PORT=3349 node server.js & sleep 1
curl -s -o /dev/null -w '/.git/config -> %{http_code}\n' http://localhost:3349/.git/config   # 404
kill %1
```

---

*Plano inicial gerado em 2026-10-02. `BUG-01` ja estava resolvido na base; os demais itens listados
foram confirmados antes desta implementacao.*
