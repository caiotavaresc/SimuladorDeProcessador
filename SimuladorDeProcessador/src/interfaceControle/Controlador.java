package interfaceControle;

import java.util.Scanner;
import simulador.*;
import simulador.cpu.*;

public class Controlador {

	static Scanner sc;
	static Memoria memory;
	static Uc uc;
	
	public Controlador()
	{
		sc = new Scanner(System.in);
		memory = new Memoria();
		uc = new Uc();
		Registradores.inicializaRegistradores();
	}
	
	//Método feito para reiniciar a aplicação
	public void zerarTudo()
	{
		Uc.PC = 0;
		Uc.IR = null;
		Uc.MAR = 0;
		Uc.MBR = null;
		
		Uc.flag0 = 0;
		Uc.flagSinal = 0;
		
		Registradores.inicializaRegistradores();
	}
	
	public void adicionaInstrucao(String instrucao)
	{
		//Como as instruções podem vir com mais de uma linha, é preciso quebrá-las
		String[] instrucoes = instrucao.split("\n");
		
		for(int i = 0; i < instrucoes.length; i++)
			Memoria.setInstrucao(instrucoes[i]);
	}
	
	public void adicionaInstrucao()
	{
		System.out.println("Por favor digite a instrucao");
		sc.nextLine();
		String instrucao = sc.nextLine();
		Memoria.setInstrucao(instrucao);
		System.out.println("Instrucao adicionada com sucesso!");
	}
	
	public void limpaMemoria()
	{
		Memoria.clear();
	}
	
	public static void executaInstrucoes()
	{
		uc.cicloDeInstrucao();
	}
	
	public void exibeMemoria()
	{
		Memoria.imprimeMemoria();
	}
	
	public /*List<Object>*/ Object[] getMemoria()
	{
		return Memoria.memoriaPrincipal;
	}
	
	public void telaInicial()
	{
		System.out.println("O que deseja fazer?");
		System.out.println();
		System.out.println("1 - Adicionar novas instrucoes a memoria");
		System.out.println("2 - Limpar as instrucoes da memoria");
		System.out.println("3 - Executar as instrucoes da memoria");
		System.out.println("4 - Visualizar o estado atual das instrucoes na memoria");
		System.out.println("5 - Visualizar os sinais de controle da memoria");
		System.out.println("6 - Sair");
		System.out.println();
		
		int saida = sc.nextInt();
		
		switch(saida)
		{
		case 1:
//			adicionaInstrucao("Teste");
			adicionaInstrucao();
			break;
		case 2:
			limpaMemoria();
			break;
		case 3:
			executaInstrucoes();
			break;
		case 4:
			exibeMemoria();
			break;
		case 6:
			System.out.println("Saindo...");;
			break;
		default:
			System.out.println("Erro");	
		}
	}
	
	/*public static void main(String[] args) {
		
		Controlador cont = new Controlador();
		
		System.out.println("Seja bem-vindo!");
		
		while(true)
			cont.telaInicial();
	}*/
}