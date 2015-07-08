package simulador.cpu;

//Unidade de L�gica e Aritm�tica, destinada a efetuar os c�lculos
//O �nico registrador que a ULA possui � o AC
public class Ula {
	
	static Integer Dado;
	static Integer AC;
	
	//M�todos para enviar dados para o barramento interno
	public static void EnviarACBarramento()
	{
		BarramentoInterno.setDado(AC);
	}
	
	public static void enviarDadoULA(Integer dado)
	{
		Dado = dado;
	}
	
	//--------------------------Classe que define as opera��es da ULA-------------------------------
	
	public static void operacao(Integer[] sinal)
	{
		int indicador;
		
		indicador = (int) (sinal[22] * Math.pow(2, 3));
		indicador += (int) (sinal[23] * Math.pow(2, 2));
		indicador += (int) (sinal[24] * 2);
		indicador += sinal[25];
		
		//Valida��es
		if(indicador == 0)
		{
			//N�o faz nada, mant�m o AC zerado
			AC = Dado;
			return;
		}
		
		if(indicador == 1)
		{
			//Incrementar o conte�do do dado e jogar no AC
			AC = ++Dado;
			return;
		}
		
		if(indicador == 2)
		{
			//Decrementar o conte�do do dado e jogar no AC
			AC = --Dado;
			return;
		}
		
		if(indicador == 3)
		{
			//Somar o que j� est� em AC com o que est� em Dado
			AC = AC + Dado;
			return;
		}
		
		if(indicador == 4)
		{
			//Subtrair o que est� em Dado do que j� est� em AC
			AC = AC - Dado;
			return;
		}
		
		if(indicador == 5)
		{
			//Multiplicar o que est� em Dado e o que est� em AC e colocar o resultado em AC
			AC = AC*Dado;
			return;
		}
		
		if(indicador == 6)
		{
			//Essa vari�vel temp � um terceiro flip flop que est� temporariamente na ULA
			//Se tiver tempo h�bil vou remov�-lo
			int temp = AC;
			
			//Dividir o que est� em AC pelo que est� em DADO e colocar o resultado em AC
			AC = AC%Dado;
			Dado = temp/Dado;
			return;
		}
		
		if(indicador == 7)
		{
			//Compara��o - � relativamente simples e id�ntica � subtra��o
			//No Barramento, a UC far� a valida��o dos dados e ligar� as flags
			AC = AC - Dado;
			return;
		}
	}
}
