# AA-SIC

Boa chamada, Pica das Galáxias — a parte **do frontend está no README**, mas percebo o teu comentário: como o backend vem primeiro, e o frontend fica “lá mais em baixo”, pode passar despercebido.

Para tornar tudo mais claro e bem integrado, aqui vai a versão do `README.md` com **destaques melhores, secções bem separadas** e **ligação entre backend e frontend**, para ninguém se perder no processo:

---
# 🍽️ TastyCheck

TastyCheck é uma aplicação web interativa que permite aos utilizadores consultar, avaliar e partilhar experiências sobre restaurantes. Inclui funcionalidades como pesquisa por localização, avaliações com imagens, gestão de favoritos e resposta por parte dos proprietários.

---

## Requisitos

- Docker e Docker Compose
- Node.js + npm (v18 ou superior)
- Maven
- Java JDK 17+

---

## Instruções de Execução

 **Nota:** o backend deve estar a correr **antes** de iniciar o frontend.

---

### 1. Backend (Java + WildFly)

1. Abre um terminal e entra no diretório `tastycheck`:

```bash
cd tastycheck
````

2. Compila o projeto com Maven:

```bash
mvn clean install
mvn package
```

3. Cria a imagem Docker:

```bash
docker build -t tastycheck-app .
```

4. Corre o container na porta 8080:

```bash
docker run -p 8080:8080 --name tastycheck tastycheck-app
```

---

### 2. Frontend (Vue.js)

1. Noutro terminal, navega até à raiz do projeto (onde está o `docker-compose.yml`).
2. Sobe os serviços da base de dados com Docker Compose:

```bash
docker-compose up -d
```

3. Entra no diretório do frontend:

```bash
cd frontend/tasty-check
```

4. Instala as dependências:

```bash
npm install
```

5. Inicia a aplicação:

```bash
npm start
```

A aplicação ficará disponível em:
[http://localhost:3000](http://localhost:3000)

---

## Estrutura do Projeto

```
/
├── docker-compose.yml           # Configuração da base de dados
├── tastycheck/                  # Backend Java (WildFly)
├── frontend/
│   └── tasty-check/             # Frontend Vue.js
```

---

## Tecnologias Utilizadas

* **Frontend**: Vue.js, HTML, CSS, JavaScript
* **Backend**: Java, Servlets, Hibernate, JWT, WildFly
* **Base de Dados**: PostgreSQL (via Docker)
* **Infraestrutura**: Docker, Docker Compose, Google Maps API

---

## Licença e Créditos

Este projeto foi desenvolvido no âmbito das unidades curriculares de
**Arquiteturas Aplicacionais** e **Sistemas Interativos Confiáveis**.

Licenciado apenas para uso académico.
© 2025 — Todos os direitos reservados.

```
