package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import interfaceControle.*;
import simulador.*;
import simulador.cpu.*;

public class Execute {

	public JFrame frame;
	public JTextField verifyDataInput;
	public JTextField verifyDataResult;
	public JTextField textRegisterA;
	public JTextField textRegisterB;
	public JTextField textRegisterC;
	public JTextField textRegisterD;
	public JTextField textCpuMAR;
	public JTextField textCpuMBR;
	public JTextField textCpuPC;
	public JTextField textCpuIR;
	public JTextField textFlagZero;
	public JTextField textFlagSinal;
	public JTextArea textMemoryContent;
	public JTextPane textMicroinstructions;
	public JTextPane textControlSinal;
	public JButton btnVoltarFrame;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Execute window = new Execute();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	/**
	 * Create the application.
	 */
	public Execute() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 793, 469);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panelExecute = new JPanel();
		panelExecute.setBounds(0, 0, 771, 447);
		frame.getContentPane().add(panelExecute);
		panelExecute.setLayout(null);
		
		JLabel lblRegistradores = new JLabel("Registradores");
		lblRegistradores.setBounds(241, 57, 108, 20);
		panelExecute.add(lblRegistradores);
		lblRegistradores.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		
		JPanel verifyDataPanel = new JPanel();
		verifyDataPanel.setBounds(25, 307, 195, 82);
		panelExecute.add(verifyDataPanel);
		verifyDataPanel.setLayout(null);
		
		JLabel lblEndereo = new JLabel("Endere\u00E7o");
		lblEndereo.setBounds(6, 6, 57, 16);
		verifyDataPanel.add(lblEndereo);
		
		JLabel lblEmDecimal = new JLabel("Hexadecimal");
		lblEmDecimal.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		lblEmDecimal.setBounds(6, 21, 79, 13);
		verifyDataPanel.add(lblEmDecimal);
		
		verifyDataInput = new JTextField();
		verifyDataInput.setBounds(75, 6, 79, 28);
		verifyDataPanel.add(verifyDataInput);
		verifyDataInput.setColumns(10);
		
		JButton btnVerifyDataSend = new JButton("Ir");
		btnVerifyDataSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String result = verifyData(verifyDataInput.getText());
				System.out.println("Input = "+result);
				verifyDataResult.setText(result);
			}
		});
		btnVerifyDataSend.setBounds(147, 6, 48, 29);
		verifyDataPanel.add(btnVerifyDataSend);
		
		JLabel lblDado = new JLabel("Dado");
		lblDado.setBounds(6, 49, 41, 16);
		verifyDataPanel.add(lblDado);
		
		verifyDataResult = new JTextField();
		verifyDataResult.setEditable(false);
		verifyDataResult.setBounds(74, 46, 115, 28);
		verifyDataPanel.add(verifyDataResult);
		verifyDataResult.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Verificar Dado");
		lblNewLabel.setBounds(25, 286, 105, 16);
		panelExecute.add(lblNewLabel);
		
		JLabel lblExecutarInstruesDa = new JLabel("Executar Instru\u00E7\u00F5es da Mem\u00F3ria");
		lblExecutarInstruesDa.setBounds(239, 6, 317, 27);
		panelExecute.add(lblExecutarInstruesDa);
		lblExecutarInstruesDa.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblExecutarInstruesDa.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblMemria = new JLabel("Mem\u00F3ria");
		lblMemria.setBounds(23, 57, 86, 20);
		panelExecute.add(lblMemria);
		lblMemria.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		
		JPanel panelRegister = new JPanel();
		panelRegister.setBounds(241, 89, 141, 126);
		panelExecute.add(panelRegister);
		panelRegister.setLayout(null);
		
		textRegisterA = new JTextField();
		textRegisterA.setBounds(24, 5, 104, 28);
		panelRegister.add(textRegisterA);
		textRegisterA.setColumns(10);
		
		textRegisterB = new JTextField();
		textRegisterB.setBounds(24, 35, 104, 28);
		panelRegister.add(textRegisterB);
		textRegisterB.setColumns(10);
		
		textRegisterC = new JTextField();
		textRegisterC.setBounds(24, 64, 104, 28);
		panelRegister.add(textRegisterC);
		textRegisterC.setColumns(10);
		
		textRegisterD = new JTextField();
		textRegisterD.setBounds(24, 92, 105, 28);
		panelRegister.add(textRegisterD);
		textRegisterD.setColumns(10);
		
		JLabel lblA = new JLabel("A:");
		lblA.setBounds(6, 11, 18, 16);
		panelRegister.add(lblA);
		
		JLabel lblB = new JLabel("B:");
		lblB.setBounds(6, 41, 18, 16);
		panelRegister.add(lblB);
		
		JLabel lblC = new JLabel("C:");
		lblC.setBounds(6, 70, 18, 16);
		panelRegister.add(lblC);
		
		JLabel lblD = new JLabel("D:");
		lblD.setBounds(6, 98, 18, 16);
		panelRegister.add(lblD);
		
		JPanel panelCpu = new JPanel();
		panelCpu.setLayout(null);
		panelCpu.setBounds(407, 89, 165, 126);
		panelExecute.add(panelCpu);
		
		textCpuMAR = new JTextField();
		textCpuMAR.setColumns(10);
		textCpuMAR.setBounds(50, 5, 104, 28);
		panelCpu.add(textCpuMAR);
		
		textCpuMBR = new JTextField();
		textCpuMBR.setColumns(10);
		textCpuMBR.setBounds(50, 35, 104, 28);
		panelCpu.add(textCpuMBR);
		
		textCpuPC = new JTextField();
		textCpuPC.setColumns(10);
		textCpuPC.setBounds(50, 64, 104, 28);
		panelCpu.add(textCpuPC);
		
		textCpuIR = new JTextField();
		textCpuIR.setColumns(10);
		textCpuIR.setBounds(49, 92, 105, 28);
		panelCpu.add(textCpuIR);
		
		JLabel lblMar = new JLabel("MAR:");
		lblMar.setBounds(6, 11, 32, 16);
		panelCpu.add(lblMar);
		
		JLabel lblMbr = new JLabel("MBR:");
		lblMbr.setBounds(6, 41, 32, 16);
		panelCpu.add(lblMbr);
		
		JLabel lblPc = new JLabel("PC:");
		lblPc.setBounds(6, 70, 32, 16);
		panelCpu.add(lblPc);
		
		JLabel lblIr = new JLabel("IR:");
		lblIr.setBounds(6, 98, 31, 16);
		panelCpu.add(lblIr);
		
		JLabel labelCpu = new JLabel("CPU");
		labelCpu.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		labelCpu.setBounds(407, 60, 108, 20);
		panelExecute.add(labelCpu);
		
		JLabel labelFlags = new JLabel("Flags");
		labelFlags.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		labelFlags.setBounds(584, 60, 108, 20);
		panelExecute.add(labelFlags);
		
		JPanel panelFlag = new JPanel();
		panelFlag.setLayout(null);
		panelFlag.setBounds(584, 89, 165, 69);
		panelExecute.add(panelFlag);
		
		textFlagZero = new JTextField();
		textFlagZero.setEditable(false);
		textFlagZero.setColumns(10);
		textFlagZero.setBounds(50, 5, 104, 28);
		panelFlag.add(textFlagZero);
		
		textFlagSinal = new JTextField();
		textFlagSinal.setEditable(false);
		textFlagSinal.setColumns(10);
		textFlagSinal.setBounds(50, 35, 104, 28);
		panelFlag.add(textFlagSinal);
		
		JLabel lblZero = new JLabel("Zero:");
		lblZero.setBounds(6, 11, 32, 16);
		panelFlag.add(lblZero);
		
		JLabel lblSinal = new JLabel("Sinal:");
		lblSinal.setBounds(6, 41, 44, 16);
		panelFlag.add(lblSinal);
		
		JButton btnExecuteNext = new JButton("Executar Pr\u00F3xima Linha");
		btnExecuteNext.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		btnExecuteNext.setBounds(584, 170, 165, 29);
		
		btnExecuteNext.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//Executar as instruções
				Controlador.executaInstrucoes();
				
				//Atualizar os campos
				updateStates();
				
				//Colocar as microinstruções na saída
				insertMicroinstrucoesAppend(Uc.micro);
				
				//Colocar os sinais de controle na saída
				insertSinaisAppend(Uc.control);
			}
		});
		
		panelExecute.add(btnExecuteNext);
		
		JLabel lblMicroinstrues = new JLabel("Microinstru\u00E7\u00F5es");
		lblMicroinstrues.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblMicroinstrues.setBounds(241, 225, 133, 16);
		panelExecute.add(lblMicroinstrues);
		
		textMicroinstructions = new JTextPane();
		textMicroinstructions.setBounds(241, 253, 230, 136);
		
		//Inserir scroll vertical
		JScrollPane textMicroinstructions2 = new JScrollPane(textMicroinstructions);
		textMicroinstructions2.setBounds(241, 253, 230, 136);
		
		//panelExecute.add(textMicroinstructions);
		panelExecute.add(textMicroinstructions2);
		
		JLabel lblSinaisDeControle = new JLabel("Sinais de Controle");
		lblSinaisDeControle.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblSinaisDeControle.setBounds(494, 225, 150, 16);
		panelExecute.add(lblSinaisDeControle);
		
		textControlSinal = new JTextPane();
		textControlSinal.setBounds(494, 253, 255, 136);
		
		//Inserir scroll vertical
		JScrollPane textControlSinal2 = new JScrollPane(textControlSinal);
		textControlSinal2.setBounds(494, 253, 255, 136);
		
		//Colocar a scroll na tela
		panelExecute.add(textControlSinal2);
		
		JButton btnLimparMicro = new JButton("Limpar");
		btnLimparMicro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textMicroinstructions.setText("");
			}
		});
		btnLimparMicro.setBounds(378, 221, 93, 29);
		panelExecute.add(btnLimparMicro);
		
		JButton buttonLimparSignal = new JButton("Limpar");
		buttonLimparSignal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textControlSinal.setText("");
			}
		});
		buttonLimparSignal.setBounds(656, 221, 93, 29);
		panelExecute.add(buttonLimparSignal);
		
		textMemoryContent = new JTextArea();
		textMemoryContent.setEditable(false);
		textMemoryContent.setBounds(25, 89, 195, 174);
		
		//Adicionar a scroll no objeto
		JScrollPane textMemoryContent2 = new JScrollPane(textMemoryContent);
		textMemoryContent2.setBounds(25, 89, 195, 174);
		
		panelExecute.add(textMemoryContent2);
		
		btnVoltarFrame = new JButton("Voltar");
		btnVoltarFrame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnVoltarFrame.setBounds(333, 412, 117, 29);
		panelExecute.add(btnVoltarFrame);
	}
	
	public String verifyData(String input){
		String result = Memoria.getInstrucao(Integer.parseInt(input, 16));
		return result;
	}
	
	public void setMemoryContent(Object [] content){
		String memory = "";
		
		for(int i = 0; i < Memoria.indice; i++)
			memory = memory + content[i].toString() + "\n";
		
		textMemoryContent.setText(memory);
	}
	
	public void cleanFields()
	{
		// REGISTRADORES
		textRegisterA.setText("");
		textRegisterB.setText("");
		textRegisterC.setText("");
		textRegisterD.setText("");
		
		// UC
		textCpuMAR.setText("");
		textCpuMBR.setText("");
		textCpuPC.setText("");
		textCpuIR.setText("");
		
		// Flags
		textFlagZero.setText("");
		textFlagSinal.setText("");
		
		//Limpar saídas de microinstruções e sinais de controle
		textMicroinstructions.setText("");
		textControlSinal.setText("");
	}
	
	public void updateStates(){
		// REGISTRADORES
		int tempAx = Registradores.getAX();
		textRegisterA.setText("x" + Integer.toHexString(tempAx));

		int tempBx = Registradores.getBX();
		textRegisterB.setText("x" + Integer.toHexString(tempBx));
		
		int tempCx = Registradores.getCX();
		textRegisterC.setText("x" + Integer.toHexString(tempCx));
		
		int tempDx = Registradores.getDX();
		textRegisterD.setText("x" + Integer.toHexString(tempDx));
		
		// UC
		int tempMAR = Uc.MAR;
		textCpuMAR.setText("x" + Integer.toHexString(tempMAR));
		
		if(Uc.MBR instanceof Integer)
			textCpuMBR.setText("x" + Integer.toHexString((Integer) Uc.MBR));
		else
			textCpuMBR.setText(Uc.MBR.toString());
		
		int tempPC = Uc.PC;
		textCpuPC.setText("x" + Integer.toHexString(tempPC));
		
		textCpuIR.setText(Uc.IR.toString());
		
		// Flags
		int tempFlag = Uc.flag0;
		textFlagZero.setText(String.valueOf(tempFlag));
		
		int tempFlagSignal = Uc.flagSinal;
		textFlagSinal.setText(String.valueOf(tempFlagSignal));
	}
	
	public void insertMicroinstrucoesAppend(String instrucao){
		String current = textMicroinstructions.getText();
		current += (instrucao + "\n");
		textMicroinstructions.setText(current);
	}
	
	public void insertSinaisAppend(String sinal){
		String current = textControlSinal.getText();
		current += (sinal + "\n");
		textControlSinal.setText(current);
	}
}
