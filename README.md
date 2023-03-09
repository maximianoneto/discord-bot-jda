# Bot para Discord Utilizando Java e Spring Boot com a biblioteca JDA
Este é um bot para Discord desenvolvido utilizando Java e Spring Boot, fazendo uso da biblioteca JDA para a interação com a API do Discord. O bot é capaz de executar várias tarefas úteis, como tocar música, exibir imagens e informações, além de permitir a comunicação entre usuários do servidor.

Como executar o projeto localmente
Para executar o projeto localmente, siga as instruções abaixo:

Clone o repositório em sua máquina local:

git clone https://github.com/maximianoneto/discord-bot-jda

Abra o projeto em sua IDE preferida, como o Eclipse ou o IntelliJ.

# Configure suas credenciais no arquivo .env

Seguindo o Exemplo abaixo

TOKEN="Insira o Token gerado pela API do Discord"

OWNER_ID="Seu ID do Discord"

PREFIX="Estou utilizando "$", porém sinta se a vontade pra usar outro caractér"

AUTHOR="SEU NOME"

API_KEY_YOUTUBE= "API KEY do Youtube"

# Execute o seguinte comando no terminal para iniciar a aplicação:

./mvnw spring-boot:run

Acesse o Discord e adicione o bot ao seu servidor.

Use os comandos disponíveis para interagir com o bot.

# Tecnologias Utilizadas

Java

Spring Boot

JDA

Jackson

Google Auth

LavaPlayer

Dotenv

JUnit

# Comandos disponíveis
O bot é capaz de executar as seguintes tarefas:

Tocar música;

Exibir imagens;

Exibir informações;

Permitir a comunicação entre usuários do servidor.

Aqui estão alguns dos principais comandos disponíveis:

```
$play: Toca uma música passando a URL do youtube.

$randommusic: Toca uma música aleatória/playlist do youtube.

$join: Faz o bot entrar no canal de voz;

$shutdown: Desliga o bot;

$ping: Mostra a latência do bot;

$whisky: Exibe uma imagem de whisky;

$esculachar: O bot responderá com uma série de insultos aleatórios

$meme: O bot responderá com um meme aleatório

$help: Exibe os comandos disponíveis.

```
# Contribuindo
Sinta-se à vontade para contribuir com o projeto! 

Basta enviar um pull request com suas alterações e iremos avaliar e incorporar suas contribuições ao código.

Envie para o branch: git push origin/my-feature.

# Licença
Este é um projeto open source, portanto sinta a vontade para fazer alterações ou rodar localmente.
