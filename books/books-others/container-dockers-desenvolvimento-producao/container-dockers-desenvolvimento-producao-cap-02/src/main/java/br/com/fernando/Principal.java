package br.com.fernando;

public class Principal {

    // *Instalando no Linux
    //
    // No Ubuntu, para instalar e começar a utilizar o Docker é muito simples. Primeiro,
    // vamos precisar de alguns pacotes adicionais:
    //
    // $ sudo apt-get update
    // $ sudo apt-get install build-essential
    // $ sudo apt-get install docker
    //
    // *Hello, Docker!
    //
    // $ sudo docker run ubuntu /bin/echo Hello, Docker!
    //
    // Vamos entender por partes o que aconteceu. Primeiro, o Docker fez o download de uma imagem base, no caso do
    // repositório oficial do Ubuntu, na última versão estável 14.04 LTS .
    // Assim, ele instanciou um novo container, configurou a interface de rede e escolheu um IP dinâmico para ele.
    // Em seguida, selecionou um sistema de arquivos para esse novo container, e executou o comando /bin/echo
    // graças ao run, que é uma opção utilizada pelo Docker para enviar comandos ao container.
    // No final da execução, o Docker capturou a saída do comando que injetamos.
    //
    // *Um pouco de interatividade
    //
    // Podemos verificar a existência de containers em execução com o comando docker ps :
    //
    // $ sudo docker ps
    //
    // Como não possuímos nenhum container em execução ainda, a saída do comando foi
    // apenas o cabeçalho. Para o próximo exemplo, vamos criar um container e acessar o seu shell interativo:
    //
    // $ sudo docker run -i -t ubuntu /bin/bash
    //
    // O parâmetro -i diz ao Docker que queremos ter interatividade com o container, e o -t que queremos nos
    // linkar ao terminal do container. Em seguida, informamos o nome da imagem usada, no caso Ubuntu, e
    // passamos o comando /bin/bash como argumento.
    //
    //
    // Verificando o arquivo /etc/lsb-release , podemos ter certeza de que este shell está sendo executado em
    // um sistema diferente:
    //
    // root@bc5fdb751dd1:/# cat /etc/lsb-release
    // DISTRIB_ID=Ubuntu
    // DISTRIB_RELEASE=14.04
    // DISTRIB_CODENAME=trusty
    // DISTRIB_DESCRIPTION="Ubuntu 14.04.1 LTS"
    // root@bc5fdb751dd1:/#
    //
    // Ao sair do container, ele será colocado em estado de pausa pelo Docker. Vamos conferir usando
    // o complemento -a ao parâmetro ps ; isso significa que queremos listar todos os containers que
    // foram inicializados ou pausados.
    //
    // Uma outra opção que utilizaremos bastante é a -q , que retorna apenas o ID do container:
    //
    // $ sudo docker ps -qa
    //
    // O parametro stats, informa em tempo de execução, detalhes sobre o nível de consumo de recursos na
    // máquina host, feito pelos containers.
    //
    // $ sudo docker stats bc5fdb751dd1
    //
    // No primeiro exemplo, quando criamos o container, junto a ele geramos uma imagem.
    // Imagens podem ser listadas com a opção images:
    //
    // $ sudo docker images
    //
    // Voltando à listagem de containers, removeremos o container criado com a opção rm – isso mesmo, igual
    // ao comando do Linux! Junto ao comando, é necessário informar o nome ou ID:
    //
    // $ sudo docker rm bc5fdb751dd1
    //
    // *Controlando containers
    //
    // Ainda utilizando a imagem que foi construída no primeiro exemplo, veremos agora como vamos controlar
    // o funcionamento dos containers. Para isso, vamos criar um novo container e fazer a instalação do web server Nginx:
    //
    // $ sudo docker run -it --name ex_nginx ubuntu
    //
    // Perceba o uso da opção --name, onde foi informado um apelido para o novo container criado. Agora que estamos
    // no shell da nova instância, vamos atualizar os pacotes e, em seguida, instalar o Nginx no container:
    //
    // root@943b6186d4f8:/# apt-get update
    // root@943b6186d4f8:/# apt-get install -y nginx
    // root@943b6186d4f8:/# nginx -v
    // nginx version: nginx/1.4.6 (Ubuntu)
    // root@943b6186d4f8:/# exit
    //
    // É importante saber que toda e qualquer alteração realizada dentro de um container é volátil. Ou seja, se o finalizarmos,
    // ao iniciar novamente essa instalação do Nginx, a alteração não vai permanecer.
    // Para tornar as alterações permanentes, é necessário commitar o container – sim, o Docker possui um recurso para versionamento
    // igual ao Git. Sempre que um commit for feito, estaremos salvando o estado atual do container na imagem utilizada.
    // Para isso, basta usarmos o ID ou o apelido do container:
    //
    // $ sudo docker commit f637c4df67a1 ubuntu/nginx
    //
    // Com a nova imagem gerada contendo o Nginx instalado, agora vamos explorar um pouco mais. É possível criar containers
    // autodestrutivos com o uso da opção --rm . Isso significa que, ao finalizar a execução do container,
    // ele será excluído automaticamente. Para exemplificar, vamos criar uma nova instância com os seguintes argumentos:
    //
    // $ sudo docker run -it --rm -p 8080:80 ubuntu/nginx /bin/bash
    // root@e98f73a63965:/# service nginx start
    //
    // Observe o uso da opção -p . Com ela, estamos informando ao Docker que a porta 8080 no host será aberta e mapeada
    // para a porta 80 no container, estabelecendo uma conexão entre eles. Dentro da nova instância, executei o
    // comando service nginx start , para inicializar o Nginx. Para entender melhor o -p 8080:80 , podemos abrir o
    // browser e acessar o seguinte endereço: http://localhost:8080/.
    //
    // Saia do container:
    //
    // $ exit
    //
    // O acesso ao Nginx dentro do container está passando para a porta 8080 no host local.
    // De volta à nossa instância, vamos finalizar saindo do container, e ver o que o parâmetro --rm pode fazer:
    //
    // $ sudo docker ps -a
    //
    // O container só existiu enquanto estávamos dentro dele. Outra opção bastante usada é o -d , pois envia toda
    // a execução para background. Para testar, execute o código:
    //
    // $ sudo docker run -d -p 8080:80 ubuntu/nginx /usr/sbin/nginx -g "daemon off;"
    //
    // Já temos uma instância em plano de fundo com o Nginx funcionando. Agora, vamos manipular o seu funcionamento
    // utilizando as opções stop e start . Para isso, basta indicar o nome do container ou o seu ID:
    //
    // $ docker ps -q
    // $ sudo docker stop 6968dcb97e37
    //
    // *Rodando Novamente Containers Parados
    //
    // Reinicia todos em background:
    //
    // $ sudo docker start $(sudo docker ps -qa)
    //
    // Reinicia e vincula a um terminal/shel:
    //
    // $ sudo docker attach $(sudo docker ps -qa)
    //
    // *Destruindo imagens e containers
    //
    // É possível apagar todos os containers e imagens de uma só vez. Para isso, basta um pouco de shell script:
    //
    // $ sudo docker rm -f $(sudo docker ps -qa)
    //
    // Mas antes é necessario para os containers:
    //
    // $ sudo docker stop $(sudo docker ps -a -q)
    //
    // O mesmo serve para apagar imagens:
    //
    // $ sudo docker rmi -f $(sudo docker images -q)
    //
}
