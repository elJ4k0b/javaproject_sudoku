import java.awt.*;
import javax.swing.BorderFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
public class GUI implements ActionListener, ChangeListener{
	
	//Oberflächenelemente
	private JLabel cellLabel;
	private JPanel creationPanel;
	private JPanel solvingPanel;
	private JTabbedPane tabPanel;
	private GameManager gameManager;
	private JPanel creationCenterPanel;
	private JPanel solvingCenterPanel;
	private JTextField titleField;
	private ArrayList <String> titles = new ArrayList<>();
	private List titleList1 = new List();
	private List titleList2 = new List();
	private JButton saveButton;
	private JLabel titleLabel;
	private JButton checkButton;
	private JButton resetButton;
	private Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
	private JOptionPane winMessage;
	private JFrame window;
	//Fonts
	Font cellFont = new Font("SansSerif", Font.BOLD, 30);
	Font titleFont = new Font ("SansSerif", Font.ITALIC,20);
	
	public void InitializeWindow(JFrame window, GameManager gameManager)
	{
		//Fenster Initialisieren
		this.window = window;
	    window.setSize(1000,1000);
	    //gameManager initialisieren
	    this.gameManager = gameManager;
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void AddPanels(JFrame window)
	{
	    //BasisPanel generieren
	    JPanel RootPane = new JPanel();
	    
	    //Tab Panels generieren
	    solvingPanel = new JPanel();
		creationPanel = new JPanel();
	    
	    //Layout festlegen
	    creationPanel.setLayout(new BorderLayout());
	    solvingPanel.setLayout(new BorderLayout());
	    
	    //Panel Elemente hinzufügen
	    LayoutSolvingPanel(solvingPanel);
	    LayoutCreationPanel(creationPanel);
	    
	    //Tab Panel generieren
	    tabPanel = new JTabbedPane (JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT );
	    tabPanel.addChangeListener(this);
	    tabPanel.add("Erstellen",creationPanel);
	    tabPanel.add("Lösen",solvingPanel);
	    
	    //Alles dem Fenster hinzufügen
	    window.add(RootPane);
	    window.add(tabPanel);
	}
	private void LayoutCreationPanel(JPanel panel)
	{
		creationCenterPanel = new JPanel();
		creationCenterPanel.setBackground(Color.GRAY);
		creationCenterPanel.setLayout(new GridLayout(9,9));
		titleField = new JTextField("Benenne dein Sudoku");
		titleField.setHorizontalAlignment(JTextField.CENTER);
		titleField.setFont(titleFont);
		titleField.addActionListener(this);
		panel.add(titleField, BorderLayout.NORTH);
		saveButton = new JButton("Speichern");
		saveButton.addActionListener(this);
		panel.add(saveButton, BorderLayout.SOUTH);
		panel.add(new JButton("reset"), BorderLayout.EAST);
		titleList1.addActionListener(this);
		panel.add(titleList1, BorderLayout.WEST);
		panel.add(creationCenterPanel, BorderLayout.CENTER);
	}
	private void LayoutSolvingPanel(JPanel panel)
	{	
		solvingCenterPanel = new JPanel();
		solvingCenterPanel.setBackground(Color.GRAY);
		solvingCenterPanel.setLayout(new GridLayout(9,9));
		titleLabel = new JLabel("Benenne dein Sudoku");
		titleLabel.setFont(titleFont);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(titleLabel, BorderLayout.NORTH);
		checkButton = new JButton("Prüfen");
		checkButton.addActionListener(this);
		panel.add(checkButton, BorderLayout.SOUTH);
		resetButton = new JButton("reset");
		resetButton.addActionListener(this);
		panel.add(resetButton, BorderLayout.EAST);
		panel.add(titleList2, BorderLayout.WEST);
		titleList2.addActionListener(this);
		panel.add(solvingCenterPanel, BorderLayout.CENTER);
	}
	public void RenderRiddle (int index )
	{	
		Riddle riddleToRender;
		JPanel currentPanel = getCurrentTabPanel();
		System.out.println("panel cleared");
		if(currentPanel == creationCenterPanel)
		{
			currentPanel.removeAll();
			riddleToRender = gameManager.LoadRiddle(index);
			for(Cell cell : riddleToRender.allCells)
			{
				cell.finalState(false);
				cell.inputField.setHorizontalAlignment(JTextField.CENTER);
				cell.inputField.setFont(cellFont);
				if(cell.GetValue() != 0)
				{
					cell.inputField.setText(""+cell.GetValue());
				}
				else
				{
					cell.inputField.setText("");
				}
				if(currentPanel == creationCenterPanel)
				{
					cell.inputField.setBackground(Color.WHITE);
				}
				currentPanel.add(cell.inputField);
				currentPanel.repaint();
			}
		}
		else
		{
			currentPanel.removeAll();
			riddleToRender = gameManager.LoadCopyOf(index);
			for(Cell cell : riddleToRender.allCells)
			{
				if(cell.CanBeEdited())
				{
					cell.inputField.setHorizontalAlignment(JTextField.CENTER);
					cell.inputField.setFont(cellFont);
					if(cell.GetValue() != 0)
					{
						cell.inputField.setText(""+cell.GetValue());
					}
					else
					{
						cell.inputField.setText("");
					}
					if(currentPanel == creationCenterPanel)
					{
						cell.inputField.setBackground(Color.WHITE);
					}
					currentPanel.add(cell.inputField);
					currentPanel.repaint();
				}
				else
				{
					cellLabel = new JLabel();
					currentPanel.add(cellLabel);
					cellLabel.setHorizontalAlignment(JLabel.CENTER);
					cellLabel.setBorder(border);
					cellLabel.setBackground(Color.WHITE);
					cellLabel.setOpaque(true);
					cellLabel.setFont(cellFont);
					if(cell.GetValue() != 0)
					{
						cellLabel.setText(""+cell.GetValue());
					}
					else
					{
						cellLabel.setText("");
					}
					currentPanel.repaint();
				}
			}	
		}
		titleField.setText(riddleToRender.getTitle());
		titleLabel.setText(riddleToRender.getTitle());
		titles.add(riddleToRender.getTitle());
	}
	
	public JPanel getCurrentTabPanel()
	{
		if(tabPanel.getSelectedComponent() == creationPanel)
		{
			return creationCenterPanel;
		}
		else if (tabPanel.getSelectedComponent() == solvingPanel)
		{
			return solvingCenterPanel;
		}
		return creationCenterPanel;
	}
	public void RenderRiddle ()
	{
		JPanel currentPanel = getCurrentTabPanel();
		currentPanel.removeAll();
		System.out.println("panel cleared");
		Riddle riddleToRender = gameManager.LoadRiddle();
		if(gameManager.returnAllRiddles().size() == 1)
		{
			titleField.setText(riddleToRender.getTitle());
			titleLabel.setText(riddleToRender.getTitle());
			titleList1.add(riddleToRender.getTitle());
			titleList2.add(riddleToRender.getTitle());
		}
		for(Cell cell : riddleToRender.allCells)
		{
			cell.inputField.setHorizontalAlignment(JTextField.CENTER);
			cell.inputField.setFont(cellFont);
			if(cell.GetValue() != 0)
			{
				cell.inputField.setText(""+cell.GetValue());
			}
			else
			{
				cell.inputField.setText("");
			}
			currentPanel.add(cell.inputField);
		}
		titles.add(riddleToRender.getTitle());
	}
	@Override
	public void actionPerformed(ActionEvent event)
	{	
		if(event.getSource() == saveButton)
		{
			String currentRiddleTitle = gameManager.GetCurrentRiddle().getTitle();
			if(titles.contains(currentRiddleTitle) == false)
			{
				this.titles.add(currentRiddleTitle);
				titleList1.add(currentRiddleTitle);
				titleList2.add(currentRiddleTitle);
				System.out.println("game saved");
				for(String s : gameManager.returnAllRiddles())
				{
					System.out.println(s);
				}
				//gameManager.SaveRiddle(gameManager.GetCurrentRiddle());
			}
		}
		
		if(event.getSource() == checkButton)
		{
			int filledCellCounter = 0;
			ArrayList<Cell> wrongAnswers = gameManager.CheckResults(gameManager.GetCurrentRiddle());
			for (Cell cell : gameManager.GetCurrentRiddle().allCells)
			{
				if(wrongAnswers != null)
				{
					if(wrongAnswers.contains(cell)!= true)
					{
						cell.inputField.setBackground(Color.GREEN);	
					}
					else
					{
						cell.inputField.setBackground(Color.RED);
					}
				}
				else
				{
					cell.inputField.setBackground(Color.WHITE);
				}
				if(cell.GetValue() != 0)
				{
					filledCellCounter++;
				}
				
			}
			if(wrongAnswers == null)
			{
				if(filledCellCounter >= 81)
				{
					JOptionPane.showMessageDialog(window, "Sie haben das Sudoku vollständig und richtig gelöst.");
				}
				else
				{
					JOptionPane.showMessageDialog(window, "Sie haben bisher keine Fehler gemacht.");
				}
			}
			else
			{
				
				JOptionPane.showMessageDialog(window, "Leider stimmen noch nicht alle Anworten. Drücken Sie auf reset, um einen neuen Versuch zu starten.");
			}
		}
		
		if(event.getSource() == resetButton)
		{
			if(titleList2.getSelectedIndex() < 0)
			{
				titleList2.select(0);
			}
			RenderRiddle(titleList2.getSelectedIndex());
		}
		if(event.getSource() == titleField)
		{
			String currentRiddleTitle = gameManager.GetCurrentRiddle().getTitle();
			if(currentRiddleTitle.equals(titleField.getText()) != true)
			{	
				RenderRiddle();
				System.out.println("title changed");
				gameManager.GetCurrentRiddle().setTitle(titleField.getText());
			}
			else
			{
				System.out.println("same title");
			}
		}
		
		if(event.getSource() == titleList1)
		{
			RenderRiddle(titleList1.getSelectedIndex());
			System.out.println("listSelction");
		}
		
		if(event.getSource() == titleList2)
		{
			RenderRiddle(titleList2.getSelectedIndex());
			System.out.println("listSelction");
		}
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == tabPanel)
		{
			System.out.println("tabPabel state changed");
			if(gameManager.returnAllRiddles().isEmpty() != true)
			{
				RenderRiddle(0);
			}
			else
			{
				return;
			}
		}
		
	}
}
