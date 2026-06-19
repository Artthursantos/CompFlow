# CompFlow — Como rodar (apresentação)

Sistema de acompanhamento acadêmico (UFPel): MySQL → Spring Boot (porta 8080) → Frontend HTML/JS (porta 5500).

## Jeito rápido (recomendado)

Dê **duplo clique** em **`INICIAR-COMPFLOW.bat`**.

Ele sobe o MySQL (se preciso), o backend, o frontend e abre o navegador em
`http://localhost:5500/login.html`.

Para **parar**: feche as duas janelas de terminal (Backend e Frontend).

### Logins de teste
| Papel | E-mail | Senha |
|-------|--------|-------|
| Admin | `admin@inf.ufpel.edu.br` | `admin123` |
| Aluno | `aluno@inf.ufpel.edu.br` | `aluno123` |

---

## Jeito manual (passo a passo)

Pré-requisitos já instalados nesta máquina:
- **JDK 21** (portátil): `C:\Users\caiog\compflow-tools\jdk\jdk-21.0.11+10`
- **Maven 3.9.9** (portátil): `C:\Users\caiog\compflow-tools\maven\apache-maven-3.9.9`
- **MySQL 8.4** (serviço `MySQL84`, senha root = `root`)
- **Node.js** (para servir o frontend)

### 1. Banco de dados (só na primeira vez)
Use o comando `source` do cliente MySQL (lê o arquivo direto do disco e
**preserva os acentos UTF-8**). Não use `Get-Content | mysql` nem `< arquivo.sql`
no PowerShell — o pipe converte os acentos em `?` e corrompe os dados.
```powershell
& "C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe" --default-character-set=utf8mb4 -u root -proot -e "source C:/Users/caiog/OneDrive/Desktop/Flow/compflow-java/banco.sql"
```

### 2. Backend
```powershell
$env:JAVA_HOME = "C:\Users\caiog\compflow-tools\jdk\jdk-21.0.11+10"
cd compflow-java
# rodar o JAR já empacotado:
& "$env:JAVA_HOME\bin\java.exe" -jar "target\compflow-0.0.1-SNAPSHOT.jar"
# (ou, para recompilar: ...\maven\...\bin\mvn.cmd clean package -DskipTests)
```
API em `http://localhost:8080/api` — teste: `http://localhost:8080/api/disciplinas`.

### 3. Frontend
```powershell
node "C:\Users\caiog\compflow-tools\serve.js"
```
Abre em `http://localhost:5500/login.html`.

> O frontend precisa ser servido em **localhost:5500** (ou 3000/3001) porque
> são as origens liberadas no `CorsConfig` do backend. Abrir o HTML direto
> pelo `file://` **não funciona** (o navegador bloqueia as chamadas à API).

---

## Por que o JDK 21 (e não o 25 da máquina)?
O projeto usa **Spring Boot 3.2.5 + Lombok 1.18.30**, que não suportam o
Java 25 instalado no sistema. O JDK 21 LTS (portátil, em `compflow-tools`)
é o ambiente oficialmente suportado por essa versão do Spring Boot. O Java 25
do sistema continua intacto — o projeto só usa o 21 para buildar e rodar.

## O que mudou no setup
- `application.properties`: senha do banco preenchida (`root`).
- `compflow-v2` (mockup estático, sem backend) foi arquivado em
  `_arquivo-compflow-v2-mockup`. A versão usada é a `compflow-integrado`.
