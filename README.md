# Documentação atividade tarefas

Aqui você irá ver o desenvolvimento de um projeto com o objetivo de organizar de tarefas solicitado por um gestor sendo usado o Spring Boot em linguagem Java com comunicação com o banco de dados MySQL e disponibilização de aplicações usando o Docker com ações de CI/CD.

## Ferramentas

![Logos ferramentas](./imagens/logos%20rademe.png)

As ferramentas utilizadas foram: Docker, Jenkins, Spring boot, Eclipse, MySQL e o Postman.
- A responsabilidade do Docker foi de armazenar e iniciar as imagens e os containers para fazer a conexão do eclipse com o banco.
- A utilidade do Jenkins é um gerenciador de ações que foi usado para fazer a automação de exclusão e criação dos containers e imagens pelos comandos do Docker.
- O SpringBoot foi a base de tudo onde foi feito as tabelas requisitadas pelo cliente que foram: idtarefa, titulo, descricao, datainicio, datatermino, estado(aberta/fechada) e  lá foi criado os controllers e os repositories que foram responsáveis de fazer os cadastros, listagem
das tarefas, atualização das tarefas, exclusão das tarefas e a conexão com servidor web.
- Eclipse foi a interface onde os códigos foram colocados, rodados e aplicados as extensões do SpringBoot que foram: MySQL Driver, Spring Web, Spring Data JPA, Spring Boot DevTools e o Spring Boot Actuator.
- O MySQL Workbench foi utilizado para criar o Banco de dados e armazenar os cadastros.
- Postman foi usado testar os códigos e aplicações para ver se está cadastrando as tarefas com os campos requisitados.

## Primeira etapa criação do banco de dados

No aplicativo do MySQL WorkBench você terá que criar a base de dados para lá ser criada a tabela e armazenar os dados e cadastros.

    create database tarefasdb charset= "utf8mb4" collate="utf8mb4_general_ci";
    use tarefasdb;

## Configuração eclipse e instalação do Spring Boot

Dentro do Eclipse terá que ser instalado o Spring Boot e suas extensões, para fazer isso temos que abrir o Marketplace do eclipse e buscar pelo Spring Boot e instalá-lo nesse processo pode dar diversos erros.

## Criação do projeto e aplicando as extensões

Primeiramente teremos que criar o Projeto com o Spring Start Project e aplicar que vão ser direcionadas para o pom.xml suas extensões que foram o MySQL Driver, Spring Web, Spring Data JPA, Spring Boot DevTools e o Spring Boot Actuator.

## Criação do Pojo

Iremos começar pelo pacote pojo que é um domain que é criado e armazenado informações de uma entidade — classe contendo um ou mais construtores, atributos e métodos que encapsulam seu comportamento. Sua estrutura é essa:

    package br.com.tarefa.projeto.domain;
	
	import java.util.Date;
	import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.EnumType;
	import jakarta.persistence.Enumerated;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	
	@Entity
	public class Tarefas {
			@Id
			@GeneratedValue(strategy=GenerationType.IDENTITY)
			@Column(nullable = false)
			private Integer idtarefa;
			
			@Column(nullable = false)
			private String titulo;	
			
			@Column(nullable = false)
			private String descricao;		
			
			@Column(nullable = false)
			private Date datainicio;		
			
			@Column(nullable = false)
			private Date datatermino;		
			
			@Column(nullable = false)
			@Enumerated(value = EnumType.STRING)
			private Estado estado;
			#Enumerated foi usado para especificar o estado da tarefa definida 
			aberta ou fechada
			
			public  Tarefas()  {

	}

	public  Tarefas(Integer  idtarefa,  String  titulo,  String  descricao,  		   Date  datainicio,  Date  datatermino,

	Estado  estado)  {

	this.idtarefa  =  idtarefa;
	this.titulo  =  titulo;
	this.descricao  =  descricao;
	this.datainicio  =  datainicio;
	this.datatermino  =  datatermino;
	this.estado  =  estado;
	}

	public  Integer  getIdtarefa()  {
	return  idtarefa;
	}
	
	public  void  setIdtarefa(Integer  idtarefa)  {
	this.idtarefa  =  idtarefa;
	}

	public  String  getTitulo()  {
	return  titulo;
	}
	
	public  void  setTitulo(String  titulo)  {
	this.titulo  =  titulo;
	}

	public  String  getDescricao()  {
	return  descricao;
	} 

	public  void  setDescricao(String  descricao)  {
	this.descricao  =  descricao;
	}

	public  Date  getDatainicio()  {
	return  datainicio;
	}

	  

	public  void  setDatainicio(Date  datainicio)  {
	this.datainicio  =  datainicio;
	}

	public  Date  getDatatermino()  {
	return  datatermino;
	}
	
	public  void  setDatatermino(Date  datatermino)  {
	this.datatermino  =  datatermino;
	}  
	
	public  Estado  getEstado()  {
	return  estado;
	}

	public  void  setEstado(Estado  estado)  {
	this.estado  =  estado;
	}
	}

## Criação do Enum 
No mesmo pacote que foi feito o domain temos que colocar o Enum para atender as necessidades e especificações do cliente para declarar a tarefa aberta e fechada.
Assim feito abaixo:

    package br.com.tarefa.projeto.domain;
	public  enum  Estado  {

	Aberta,

	Finalizada

	}

## Criação do Repository
O Spring Data JPA se concentra no uso do JPA para armazenar dados em um banco de dados relacional.
O código usado para a criação do repository foi:

    package br.com.tarefa.projeto.repository;
	
	import java.util.List;
	import org.springframework.data.jpa.repository.JpaRepository;
	import br.com.tarefa.projeto.domain.Estado;
	import br.com.tarefa.projeto.domain.Tarefas;

	public  interface  TarefasRepository  extends  JpaRepository  <Tarefas,  Integer>  {

	#Criação dos filtros de buscas que o cliente pediu que foi listar por titulo e estado como aberta ou fechada.
	List<Tarefas>  findAll();
	List<Tarefas>  findByTitulo(String  titulo);
	List<Tarefas>  findByEstado(Estado  estado);
	}

## Criação do Controller
O Controller é a camada responsável tanto por receber requisições como por enviar a resposta ao usuário.
Como o código abaixo:
	
	package br.com.tarefa.projeto.controller;
	import  java.util.List;
	import java.util.Optional;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.web.bind.annotation.DeleteMapping;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.PutMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RestController;

	import br.com.tarefa.projeto.domain.Estado;
	import br.com.tarefa.projeto.domain.Tarefas;
	import br.com.tarefa.projeto.repository.TarefasRepository;
	  

	@RestController
	public  class  TarefasController  {

	@Autowired
	private  TarefasRepository  tr;
	
	@GetMapping("/tarefa/listar")
	public  List<Tarefas>  listar(){
	return  tr.findAll();
	}

	@GetMapping("/tarefa/listar/{titulo}")
	public  List<Tarefas>  listarT(@PathVariable  String  titulo){
	return  tr.findByTitulo(titulo);
	}

	@GetMapping("/tarefa/listar/Aberta")
	public  List<Tarefas>  findByEstado(Estado  estado)  {
	return  tr.findByEstado(estado.Aberta);
	}

	@GetMapping("/tarefa/listar/Finalizada")
	public  List<Tarefas>  findByEstadoo(Estado  estado)  {
	return  tr.findByEstado(estado.Finalizada);
	}

	@PostMapping("/tarefa/cadastrar")
	public  String  cadastrar(@RequestBody  Tarefas  tarefas)  {
	tr.save(tarefas);
	return  "Tarefa cadastrada";
	}

	@PutMapping("/tarefa/atualizar/{id}")
	public  String  atualizar(@PathVariable  Integer  id,  @RequestBody  Tarefas  tarefas)  {
	String  msg  =  "";
	Optional<Tarefas>  t  =  tr.findById(id);
	if(t.isPresent())  {
	tarefas.setIdtarefa(id);
	tr.save(tarefas);
	msg  =  "Tarefa atualizada";
	}
	else  {
	msg  =  "Tarefa não encontrada";
	}
	return  msg;
	} 

	@DeleteMapping("/tarefa/apagar/{id}")
	public  String  apagar  (@PathVariable  Integer  id)  {
	String  msg  =  "";
	Optional<Tarefas>  t  =  tr.findById(id);
	if(t.isPresent())  {
	tr.deleteById(id);
	msg  =  " Tarefa apagada";
	}
	else  {
	msg  =  "Tarefa não encontrada";
	}
	return  msg;
	}
	}

## Configurações do application.properties

Agora temos que configurar o application.properties para fazer a ligação com o banco no MySQL para fazer os testes pelo Postman
Para funcionar temos que fazer os seguintes testes:

    spring.datasource.url=jdbc:mysql://127.0.0.1:6556/tarefasdb?usessl=false
    #A porta você pode trocar pra qual vc estiver usando a porta se localiza após os exemplo: ip:porta
	spring.datasource.username=root
	spring.datasource.password=senac@123
	spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
	spring.jpa.hibernate.ddl-auto=update

	#porta do servidor spring
	server.port=8095

**ATENÇÃO ESSA CONFIGURAÇÃO É SOMENTE PARA TESTES APÓS TERMINAR ELES TEREMOS QUE FAZER ALTERAÇÕES**

## Criação do Dockerfile

O Dockerfile nada mais é do que **um meio que utilizamos para criar nossas próprias imagens**. Em outras palavras, ele serve como a receita para construir um container, permitindo definir um ambiente personalizado e próprio para meu projeto.
Para criar ele fizemos essa sequência de códigos em um arquivo file normal no eclipse

    FROM openjdk:17
	EXPOSE 8095 #porta que vc está usando
	ADD target/app-tarefas.jar app-tarefas.jar
	ENTRYPOINT ["java","-jar","app-tarefas.jar"]

## Reajuste no application.properties após os testes

Para fazer desfazer a ligação com o Postman e fazer a ligação com o docker e com o MySQL sendo servido com o servidor do Spring.

    spring.datasource.url=jdbc:mysql://172.17.0.2:3306/tarefasdb?usessl=false
	spring.datasource.username=root
	spring.datasource.password=senac@123
	spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
	spring.jpa.hibernate.ddl-auto=update

	#porta do servidor spring
	server.port=8095
E temos que fazer uma pequena alteração no pom.xml nas ultimas linhas para colocar o lastname na aplicação assim como na print:

![print pom](./imagens/Captura%20de%20tela%202023-05-10%20112116.png)

## Configuração pós eclipse

Agora teremos que colocar nosso projeto em um repository online no github, no eclipse abra o terminal e faça o lançamento para o github para a criação dos containers e imagens no Docker.

## Criação da imagem 
Na situação atual estamos temos que clonar o projeto do seu github usando o código:
`git clone https://www.github.com/username/nome do repositorio`
Após entre na pasta criada pelo repositorio e use o código:

    mvn clean install

Depois disso  temos que criar a imagem no Docker usando este código:

    docker build -t app-tarefas .
O nome escolhido por causa do projeto.
E use esse comando para verificar a criação da imagem:

    docker images

## Criação do container
Para a criação do container temos que fazer o seguinte codigo:

    docker run --name tarefas -p 8095:8095 -d tarefas
E verifique se o container foi criado usando o código:

    docker ps -a

## Automatizando com o Jenkins
Configure a porta no Virtual Box adicionando mais uma com a porta 8080 para o funcionamento do Jenkins, após isso temos que ir no navegador e colocar 
http://seuip:porta no meu caso o link fica assim http://172.17.0.2:8080
Abrindo o site do Jenkins  criamos uma conta e um pipeline para refazer os passos do exclusão do container e imagem e criação deles.

Na linha de pipeline colocamos o seguinte script:

    pipeline{
    agent any
    tools{
        maven 'maven_3_8_5'
    }
    stages{
        stage('Build Manven'){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/JotaTMBR/app-papelaria']])
                sh 'mvn clean install'
            }
        }
        stage('Deletar Container'){
            steps{
                sh 'docker rm -f tarefas'
            }
        }
        stage('Deletar Imagem'){
	        steps{
		        sh'docker rmi tarefas'
	        }
        }
        
        stage('Criando uma Docker Image'){
            steps{
                sh 'docker build -t tarefas .'
            }
        }
        stage('Criar Container Docker'){
            steps{
                sh 'docker run --name tarefas -p 8095:8095 -d tarefas'
            }
        }
    }
	}	
OBS: O nome varia com o nome que você deu na configuração do container