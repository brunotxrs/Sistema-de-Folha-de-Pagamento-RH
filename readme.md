# 📄 README.md - Sistema de Folha de Pagamento RH

## 🚀 Visão Geral do Projeto
Este projeto é uma solução completa em Java, desenvolvida como 
resposta ao **Desafio da Atividade 2** (Contexto RH e Folha de Pagamento)
que exigia a aplicação prática dos conceitos de **Programação Orientada
a Objetos (POO)**, **Herança**, e **Polimorfismo**.

O sistema permite o cadastro e gerenciamento de dois tipos de 
funcionários — **Assalariados** e **Horistas** — em uma única lista 
polimórfica, aplicando cálculos de pagamento e aumentos gerais de 
forma dinâmica e robusta.
---

## ✨ Principais Funcionalidades e Conceitos Aplicados
1. **Estrutura POO e Polimorfismo**
- **Classe Abstrata** `Employee`: Define a estrutura base e os 
dados comuns (Nome, CPF, Setor, Contato) para todos os funcionários.

- **Método Abstrato** `calculatePayment()`: Força as classes filhas 
a implementarem seu próprio cálculo de salário.

- **Herança Polimórfica**: As classes `Salaried` e `Hourly` 
estendem `Employee`, permitindo que sejam tratadas genericamente 
em uma única lista (`List<Employee>`).

- **Aumento Geral** (`applyIncrease`): O método para aplicar 
aumento percentual é centralizado na classe `Employee`, garantindo 
que funcione para ambos os tipos de funcionários.

2. **Robustez e Experiência do Usuário (UX)** <br>
O foco na qualidade de entrada de dados garante que o programa 
não quebre, mesmo com entradas incorretas (letras, símbolos, ou vazias):

- **Validação de Input**: Uso de loops `while(true)` e blocos 
`try-catch` para garantir que o usuário insira apenas tipos de 
dados corretos (números para valores monetários, inteiros para horas).

- **Tipagem Segura**: Utilização da classe `java.math.BigDecimal` 
para todos os cálculos monetários (salário, taxa por hora, aumento), 
prevenindo erros de ponto flutuante.

- **Formatação de Dados**: Máscaras de saída para CPF (`XXX.XXX.XXX-XX`) 
e Contato (`(XX) X XXXX-XXXX`), além da formatação de moeda brasileira
(R$) nos pagamentos.

- **Controle de Fluxo Robustos**: Métodos específicos (`readValidName`, 
`readValidCPF`, `readValidYesNo`) para garantir a integridade e o 
formato das informações (ex: checagem de 11 dígitos no CPF, 
apenas letras no nome).
---

## 💻 Estrutura do Projeto (Classes)
A organização do código em classes (`entities`) e interação (`interaction`) demonstra o uso correto dos princípios de POO.

| Classe | Tipo | Função |
| :--- | :--- | :--- |
| **`Employee.java`** | Abstrata | Classe base. Contém dados comuns, o método `applyIncrease()` e o método abstrato `calculatePayment()`. |
| **`Salaried.java`** | Concreta | Extende `Employee`. Implementa `calculatePayment()` retornando o salário mensal. |
| **`Hourly.java`** | Concreta | Extende `Employee`. Implementa `calculatePayment()` com a lógica `horas * valor_hora`. |
| **`UI.java`** | Interação | Contém a lista polimórfica (`List<Employee>`) e toda a lógica de entrada/saída de dados (`Scanner`) e validação (robustez). |
| **`Main.java`** | Execução | Classe principal para iniciar o programa (`runUI.runProgram()`). |
---
## 💻 Como Executar

1. **Ambiente**: Certifique-se de ter o JDK (Java Development Kit) 
instalado.
2. **Compilação**: Compile todos os arquivos `.java` no seu IDE 
(NetBeans, IntelliJ, VS Code) ou via terminal.
3. **Execução**: Execute a classe principal: `Main.java`.

O programa o guiará através das etapas de cadastro de até 10 
funcionários, exibição de dados, aplicação do aumento geral e 
exibição final dos novos pagamentos.

----

## 🧑‍💻 Autor
Este projeto foi desenvolvido como parte do desafio da
**Atividade 2** do curso: TDS - Módulo A - Desenvolver código orientado a objetos

**Bruno Teixeira**

**Técnico em Desenvolvimento de Sistemas**

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)]([https://github.com/brunotxrs])
