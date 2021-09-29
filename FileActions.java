import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class FileActions {
	
	static GameWindow gw = new GameWindow(5, 5);
	
	private ArrayList<Object> objects = new ArrayList<Object>();

	public static void main(String[] args) {
		
	}
    //setters
    public void addObject(Object o)
    {
        objects.add(o);
    }
	
	public void saveGame() {
		
		try {
			  FileOutputStream fos = new FileOutputStream(new File("myFile.txt"));
			  ObjectOutputStream oos = new ObjectOutputStream(fos);
			  for(int i = 0; i < objects.size();i++)
	            {
	                oos.writeObject(objects.get(i));
	            }
			  oos.close();
			  fos.close();
			
		} catch (IOException i) {
			 i.printStackTrace();
		}
	}
	
	public void loadGame() {
		GameWindow gs = null;
		try {
			FileInputStream fileIn = new FileInputStream("myFile.txt");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			gs = (GameWindow) in.readObject();
			in.close();
			fileIn.close();
		}
		catch (IOException i) {
			i.printStackTrace();
			return;
		}
		catch (ClassNotFoundException c) {
			// TODO: handle exception
			System.out.println("GameWindow class not found");
			c.printStackTrace();
			return;
		}
	}

}