
/* create table Autor (id int, nome varchar(255)) */
/* create table Livro (id int, anoDePublicacao int, editora varchar(255),nome varchar(255),resumo varchar(255))  */ 
/* create table Livro_Autor (livro_id int, autores_id int)  */



insert into Autor (id, nome) values (1, 'Paulo Silveira') 
insert into Autor (id, nome) values (2, 'Adriano Almeida')
insert into Autor (id, nome) values (3, 'Vinicius Baggio Fuentes')

insert into Livro (id, anoDePublicacao, editora, nome, resumo) values (1, 2012, 'Casa do Código', 'Guia do Programador', 'Vá do "nunca programei"...')
insert into Livro (id, anoDePublicacao, editora, nome, resumo) values (2, 2012, 'Casa do Código', 'Ruby on Rails', 'Crie rapidamente aplicações web')

insert into Livro_Autor (livro_id,autores_id) values (1,1)
insert into Livro_Autor (livro_id,autores_id) values (1,2)
insert into Livro_Autor (livro_id,autores_id) values (2,3)