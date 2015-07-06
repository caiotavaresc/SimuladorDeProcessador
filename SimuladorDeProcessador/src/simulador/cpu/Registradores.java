package simulador.cpu;

//Classe que conterá os registradores AX, BX, CX, DX e X
public class Registradores {
	
	static Integer AX, BX, CX, DX, X;
	
	//Inicializar os registradores com zero
	public static void inicializaRegistradores()
	{
		AX = 0;
		BX = 0;
		CX = 0;
		DX = 0;
		X = 0;
	}
	
	//-------------MÉTODOS QUE ENVIAM O DADO DO BARRAMENTO PARA OS REGISTRADORES----------------
	
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
