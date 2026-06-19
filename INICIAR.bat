@echo off
chcp 65001 >nul
title CompFlow - Inicializador
echo ============================================================
echo                    INICIANDO COMPFLOW
echo ============================================================
echo.

REM --- Caminhos relativos a esta pasta (%~dp0 = pasta deste .bat) ---
set "ROOT=%~dp0"
set "JAR=%ROOT%compflow-java\target\compflow-0.0.1-SNAPSHOT.jar"
set "FRONT=%ROOT%compflow-integrado"

REM --- Descobre o JDK 21 instalado (qualquer versao 21.x) ---
set "JAVA_HOME="
for /d %%D in ("C:\Program Files\Eclipse Adoptium\jdk-21*") do set "JAVA_HOME=%%D"
if not defined JAVA_HOME (
  echo ERRO: JDK 21 nao encontrado. Rode primeiro o setup-completo.ps1
  pause
  exit /b 1
)

if not exist "%JAR%" (
  echo ERRO: backend nao compilado. Rode primeiro o setup-completo.ps1
  pause
  exit /b 1
)

REM --- 1) Garante o MySQL rodando (servico MySQL84) ---
echo [1/3] Verificando MySQL...
sc query MySQL84 | find "RUNNING" >nul
if errorlevel 1 (
  echo     MySQL parado. Tentando iniciar ^(pode pedir admin^)...
  net start MySQL84
) else (
  echo     MySQL OK.
)

REM --- 2) Backend (Spring Boot) em janela propria ---
echo [2/3] Subindo backend na porta 8080...
start "CompFlow Backend (porta 8080)" "%JAVA_HOME%\bin\java.exe" -jar "%JAR%"

REM --- 3) Frontend (servidor estatico via npx serve) em outra janela ---
echo [3/3] Subindo frontend na porta 5500...
start "CompFlow Frontend (porta 5500)" cmd /c "npx -y serve "%FRONT%" -l 5500"

echo.
echo Aguardando o backend iniciar...
timeout /t 12 /nobreak >nul

echo Abrindo o navegador...
start "" http://localhost:5500/login.html

echo.
echo ============================================================
echo  CompFlow no ar!
echo    Frontend: http://localhost:5500/login.html
echo    Backend : http://localhost:8080/api
echo.
echo  Login ADMIN: admin@inf.ufpel.edu.br / admin123
echo  Login ALUNO: aluno@inf.ufpel.edu.br / aluno123
echo.
echo  Para PARAR: feche as duas janelas (Backend e Frontend).
echo ============================================================
echo.
pause
