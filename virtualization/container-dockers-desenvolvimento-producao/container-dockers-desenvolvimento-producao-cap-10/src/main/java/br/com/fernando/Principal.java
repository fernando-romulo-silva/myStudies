package br.com.fernando;

public class Principal {

	// * Alpine Linux
	//
	// Ao construir imagens Docker, não nos preocupamos com o seu tamanho. Porém, depois de um tempo, 
	// isso pode ser um problema, pois quanto maior a imagem maior será o tempo para pull e push no Docker Hub.
	//
	// Pensando nisso, o time da Glider Labs vem se empenhando no projeto Alpine Linux, um sistema operacional 
	// projetado para ser pequeno, simples e seguro. Focaremos apenas em seu tamanho para comparar com as imagens
	// base mais comuns. 
	//
	// $ sudo docker build -t mysql_lite .
	//
	// $ sudo docker run -d -p 3306:3306 -e MYSQL_PASS=123mudar --name app_mysql_lite mysql_lite
	//
	//
	// https://github.com/luislavena/dockerfiles/tree/master/mini-mysql
	
}
