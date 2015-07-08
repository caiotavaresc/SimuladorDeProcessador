package simulador.cpu;

//Unidade de Lógica e Aritmética, destinada a efetuar os cálculos
//O único registrador que a ULA possui é o AC
public class Ula {
	
	static Integer Dado;
	static Integer AC;
	
	//Métodos para enviar dados para o barramento interno
	public static void EnviarACBarramento()
	{
		BarramentoInterno.setDado(AC);
	}
	
	public static void enviarDadoULA(Integer dado)
	{
		Dado = dado;
	}
	
	//--------------------------Classe que define as operações da ULA-------------------------------
	
	public static void operacao(Integer[] sinal)
	{
		int indicador;
		
		indicador = (int) (sinal[22] * Math.pow(2, 3));
		indicador += (int) (sinal[23] * Math.pow(2, 2));
		indicador += (int) (sinal[24] * 2);
		indicador += sinal[25];
		
		//Validações
		if(indicador == 0)
		{
			//Não faz nada, mantém o AC zerado
			AC = Dado;
			return;
		}
		
		if(indicador == 1)
		{
			//Incrementar o conteúdo do dado e jogar no AC
			AC = ++Dado;
			return;
		}
		
		if(indicador == 2)
		{
			//Decrementar o conteúdo do dado e jogar no AC
			AC = --Dado;
			return;
		}
		
		if(indicador == 3)
		{
			//Somar o que já está em AC com o que está em Dado
			AC = AC + Dado;
			return;
		}
		
		if(indicador == 4)
		{
			//Subtrair o que está em Dado do que já está em AC
			AC = AC - Dado;
			return;
		}
		
		if(indicador == 5)
		{
			//Multiplicar o que está em Dado e o que está em AC e colocar o resultado em AC
			AC = AC*Dado;
			return;
		}
		
		if(indicador == 6)
		{
			//Essa variável temp é um terceiro flip flop que está temporariamente na ULA
			//Se tiver tempo hábil vou removê-lo
			int temp = AC;
			
			//Dividir o que está em AC pelo que está em DADO e colocar o resultado em AC
			AC = AC%Dado;
			Dado = temp/Dado;
			return;
		}
		
		if(indicador == 7)
		{
			//Comparação - é relativamente simples e idêntica à subtração
			//No Barramento, a UC fará a validação dos dados e ligará as flags
			AC = AC - Dado;
			return;
		}
	}
}
