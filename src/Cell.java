import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JTextField;

public class Cell implements ActionListener, Serializable{
	private int value;
	private boolean canBeEdited;
	private boolean finalState;
	public JTextField inputField;
	
	public boolean GetFinalState()
	{
		return finalState;
	}
	public Cell ()
	{
		inputField = new JTextField ();
		inputField.addActionListener(this);
		
		value = 0;
		canBeEdited = true;
	}
	public int GetValue()
	{
		return value;
	}
	public void SetValue(int value)
	{
		if(canBeEdited == true)
		{
			this.value = value;
		}
	}
	public boolean CanBeEdited ()
	{
		return canBeEdited;
	}
	public void finalState(boolean bool)
	{
		finalState = bool;
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == inputField)
		{
			try
			{
				int newInput = Integer.parseInt(inputField.getText());
				if(newInput <= 9 && newInput >=1)
				{
					if(finalState != true)
					{
						canBeEdited = false;
						System.out.println("changed canBeEdited to "+canBeEdited);
					}
					value = newInput;
					System.out.println("cell value set");
				}
				System.out.println(value);
			}
			catch (NumberFormatException e)
			{
				inputField.setText("");
				return;
			}
		}
	}
	
}
