package simulador.cpu;

import simulador.*;

//Por uma quest�o de organiza��o, a UC conter� os registradores PC, IR, MAR e MBR
//Por�m a fun��o da UC � apenas implementar os ciclos (busca, indire��o e execu��o)
public class Uc {
	
	public static int PC, MAR;
	public static Object MBR, IR;
	
	//Inicializar toods os registradores como zero
	public Uc()
	{
		PC = 0;
		MAR = 0;
		MBR = null;
		IR = null;
	}
	
	//Esse m�todo interpretar� o conte�do do sinal de controle e tomar� a decis�o adequada
	public void interpretadorSinaisDeControle(Integer[] sinal)
	{
		InterpretadorSinais.setSinal(sinal);
		InterpretadorSinais.interpretar();
	}

	//M�todo do ciclo de instru��o - respons�vel por executar os ciclos
	public void cicloDeInstrucao()
	{
		System.out.println("Ciclo de Instru��o:");
		System.out.println("-------------------");
		
		System.out.println("Ciclo de Busca:");
		cicloDeBusca();
		/*
		System.out.println("Ciclo de Indirecao");
		cicloDeIndirecao();
		
		System.out.println("Ciclo de Execu��o");
		cicloDeExecucao();
		*/
	}
	
	//Ciclo de busca - Buscar execu��es na mem�ria
	public void cicloDeBusca()
	{
		//Array de instru��o
		Integer[] instr;
		
		instr = zeraTudo(64);
		
		//t1: MAR <- PC
		instr[8] = 1; //Controlador de sa�da de PC
		instr[12] = 1; //Controlador de entrada de MAR
		
		//Jogar o pr�ximo endere�o a ser lido no barramento
		instr[17] = 1;
		
		//Do barramento j� mandar pra mem�ria
		instr[20] = 1;

		//Executar a opera��o e zerar o sinal de controle
		this.interpretadorSinaisDeControle(instr);
		instr = zeraTudo(64);
		
		//t2: MBR <- Mem�ria
		//O barramento j� est� alimentado com o dado do pr�ximo endere�o, basta acess�-lo na mem�ria e coloc�-lo no MBR
		instr[20] = 1;
		instr[21] = 1;
		instr[18] = 1;
		
		//Marcar ADDRESS Valid e READ
		instr[27] = 1;
		instr[28] = 0;
		
		this.interpretadorSinaisDeControle(instr);
		instr = zeraTudo(64);
		
		//t3: PC <- PC + 1
		//    IR <- MBR
		/*
		//Abrir a Sa�da do PC e a entrada do X
		instr[8] = 1;
		instr[14] = 1;
		
		this.interpretadorSinaisDeControle(instr);
		instr = zeraTudo(64);
		
		//Abrir a sa�da do X, a sa�da do AC e a entrada do PC
		instr[15] = 1;
		instr[16] = 1;
		instr[9] = 1;
		
		//Sinal de controle da ULA
		//00001 - Incrementar
		instr[26] = 1;
		
		this.interpretadorSinaisDeControle(instr);
		instr = zeraTudo(64);
		
		//Abrir a sa�da do MBR e a entrada do IR
		instr[10] = 1;
		instr[13] = 1;
		
		this.interpretadorSinaisDeControle(instr);*/
	}
	
	//Ciclo de Execu��o - Execu��o efetiva da mem�ria
	public void cicloDeExecucao()
	{
		System.out.println("Ciclo de Execu��o");
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
}