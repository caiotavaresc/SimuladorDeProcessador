package simulador.cpu;

import simulador.*;

//Essa classe será responsável pela interpretação dos sinais de controle e por sua efetiva execução
public class InterpretadorSinais {

	private static Integer[] sinal;
	static BarramentoInterno barramentoInt = new BarramentoInterno();
	static BarramentoDados barramentoExt = new BarramentoDados();
	static Registradores registradores = new Registradores();
	static Uc unidadeControle = new Uc();
	static Ula unidadeLogica = new Ula();
	
	//Passar o sinal necessário
	public static void setSinal(Integer[] MySign)
	{
		sinal = MySign;
	}
	
	//Método coração - INTERPRETAR
	public static void interpretar()
	{
		int i;
		//1 Verificar as portas de entrada abertas e jogar os dados nos barramentos
		
		//1 - Barramento Interno
		
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
					unidadeLogica.EnviarACBarramento();
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
		
		//2 - Ações da Memória - Se o endereço é válido
		if(sinal[27] == 1)
		{
			//Leitura
			if(sinal[28] == 0)
				Memoria.le();
			else if(sinal[28] == 1)
				Memoria.escreve();
		}
		
		//3 - Barramento Externo
		
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