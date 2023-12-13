package compi1.proyecto1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import java.awt.TextArea;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;
import java.awt.Panel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class proyecto1_ventana extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenu mn_Archivo;
	private JTable table_TablaEstados;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					proyecto1_ventana frame = new proyecto1_ventana();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public proyecto1_ventana() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1174, 689);
		
		JMenuBar menuBar_principal = new JMenuBar();
		setJMenuBar(menuBar_principal);
		
		mn_Archivo = new JMenu("Archivo");
		menuBar_principal.add(mn_Archivo);
		
		JMenuItem mntm_Abrir = new JMenuItem("Abrir");
		mn_Archivo.add(mntm_Abrir);
		
		JMenuItem mntm_Guardar = new JMenuItem("Guardar");
		mn_Archivo.add(mntm_Guardar);
		
		JMenuItem mntm_GuardarComo = new JMenuItem("Guardar Como");
		mn_Archivo.add(mntm_GuardarComo);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Panel panel_Automatas = new Panel();
		panel_Automatas.setBounds(632, 10, 516, 405);
		contentPane.add(panel_Automatas);
		panel_Automatas.setLayout(null);
		
		JTabbedPane tabPane_PestanasAutomatas = new JTabbedPane(JTabbedPane.TOP);
		tabPane_PestanasAutomatas.setBounds(10, 11, 496, 383);
		panel_Automatas.add(tabPane_PestanasAutomatas);
		
		JPanel panel_AFN = new JPanel();
		tabPane_PestanasAutomatas.addTab("AFN", null, panel_AFN, null);
		tabPane_PestanasAutomatas.setEnabledAt(0, true);
		panel_AFN.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("AFN");
		lblNewLabel_1.setBounds(206, 167, 46, 14);
		panel_AFN.add(lblNewLabel_1);
		
		JPanel panel_AFD = new JPanel();
		tabPane_PestanasAutomatas.addTab("AFD", null, panel_AFD, null);
		panel_AFD.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("AFD");
		lblNewLabel_2.setBounds(235, 155, 46, 14);
		panel_AFD.add(lblNewLabel_2);
		
		Panel panel_Consola = new Panel();
		panel_Consola.setBounds(10, 421, 621, 197);
		contentPane.add(panel_Consola);
		panel_Consola.setLayout(null);
		
		JTextArea textArea_Consola = new JTextArea();
		textArea_Consola.setForeground(SystemColor.window);
		textArea_Consola.setBackground(SystemColor.menuText);
		textArea_Consola.setEditable(false);
		textArea_Consola.setBounds(10, 40, 601, 146);
		panel_Consola.add(textArea_Consola);
		
		JLabel lblNewLabel = new JLabel("Consola");
		lblNewLabel.setBounds(10, 15, 46, 14);
		panel_Consola.add(lblNewLabel);
		
		Panel panel_Estados = new Panel();
		panel_Estados.setBounds(642, 421, 506, 197);
		contentPane.add(panel_Estados);
		panel_Estados.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(429, 184, 2, 2);
		panel_Estados.add(scrollPane);
		
		JScrollPane scrollPane_TablaEstados = new JScrollPane();
		scrollPane_TablaEstados.setBounds(10, 30, 496, 167);
		panel_Estados.add(scrollPane_TablaEstados);
		
		table_TablaEstados = new JTable();
		table_TablaEstados.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"Estado", "a", "b", "c"
			}
		));
		scrollPane_TablaEstados.setViewportView(table_TablaEstados);
		
		JLabel lblNewLabel_3 = new JLabel("Tabla de estados");
		lblNewLabel_3.setBounds(10, 11, 160, 14);
		panel_Estados.add(lblNewLabel_3);
		
		Panel panel_Editor = new Panel();
		panel_Editor.setBounds(10, 10, 616, 405);
		contentPane.add(panel_Editor);
		panel_Editor.setLayout(null);
		
		TextArea textArea = new TextArea();
		textArea.setBackground(new Color(2, 0, 21));
		textArea.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		textArea.setBounds(10, 43, 596, 352);
		panel_Editor.add(textArea);
		
		JLabel lbl_Editor = new JLabel("Editor");
		lbl_Editor.setBounds(10, 23, 46, 14);
		panel_Editor.add(lbl_Editor);
		
		JButton btn_Ejecutar = new JButton("Ejecutar");
		btn_Ejecutar.setBounds(364, 14, 89, 23);
		panel_Editor.add(btn_Ejecutar);
		
		JButton btn_GenerarReporte = new JButton("Generar Reportes");
		btn_GenerarReporte.setBounds(463, 14, 143, 23);
		panel_Editor.add(btn_GenerarReporte);
	}
}
