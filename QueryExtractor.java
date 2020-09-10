package assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//

public class QueryExtractor {
	{
		
		

		
		String filename = "E:\\db\\codd-data-gen\\job_benchmark\\sqlqueries\\index";
		

	    BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    String line;
	    
	   try {
		if ((line = reader.readLine()) != null)
		    {
		      String query =  readFile("E:\\db\\codd-data-gen\\job_benchmark\\sqlqueries\\" + line);
		      System.out.print(query);
		    }
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
		

		try
		{

		}catch (Exception e)
		{
		  System.err.format("Exception occurred trying to original read '%s'.", filename);
		  e.printStackTrace();
		}

}
	

	@SuppressWarnings("resource")
	private String readFile(String file){
	 // List<String> records = new ArrayList<String>();
	  try
	  {
	    BufferedReader reader = new BufferedReader(new FileReader(file));
	    String line;
	   if ((line = reader.readLine()) != null)
	    {
	      return line;
	    }
	    reader.close();
	  //  return records;
	  }
	  catch (Exception e)
	  {
	    System.err.format("Exception occurred trying to read '%s'.", file);
	    e.printStackTrace();
	    return null;
	  }
	return null;
	}

}
