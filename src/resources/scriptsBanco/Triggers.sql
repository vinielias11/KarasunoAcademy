create trigger gerar_faturas
after insert
on public.matriculas_modalidades
for each row
execute function public.gerar_faturas();

create or replace function gerar_faturas()
returns trigger AS $gerar_faturas$
declare
	dia_vencimento int;
	proximo_mes timestamp;
	valor numeric;
	diferenca_meses int;
	aux_ano int;
	aux_mes int;
	aux_dataFim date;
begin
	aux_dataFim = (new.data_inicio + interval '1 year');

 	update public.matriculas_modalidades
 	set data_fim = aux_dataFim
	where codigo_matricula = new.codigo_matricula and id_modalidade = new.id_modalidade;

	proximo_mes = (select mm.data_inicio from public.matriculas_modalidades mm where mm.codigo_matricula = new.codigo_matricula limit 1) + interval '1 month';
	valor = (select p.valor_mensal from public.planos p where p.id = new.id_plano limit 1);
	diferenca_meses = (select ((extract('years' from aux_dataFim)::int -  extract('years' from new.data_inicio)::int) * 12) - extract('month' from  new.data_inicio)::int + extract('month' from aux_dataFim)::int);
	for contador in 0 .. (diferenca_meses - 1) by 1
	loop
		dia_vencimento = (select m.dia_vencimento from public.matriculas m where m.codigo_matricula = new.codigo_matricula limit 1);
		aux_ano = (extract (year from proximo_mes))::int;
		aux_mes = (extract (month from proximo_mes) + contador)::int;
		if (aux_mes::int >= 13) then
			aux_mes = aux_mes - 12;
			aux_ano = aux_ano + 1;
		end if;
		insert into public.faturas_matriculas (codigo_matricula, data_vencimento, valor, data_pagamento, data_cancelamento)
		values (
			new.codigo_matricula,
			make_date(aux_ano, aux_mes, dia_vencimento),
			valor,
			null,
			null
		);
	end loop;
return new;
end;
$gerar_faturas$ LANGUAGE plpgsql;
