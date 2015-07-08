/*********************************************************************
 *                     UNIVERSIDADE DE SÃO PAULO                     *
 *               ESCOLA DE ARTES, CIÊNCIAS E HUMANIDADES             *
 *-------------------------------------------------------------------*
 * Caio Tavares Cruz - 8921840                                       *
 * Humberto Rocha Pinheiro - 7556816                                 *
 *-------------------------------------------------------------------*
 * Exercício Programa de OCD - Simulador de Processador              *
 *-------------------------------------------------------------------*
 * Descrição: Essa classe é representa a memória principal numa      *
 * arquitetura tradicional. Ela é conectada ao barramento de sistema * 
 *********************************************************************/

package simulador;

//A classe controladora sera responsavel pela interacoes com o usuario
public class Memoria {
	
	//Memoria principal: Guarda tanto dados quanto instrucoes
	public static Object[] memoriaPrincipal;
	
	//Indice - Endereço da próxima instrução a ser gravada (quando o usuário adicionar novas instruções)
	public static int indice;
	
	//Atributos de dado e endereço utilizado nas transações entre memória e barramento de sistema
	public static Integer Endereco;
	public static Object Dado;
	
	//Inicializar a memoria
	public Memoria()
	{
		memoriaPrincipal = new Object[1000];
		indice = 0;	
	}
	
	//Metodo que escreve a instrucoes em Assembly e guarda na memoria os sinais traduzidos
	public static void setInstrucao(String instrucao)
	{
		memoriaPrincipal[indice++] = instrucao;
	}
	
	//Metodo que pega o dado/instrução na memória e retorna como string
	public static String getInstrucao(int i)
	{
		if(memoriaPrincipal[i] == null)
			return "";
		else
		{
			if(memoriaPrincipal[i] instanceof Integer)
				return Integer.toHexString((Integer) memoriaPrincipal[i]);
			else
				return (String) memoriaPrincipal[i];
		}
	}
	
	//Metodo que limpa as instrucoes das memorias
	public static void clear()
	{
		//memoriaPrincipal.clear();
		for(int i = 0; i < memoriaPrincipal.length; i++)
			memoriaPrincipal[i] = null;
		
		indice = 0;
	}
	
	//Imprimir as instrucoes armazenadas em memoria
	public static void imprimeMemoria()
	{
		
		for(int i = 0; i < indice; i++)
			System.out.println(memoriaPrincipal[i]);
	}
	
	//------------------------------METODOS DE TRANSMISS�O DE DADOS-------------------------------------
	public static void EnviarDadoParaBarramento()
	{
		BarramentoDados.setDado(Dado);
	}
	
	public static void setDado(Object dado)
	{
		Dado = dado;
	}
	
	public static void setEndereco(int endereco)
	{
		Endereco = endereco;
	}
	
	//---------------------METODOS DE LEITURA E ESCRITA--------------
	public static void le()
	{
		Dado = memoriaPrincipal[Endereco];
	}
	
	public static void escreve()
	{
		memoriaPrincipal[Endereco] = Dado;
	}
}