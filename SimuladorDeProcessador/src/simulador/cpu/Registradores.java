package simulador.cpu;

//Classe que conter� os registradores AX, BX, CX, DX e X
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
	
	//-------------M�TODOS QUE ENVIAM O DADO DO BARRAMENTO PARA OS REGISTRADORES----------------
	
	public void EnviarAXBarramento()
	{
		BarramentoInterno.setDado(AX);
	}
	public static int getAX(){
		return AX;
	}

	public void EnviarBXBarramento()
	{
		BarramentoInterno.setDado(BX);
	}
	public static int getBX(){
		return BX;
	}
	
	public void EnviarCXBarramento()
	{
		BarramentoInterno.setDado(CX);
	}
	public static int getCX(){
		return CX;
	}

	public void EnviarDXBarramento()
	{
		BarramentoInterno.setDado(DX);
	}
	public static int getDX(){
		return DX;
	}
}
