package interfaceControle;

import java.util.*;
import simulador.cpu.*;

//Classe que recebe a String com a instrução e a traduz para a arquitetura
public class TradutorDeInstrucoes {
	public List<Integer[]> instrucoesBits;
	
	//Mapas que guardarão os números das portas dos registradores e os números das instruções
	public static HashMap<String, Integer[]> mapaDeInstrucoes = new HashMap<String, Integer[]>();
	public static HashMap<String, Integer> mapaDeRegistradoresEntrada = new HashMap<String, Integer>();
	public static HashMap<String, Integer> mapaDeRegistradoresSaida = new HashMap<String, Integer>();
	
	//Mapa de Instruções estático - Adicionar os sinalizadores de instrução
	public static void carregarMapaDeInstrucoes()
	{
		Integer[] inc = {0, 0, 0, 0, 1};
		mapaDeInstrucoes.put("INC", inc);
	}
	
	//Mapa de registradores estático - Portas de entrada
	public static void carregarMapaDeRegistradoresEntrada()
	{
		mapaDeRegistradoresEntrada.put("AX", 4);
		mapaDeRegistradoresEntrada.put("BX", 5);
		mapaDeRegistradoresEntrada.put("CX", 6);
		mapaDeRegistradoresEntrada.put("DX", 7);
	}
	
	//Mapa de registradores estático - Portas de saída
	public static void carregarMapaDeRegistradoresSaida()
	{
		mapaDeRegistradoresSaida.put("AX", 0);
		mapaDeRegistradoresSaida.put("BX", 1);
		mapaDeRegistradoresSaida.put("CX", 2);
		mapaDeRegistradoresSaida.put("DX", 3);
	}
	
	//Inicializar o tradutor com o endereço da lista de instruções (memória de bits)
	public TradutorDeInstrucoes(List<Integer[]> MyinstrucoesBits)
	{
		instrucoesBits = MyinstrucoesBits;
	}

	//Método encarregado de ler e traduzir cada instrução passada na linha de comando
	public void traduzInstrucao(String instrucao)
	{	
		Integer[] sinal = Uc.zeraTudo(64);
		
		//1 - Remover todas as vírgulas da instrução
		instrucao.replaceAll(",", "");
		
		//fazer um split na instrução
		String[] res = instrucao.split(" ");
		
		//Por enquanto só tratei a instrução INC
		if(res[0].equals("INC"))
		{
			//mudar o 27º bit para 1 - Indicar a operação
			sinal[26] = 1;
			
			//Pegar qual é o registrador para incrementar
			String registrador = res[1];
			
			//Marcar a porta de saída do registrador como 1
			sinal[mapaDeRegistradoresSaida.get(registrador)] = 1;
			
			//Marcar a porta de entrada e de saída do X, para poder alimentar a ULA
			sinal[14] = 1;
			sinal[15] = 1;
			
			instrucoesBits.add(sinal);
			sinal = Uc.zeraTudo(64);
			
			//Depois de executada a operação, enviar o resultado do AC para o registrador
			sinal[16] = 1;
			sinal[mapaDeRegistradoresEntrada.get(res[1])] = 1;
			
			instrucoesBits.add(sinal);
		}
	}
}