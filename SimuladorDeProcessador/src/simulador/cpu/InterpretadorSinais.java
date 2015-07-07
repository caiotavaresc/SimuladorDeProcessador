package simulador.cpu;

import simulador.*;

//Essa classe ser� respons�vel pela interpreta��o dos sinais de controle e por sua efetiva execu��o
public class InterpretadorSinais {

	private static Integer[] sinal;
	static BarramentoInterno barramentoInt = new BarramentoInterno();
	static BarramentoDados barramentoExt = new BarramentoDados();
	static Registradores registradores = new Registradores();
	static Uc unidadeControle = new Uc();
	
	//Calcula constantes: M�todo utilizado para saber qual � o sinal que est� sendo passado pelo controle JMP
	//Esse m�todo � uma abstra��o, na verdade, o controlador deveria verificar com um IF todas as combina��es
	//diferentes entre as linhas de bits. Por uma quest�o pr�tica, estamos verificando e somando, mais abaixo
	//utilizaremos esse resultado para decidir o que fazer com os valores constantes que v�m nas linhas de endere�o
	//do sinal de controle (bits 32 a 63).
	public static int calculaConstantes()
	{
		int constante = 0;
		
			constante += (int) sinal[29] * Math.pow(2, 2);
			constante += sinal[30] * 2;
			constante += sinal[31];
		
		return constante;
	}
	
	//Passar o sinal necess�rio
	public static void setSinal(Integer[] MySign)
	{
		sinal = MySign;
	}
	
	//M�todo cora��o - INTERPRETAR
	public static void interpretar()
	{
		int i;
		
		//1: Mandar os sinais constantes para o barramento
		//As constantes s�o valores fixos que ficam nos �ltimos 32 bits do sinal de controle
		//Esses bits s�o utilizados para o c�lculo de endere�os, jumps e tamb�m para passar n�meros diretamente
		//para um registrador ou endere�o de mem�ria. Como o professor disse, a palavra deve ter a quantidade exata
		//de bits, mas n�o precisamos ficar fazendo c�lculos bin�rios.
		//Desse modo, estou mandando o valor inteiro na posi��o 32. Entenda que eles na verdade estariam em todas as
		//outras posi��es como bin�rio.
		switch(calculaConstantes())
		{
		case 1:
			unidadeControle.EnviarDadosBarramento(sinal[32]);
			break;
		case 2:
			unidadeControle.EnviarEnderecoBarramento(sinal[32]);
			break;
		}
		
		//2: Processar opera��es da ULA
		
		//Se a porta da ULA estiver aberta, transferir o conte�do de X
		if(sinal[15]==1)
			Ula.enviarDadoULA(Registradores.X);
		
		//Solicitar opera��o da ULA
		Ula.operacao(sinal);
		
		//------ Verificar as portas de entrada abertas e jogar os dados nos barramentos
		
		//3 - Barramento Interno
		
		//Pegar todas as portas que entram no barramento
		for(i = 0; i < barramentoInt.EntradaBarramentoInterno.length; i++)
		{
			//Se a porta estiver aberta
			if(sinal[barramentoInt.EntradaBarramentoInterno[i]] == 1)
			{
				//enviar o sinal
				switch(barramentoInt.EntradaBarramentoInterno[i])
				{
				case 8:
					unidadeControle.EnviarPCBarramento();
					break;
				case 10:
					unidadeControle.EnviarMBRBarramento();
					break;
				case 0:
					registradores.EnviarAXBarramento();
					break;
				case 1:
					registradores.EnviarBXBarramento();
					break;
				case 2:
					registradores.EnviarCXBarramento();
					break;
				case 3:
					registradores.EnviarDXBarramento();
					break;
				case 16:
					Ula.EnviarACBarramento();
				}
			}
		}
		
		//Pegar todas as portas que SAEM do barramento
		for(i = 0; i < barramentoInt.SaidaBarramentoInterno.length; i++)
		{
			//Se a porta estiver aberta
			if(sinal[barramentoInt.SaidaBarramentoInterno[i]] == 1)
			{
				//Enviar o sinal
				switch(barramentoInt.SaidaBarramentoInterno[i])
				{
				case 9:
					barramentoInt.enviarBarramentoPC();
					break;
				case 12:
					barramentoInt.enviarBarramentoMAR();
					break;
				case 13:
					barramentoInt.enviarBarramentoIR();
					break;
				case 11:
					barramentoInt.enviarBarramentoMBR();
					break;
				case 14:
					barramentoInt.enviarBarramentoX();
					break;
				case 4:
					barramentoInt.enviarBarramentoAX();
					break;
				case 5:
					barramentoInt.enviarBarramentoBX();
					break;
				case 6:
					barramentoInt.enviarBarramentoCX();
					break;
				case 7:
					barramentoInt.enviarBarramentoDX();
				}
			}
		}
		
		//4 - A��es da Mem�ria - Se o endere�o � v�lido
		if(sinal[27] == 1)
		{
			//Leitura
			if(sinal[28] == 0)
				Memoria.le();
			else if(sinal[28] == 1)
				Memoria.escreve();
		}
		
		//5 - Barramento Externo
		
		//Pegar todas as portas que entram no barramento externo
		for(i = 0; i < barramentoExt.barramentoExtEntradas.length; i++)
		{
			//Se a porta estiver aberta
			if(sinal[barramentoExt.barramentoExtEntradas[i]] == 1)
			{
				//Enviar o sinal
				switch(barramentoExt.barramentoExtEntradas[i])
				{
				case 17:
					unidadeControle.EnviarMARBarramentoExt();
					break;
				case 19:
					unidadeControle.EnviarMBRBarramentoExt();
					break;
				case 21:
					Memoria.EnviarDadoParaBarramento();
				}
			}
		}		
		
		//Pegar todas as portas que SAEM do barramento Externo
		for(i = 0; i < barramentoExt.barramentoExtSaidas.length; i++)
		{
			//Se a porta estiver aberta
			if(sinal[barramentoExt.barramentoExtSaidas[i]] == 1)
			{
				switch(barramentoExt.barramentoExtSaidas[i])
				{
				case 18:
					barramentoExt.enviarBarramentoExtMBR();
					break;
				case 20:
					barramentoExt.enviarEnderecoMemoria();
					barramentoExt.enviarDadoMemoria();
					break;
				}
			}
		}
	}
}