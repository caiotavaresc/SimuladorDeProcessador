package simulador.cpu;

//Unidade de Lógica e Aritmética, destinada a efetuar os cálculos
//O único registrador que a ULA possui é o AC
public class Ula {
	
	static Integer[] AC;
	
	//Métodos para enviar dados para o barramento interno
	public void EnviarACBarramento()
	{
		BarramentoInterno.setDado(AC);
	}
}
