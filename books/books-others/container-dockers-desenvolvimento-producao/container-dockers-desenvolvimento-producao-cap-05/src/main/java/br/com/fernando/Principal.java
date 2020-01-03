package br.com.fernando;

public class Principal {

	// *Comunicação entre containers
	//
	// Na sua configuração padrão, o Docker utiliza apenas uma interface ponte para concentrar todas as 
	// comunicações. Além disso, ele providencia as regras de firewall no iptables para prover as rotas 
	// de tráfego. Isso pode ser conferido ao inicializarmos dois containers:
	//
	// $ sudo docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123mudar --name app_mysql mysql
	//
	// $ sudo docker run -d -p 8080:8080 --name app_nginx nginx
	//
	// $ sudo iptables -L -n
	//
	// O retorno da verificação no iptables exibe o envio de pacotes do host local para os IPs de destino, 
	// em suas respectivas portas. Podemos, assim, realizar alguns testes de um container para outro, 
	// utilizando a opção exec .
	// 
	// $ sudo docker exec -it app_nginx ping 172.17.0.2
	//
	// Ao usarmos a opção exec , foi solicitado para que o container nginx realizasse um ping no IP do container
	// mysql, apenas para confirmar a comunicação estabelecida entre eles.
	//
	// *Comunicação de containers no mesmo host
	//
	// Vamos instalar o mysql cliente no container app_nginx
	// 
	// $ sudo docker exec -it app_nginx bash
	//
	// root@1500621d9024:/# apt-get install -y mysql-client-5.5
	//
	// Conecte ao servidor de MySql dentro do container app_nginx:
	//
	// root@1500621d9024:/# mysql -h 172.17.0.1 -u root -p
	//
	// Lembre-se apenas de verificar os IPs atribuídos aos containers.
	//
	// *Linkando containers
	//
	// Uma outra forma de estabelecer comunicação entre containers no mesmo host é utilizando a opção link . Essa opção 
	// providencia o mapeamento de rede entre os containers que estão sendo linkados.
	// 
	// Refazendo o último exemplo, teríamos o mesmo resultado desta forma:
	//
	// $ sudo docker run -d -p 8080:8080 --name app_nginx --link app_mysql:db nginx
	//
	// Note que na opção link é informado o nome do container que queremos linkar e um apelido para ele – neste caso, 
	// app_mysql é o nome do container, e db o seu apelido.
	// Verificaremos se o link está funcionando entrando no container app e testando a comunicação com o container mysql, 
	// através do apelido que criamos:
	//
	// $ sudo docker exec -it app_nginx bash
	//
	// root@fb5c056a8fc7:/# ping db
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
}
