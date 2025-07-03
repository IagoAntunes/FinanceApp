# AppFinancas 💰

Um aplicativo Android moderno para gerenciamento de finanças pessoais, desenvolvido com as melhores práticas de desenvolvimento mobile.

## 📱 Sobre o Aplicativo

O AppFinancas é uma aplicação de controle financeiro que permite aos usuários gerenciar suas finanças pessoais de forma intuitiva e eficiente. O aplicativo foi projetado com foco na experiência do usuário e arquitetura limpa, garantindo escalabilidade e manutenibilidade.

## 🎯 Funcionalidades Atuais

- **Autenticação de Usuário**
  - Login seguro com email e senha
  - Registro de novos usuários
  - Integração com Firebase Authentication

- **Interface Moderna**
  - Design responsivo com Material Design 3
  - Tema personalizado com paleta de cores consistente
  - Componentes UI customizados

## 🛠️ Tecnologias e Ferramentas

### Linguagem
- **Kotlin** - Linguagem principal do projeto

### Framework e UI
- **Jetpack Compose** - Toolkit moderno para construção de UI nativa
- **Material Design 3** - Sistema de design para interfaces consistentes
- **Navigation Compose** - Navegação declarativa entre telas

### Arquitetura e Padrões
- **Clean Architecture** - Separação clara de responsabilidades
- **MVVM (Model-View-ViewModel)** - Padrão arquitetural para UI
- **Repository Pattern** - Abstração da camada de dados
- **Dependency Injection** - Injeção de dependências com Hilt

### Backend e Serviços
- **Firebase Authentication** - Autenticação segura de usuários
- **Firebase Firestore** - Banco de dados NoSQL em tempo real
- **Firebase SDK** - Integração completa com serviços Firebase

### Utilitários e Padrões
- **BaseResult** - Wrapper para operações que podem falhar (Success/Error)
- **StateFlow** - Gerenciamento de estado reativo
- **Sealed Classes** - Tipo seguro para estados e listeners
- **Extension Functions** - Funções de extensão para melhor usabilidade
- **Composables Preview** - Previews para desenvolvimento e testes visuais

### Injeção de Dependências
- **Dagger Hilt** - Framework de injeção de dependências para Android

### Testes
- **JUnit 4** - Framework de testes unitários
- **Espresso** - Testes de UI automatizados
- **Compose Testing** - Testes específicos para Jetpack Compose

## 🏗️ Arquitetura

O projeto segue os princípios da **Clean Architecture**, organizando o código em camadas bem definidas:

```
src/
├── core/                    # Componentes compartilhados
│   ├── components/          # Componentes UI reutilizáveis
│   │   └── CTextField.kt    # Campo de texto customizado
│   ├── routes/              # Definição das rotas de navegação
│   │   └── AppRoutes.kt     # Constantes de rotas
│   ├── ui/
│   │   └── theme/           # Sistema de design
│   │       ├── Color.kt     # Paleta de cores
│   │       ├── Theme.kt     # Configuração do tema
│   │       └── Type.kt      # Tipografia e estilos
│   └── result/              # Classes para tratamento de resultados
│       └── BaseResult.kt    # Wrapper para Success/Error
│
└── features/                # Módulos por funcionalidade
    └── auth/                # Módulo de autenticação
        ├── domain/          # Regras de negócio e interfaces
        │   ├── model/       # Modelos de domínio
        │   ├── repository/  # Interfaces de repositório
        │   └── inject/      # Módulos de injeção
        ├── infra/           # Implementação dos repositórios
        │   ├── repository/  # Implementação dos repositórios
        │   └── service/     # Interfaces de serviços
        ├── presentation/    # ViewModels, States e UI
        │   ├── screen/      # Telas do módulo
        │   ├── viewmodel/   # ViewModels
        │   ├── state/       # Estados da UI
        │   └── listener/    # Listeners de eventos
        └── external/        # Integrações externas
            └── service/     # Implementações Firebase
```

### Camadas da Arquitetura

- **Domain**: Contém as regras de negócio, casos de uso e interfaces
  - Models, Repository interfaces, Dependency injection modules
- **Infrastructure**: Implementa os repositórios e acesso a dados
  - Repository implementations, Service interfaces
- **Presentation**: Gerencia a UI, ViewModels e estados
  - Screens, ViewModels, States, Listeners
- **External**: Integra com APIs e serviços externos
  - Firebase Authentication, Firestore integration

### Fluxo de Dados

```
UI (Composables) → ViewModel → Repository → Service → Firebase
                ←            ←            ←         ←
```

1. **UI** envia ações para o **ViewModel**
2. **ViewModel** processa a lógica e chama o **Repository**
3. **Repository** abstrai o acesso aos dados via **Service**
4. **Service** implementa a comunicação com **Firebase**
5. Resultados retornam através das mesmas camadas usando `BaseResult`

## 🚀 Configuração do Projeto

### Pré-requisitos

- **Android Studio** Flamingo ou superior
- **JDK 11** ou superior
- **SDK Android** nível 24 ou superior
- **Gradle 8.0+**

### Versões Suportadas

- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)
- **Compile SDK**: 35

### Instalação

1. **Clone o repositório**
   ```bash
   git clone https://github.com/IagoAntunes/AppFinancas.git
   cd AppFinancas
   ```

2. **Configurar Firebase**
   - Crie um projeto no [Firebase Console](https://console.firebase.google.com/)
   - Adicione seu app Android ao projeto
   - Configure Authentication:
     - Vá para Authentication > Sign-in method
     - Habilite "Email/Password"
   - Configure Firestore:
     - Vá para Firestore Database
     - Crie o banco de dados
     - Configure as regras de segurança
   - Baixe o arquivo `google-services.json`
   - Coloque o arquivo na pasta `app/`

3. **Configurar Firestore Rules**
   ```javascript
   rules_version = '2';
   service cloud.firestore {
     match /databases/{database}/documents {
       match /users/{userId} {
         allow read, write: if request.auth != null && request.auth.uid == userId;
       }
     }
   }
   ```

4. **Compilar o projeto**
   ```bash
   ./gradlew build
   ```

5. **Executar testes**
   ```bash
   ./gradlew test
   ./gradlew connectedAndroidTest
   ```

### Solução de Problemas Comuns

#### Problemas de Build
- **Erro de versão do Gradle**: Verifique se está usando Android Studio Flamingo ou superior
- **Problemas com Firebase**: Certifique-se de que o arquivo `google-services.json` está na pasta `app/`
- **Dependências não resolvidas**: Execute `./gradlew clean` e depois `./gradlew build`

#### Problemas de Execução
- **Crash no login**: Verifique se o Firebase Auth está configurado corretamente
- **Erro de rede**: Certifique-se de que o dispositivo tem acesso à internet
- **Problemas de UI**: Verifique se o tema e as cores estão sendo aplicados corretamente

## 📦 Dependências Principais

### Core Dependencies
```kotlin
// Jetpack Compose
implementation("androidx.compose.ui:ui:2024.09.00")
implementation("androidx.compose.material3:material3")
implementation("androidx.activity:activity-compose:1.10.1")

// Navigation
implementation("androidx.navigation:navigation-compose:2.9.0")

// Dependency Injection
implementation("com.google.dagger:hilt-android:2.56.2")
implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

// Firebase
implementation("com.google.firebase:firebase-auth-ktx")
implementation("com.google.firebase:firebase-firestore-ktx")
```

## 🎨 Design System

### Cores Principais
- **Primary**: Magenta (#DA4BDD)
- **Secondary**: Red (#D93A4A)
- **Tertiary**: Green (#1FA342)
- **Background**: Gray scales
  - Gray100: #F9FBF9
  - Gray200: #EFF0EF
  - Gray300: #E5E6E5
  - Gray400: #A1A2A1
  - Gray500: #676767
  - Gray600: #494A49
  - Gray700: #0F0F0F

### Tipografia
- **Font Family**: Lato (Regular, Bold, Black)
- **Escalas**: Title (XS, SM, MD, LG), Text (XS, SM), Button (SM, MD), Input
- **Assets**: Fontes customizadas incluídas no projeto

### Recursos Visuais
- **Logo**: Versões light e dark do logo
- **Ícones**: Conjunto de ícones vetoriais customizados
- **Imagens**: Head logo para tela de login
- **Fonte**: Família Lato completa (Regular, Bold, Black)

### Componentes Customizados
- `CTextField` - Campo de texto personalizado com bordas dinâmicas
- `BaseResult` - Classe sealed para tratamento de resultados (Success/Error)
- Theme customizado com `AppFinancasTheme`
- Tipografia escalável com `appTypography`
- Sistema de cores consistente e acessível

## 🧪 Testes

O projeto inclui diferentes tipos de testes:

### Testes Unitários
```bash
./gradlew test
```

### Testes de Instrumentação
```bash
./gradlew connectedAndroidTest
```

### Cobertura de Testes
- Testes de ViewModels
- Testes de repositórios
- Testes de UI com Compose

## 🔧 Configuração de Desenvolvimento

### Gradle Properties
```properties
android.useAndroidX=true
kotlin.code.style=official
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
```

### Plugins Utilizados
- `com.android.application`
- `org.jetbrains.kotlin.android`
- `org.jetbrains.kotlin.plugin.compose`
- `com.google.dagger.hilt.android`
- `com.google.devtools.ksp`
- `com.google.gms.google-services`

## 📱 Screens Disponíveis

### 🔐 Autenticação
- **LoginScreen**: Tela de login com validação de email/senha
- **RegisterScreen**: Tela de registro com validação de campos
- **Firebase Auth**: Integração completa com autenticação Firebase
- **User Management**: Criação e gerenciamento de perfis de usuário no Firestore

### 🔥 Integração Firebase
- **Authentication**: Login/Registro com email e senha
- **Firestore**: Armazenamento de dados de usuário
- **Real-time Updates**: Sincronização em tempo real (preparado para futuras features)
- **Offline Support**: Suporte offline nativo do Firebase (futuro)

### 🎨 Recursos de UI
- Navegação fluida entre telas
- Feedback visual para estados de loading
- Validação de formulários em tempo real
- Suporte a dark theme (futuro)

### 🔄 Estado Atual do Projeto
- ✅ **Base do projeto configurada** - Arquitetura limpa implementada
- ✅ **Autenticação funcional** - Login e registro com Firebase
- ✅ **UI/UX base** - Telas principais com design system
- ✅ **Navegação** - Fluxo entre telas implementado
- ✅ **Injeção de dependências** - Hilt configurado
- ✅ **Tratamento de erros** - BaseResult para operações
- 🔄 **Dashboard** - Em desenvolvimento
- 🔄 **Funcionalidades financeiras** - Próximas features

## 🚀 Roadmap

### Próximas Funcionalidades
- [ ] Dashboard financeiro
- [ ] Categorização de gastos
- [ ] Relatórios e gráficos
- [ ] Sincronização offline
- [ ] Notificações push
- [ ] Backup automático
- [ ] Suporte a múltiplas moedas

### Melhorias Técnicas
- [ ] Implementação de testes automatizados
- [ ] CI/CD pipeline
- [ ] Análise de código estática
- [ ] Otimização de performance
- [ ] Suporte a tablets

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

### Padrões de Código
- Siga as convenções do Kotlin
- Use ktlint para formatação
- Documente funções públicas
- Mantenha as classes pequenas e focadas
- Teste suas implementações

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 👤 Autor

**Iago Antunes** - [@IagoAntunes](https://github.com/IagoAntunes)

---

⭐ Se este projeto te ajudou, considera dar uma estrela no repositório!