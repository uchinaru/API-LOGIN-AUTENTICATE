# API - Login, autenticate e encrypt.

> Solicitada ao **ChatGPT** para desafio pessoal e estudo.

#### 1. **Sistema de Autenticação de Usuário**
-   Crie uma API simples para registro e login de usuários.
-   Use uma base de dados (H2 ou MySQL).
-   Utilize bcrypt para hash de senhas.
-   Valide sessões de usuários usando JWT (JSON Web Token).

### Configurações adicionais ⚙️
> dependency

 - <artifactId>spring-boot-starter-data-jpa</artifactId>
 - <artifactId>spring-boot-devtools</artifactId>
 - <artifactId>mysql-connector-j</artifactId>
 - <artifactId>lombok</artifactId>
 - <artifactId>spring-boot-starter-tomcat</artifactId>
 - <artifactId>spring-boot-starter-test</artifactId>
 - <artifactId>jbcrypt</artifactId>


### Fix 🛠️

Atualizei a versão do `SpringToolSuite4` e tomei erro ao acessar os `@Getter` e `@Setter` pois desconfigurou o `Lombok`, achei um tutorial que resolveu tranquilamente. [Aqui](https://dicasdeprogramacao.com.br/como-configurar-o-lombok-no-eclipse/)
