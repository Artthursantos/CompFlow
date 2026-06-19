# CompFlow — Arquitetura do Sistema

## Visão Geral

```
┌─────────────────────────────────────────────────────────┐
│                      NAVEGADOR                          │
│        Frontend HTML + CSS + JavaScript puro            │
│   Aluno: login.html, dashboard.html, etc.               │
│   Admin: admin-dashboard.html, admin-disciplinas.html...│
└─────────────────────┬───────────────────────────────────┘
                      │ fetch() → HTTP/JSON (REST)
┌─────────────────────▼───────────────────────────────────┐
│               Java Spring Boot (porta 8080)             │
│    Controllers → Services → Repositories → Entities     │
└─────────────────────┬───────────────────────────────────┘
                      │ JPA / Hibernate
┌─────────────────────▼───────────────────────────────────┐
│                    MySQL (porta 3306)                    │
│                  banco: compflow_db                     │
└─────────────────────────────────────────────────────────┘
```

Não há autenticação por token (JWT) — o usuário logado fica salvo em
`localStorage` no navegador após o login, e cada página verifica sua
presença antes de carregar dados.

---

## 1. Banco de Dados (MySQL)

### Diagrama de Entidades

```
cursos
├── id            BIGINT PK AUTO_INCREMENT
├── nome          VARCHAR(150)
└── codigo        VARCHAR(20) UNIQUE

usuarios
├── id            BIGINT PK AUTO_INCREMENT
├── nome          VARCHAR(150)
├── email         VARCHAR(150) UNIQUE
├── senha_hash    VARCHAR(255)
├── role          ENUM('ALUNO','ADMIN')
├── curso_id      BIGINT FK → cursos.id (nullable)
└── criado_em     DATETIME

disciplinas
├── id            BIGINT PK AUTO_INCREMENT
├── codigo        VARCHAR(20) UNIQUE
├── nome          VARCHAR(150)
├── creditos      INT
└── tipo          ENUM('INTERNO','EXTERNO')

curriculos
├── id            BIGINT PK AUTO_INCREMENT
├── nome          VARCHAR(150)
└── curso_id      BIGINT FK → cursos.id (nullable)

curriculo_disciplinas          ← tabela associativa
├── id            BIGINT PK AUTO_INCREMENT
├── curriculo_id  BIGINT FK → curriculos.id
├── disciplina_id BIGINT FK → disciplinas.id
└── semestre      INT            ← 1º, 2º, 3º...

progresso_aluno
├── id            BIGINT PK AUTO_INCREMENT
├── usuario_id    BIGINT FK → usuarios.id
├── disciplina_id BIGINT FK → disciplinas.id
├── status        ENUM('APROVADO','CURSANDO','PENDENTE')
└── atualizado_em DATETIME

eventos
├── id            BIGINT PK AUTO_INCREMENT
├── titulo        VARCHAR(200)
├── data_inicio   DATE
├── data_fim      DATE           ← nullable (eventos de 1 dia)
└── tipo          ENUM('PRAZO','EVENTO_ACADEMICO','FERIADO')

contatos
├── id            BIGINT PK AUTO_INCREMENT
├── nome          VARCHAR(150)
├── cargo         VARCHAR(150)   ← nullable
├── categoria     ENUM('DOCENTE','APOIO')
├── email         VARCHAR(150)
└── departamento  VARCHAR(150)   ← nullable
```

### Script SQL completo

O arquivo **`banco.sql`** (incluído no ZIP do backend) cria todas as
tabelas acima e já insere dados de teste:

- 2 cursos (Ciência da Computação, Engenharia de Computação)
- 2 usuários (1 admin, 1 aluno)
- 16 disciplinas
- 1 currículo com 16 disciplinas distribuídas em 4 semestres
- 6 eventos
- 5 contatos

Execute esse script direto no MySQL antes de subir o backend.

**Credenciais de teste:**
| Papel | E-mail | Senha |
|-------|--------|-------|
| Admin | `admin@inf.ufpel.edu.br` | `admin123` |
| Aluno | `aluno@inf.ufpel.edu.br` | `aluno123` |

---

## 2. Backend — Java Spring Boot

### Estrutura de Pastas (51 arquivos)

```
compflow-java/
├── pom.xml
├── banco.sql
└── src/main/
    ├── resources/
    │   └── application.properties
    └── java/br/ufpel/compflow/
        ├── CompflowApplication.java
        │
        ├── config/
        │   └── CorsConfig.java
        │
        ├── entity/                       (8 classes)
        │   ├── Curso.java
        │   ├── Usuario.java
        │   ├── Disciplina.java
        │   ├── Curriculo.java
        │   ├── CurriculoDisciplina.java
        │   ├── ProgressoAluno.java
        │   ├── Evento.java
        │   └── Contato.java
        │
        ├── repository/                   (8 interfaces)
        │   ├── UsuarioRepository.java
        │   ├── CursoRepository.java
        │   ├── DisciplinaRepository.java
        │   ├── CurriculoRepository.java
        │   ├── CurriculoDisciplinaRepository.java
        │   ├── ProgressoAlunoRepository.java
        │   ├── EventoRepository.java
        │   └── ContatoRepository.java
        │
        ├── dto/
        │   ├── request/                  (8 classes)
        │   │   ├── LoginRequest.java
        │   │   ├── CriarUsuarioRequest.java
        │   │   ├── AtualizarNomeRequest.java
        │   │   ├── DisciplinaRequest.java
        │   │   ├── CurriculoRequest.java
        │   │   ├── EventoRequest.java
        │   │   ├── ContatoRequest.java
        │   │   └── ProgressoRequest.java
        │   └── response/                 (7 classes)
        │       ├── UsuarioResponse.java
        │       ├── CursoResponse.java
        │       ├── DisciplinaResponse.java
        │       ├── CurriculoResponse.java
        │       ├── EventoResponse.java
        │       ├── ContatoResponse.java
        │       └── ProgressoResponse.java
        │
        ├── service/                      (7 classes)
        │   ├── UsuarioService.java
        │   ├── CursoService.java
        │   ├── DisciplinaService.java
        │   ├── CurriculoService.java
        │   ├── ProgressoService.java
        │   ├── EventoService.java
        │   └── ContatoService.java
        │
        └── controller/                   (8 classes)
            ├── AuthController.java
            ├── UsuarioController.java
            ├── CursoController.java
            ├── DisciplinaController.java
            ├── CurriculoController.java
            ├── ProgressoController.java
            ├── EventoController.java
            └── ContatoController.java
```

### pom.xml — dependências

```xml
<dependencies>
  <!-- Web -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>

  <!-- JPA + Hibernate -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>

  <!-- Validação (@Valid, @NotBlank, etc.) -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
  </dependency>

  <!-- MySQL Driver -->
  <dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
  </dependency>

  <!-- Lombok (getters/setters automáticos) -->
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>

  <!-- Testes -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
</dependencies>
```

### application.properties

```properties
# Banco de dados
spring.datasource.url=jdbc:mysql://localhost:3306/compflow_db?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_AQUI

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Servidor
server.port=8080

# Datas em formato ISO (yyyy-MM-dd) no JSON
spring.jackson.serialization.write-dates-as-timestamps=false
```

> `ddl-auto=validate` significa que o Hibernate **não cria tabelas
> sozinho** — ele só confere se as entidades batem com o banco. As
> tabelas vêm do `banco.sql`.

### CorsConfig

Libera o frontend rodando em arquivo local ou Live Server:

```java
registry.addMapping("/api/**")
    .allowedOrigins(
        "http://localhost:3000",
        "http://localhost:3001",
        "http://127.0.0.1:5500",
        "http://localhost:5500"
    )
    .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
    .allowedHeaders("*")
    .allowCredentials(true);
```

---

## 3. Endpoints REST

### Autenticação

| Método | Endpoint              | Descrição             | Body                              |
| ------ | --------------------- | --------------------- | --------------------------------- |
| POST   | `/api/auth/login`     | Login (email + senha) | `{ email, senha }`                |
| POST   | `/api/auth/registrar` | Criar conta de aluno  | `{ nome, email, senha, cursoId }` |

### Usuários

| Método | Endpoint                  | Descrição                 |
| ------ | ------------------------- | ------------------------- |
| GET    | `/api/usuarios`           | Listar todos (admin)      |
| GET    | `/api/usuarios/{id}`      | Ver perfil                |
| PATCH  | `/api/usuarios/{id}/nome` | Atualizar nome `{ nome }` |

### Cursos

| Método | Endpoint      | Descrição                                   |
| ------ | ------------- | ------------------------------------------- |
| GET    | `/api/cursos` | Listar cursos (usado no select de cadastro) |

### Disciplinas

| Método | Endpoint                | Descrição       |
| ------ | ----------------------- | --------------- |
| GET    | `/api/disciplinas`      | Listar todas    |
| GET    | `/api/disciplinas/{id}` | Buscar por ID   |
| POST   | `/api/disciplinas`      | Criar (admin)   |
| PUT    | `/api/disciplinas/{id}` | Editar (admin)  |
| DELETE | `/api/disciplinas/{id}` | Excluir (admin) |

### Currículos

| Método | Endpoint               | Descrição                            |
| ------ | ---------------------- | ------------------------------------ |
| GET    | `/api/curriculos`      | Listar todos                         |
| GET    | `/api/curriculos/{id}` | Buscar com disciplinas por semestre  |
| POST   | `/api/curriculos`      | Criar (admin)                        |
| PUT    | `/api/curriculos/{id}` | Editar — substitui semestres (admin) |
| DELETE | `/api/curriculos/{id}` | Excluir (admin)                      |

Body de criação/edição:

```json
{
  "nome": "Ciência da Computação",
  "cursoId": 1,
  "semestres": [
    { "semestre": 1, "disciplinaIds": [1, 2, 3] },
    { "semestre": 2, "disciplinaIds": [4, 5] }
  ]
}
```

### Progresso do Aluno

| Método | Endpoint                              | Descrição                                 |
| ------ | ------------------------------------- | ----------------------------------------- |
| GET    | `/api/progresso/{usuarioId}`          | Progresso completo por semestre           |
| PUT    | `/api/progresso/{usuarioId}/{discId}` | Atualizar status `{ status: "APROVADO" }` |

A resposta inclui: `aprovadas`, `totalDisciplinas`, `creditorConcluidos`,
`totalCreditos`, `percentual` e a lista `semestres[]` com cada disciplina
e seu `status` (APROVADO / CURSANDO / PENDENTE).

### Eventos

| Método | Endpoint            | Descrição       |
| ------ | ------------------- | --------------- |
| GET    | `/api/eventos`      | Listar todos    |
| POST   | `/api/eventos`      | Criar (admin)   |
| PUT    | `/api/eventos/{id}` | Editar (admin)  |
| DELETE | `/api/eventos/{id}` | Excluir (admin) |

### Contatos

| Método | Endpoint             | Descrição       |
| ------ | -------------------- | --------------- |
| GET    | `/api/contatos`      | Listar todos    |
| POST   | `/api/contatos`      | Criar (admin)   |
| PUT    | `/api/contatos/{id}` | Editar (admin)  |
| DELETE | `/api/contatos/{id}` | Excluir (admin) |

---

## 4. Frontend — HTML + CSS + JavaScript

### Estrutura de Arquivos

```
compflow-integrado/
├── sidebar.css              ← compartilhado pelas páginas do aluno
├── admin.css                ← compartilhado pelas páginas do admin
│
├── login.html               ← POST /api/auth/login
├── criarconta.html          ← GET /api/cursos · POST /api/auth/registrar
│
├── dashboard.html           ← GET /api/progresso/{id} · GET /api/eventos
├── meuprogresso.html        ← GET/PUT /api/progresso/{id}
├── fluxograma.html          ← GET/PUT /api/progresso/{id}
├── calendario.html          ← GET /api/eventos
├── contatos.html            ← GET /api/contatos
├── perfil.html              ← GET /api/progresso/{id} · PATCH /api/usuarios/{id}/nome
│
├── admin-dashboard.html     ← GET disciplinas/eventos/contatos/usuarios
├── admin-disciplinas.html   ← CRUD /api/disciplinas
├── admin-eventos.html       ← CRUD /api/eventos
├── admin-contatos.html      ← CRUD /api/contatos
├── admin-curriculos.html    ← CRUD /api/curriculos · GET /api/disciplinas
└── admin-perfil.html        ← GET stats · PATCH /api/usuarios/{id}/nome
```

### Padrão de autenticação (todas as páginas)

```javascript
const BASE = "http://localhost:8080/api";
const usuario = JSON.parse(localStorage.getItem("usuario"));
if (!usuario) window.location.href = "login.html";

// Páginas admin também checam:
if (usuario.role !== "ADMIN") window.location.href = "login.html";

function sair() {
  localStorage.removeItem("usuario");
  window.location.href = "login.html";
}
```

O objeto salvo no `localStorage` após o login tem o formato:

```json
{
  "id": 1,
  "nome": "Admin",
  "email": "admin@inf.ufpel.edu.br",
  "role": "ADMIN",
  "nomeCurso": null
}
```

### Tema visual

| Perfil | Cor primária      | Cor hover |
| ------ | ----------------- | --------- |
| Aluno  | `#173d7a` (azul)  | `#294d88` |
| Admin  | `#7f1d1d` (vinho) | `#991b1b` |

---

## 5. Fluxo de Dados — Exemplo Completo

```
[Aluno abre meuprogresso.html]
       │
       ▼
fetch(`${BASE}/progresso/${usuario.id}`)
       │
       ▼ GET /api/progresso/{usuarioId}
ProgressoController
  → ProgressoService.buscarProgresso(usuarioId)
      1. Busca o curso do usuário (usuarios.curso_id)
      2. Busca o currículo desse curso (curriculos.curso_id)
      3. Busca curriculo_disciplinas ordenado por semestre
      4. Busca progresso_aluno do usuário (status salvo)
      5. Combina: para cada disciplina do currículo,
         aplica o status salvo (ou PENDENTE se não existir)
      6. Calcula totais: aprovadas, créditos, percentual
       │
       ▼ JSON
{
  "aprovadas": 5, "totalDisciplinas": 16,
  "creditorConcluidos": 18, "totalCreditos": 64,
  "percentual": 28,
  "semestres": [
    { "semestre": 1, "disciplinas": [
        { "id":1, "codigo":"1110179", "nome":"Sistemas Discretos",
          "creditos":4, "status":"APROVADO" }
    ]}
  ]
}
       │
       ▼
meuprogresso.html renderiza cards por semestre.
Clique numa disciplina → PUT /api/progresso/{id}/{discId}
                          { "status": "APROVADO" }
       │
       ▼
ProgressoService.atualizarStatus()
  → cria ou atualiza linha em progresso_aluno
```

---

## 6. Pontos de atenção / próximos passos

```
[ ] Senhas em texto puro — trocar para BCrypt
      pom.xml: adicionar spring-boot-starter-security
      UsuarioService: usar BCryptPasswordEncoder no registrar() e login()

[ ] Sem JWT — sessão é só localStorage no navegador
      Funciona para desenvolvimento; para produção considerar
      Spring Security + token

[ ] CurriculoService.buscarProgresso() pega o "primeiro currículo"
      do curso. Se um curso tiver múltiplos currículos (grades antigas
      vs novas), seria necessário um campo "ativo" ou vínculo direto
      usuario → curriculo

[ ] Sem paginação — /api/disciplinas, /api/contatos etc. retornam
      a lista inteira. OK para o volume atual (dezenas de registros)
```

---

## 7. Como rodar o projeto

### Banco de dados

```bash
mysql -u root -p < banco.sql
```

### Backend

```bash
cd compflow-java
mvn spring-boot:run
# API disponível em http://localhost:8080/api
```

Teste rápido:

```
GET http://localhost:8080/api/disciplinas
→ deve retornar as 16 disciplinas inseridas pelo banco.sql
```

### Frontend

Não precisa de build — são arquivos estáticos. Basta abrir
`login.html` no navegador (ou usar a extensão Live Server do VS Code).

Login de teste:

- Admin: `admin@inf.ufpel.edu.br` / `admin123` → `admin-dashboard.html`
- Aluno: `aluno@inf.ufpel.edu.br` / `aluno123` → `dashboard.html`
