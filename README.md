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
├── core/
│   ├── components/     # Componentes UI reutilizáveis
│   ├── routes/         # Definição das rotas de navegação
│   ├── ui/
│   │   └── theme/      # Tema, cores e tipografia
│   └── result/         # Classes para tratamento de resultados
│
└── features/
    └── auth/           # Módulo de autenticação
        ├── domain/     # Regras de negócio e interfaces
        ├── infra/      # Implementação dos repositórios
        ├── presentation/ # ViewModels, States e UI
        └── external/   # Integrações externas
```

### Camadas da Arquitetura

- **Domain**: Contém as regras de negócio, casos de uso e interfaces
- **Infrastructure**: Implementa os repositórios e acesso a dados
- **Presentation**: Gerencia a UI, ViewModels e estados
- **External**: Integra com APIs e serviços externos

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
   - Baixe o arquivo `google-services.json`
   - Coloque o arquivo na pasta `app/`

3. **Compilar o projeto**
   ```bash
   ./gradlew build
   ```

4. **Executar testes**
   ```bash
   ./gradlew test
   ./gradlew connectedAndroidTest
   ```

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

### 🎨 Recursos de UI
- Navegação fluida entre telas
- Feedback visual para estados de loading
- Validação de formulários em tempo real
- Suporte a dark theme (futuro)

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