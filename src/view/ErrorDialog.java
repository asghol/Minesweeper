package view;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class ErrorDialog extends JDialog {
	private static final long serialVersionUID = -3706752260564736641L;

	public ErrorDialog(String error, String message) {
		setTitle(error);
		setModalityType(DEFAULT_MODALITY_TYPE);
		add(new JLabel(message));
		
		pack();
		setVisible(true);
	}
}