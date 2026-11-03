# CRUD Produtos + Logger Singleton

Rodar: `mvn spring-boot:run` (Java 17). Logs no console e em `app.log`.

## Respostas

**Quem garante a instância única em cada versão?**
- `AppLogger` (clássico): o próprio código — construtor privado + `getInstance()` com `volatile` e dupla checagem em `synchronized`.
- `SpringAppLogger` (`@Component`): o container Spring, que cria um único bean por contexto (escopo `singleton`). Nada impede um `new` manual.

**Quando o Singleton clássico ainda faz sentido?**
- Código fora do container Spring (utilitários estáticos, `main`, bibliotecas sem Spring) ou quando a instância única precisa valer para toda a JVM, não só para um contexto.

## Versão final: `AppLogger` (clássico)
Service e Controller usam `AppLogger.getInstance()`. Justificativa: é o foco do trabalho, não depende do Spring, não exige injeção em cada classe e garante instância única na JVM. Contra: mais difícil de mockar em testes; em projeto real, o bean Spring seria preferível por isso.
