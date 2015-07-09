/*********************************************************************
 *                     UNIVERSIDADE DE SÃO PAULO                     *
 *               ESCOLA DE ARTES, CIÊNCIAS E HUMANIDADES             *
 *-------------------------------------------------------------------*
 * Caio Tavares Cruz - 8921840                                       *
 * Humberto Rocha Pinheiro - 7556816                                 *
 *-------------------------------------------------------------------*
 * Exercício Programa de OCD - Simulador de Processador              *
 *-------------------------------------------------------------------*
 * Descrição: Essa classe é responsável por construir a interface    *
 * gráfica do programa.                                              * 
 *********************************************************************/

package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import interfaceControle.*;
import simulador.*;
import simulador.cpu.Uc;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class Application {
	
	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private static Execute execute;
	
	private JPanel panelAddInstruction;
	private JPanel panelMenu;
	private JPanel panelMemoryList;
	
	private static Controlador controlador;
	
	private int option;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		controlador = new Controlador();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					execute = new Execute();
//					execute.frame.setVisible(true);
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
		frame = new JFrame("Simulador de Processador");
		frame.setBounds(100, 100, 450, 314);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		final JPanel panelMenu = new JPanel();
		frame.getContentPane().add(panelMenu, "name_1436233667157554000");
		panelMenu.setLayout(null);
		
		final JPanel panelAddInstruction = new JPanel();
		frame.getContentPane().add(panelAddInstruction, "name_1436234348321566000");
		panelAddInstruction.setLayout(null);
		
		final JPanel panelMemoryList = new JPanel();
		frame.getContentPane().add(panelMemoryList, "name_1436244150244540000");
		panelMemoryList.setLayout(null);
		
		JLabel lblExibioDados = new JLabel("Exibição - Dados da Memória");
		lblExibioDados.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblExibioDados.setBounds(82, 19, 286, 22);
		panelMemoryList.add(lblExibioDados);
		
		final JTextPane textMemoryContent = new JTextPane();
		textMemoryContent.setBounds(82, 73, 286, 144);
		
		JScrollPane textMemoryContent2 = new JScrollPane(textMemoryContent);
		textMemoryContent2.setBounds(82, 73, 286, 144);
		panelMemoryList.add(textMemoryContent2);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMemoryList.setVisible(false);
				panelMenu.setVisible(true);
			}
		});
		btnVoltar.setBounds(166, 245, 117, 29);
		panelMemoryList.add(btnVoltar);
		
		
		JLabel lblNewLabel = new JLabel("Adicionar Instru\u00E7\u00E3o");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(128, 17, 193, 26);
		panelAddInstruction.add(lblNewLabel);
		
		final JTextArea instructionAdd = new JTextArea();
		instructionAdd.setBounds(76, 99, 278, 132);
		
		JScrollPane instructionAdd2 = new JScrollPane(instructionAdd);
		instructionAdd2.setBounds(76, 99, 278, 132);
		
		panelAddInstruction.add(instructionAdd2);
		panelAddInstruction.setVisible(false);
		
		//O Botão adicionar, quando pressionado, chama o método de adição da classe controlador
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(instructionAdd.getText().length() > 0){
					controlador.adicionaInstrucao(instructionAdd.getText());
					JOptionPane.showMessageDialog(frame, "Instruções adicionadas com sucesso");
					panelAddInstruction.setVisible(false);
					instructionAdd.setText("");
					panelMenu.setVisible(true);
				}else{
					JOptionPane.showMessageDialog(frame, "Insira uma instrução válida", "Erro", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnAdicionar.setBounds(166, 246, 117, 29);
		panelAddInstruction.add(btnAdicionar);
		
		JLabel lblDigiteAInstruo = new JLabel("Digite as instruções no campo abaixo");
		lblDigiteAInstruo.setBounds(103, 55, 244, 16);
		panelAddInstruction.add(lblDigiteAInstruo);
		
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
		
		JRadioButton rdbtnLimparAsInstrues = new JRadioButton("Reiniciar a Aplicação");
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
		btnSair.setBounds(99, 244, 117, 29);
		panelMenu.add(btnSair);
		
		//O botão avançar se comporta de maneiras distintas, de acordo com a opção que estiver selecionada
		JButton btnAvanar = new JButton("Avan\u00E7ar");
		btnAvanar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch(option)
				{
				case 1:
					//Caso seja adicionar nova instrução, ele abre a tela de adição
					panelMenu.setVisible(false);
					panelAddInstruction.setVisible(true);
					break;
				case 2:
					//Caso seja reiniciar a aplicação, ele chama os métodos responsáveis pela limpeza
					//Dos controles internos, da memória, e dos dados que estão na interface gráfica
					controlador.limpaMemoria();
					controlador.zerarTudo();
					execute.cleanFields();
					JOptionPane.showMessageDialog(frame, "Sistema reiniciado com sucesso!", "Dados Limpos", JOptionPane.PLAIN_MESSAGE);
					break;
				case 3:
					//Caso seja selecionada a opção de executar instruções da memória, é aberta a tela de execução
					setExecuteScreen();
					execute.frame.setVisible(true);
					break;
				case 4:
					//Caso seja escolhida a opção de visualizar o conteúdo e instruções da memória
					//as mesmas são carregadas, depois exibidas.
					Object[] content = controlador.getMemoria();
					
					String memory = "";
					
					for(int i = 0; i < Memoria.indice; i++)
						memory = memory + content[i].toString() + "\n";
					
					textMemoryContent.setText(memory);
					
					panelMenu.setVisible(false);
					panelMemoryList.setVisible(true);
					
					break;
				default:
					//Caso contrário, é exibida uma mensagem de erro.
					JOptionPane.showMessageDialog(frame, "Escolha uma opção.", "Erro", JOptionPane.ERROR_MESSAGE);
					System.out.println("Erro");	
				}
			}
		});
		
		btnAvanar.setBounds(233, 244, 117, 29);
		panelMenu.add(btnAvanar);
	
	}
	
	public void setOption(int option){
		this.option = option;
	}
	
	//Método que manda o conteúdo da memória para a tela de execução das instruções
	public void setExecuteScreen(){
		execute.setMemoryContent(controlador.getMemoria());		
	}
	
}
