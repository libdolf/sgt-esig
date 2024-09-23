## SISTEMA DE GESTÃO DE TAREFAS

### Tecnologias usadas
[![Technologies](https://skillicons.dev/icons?i=java,maven,postgres,hibernate,docker&theme=light)](https://skillicons.dev)
### Funcionalidades implementadas

Sistema de gestão de tarefas que salva novas tarefas, busca por Número, Título/Descrição, 
Responsável e etc. Utiliza JSF e Primefaces para estruturar a aplicação MVC. A implementação do
JPA fica por conta do Hibernate. O banco de dados utilizado é o PostgreeSQL. 

O sistema consegue:
- Criar uma tarefa
- Atualizar a tarefa
- Remover a tarefa
- Listar tarefas


Requisitos implementados:
- **a) Criar uma aplicação Java Web utilizando JavaServer Faces (JSF)**
- **b) Utilizar persistência em um banco de dados PostgreSQL**
- **c) Utilizar JPA**
- **d) Utilizar testes de unidades**

### Como executar 

#### Requisitos:

- Java 22
- Maven 3.9.x
- Docker
- Tomcat 10.x

1 - Instale o Apache Tomcat:
- Baixe o Tomcat do [site oficial](https://tomcat.apache.org/download-10.cgi).
- Extraia o arquivo em um diretório de sua escolha.

2 - Crie o arquivo WAR:
````bash
mvn clean package 
````
3 - Inicie o Banco de Dados:
- Na raíz do projeto execute o docker-compose.yml
````bash
docker-compose up
````

4 - Implante o WAR no Tomcat:
- Copie o arquivo .war gerado (encontrado na pasta target do seu projeto) para a pasta webapps do diretório Tomcat.

5 - Inicie o Tomcat:
 - Navegue até o diretório bin do Tomcat e execute o script de inicialização:
 - No Windows:
 ````bash 
catalina.bat start
 `````
 - No Linux/Mac: 
 ````bash 
catalina.sh start
 `````
6 - Acesse a aplicação:

Abra um navegador e acesse http://localhost:8080/sgt-esig/