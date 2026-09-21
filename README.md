# Calculadora

Calculadora desktop em Java Swing — **projeto de estudo** desenvolvido em 2022.

![Java](https://img.shields.io/badge/Java-8-orange?logo=openjdk&logoColor=white)
![License](https://img.shields.io/badge/license-MIT-green)
![Status](https://img.shields.io/badge/status-conclu%C3%ADdo%20(estudo)-blue)

## Sobre

Aplicativo de calculadora com interface gráfica (janela `JFrame`) construído com Java Swing e `GroupLayout`.
Foi um dos primeiros projetos de programação do autor, feito para praticar interfaces, eventos de botões e
tratamento de números em Java. É um **projeto de estudo**, não um produto.

## Funcionalidades

- Operações básicas: soma, subtração, multiplicação e divisão (`+`, `-`, `*`, `/`).
- Visor numérico com `JFormattedTextField` aceitando apenas números.
- Botões `C` (limpa tudo), `CE` (limpa operação) e botão de apagar último dígito.
- Suporte a teclado para os dígitos 0–9 e para a tecla `+` do teclado numérico.
- Botão de vírgula decimal.
- Ícone do botão de apagar carregado de `src/resource/seta.png`.

## Stack

- **Java 8** (Eclipse `JavaSE-1.8`).
- **Java Swing / AWT** (`JFrame`, `JButton`, `JPanel`, `GroupLayout`, `KeyAdapter`).
- Projeto configurado para **Eclipse** (`.classpath`, `.project`).

## Como rodar

Requer JDK 8+ instalado. Não há build automatizado (Maven/Gradle) no repositório — o projeto é Eclipse puro.

Via linha de comando, a partir da raiz do repositório:

```bash
javac -d bin src/frontEnd/AppCalc.java
java -cp bin frontEnd.AppCalc
```

Ou importe a pasta no Eclipse/IntelliJ como projeto Java existente e execute a classe `frontEnd.AppCalc`.

> Observação: o `.classpath` da raiz referencia as bibliotecas `miglayout15-swing.jar` e
> `jgoodies-forms-1.8.0.jar`, que **não** estão versionadas neste repositório (existem apenas na cópia
> deste projeto dentro do repositório [Portfolio](https://github.com/FrancosCorporation/Portfolio)).
> O código de `AppCalc.java` não usa essas bibliotecas.

## Estrutura do projeto

```
calculadora/
├── index.html                  # página estática (GitHub Pages) apontando para o código
├── src/
│   ├── frontEnd/
│   │   └── AppCalc.java        # classe principal (main) da calculadora
│   └── resource/
│       └── seta.png            # ícone do botão de apagar
├── .classpath / .project       # configuração Eclipse
└── README.md
```

## Licença

MIT — veja [LICENSE](LICENSE).
