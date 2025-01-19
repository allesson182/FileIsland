# FileIsland

FileIsland é um projeto de backend desenvolvido em Java 17 com Spring Boot. Ele fornece APIs para realizar upload e download de arquivos utilizando o MinIO como solução de armazenamento. O projeto utiliza o SDK da Amazon para integração com o MinIO.

## Funcionalidades

- **Upload de arquivos**: Envia arquivos para o armazenamento MinIO.
- **Download de arquivos**: Recupera arquivos armazenados no MinIO.
- **Gerenciamento de arquivos**: Exemplos de operações adicionais (se aplicável, como exclusão ou listagem de arquivos).

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação principal.
- **Spring Boot**: Framework para facilitar o desenvolvimento e a configuração.
- **MinIO**: Solução de armazenamento de objetos compatível com o protocolo S3.
- **Amazon SDK**: Biblioteca para comunicação com o MinIO.

## Como Configurar

### Pré-requisitos

- **Java 17** instalado.
- **MinIO** configurado e em execução.
- **Docker (opcional)**: Caso deseje rodar o MinIO em um container.
- Dependências gerenciadas com **Maven**.

### Configuração do MinIO

Certifique-se de que o MinIO está rodando e que as credenciais estão disponíveis. Você pode configurar um container MinIO utilizando o Docker:


docker run -p 9000:9000 -p 9001:9001 --name minio \
  -e "MINIO_ROOT_USER=seu_usuario" \
  -e "MINIO_ROOT_PASSWORD=sua_senha" \
  quay.io/minio/minio server /data --console-address ":9001" 
## Configuração do Projeto

- **git clone** https://github.com/allesson182/FileIsland.git
- **cd** FileIsland

### Endpoints

    Upload de Arquivos
        POST /api/files/upload
        Corpo da requisição: Multipart file.

    Download de Arquivos
        GET /api/files/download/{nomeArquivo}
        Parâmetro: nomeArquivo - Nome do arquivo a ser baixado.

## Contribuição

Sinta-se à vontade para abrir issues e enviar pull requests para melhorar este projeto.
Licença

Este projeto está licenciado sob a MIT License.
