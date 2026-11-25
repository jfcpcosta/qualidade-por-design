# Qualidade por Design

Código de apoio à talk **Qualidade por Design: como arquitetura e boas práticas reduzem
bugs em produção** (Moongy Sprint Talk).

Uma loja online, uma Black Friday e uma alteração de três linhas que correu mal. Cada
módulo é um ato da talk: o mesmo problema, cada vez mais bem desenhado.

```bash
git clone https://github.com/jfcpcosta/qualidade-por-design.git
```

## Pré-requisitos

- JDK 25 ou mais recente
- Maven 3.9 ou mais recente

```bash
mvn test                 # os quatro atos
mvn -Pspring test        # inclui o módulo Spring Boot
```

## Os atos

| Módulo | O que mostra | Por onde começar |
| --- | --- | --- |
| `ato-0-incidente` | A regra de desconto vive em 4 sítios, com 4 arredondamentos diferentes. Os testes passam. | `loja/Sexta.java` |
| `ato-1-coesao` | Uma `PricingPolicy` calcula o preço uma vez; checkout, fatura, email e ERP só o mostram. | `loja/precos/PricingPolicy.java` |
| `ato-2-sem-dogmas` | A mesma regra com 10 classes (`enterprise`) e com 2 (`simples`). Duplicação boa e abstração errada (`taxas`). | `loja/descontos/` |
| `ato-3-testavel` | `Clock` injetado, estados `sealed` com `switch` exaustivo, núcleo puro e portas à volta. | `loja/campanhas/`, `loja/estados/` |
| `spring-adapter` | O domínio do ato 3, sem uma anotação, ligado por Spring Boot. | `loja/web/LojaConfiguration.java` |

## Ver o incidente

```bash
mvn -q -pl ato-0-incidente compile
java -cp ato-0-incidente/target/classes loja.Sexta
```

A tabela compara o valor cobrado, faturado, enviado por email e exportado para o ERP.
Repara que, mesmo antes da Black Friday, a encomenda `BF-1003` já tinha 1 cêntimo de
diferença no ERP.

Agora aplica a alteração da Black Friday (está em `demos/demo-1/CheckoutService.java`),
corre `mvn -q -pl ato-0-incidente test` e volta a correr a simulação. Os testes continuam
verdes.

Compara com o ato 1:

```bash
mvn -q -pl ato-1-coesao compile
java -cp ato-1-coesao/target/classes loja.Sexta
```

## Experimenta tu

| Desafio | Onde | O que deve acontecer |
| --- | --- | --- |
| Acrescentar um desconto "Cliente frequente" de 5% | `ato-2-sem-dogmas`, nas duas versões | Na `simples`, o compilador aponta o `switch` que falta. Na `enterprise`, conta quantos ficheiros tens de abrir. |
| Acrescentar o estado `PartiallyRefunded` | `ato-3-testavel/.../OrderStatus.java` | O compilador aponta cada `switch` que ainda não sabe o que fazer. |
| Escrever um teste para `LegacyCampaignCheck` às 23h30 de sexta | `ato-3-testavel` | Não consegues sem mudar o relógio da máquina. Compara com `CampaignTest`. |
| Juntar `ShippingFee` e `ReturnFee` numa só classe | `ato-2-sem-dogmas/.../taxas` | Funciona hoje. Agora a logística sobe o limite para 60 €. |

As soluções das demos estão em `demos/`.

## Sete perguntas para o próximo code review

| Pergunta | O que denuncia |
| --- | --- |
| Se esta regra mudar amanhã, quantos ficheiros abro? | Rigidez, conhecimento duplicado |
| Esta informação já é calculada noutro sítio? | Duas fontes de verdade |
| O que é que este código vai buscar sozinho? | Hora, config, estáticos escondidos |
| Consigo testar a decisão sem base de dados nem rede? | Regras misturadas com IO |
| Que estados impossíveis este tipo permite? | Nulls e flags que se contradizem |
| Esta abstração tem mais do que um caso hoje? | Design especulativo |
| Quem é o dono desta regra? | Responsabilidade difusa |

## Leituras

- John Ousterhout, *A Philosophy of Software Design*: complexidade, módulos profundos.
- Sandi Metz, *The Wrong Abstraction*: "duplication is far cheaper than the wrong abstraction".
- Gary Bernhardt, *Boundaries*: functional core, imperative shell.
- Michael Nygard, *Release It!*: o que acontece depois do deploy.
- Kent Beck, *Tidy First?*: pequenas arrumações antes de mudar comportamento.
