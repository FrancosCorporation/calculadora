package frontEnd;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import org.w3c.dom.events.MouseEvent;

public class AppCalc extends JFrame {

	private String painel = "";
	private String operacao = "";
	private double x, y = 0, subtracao, divisao, start = -1, result;
	private JPanel contentPane;
	private JButton btn_0;
	private boolean active = true;
	private JTextField Display;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppCalc frame = new AppCalc();
					frame.setTitle("Calcu By Dolfim");
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
	public AppCalc() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 294, 406);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btn_somar = new JButton("+");
		btn_somar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				y = Double.parseDouble(Display.getText().replaceAll(",", "."));
				operacao = "+";
				setOperations((int) y);

			}
		});

		JButton btn_diminuir = new JButton("-");
		btn_diminuir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				y = Double.parseDouble(Display.getText().replaceAll(",", "."));
				operacao = "-";
				setOperations((int) y);
			}
		});

		JButton btn_multiplicar = new JButton("*");
		btn_multiplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				y = Double.parseDouble(Display.getText().replaceAll(",", "."));
				operacao = "*";
				setOperations((int) y);
			}
		});
		JButton btn_dividir = new JButton("/");
		btn_dividir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				y = Double.parseDouble(Display.getText().replaceAll(",", "."));
				operacao = "/";
				setOperations((int) y);
			}
		});

		JButton btn_igual = new JButton("=");
		btn_igual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (operacao.equalsIgnoreCase("+")) {
					y = Double.parseDouble(Display.getText().replaceAll(",", "."));
					operacao = "+";
					setOperations((int) y);
				} else if (operacao.equalsIgnoreCase("-")) {
					y = Double.parseDouble(Display.getText().replaceAll(",", "."));
					operacao = "-";
					setOperations((int) y);
				} else if (operacao.equalsIgnoreCase("*")) {
					y = Double.parseDouble(Display.getText().replaceAll(",", "."));
					operacao = "*";
					setOperations((int) y);
				} else if (operacao.equalsIgnoreCase("/")) {
					y = Double.parseDouble(Display.getText().replaceAll(",", "."));
					operacao = "/";
					setOperations((int) y);
				}
			}
		});

		btn_0 = new JButton("0");
		btn_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDisplayNumber(0);
			}
		});
		btn_0.setFont(new Font("Bahnschrift", Font.PLAIN, 18));

		// Acepted only numbers
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);
		Display = new JFormattedTextField(formatter);
		// Finish

		Display.setFont(new Font("Bahnschrift", Font.PLAIN, 26));
		Display.setHorizontalAlignment(SwingConstants.RIGHT);
		Display.setText("0");
		Display.setColumns(10);

		Display.setFocusable(true);
		Display.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				Display.setText("");
				if (e.getKeyCode() == KeyEvent.VK_ADD && Display.isFocusable()) {
					y = Double.parseDouble(Display.getText().replaceAll(",", "."));
					System.out.println(y);
					System.out.println(result);
					operacao = "+";
					setOperations((int) y);
					setDisplayNumber((int) y);
				} else if (e.getKeyCode() == KeyEvent.VK_1 && Display.isFocusable()) {
					setDisplayNumber(1);
				} else if (e.getKeyCode() == KeyEvent.VK_2 && Display.isFocusable()) {
					setDisplayNumber(2);
				} else if (e.getKeyCode() == KeyEvent.VK_3 && Display.isFocusable()) {
					setDisplayNumber(3);
				} else if (e.getKeyCode() == KeyEvent.VK_4 && Display.isFocusable()) {
					setDisplayNumber(4);
				} else if (e.getKeyCode() == KeyEvent.VK_5 && Display.isFocusable()) {
					setDisplayNumber(5);
				} else if (e.getKeyCode() == KeyEvent.VK_6 && Display.isFocusable()) {
					setDisplayNumber(6);
				} else if (e.getKeyCode() == KeyEvent.VK_7 && Display.isFocusable()) {
					setDisplayNumber(7);
				} else if (e.getKeyCode() == KeyEvent.VK_8 && Display.isFocusable()) {
					setDisplayNumber(8);
				} else if (e.getKeyCode() == KeyEvent.VK_9 && Display.isFocusable()) {
					setDisplayNumber(9);
				}
			}

		});

		JButton btn_virgula = new JButton(",");
		btn_virgula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (painel.contains(",") == false) {
					painel += ",";
				}
				Display.setText(painel);
			}
		});
		btn_virgula.setFont(new Font("Bahnschrift", Font.PLAIN, 18));

		btn_somar.setFont(new Font("Bahnschrift", Font.PLAIN, 18));

		btn_igual.setFont(new Font("Bahnschrift", Font.PLAIN, 18));

		btn_diminuir.setFont(new Font("Bahnschrift", Font.PLAIN, 18));

		btn_multiplicar.setFont(new Font("Bahnschrift", Font.PLAIN, 18));

		btn_dividir.setFont(new Font("Bahnschrift", Font.PLAIN, 18));

		JButton btn_1 = new JButton("1");
		btn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDisplayNumber(1);
			}
		});
		btn_1.setFont(new Font("Bahnschrift", Font.PLAIN, 18));

		JButton btn_2 = new JButton("2");
		btn_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDisplayNumber(2);
			}
		});
		btn_2.setFont(new Font("Bahnschrift", Font.PLAIN, 18));

		JButton btn_3 = new JButton("3");
		btn_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDisplayNumber(3);
			}
		});
		btn_3.setFont(new Font("Bahnschrift", Font.PLAIN, 18));

		JButton btn_4 = new JButton("4");
		btn_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDisplayNumber(4);
			}
		});
		btn_4.setFont(new Font("Bahnschrift", Font.PLAIN, 18));

		JButton btn_5 = new JButton("5");
		btn_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDisplayNumber(5);
			}
		});
		btn_5.setFont(new Font("Bahnschrift", Font.PLAIN, 18));

		JButton btn_6 = new JButton("6");
		btn_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDisplayNumber(6);
			}
		});
		btn_6.setFont(new Font("Bahnschrift", Font.PLAIN, 18));

		JButton btn_7 = new JButton("7");
		btn_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDisplayNumber(7);
			}
		});
		btn_7.setFont(new Font("Bahnschrift", Font.PLAIN, 18));

		JButton btn_8 = new JButton("8");
		btn_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDisplayNumber(8);
			}
		});
		btn_8.setFont(new Font("Bahnschrift", Font.PLAIN, 18));

		JButton btn_9 = new JButton("9");
		btn_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDisplayNumber(9);
			}
		});
		btn_9.setFont(new Font("Bahnschrift", Font.PLAIN, 18));

		JButton btn_apagatudo = new JButton("C");
		btn_apagatudo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText("0");
				Display.requestFocus();
				painel = "";
				y = 0;
			}
		});
		JButton btn_apagaultima = new JButton("CE");
		btn_apagaultima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText("0");
				Display.requestFocus();
				painel = "";
				x = 0;
				y = 0;
				subtracao = 0;
				start = -1;
				result = 0;
				operacao = "";
			}
		});
		btn_apagatudo.setFont(new Font("Bahnschrift", Font.PLAIN, 18));

		JButton btn_apaganumeropornumero = new JButton("");

		try {
			btn_apaganumeropornumero
					.setIcon(new ImageIcon(this.getClass().getResource("../resource/seta13.png")));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		btn_apaganumeropornumero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (painel.length() > 0) {
					painel = painel.substring(0, painel.length() - 1);
					Display.setText(painel);
					Display.requestFocus();
				}
				if (painel.length() == 0) {
					painel = "";
					Display.setText("0");
					Display.requestFocus();
				}
			}
		});
		btn_apaganumeropornumero.setFont(new Font("Bahnschrift", Font.PLAIN, 13));

		btn_apagaultima.setFont(new Font("Bahnschrift", Font.BOLD, 9));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
						.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(Display, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane
										.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(btn_4, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn_5, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn_6, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(
														btn_multiplicar, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
										.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(btn_1, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn_2, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn_3, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(btn_diminuir,
														GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
										.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(btn_0, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn_virgula, GroupLayout.DEFAULT_SIZE, 44,
														Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn_somar, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
										.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(btn_7, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn_8, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn_9, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(btn_dividir,
														GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn_igual, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)))
						.addGap(19))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(btn_apaganumeropornumero, GroupLayout.PREFERRED_SIZE, 46, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btn_apagaultima, GroupLayout.PREFERRED_SIZE, 46, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btn_apagatudo,
										GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGap(113)))));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addComponent(Display, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btn_apaganumeropornumero, GroupLayout.PREFERRED_SIZE, 34,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_apagaultima, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_apagatudo, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
				.addGap(7)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(btn_7, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_8, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_9, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(btn_dividir,
														GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(btn_4, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_5, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(btn_6, GroupLayout.PREFERRED_SIZE, 34,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(btn_multiplicar, GroupLayout.PREFERRED_SIZE, 34,
														GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(btn_diminuir, GroupLayout.PREFERRED_SIZE, 34,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_2, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_3, GroupLayout.PREFERRED_SIZE, 34,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(btn_0, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
										.addComponent(btn_virgula, GroupLayout.PREFERRED_SIZE, 34,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_somar, GroupLayout.PREFERRED_SIZE, 34,
												GroupLayout.PREFERRED_SIZE)))
						.addComponent(btn_igual, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))
				.addContainerGap()));
		contentPane.setLayout(gl_contentPane);
	}

	private void setDisplayNumber(int numb) {
		if (active == true && numb == 0) {
			if (painel.startsWith("0")) {
				return;
			} else {
				Display.setText(painel += numb);
			}
		} else {
			if (painel.startsWith("0")) {
				painel = "";
				Display.setText(painel += numb);
				active = false;
			} else {
				Display.setText(painel += numb);
				active = false;
			}

		}
	}

	private void setOperations(int numb) {
		if (operacao.equalsIgnoreCase("+")) {
			result += numb;
			Display.setText(result + "");
			painel = "";
		} else if (operacao.equalsIgnoreCase("-")) {
			if (start == -1) {
				result = numb;
				Display.setText(result + "");
				painel = "";
				start = 0;
			} else {
				result = -numb + (result);
				Display.setText(result + "");
				painel = "";
			}
		} else if (operacao.equalsIgnoreCase("*")) {
			if (start == -1) {
				result = numb;
				Display.setText(result + "");
				painel = "";
				start = 0;
			} else {
				result *= numb;
				Display.setText(result + "");
				painel = "";
			}
		} else if (operacao.equalsIgnoreCase("/")) {
			if (start == -1) {
				result = numb;
				Display.setText(result + "");
				painel = "";
				start = 0;
			} else {
				result /= numb;
				Display.setText(result + "");
				painel = "";
			}
		}
	}

}
