## Licença

Copyright (c) 2024 Wanderson Brito. 

Todos os direitos reservados.

Este software é fornecido apenas para uso pessoal e não pode ser utilizado para fins comerciais ou redistribuído sem permissão explícita do autor.

# Tensão Certa 📱⚡

O **Tensão Certa** é um aplicativo intuitivo e eficiente projetado para engenheiros elétricos, estudantes, eletricistas e entusiastas da eletrônica. Ele facilita o cálculo preciso das voltagens primárias e secundárias de transformadores, tornando o design e a verificação de transformadores mais simples e rápidos.

## 📋 Funcionalidades Principais

### 1. Tela Inicial
A tela inicial oferece um **menu principal** com opções para:
- **Calcular o Primário e Secundário**
- **Configurações de Ajuste de Voltagem**
- **Histórico de Cálculos**
- **Informações Gerais da Aplicação e Fórmulas**

### 2. Cálculo do Transformador
Esta funcionalidade permite calcular o número de voltas necessárias e muito mais no transformador para atingir uma tensão de saída específica com base na tensão de entrada.

- **Entrada**:
  - Base do núcleo `(cm)`
  - Altura do núcleo `(cm)`
  - Tensão do primário `(Vin)`
  - Tensão do secundário `(Vout)`
  - Escolha do material `(Ferrite ou Silício)`
- **Cálculo**: Utiliza fórmulas estabelecidas para determinar os resultados.
- **Saída**: Apresenta a área do núcleo, a potência do transformador, as correntes no primário e no secundário, a seleção dos condutores para ambas as seções, o número de voltas requeridas para o primário e o secundário, além de uma estimativa de eficiência.

### 3. Configurações de Ajuste de Voltagem
Aqui, o usuário pode ajustar a tensão de saída e calcular automaticamente as voltas necessárias para o transformador, de acordo com as características inseridas.

- **Entrada**: 
  - Tensão de saída desejada
- **Cálculo**: Ajusta automaticamente as voltas com base nas características de entrada.
- **Saída**: Recomendações para ajustes de voltas.

### 4. Histórico de Cálculos
A seção de histórico permite ao usuário salvar e consultar cálculos anteriores para fácil referência em projetos futuros.

### 5. Informações Gerais
Nesta seção, o usuário pode acessar detalhes relevantes sobre a aplicação, incluindo:
- **Objetivo do Aplicativo**: Explicação sobre a finalidade do aplicativo e suas funcionalidades.
- **Fórmulas Utilizadas**: Apresentação das fórmulas aplicadas nos cálculos, com breves descrições.
- **Notas de Uso**: Dicas e orientações para uma melhor experiência ao utilizar o aplicativo.

## 🎨 Interface Amigável
O **Tensão Certa** é projetado para ser intuitivo e acessível, com uma interface clara que facilita a navegação:
- Campos de entrada organizados
- Gráficos e visualizações claras dos cálculos
- Simplicidade na exibição dos resultados

## 🔧 Tecnologias Utilizadas

- **Kotlin**: Linguagem principal usada para desenvolver o aplicativo Android.
- **Java**: Utilizado para importar a biblioteca [AboutPage](https://github.com/medyo/android-about-page) e as funcionalidades de informações.
- **XML**: Para construção dos layouts.
- **Jetpack Navigation**: Gerenciamento de navegação entre fragmentos.
- **Data Binding**: Vinculação de componentes da interface com dados dinâmicos.
- **View Binding**: Simplificação do acesso a views no código.
- **Coroutines**: Tratamento de operações assíncronas sem bloquear a thread principal.
- **LiveData**: Atualização dinâmica de elementos da interface com base em mudanças de dados.
- **Fragments**: Estrutura para dividir a interface do aplicativo em diferentes seções.
- **Activity para Tela "Sobre"**: Uma **Activity** é utilizada para exibir informações sobre o aplicativo, proporcionando uma transição suave ao navegar de uma tela para outra.
- **Snackbar**: Exibição de mensagens de feedback ao usuário.
- **ObjectAnimator e AnimatorSet**: Criação de animações para elementos da UI.
- **RecyclerView**: Exibição de listas dinâmicas com otimização de desempenho.
- **ClipboardManager**: Função para copiar dados para a área de transferência no Android.
- **AdapterHistorico**: Classe customizada para adaptar e exibir o histórico de cálculos em uma lista.
- **Model**: Estrutura de dados que representa o histórico, permitindo a fácil manipulação e exibição de informações.
- **Interfaces**: Utilização de interfaces para abstrair a funcionalidade de salvar históricos. Por exemplo, a interface `OnHistoricoListener` é usada para adicionar novos itens ao histórico.


## Estrutura do Projeto

O aplicativo consiste em quatro fragmentos principais, cada um representando uma seção acessível através da barra de navegação inferior (BottomNavigationView):

- **CalculoFragment**
- **AjusteFragment**
- **HistoricoFragment**
- **InfoFragment**

Além disso, o **MainActivity** gerencia o histórico e a navegação entre os fragmentos, implementando animações personalizadas para a transição de telas.

## 📫 Contato
Se você tiver dúvidas, sugestões ou quiser contribuir para o projeto, sinta-se à vontade para entrar em contato comigo!


## 📦 Instalação do APK
### 🚀 Como Rodar o Projeto?

Você pode instalar o aplicativo diretamente no seu dispositivo Android sem a necessidade de ter acesso ao Android Studio. Siga os passos abaixo:

1. **Baixe o APK**:
   - [Baixe aqui!](https://drive.google.com/file/d/1a_xiR4lf4aT-br_1UZ0TzkeHVrbNdcho/view?usp=sharing)

2. **Permita a instalação de aplicativos de fontes desconhecidas**:
   - Ative a opção **Instalar aplicativos de fontes desconhecidas**. Se você estiver usando o Android 8.0 (Oreo) ou superior, será necessário permitir a instalação especificamente para o navegador ou gerenciador de arquivos que você usará para instalar o APK.

3. **Instale o APK**:
   - Localize o arquivo APK que você baixou, geralmente na pasta **Downloads**.
   - Toque no arquivo APK para iniciar a instalação.
   - Se aparecer uma mensagem de aviso de segurança do Google, não se preocupe! O aplicativo foi verificado e não contém vírus.

4. **Conclua a instalação**:
   - Siga as instruções na tela para concluir a instalação.
