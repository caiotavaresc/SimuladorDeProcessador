package simulador.cpu;

//Por uma questão de organização, a UC conterá os registradores PC, IR, MAR e MBR
//Porém a função da UC é apenas implementar os ciclos (busca, indireção e execução)
public class Uc {
	
	int PC, MAR;
	String IR;
	
	//Esse método interpretará o conteúdo do sinal de controle e tomará a decisão adequada
	public void interpretadorSinaisDeControle(int[] sinal)
	{
		System.out.println("Montando...");
	}

	//Método do ciclo de instrução - responsável por executar os ciclos
	public void cicloDeInstrucao()
	{
		System.out.println("Ciclo de Instrução:");
		
		System.out.println("Ciclo de Busca:");
		cicloDeBusca();
		
		System.out.println("Ciclo de Indirecao");
		cicloDeIndirecao();
		
		System.out.println("Ciclo de Execução");
		cicloDeExecucao();
	}
	
	//Ciclo de busca - Buscar execuções na memória
	public void cicloDeBusca()
	{
		System.out.println("MAR <- PC");
	}
	
	//Ciclo de Indireção - Operandos que utilizam ponteiros
	public void cicloDeIndirecao()
	{
		System.out.println("Ciclo de Indireção");
	}
	
	//Ciclo de Execução - Execução efetiva da memória
	public void cicloDeExecucao()
	{
		System.out.println("Ciclo de Execução");
	}
}