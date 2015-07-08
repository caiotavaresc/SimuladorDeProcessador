/*********************************************************************
 *                     UNIVERSIDADE DE SÃO PAULO                     *
 *               ESCOLA DE ARTES, CIÊNCIAS E HUMANIDADES             *
 *-------------------------------------------------------------------*
 * Caio Tavares Cruz - 8921840                                       *
 * Humberto Rocha Pinheiro - 7556816                                 *
 *-------------------------------------------------------------------*
 * Exercício Programa de OCD - Simulador de Processador              *
 *-------------------------------------------------------------------*
 * Descrição: Essa classe representa o Barramento de Sistema de uma  *
 * arquitetura comum. Nesse barramento existem duas linhas, uma de   *
 * dados e outra de endereços, onde circulam endereços, dados e      *
 * instruções.                                                       *
 *********************************************************************/

package simulador;

import simulador.cpu.*;

//Barramento de dados da memória
public class BarramentoDados {

	//Dado que atualizado/pego na memória
	//LINHA DE DADOS
	public static Object Dado;
	
	//Endereço a ser atualizado/pego na memória
	//LINHA DE ENDEREÇOS
	public static int Endereco;
	
	//Mapa de portas de entrada e saída do barramento
	public Integer[] barramentoExtEntradas;
	public Integer[] barramentoExtSaidas;
	
	//Inicializar mapas com as portas do barramento
	public BarramentoDados()
	{
		barramentoExtEntradas = new Integer[3];
		
		barramentoExtEntradas[0] = 17;
		barramentoExtEntradas[1] = 19;
		barramentoExtEntradas[2] = 21;
		
		barramentoExtSaidas = new Integer[2];
		
		barramentoExtSaidas[0] = 20;
		barramentoExtSaidas[1] = 18;
	}
	
	//-----------------Método para os sinais de controle inserirem o Dado/Endereço de acesso no barramento
	public static void setDado(Object dado)
	{
		Dado = dado;
	}
	
	public static void setEndereco(int endereco)
	{
		Endereco = endereco;
	}
	
	//----------------Métodos para inserir transferir valores para as saídas
	
	public void enviarBarramentoExtMBR()
	{
		Uc.MBR = Dado;
	}
	
	//Esse procedimento deverá gravar o DADO no ENDEREÇO da memória
	public void enviarDadoMemoria()
	{
		Memoria.setDado(Dado);
	}
	
	//Esse procedimento envia um endereço para o controle da memória
	public void enviarEnderecoMemoria()
	{
		Memoria.setEndereco(Endereco);
	}
}