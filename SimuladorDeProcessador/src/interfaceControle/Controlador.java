/*********************************************************************
 *                     UNIVERSIDADE DE SÃO PAULO                     *
 *               ESCOLA DE ARTES, CIÊNCIAS E HUMANIDADES             *
 *-------------------------------------------------------------------*
 * Caio Tavares Cruz - 8921840                                       *
 * Humberto Rocha Pinheiro - 7556816                                 *
 *-------------------------------------------------------------------*
 * Exercício Programa de OCD - Simulador de Processador              *
 *-------------------------------------------------------------------*
 * Descrição: Essa classe é responsável por fazer a comunicação      *
 * entre o processador (sistema) e a interface gráfica (saída)       * 
 *********************************************************************/

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
	
	//Esse método recebe o bloco de instruções e adiciona uma a uma na memória
	public void adicionaInstrucao(String instrucao)
	{
		//Como as instruções podem vir com mais de uma linha, é preciso quebrá-las
		String[] instrucoes = instrucao.split("\n");
		
		for(int i = 0; i < instrucoes.length; i++)
			Memoria.setInstrucao(instrucoes[i]);
	}
	
	//Esse método pede para que a memória seja reinicializada
	public void limpaMemoria()
	{
		Memoria.clear();
	}
	
	//Esse método é responsável por chamar o ciclo de instrução do processador
	//Para executar uma nova instrução na memória
	public static void executaInstrucoes()
	{
		uc.cicloDeInstrucao();
	}
	
	//Esse método exibe a memória no console - EM DESUSO
	public void exibeMemoria()
	{
		Memoria.imprimeMemoria();
	}
	
	//Esse método retorna o array da memória
	public /*List<Object>*/ Object[] getMemoria()
	{
		return Memoria.memoriaPrincipal;
	}
}