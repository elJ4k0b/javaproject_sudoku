import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
public class Riddle implements Serializable
{
	private String title;
	private ArrayList<ArrayList<Cell>> rows = new ArrayList<>();
	private ArrayList<ArrayList<Cell>> lanes = new ArrayList<>();
	private ArrayList<ArrayList<Cell>> square = new ArrayList<>();
	public ArrayList<Cell> allCells = new ArrayList<>();
	
	public Riddle(int width, int height)
	{
		title = "Mein erstes Sudoku";
		Cell cell = null;
		int buffer = 0;
		for (int i = 0; i < width; i++)
		{
			lanes.add(new ArrayList<Cell>());
			rows.add(new ArrayList<Cell>());
			square.add(new ArrayList<Cell>());
		}
		for (int y = 0; y < height; y++)
		{
			
			for(int x = 0; x < width; x++)
			{
				cell = new Cell();
				allCells.add(cell);
				if(y > 2 && y <= 5)
				{
					buffer = 3;
				}
				else if (y > 5)
				{
					buffer = 6;
				}
				square.get(((x)/3)+(buffer)).add(cell);	
				lanes.get(x).add(cell);
				rows.get(y).add(cell);
			}
			System.out.println(square.get(8).size());
			
		}
	}
	
	public ArrayList<Cell> IsCorrect ()
	{
		boolean added; 
		Set<Integer> possibleSolutions = new HashSet<Integer> ();
		HashMap<Integer,Cell> checkedCells = new HashMap<>();;
		ArrayList<Cell> wrongCells = new ArrayList<>();
		ArrayList<ArrayList<ArrayList<Cell>>> allLists = new ArrayList<>();
		allLists.add(this.rows);
		allLists.add(this.lanes);
		allLists.add(this.square);
		for(ArrayList<ArrayList<Cell>> currentList : allLists)
		{
			for(ArrayList<Cell> sequence : currentList)
			{
				for(Cell cell : sequence)
				{
					if(cell.GetValue() != 0)
					{
						added = possibleSolutions.add(cell.GetValue());
						if(added == false)
						{
							if(cell.CanBeEdited() == true)
							{
								wrongCells.add(cell);
								wrongCells.add(checkedCells.get(cell.GetValue()));
							}
							else
							{
								if(checkedCells.get(cell.GetValue()) == null)
								{
									System.out.println("Fehler beim Überprüfen einer vordefinierten Zelle.");
									continue;
								}
								else
								{
									wrongCells.add(checkedCells.get(cell.GetValue()));
								}
							}
							
						}
						checkedCells.put(cell.GetValue(),cell);
					}
					else
					{
						continue;
					}
				}
				checkedCells.clear();
				possibleSolutions.clear();
			}
		}
		return wrongCells;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title; 
	}
}
