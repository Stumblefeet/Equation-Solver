import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
/*
 * Controller class
 */
public class Main extends JPanel implements ActionListener{
	protected JLabel message;
	protected JTextField input;
	protected JTextArea textArea;

	public Main() {
		super(new GridBagLayout());
		
		message = new JLabel("Enter equation here:");
		add(message);

		input = new JTextField(50);
		input.addActionListener(this);
		add(input);

		textArea = new JTextArea(15,50);
		textArea.setEditable(false);

		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;

		c.fill = GridBagConstraints.HORIZONTAL;
		add(input, c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(textArea, c);

		Font font = new Font("Courier", Font.BOLD, 16);
		input.setFont(font);
		textArea.setForeground(Color.GREEN);
		textArea.setFont(font);
		textArea.setForeground(Color.BLUE);


		JFrame frame = new JFrame("Equation Solver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Add contents to the window.
		frame.add(this);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent evt) {
		String text = input.getText();
		//Test the equation

		//Test accepted
		Solution solution = EquationSolver.solveEquation(text);
		textArea.setText(solution.toString());

		//Test not accepted
		//textArea.setText("Syntax error in the equation.");
	}

	public static void main(String[] args){
		new Main();
	}
}




