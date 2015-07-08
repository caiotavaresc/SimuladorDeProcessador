package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;

public class Execute {

	private JFrame frame;
	private JTextField verifyDataInput;
	private JTextField verifyDataResult;
	private JTextField textRegisterA;
	private JTextField textRegisterB;
	private JTextField textRegisterC;
	private JTextField textRegisterD;
	private JTextField textCpuMAR;
	private JTextField textCpuMBR;
	private JTextField textCpuPC;
	private JTextField textCpuIR;
	private JTextField textFlagZero;
	private JTextField textFlagSinal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Execute window = new Execute();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
		frame.setBounds(100, 100, 771, 430);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panelExecute = new JPanel();
		panelExecute.setBounds(0, 0, 771, 408);
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
		
		JLabel lblEmDecimal = new JLabel("Em Decimal");
		lblEmDecimal.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblEmDecimal.setBounds(6, 21, 62, 16);
		verifyDataPanel.add(lblEmDecimal);
		
		verifyDataInput = new JTextField();
		verifyDataInput.setBounds(75, 6, 79, 28);
		verifyDataPanel.add(verifyDataInput);
		verifyDataInput.setColumns(10);
		
		JButton btnVerifyDataSend = new JButton("Ir");
		btnVerifyDataSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnVerifyDataSend.setBounds(147, 6, 48, 29);
		verifyDataPanel.add(btnVerifyDataSend);
		
		JLabel lblDado = new JLabel("Dado");
		lblDado.setBounds(6, 49, 41, 16);
		verifyDataPanel.add(lblDado);
		
		verifyDataResult = new JTextField();
		verifyDataResult.setBounds(74, 46, 115, 28);
		verifyDataPanel.add(verifyDataResult);
		verifyDataResult.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Verificar Dado");
		lblNewLabel.setBounds(25, 286, 105, 16);
		panelExecute.add(lblNewLabel);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(22, 87, 195, 175);
		panelExecute.add(textPane);
		
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
		textFlagZero.setColumns(10);
		textFlagZero.setBounds(50, 5, 104, 28);
		panelFlag.add(textFlagZero);
		
		textFlagSinal = new JTextField();
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
		panelExecute.add(btnExecuteNext);
		
		JLabel lblMicroinstrues = new JLabel("Microinstru\u00E7\u00F5es");
		lblMicroinstrues.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblMicroinstrues.setBounds(241, 225, 133, 16);
		panelExecute.add(lblMicroinstrues);
		
		JTextPane textMicroinstructions = new JTextPane();
		textMicroinstructions.setBounds(241, 253, 230, 136);
		panelExecute.add(textMicroinstructions);
		
		JLabel lblSinaisDeControle = new JLabel("Sinais de Controle");
		lblSinaisDeControle.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblSinaisDeControle.setBounds(494, 225, 150, 16);
		panelExecute.add(lblSinaisDeControle);
		
		JTextPane textControlSinal = new JTextPane();
		textControlSinal.setBounds(494, 253, 255, 136);
		panelExecute.add(textControlSinal);
		
		JButton btnLimparMicro = new JButton("Limpar");
		btnLimparMicro.setBounds(378, 221, 93, 29);
		panelExecute.add(btnLimparMicro);
		
		JButton buttonLimparSignal = new JButton("Limpar");
		buttonLimparSignal.setBounds(656, 221, 93, 29);
		panelExecute.add(buttonLimparSignal);
	}
}
