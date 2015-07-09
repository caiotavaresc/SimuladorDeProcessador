/*********************************************************************
 *                     UNIVERSIDADE DE SÃO PAULO                     *
 *               ESCOLA DE ARTES, CIÊNCIAS E HUMANIDADES             *
 *-------------------------------------------------------------------*
 * Caio Tavares Cruz - 8921840                                       *
 * Humberto Rocha Pinheiro - 7556816                                 *
 *-------------------------------------------------------------------*
 * Exercício Programa de OCD - Simulador de Processador              *
 *-------------------------------------------------------------------*
 * Descrição: Essa classe representa a Unidade de Controle do proces-*
 * sador, responsável pelo controle das operações que ocorrem no     *
 * computador.                                                       *
 *********************************************************************/

package simulador.cpu;

import simulador.*;

//Por uma questão de organização, a UC conterá os registradores PC, IR, MAR e MBR
//já que são de uso exclusivo dela, a função da UC é implementar os ciclos (busca e execução)
//(O professor cancelou indireção).
public class Uc {
	
	//Registradores da UC
	public static int PC, MAR;
	public static Object MBR, IR;
	
	//Referência para o microprograma que traduz as instruções e devolve
	//os sinais de controle.
	public static TradutorDeInstrucoes tradutor;
	
	//Variável que indica em qual ciclo o programa se encontra
	public static int myCicle;
	
	//Flags Zero e de Sinal
	public static int flag0, flagSinal;
	
	//Strings de Microinstruções, Sinais de Controle e saída de erros.
	public static String micro, control, erro;
	
	//Inicializar toods os registradores como zero
	public Uc()
	{
		PC = 0;
		MAR = 0;
		MBR = null;
		IR = null;
		tradutor = new TradutorDeInstrucoes();
		
		myCicle = 0;
	}
	
	//O sinal de controle devolvido pelo Microprograma será enviado
	//para o fluxo. Nesse caso, esse é o método que recebe o sinal e 
	//manda para o fluxo, que é representado pela classe InterpretadorSinais
	public static void interpretadorSinaisDeControle(Integer[] sinal)
	{
		InterpretadorSinais.setSinal(sinal);
		InterpretadorSinais.interpretar();
		
		//Depois de executar o sinal, colocá-lo na saída apropriada
		control = control + imprimirSinalDeControle(sinal) + "\n";
		
	}

	//Método do ciclo de instrução - responsável por executar os ciclos
	public void cicloDeInstrucao()
	{
		micro = "";
		control = "";
		erro = null;
		
		micro = "Iniciando Ciclo de Instrução\n";
		control = "Iniciando Ciclo de Instrução\n";
				
		micro = micro + "  Ciclo de Busca:\n";
		control = control + "Ciclo de Busca:\n";
		cicloDeBusca();
		
		micro = micro + "  Ciclo de Execução:\n";
		control = control + "Ciclo de Execução:\n";
		cicloDeExecucao();
	}
	
	//Ciclo de busca - Buscar execuções na memória
	public void cicloDeBusca()
	{
		myCicle = 0;
		//Array de instrução
		Integer[] instr;
		
		instr = zeraTudo(64);
		
		micro = micro + "     t1: MAR <- PC\n";
		instr[8] = 1; //Controlador de saída de PC
		instr[12] = 1; //Controlador de entrada de MAR
		
		//Jogar o próximo endereço a ser lido no barramento
		instr[17] = 1;
		
		//Do barramento já mandar pra memória
		instr[20] = 1;

		//Executar a operação e zerar o sinal de controle
		interpretadorSinaisDeControle(instr);
		instr = zeraTudo(64);
		
		micro = micro + "     t2: MBR <- Memória\n";
		micro = micro + "         PC <- PC + 1\n";
		//O barramento já está alimentado com o dado do próximo endereço, basta acessá-lo na memória e colocá-lo no MBR
		instr[20] = 1;
		instr[21] = 1;
		instr[18] = 1;
		
		//Marcar ADDRESS Valid e READ
		instr[26] = 1;
		instr[27] = 0;
		
		interpretadorSinaisDeControle(instr);
		instr = zeraTudo(64);
		
		micro = micro + "     t3: IR <- MBR\n";
		
		//Abrir a Saída do PC e a entrada do X
		instr[8] = 1;
		instr[14] = 1;
		
		interpretadorSinaisDeControle(instr);
		instr = zeraTudo(64);

		//Abrir a saída do X, a saída do AC e a entrada do PC
		instr[15] = 1;
		instr[16] = 1;
		instr[9] = 1;
		
		//Sinal de controle da ULA
		//00001 - Incrementar
		instr[25] = 1;
		
		interpretadorSinaisDeControle(instr);
		instr = zeraTudo(64);
		
		//Abrir a saída do MBR e a entrada do IR
		instr[10] = 1;
		instr[13] = 1;
		
		interpretadorSinaisDeControle(instr);
		instr = zeraTudo(64);
	}
	
	//Ciclo de Execução - Execução efetiva da memória
	public void cicloDeExecucao()
	{
		myCicle = 2;
		
		//1 - TRADUZIR O QUE ESTÁ NO IR PARA SINAIS DE CONTROLE
		try
		{
			tradutor.traduzInstrucao((String) IR);
		}
		catch (ArithmeticException e)
		{
			erro = "Erro: Divisão por zero";
		}
		catch (Exception e)
		{
			erro = "Erro: Instrução não reconhecida";
		}
		//O tradutor devolve as instruções para a UC, que executa pontualmente cada uma.
	}
	
	//Método que retorna um novo array com o tamanho estipulado todo zerado
	public static Integer[] zeraTudo(int tamanho)
	{
		Integer[] arr = new Integer[tamanho];
		
		for(int i = 0; i < arr.length; i++)
			arr[i] = 0;
		
		return arr;
	}
	
	//-------------------------------------------------------------------------------------------//
	//Operação para transferir dados para o barramento
	//-------------------------------------------------------------------------------------------//
	
	public void EnviarPCBarramento()
	{
		BarramentoInterno.setEndereco(PC);
		BarramentoInterno.setDado(PC);
	}

	public void EnviarMBRBarramento()
	{
		BarramentoInterno.setDado(MBR);
	}
	
	public void EnviarMBRBarramentoExt()
	{
		BarramentoDados.setDado(MBR);
	}
	
	public void EnviarMARBarramentoExt()
	{
		BarramentoDados.setEndereco(MAR);
	}
	
	//Métodos genéricos para enviar dados e endereços no caso de constantes - Vide a classe InterpretadorSinais
	public void EnviarDadosBarramento(Object dado)
	{
		BarramentoInterno.setDado(dado);
	}
	
	public void EnviarEnderecoBarramento(Integer endereco)
	{
		BarramentoInterno.setEndereco(endereco);
	}
	
	//Método que atualiza as flags conforme um dado especificado
	//Método que atualiza as flags de acordo com o resultado da operação
	public void AtualizaFlags(Integer valor)
	{
		//Flag 0 só é zero se o resultado da expressão for zero
		if(valor==0)
			flag0 = 1;
		else
			flag0 = 0;
		
		//Flag de sinal é 1 se o resultado da expressão for negativo
		if(valor<0)
			flagSinal = 1;
		else
			flagSinal = 0;
	}
	
	//----------------------------------------------------------------------
	//Função para adicionar texto no fim
	public static void insertMicroAppend(String microinst)
	{
		micro = micro + microinst + "\n";
	}
	
	//Método que pega o conteúdo da palavra de controle e retorna como string
	//Finalidade: Facilitar...
	public static String imprimirSinalDeControle(Integer[] sinal)
	{
		String retorno = "";
		
			for(int i = 0; i < 33; i++)
				retorno = retorno + sinal[i];
			
		return retorno;
	}
}