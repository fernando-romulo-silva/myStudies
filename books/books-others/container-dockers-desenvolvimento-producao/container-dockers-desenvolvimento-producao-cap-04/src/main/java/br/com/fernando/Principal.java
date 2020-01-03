package br.com.fernando;

public class Principal {

    // *Gerenciando os Dados
    //
    // Antes de continuar, precisamos entender três coisas:
    //
    // • Os volumes podem ser compartilhados ou reutilizados entre containers;
    // • Toda alteração feita em um volume é de forma direta;
    // • Volumes alterados não são incluídos quando atualizamos uma imagem.
    //
    // Ainda utilizando a imagem criada para o Nginx, podemos testar o uso de volumes com a opção -v.
    // Com ela, é possível informar um diretório no host local que poderá ser acessado no container, funcionando de forma parecida
    // com um mapeamento. Além disso, vamos informar o tipo de permissão que o container terá sob o diretório local, sendo:
    // ro para somente leitura, e rw para leitura e escrita.
    // Utilizando a opção -v , veja na prática como isso funciona:
    //
    // $ sudo docker run -d -p 8080:8080 -v /tmp/nginx:/usr/share/nginx/html:ro nginx
    //
    // O diretório /tmp/nginx pertence ao host local, e quero que ele seja mapeado para /usr/share/nginx/html dentro do container
    // que está sendo inicializado. Além disso, estou passando a instrução ro no final para informar ao container que ele só
    // poderá ler o conteúdo em /tmp/nginx.
    //
    //
    // *Utilizando volumes no Dockerfile
    //
    // Antes de começar, crie uma pasta chamada mysql e um novo arquivo Dockerfile com as seguintes instruções de instalação:
    //
    // FROM ubuntu:14.04
    // MAINTAINER Fernando Romulo Silva
    // ENV DEBIAN_FRONTEND noninteractive
    // RUN apt-get update -qq && apt-get install -y mysql-server-5.5 && apt-get clean && rm -r /var/lib/apt/lists/*
    // ADD my.cnf /etc/mysql/conf.d/my.cnf
    // RUN chmod 664 /etc/mysql/conf.d/my.cnf
    // ADD run.sh /usr/local/bin/run.sh
    // RUN chmod +x /usr/local/bin/run.sh
    // VOLUME ["/var/lib/mysql"]
    // EXPOSE 3306
    // CMD ["/usr/local/bin/run.sh"]
    //
    //
    // Não se preocupe comos arquivos run e my.cnf; eles estão sendo adicionados ao Dockerfile apenas para tornar a instalação e
    // execução do MySQL mais simples. Bem, aqui temos duas novidades.
    // A primeira é o uso da opção ENV para declarar uma variável ambiente, que neste caso será utilizada no processo de
    // instalação doMySQL, afimde evitar telas interativas que são exibidas durante a instalação para cadastrar usuários e configurar
    // o banco de dados.
    //
    // E a segunda é que estamos informando que o diretório /var/lib/mysql será um VOLUME; assim, poderemos manipular os
    // arquivos de dados salvos pelo MySQL.
    //
    // $ sudo docker build -t mysql .
    //
    // $ sudo docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123mudar --name app_mysql mysql
    //
    // Para testar se o container está funcionando perfeitamente, podemos acessar o MySQL partindo do host local:
    //
    // $ mysql -h 127.0.0.1 -u root -p
    //
    // No exemplo a seguir, usaremos uma imagem mínima chamada busybox para ter acesso aos dados do container que está em
    // execução com MySQL:
    //
    // $ sudo docker run -i -t --volumes-from app_mysql busybox
    //
    // O Docker inicializou o novo container utilizando a imagem busybox, e mapeou o volume do container mysql para a nova
    // instância que foi criada. Isso pode ser visto ao acessarmos o diretório do mysql no novo container:
    //
    // / # ls -la var/lib/mysql/
    //
    // Temos acesso aos dados que estão sendo gravados em disco pelo mysql. O mesmo poderia ser feito com arquivos de logs,
    // por exemplo. Além disso, a opção --volumes-from pode ser usada para replicar esse volume em múltiplos containers.
    //
    // É interessante saber que se você remover o container que monta o volume inicial – ou seja, o do mysql –, os posteriores
    // que foram inicializados com a opção --volumes-from não serão excluídos.
    //
    // * Backup e restore de volumes
    //
    // Outra forma de uso para --volumes-from pode ser para a construção de backups. Imagine que você queira realizar
    // um backup do diretório /var/lib/mysql do container para o seu host local:
    //
    // $ sudo docker run --volumes-from e1c9c12c71ed -v \ $(pwd):/backup ubuntu tar cvf /backup/backup.tar /var/lib/mysql
    //
    //
}
