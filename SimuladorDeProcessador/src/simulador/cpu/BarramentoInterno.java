package simulador.cpu;

//Barramento de dados interno da CPU
public class BarramentoInterno {

	//Controle de todas as portas de entrada e de saída do barramento
	Integer[] EntradaBarramentoInterno;
	Integer[] SaidaBarramentoInterno;
	
	//Dado que circula no barramento
	static Object Dado;
	static int Endereco;
		
	//Incializar os arrays de controle
	public BarramentoInterno()
	{
		EntradaBarramentoInterno = new Integer[7];
		
		EntradaBarramentoInterno[0] = 8;
		EntradaBarramentoInterno[1] = 10;
		EntradaBarramentoInterno[2] = 0;
		EntradaBarramentoInterno[3] = 1;
		EntradaBarramentoInterno[4] = 2;
		EntradaBarramentoInterno[5] = 3;
		EntradaBarramentoInterno[6] = 16;
		
		SaidaBarramentoInterno = new Integer[9];
		
		SaidaBarramentoInterno[0] = 9;
		SaidaBarramentoInterno[1] = 12;
		SaidaBarramentoInterno[2] = 13;
		SaidaBarramentoInterno[3] = 11;
		SaidaBarramentoInterno[4] = 14;
		SaidaBarramentoInterno[5] = 4;
		SaidaBarramentoInterno[6] = 5;
		SaidaBarramentoInterno[7] = 6;
		SaidaBarramentoInterno[8] = 7;
	}
	
	//Método que escreve o dado
	public static void setDado(Object dado)
	{
		Dado = dado;
	}
	
	public static void setEndereco(int endereco)
	{
		Endereco = endereco;
	}
	
	//-----------------TRANSFERIR OS DADOS DO BARRAMENTO PARA OS REGISTRADORES-------------------//
	public void enviarBarramentoPC()
	{
		Uc.PC = (Integer) Dado;
	}
	
	public void enviarBarramentoMAR()
	{
		Uc.MAR = Endereco;
	}
	
	public void enviarBarramentoIR()
	{
		Uc.IR = Dado;
	}
	
	public void enviarBarramentoMBR()
	{
		Uc.MBR = Dado;
	}
	
	public void enviarBarramentoX()
	{
		Registradores.X = (Integer) Dado;
	}
	
	public void enviarBarramentoAX()
	{
		Registradores.AX = (Integer) Dado;
	}
	
	public void enviarBarramentoBX()
	{
		Registradores.BX = (Integer) Dado;
	}
	
	public void enviarBarramentoCX()
	{
		Registradores.CX = (Integer) Dado;
	}
	
	public void enviarBarramentoDX()
	{
		Registradores.DX = (Integer) Dado;
	}
}
