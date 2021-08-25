import javax.swing.JFrame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class Main{
	public static void main(String[] args){
		JFrame window = new JFrame("Master Sudoku");
		GUI GuiManager = new GUI();
		GameManager gameManager = new GameManager();
		GuiManager.InitializeWindow(window, gameManager);
		GuiManager.AddPanels(window);
		window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                gameManager.SerializeRiddles();
                System.exit(0);
            }
        });
		window.setVisible(true);
		GuiManager.RenderRiddle();
		System.out.println(8/3);

		
	}
}
