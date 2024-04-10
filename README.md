# Aplicativo Bellus

## Descrição
É um aplicativo de beleza que oferece serviços de manicure, pedicure, depilação, maquiagem, massagem, entre outros.
O aplicativo é voltado para o público geral e tem como objetivo facilitar o agendamento de serviços de beleza.

## Levantamento de requisitos
### Requisitos Funcionais
- Cadastro de usuário
- Cadastro de profissional
- Agendamento de serviços
- Avaliação de profissional
- Avaliação de serviço

### Requisitos Não Funcionais
- Linguagem de programação **Kotlin**
- Banco de dados NoSQL (Firebase)
- Autenticação de usuário (Firebase Auth)
- Autenticação de profissional (Firebase Auth)
- Armazenamento de imagens (Firebase Storage)
- Armazenamento de dados (Firestore)
- Notificações (Firebase Cloud Messaging)
- Mapa (Google Maps API)
- Chat (Firebase Realtime Database)
- Testes unitários

### Restrições do sistema
- Apenas Android

## Arquitetura
#### O aplicativo foi desenvolvido utilizando as boas práticas da **Arquitetura Limpa (Clean Architecture)** de *Robert C. Martin*.
#### O aplicativo foi dividido em camadas, sendo eles:
- **base**: Essa camada contém classes e interfaces base que serão utilizadas em todo o projeto.
- dominio: Essa camada contém as regras de négocio da aplicação e os casos de uso do aplicativo.
  - **Casos de uso** (*UseCase*): São operações *específicas* que o sistema pode realizar em resposta a uma intereção do usuário.
    - Usuário seleciona a opção para agendar um serviço -> Sistema apresenta uma lista de serviços disponíveis.
    - Usuário seleciona um serviço da lista -> Sistema apresenta uma lista de profissionais disponíveis.
    - Usuário seleciona um profissional da lista -> Sistema apresenta uma lista de horários disponíveis.
    - Assim por diante...
  - **Entidades**: São objetos de négocios que o sistema manipula. No aplicativo *Bellus*, temos as seguintes entidades:
    - Usuário
    - Profissional
    - Serviço
    - Agendamento
    - Avaliação
    - Entre outros...
- **data**: Responsável por lidar com todas as operações de dados, sendo a coleta dos dados a partir das APIs do firebase e por seguinte a manipulação dos dados dentro da aplicação.
  - **Datasource**: Responsável por lidar diretamente com a obtenção de dados de uma fonte específica, nesse caso *FirebaseAuth* para autenticar usuários. Datasources podem interagir com qualquer tipo de fonte de dados: banco de dados local, api, etc.
  - **Repository**: Usa os datasources para fornecer dados para o resto da aplicação. É responsável por decidir qual datasource obter os dados e como armazenar em cache. É a camada de abstração entre as fontes de dados e o restante da aplicação, possibilitando que mude a fonte de dados sem afetar o restante de código.
- **navigation**: Essa camada contém apenas a navegação entre as diferentes telas do aplicativo utilizando compose dispensando a criação de várias activities, ou seja, economiza muito código.
- **telas**: É a camada de apresentação do modelo Clean Architecture, onde estão as telas do aplicativo.
- **di**: Injeção de Depedência (Dependency Injection)
- **ui**: Responsável pelos componentes de interface do usuário. São como blocos que a gente monta separado e depois junta tudo em uma tela.
- **util**: Contém apenas utilitários comuns.
  
### Padrões de projeto
#### Com base na arquitetura, os principais padrões de projeto foram utilizados:
- **MVVM (Model-View-ViewModel)**: Padrão de projeto que separa a lógica da interface do usuário da lógica de negócios:
  - A View solicita dados ao ViewModel.
  - O ViewModel obtém os dados do Model.
  - O ViewModel processa os dados para que possam ser facilmente consumidos pela View.
  - O ViewModel retorna os dados processados para a View.
  - A View exibe os dados ao usuário.
- **Repository**: Padrão de projeto que abstrai a fonte de dados, permitindo que a aplicação trabalhe com múltiplas fontes de dados e troque entre elas.
- **Observer**: Padrão de projeto que define uma dependência um-para-muitos entre objetos, de maneira que quando um objeto muda de estado, todos os seus dependentes são notificados e atualizados automaticamente.
- **Singleton**: Padrão de projeto que garante que uma classe tenha apenas uma instância e fornece um ponto de acesso global para essa instância.
- **Factory**: Padrão de projeto que define uma interface para criar um objeto, mas permite que as subclasses alterem o tipo de objetos que serão criados.
