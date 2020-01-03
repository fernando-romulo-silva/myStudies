package br.com.fernando;

public class Principal {

    // *Explorando o Dockerfile
    //
    // O Dockerfile é um arquivo que aceita rotinas em shell script para serem executadas. Veja a seguir como
    // ficaria o processo de instalação do Nginx, que fizemos no capítulo anterior, em um script Dockerfile:
    //
    // FROM ubuntu
    // MAINTAINER Daniel Romero <infoslack@gmail.com>
    // RUN apt-get update
    // RUN apt-get install -y nginx
    //
    // Vamos por partes:
    // • FROM – estamos escolhendo a imagem base para criar o container;
    // • MAINTAINER – especifica o nome de quem vai manter a imagem;
    // • RUN – permite a execução de um comando no container.
    //
    // Vamos continuar o processo e gerar a nossa primeira imagem com a opção build:
    //
    // $ sudo docker build -t nginx .
    //
    // O ponto no final indica o path do script de Dockerfile para gerar a imagem, supondo que você está executando
    // o comando no mesmo diretório onde o Dockerfile se encontra.
    //
    // O próximo passo é testarmos a imagem que foi gerada criando um container, e dizer que queremos utilizar a porta 8080
    // no nosso host, assim como fizemos no capítulo anterior.
    //
    // $ sudo docker run -d -p 8080:80 nginx /usr/sbin/nginx -g "daemon off;"
    //
    // *Mapeando portas
    //
    // O comando para execução de um container com Nginx está um pouco grande. Vamos reduzir a instrução de inicialização
    // inserindo a opção EXPOSE no nosso Dockerfile. Além disso, faremos uso de um atalho para mapeamento de portas,
    // o parâmetro -P . No Dockerfile, adicionamos a instrução EXPOSE :
    //
    // FROM ubuntu
    // MAINTAINER Daniel Romero <infoslack@gmail.com>
    // RUN apt-get update
    // RUN apt-get install -y nginx
    // EXPOSE 80
    //
    // Note que o Docker apenas atualizou a imagem que já existia, acrescentando a instrução para expor a porta 80 sempre que
    // um novo container for criado. Agora, podemos testar criando uma nova instância:
    //
    // $ sudo docker run -d -P nginx /usr/sbin/nginx -g "daemon off;"
    //
    // Ao criar um container passando a instrução -P , estamos permitindo que o Docker faça o mapeamento de qualquer porta
    // utilizada no container, para alguma outra porta no host. Conferiremos isso com o docker ps.
    //
    // Sempre que um container for criado desta forma, o retorno da instrução -P será uma porta aleatória criada no host.
    // Se testarmos o acesso ao Nginx na porta criada, teremos o seguinte retorno:
    //
    // $ curl -IL http://localhost:49153/
    //
    // *Copiando arquivos
    //
    // Temos um pequeno problema ao utilizar a porta 8080 , pois o Nginx está configurado para utilizar a porta 80.
    // Podemos criar um container acessando o seu shell, atualizar a sua configuração, sair e commitar a imagem.
    // Imagine se você precisar fazer várias alterações nos arquivos de configuração do web server, a produtividade pode
    // ser comprometida. Para resolver isso, vamos utilizar a opção ADD no Dockerfile. Desta forma, vamos referenciar o
    // arquivo que queremos copiar e o local de destino para a imagem durante o processo de build.
    //
    // FROM ubuntu
    // MAINTAINER Daniel Romero <infoslack@gmail.com>
    // RUN apt-get update
    // RUN apt-get install -y nginx
    // ADD exemplo /etc/nginx/sites-enabled/default
    // EXPOSE 8080
    //
    // Com a instrução ADD , o arquivo chamado exemplo será copiado para o diretório /etc/nginx/sites-enabled , e será chamado de default .
    // O arquivo exemplo deve existir no mesmo contexto do Dockerfile. O seu conteúdo é bem simples e foi alterado apenas
    // para o Nginx utilizar a nova porta, no nosso caso, a 8080.
    //
    // Então, vamos criar um novo container, sem informar instruções de portas, e testar para ver se está tudo certo:
    //
    // $ sudo docker run -d nginx /usr/sbin/nginx -g "daemon off;"
    // $ curl -IL http://localhost:8080
    //
    // curl: (7) Failed to connect to localhost port 8080: Connection refused
    //
    // Como resposta, recebemos um erro em nosso teste. Será que esquecemos alguma coisa? Não, não esquecemos de nada.
    // Quando utilizamos o -p ou o -P , estamos mapeando uma porta interna do container para uma porta em nosso host local.
    // Agora verificaremos o funcionamento do Nginx acessando o diretamente o IP do container.
    // Para capturar informações do container, podemos fazer uso da opção inspect, que retorna informações low-level de um container,
    // ou de uma imagem (como o retorno é muito grande, vamos simplificar com grep):
    //
    // $ sudo docker inspect a6b4fe21aece | grep IPAddress
    //
    // Aí está o IP do nosso container. Outra opção seria executar o comando ifconfig diretamente no container. Tb temos
    // a opção exec justamente para facilitar testes rápidos nos containers:
    //
    // $ sudo docker exec -it a6b4fe21aece ifconfig eth0
    //
    // *Definindo o diretório de trabalho
    //
    // A área de trabalho padrão do Docker é o diretório raiz / . Podemos alterar isso durante a criação de um container ao usarmos
    // a opção -w , ou tornando padrão usando a diretiva WORKDIR no Dockerfile. Imagine que queremos utilizar um container para desenvolvimento de
    // um projeto Ruby on Rails. O Dockerfile poderia ser adicionado dentro do diretório do projeto e teria as seguintes configurações:
    //
    // FROM ubuntu
    // MAINTAINER Daniel Romero <infoslack@gmail.com>
    // RUN apt-get update
    // RUN apt-get install -y nginx
    // ADD exemplo /etc/nginx/sites-enabled/default
    // RUN echo "daemon off;" >> /etc/nginx/nginx.conf
    // ADD ./ /rails
    // WORKDIR /rails
    // EXPOSE 8080
    // CMD service nginx start
    //
    // Com o diretório de trabalho definido em WORKDIR , temos antes dele o ADD ./ /rails , que copia os arquivos a partir do contexto no qual foi
    // executado para o filesystem do container. Lembre-se de que este arquivo Dockerfile, neste exemplo, está dentro do projeto Rails:
    //
	// $ sudo docker build -t nginx .
	// 
    // $ sudo docker run -d -p 8080:8080 --name app_nginx nginx
    //
	// Observer o nome do container: app_nginx 
	// Nao pode ter dois container com o mesmo nome.
    //
    // Para entendermos o que de fato aconteceu nas instruções ADD e WORKDIR , que foram adicionadas ao Dockerfile, podemos acessar o container
    // utilizando o exec :
    //
    // $ sudo docker exec -it app_nginx bash
    //
    // *Inicializando serviços
    //
    // O comando utilizado para geração de containers ficou bem menor. Entretanto, podemos reduzi-lo um pouco mais, informando no Dockerfile a
    // instrução CMD, para que ele possa inicializar o Nginx sempre que um container novo for criado. Vamos à edição:
    //
    // FROM ubuntu
    // MAINTAINER Daniel Romero <infoslack@gmail.com>
    // RUN apt-get update
    // RUN apt-get install -y nginx
    // ADD exemplo /etc/nginx/sites-enabled/default
    // RUN echo "daemon off;" >> /etc/nginx/nginx.conf
    // EXPOSE 8080
    // CMD service nginx start
    //
    // Existe outra forma de inicializar serviços, utilizando ENTRYPOINT. Sempre que usamos CMD em seu background, ele está chamando o
    // bash assim: /bin/sh -c
    // Em seguida, envia como parâmetro o comando ou instrução que especificamos.
    // A diferença em usar ENTRYPOINT é que ele chama o comando ou script diretamente, por exemplo:
    //
    // ...
    // EXPOSE 8080
    // ENTRYPOINT ["/usr/sbin/nginx"]
    //
    // Quando utilizamos ENTRYPOINT , tudo o que for especificado em CMD será enviado como complemento para ENTRYPOINT :
    //
    // ...
    // ENTRYPOINT ["/etc/init.d/nginx"]
    // CMD ["start"]
    //
    //
    // *Tratando Logs
    //
    // O Docker possui um recurso para visualizar os logs de saída e de erro padrão (stdout e stderr). Isso é interessante para
    // verificarmos o que está acontecendo dentro de um container, sem a necessidade de conferir um determinado arquivo de log.
    // Para este exemplo, vamos utilizar o Dockerfile da seção anterior e redirecionar os logs do Nginx para stdout e stderr.
    // O Dockerfile ficará assim:
    //
    // FROM ubuntu
    // MAINTAINER Daniel Romero <infoslack@gmail.com>
    // RUN apt-get update
    // RUN apt-get install -y nginx
    // ADD exemplo /etc/nginx/sites-enabled/default
    // RUN ln -sf /dev/stdout /var/log/nginx/access.log
    // RUN ln -sf /dev/stderr /var/log/nginx/error.log
    // EXPOSE 80
    // CMD ["nginx", "-g", "daemon off;"]
    //
    // Faça uma request e pegue o logs pelo comando
    //
    // $ sudo docker logs 428c5cb6e03e
    //
    //
    // *Exportação e importação de containers
    //
    // Podemos criar uma imagem partindo de um container que está funcionando, e gerar um arquivo .tar usando a opção save.
    // Para criar a nova imagem, basta fazer um commit no container em execução:
    //
    // $ sudo docker ps -q
    //
    // $ sudo docker commit b02af9430141 nova_imagem
    //
    // De posse da nova imagem que foi gerada, faremos a exportação criando um arquivo:
    //
    // $ sudo docker save nova_imagem > /tmp/nova_imagem.tar
    //
    // Para fazer a importação desse arquivo, utilizamos a opção load :
    //
    // $ sudo docker load < /tmp/nova_imagem.tar
    //
}
