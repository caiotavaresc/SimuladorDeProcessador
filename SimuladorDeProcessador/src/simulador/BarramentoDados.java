/*********************************************************************
 *                     UNIVERSIDADE DE S�O PAULO                     *
 *               ESCOLA DE ARTES, CI�NCIAS E HUMANIDADES             *
 *-------------------------------------------------------------------*
 * Caio Tavares Cruz - 8921840                                       *
 * Humberto Rocha Pinheiro - 7556816                                 *
 *-------------------------------------------------------------------*
 * Exerc�cio Programa de OCD - Simulador de Processador              *
 *-------------------------------------------------------------------*
 * Descri��o: Essa classe representa o Barramento de Sistema de uma  *
 * arquitetura comum. Nesse barramento existem duas linhas, uma de   *
 * dados e outra de endere�os, onde circulam endere�os, dados e      *
 * instru��es.                                                       *
 *********************************************************************/

package simulador;

import simulador.cpu.*;

//Barramento de dados da mem�ria
public class BarramentoDados {

	//Dado que atualizado/pego na mem�ria
	//LINHA DE DADOS
	public static Object Dado;
	
	//Endere�o a ser atualizado/pego na mem�ria
	//LINHA DE ENDERE�OS
	public static int Endereco;
	
	//Mapa de portas de entrada e sa�da do barramento
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
	
	//-----------------M�todo para os sinais de controle inserirem o Dado/Endere�o de acesso no barramento
	public static void setDado(Object dado)
	{
		Dado = dado;
	}
	
	public static void setEndereco(int endereco)
	{
		Endereco = endereco;
	}
	
	//----------------M�todos para inserir transferir valores para as sa�das
	
	public void enviarBarramentoExtMBR()
	{
		Uc.MBR = Dado;
	}
	
	//Esse procedimento dever� gravar o DADO no ENDERE�O da mem�ria
	public void enviarDadoMemoria()
	{
		Memoria.setDado(Dado);
	}
	
	//Esse procedimento envia um endere�o para o controle da mem�ria
	public void enviarEnderecoMemoria()
	{
		Memoria.setEndereco(Endereco);
	}
}