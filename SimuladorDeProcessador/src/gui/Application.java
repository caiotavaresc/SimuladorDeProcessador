package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Application {
	
	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private int option;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
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
	public Application() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 314);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel panelMenu = new JPanel();
		frame.getContentPane().add(panelMenu, "name_1436233667157554000");
		panelMenu.setLayout(null);
		
		JLabel lblSejaBemVindo = new JLabel("Seja Bem Vindo!");
		lblSejaBemVindo.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblSejaBemVindo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSejaBemVindo.setBounds(110, 6, 221, 29);
		panelMenu.add(lblSejaBemVindo);
		
		JLabel lblSelecioneAOpo = new JLabel("Selecione a op\u00E7\u00E3o desejada");
		lblSelecioneAOpo.setBounds(39, 48, 182, 16);
		panelMenu.add(lblSelecioneAOpo);
		
		JRadioButton rdbtnAdicionarNovasInstrues = new JRadioButton("Adicionar novas instru\u00E7\u00F5es");
		rdbtnAdicionarNovasInstrues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOption(1);
			}
		});
		buttonGroup.add(rdbtnAdicionarNovasInstrues);
		rdbtnAdicionarNovasInstrues.setBounds(39, 76, 203, 23);
		panelMenu.add(rdbtnAdicionarNovasInstrues);
		
		JRadioButton rdbtnLimparAsInstrues = new JRadioButton("Limpar as instru\u00E7\u00F5es da mem\u00F3ria");
		rdbtnLimparAsInstrues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOption(2);
			}
		});
		buttonGroup.add(rdbtnLimparAsInstrues);
		rdbtnLimparAsInstrues.setBounds(39, 108, 250, 23);
		panelMenu.add(rdbtnLimparAsInstrues);
		
		JRadioButton rdbtnExecutarAsInstrues = new JRadioButton("Executar as instru\u00E7\u00F5es da mem\u00F3ria");
		rdbtnExecutarAsInstrues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOption(3);
			}
		});
		buttonGroup.add(rdbtnExecutarAsInstrues);
		rdbtnExecutarAsInstrues.setBounds(39, 143, 275, 23);
		panelMenu.add(rdbtnExecutarAsInstrues);
		
		JRadioButton rdbtnVisualizarOEstado = new JRadioButton("Visualizar o estado atual das instru\u00E7\u00F5es");
		rdbtnVisualizarOEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOption(4);
			}
		});
		buttonGroup.add(rdbtnVisualizarOEstado);
		rdbtnVisualizarOEstado.setBounds(39, 178, 292, 23);
		panelMenu.add(rdbtnVisualizarOEstado);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		btnSair.setBounds(99, 257, 117, 29);
		panelMenu.add(btnSair);
		
		JButton btnAvanar = new JButton("Avan\u00E7ar");
		btnAvanar.setBounds(233, 257, 117, 29);
		panelMenu.add(btnAvanar);
		
		JRadioButton rdbtnVisualizarOsSinais = new JRadioButton("Visualizar os sinais de controle da mem\u00F3ria");
		rdbtnVisualizarOsSinais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOption(5);
			}
		});
		buttonGroup.add(rdbtnVisualizarOsSinais);
		rdbtnVisualizarOsSinais.setBounds(39, 213, 311, 23);
		panelMenu.add(rdbtnVisualizarOsSinais);
	}
	
	public void setOption(int option){
		this.option = option;
	}
}
