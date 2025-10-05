- Responda em Português (pt-BR).
- Saída OBRIGATÓRIA em Conventional Commits v1.0.0 válida.
- ÚNICO commit: escolha exatamente 1 `type` (veja “Seleção de type”).
- Cabeçalho: `type(scope opcional)!: assunto em imperativo` (≤72 chars, sem ponto final).
- `scope` (quando usado) deve ser ÚNICO e curto (ex.: api, ui, deps). PROIBIDO múltiplos scopes.
- Corpo: bullets; cada bullet inicia com verbo no imperativo (Adiciona, Remove, Corrige, Atualiza, Refatora, Ajusta, Otimiza, etc.).
- NÃO use verbos em outros tempos.
- Quebra de compatibilidade: adicione `!` e uma linha `BREAKING CHANGE: descrição sucinta`.

Seleção de `type` (uma única escolha):
1) Se o commit reverte algo e não faz mais nada → `revert`.
2) Caso contrário, classifique cada bullet e conte por categoria; escolha o `type` com MAIOR contagem.
3) Empate → prioridade: `feat` > `fix` > `perf` > `refactor` > `build` > `ci` > `docs` > `test` > `chore`.
4) Regras de intenção (sobrepõem contagem quando presentes):
  - Nova funcionalidade/alteração visível → `feat`.
  - Correção de bug → `fix`.
  - Apenas desempenho → `perf`.
  - Apenas refatoração sem mudar comportamento → `refactor`.
  - Somente dependências/build/toolchain/empacotamento → `build`.
  - Somente pipeline/automação → `ci`.
  - Somente documentação → `docs`.
  - Somente testes → `test`.
  - Caso contrário → `chore`.
  - Se juntar feature/bug com mudanças de dependência, mantenha `feat` ou `fix` (NÃO use `build`).

Mapeamento de `type` (uso automático conforme contexto):
- feat, fix, docs, style, refactor, perf, test, build, ci, chore, revert.

Regras específicas de bullets:
- Dependências (apenas nos bullets; o `type` segue as regras acima):
  - Altera versão da dependência de `grupo:artifact@versão-antiga` para `grupo:artifact@versão-nova`
  - Adiciona a dependência `grupo:artifact@versão`
  - Remove a dependência `grupo:artifact@versão`
  - Dica de `scope` quando pertinente: `deps`
- Variáveis de ambiente/propriedades:
  - Runtime/app config → geralmente `chore` (ou `feat/fix` se afetar comportamento do usuário)
  - Build-time/tooling → `build`
  - CI/CD → `ci`
  - Bullets:
    - Altera variável de ambiente `NOME_VARIAVEL` de `valor-antigo` para `valor-novo`
    - Adiciona variável de ambiente `NOME_VARIAVEL`

Rodapé (opcional, SOMENTE quando houver identificador explícito):
- Fonte: `$GIT_BRANCH_NAME`.
- NÃO inferir identificador para `develop`, `main`, `release/*`, `hotfix/*`. Nesses casos, OMITA o rodapé `Refs`.
- Se (e somente se) `$GIT_BRANCH_NAME` contiver identificador no formato **`[A-Z][A-Z0-9_]+[-_]\d+`** (ex.: `ABC-123`, `TASK-42`, `GH-7`, `PROJ_999`), use a última ocorrência como `IDENTIFICADOR_DA_TAREFA`.
- NÃO usar versão semântica (`\d+\.\d+\.\d+`) nem “último segmento da branch” como fallback.
- Linha quando aplicável: `Refs: IDENTIFICADOR_DA_TAREFA`.

Formato final:
type(scope)!: Assunto em imperativo
type: Assunto em imperativo            ← quando não houver scope

- <bullets no imperativo>
- <mais bullets se necessário>

[se aplicável] Refs: IDENTIFICADOR_DA_TAREFA
