# Docker-Certified-Associate-DCA-Exam-Guide
Docker Certified Associate (DCA) – Exam Guide | Published by Packt

This code repository contains all the labs provided in Docker Certified Associate (DCA) – Exam Guide | Published by Packt.

Chapter's labs are included inside directories. There is a directory per chapter and all lab's files and instructions are included.

Chapter

| Chapter Directory | Title |
|---|---|
| [chapter1](./chapter01) | Modern Infrastructures and Applications with Docker |
| [chapter2](./chapter02) | Building Docker Images |
| [chapter3](./chapter03) | Running Docker Containers |
| [chapter4](./chapter04) | Container Persistence and Networking |
| [chapter5](./chapter05) | Deploying Multi-Container Applications |
| [chapter6](./chapter06) | Introduction to Docker Content Trust |
| [chapter7](./chapter07) | Introduction to Orchestration |
| [chapter8](./chapter08) | Orchestration using Docker Swarm |
| [chapter9](./chapter09) | Orchestration using Kubernetes |
| [chapter10](./chapter10) | Introduction to Docker Enterprise Platform |
| [chapter11](./chapter11) | Learning Universal Control Plane |
| [chapter12](./chapter12) | Publishing Applications in Docker Enteprise |
| [chapter13](./chapter13) | Implementing an Enterprise-Grade Registry with Docker Trusted |

In order to execute these labs without modifying your computer, we provide you with some virtual environments.

>All these labs can be executed on your own Docker Engine, Docker Swarm or Docker Enterprise platform. Virtual environments are provided to help you deploying all Docker platforms from zero, including the installation processes.

This tables shows the required resources for each virtual environment.

| Chapters | Environment | Virtual Nodes | Requirements |
|---|---|---|---|
| chapters 1 to 6 |[Standalone](./environments/standalone) | 1 standalone | 1vCPU, 2GB of RAM and 10GB of disk space. |
| chapter 8 | [Docker Swarm Custer](./environments/swarm) | 4 virtual nodes | 4vCPU, 8GB of RAM and 50GB of disk space. |
| chapter 9 | [Kubernetes Custer](./environments/kubernetes) | 3 virtual nodes | 4vCPU, 8GB of RAM and 50GB of disk space. |
| chapters 11 to 13 | [Docker Enterprise](./environments/enterprise) | 4 virtual nodes | 8vCPU, 16GB of RAM and 100GB of disk space. |

Lab environments were tested on Linux and Windows 10 Pro and you can change the number of nodes and virtual resources modifying ___config.yaml___ file. There are specific instructions on each environment.



## Enjoy your learning ;)