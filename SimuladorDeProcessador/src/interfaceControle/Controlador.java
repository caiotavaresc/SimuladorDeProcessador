package interfaceControle;

import java.util.Scanner;
import simulador.*;
import simulador.cpu.*;

public class Controlador {

	Scanner sc;
	Memoria memory;
	Uc uc;
	
	public Controlador()
	{
		sc = new Scanner(System.in);
		memory = new Memoria();
		uc = new Uc();
		Registradores.inicializaRegistradores();
	}
	
	public void adicionaInstrucao()
	{
		System.out.println("Por favor digite a instrução");
		sc.nextLine();
		String instrucao = sc.nextLine();
		memory.setInstrucao(instrucao);
		System.out.println("Instrução adicionada com sucesso!");
	}
	
	public void limpaMemoria()
	{
		memory.clear();
	}
	
	public void executaInstrucoes()
	{
		uc.cicloDeInstrucao();
	}
	
	public void exibeMemoria()
	{
		memory.imprimeMemoria();
	}
	
	public void exibeSinaisDeControle()
	{
		memory.imprimeMemoriaBits();
	}
	
	public void telaInicial()
	{
		System.out.println("O que deseja fazer?");
		System.out.println();
		System.out.println("1 - Adicionar novas instruções à memória");
		System.out.println("2 - Limpar as instruções da memória");
		System.out.println("3 - Executar as instruções da memória");
		System.out.println("4 - Visualizar o estado atual das instruções na memória");
		System.out.println("5 - Visualizar os sinais de controle da memória");
		System.out.println("6 - Sair");
		System.out.println();
		
		int saida = sc.nextInt();
		
		switch(saida)
		{
		case 1:
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
		case 5:
			exibeSinaisDeControle();
			break;
		case 6:
			System.out.println("Saindo...");;
			break;
		default:
			System.out.println("Erro");	
		}
	}
	
	public static void main(String[] args) {
		
		Controlador cont = new Controlador();
		
		System.out.println("Seja bem-vindo!");
		
		while(true)
			cont.telaInicial();
	}
}