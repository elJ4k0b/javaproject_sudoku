import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.xml.crypto.dsig.keyinfo.KeyValue;

public class GameManager implements Serializable{
	private ArrayList<Riddle> allRiddles = new ArrayList<>();
	private ArrayList<Riddle> allCopiedRiddle = new ArrayList<>();
	private Riddle currentRiddle;
	
	public void SaveRiddle(Riddle riddle)
	{
		if(allRiddles.contains(riddle))
		{
			//allRiddles.add(allRiddles.indexOf(riddle),riddle);
			System.out.println("updated existing Riddle");
			return;
		}
		else
		{
			allRiddles.add( riddle);
			System.out.println("saved new Riddle");
		}
	}
	
	public Riddle LoadRiddle()
	{
		currentRiddle = new Riddle(9,9);
		SaveRiddle(currentRiddle);	
		System.out.println("loading new Riddle");
		return currentRiddle;
	}
	
	public Riddle LoadRiddle(int index)
	{
		currentRiddle = allRiddles.get(index);
		System.out.println("loading existing Riddle " + currentRiddle.getTitle());
		return allRiddles.get(index);
	}
	
	public Riddle LoadCopyOf(int index)
	{
		Riddle copyOfRiddle = (Riddle) deepClone(allRiddles.get(index));
		for(Cell cell : copyOfRiddle.allCells)
		{
			cell.finalState(true);
		}
		currentRiddle = copyOfRiddle;
		System.out.println("loading copy of riddle "+index);
		return copyOfRiddle;
	}
	
	public Riddle GetCurrentRiddle()
	{
		return currentRiddle;
	}
	
	public ArrayList<String> returnAllRiddles()
	{
		ArrayList<String> titles = new ArrayList<>();
		for (Riddle riddle : allRiddles)
		{
			titles.add(riddle.getTitle());
		}
		return titles;
	}
	
	public Object deepClone (Object o)
	{
		try 
		{
			ByteArrayOutputStream bas = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bas);
			oos.writeObject(o);
			ByteArrayInputStream bis = new ByteArrayInputStream(bas.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bis);
			return ois.readObject();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public ArrayList<Cell> CheckResults(Riddle riddle)
	{
		ArrayList <Cell> wrongAnswers = riddle.IsCorrect();
		if(wrongAnswers.isEmpty())
		{
			return null;
		}
		else
		{
			return wrongAnswers;
		}
	}
	
	public void SerializeRiddles ()
	{
		
	}
}
