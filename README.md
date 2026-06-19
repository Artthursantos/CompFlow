# CompFlow — Planejamento Acadêmico UFPel

Sistema web de acompanhamento acadêmico para alunos da UFPel: o aluno
visualiza seu currículo por semestre, marca disciplinas como cursando/aprovadas
e acompanha seu progresso; o administrador gerencia disciplinas, currículos,
eventos e contatos.

## 🧩 Arquitetura

```
Frontend (HTML/CSS/JS)  →  Backend (Java + Spring Boot)  →  Banco (MySQL)
     porta 5500                   porta 8080                  porta 3306
```

- **Frontend:** HTML, CSS e JavaScript puro (sem framework). Pasta `compflow-integrado/`.
- **Backend:** Java 21 + Spring Boot 3.2.5 (Spring Web, Spring Data JPA, Validation). Pasta `compflow-java/`.
- **Banco:** MySQL 8. Script de criação e dados de teste em `compflow-java/banco.sql`.

Detalhes técnicos (tabelas, endpoints REST e fluxos) em [ARQUITETURA.md](ARQUITETURA.md).

## ▶️ Como rodar localmente

**Pré-requisitos:** JDK 21, MySQL 8 e Node.js.

```bash
# 1. Banco — carregue o script (cria o banco compflow_db com dados de teste)
mysql -u root -p < compflow-java/banco.sql

# 2. Backend — na pasta compflow-java (usa o Maven Wrapper, não precisa instalar Maven)
cd compflow-java
./mvnw spring-boot:run        # Windows: .\mvnw.cmd spring-boot:run
# API em http://localhost:8080/api

# 3. Frontend — sirva a pasta na porta 5500
npx serve compflow-integrado -l 5500
# Acesse http://localhost:5500/login.html
```

> No Windows também há o atalho `INICIAR.bat`, que sobe tudo de uma vez.

### Logins de teste
| Papel | E-mail | Senha |
|-------|--------|-------|
| Admin | `admin@inf.ufpel.edu.br` | `admin123` |
| Aluno | `aluno@inf.ufpel.edu.br` | `aluno123` |

## 🛠️ Tecnologias
Java 21 · Spring Boot 3.2.5 · Spring Data JPA / Hibernate · MySQL 8 ·
HTML5 · CSS3 · JavaScript · Maven

## 📂 Estrutura
```
compflow-java/        backend Spring Boot
compflow-integrado/   frontend (páginas do aluno e do admin)
ARQUITETURA.md        documentação técnica detalhada
COMO-RODAR.md         guia de execução
```

---
Trabalho acadêmico — UFPel.
