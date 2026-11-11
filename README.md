# 🛸 Rick and Morty Finder App

Uma aplicação Android moderna desenvolvida em **Kotlin** com **Jetpack Compose** para exploração dos personagens do universo de Rick and Morty. O aplicativo inclui autenticação local com persistência de dados, busca integrada com a API oficial de Rick and Morty, sistema de favoritos isolado por usuário e interface estilizada em tons escuros e azuis.

---

## 📌 Sumário
- [Funcionalidades](#-funcionalidades)
- [Arquitetura e Tecnologias](#-arquitetura-e-tecnologias)
- [Design System](#-design-system)
- [Checklist de Validação](#-checklist-de-validação)
- [Como Executar o Projeto](#-como-executar-o-projeto)

---

## 🚀 Funcionalidades

- **Autenticação Local:** Tela de cadastro e login salvos localmente via Room Database com armazenamento seguro de senha (hash).
- **Persistência de Sessão:** O estado de login é mantido mesmo ao fechar e reabrir o app.
- **Isolamento de Dados:** Cada conta de usuário enxerga apenas a sua própria lista de favoritos.
- **Busca na API Rick and Morty:** Pesquisa de personagens por nome ou ID com tratamento visual de estados (`Loading`, `Error` e `Success`).
- **Gerenciamento de Favoritos:** Marcar/desmarcar personagens como favoritos sincronizados em tempo real no banco SQLite local.
- **Navegação Type-Safe:** Navegação desacoplada e tipada entre as telas utilizando `Navigation Compose` (versão 2.8+).
- **Tela de Detalhes:** Visualização completa das estatísticas, origem, status e imagem em alta resolução de cada personagem.

---

## 🛠️ Arquitetura e Tecnologias

O projeto segue os princípios de **Clean Architecture** e o padrão de arquitetura **MVVM (Model-View-ViewModel)**.

```text
com.example.apppersonagemrickmorty
├── database/        # Room Database, DAOs (UsuarioDao, PersonagemDao) e Entities
├── model/           # Modelos de dados da API e Sealed Interfaces de Navegação
├── network/         # Configuração do Retrofit e interface da API
├── repository/      # Intermediadores entre fontes de dados (Room e Retrofit)
├── userinterface/   # Telas Compose (Splash, Login, Cadastro, Home, Favoritos, Detalhes)
└── viewmodel/       # ViewModels gerenciadores de estado (LoginViewModel, PersonagemViewModel)
```

### Principais Bibliotecas Utilizadas
- **UI & Layout:** Jetpack Compose & Material 3
- **Navegação:** Type-Safe Navigation Compose (`kotlinx.serialization`)
- **Carregamento de Imagens:** Coil Compose (`io.coil-kt:coil-compose`)
- **Rede / API:** Retrofit 2 + Gson Converter
- **Banco de Dados Local:** Room Database + KSP (Kotlin Symbol Processing)
- **Concorrência:** Kotlin Coroutines & Flow (`StateFlow`)

---

## 🎨 Design System

A interface do aplicativo segue um padrão visual escuro focado no tema do universo Rick and Morty:

| Elemento | Código da Cor | Descrição |
| :--- | :--- | :--- |
| **Fundo Principal** | `#1E1E1E` | Tom escuro profundo para telas e fundos |
| **Contêiner Externo** | `#E2ECF0` | Card externo em azul bem claro |
| **Contêiner Interno** | `#CCDCE3` | Card interno em azul suave para destaque |
| **Botões Primários** | `#5A758D` | Azul acinzentado com cantos arredondados (estilo pílula) |
| **Destaque Portal** | `#97CE4C` | Verde característico da série utilizado na Splash Screen |

---

## 🧪 Checklist de Validação

- [x] **Estabilidade:** O app executa no emulador sem travar, permitindo navegar livremente entre todas as telas.
- [x] **Integração de API:** Exibição dinâmica dos dados da API com indicadores claros de carregamento e mensagem de erro em caso de falha.
- [x] **Segurança e Login:** Fluxo completo de cadastro e login. Validação de senha incorreta e persistência do hash da senha no banco de dados.
- [x] **Persistência de Estado:** Sessão do usuário e dados salvos no Room são mantidos ao fechar e reabrir a aplicação.
- [x] **Isolamento de Dados:** Sair da conta direciona para o login, e uma nova conta não tem acesso aos favoritos salvos pela conta anterior.
- [x] **Navegação de Detalhes:** Clique em qualquer personagem abre a tela de detalhes correspondente ao ID selecionado.
- [x] **Tratamento de Regras:** Buscas sem correspondência tratam resultados vazios adequadamente.
- [x] **Qualidade do Código:** Arquitetura desacoplada com divisão clara de responsabilidades por camadas.

---

## 💻 Como Executar o Projeto

### Pré-requisitos
- **Android Studio:** Ladybug (2024.2.1) ou superior
- **Gradle JDK:** Versão 17 ou 21
- **Android SDK:** Compile SDK 35 / Min SDK 24

### Passo a Passo

1. **Clonar o Repositório:**
   ```bash
   git clone https://github.com/seu-usuario/app-personagem-rick-morty.git
   ```

2. **Abrir no Android Studio:**
   - Abra o Android Studio e selecione **Open Project**.
   - Navegue até a pasta onde o repositório foi clonado.

3. **Configurar o Gradle JDK:**
   - Vá em `Settings` (ou `Preferences`) > `Build, Execution, Deployment` > `Build Tools` > `Gradle`.
   - Certifique-se de que o **Gradle JDK** esteja definido para a versão **JDK 17** ou **Embedded JDK**.

4. **Sincronizar e Rodar:**
   - Clique no ícone de elefante (**Sync Project with Gradle Files**).
   - Selecione um emulador Android ou dispositivo físico e clique em **Run 'app'** (`Shift + F10`).
