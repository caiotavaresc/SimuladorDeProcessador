/*********************************************************************
 *                     UNIVERSIDADE DE S�O PAULO                     *
 *               ESCOLA DE ARTES, CI�NCIAS E HUMANIDADES             *
 *-------------------------------------------------------------------*
 * Caio Tavares Cruz - 8921840                                       *
 * Humberto Rocha Pinheiro - 7556816                                 *
 *-------------------------------------------------------------------*
 * Exerc�cio Programa de OCD - Simulador de Processador              *
 *-------------------------------------------------------------------*
 * Descri��o: Essa classe representa a Unidade de Controle do proces-*
 * sador, respons�vel pelo controle das opera��es que ocorrem no     *
 * computador.                                                       *
 *********************************************************************/

package simulador.cpu;

import simulador.*;

//Por uma quest�o de organiza��o, a UC conter� os registradores PC, IR, MAR e MBR
//j� que s�o de uso exclusivo dela, a fun��o da UC � implementar os ciclos (busca e execu��o)
//(O professor cancelou indire��o).
public class Uc {
	
	//Registradores da UC
	public static int PC, MAR;
	public static Object MBR, IR;
	
	//Refer�ncia para o microprograma que traduz as instru��es e devolve
	//os sinais de controle.
	public static TradutorDeInstrucoes tradutor;
	
	//Vari�vel que indica em qual ciclo o programa se encontra
	public static int myCicle;
	
	//Flags Zero e de Sinal
	public static int flag0, flagSinal;
	
	//Strings de Microinstru��es, Sinais de Controle e sa�da de erros.
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
	
	//O sinal de controle devolvido pelo Microprograma ser� enviado
	//para o fluxo. Nesse caso, esse � o m�todo que recebe o sinal e 
	//manda para o fluxo, que � representado pela classe InterpretadorSinais
	public static void interpretadorSinaisDeControle(Integer[] sinal)
	{
		InterpretadorSinais.setSinal(sinal);
		InterpretadorSinais.interpretar();
		
		//Depois de executar o sinal, coloc�-lo na sa�da apropriada
		control = control + imprimirSinalDeControle(sinal) + "\n";
		
	}

	//M�todo do ciclo de instru��o - respons�vel por executar os ciclos
	public void cicloDeInstrucao()
	{
		micro = "";
		control = "";
		erro = null;
		
		micro = "Iniciando Ciclo de Instru��o\n";
		control = "Iniciando Ciclo de Instru��o\n";
				
		micro = micro + "  Ciclo de Busca:\n";
		control = control + "Ciclo de Busca:\n";
		cicloDeBusca();
		
		micro = micro + "  Ciclo de Execu��o:\n";
		control = control + "Ciclo de Execu��o:\n";
		cicloDeExecucao();
	}
	
	//Ciclo de busca - Buscar execu��es na mem�ria
	public void cicloDeBusca()
	{
		myCicle = 0;
		//Array de instru��o
		Integer[] instr;
		
		instr = zeraTudo(64);
		
		micro = micro + "     t1: MAR <- PC\n";
		instr[8] = 1; //Controlador de sa�da de PC
		instr[12] = 1; //Controlador de entrada de MAR
		
		//Jogar o pr�ximo endere�o a ser lido no barramento
		instr[17] = 1;
		
		//Do barramento j� mandar pra mem�ria
		instr[20] = 1;

		//Executar a opera��o e zerar o sinal de controle
		interpretadorSinaisDeControle(instr);
		instr = zeraTudo(64);
		
		micro = micro + "     t2: MBR <- Mem�ria\n";
		micro = micro + "         PC <- PC + 1\n";
		//O barramento j� est� alimentado com o dado do pr�ximo endere�o, basta acess�-lo na mem�ria e coloc�-lo no MBR
		instr[20] = 1;
		instr[21] = 1;
		instr[18] = 1;
		
		//Marcar ADDRESS Valid e READ
		instr[26] = 1;
		instr[27] = 0;
		
		interpretadorSinaisDeControle(instr);
		instr = zeraTudo(64);
		
		micro = micro + "     t3: IR <- MBR\n";
		
		//Abrir a Sa�da do PC e a entrada do X
		instr[8] = 1;
		instr[14] = 1;
		
		interpretadorSinaisDeControle(instr);
		instr = zeraTudo(64);

		//Abrir a sa�da do X, a sa�da do AC e a entrada do PC
		instr[15] = 1;
		instr[16] = 1;
		instr[9] = 1;
		
		//Sinal de controle da ULA
		//00001 - Incrementar
		instr[25] = 1;
		
		interpretadorSinaisDeControle(instr);
		instr = zeraTudo(64);
		
		//Abrir a sa�da do MBR e a entrada do IR
		instr[10] = 1;
		instr[13] = 1;
		
		interpretadorSinaisDeControle(instr);
		instr = zeraTudo(64);
	}
	
	//Ciclo de Execu��o - Execu��o efetiva da mem�ria
	public void cicloDeExecucao()
	{
		myCicle = 2;
		
		//1 - TRADUZIR O QUE EST� NO IR PARA SINAIS DE CONTROLE
		try
		{
			tradutor.traduzInstrucao((String) IR);
		}
		catch (ArithmeticException e)
		{
			erro = "Erro: Divis�o por zero";
		}
		catch (Exception e)
		{
			erro = "Erro: Instru��o n�o reconhecida";
		}
		//O tradutor devolve as instru��es para a UC, que executa pontualmente cada uma.
	}
	
	//M�todo que retorna um novo array com o tamanho estipulado todo zerado
	public static Integer[] zeraTudo(int tamanho)
	{
		Integer[] arr = new Integer[tamanho];
		
		for(int i = 0; i < arr.length; i++)
			arr[i] = 0;
		
		return arr;
	}
	
	//-------------------------------------------------------------------------------------------//
	//Opera��o para transferir dados para o barramento
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
	
	//M�todos gen�ricos para enviar dados e endere�os no caso de constantes - Vide a classe InterpretadorSinais
	public void EnviarDadosBarramento(Object dado)
	{
		BarramentoInterno.setDado(dado);
	}
	
	public void EnviarEnderecoBarramento(Integer endereco)
	{
		BarramentoInterno.setEndereco(endereco);
	}
	
	//M�todo que atualiza as flags conforme um dado especificado
	//M�todo que atualiza as flags de acordo com o resultado da opera��o
	public void AtualizaFlags(Integer valor)
	{
		//Flag 0 s� � zero se o resultado da express�o for zero
		if(valor==0)
			flag0 = 1;
		else
			flag0 = 0;
		
		//Flag de sinal � 1 se o resultado da express�o for negativo
		if(valor<0)
			flagSinal = 1;
		else
			flagSinal = 0;
	}
	
	//----------------------------------------------------------------------
	//Fun��o para adicionar texto no fim
	public static void insertMicroAppend(String microinst)
	{
		micro = micro + microinst + "\n";
	}
	
	//M�todo que pega o conte�do da palavra de controle e retorna como string
	//Finalidade: Facilitar...
	public static String imprimirSinalDeControle(Integer[] sinal)
	{
		String retorno = "";
		
			for(int i = 0; i < 33; i++)
				retorno = retorno + sinal[i];
			
		return retorno;
	}
}