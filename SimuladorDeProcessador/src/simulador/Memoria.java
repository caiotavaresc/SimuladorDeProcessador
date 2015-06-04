package simulador;

import java.util.*;

//A classe controladora será responsável pela interação com o usuário
public class Memoria {
	
	//Lista das instruções escritas em assembly
	public List<String> instrucoes;
	
	//Lista das palavras de controle que representam as instruções
	//A ideia é que o usuário digite a instrução e a aplicação a transforme na palavra de controle
	public List<Integer[]> instrucoesBits;
	
	//Inicializar a memória
	public Memoria()
	{
		instrucoes = new ArrayList<String>();
		instrucoesBits = new ArrayList<Integer[]>();
	}
	
	//Ainda não desenvolvi esse método porque estou estudando a melhor maneira de implementar a arquitetura
	public void conversorInstrucaoAssembly(String instrucao)
	{
		System.out.println("Conversor de instruções assembly");
	}
	
	//Método que escreve a instrução em Assembly
	public void setInstrucao(String instrucao)
	{
		instrucoes.add(instrucao);
	}
	
	//Método que pega a instrução em Assembly
	//Acho que não vai servir pra nada, mas já tinha feito...
	public String getInstrucao(int i)
	{
		return instrucoes.get(i);
	}
	
	//Método que limpa as instruções das memórias
	public void clear()
	{
		instrucoes.clear();
		instrucoesBits.clear();
	}
	
	//Imprimir as instruções armazenadas em memória
	public void imprimeMemoria()
	{
		Iterator<String> it = instrucoes.iterator();
		
		while(it.hasNext())
			System.out.println(it.next());
	}
}