insert into tb_usuario (id,c_nome_usuario , c_senha) values (1,"Yuri" , "$2y$12$wVaJkz5qYmM43dQppmqWiuN2lH0zCNzvbKMWtjc9mWPZZ37iTAinK");
insert into tb_usuario (id,c_nome_usuario , c_senha) values (2,"Marcos" , "$2y$12$wVaJkz5qYmM43dQppmqWiuN2lH0zCNzvbKMWtjc9mWPZZ37iTAinK");
insert into tb_usuario (id,c_nome_usuario , c_senha) values (3,"Fernando" , "$2y$12$wVaJkz5qYmM43dQppmqWiuN2lH0zCNzvbKMWtjc9mWPZZ37iTAinK");
insert into tb_usuario (id,c_nome_usuario , c_senha) values (4,"Fabio" , "$2y$12$wVaJkz5qYmM43dQppmqWiuN2lH0zCNzvbKMWtjc9mWPZZ37iTAinK");
insert into tb_usuario (id,c_nome_usuario , c_senha) values (5,"Joao" , "$2y$12$wVaJkz5qYmM43dQppmqWiuN2lH0zCNzvbKMWtjc9mWPZZ37iTAinK");


insert into tb_operador (id, c_tipo , usuario_id) values (1, "ADMIN"  , 1);
insert into tb_operador (id, c_tipo , usuario_id) values (2, "OPERADORMOBILE"  , 2);
insert into tb_operador (id, c_tipo , usuario_id) values (3, "OPERADORDESKTOP"  , 3);
insert into tb_operador (id, c_tipo , usuario_id) values (4, "OPERADORWEB"  , 4);




insert into tb_app_captura (id, c_formato , c_nome ,operador_id ) values (1, "txt" ,"mobile" , 1);
insert into tb_app_captura (id, c_formato , c_nome ,operador_id ) values (2, "pdf" ,"mobile" , 1);



insert into tb_documento (id, c_formato , c_nome ,  c_app_captura, c_data, c_valor_arquivo ,operador_id ) values (1, "txt" ,"DocTeste1" ,"Mobile","29/12/2020", "jsnQIDYBEYBUAYBA", 2);
insert into tb_documento (id, c_formato , c_nome ,  c_app_captura, c_data, c_valor_arquivo ,operador_id ) values (2, "DOC" ,"DocTeste2" ,"Desktop","29/12/2020", "jsnQIDYBEYBUAYBA", 3);
