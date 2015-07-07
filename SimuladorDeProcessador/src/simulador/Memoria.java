package simulador;
import java.util.*;

//A classe controladora ser� respons�vel pela intera��o com o usu�rio
public class Memoria {
	
	//Mem�ria principal: Guarda tanto dados quanto instru��es
	public static List<Object> memoriaPrincipal;
	public static Integer Endereco;
	public static Object Dado;
	
	//Inicializar a mem�ria
	public Memoria()
	{
		memoriaPrincipal = new ArrayList<Object>(1000);
		
	}
	
	//M�todo que escreve a instru��o em Assembly e guarda na mem�ria os sinais traduzidos
	public static void setInstrucao(String instrucao)
	{
		memoriaPrincipal.add(instrucao);
	}
	
	//M�todo que pega a instru��o em Assembly
	//Acho que n�o vai servir pra nada, mas j� tinha feito...
	public String getInstrucao(int i)
	{
		return (String) memoriaPrincipal.get(i);
	}
	
	//M�todo que limpa as instru��es das mem�rias
	public static void clear()
	{
		memoriaPrincipal.clear();
	}
	
	//Imprimir as instru��es armazenadas em mem�ria
	public static void imprimeMemoria()
	{
		Iterator<Object> it = memoriaPrincipal.iterator();
		
		while(it.hasNext())
			System.out.println(it.next());
	}
	
	//------------------------------M�TODOS DE TRANSMISS�O DE DADOS-------------------------------------
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
	
	//---------------------M�TODOS DE LEITURA E ESCRITA--------------
	public static void le()
	{
		Dado = memoriaPrincipal.get(Endereco);
	}
	
	public static void escreve()
	{
		memoriaPrincipal.set(Endereco, Dado);
	}
}