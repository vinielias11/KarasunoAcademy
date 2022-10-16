ALTER TABLE public.matriculas DROP CONSTRAINT matriculas_f1;
alter table  public.matriculas ADD CONSTRAINT matriculas_f1 FOREIGN KEY (id_aluno) REFERENCES alunos(id) on delete cascade deferrable;
ALTER TABLE public.matriculas_modalidades DROP CONSTRAINT matriculas_modalidades_f1;
ALTER TABLE public.matriculas_modalidades ADD CONSTRAINT matriculas_modalidades_f1 FOREIGN KEY (codigo_matricula) REFERENCES matriculas(codigo_matricula) on delete cascade DEFERRABLE;