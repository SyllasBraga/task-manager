# Task Manager

  ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
  ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
  ![MYSQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
  ![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)
  ![DOCKER](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)
  ![Spring-Data](https://img.shields.io/badge/spring-data-%236DB33F.svg?style=for-the-badge&logo=Spring-Data&logoColor=white)
  ![Spring-Security](https://img.shields.io/badge/spring-security-%236DB33F.svg?style=for-the-badge&logo=Spring-Security&logoColor=white)
  
Esse repositório armazena uma API Rest que possui as seguintes funcionalidades:
- Criar um usuário;
- Gerar um [JSON Web Token](https://www.alura.com.br/artigos/o-que-e-json-web-tokens?srsltid=AfmBOor7Yqh6ftlYmZ8NboAhH2ono0Oo751NkyueWHXBlX-CmThMpzmx) para a autenticação nos demais endpoints;
- Gerenciar tarefas: listar todas por filtros, inserir e atualizar;
- Gerenciar Subtarefas: inserir, atualizar e deletar.

### Requisitos
São requisitos para rodar esta aplicação:
- Docker na versão 25.0.3 ou superior;
- Java na versão 17 ou superior;
- Maven na versão 3.9.6 ou superior;

### Para rodar a aplicação
Primeiro, pelo terminal, navegue até a pasta "\backend\docker".  
Execute o seguinte comando:
``` PowerShell
docker-compose up -d
```
Após isso, navegue para a pasta "\backend", e excute execute este comando:
``` PowerShell
mvn clean install
```
Em seguida, execute este comando:
``` PowerShell
mvn spring-boot:run
```
Observe que o Docker irá subir um container com uma instância do MySQL na porta 3306.  
E que a aplicação Spring-Boot subirá na porta 8080.  
### Documentação
Todos os endpoints estão documentados no Swagger.   
Incluindo exemplos de requisições e respostas.   
Com a aplicação rodando, em seu navegador, navegue até a seguinte URL:
```
http://localhost:8080/task-manager/swagger-ui/index.html#/
```
### A base de dados
Os comandos DDL são gerados automaticamente pelo programa ao ser iniciando.  
A aplicação também possui um arquivo script de SQL para o preenchimento da base  
com dados iniciais. Disponível em "\src\main\resources\data.sql".
### Testes unitários e de integração
As funcionalidades tiveram testes unitários e de integração escritos.  
Estão disponíveis na pasta "\src\test\java\br\com\task_manager".  
Foram utilizadas as tecnologias JUnit 5 e Mockito para os unititários e   
também MockMVC para os testes de integração.  
Pela funcionalidade de teste coverage do VSCode, a aplicação está com   
95% de coverage. 


![vscode-coverage](/images/vscode-coverage.png)

Para rodar os testes, no terminal, execute este comando:
``` PowerShell
mvn test
```

### Logs
Para facilitar a depuração, nos métodos das classes de serviço,  
foram adionados logs das entradas e saídas, seguindo este modelo, onde aplicável:
``` Java
logger.info(" :: TaskServiceImpl.updateTask() - Request: {}", taskModel);
```
``` Java
logger.info(" :: TaskServiceImpl.updateTask() - Response: {}", taskUpdated);
```