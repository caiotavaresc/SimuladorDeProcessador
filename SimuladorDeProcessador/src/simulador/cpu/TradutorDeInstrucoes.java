/*********************************************************************
 *                     UNIVERSIDADE DE SÃO PAULO                     *
 *               ESCOLA DE ARTES, CIÊNCIAS E HUMANIDADES             *
 *-------------------------------------------------------------------*
 * Caio Tavares Cruz - 8921840                                       *
 * Humberto Rocha Pinheiro - 7556816                                 *
 *-------------------------------------------------------------------*
 * Exercício Programa de OCD - Simulador de Processador              *
 *-------------------------------------------------------------------*
 * Descrição: Essa classe representa o microprograma, software que   *
 * fica dentro da memória ROM e é responsável por transformar os     *
 * comandos da linguagem de montagem (Assembly) em sinais de controle*
 *********************************************************************/

package simulador.cpu;

import java.util.*;

//Classe que recebe a String com a instrução e a traduz para a arquitetura
public class TradutorDeInstrucoes {
	
	//Mapas que guardarão os números das portas dos registradores e os números das instruções
	public static HashMap<String, Integer[]> mapaDeInstrucoes = new HashMap<String, Integer[]>();
	public static HashMap<String, Integer[]> mapaDeInstrucoesJump = new HashMap<String, Integer[]>();
	public static HashMap<String, Integer> mapaDeRegistradoresEntrada = new HashMap<String, Integer>();
	public static HashMap<String, Integer> mapaDeRegistradoresSaida = new HashMap<String, Integer>();
	
	//Mapa de Instruções da ULA estático - Adicionar os sinalizadores de instrução
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
	
	//Mapa de Instruções de JUMP estático - Adicionar os sinalizadores de instrução
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
	
	//Mapa de registradores estático - Portas de entrada
	public static void carregarMapaDeRegistradoresEntrada()
	{
		mapaDeRegistradoresEntrada.put("A", 4);
		mapaDeRegistradoresEntrada.put("B", 5);
		mapaDeRegistradoresEntrada.put("C", 6);
		mapaDeRegistradoresEntrada.put("D", 7);
	}
	
	//Mapa de registradores estático - Portas de saída
	public static void carregarMapaDeRegistradoresSaida()
	{
		mapaDeRegistradoresSaida.put("A", 0);
		mapaDeRegistradoresSaida.put("B", 1);
		mapaDeRegistradoresSaida.put("C", 2);
		mapaDeRegistradoresSaida.put("D", 3);
	}
	
	//Inicializar o tradutor com o endereço da lista de instruções (memória de bits)
	public TradutorDeInstrucoes()
	{
		carregarMapaDeInstrucoes();
		carregarMapaDeInstrucoesJump();
		carregarMapaDeRegistradoresEntrada();
		carregarMapaDeRegistradoresSaida();
	}

	//Método encarregado de ler e traduzir cada instrução passada na linha de comando
	public void traduzInstrucao(String instrucao) throws Exception
	{			
		Integer[] sinal = Uc.zeraTudo(64);
		
		//1 - Remover todas as vírgulas da instrução
		instrucao = instrucao.replaceAll(",", "");
		
		//fazer um split na instrução
		String[] res = instrucao.split(" ");
		String registrador;
		String registrador2;
		
		/*
		 * PARA CADA INSTRUÇÃO (OPCODE) HÁ UM SINAL DE CONTROLE DIFERENTE SENDO ENVIADO PARA A MEMÓRIA
		 * NOSSO SOFTWARE REAPROVEITA O FATO QUE ALGUNS OPCODES POSSUEM SINAIS BEM PARECIDOS, QUE SÓ
		 * MUDAM ALGUMA COISA NA SUA NATUREZA. POR ISSO FORAM DESENVOLVIDOS OS MAPAS DE OPERAÇÕES DA ULA
		 * E DE JUMP, QUE PROMOVEM O REUSO DE CODIGO, E PERMITEM QUE UM UNICO BLOCO GERE SINAIS DE CONTROLE
		 * DIFERENTES PARA INSTRUÇÕES DIFERENTES ENTRE SI.
		 * */
		
		/*
		 * QUANDO UM SINAL É MONTADO, ELE IMEDIATAMENTE É DEVOLVIDO PARA A UC, QUE DEVE EXECUTÁ-LO
		 * O TRABALHO DO MICROPROGRAMA CONTINUA DURANTE O CICLO DE EXECUÇÃO, DEVOLVENDO SINAIS DE 
		 * CONTROLE PARA A UC.
		 * */
		
		//Instrução INC
		if(res[0].equals("INC"))
		{
			//Pegar qual é o registrador para incrementar
			registrador = res[1];
			
			//Inserir a microinstrução que manda o dado pro X
			Uc.insertMicroAppend("     t1: X = " + registrador);
			
			//Marcar a porta de saída do registrador como 1
			sinal[mapaDeRegistradoresSaida.get(registrador)] = 1;
			
			//Marcar a porta de entrada do X, para poder alimentar a ULA
			sinal[14] = 1;
			
			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			
			//Inserir a microinstrução que calcula o valor de AC e joga no registrador
			Uc.insertMicroAppend("     t2: (ULA) AC = X + 1");
			Uc.insertMicroAppend("         " + registrador + " = AC");
			
			//Marcar saida do X para alimentar a ULA
			sinal[15] = 1;
			
			//Carregar sinais de controle da operação da ULA
			DevolveSinais(sinal, res[0]);
			
			//Depois de executada a operação, enviar o resultado do AC para o registrador
			sinal[16] = 1;
			sinal[mapaDeRegistradoresEntrada.get(registrador)] = 1;
			
			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			return;
		}
		
		//instrução DEC
		if(res[0].equals("DEC"))
		{
			//Pegar qual é o registrador para decrementar
			registrador = res[1];
			
			//Inserir a microinstrução que manda o dado pro X
			Uc.insertMicroAppend("     t1: X = " + registrador);
			
			//Marcar porta de saída do registrador como 1
			sinal[mapaDeRegistradoresSaida.get(registrador)] = 1;
			
			//Marcar a porta de entrada do X como 1
			sinal[14] = 1;
			
			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			
			//Inserir a microinstrução que calcula o valor de AC e joga no registrador
			Uc.insertMicroAppend("     t2: (ULA) AC = X - 1");
			Uc.insertMicroAppend("         " + registrador + " = AC");
			
			//Marcar a porta de saída do X para alimentar a ULA
			sinal[15] = 1;
			
			//Carregar sinais de controle da operação da ULA
			DevolveSinais(sinal, res[0]);
			
			//Depois de executada a operação, enviar o AC para o registrador
			sinal[16] = 1;
			sinal[mapaDeRegistradoresEntrada.get(registrador)] = 1;
			
			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			return;
		}
		
		//Procedimento de produção de sinais dinâmico - Gera sinais diferentes dentro do mesmo código
		if(res[0].equals("ADD") || res[0].equals("SUB") || res[0].equals("MUL") || res[0].equals("DIV"))
		{
			//Se estivermos falando de uma multiplicação, precisaremos mudar a lógica dos operandos
			//Mas o funcionamento interno é o mesmo
			if(res[0].equals("MUL") || res[0].equals("DIV"))
			{
				registrador = "A";
				registrador2 = res[1];
			}
			//Caso geral: soma e subtração
			else
			{
			//Registrador 1 -> Recebe o operando
			registrador = res[1];
			//Registrador 2 -> Recebe o outro operando
			registrador2 = res[2];
			}
			
			//Inserir a microinstrução que manda o primeiro operando para a ULA
			Uc.insertMicroAppend("     t1: X = " + registrador);
			
			//Marcar a porta de saída do registrador 1 e a porta de entrada do X
			sinal[mapaDeRegistradoresSaida.get(registrador)] = 1;
			sinal[14] = 1;
			
			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			
			//Abrir a saída do X pra mandar o primeiro operando pra ULA
			sinal[15] = 1;
			
			//Ainda não vai ser executada operação nenhuma na ULA;
			
			//Mandar o segundo operando pro X
			
			//Nos casos de ADD e SUB, se o segundo operando for um número ou letra minúscula(hexa)
			//Nos casos de DIV e MUL, o segundo registrador NUNCA será uma letra minúscula ou um número
			//portanto ele NUNCA entrará aqui
			if(Character.isDigit(registrador2.charAt(0)) || (Character.isLetter(registrador2.charAt(0)) && Character.isLowerCase(registrador2.charAt(0))) || registrador2.charAt(0) == '-')
			{
				//Mandar o sinal de controle com o número em questão ao invés do registrador
				sinal[32] = Integer.parseInt(registrador2, 16);
				sinal[31] = 1;
			}
			else
			{
				//Caso contrário, é um ADD de Registrador e, portanto, basta abrir a porta do segundo registrador
				sinal[mapaDeRegistradoresSaida.get(registrador2)] = 1;
			}
			sinal[14] = 1;
			
			//Imprimir as microinstruções:
			Uc.insertMicroAppend("     t2: (ULA) AC = X");
			Uc.insertMicroAppend("         X = " + registrador2);
			
			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			
			//Imprimir cálculo
			Uc.micro = Uc.micro + "     t3: (ULA) AC = AC ";
			
			//Abrir a saída do X pra mandar o segundo oprando pra ULA
			sinal[15] = 1;
			
			//Mandar o sinal da operação
			DevolveSinais(sinal, res[0]);
			
			//Enviar o AC para o registrador correto
			sinal[16] = 1;
			
			//Se for uma divisão, o registrador correto para enviar o RESTO é o D
			if(res[0].equals("DIV"))
				sinal[mapaDeRegistradoresEntrada.get("D")] = 1;
			else
				sinal[mapaDeRegistradoresEntrada.get(registrador)] = 1;
			
			DevolverSinalParaUC(sinal);
			
			//Terminar de colocar o t3 - o registrador recebe o resultado
			Uc.insertMicroAppend("          " + registrador + " = AC");
			
			sinal = Uc.zeraTudo(64);
			
			//Como na divisão é necessário enviar também o resto,
			//Esse sinal de controle só será enviado na divisão
			//O resto é enviado primeiro para o D (veja o código acima)
			//O resultado da divisão fica armazenado na ULA, e depois é enviado para o barramento, sempre para o registrador A
			//Essa foi uma manobra para que o cálculo do resto não influísse no resultado das flags
			if(res[0].equals("DIV"))
			{
				//Imprimir microinstrução do resto da divisão
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
			//SE O PRIMEIRO OPERANDO FOR UM REGISTRADOR - Primeira posição é LETRA
			if(Character.isLetter(registrador.charAt(0)))
			{
				//Se o segundo também for uma letra ou então um número
				if(Character.isLetter(registrador2.charAt(0)) || Character.isDigit(registrador2.charAt(0)) || registrador2.charAt(0) == '-')
				{
					//Imprimir microinstrução simplificada
					Uc.insertMicroAppend("     t1: "+ registrador +" = " + registrador2);
					
					//E se a segunda letra também iniciar como maiúscula
					if(Character.isLetter(registrador2.charAt(0)) && Character.isUpperCase(registrador2.charAt(0)))
					{
						//É um MOV de registrador pra registrador
						//Esse MOV é Bem Simples, basta abrir as portas dos registradores
						
						//Abrir a porta de entrada do registrador 1
						sinal[mapaDeRegistradoresEntrada.get(registrador)] = 1;
						
						//Abrir a porta de saída do registrador 2
						sinal[mapaDeRegistradoresSaida.get(registrador2)] = 1;
						
						//Mandar executar o sinal
						DevolverSinalParaUC(sinal);
						
						sinal = Uc.zeraTudo(64);
						return;
					}
					//Se a segunda não for letra ou não iniciar como maiúscula, isso significa que ela é um número em hexa
					else 
					{
						//Para atribuições, basta jogar o número no barramento e abrir a porta que vai para o registrador
						sinal[32] = Integer.parseInt(registrador2, 16);
						
						//Mandar sinal de controle para pôr o valor constante na linha de dados
						sinal[31] = 1;
						
						//Abrir apenas a porta de de entrada do registrador 1
						sinal[mapaDeRegistradoresEntrada.get(registrador)] = 1;
						
						//Mandar executar o sinal
						DevolverSinalParaUC(sinal);
						
						sinal = Uc.zeraTudo(64);
						return;
					}
				}
				//Senão, é um endereço de memória
				else
				{
					//1: Pegar o endereço
					registrador2 = registrador2.replaceAll("\\[", "");
					registrador2 = registrador2.replaceAll("\\]", "");
					
					//Se o que estiver entre [] for uma letra MAIÚSCULA, é um MOV do ENDEREÇO GUARDADO NO REGISTRADOR
					//Nesse caso, é necessário pegar o endereço que está guardado no registrador e mandá-lo para a memória
					if(Character.isLetter(registrador2.charAt(0)) && Character.isUpperCase(registrador2.charAt(0)))
					{
						//Mandar o que está no registrador para o barramento
						sinal[mapaDeRegistradoresSaida.get(registrador2)] = 1;
						
						//Executar sinal
						DevolverSinalParaUC(sinal);
						
						sinal = Uc.zeraTudo(64);
						
						//Pedir para jogar o dado que veio do registrador na linha de endereço do barramento
						//Pra depois ler desse endereçoo
						sinal[28] = 1;
						sinal[31] = 1;
					}
					else
					{					
						//1º Sinal - Pôr o endereço no barramento e pedir para enviá-lo à memória
						sinal[32] = Integer.parseInt(registrador2, 16);
						
						//Controle de JMP para pôr o endereço no barramento
						sinal[30] = 1;
					}
					
					//Abrir portas do MAR
					sinal[12] = 1;
					sinal[17] = 1;
					
					//Enviar o endereço pra memória
					sinal[20] = 1;
					
					//Imprimir microinstrução relacionada
					Uc.insertMicroAppend("     t1: MAR = " + registrador2);
					
					DevolverSinalParaUC(sinal);
					
					sinal = Uc.zeraTudo(64);
					
					//2º Sinal: Ler da memória e jogar no MBR
					//Mandar sinal de Address Valid e Leitura para a memória
					sinal[26] = 1;
					sinal[27] = 0;
					
					//Abrir a porta da memória para o barramento externo
					sinal[21] = 1;
					
					//Abrir a porta do barramento externo para o MBR
					sinal[18] = 1;
					
					//Imprimir microinstrução da leitura da memória
					Uc.insertMicroAppend("     t2: MBR = (Memória)");
					
					//Executar
					DevolverSinalParaUC(sinal);
					
					sinal = Uc.zeraTudo(64);
					
					//3º Sinal: Jogar do MBR no registrador correto
					//Abrir a porta do MBR para o barramento interno
					sinal[10] = 1;
					
					//Abrir a porta de entrada do registrador
					sinal[mapaDeRegistradoresEntrada.get(registrador)] = 1;
					
					//Imprimir microinstrução da transferência para registrador
					Uc.insertMicroAppend("     t3: "+registrador+" = MBR");
					
					//Executar
					DevolverSinalParaUC(sinal);
					
					sinal = Uc.zeraTudo(64);
					return;
				}
			}
			//Caso contrário, é um endereço de memória
			else
			{				
				//1: Descobrir o endereço
				registrador = registrador.replaceAll("\\[", "");
				registrador = registrador.replaceAll("\\]", "");
				
				//Validar se o conteúdo do operando é um número ou um registrador
				if(Character.isLetter(registrador.charAt(0)) && Character.isUpperCase(registrador.charAt(0)))
				{
					//Se o operando for um registrador - ou seja, se uma letra maiúscula estiver entre os colchetes
					//Então é preciso guardar o dado no barramento e depois mandá-lo como endereço para escrita no MAR
					
					//Abrir a porta de saída do registrador
					sinal[mapaDeRegistradoresSaida.get(registrador)] = 1;
					
					//Executar o sinal
					DevolverSinalParaUC(sinal);
					
					sinal = Uc.zeraTudo(64);
					
					//Controle interno, pedir para inverter o dado com o endereço
					sinal[28] = 1;
					sinal[31] = 1;
				}
				else
				{
					//1 - Mandar o endereço
					sinal[32] = Integer.parseInt(registrador, 16);
					sinal[30] = 1;
				}
				
				//Abrir as portas do MAR para deixar o dado no barramento externo
				sinal[12] = 1;
				sinal[17] = 1;
				
				//Imprimir sinal de controle relacionado
				Uc.insertMicroAppend("     t1: MAR = " + registrador);
				
				//Executar instrução
				DevolverSinalParaUC(sinal);
				
				sinal = Uc.zeraTudo(64);
				
				//Fazer a mesma validação de cima para o segundo operando agora.
				//Se o segundo também for uma letra
				if(Character.isLetter(registrador2.charAt(0)) || Character.isDigit(registrador2.charAt(0)))
				{
					
					//E se a segunda letra também iniciar como maiúscula
					if(Character.isLetter(registrador2.charAt(0)) && Character.isUpperCase(registrador2.charAt(0)))
					{
						//É um MOV de registrador pra endereço de memória
						//Agora é necessário mandar o dado pra memória
						
						//Abrir a porta de saída do registrador 2
						sinal[mapaDeRegistradoresSaida.get(registrador2)] = 1;
						
						//Abrir a porta de entrada do MBR
						sinal[11] = 1;
						
						//Abrir a porta de saída do MBR
						sinal[19] = 1;
						
						//Mandar o dado para a memória
						sinal[20] = 1;
						
						//Imprimir microinstrução -> MBR recebe conteúdo do registrador
						Uc.insertMicroAppend("     t2: MBR = " + registrador2);
						
						//Mandar executar o sinal
						DevolverSinalParaUC(sinal);
						
						sinal = Uc.zeraTudo(64);
						
						//Mandar Escrever o dado na memória
						sinal[26] = 1;
						sinal[27] = 1;
						
						//Imprimir microinstrução -> Memória recebe o que está em MBR
						Uc.insertMicroAppend("     t3: (Memória) = MBR");
						
						//Mandar executar o sinal
						DevolverSinalParaUC(sinal);
						
						sinal = Uc.zeraTudo(64);
						return;
					}
					//Se a segunda não for letra ou não iniciar como maiúscula, isso significa que ela é um número em hexa
					else 
					{
						//É UM MOV DE UMA CONSTANTE PARA UM ENDEREÇO DE MEMÓRIA
						//2 - Mandar o dado
						sinal[32] = Integer.parseInt(registrador2, 16);
						
						//Marcar o sinal de transferência de dado
						sinal[31] = 1;
						
						//Abrir as portas do MBR
						sinal[11] = 1;
						sinal[19] = 1;
						
						//Mandar o dado e o endereço para a memória
						sinal[20] = 1;
						
						//Imprimir microinstrução -> MBR recebe uma constante
						Uc.insertMicroAppend("     t2: MBR = " + registrador2);
						
						//Mandar executar o sinal
						DevolverSinalParaUC(sinal);
						
						sinal = Uc.zeraTudo(64);
						
						//3 - ESCREVER O DADO NO ENDEREÇO
						//Marcar Adress Valid
						sinal[26] = 1;
						//Marcar escrita
						sinal[27] = 1;
						
						//Imprimir microinstrução -> Memória recebe o que está em MBR
						Uc.insertMicroAppend("     t3: (Memória) = MBR");
						
						//Mandar executar o sinal
						DevolverSinalParaUC(sinal);
						
						sinal = Uc.zeraTudo(64);
						return;
					}
				}
			}
		}
		
		//Verificar se é uma comparação
		if(res[0].equals("CMP"))
		{
			//Registrador 1 -> Recebe o operando
			registrador = res[1];
			//Registrador 2 -> Recebe o outro operando
			registrador2 = res[2];
			
			//Se o primeiro operando for um número ou uma letra minúscula (hexa)
			if(Character.isDigit(registrador.charAt(0)) || (Character.isLetter(registrador.charAt(0)) && Character.isLowerCase(registrador.charAt(0))) || registrador.charAt(0) == '-')
			{
				//Mandar o número de entrada para o barramento
				sinal[32] = Integer.parseInt(registrador, 16);
				
				//Mandar o sinal para colocar o número no barramento de dados
				sinal[31] = 1;
			}
			else
			{
				//Senão, é um registrador mesmo
				//Marcar a porta de saída do registrador 1
				sinal[mapaDeRegistradoresSaida.get(registrador)] = 1;
			}
			
			//Marcar a porta de entrada do X
			sinal[14] = 1;
			
			//Imprimir microinstrução relacionada ao envio do primeiro registrador para a memória
			Uc.insertMicroAppend("     t1: X = " + registrador);
			
			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			
			//Abrir a saída do X pra mandar o primeiro operando pra ULA
			sinal[15] = 1;
			
			//Ainda não vai ser executada operação nenhuma na ULA;
			
			//Mandar o segundo operando pro X
			
			//Se o segundo operando for um número ou letra minúscula(hexa)
			if(Character.isDigit(registrador2.charAt(0)) || (Character.isLetter(registrador2.charAt(0)) && Character.isLowerCase(registrador2.charAt(0))) || registrador2.charAt(0) == '-')
			{
				//Mandar o sinal de controle com o número em questão ao invés do registrador
				sinal[32] = Integer.parseInt(registrador2, 16);
				sinal[31] = 1;
			}
			else
			{
				//Caso contrário, é um CMP de Registrador e, portanto, basta abrir a porta do segundo registrador
				sinal[mapaDeRegistradoresSaida.get(registrador2)] = 1;
			}
			sinal[14] = 1;
			
			//Exibir microinstrução relacionada
			Uc.insertMicroAppend("     t2: (ULA) AC = X");
			Uc.insertMicroAppend("     t3: X = " + registrador2);
			
			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			
			//Abrir a saída do X pra mandar o segundo operando pra ULA
			sinal[15] = 1;
			
			//Abrir a saída do AC pra mandar o AC pro barramento
			sinal[16] = 1;
			
			//Mandar o sinal de comparação
			DevolveSinais(sinal, res[0]);
			
			//Exibir saída da comparação
			Uc.insertMicroAppend("     t4: AC = AC - X");
			Uc.insertMicroAppend("         (flags atualizadas)");

			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			
			return;
		}
		
		//Instruções de Pulo
		if(res[0].equals("JMP") || res[0].equals("JZ") || res[0].equals("JNZ") || res[0].equals("JL") || res[0].equals("JG") || res[0].equals("JLE") || res[0].equals("JGE"))
		{
			registrador = res[1];
			
			//Primeiro, colocar o endereço do PC no barramento de dados
			//Caso não seja necessário efetuar o JUMP, ele deverá receber o que já estava nele
			sinal[8] = 1;
			
			//Executar instrucao
			DevolverSinalParaUC(sinal);
			
			sinal = Uc.zeraTudo(64);
			
			//Colocar o endereço nos bits de endereço
			sinal[32] = Integer.parseInt(registrador, 16);
			
			//Mandar o sinal para colocar o endereço no barramento
			DevolveSinaisJump(sinal, res[0]);
			
			//Abrir a porta do PC
			sinal[9] = 1;
				
			//Iniciar exibição da microinstrução
			Uc.micro = Uc.micro + "     t1: ";
			
			//Executar instrucao
			DevolverSinalParaUC(sinal);
			
			//Finalizar exibição da microinstrução de pulo
			Uc.micro = Uc.micro + "PC = " + registrador;
			
			sinal = Uc.zeraTudo(64);
			return;
		}
	
		throw new Exception("Instrução não reconhecida");
	}
	
	//Método que recebe uma instrução e devolve, no sinal de controle
	//os sinais correspondentes para que a ULA possa executar corretamente suas operações
	public void DevolveSinais(Integer[] sinal, String instrucao)
	{
		Integer[] ulaInst = mapaDeInstrucoes.get(instrucao);
		
		sinal[22] = ulaInst[0];
		sinal[23] = ulaInst[1];
		sinal[24] = ulaInst[2];
		sinal[25] = ulaInst[3];
	}
	
	//Método que recebe uma instrução e devolve, no sinal de controle
	//os sinais correspondentes para que o fluxo de operações possa executar corretamente o jump.
	public void DevolveSinaisJump(Integer[] sinal, String instrucao)
	{
		Integer[] jumpInst = mapaDeInstrucoesJump.get(instrucao);
		
		sinal[28] = jumpInst[0];
		sinal[29] = jumpInst[1];
		sinal[30] = jumpInst[2];
		sinal[31] = jumpInst[3];
	}
	
	//Esse é o método que devolve os sinais de controle para
	//que a UC possa executá-los.
	public void DevolverSinalParaUC(Integer[] sinal)
	{
		Uc.interpretadorSinaisDeControle(sinal);
	}
}