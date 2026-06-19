-- ============================================================
-- CompFlow - Script de criação do banco de dados
-- Execute no MySQL antes de rodar o Spring Boot
-- ============================================================

CREATE DATABASE IF NOT EXISTS compflow_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE compflow_db;

-- Cursos
CREATE TABLE IF NOT EXISTS cursos (
  id     BIGINT       NOT NULL AUTO_INCREMENT,
  nome   VARCHAR(150) NOT NULL,
  codigo VARCHAR(20)  NOT NULL UNIQUE,
  PRIMARY KEY (id)
);

-- Usuarios
CREATE TABLE IF NOT EXISTS usuarios (
  id          BIGINT       NOT NULL AUTO_INCREMENT,
  nome        VARCHAR(150) NOT NULL,
  email       VARCHAR(150) NOT NULL UNIQUE,
  senha_hash  VARCHAR(255) NOT NULL,
  role        ENUM('ALUNO','ADMIN') NOT NULL DEFAULT 'ALUNO',
  curso_id    BIGINT,
  criado_em   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

-- Disciplinas
CREATE TABLE IF NOT EXISTS disciplinas (
  id       BIGINT       NOT NULL AUTO_INCREMENT,
  codigo   VARCHAR(20)  NOT NULL UNIQUE,
  nome     VARCHAR(150) NOT NULL,
  creditos INT          NOT NULL,
  tipo     ENUM('INTERNO','EXTERNO') NOT NULL DEFAULT 'INTERNO',
  PRIMARY KEY (id)
);

-- Curriculos
CREATE TABLE IF NOT EXISTS curriculos (
  id       BIGINT       NOT NULL AUTO_INCREMENT,
  nome     VARCHAR(150) NOT NULL,
  curso_id BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

-- Curriculo x Disciplinas
CREATE TABLE IF NOT EXISTS curriculo_disciplinas (
  id            BIGINT NOT NULL AUTO_INCREMENT,
  curriculo_id  BIGINT NOT NULL,
  disciplina_id BIGINT NOT NULL,
  semestre      INT    NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (curriculo_id)  REFERENCES curriculos(id)  ON DELETE CASCADE,
  FOREIGN KEY (disciplina_id) REFERENCES disciplinas(id) ON DELETE CASCADE
);

-- Progresso do aluno
CREATE TABLE IF NOT EXISTS progresso_aluno (
  id            BIGINT   NOT NULL AUTO_INCREMENT,
  usuario_id    BIGINT   NOT NULL,
  disciplina_id BIGINT   NOT NULL,
  status        ENUM('APROVADO','CURSANDO','PENDENTE') NOT NULL DEFAULT 'PENDENTE',
  atualizado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uq_prog (usuario_id, disciplina_id),
  FOREIGN KEY (usuario_id)    REFERENCES usuarios(id)    ON DELETE CASCADE,
  FOREIGN KEY (disciplina_id) REFERENCES disciplinas(id) ON DELETE CASCADE
);

-- Eventos
CREATE TABLE IF NOT EXISTS eventos (
  id          BIGINT       NOT NULL AUTO_INCREMENT,
  titulo      VARCHAR(200) NOT NULL,
  data_inicio DATE         NOT NULL,
  data_fim    DATE,
  tipo        ENUM('PRAZO','EVENTO_ACADEMICO','FERIADO') NOT NULL,
  PRIMARY KEY (id)
);

-- Contatos
CREATE TABLE IF NOT EXISTS contatos (
  id           BIGINT       NOT NULL AUTO_INCREMENT,
  nome         VARCHAR(150) NOT NULL,
  cargo        VARCHAR(150),
  categoria    ENUM('DOCENTE','APOIO') NOT NULL,
  email        VARCHAR(150) NOT NULL,
  departamento VARCHAR(150),
  PRIMARY KEY (id)
);

-- ============================================================
-- Dados iniciais para teste
-- ============================================================

-- Cursos
INSERT INTO cursos (nome, codigo) VALUES
  ('Ciência da Computação',   'CC'),
  ('Engenharia de Computação','EC');

-- Usuários (admin + aluno de teste)
INSERT INTO usuarios (nome, email, senha_hash, role, curso_id) VALUES
  ('Admin',        'admin@inf.ufpel.edu.br',  'admin123', 'ADMIN', NULL),
  ('Usuário Teste','aluno@inf.ufpel.edu.br',  'aluno123', 'ALUNO', 1);

-- Disciplinas
INSERT INTO disciplinas (codigo, nome, creditos, tipo) VALUES
  ('1110179','Sistemas Discretos',                    4,'INTERNO'),
  ('1110180','Algoritmos e Programação',              4,'INTERNO'),
  ('1110181','Introdução à Computação',               2,'INTERNO'),
  ('0100301','Cálculo 1',                             4,'EXTERNO'),
  ('1110182','Algoritmos e Estrutura de Dados I',     4,'INTERNO'),
  ('0100302','Álgebra Linear',                        4,'EXTERNO'),
  ('1110183','Algoritmos e Estrutura de Dados II',    4,'INTERNO'),
  ('1110184','Programação Orientada a Objetos',       4,'INTERNO'),
  ('1110185','Banco de Dados I',                      4,'INTERNO'),
  ('1110186','Redes de Computadores',                 4,'INTERNO'),
  ('1110187','Sistemas Operacionais',                 4,'INTERNO'),
  ('1110188','Engenharia de Software I',              4,'INTERNO'),
  ('1110189','Inteligência Artificial',               4,'INTERNO'),
  ('1110190','Computação Gráfica',                    4,'INTERNO'),
  ('1110191','TCC I',                                 4,'INTERNO'),
  ('1110192','TCC II',                                8,'INTERNO');

-- Currículo Ciência da Computação
INSERT INTO curriculos (nome, curso_id) VALUES ('Ciência da Computação', 1);

-- Disciplinas por semestre (curriculo_id = 1)
INSERT INTO curriculo_disciplinas (curriculo_id, disciplina_id, semestre) VALUES
  (1, 1, 1),(1, 2, 1),(1, 3, 1),(1, 4, 1),(1, 6, 1),
  (1, 5, 2),(1, 7, 2),(1, 8, 2),(1, 9, 2),(1,10, 2),
  (1,11, 3),(1,12, 3),(1,13, 3),(1,14, 3),(1,15, 3),
  (1,16, 4);

-- Eventos
INSERT INTO eventos (titulo, data_inicio, tipo) VALUES
  ('Prazo Final Trancamento',    '2026-05-30', 'PRAZO'),
  ('Corpus Christi',             '2026-06-04', 'FERIADO'),
  ('Período de Rematrícula',     '2026-06-15', 'EVENTO_ACADEMICO'),
  ('Início das Avaliações',      '2026-06-20', 'EVENTO_ACADEMICO'),
  ('Independência do Brasil',    '2026-09-07', 'FERIADO'),
  ('Início do Semestre 2026/2',  '2026-08-12', 'EVENTO_ACADEMICO');

-- Contatos
INSERT INTO contatos (nome, cargo, categoria, email) VALUES
  ('Prof. Dr. Ana Marilza Pernas','Docente',                            'DOCENTE','marilza@inf.ufpel.edu.br'),
  ('Prof. Dr. Andre Rauber Du Bois','Docente',                          'DOCENTE','dubois@inf.ufpel.edu.br'),
  ('Secretaria da Ciência da Computação','Secretaria Acadêmica',        'APOIO',  'secretaria-ccomp@inf.ufpel.edu.br'),
  ('Coordenação Ciência da Computação','Coordenação',                   'APOIO',  'coord_ccomp@inf.ufpel.edu.br'),
  ('Ouvidoria UFPel','Apoio',                                           'APOIO',  'ouvidoria@ufpel.edu.br');
