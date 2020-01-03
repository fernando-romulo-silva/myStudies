package br.com.fernando;

public class Principal {

    // * O que é Docker?
    //
    // Docker é uma ferramenta para criar e manter containers, ou
    // seja, ele é responsável por armazenar vários serviços de forma isolada do SO
    // host, como: web server, banco de dados, aplicação, memcached etc. O seu
    // back-end é baseado em LXC (LinuX Containers).
    //
    // * Problemas em usar internet nos containers (principalmente no comando apt-get dentro de vms)
    //
    // $ sudo gedit /etc/NetworkManager/NetworkManager.conf
    //
    // Change:
    // dns=dnsmasq
    // To
    // #dns=dnsmasq
    //
    // Reinicie, se continuar vc precisa executar:
    //
    // $ sudo apt-get install bridge-utils
    //
    // $ sudo pkill docker
    //
    // $ sudo iptables -t nat -F
    //
    // $ sudo ifconfig docker0 down
    //
    // $ brctl delbr docker0
    //
    // $ sudo service docker start
    //
    // * O que diferencia um container de uma máquina virtual?
    //
    // A virtualização por containers, proposta pelo LXC, ocorre de forma me-
    // nos isolada, pois compartilha algumas partes do kernel do host, fazendo com
    // que a sobrecarga seja menor.
    //
    // O Linux Kernel possui um recurso chamado de Cgroups (control groups),
    // que é usado para limitar e isolar o uso de CPU, memória, disco, rede etc. Um
    // outro recurso são os Namespaces, responsáveis por isolar grupos de processos,
    // de modo que eles não enxerguem os processos de outros grupos, em outros
    // containers ou no sistema host.
    //
    // * O que são Namespaces?
    //
    // Como vimos na seção anterior, o Docker tira proveito do recurso de Names-
    // paces para prover um espaço de trabalho isolado para os containers. Sendo
    // assim, quando um container é criado, automaticamente um conjunto de na-
    // mespaces também é criado para ele.
    // Namespaces cria uma camada de isolamento para grupos de processos.
    // No caso do Docker, são estes:
    //
    // • pid – isolamento de processos (PID);
    // • net – controle de interfaces de rede;
    // • ipc – controle dos recursos de IPC (InterProcess Communication);
    // • mnt – gestão de pontos de montagem;
    // • uts – UTS (Unix Timesharing System) isolar recursos do kernel;
    //
}
