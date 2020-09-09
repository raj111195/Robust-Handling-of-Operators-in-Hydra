package assignment;

import java.util.ArrayList;

public class FindWord {

	static Integer found;;
	static String word;;
	
	public static String findWord(Dfa dfa)
	{
		int start = dfa.getStartStateNumber();
		int visited[][];
		found = new Integer(0);
		word = new String();

		if(start==-1)
			return "NULL";
		else if(dfa.acceptStatesPos.size()>0 && start==dfa.acceptStatesPos.get(0))
		{
			int transTable[][] = dfa.getTransTable();
			
			for(int k=0; k<dfa.alphabetArray.size();k++)
			{
				if(transTable[start][k]==dfa.acceptStatesPos.get(0))
					return dfa.alphabetArray.get(k).toString();
			}
			
			for(int t=1; t<dfa.acceptStatesPos.size();t++)
			for(int k=0; k<dfa.alphabetArray.size();k++)
			{
				if(transTable[start][k]==dfa.acceptStatesPos.get(t))
					return dfa.alphabetArray.get(k).toString();
			}
			dfa.setStartStateNumber(-1);
			return "NULL";
			
		}
		else 	{			
			visited=new int[dfa.getSize()][dfa.alphabetArray.size()];

			find(dfa.getTransTable(),dfa.alphabetArray,dfa.alphabetArray.size(),start,dfa.acceptStatesPos.get(0),visited,new StringBuilder(""),0);		
			
		/*	if(word=="") {
			for ( int state = 0; state <dfa.getSize() ; state++ )	
				{for ( int p = 0; p <dfa.getSize(); p++ )	
					{System.out.println(visited[state][p]);}
					System.out.println("\n");
				}
			}*/
					
		}
	return word;	
	}

	private static void find(int[][] transTable, ArrayList<Character> alphabetArray, int size, int start,int finalState, int[][] visited,StringBuilder s, int index) {
		// TODO Auto-generated method stub
		
		if(found==1)
			return;
		
		if(start==finalState)
		{
			found=1;
			word = s.toString().trim();
		}
		
//	System.out.println(s.toString());
		
		s.insert(index," ");
		
		
		for(int i=0;i<size;i++)
			{
			if(found==1)
				return;
			
			if(visited[start][i]==0) {
			visited[start][i]=1;	
			//flag=1;
			s.setCharAt(index, alphabetArray.get(i));			
			find(transTable,alphabetArray,size,transTable[start][i],finalState,visited,s,index+1);
			}
			
			}
		
		s.deleteCharAt(s.length()-1);

			}
	

public static void clear() {
	FindWord.found= new Integer(0);
	FindWord.word = new String();
}

}

