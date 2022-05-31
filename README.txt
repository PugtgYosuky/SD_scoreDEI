Webserver ScoreDEI
31/05/2022

Alunos:
    Joana Simões, 2019217013
    Samuel Carinhas, 2019217199

Requisitos:
    - Java 18 (O projeto não foi testado em outras versões, por este motivo é altamente recomendável usar esta versão)
    - Base de dados Postgres (Apenas se usar uma base de dados pessoal)

Informações:
    Por defeito, o executável "scoreDEI.war" já vem com os dados de uma base de dados externa. Para alterar a base de dados a utilizar, é possível
abrir o executável com um sistema de ficheiros ZIP (Winrar, por exemplo), e alterar o ficheiro WEB-INF/classes/application.properties para os dados
da nova base de dados. Se a nova base de dados não conter as tabelas necessárias para correr o projeto, é necessário alterar o valor do parâmetro "spring.jpa.hibernate.ddl-auto"
para "create" no ficheiro referido anteriormente.

Como executar:
    Para executar o WebServer, basta correr o seguinte comando:
        java -jar scoreDEI.war
