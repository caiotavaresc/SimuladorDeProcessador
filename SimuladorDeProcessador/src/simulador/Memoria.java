package simulador;

import java.util.*;

//A classe controladora ser� respons�vel pela intera��o com o usu�rio
public class Memoria {
	
	//Lista das instru��es escritas em assembly
	public List<String> instrucoes;
	
	//Lista das palavras de controle que representam as instru��es
	//A ideia � que o usu�rio digite a instru��o e a aplica��o a transforme na palavra de controle
	public List<Integer[]> instrucoesBits;
	
	//Inicializar a mem�ria
	public Memoria()
	{
		instrucoes = new ArrayList<String>();
		instrucoesBits = new ArrayList<Integer[]>();
	}
	
	//Ainda n�o desenvolvi esse m�todo porque estou estudando a melhor maneira de implementar a arquitetura
	public void conversorInstrucaoAssembly(String instrucao)
	{
		System.out.println("Conversor de instru��es assembly");
	}
	
	//M�todo que escreve a instru��o em Assembly
	public void setInstrucao(String instrucao)
	{
		instrucoes.add(instrucao);
	}
	
	//M�todo que pega a instru��o em Assembly
	//Acho que n�o vai servir pra nada, mas j� tinha feito...
	public String getInstrucao(int i)
	{
		return instrucoes.get(i);
	}
	
	//M�todo que limpa as instru��es das mem�rias
	public void clear()
	{
		instrucoes.clear();
		instrucoesBits.clear();
	}
	
	//Imprimir as instru��es armazenadas em mem�ria
	public void imprimeMemoria()
	{
		Iterator<String> it = instrucoes.iterator();
		
		while(it.hasNext())
			System.out.println(it.next());
	}
}