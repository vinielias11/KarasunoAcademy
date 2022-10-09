----------------------------------------------
----------------------------------------------
-- CRIACAO DE TABELAS
----------------------------------------------
----------------------------------------------

/*********************************************
CIDADES
*********************************************/
CREATE TABLE cidades (
  id serial NOT NULL,
  nome text NOT NULL,
  estado char(2) NOT NULL,
  pais text NOT NULL,
  CONSTRAINT cidades_pk PRIMARY KEY(nome, estado, pais)
);

/*********************************************
USUARIOS
*********************************************/
CREATE TABLE usuarios (
  id serial NOT NULL CONSTRAINT usuarios_pk PRIMARY KEY,
  nome text NOT NULL UNIQUE, senha text NOT NULL,
  perfil text NOT NULL
);

/*********************************************
ALUNOS
*********************************************/
CREATE TABLE alunos (
  id serial NOT NULL CONSTRAINT aluno_pk PRIMARY KEY,
  codigo_aluno integer NOT NULL DEFAULT (floor(((random() * (100000)::double precision) + (5)::double precision)))::integer,
  nome text NOT NULL,
  data_nascimento date,
  sexo char(1) CONSTRAINT alunos_sexo check (
    sexo in ('M', 'F')
  ),
  telefone text,
  celular text,
  email text,
  observacao text,
  endereco text,
  numero text,
  complemento text,
  bairro text,
  cidade text,
  estado char(2),
  pais text,
  CONSTRAINT alunos_enderecos_f2 FOREIGN KEY(cidade, estado, pais) REFERENCES cidades(nome, estado, pais) DEFERRABLE,
  cep text
);
--
--
CREATE INDEX alunos_1 ON alunos(nome);
--
--
/*********************************************
MODALIDADES
*********************************************/
CREATE TABLE modalidades (
  id serial NOT NULL CONSTRAINT modalidades_pk PRIMARY KEY,
  nome text NOT NULL
);

/*********************************************
GRADUACOES
*********************************************/
CREATE TABLE graduacoes (
  id serial NOT NULL,
  id_modalidade integer not null,  CONSTRAINT graduacoes_f1 FOREIGN KEY(id_modalidade) REFERENCES modalidades(id) DEFERRABLE,
  nome text NOT NULL,
  CONSTRAINT graduacoes_pk PRIMARY KEY(id, id_modalidade)
);

/*********************************************
PLANOS
*********************************************/
CREATE TABLE planos (
  id serial NOT NULL,
  id_modalidade integer NOT NULL,
  CONSTRAINT planos_f1 FOREIGN KEY(id_modalidade) REFERENCES modalidades(id) DEFERRABLE,
  nome text NOT NULL,
  CONSTRAINT planos_pk PRIMARY KEY(id_modalidade, id),
  valor_mensal numeric(9, 2) NOT NULL
);

/*********************************************
MATRICULAS
*********************************************/
CREATE TABLE matriculas (
  codigo_matricula serial CONSTRAINT matriculas_pk PRIMARY KEY,
  id_aluno integer NOT NULL,
  CONSTRAINT matriculas_f1 FOREIGN KEY(id_aluno) REFERENCES alunos(id) DEFERRABLE,
  data_matricula date NOT NULL,
  dia_vencimento integer NOT NULL,
  data_encerramento date
);

/*********************************************
MATRICULAS_MODALIDADES
*********************************************/
CREATE TABLE matriculas_modalidades (
  codigo_matricula integer NOT NULL,
  CONSTRAINT matriculas_modalidades_f1 FOREIGN KEY(codigo_matricula) REFERENCES matriculas(codigo_matricula) DEFERRABLE,
  id_modalidade integer NOT NULL,
  CONSTRAINT matriculas_modalidades_f2 FOREIGN KEY(id_modalidade) REFERENCES modalidades(id) DEFERRABLE,
  CONSTRAINT matriculas_modalidades_pk PRIMARY KEY(codigo_matricula, id_modalidade),
  id_graduacao integer,
  CONSTRAINT matriculas_modalidades_f3 FOREIGN KEY(id_modalidade, id_graduacao) REFERENCES graduacoes(id_modalidade, id) DEFERRABLE,
  id_plano integer NOT NULL,
  CONSTRAINT matriculas_modalidades_f4 FOREIGN KEY(id_modalidade, id_plano) REFERENCES planos(id_modalidade, id) DEFERRABLE,
  data_inicio date DEFAULT current_date,
  data_fim date
);

/*********************************************
FATURAS_MATRICULAS
*********************************************/
CREATE TABLE faturas_matriculas (
  codigo_matricula integer NOT NULL,
  CONSTRAINT faturas_matriculas_f1 FOREIGN KEY(codigo_matricula) REFERENCES matriculas(codigo_matricula) DEFERRABLE,
  data_vencimento date NOT NULL,
  CONSTRAINT faturas_matriculas_pk PRIMARY KEY(
    codigo_matricula, data_vencimento
  ),
  valor numeric(9, 2) NOT NULL,
  data_pagamento timestamp DEFAULT localtimestamp,
  data_cancelamento date
);

/*********************************************
ASSIDUIDADE
*********************************************/
CREATE TABLE assiduidade (
  codigo_matricula integer NOT NULL,
  CONSTRAINT assiduidade_f1 FOREIGN KEY(codigo_matricula) REFERENCES matriculas(codigo_matricula) DEFERRABLE,
  data_entrada timestamp NOT NULL DEFAULT localtimestamp,
  CONSTRAINT assiduidade_pk PRIMARY KEY(codigo_matricula, data_entrada)
);
