# Roman Numerals Encoder: Verificação e Validação (Trabalho T1)

Este repositório contém a solução para o *Kata* "Roman Numerals Encoder" e a aplicação de duas técnicas de teste de software, conforme exigido pelo Trabalho T1 da disciplina de Verificação e Validação.

Link para o problema: https://www.codewars.com/kata/51b62bf6a9c58071c600001b

O objetivo do projeto é converter números inteiros positivos (de 1 a 3999) em sua representação em numerais romanos, enquanto demonstramos o uso de **Teste Baseado em Contrato (Design by Contract)** e **Teste Metamórfico** como oráculos de teste.

## Tecnologias Utilizadas

* **Linguagem:** Java
* **Plataforma de Teste:** JUnit 5
* **Sistema Sob Teste (SUT):** `Conversion.java`

## Técnicas de Teste Aplicadas (Oráculos)

A verificação do comportamento correto da função de conversão foi realizada utilizando as seguintes técnicas de oráculo, discutidas em nossa Resenha Crítica.

### 1. Teste Baseado em Contrato 
O **Design by Contract (DbC)** define formalmente as obrigações e garantias da função sob teste.  
Os testes da classe `ConversionContractTest` validam cada elemento do contrato:

| Elemento do Contrato | Descrição | Teste Correspondente |
| :--- | :--- | :--- |
| **Pré-condição** | A função deve aceitar apenas números no intervalo **[1, 3999]**. Entradas fora desse domínio devem causar erro. | `precondition_rejectsOutOfRange()` verifica se números inválidos lançam exceções. |
| **Pós-condição — Alfabeto** | A saída deve conter **apenas caracteres válidos** do alfabeto romano (`M, D, C, L, X, V, I`). | Validado em `postcondition_alphabetAndCanonical_selected()` usando regex. |
| **Pós-condição — Forma Canônica** | A saída deve seguir a **representação canônica** dos numerais romanos (sem repetições ou subtrações incorretas). | Também verificado em `postcondition_alphabetAndCanonical_selected()`. |
| **Pós-condição — Casos Ouro** | A função deve gerar resultados **iguais aos esperados** em exemplos clássicos. | `knownPairs_andSubtractivePairs()` verifica pares conhecidos e subtrações válidas (`IV`, `IX`, `XL`, etc.). |
| **Limites do Domínio** | Garante o comportamento correto nas extremidades do intervalo. | `boundaries_minAndMax()` testa 1 e 3999. |
| **Invariante — Pureza** | A função deve ser determinística, produzindo sempre a mesma saída para a mesma entrada. | `purity_sameInputSameOutput()` confirma que o método é puro. |

Esses testes garantem que o comportamento da função está **conforme o contrato formal** estabelecido pelo problema.

---

### 2️⃣ Teste Metamórfico

O **Teste Metamórfico** é aplicado quando não há um oráculo direto ou quando desejamos verificar **relações consistentes entre diferentes entradas e saídas**.  
Na classe `ConversionOracleTest`, utilizamos **dois tipos de metamorfismo**:

| Tipo de Oráculo | Descrição | Teste Correspondente |
| :--- | :--- | :--- |
| **Inversão (Round-trip)** | Codificar um número e depois decodificar o resultado deve retornar o número original. | `roundTrip_wholeDomain()` testa `decode(encode(n)) == n` para todo `n ∈ [1, 3999]`. |
| **Concatenação Segura** | Concatenar duas representações romanas deve resultar em uma soma coerente (`decode(encode(a)+encode(b)) == a+b`). | `metamorphic_decodeConcatEqualsSum_onSafePairs()` valida combinações seguras, como `X + III = XIII`. |

Além disso, um **teste de sanidade (“gold table”)** compara um pequeno conjunto de pares conhecidos para confirmar a consistência geral do encoder (`goldTable_smallSet()`).

---
## 🤝 Contribuições

Este trabalho foi desenvolvido em equipe. A responsabilidade e contribuição de cada membro foram as seguintes:

* **Lorenzo Souza**: Implementação do código-fonte, Estrutura do repositório, Elaboração da resenha.
* **Rafael Sasso**: Desenvolvimento da Apresentação, Elaboração da resenha, auxílio no código-fonte.
* **Samuel Ribeiro Bitdinger**: Elaboração da resenha, auxílio no código-fonte e elaboração do README.

