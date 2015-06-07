package simulador.cpu;

//Classe que conter� os registradores AX, BX, CX, DX e X
public class Registradores {
	
	static Integer[] AX, BX, CX, DX, X;
	
	//Inicializar os registradores com zero
	public static void inicializaRegistradores()
	{
		AX = Uc.zeraTudo(64);
		BX = Uc.zeraTudo(64);
		CX = Uc.zeraTudo(64);
		DX = Uc.zeraTudo(64);
	}
	
	//-------------M�TODOS QUE ENVIAM O DADO DO BARRAMENTO PARA OS REGISTRADORES----------------
	
	public void EnviarAXBarramento()
	{
		BarramentoInterno.setDado(AX);
	}

	public void EnviarBXBarramento()
	{
		BarramentoInterno.setDado(BX);
	}
	
	public void EnviarCXBarramento()
	{
		BarramentoInterno.setDado(CX);
	}

	public void EnviarDXBarramento()
	{
		BarramentoInterno.setDado(DX);
	}
}
