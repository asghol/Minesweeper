package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Creates a dialog for making a custom game
 * @author Asgeir Hølleland
 */
public class CustomGame extends JDialog implements ActionListener {
	private static final long serialVersionUID = 5513479534980520510L;

	private JLabel jlColumns, jlRows, jlMines;
	private JTextField jtfColumns, jtfRows, jtfMines;
	private JButton jbOk, jbCancel;
	
	private int rows, columns, mines;
	private boolean failed;

	public CustomGame(JFrame frame, Point p, int rows, int columns, int mines) {
		super(frame, "Custom Game", true);
		
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
		failed = false;
		
		setup(rows, columns, mines);
		setLocation(p);
		pack();
		setResizable(false);
		setVisible(true);
		setSize(160, 140);
	}

	// Setup method
	private void setup(int rows, int columns, int mines) {
		jlRows = new JLabel("Rows:");
		jtfRows = new JTextField(Integer.toString(rows));
		
		jlColumns = new JLabel("Columns:");
		jtfColumns = new JTextField(Integer.toString(columns));

		jlMines = new JLabel("Mines:");
		jtfMines = new JTextField(Integer.toString(mines));
		
		jbOk = new JButton("Ok");
		jbOk.addActionListener(this);
		
		jbCancel = new JButton("Cancel");
		jbCancel.addActionListener(this);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.insets = new Insets(1,1,1,1);
		
		c.gridx = 0;
		c.gridy = 0;
		add(jlRows, c);
		
		c.gridx = 1;
		c.gridy = 0;
		add(jtfRows, c);
		
		c.gridx = 0;
		c.gridy = 1;
		add(jlColumns, c);
		
		c.gridx = 1;
		c.gridy = 1;
		add(jtfColumns, c);
		
		c.gridx = 0;
		c.gridy = 2;
		add(jlMines, c);
		
		c.gridx = 1;
		c.gridy = 2;
		add(jtfMines, c);
		
		c.gridx = 0;
		c.gridy = 3;
		add(jbOk, c);
		
		c.gridx = 1;
		c.gridy = 3;
		add(jbCancel, c);
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public int getMines() {
		return mines;
	}
	
	public boolean failed() {
		return failed;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == jbOk) {
			int row = 0;
			int mine = 0;
			int col = 0;
			try {
				row = Integer.parseInt(jtfRows.getText());
				col = Integer.parseInt(jtfColumns.getText());
				mine = Integer.parseInt(jtfMines.getText());	
			} catch(NumberFormatException e) {
				failed = true;
			}
			if(!failed && (row > 0 && col > 0 && mine > 0) && (row * col) > mine) {
				rows = row;
				columns = col;
				mines = mine;
			}
			else
				failed = true;
			dispose();
		}
		else {
			failed = true;
			dispose();
		}
	}
}