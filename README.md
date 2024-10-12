# Tensão Certa 📱⚡

O **Tensão Certa** é um aplicativo intuitivo e eficiente projetado para engenheiros elétricos, estudantes, eletricistas e entusiastas da eletrônica. Ele facilita o cálculo preciso das voltagens primárias e secundárias de transformadores, tornando o design e a verificação de transformadores mais simples e rápidos.

## 📋 Funcionalidades Principais

### 1. Tela Inicial
A tela inicial oferece um **menu principal** com opções para:
- **Calcular o Primário**
- **Calcular o Secundário**
- **Configurações de Ajuste de Voltagem**
- **Histórico de Cálculos**
- **Calculadora de Dados do Núcleo**

### 2. Cálculo do Primário do Carregador
Esta funcionalidade permite calcular o número de voltas necessárias no transformador para atingir uma tensão de saída específica com base na tensão de entrada.

- **Entrada**: 
  - Tensão de saída desejada (Vout)
  - Tensão de entrada (Vin)
- **Cálculo**: Utiliza fórmulas estabelecidas para determinar o número de voltas.
- **Saída**: Exibe o número de voltas necessárias para o primário e o secundário.

### 3. Cálculo de Transformador
Permite ao usuário calcular as voltas necessárias para um transformador baseado em uma tensão primária e uma tensão secundária desejada.

- **Entrada**: 
  - Tensão primária (Vin)
  - Tensão secundária desejada (Vout)
- **Cálculo**: Fórmulas matemáticas específicas para o cálculo de voltas.
- **Saída**: Exibe o número de voltas para o primário e o secundário.

### 4. Configurações de Ajuste de Voltagem
Aqui, o usuário pode ajustar a tensão de saída e calcular automaticamente as voltas necessárias para o transformador, de acordo com as características inseridas.

- **Entrada**: 
  - Tensão de saída desejada
- **Cálculo**: Ajusta automaticamente as voltas com base nas características de entrada.
- **Saída**: Recomendações para ajustes de voltas.

### 5. Calculadora de Dados do Núcleo
Permite ao usuário calcular a eficiência e as perdas de energia do núcleo do transformador com base nas dimensões do núcleo e nos materiais utilizados.

- **Entrada**: 
  - Dimensões do núcleo
  - Material do núcleo
- **Cálculo**: Informações detalhadas sobre perdas e eficiência.
- **Saída**: Sugestões de ajustes e quantidade de ferro necessária.

### 6. Histórico de Cálculos
A seção de histórico permite ao usuário salvar e consultar cálculos anteriores para fácil referência em projetos futuros.

## 🎨 Interface Amigável
O **Tensão Certa** é projetado para ser intuitivo e acessível, com uma interface clara que facilita a navegação:
- Campos de entrada organizados
- Gráficos e visualizações claras dos cálculos
- Simplicidade na exibição dos resultados

## 🔧 Tecnologias Utilizadas

- **Kotlin**: Linguagem principal usada para desenvolver o aplicativo Android.
- **Java**: Utilizado para importar a biblioteca `AboutPage` e outras dependências.
- **Jetpack Navigation**: Gerenciamento de navegação entre fragmentos.
- **Data Binding**: Vinculação de componentes da interface com dados dinâmicos.
- **View Binding**: Simplificação do acesso a views no código.
- **Coroutines**: Tratamento de operações assíncronas sem bloquear a thread principal.
- **LiveData**: Atualização dinâmica de elementos da interface com base em mudanças de dados.
- **Fragments**: Estrutura para dividir a interface do aplicativo em diferentes seções.
- **Snackbar**: Exibição de mensagens de feedback ao usuário.
- **ObjectAnimator e AnimatorSet**: Criação de animações para elementos da UI.
- **RecyclerView**: Exibição de listas dinâmicas com otimização de desempenho.
- **ClipboardManager**: Função para copiar dados para a área de transferência no Android.
- **AdapterHistorico**: Classe customizada para adaptar e exibir o histórico de cálculos em uma lista.
- **Model**: Estrutura de dados que representa o histórico, permitindo a fácil manipulação e exibição de informações.


## Estrutura do Projeto

O aplicativo consiste em quatro fragmentos principais, cada um representando uma seção acessível através da barra de navegação inferior (BottomNavigationView):

- **PrimárioFragment**
- **SecundárioFragment**
- **AjusteFragment**
- **NucleoFragment**

Além disso, o **MainActivity** gerencia o histórico e a navegação entre os fragmentos, implementando animações personalizadas para a transição de telas.

Possui um FloatingActionButton (Botão Flutuante de Ação) para exibir o histórico de dados salvos dos resultados, onde o usuário pode administrar a lista com seus valores das entradas inseridas.

## 🚀 Próximos Recursos
- **Exportação de Cálculos**: Salvar e compartilhar cálculos em formato PDF.
- **Simulações Gráficas**: Exibir simulações gráficas do comportamento do transformador com base nos cálculos.

## 📫 Contato
Se você tiver dúvidas, sugestões ou quiser contribuir para o projeto, sinta-se à vontade para entrar em contato comigo!
