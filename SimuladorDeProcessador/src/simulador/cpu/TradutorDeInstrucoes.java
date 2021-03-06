/*********************************************************************
 *                     UNIVERSIDADE DE S�O PAULO                     *
 *               ESCOLA DE ARTES, CI�NCIAS E HUMANIDADES             *
 *-------------------------------------------------------------------*
 * Caio Tavares Cruz - 8921840                                       *
 * Humberto Rocha Pinheiro - 7556816                                 *
 *-------------------------------------------------------------------*
 * Exerc�cio Programa de OCD - Simulador de Processador              *
 *-------------------------------------------------------------------*
 * Descri��o: Essa classe representa o microprograma, software que   *
 * fica dentro da mem�ria ROM e � respons�vel por transformar os     *
 * comandos da linguagem de montagem (Assembly) em sinais de controle*
 *********************************************************************/

package simulador.cpu;

import java.util.*;

//Classe que recebe a String com a instru��o e a traduz para a arquitetura
public class TradutorDeInstrucoes {
	
	//Mapas que guardar�o os n�meros das portas dos registradores e os n�meros das instru��es
	public static HashMap<String, Integer[]> mapaDeInstrucoes = new HashMap<String, Integer[]>();
	public static HashMap<String, Integer[]> mapaDeInstrucoesJump = new HashMap<String, Integer[]>();
	public static HashMap<String, Integer> mapaDeRegistradoresEntrada = new HashMap<String, Integer>();
	public static HashMap<String, Integer> mapaDeRegistradoresSaida = new HashMap<String, Integer>();
	
	//Mapa de Instru��es da ULA est�tico - Adicionar os sinalizadores de instru��o
	public static void carregarMapaDeInstrucoes()
	{
		Integer[] inc = {0, 0, 0, 1};
		mapaDeInstrucoes.put("INC", inc);
		
		Integer[] dec = {0, 0, 1, 0};
		mapaDeInstrucoes.put("DEC", dec);
		
		Integer[] add = {0, 0, 1, 1};
		mapaDeInstrucoes.put("ADD", add);
		
		Integer[] sub = {0, 1, 0, 0};
		mapaDeInstrucoes.put("SUB", sub);
		
		Integer[] mul = {0, 1, 0, 1};
		mapaDeInstrucoes.put("MUL", mul);
		
		Integer[] div = {0, 1, 1, 0};
		mapaDeInstrucoes.put("DIV", div);
		
		Integer[] cmp = {0, 1, 1, 1};
		mapaDeInstrucoes.put("CMP", cmp);
	}
	
	//Mapa de Instru��es de JUMP est�tico - Adicionar os sinalizadores de instru��o
	public static void carregarMapaDeInstrucoesJump()
	{
		Integer[] jmp = {0, 0, 0, 1};
		mapaDeInstrucoesJump.put("JMP", jmp);
		
		Integer[] jz = {0, 0, 1, 1};
		mapaDeInstrucoesJump.put("JZ", jz);
		
		Integer[] jnz = {0, 1, 0, 0};
		mapaDeInstrucoesJump.put("JNZ", jnz);
		
		Integer[] jl = {0, 1, 0, 1};
		mapaDeInstrucoesJump.put("JL", jl);
		
		Integer[] jg = {0, 1, 1, 0};
		mapaDeInstrucoesJump.put("JG", jg);
		
		Integer[] jle = {0, 1, 1, 1};
		mapaDeInstrucoesJump.put("JLE", jle);
		
		Integer[] jge = {1, 0, 0, 0};
		mapaDeInstrucoesJump.put("JGE", jge);
	}
	
	//Mapa de registradores est�tico - Portas de entrada
	public static void carregarMapaDeRegistradoresEntrada()
	{
		mapaDeRegistradoresEntrada.put("A", 4);
		mapaDeRegistradoresEntrada.put("B", 5);
		mapaDeRegistradoresEntrada.put("C", 6);
		mapaDeRegistradoresEntrada.put("D", 7);
	}
	
	//Mapa de registradores est�tico - Portas de sa�da
	public static void carregarMapaDeRegistradoresSaida()
	{
		mapaDeRegistradoresSaida.put("A", 0);
		mapaDeRegistradoresSaida.put("B", 1);
		mapaDeRegistradoresSaida.put("C", 2);
		mapaDeRegistradoresSaida.put("D", 3);
	}
	
	//Inicializar o tradutor com o endere�o da lista de instru��es (mem�ria de bits)
	public TradutorDeInstrucoes()
	{
		carregarMapaDeInstrucoes();
		carregarMapaDeInstrucoesJump();
		carregarMapaDeRegistradoresEntrada();
		carregarMapaDeRegistradoresSaida();
	}

	//M�todo encarregado de ler e traduzir cada instru��o passada na linha de comando
	public void traduzInstrucao(String instrucao) throws Exception
	{			
		Integer[] sinal = Uc.zeraTudo(64);
		
		//1 - Remover todas as v�rgulas da instru��o
		instrucao = instrucao.replaceAll(",", "");
		
		//fazer um split na instru��o
		String[] res = instrucao.split(" ");
		String registrador;
		String registrador2;
		
		/*
		 * PARA CADA INSTRU��O (OPCODE) H� UM SINAL DE CONTROLE DIFERENTE SENDO ENVIADO PARA A MEM�RIA
		 * NOSSO SOFTWARE REAPROVEITA O FATO QUE ALGUNS OPCODES POSSUEM SINAIS BEM PARECIDOS, QUE S�
		 * MUDAM ALGUMA COISA NA SUA NATUREZA. POR ISSO FORAM DESENVOLVIDOS OS MAPAS DE OPERA��ES DA ULA
		 * E DE JUMP, QUE PROMOVEM O REUSO DE CODIGO, E PERMITEM QUE UM UNICO BLOCO GERE SINAIS DE CONTROLE
		 * DIFERENTES PARA INSTRU��ES DIFERENTES ENTRE SI.
		 * */
		
		/*
		 * QUANDO UM SINAL � MONTADO, ELE IMEDIATAMENTE � DEVOLVIDO PARA A UC, QUE DEVE EXECUT�-LO
		 * O TRABALHO DO MICROPROGRAMA CONTINUA DURANTE O CICLO DE EXECU��O, DEVOLVENDO SINAIS DE 
		 * CONTROLE PARA A UC.
		 * */
		
		//Instru��o INC
		if(res[0].equals("INC"))
		{
			//Pegar qual � o registrador para incrementar
			registrador = res[1];
			
			//Inserir a microinstru��o que manda o dado pro X
			Uc.insertMicroAppend("     t1: X = " + registrador);
			
			//Marcar a porta de sa�da do registrador como 1
			sinal[mapaDeRegistradoresSaida.get(registrador)] = 1;
			
			//Marcar a porta de entrada do X, para poder alimentar a ULA
			sinal[14] = 1;
			
			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			
			//Inserir a microinstru��o que calcula o valor de AC e joga no registrador
			Uc.insertMicroAppend("     t2: (ULA) AC = X + 1");
			Uc.insertMicroAppend("         " + registrador + " = AC");
			
			//Marcar saida do X para alimentar a ULA
			sinal[15] = 1;
			
			//Carregar sinais de controle da opera��o da ULA
			DevolveSinais(sinal, res[0]);
			
			//Depois de executada a opera��o, enviar o resultado do AC para o registrador
			sinal[16] = 1;
			sinal[mapaDeRegistradoresEntrada.get(registrador)] = 1;
			
			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			return;
		}
		
		//instru��o DEC
		if(res[0].equals("DEC"))
		{
			//Pegar qual � o registrador para decrementar
			registrador = res[1];
			
			//Inserir a microinstru��o que manda o dado pro X
			Uc.insertMicroAppend("     t1: X = " + registrador);
			
			//Marcar porta de sa�da do registrador como 1
			sinal[mapaDeRegistradoresSaida.get(registrador)] = 1;
			
			//Marcar a porta de entrada do X como 1
			sinal[14] = 1;
			
			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			
			//Inserir a microinstru��o que calcula o valor de AC e joga no registrador
			Uc.insertMicroAppend("     t2: (ULA) AC = X - 1");
			Uc.insertMicroAppend("         " + registrador + " = AC");
			
			//Marcar a porta de sa�da do X para alimentar a ULA
			sinal[15] = 1;
			
			//Carregar sinais de controle da opera��o da ULA
			DevolveSinais(sinal, res[0]);
			
			//Depois de executada a opera��o, enviar o AC para o registrador
			sinal[16] = 1;
			sinal[mapaDeRegistradoresEntrada.get(registrador)] = 1;
			
			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			return;
		}
		
		//Procedimento de produ��o de sinais din�mico - Gera sinais diferentes dentro do mesmo c�digo
		if(res[0].equals("ADD") || res[0].equals("SUB") || res[0].equals("MUL") || res[0].equals("DIV"))
		{
			//Se estivermos falando de uma multiplica��o, precisaremos mudar a l�gica dos operandos
			//Mas o funcionamento interno � o mesmo
			if(res[0].equals("MUL") || res[0].equals("DIV"))
			{
				registrador = "A";
				registrador2 = res[1];
			}
			//Caso geral: soma e subtra��o
			else
			{
			//Registrador 1 -> Recebe o operando
			registrador = res[1];
			//Registrador 2 -> Recebe o outro operando
			registrador2 = res[2];
			}
			
			//Inserir a microinstru��o que manda o primeiro operando para a ULA
			Uc.insertMicroAppend("     t1: X = " + registrador);
			
			//Marcar a porta de sa�da do registrador 1 e a porta de entrada do X
			sinal[mapaDeRegistradoresSaida.get(registrador)] = 1;
			sinal[14] = 1;
			
			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			
			//Abrir a sa�da do X pra mandar o primeiro operando pra ULA
			sinal[15] = 1;
			
			//Ainda n�o vai ser executada opera��o nenhuma na ULA;
			
			//Mandar o segundo operando pro X
			
			//Nos casos de ADD e SUB, se o segundo operando for um n�mero ou letra min�scula(hexa)
			//Nos casos de DIV e MUL, o segundo registrador NUNCA ser� uma letra min�scula ou um n�mero
			//portanto ele NUNCA entrar� aqui
			if(Character.isDigit(registrador2.charAt(0)) || (Character.isLetter(registrador2.charAt(0)) && Character.isLowerCase(registrador2.charAt(0))) || registrador2.charAt(0) == '-')
			{
				//Mandar o sinal de controle com o n�mero em quest�o ao inv�s do registrador
				sinal[32] = Integer.parseInt(registrador2, 16);
				sinal[31] = 1;
			}
			else
			{
				//Caso contr�rio, � um ADD de Registrador e, portanto, basta abrir a porta do segundo registrador
				sinal[mapaDeRegistradoresSaida.get(registrador2)] = 1;
			}
			sinal[14] = 1;
			
			//Imprimir as microinstru��es:
			Uc.insertMicroAppend("     t2: (ULA) AC = X");
			Uc.insertMicroAppend("         X = " + registrador2);
			
			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			
			//Imprimir c�lculo
			Uc.micro = Uc.micro + "     t3: (ULA) AC = AC ";
			
			//Abrir a sa�da do X pra mandar o segundo oprando pra ULA
			sinal[15] = 1;
			
			//Mandar o sinal da opera��o
			DevolveSinais(sinal, res[0]);
			
			//Enviar o AC para o registrador correto
			sinal[16] = 1;
			
			//Se for uma divis�o, o registrador correto para enviar o RESTO � o D
			if(res[0].equals("DIV"))
				sinal[mapaDeRegistradoresEntrada.get("D")] = 1;
			else
				sinal[mapaDeRegistradoresEntrada.get(registrador)] = 1;
			
			DevolverSinalParaUC(sinal);
			
			//Terminar de colocar o t3 - o registrador recebe o resultado
			Uc.insertMicroAppend("          " + registrador + " = AC");
			
			sinal = Uc.zeraTudo(64);
			
			//Como na divis�o � necess�rio enviar tamb�m o resto,
			//Esse sinal de controle s� ser� enviado na divis�o
			//O resto � enviado primeiro para o D (veja o c�digo acima)
			//O resultado da divis�o fica armazenado na ULA, e depois � enviado para o barramento, sempre para o registrador A
			//Essa foi uma manobra para que o c�lculo do resto n�o influ�sse no resultado das flags
			if(res[0].equals("DIV"))
			{
				//Imprimir microinstru��o do resto da divis�o
				Uc.insertMicroAppend("     t4: AC = AC % X");
				Uc.insertMicroAppend("         D = AC");
				
				//Enviar o resultado para o registrador A
				sinal[16] = 1;
				sinal[mapaDeRegistradoresEntrada.get("A")] = 1;
				
				DevolverSinalParaUC(sinal);
				
				sinal = Uc.zeraTudo(64);
			}
			return;
		}
		
		//Validar os diferentes tipos de MOV
		if(res[0].equals("MOV"))
		{			
			sinal = Uc.zeraTudo(64);
			
			registrador = res[1];
			registrador2 = res[2];
						
			//Verificar o tipo de MOV
			//SE O PRIMEIRO OPERANDO FOR UM REGISTRADOR - Primeira posi��o � LETRA
			if(Character.isLetter(registrador.charAt(0)))
			{
				//Se o segundo tamb�m for uma letra ou ent�o um n�mero
				if(Character.isLetter(registrador2.charAt(0)) || Character.isDigit(registrador2.charAt(0)) || registrador2.charAt(0) == '-')
				{
					//Imprimir microinstru��o simplificada
					Uc.insertMicroAppend("     t1: "+ registrador +" = " + registrador2);
					
					//E se a segunda letra tamb�m iniciar como mai�scula
					if(Character.isLetter(registrador2.charAt(0)) && Character.isUpperCase(registrador2.charAt(0)))
					{
						//� um MOV de registrador pra registrador
						//Esse MOV � Bem Simples, basta abrir as portas dos registradores
						
						//Abrir a porta de entrada do registrador 1
						sinal[mapaDeRegistradoresEntrada.get(registrador)] = 1;
						
						//Abrir a porta de sa�da do registrador 2
						sinal[mapaDeRegistradoresSaida.get(registrador2)] = 1;
						
						//Mandar executar o sinal
						DevolverSinalParaUC(sinal);
						
						sinal = Uc.zeraTudo(64);
						return;
					}
					//Se a segunda n�o for letra ou n�o iniciar como mai�scula, isso significa que ela � um n�mero em hexa
					else 
					{
						//Para atribui��es, basta jogar o n�mero no barramento e abrir a porta que vai para o registrador
						sinal[32] = Integer.parseInt(registrador2, 16);
						
						//Mandar sinal de controle para p�r o valor constante na linha de dados
						sinal[31] = 1;
						
						//Abrir apenas a porta de de entrada do registrador 1
						sinal[mapaDeRegistradoresEntrada.get(registrador)] = 1;
						
						//Mandar executar o sinal
						DevolverSinalParaUC(sinal);
						
						sinal = Uc.zeraTudo(64);
						return;
					}
				}
				//Sen�o, � um endere�o de mem�ria
				else
				{
					//1: Pegar o endere�o
					registrador2 = registrador2.replaceAll("\\[", "");
					registrador2 = registrador2.replaceAll("\\]", "");
					
					//Se o que estiver entre [] for uma letra MAI�SCULA, � um MOV do ENDERE�O GUARDADO NO REGISTRADOR
					//Nesse caso, � necess�rio pegar o endere�o que est� guardado no registrador e mand�-lo para a mem�ria
					if(Character.isLetter(registrador2.charAt(0)) && Character.isUpperCase(registrador2.charAt(0)))
					{
						//Mandar o que est� no registrador para o barramento
						sinal[mapaDeRegistradoresSaida.get(registrador2)] = 1;
						
						//Executar sinal
						DevolverSinalParaUC(sinal);
						
						sinal = Uc.zeraTudo(64);
						
						//Pedir para jogar o dado que veio do registrador na linha de endere�o do barramento
						//Pra depois ler desse endere�oo
						sinal[28] = 1;
						sinal[31] = 1;
					}
					else
					{					
						//1� Sinal - P�r o endere�o no barramento e pedir para envi�-lo � mem�ria
						sinal[32] = Integer.parseInt(registrador2, 16);
						
						//Controle de JMP para p�r o endere�o no barramento
						sinal[30] = 1;
					}
					
					//Abrir portas do MAR
					sinal[12] = 1;
					sinal[17] = 1;
					
					//Enviar o endere�o pra mem�ria
					sinal[20] = 1;
					
					//Imprimir microinstru��o relacionada
					Uc.insertMicroAppend("     t1: MAR = " + registrador2);
					
					DevolverSinalParaUC(sinal);
					
					sinal = Uc.zeraTudo(64);
					
					//2� Sinal: Ler da mem�ria e jogar no MBR
					//Mandar sinal de Address Valid e Leitura para a mem�ria
					sinal[26] = 1;
					sinal[27] = 0;
					
					//Abrir a porta da mem�ria para o barramento externo
					sinal[21] = 1;
					
					//Abrir a porta do barramento externo para o MBR
					sinal[18] = 1;
					
					//Imprimir microinstru��o da leitura da mem�ria
					Uc.insertMicroAppend("     t2: MBR = (Mem�ria)");
					
					//Executar
					DevolverSinalParaUC(sinal);
					
					sinal = Uc.zeraTudo(64);
					
					//3� Sinal: Jogar do MBR no registrador correto
					//Abrir a porta do MBR para o barramento interno
					sinal[10] = 1;
					
					//Abrir a porta de entrada do registrador
					sinal[mapaDeRegistradoresEntrada.get(registrador)] = 1;
					
					//Imprimir microinstru��o da transfer�ncia para registrador
					Uc.insertMicroAppend("     t3: "+registrador+" = MBR");
					
					//Executar
					DevolverSinalParaUC(sinal);
					
					sinal = Uc.zeraTudo(64);
					return;
				}
			}
			//Caso contr�rio, � um endere�o de mem�ria
			else
			{				
				//1: Descobrir o endere�o
				registrador = registrador.replaceAll("\\[", "");
				registrador = registrador.replaceAll("\\]", "");
				
				//Validar se o conte�do do operando � um n�mero ou um registrador
				if(Character.isLetter(registrador.charAt(0)) && Character.isUpperCase(registrador.charAt(0)))
				{
					//Se o operando for um registrador - ou seja, se uma letra mai�scula estiver entre os colchetes
					//Ent�o � preciso guardar o dado no barramento e depois mand�-lo como endere�o para escrita no MAR
					
					//Abrir a porta de sa�da do registrador
					sinal[mapaDeRegistradoresSaida.get(registrador)] = 1;
					
					//Executar o sinal
					DevolverSinalParaUC(sinal);
					
					sinal = Uc.zeraTudo(64);
					
					//Controle interno, pedir para inverter o dado com o endere�o
					sinal[28] = 1;
					sinal[31] = 1;
				}
				else
				{
					//1 - Mandar o endere�o
					sinal[32] = Integer.parseInt(registrador, 16);
					sinal[30] = 1;
				}
				
				//Abrir as portas do MAR para deixar o dado no barramento externo
				sinal[12] = 1;
				sinal[17] = 1;
				
				//Imprimir sinal de controle relacionado
				Uc.insertMicroAppend("     t1: MAR = " + registrador);
				
				//Executar instru��o
				DevolverSinalParaUC(sinal);
				
				sinal = Uc.zeraTudo(64);
				
				//Fazer a mesma valida��o de cima para o segundo operando agora.
				//Se o segundo tamb�m for uma letra
				if(Character.isLetter(registrador2.charAt(0)) || Character.isDigit(registrador2.charAt(0)))
				{
					
					//E se a segunda letra tamb�m iniciar como mai�scula
					if(Character.isLetter(registrador2.charAt(0)) && Character.isUpperCase(registrador2.charAt(0)))
					{
						//� um MOV de registrador pra endere�o de mem�ria
						//Agora � necess�rio mandar o dado pra mem�ria
						
						//Abrir a porta de sa�da do registrador 2
						sinal[mapaDeRegistradoresSaida.get(registrador2)] = 1;
						
						//Abrir a porta de entrada do MBR
						sinal[11] = 1;
						
						//Abrir a porta de sa�da do MBR
						sinal[19] = 1;
						
						//Mandar o dado para a mem�ria
						sinal[20] = 1;
						
						//Imprimir microinstru��o -> MBR recebe conte�do do registrador
						Uc.insertMicroAppend("     t2: MBR = " + registrador2);
						
						//Mandar executar o sinal
						DevolverSinalParaUC(sinal);
						
						sinal = Uc.zeraTudo(64);
						
						//Mandar Escrever o dado na mem�ria
						sinal[26] = 1;
						sinal[27] = 1;
						
						//Imprimir microinstru��o -> Mem�ria recebe o que est� em MBR
						Uc.insertMicroAppend("     t3: (Mem�ria) = MBR");
						
						//Mandar executar o sinal
						DevolverSinalParaUC(sinal);
						
						sinal = Uc.zeraTudo(64);
						return;
					}
					//Se a segunda n�o for letra ou n�o iniciar como mai�scula, isso significa que ela � um n�mero em hexa
					else 
					{
						//� UM MOV DE UMA CONSTANTE PARA UM ENDERE�O DE MEM�RIA
						//2 - Mandar o dado
						sinal[32] = Integer.parseInt(registrador2, 16);
						
						//Marcar o sinal de transfer�ncia de dado
						sinal[31] = 1;
						
						//Abrir as portas do MBR
						sinal[11] = 1;
						sinal[19] = 1;
						
						//Mandar o dado e o endere�o para a mem�ria
						sinal[20] = 1;
						
						//Imprimir microinstru��o -> MBR recebe uma constante
						Uc.insertMicroAppend("     t2: MBR = " + registrador2);
						
						//Mandar executar o sinal
						DevolverSinalParaUC(sinal);
						
						sinal = Uc.zeraTudo(64);
						
						//3 - ESCREVER O DADO NO ENDERE�O
						//Marcar Adress Valid
						sinal[26] = 1;
						//Marcar escrita
						sinal[27] = 1;
						
						//Imprimir microinstru��o -> Mem�ria recebe o que est� em MBR
						Uc.insertMicroAppend("     t3: (Mem�ria) = MBR");
						
						//Mandar executar o sinal
						DevolverSinalParaUC(sinal);
						
						sinal = Uc.zeraTudo(64);
						return;
					}
				}
			}
		}
		
		//Verificar se � uma compara��o
		if(res[0].equals("CMP"))
		{
			//Registrador 1 -> Recebe o operando
			registrador = res[1];
			//Registrador 2 -> Recebe o outro operando
			registrador2 = res[2];
			
			//Se o primeiro operando for um n�mero ou uma letra min�scula (hexa)
			if(Character.isDigit(registrador.charAt(0)) || (Character.isLetter(registrador.charAt(0)) && Character.isLowerCase(registrador.charAt(0))) || registrador.charAt(0) == '-')
			{
				//Mandar o n�mero de entrada para o barramento
				sinal[32] = Integer.parseInt(registrador, 16);
				
				//Mandar o sinal para colocar o n�mero no barramento de dados
				sinal[31] = 1;
			}
			else
			{
				//Sen�o, � um registrador mesmo
				//Marcar a porta de sa�da do registrador 1
				sinal[mapaDeRegistradoresSaida.get(registrador)] = 1;
			}
			
			//Marcar a porta de entrada do X
			sinal[14] = 1;
			
			//Imprimir microinstru��o relacionada ao envio do primeiro registrador para a mem�ria
			Uc.insertMicroAppend("     t1: X = " + registrador);
			
			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			
			//Abrir a sa�da do X pra mandar o primeiro operando pra ULA
			sinal[15] = 1;
			
			//Ainda n�o vai ser executada opera��o nenhuma na ULA;
			
			//Mandar o segundo operando pro X
			
			//Se o segundo operando for um n�mero ou letra min�scula(hexa)
			if(Character.isDigit(registrador2.charAt(0)) || (Character.isLetter(registrador2.charAt(0)) && Character.isLowerCase(registrador2.charAt(0))) || registrador2.charAt(0) == '-')
			{
				//Mandar o sinal de controle com o n�mero em quest�o ao inv�s do registrador
				sinal[32] = Integer.parseInt(registrador2, 16);
				sinal[31] = 1;
			}
			else
			{
				//Caso contr�rio, � um CMP de Registrador e, portanto, basta abrir a porta do segundo registrador
				sinal[mapaDeRegistradoresSaida.get(registrador2)] = 1;
			}
			sinal[14] = 1;
			
			//Exibir microinstru��o relacionada
			Uc.insertMicroAppend("     t2: (ULA) AC = X");
			Uc.insertMicroAppend("     t3: X = " + registrador2);
			
			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			
			//Abrir a sa�da do X pra mandar o segundo operando pra ULA
			sinal[15] = 1;
			
			//Abrir a sa�da do AC pra mandar o AC pro barramento
			sinal[16] = 1;
			
			//Mandar o sinal de compara��o
			DevolveSinais(sinal, res[0]);
			
			//Exibir sa�da da compara��o
			Uc.insertMicroAppend("     t4: AC = AC - X");
			Uc.insertMicroAppend("         (flags atualizadas)");

			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			
			return;
		}
		
		//Instru��es de Pulo
		if(res[0].equals("JMP") || res[0].equals("JZ") || res[0].equals("JNZ") || res[0].equals("JL") || res[0].equals("JG") || res[0].equals("JLE") || res[0].equals("JGE"))
		{
			registrador = res[1];
			
			//Primeiro, colocar o endere�o do PC no barramento de dados
			//Caso n�o seja necess�rio efetuar o JUMP, ele dever� receber o que j� estava nele
			sinal[8] = 1;
			
			//Executar instrucao
			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			
			//Colocar o endere�o nos bits de endere�o
			sinal[32] = Integer.parseInt(registrador, 16);
			
			//Mandar o sinal para colocar o endere�o no barramento
			DevolveSinaisJump(sinal, res[0]);
			
			//Abrir a porta do PC
			sinal[9] = 1;
				
			//Iniciar exibi��o da microinstru��o
			Uc.micro = Uc.micro + "     t1: ";
			
			//Executar instrucao
			DevolverSinalParaUC(sinal);
			
			//Finalizar exibi��o da microinstru��o de pulo
			Uc.micro = Uc.micro + "PC = " + registrador;
			
			sinal = Uc.zeraTudo(64);
			return;
		}
	
		throw new Exception("Instru��o n�o reconhecida");
	}
	
	//M�todo que recebe uma instru��o e devolve, no sinal de controle
	//os sinais correspondentes para que a ULA possa executar corretamente suas opera��es
	public void DevolveSinais(Integer[] sinal, String instrucao)
	{
		Integer[] ulaInst = mapaDeInstrucoes.get(instrucao);
		
		sinal[22] = ulaInst[0];
		sinal[23] = ulaInst[1];
		sinal[24] = ulaInst[2];
		sinal[25] = ulaInst[3];
	}
	
	//M�todo que recebe uma instru��o e devolve, no sinal de controle
	//os sinais correspondentes para que o fluxo de opera��es possa executar corretamente o jump.
	public void DevolveSinaisJump(Integer[] sinal, String instrucao)
	{
		Integer[] jumpInst = mapaDeInstrucoesJump.get(instrucao);
		
		sinal[28] = jumpInst[0];
		sinal[29] = jumpInst[1];
		sinal[30] = jumpInst[2];
		sinal[31] = jumpInst[3];
	}
	
	//Esse � o m�todo que devolve os sinais de controle para
	//que a UC possa execut�-los.
	public void DevolverSinalParaUC(Integer[] sinal)
	{
		Uc.interpretadorSinaisDeControle(sinal);
	}
}