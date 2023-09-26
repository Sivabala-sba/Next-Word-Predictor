import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class WordProcessor {

	public ArrayList<WordItem> fileRead(String name) throws IOException {
		ArrayList<WordItem> lines = new ArrayList<WordItem>();
		
		FileReader fileReader = new FileReader(name);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String aline = null;
      while ((aline = bufferedReader.readLine()) != null) {
         aline = aline.trim();
         if(aline.length() > 0) {
            int com = comma(aline);
            int occ = Integer.parseInt(aline.substring(com+1));
            WordItem wi = new WordItem(aline.substring(0, com), occ); 
            lines.add(wi);
         }
      }
      fileReader.close();
		return lines;
	}
   
   public int comma(String s) 
   {   
      int i = 0;
      while(i != s.length()) 
      {
         if(s.charAt(i) == ',') {
            return i;
         }
         i++;
      }
      return -1;
   }
}