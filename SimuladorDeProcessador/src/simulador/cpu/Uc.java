package simulador.cpu;

//Por uma quest�o de organiza��o, a UC conter� os registradores PC, IR, MAR e MBR
//Por�m a fun��o da UC � apenas implementar os ciclos (busca, indire��o e execu��o)
public class Uc {
	
	int PC, MAR;
	String IR;
	
	//Esse m�todo interpretar� o conte�do do sinal de controle e tomar� a decis�o adequada
	public void interpretadorSinaisDeControle(int[] sinal)
	{
		System.out.println("Montando...");
	}

	//M�todo do ciclo de instru��o - respons�vel por executar os ciclos
	public void cicloDeInstrucao()
	{
		System.out.println("Ciclo de Instru��o:");
		
		System.out.println("Ciclo de Busca:");
		cicloDeBusca();
		
		System.out.println("Ciclo de Indirecao");
		cicloDeIndirecao();
		
		System.out.println("Ciclo de Execu��o");
		cicloDeExecucao();
	}
	
	//Ciclo de busca - Buscar execu��es na mem�ria
	public void cicloDeBusca()
	{
		System.out.println("MAR <- PC");
	}
	
	//Ciclo de Indire��o - Operandos que utilizam ponteiros
	public void cicloDeIndirecao()
	{
		System.out.println("Ciclo de Indire��o");
	}
	
	//Ciclo de Execu��o - Execu��o efetiva da mem�ria
	public void cicloDeExecucao()
	{
		System.out.println("Ciclo de Execu��o");
	}
}