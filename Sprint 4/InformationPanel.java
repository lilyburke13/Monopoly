import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

/**
 * @author Lilah Koudelka
 * @author Elizabeth Burke
 * @version February 27, 2017 Sprint 2
 */

public class InformationPanel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private static final int TEXT_AREA_HEIGHT = 40;
	private static final int CHARACTER_WIDTH = 40;
	private static final int FONT_SIZE = 14;

	JTextArea textArea = new JTextArea(TEXT_AREA_HEIGHT, CHARACTER_WIDTH);
	JScrollPane scrollPane = new JScrollPane(textArea);
	DefaultCaret caret = (DefaultCaret)textArea.getCaret();

	public InformationPanel() 
	{
		textArea.setEditable(false);
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		textArea.setFont(new Font("Comic Sans MS", Font.PLAIN, FONT_SIZE));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		return;
	}

	public void addText(String text) 
	{
		textArea.setText(textArea.getText() + text + "\n");
	}
}