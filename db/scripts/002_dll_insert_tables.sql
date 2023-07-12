insert into types(name) values('Две машины');
insert into types(name) values('Машина и человек');
insert into types(name) values('Машина и велосипед');

insert into rules(name) values('Статья. 1');
insert into rules(name) values('Статья. 2');
insert into rules(name) values('Статья. 3');

insert into accidents(name, description, address, type_id) values('ДТП', 'На перекрестке', 'Москва', 1);
insert into accidents(name, description, address, type_id) values('ДТП', 'На пешеходном переходе', 'Москва', 2);
insert into accidents(name, description, address, type_id) values('ДТП', 'На тратуаре', 'Москва', 3);

insert into rules_accidents(rule_id, accident_id) values(1, 3);
insert into rules_accidents(rule_id, accident_id) values(2, 3);
insert into rules_accidents(rule_id, accident_id) values(2, 2);
insert into rules_accidents(rule_id, accident_id) values(3, 1);