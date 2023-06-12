# Projeto de Teoria dos Grafos

Este projeto foi desenvolvido como trabalho prático da disciplina de Teoria dos Grafos. Ele consiste em uma aplicação que utiliza conceitos de grafos para realizar diversas operações, como exibir grafos, calcular distâncias entre cidades e encontrar rotas mais curtas. A documentação oficial do projeto em formato de Artigo SBC pode ser encontrada na pasta documentação.

## Tecnologias Utilizadas

O projeto foi implementado utilizando as seguintes tecnologias:

- **Java**: Linguagem de programação utilizada para o desenvolvimento do back-end da aplicação.
- **Spring Boot**: Framework em Java utilizado para criar APIs que são consumidas pelo front-end.
- **Next.js**: Framework em JavaScript utilizado para o desenvolvimento do front-end da aplicação, baseado em React.
- **Vis.js**: Biblioteca JavaScript focada na exibição de grafos e gráficos de diversos tipos.

## Classes Utilizadas

No desenvolvimento do projeto, foram utilizadas as seguintes classes em Java para a estruturação e manipulação dos dados:

- **Aresta**: Classe que representa uma aresta do grafo.
- **Vértice**: Classe que representa um vértice do grafo.
- **Cidade**: Classe que representa uma cidade.
- **Grafo**: Classe que representa o grafo em si.
- **Haversine**: Classe que implementa o cálculo da distância entre duas coordenadas geográficas utilizando a fórmula de Haversine.

Além dessas, existem as classes responsáveis pelo acesso e estruturação dos dados:

- **CidadeLoader**: Classe responsável por carregar as cidades a partir de um arquivo CSV.
- **GrafoController**: Classe que define os endpoints da API e controla as requisições.
- **LeitorCsv**: Classe utilizada para ler um arquivo CSV.
- **ABB**: Classe que implementa uma árvore binária de busca.
- **Lista**: Classe que implementa uma lista encadeada.

Por fim, temos as classes **ArestaWrapper**, **VérticeWrapper** e **GrafoWrapper**, que são utilizadas para a estruturação dos dados retornados pela API.

## Equipe do Projeto de Teoria dos Grafos
- Bruno Pontes Duarte
- Davi Fernandes Ferreira Silva
- Diogo Martins de Assis
- Eduardo Augusto Brito
- Marcos Paulo Freitas da Silva
- Samuel Marques Sousa Leal
