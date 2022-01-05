import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ProjetoFinal {
    static final String nomeFicheiro = "APROG Java Exercícios/Java Projeto/src/inputProjeto.txt";
/*  (INSTRUÇÃO IMPORTANTE PARA FUNCIONAMENTO DO CÓDIGO)
    necessário criar um ficheiro de texto
    trocar o valor dentro de aspas para a localização do ficheiro de texto
    exemplo: no meu caso, a localização é "IdeaProjects/APROG Java Exercícios/Java Projeto/src/inputProjeto.txt"
*/

    public static void main(String[] args) throws FileNotFoundException {
        //criação do array da alínea a
        int[][] arrayInicial;

        //armazenamento do array da alínea a
        arrayInicial = guardarInformaçãoTerreno();

        //imprimir terreno armazenado na alínea a
        System.out.println("b)");
        imprimirTerreno(arrayInicial);

        //array da alínea c
        int[][] arrayComElevacao;

        //imprimir terreno guardado mas com a alteração definida
        arrayComElevacao = novoTerrenoComAlteração(arrayInicial);
        System.out.println("c)");
        imprimirTerreno(arrayComElevacao);

        //imprimir percentagem da área submersa do terreno com alteração
        System.out.println("d)");
        percentagemDaAreaSubmersaDoTerrenoComAlteracao(arrayComElevacao);

        //imprimir a variação da área inundada entre o terreno alterado, e o inicial
        int variacao = variacaoAreaInundada(arrayInicial, arrayComElevacao);
        System.out.println("e)");
        System.out.println("variacao da area inundada: " + variacao + " m2");

        //imprimir volume da agua
        System.out.println("f)");
        volumeAgua(arrayComElevacao);

        //necessário para alínea g
        int maiorAltura;

        //g)
        System.out.println("g)");
        maiorAltura=calculoInundacaoTotal(arrayComElevacao) ;

        //h)
        System.out.println("h)");
        incrementoAreaInundada(arrayComElevacao, maiorAltura);

        //i)
        System.out.println("i)");
        determinarLocalCubo(arrayComElevacao);

        //necessário para alínea j
        int caminho;

        //j)
        System.out.println("j)");
        caminho=caminhoMaisEste(arrayComElevacao);
        printInfo(caminho);
    }

    //alínea a--------------------------------------------------------------------------
    public static int[][] guardarInformaçãoTerreno() throws FileNotFoundException {
        // criação de scanner que vai ler o ficheiro de texto criado
        Scanner ler = new Scanner(new File("APROG Java Exercícios/Java Projeto/src/inputProjeto.txt"));

        //ignorar a linha de texto em cima dos dados necessários (exemplo: "Parque urbano da Asprela")
        ler.nextLine();
        int qtdLinhas = ler.nextInt();
        int qtdColunas = ler.nextInt();

        //criação do Array para armazenar os dados necessários, com as dimensões dadas nas linhas anteriores
        int[][] arrayInicial = new int[qtdLinhas][qtdColunas];
        for (int linha = 0; linha < qtdLinhas; linha++) {
            for (int coluna = 0; coluna < qtdColunas; coluna++) {
                arrayInicial[linha][coluna] = ler.nextInt();
            }
        }

        return arrayInicial;
    }

    //alínea b (e auxilio para c) ------------------------------------------------------------------
    public static void imprimirTerreno(int[][] arrayInicial) {
        for (int linha = 0; linha < arrayInicial.length; linha++) {
            for (int coluna = 0; coluna < arrayInicial[0].length; coluna++) {
                System.out.printf("%3d ", arrayInicial[linha][coluna]);
                /*
                vai apresentar a informação armazenada em cada linha e coluna do arrayInicial, com 3 espaços reservados
                 um dos espaços reservados vai ser para o símbolo negativo, e os outros dois vão ser para os dígitos
                 (pode armazenar até no máximo numeros de 2 digitos)
               */
            }
            System.out.println();
            //vai passar para a linha de baixo, sempre que acabar de escrever a linha
        }
    }

    //alínea c------------------------------------------------------------------------
    public static int[][] novoTerrenoComAlteração(int[][] arrayInicial) {
        //criação de constante com valor da alteração do nível da água
        final int ALTERACAO = 1;

        //criação de novo array, com a alteração definida
        int[][] arrayAux = new int[arrayInicial.length][arrayInicial[0].length];


        for (int linha = 0; linha < arrayInicial.length; linha++) {
            for (int coluna = 0; coluna < arrayInicial[0].length; coluna++) {

                arrayAux[linha][coluna] = arrayInicial[linha][coluna] + ALTERACAO;
                //vai adicionar 1 a cada valor da matriz

            }

        }
        return arrayAux;
    }

    //alínea d---------------------------------------------------------------------------
    public static void percentagemDaAreaSubmersaDoTerrenoComAlteracao(int[][] arrayInicial) {
        final double qtdAreasDoTerreno = arrayInicial.length * arrayInicial[0].length;
        //constante da quantidade total de áreas do terreno

        double qtdSubmersos = 0;

        for (int linha = 0; linha < arrayInicial.length; linha++) {
            for (int coluna = 0; coluna < arrayInicial[0].length; coluna++) {
                if (arrayInicial[linha][coluna] < 0) {
                    qtdSubmersos++;
                /* se uma área estiver submersa, ou seja, se um valor fôr negativo, o contador de
                areas submersas vai aumentar de 1 */
                }
            }
        }
        //imprimir percentagem de areas submersas sobre o total de areas com 2 casas decimais, saltando uma linha no final
        System.out.printf("area submersa: %.2f%%\n", (qtdSubmersos / qtdAreasDoTerreno) * 100);
    }

    //alínea e----------------------------------------------------------------------------
    public static int variacaoAreaInundada(int[][] arrayInicial, int[][] arrayComElevacao) {
        int variacao;

        //contador de areas submersas no terreno inicial
        int qtdSubmersosArrayInicial = 0;

        //contador de areas submersas no terreno após alteração
        int qtdSubmersosArrayComElevacao = 0;


        for (int linha = 0; linha < arrayInicial.length; linha++) {
            for (int coluna = 0; coluna < arrayInicial[0].length; coluna++) {

                if (arrayInicial[linha][coluna] < 0) {
                    qtdSubmersosArrayInicial++;
                }
                // se o valor for negativo, a area inicial vai estar inunada, e vai aumentar o contador


                if (arrayComElevacao[linha][coluna] < 0) {
                    qtdSubmersosArrayComElevacao++;
                }
                // se o valor for negativo, a area depois de sofrer alteração vai estar inunada, e vai aumentar o contador

            }
        }

        variacao =qtdSubmersosArrayComElevacao - qtdSubmersosArrayInicial;
        //vai imprimir a variação das areas inundadas do terreno após alteração, em relação ao terreno inicial

        return variacao;
    }

    //alínea f-----------------------------------------------------------------------------
    public static void volumeAgua(int[][] arrayInicial) {
        int somaVolume = 0;
        //soma volume necessario para que todas os espaços da matriz estejam igual a 0 caso estejam submersos
        for (int linha = 0; linha < arrayInicial.length; linha++) {
            for (int coluna = 0; coluna < arrayInicial[0].length; coluna++) {
                //percorrer todos os espacos da matriz
                if (arrayInicial[linha][coluna] < 0) {
                    somaVolume = somaVolume + Math.abs(arrayInicial[linha][coluna]);
                    //somar o número no espaço da matriz, caso este seja negativo, ate que seja igual a 1 e somar as respetivas unidades ao contador de volume
                }
            }
        }
        System.out.println("volume de agua: " + somaVolume + " m3");
    }

    //alínea g-----------------------------------------------------------------------------------
    public static int calculoInundacaoTotal(int[][] ArrayComElevacao) {
        int maiorAltura = 0;
        //variavel para armazenar a maior  profundidade
        for (int linha = 0; linha < ArrayComElevacao.length; linha++) {
            for (int coluna = 0; coluna < ArrayComElevacao[0].length; coluna++) {
                // percorrer todos os espacos do array para descobrir qual tem a maior profundidade
                if (ArrayComElevacao[linha][coluna] > maiorAltura) {
                    maiorAltura = ArrayComElevacao[linha][coluna];
                    //substituir a maior profundidade anterior pela a mais recente
                }
            }
        }
        maiorAltura += 1;
        System.out.println("para inundacao total, subir :" + (maiorAltura ) + " m");
        return maiorAltura ;
    }

    //alínea h---------------------------------------------------------------------------------------
    public static void incrementoAreaInundada(int[][] arraryComElevacao, int maiorAltura) {

        //IMPORTANTE!! ALTEREI!
        System.out.print("subida da agua (m) | area inundada (m2)\n");
        System.out.print("------------------ | ------------------\n");

        // imprimir parte de cima tabela
        int aumentoAgua;

        int [][] auxPrevious = new int [arraryComElevacao.length][arraryComElevacao[0].length];
        copiarArray(arraryComElevacao,auxPrevious);
        //uso de funcao do java para copiar array
        int [][] auxAtual = new int [arraryComElevacao.length][arraryComElevacao[0].length];
        // criacao de variaveis auxiliares

        for( int metroAltura = 1; metroAltura <= maiorAltura; metroAltura ++){
            // percorrer cada metro ate chegar a maior altura
            for (int linha = 0; linha < arraryComElevacao.length; linha++) {
                for (int coluna = 0; coluna < arraryComElevacao[0].length; coluna++) {
                    auxAtual[linha][coluna] = auxPrevious[linha][coluna] - 1;
                }
            }
            // percorrer todos os espacos da matriz
            System.out.printf("%18d | ",metroAltura);
            // dar print aos numeros dos metros ate chegar a maior altura
            aumentoAgua = variacaoAreaInundada(auxPrevious,auxAtual);
            // chamar metodo anterior para ajudar a calcular variacao da agua
            System.out.printf("%18d\n", aumentoAgua);
            // dar print aos numeros da variacao
            copiarArray(auxAtual,auxPrevious);
            // utilizacao de metodo para copiar array
        }
    }

    public static void copiarArray (int[][] arrayFonte, int[][] arrayDestino){

        for (int i = 0; i < arrayFonte.length; i++) {
            for (int j = 0; j < arrayFonte[0].length; j++) {
                arrayDestino[i][j] = arrayFonte[i][j];
            }
        }
    }

    //alínea i------------------------------------------------------------------------------------------------------
    public static void determinarLocalCubo (int[][] arrayComElevacao){
        final int LADOCUBO=3;
        //comprimento em metros do lado do cubo
        final int BASE = -3;
        //altura a que a base do cubo tem que estar
        int moverTerra = Integer.MAX_VALUE;
        //quantidade de terra que temos que mover
        int aux = 0;
        int x = 0;
        int y = 0;

        for (int i = 0; i <= arrayComElevacao.length - LADOCUBO ; i++) {
            for (int j = 0; j <= arrayComElevacao[0].length - LADOCUBO; j++) {
                // perceber quantos blocos sao possiveis

                for (int linha = i; linha < i + LADOCUBO ; linha++) {
                    for (int coluna = j; coluna < j + LADOCUBO ; coluna++) {
                        aux += Math.abs(arrayComElevacao[linha][coluna] - BASE);
                        //calcular a distancia da posicao pertencente ao bloco com a base pretendida
                    }
                }
                // percorrer todos os elementos desse bloco e somar par aperceber quanto e preciso para aplanar o terreno
                if(aux < moverTerra){
                    moverTerra = aux;
                    x = i;
                    y = j;
                }
                //guardar as coordenadas mais noroeste
                aux = 0;
                //zerar auxliar para o proximo bloco de numeros
            }
        }

        System.out.printf("coordenadas do cubo: (%d,%d),   terra a mobilizar: %d m2\n",x,y,moverTerra);
    }

    //j)-----------------------------------------------------------------------------------------------------------------
    public static int caminhoMaisEste (int[][] arrayComElevacao){
        int count_numerosPositivos=0;
        //iniciar contador para linhas positivas
        for (int coluna = arrayComElevacao[0].length - 1; coluna >= 0 ; coluna--) {
            for (int linha = 0; linha < arrayComElevacao.length; linha++) {
                //percorrer todos os espacos do bloco
                if (arrayComElevacao[linha][coluna]>=0){
                    count_numerosPositivos++;
                    //contador de numeros postivos
                }
            }
            if (count_numerosPositivos==arrayComElevacao.length){
                //condicao onde se verifica se o numero positivos e igual ao numero de linhas
                return coluna;
                // se for verdadeiro retornar a coluna
            }
            count_numerosPositivos=0;
        }
        return -1;
        // caso contrario retornar -1
    }

    public static void printInfo (int caminho){

        if(caminho != -1 ){
            System.out.printf("caminho seco na vertical na coluna (%d)\n",caminho);
            //caso retornar != -1 dar print a caminho seco

        }else{
            System.out.println("não há caminho seco na vertical\n");
            //caso contrario printar nao ha caminho
        }


    }
}