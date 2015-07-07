package simulador;
import java.util.*;

//A classe controladora sera responsavel pela interacoes com o usuario
public class Memoria {
	
	//Memoria principal: Guarda tanto dados quanto instrucoes
	public static List<Object> memoriaPrincipal2;
	public static Object[] memoriaPrincipal;
	public static int indice;
	public static Integer Endereco;
	public static Object Dado;
	
	//Inicializar a memoria
	public Memoria()
	{
		//memoriaPrincipal = new ArrayList<Object>(1000);
		memoriaPrincipal = new Object[1000];
		indice = 0;
		
	}
	
	//Metodo que escreve a instrucoes em Assembly e guarda na memoria os sinais traduzidos
	public static void setInstrucao(String instrucao)
	{
		//memoriaPrincipal.add(instrucao);
		memoriaPrincipal[indice++] = instrucao;
	}
	
	//Metodo que pega a instrucao em Assembly
	//Acho que nao vai servir pra nada, mas ja tinha feito...
	public static String getInstrucao(int i)
	{
		//return (String) memoriaPrincipal.get(i);
		if(memoriaPrincipal[i] == null)
			return "";
		else
			return (String) memoriaPrincipal[i];
	}
	
	//Metodo que limpa as instrucoes das memorias
	public static void clear()
	{
		//memoriaPrincipal.clear();
		for(int i = 0; i < memoriaPrincipal.length; i++)
			memoriaPrincipal[i] = null;
	}
	
	//Imprimir as instrucoes armazenadas em memoria
	public static void imprimeMemoria()
	{
		//Iterator<Object> it = memoriaPrincipal.iterator();
		
		//while(it.hasNext())
		//	System.out.println(it.next());
		
		for(int i = 0; i < indice; i++)
			System.out.println(memoriaPrincipal[i]);
	}
	
	//------------------------------METODOS DE TRANSMISS�O DE DADOS-------------------------------------
	public static void EnviarDadoParaBarramento()
	{
		BarramentoDados.setDado(Dado);
	}
	
	public static void setDado(Object dado)
	{
		Dado = dado;
	}
	
	public static void setEndereco(int endereco)
	{
		Endereco = endereco;
	}
	
	//---------------------METODOS DE LEITURA E ESCRITA--------------
	public static void le()
	{
		//Dado = memoriaPrincipal.get(Endereco);
		Dado = memoriaPrincipal[Endereco];
	}
	
	public static void escreve()
	{
		//memoriaPrincipal.set(Endereco, Dado);
		memoriaPrincipal[Endereco] = Dado;
	}
}