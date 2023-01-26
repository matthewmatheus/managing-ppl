# Managing ppl
RestAPI simples construída com Spring em 5 dias.


![Badge de status do projeto](http://img.shields.io/static/v1?label=status&message=em%20construção&color=orange)

# Tecnologias usadas

Foram usadas as seguintes tecnologias ao desenvolver esse projeto:

- Java 17
- Maven
- Spring Boot, Data JPA.
- Lombok
- Hibernate
- H2 DataBase
- Mockito
- JUnit5

Nessa aplicação construí do zero uma RestAPI com base nas propostas a seguir.

Usando Spring boot, criar uma API simples para gerenciar Pessoas. Esta API deveria permitir: 
<br> <br> 
-	Criar uma pessoa
-	Editar uma pessoa
-	Consultar uma pessoa
-	Listar pessoas
-	Criar endereço para pessoa
-	Listar endereços da pessoa
-	Poder informar qual endereço é o principal da pessoa  
<br>
Uma Pessoa deve ter os seguintes campos:  <br>

-	Nome
-	Data de nascimento
-	Endereço:
-	Logradouro
-	CEP
-	Número
-	Cidade

Requisitos  
-	Todas as respostas da API devem ser JSON  
-	Banco de dados H2



# Getting Started

### Você pode: 

- Baixar o [ZIP do projeto](https://github.com/matthewmatheus/managing-ppl/archive/refs/heads/master.zip) e abri-lo em uma IDE de sua preferência, nesta aplicação foi ultilizado o `IntelliJ`.

### Ou

- Clonar o repositório `https://github.com/matthewmatheus/managing-ppl`

- Rodar `mvn clean install` para instalar as dependências do projeto.

- Rodar `mvn spring-boot:run` para subir a aplicação.

- Certifique-se de ter instalado o banco de dados H2, a aplicação irá subir diretamente nele por padrão. <br> 

 `username: sa` <br>
 `senha: password`
<br> 
- Caso queira alterar os dados de autenticação do banco é só modificar como quiser em `application.properties`.


<br> 

# Endpoints

Você pode ultilizar o Insomnia ou outra ferramenta de sua preferência para realizar as requisições

### POST - Cadastrando uma PESSOA.

+  `http://localhost:8080/pessoas` <br/> <br>
    ```json
    {
    "nome": "Testinho Manero",
    "dataDeNascimento" : "29/03/2000",
    "enderecos": [
        {
            "logradouro": "Rua dos Testes",
            "cep": "03210001",
            "numero": 1050,
            "cidade": "São José dos Testes"
        },
        {
            "logradouro": "Rua dos Testes 2",
            "cep": "03215001",
            "numero": 20,
            "cidade": "São José dos Testes"
        }
    ]} 
    ```
    
    
      <br>
    
    ### POST - Adicionando um outro endereço para a PESSOA
    
    
    +  `http://localhost:8080/enderecos/{idPessoa}` <br/> <br>
    ```json
    {
      "endereco": {
      "logradouro": "Rua dos Testezinhos",
      "cep": "03210001",
  	    "numero": 212,
        "cidade": "São José dos Testes Tops"
      }
    }
   ```
   
   <br>
   
   ### GET - Trazendo pessoa por ID, deve retornar pessoa e seus endereços:
   
   
    
     +  `http://localhost:8080/pessoas/{idPessoa}` <br/> <br>
     
     
     
    ```json
        {
	        "id": 1,
	        "nome": "TestinhA Manero",
	        "dataDeNascimento": "29/03/2000",
	        "enderecos": [
		        {
			        "id": 1,
			        "logradouro": "Rua dos Testes",
			        "cep": "03210001",
			        "numero": "1050",
        			"cidade": "Testes dos Pinheirinhos",
			        "principal": false
	           },
		         {
			        "id": 2,
			        "logradouro": "Rua dos Testes 2",
			        "cep": "03215001",
			        "numero": "20",
			        "cidade": "Testes dos Pinheirinhos",
			        "principal": false
		         },
	  	         {
   ```
   
   ### GET - Trazendo lista de endereços da pessoa pelo seu ID, deve retornar algo como:
   
   
    +  `http://localhost:8080/enderecos/lista/{IdPessoa}` <br/> <br>
     
     
     
    ```json
      {
	"id": 1,
	"nome": "TestinhA Manero",
	"dataDeNascimento": "29/03/2000",
	"enderecos": [
		{
			"id": 1,
			"logradouro": "Rua dos Testes",
			"cep": "03210001",
			"numero": "1050",
			"cidade": "Testes dos Pinheirinhos",
			"principal": true
		},
		{
			"id": 2,
			"logradouro": "Rua dos Testes 2",
			"cep": "03215001",
			"numero": "20",
			"cidade": "Testes dos Pinheirinhos",
			"principal": false
		},
		{
			"id": 3,
			"logradouro": "Rua dos Testezinhos",
			"cep": "03210001",
			"numero": "212",
			"cidade": "São José dos Testes Corretos",
			"principal": false
   ```
   
   <br>
   
   - Note que o atributo `principal` do primeiro endereço está marcado como `true`, e todos os outros como `false` <br> <br>
   
   
   ### PUT - Alterando endereço principal, no body deve ser passado o ` id do novo endereço principal ` :
   
   +  `http://localhost:8080/enderecos/principal/{idPessoa}` <br/> <br>
     
     
     
    ```json
        {
	        "idEndereco": 1
         }
   ```
   
   
    ### PUT - Alterando dados da pessoa ou do endereço, basta passar os dados novos desejados.
      
    
    
   +  `http://localhost:8080/pessoas}` <br/> <br>
     
     
     
    ```json
        {
        	"id": 1, 
	       "nome": "Teste da Silva",
	       "endereco" : {
	        "cidade": "Testes dos Pinheirinhos"
		
    	}
    }
   ```
   
    ### GET - Por fim, retornando pagina(s) de todas as pessoas cadastradas.
    
    
      +   `http://localhost:8080/pessoas` <br/> <br>
     
     
     
    ```json
        {
	      "content": [
	    	{
		    	"id": 1,
		    	"nome": "Teste da Silva",
		    	"dataDeNascimento": "2000-03-29"
		    }
      	],
	      "pageable": {
	      	"sort": {
			      "empty": false,
		    	"sorted": true,
			      "unsorted": false
	      	},
		      "offset": 0,
		      "pageNumber": 0,
	      	"pageSize": 10,
	      	"paged": true,
		      "unpaged": false
   ```
        
    

