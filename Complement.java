package assignment;

import java.util.ArrayList;
import java.util.HashSet;

public  class Complement {

	
	public static Dfa complement(Dfa d)
	{
		
		if(d.getStartStateNumber()==-1)
			{d.setUniversalDfaStatus(true);
			return d;
			}
		
		Dfa comp = new Dfa();
			 
			ArrayList<Character> alphabetArray = new ArrayList<Character>();
			ArrayList<Integer> compNonAcceptStatesPos = new ArrayList<Integer>();
			ArrayList<Integer> compAcceptStatesPos = new ArrayList<Integer>();
		
			for(Character a : d.alphabetArray)
				alphabetArray.add(a);		
			for(Integer a : d.acceptStatesPos)
				compNonAcceptStatesPos.add(a);
			for(Integer a : d.nonAcceptStatesPos)
				compAcceptStatesPos.add(a);
			
		comp.setStartStateNumber(d.getStartStateNumber());
		comp.setAcceptStatePos(compAcceptStatesPos);
		comp.setNonAcceptStatesPos(compNonAcceptStatesPos);
		comp.setAlphabetArray(alphabetArray);
		comp.setSize(d.getSize());
		comp.setStates(null);
		comp.setUniversalDfaStatus(false);		
		comp.setTransTable(d.getTransTable());
		
		
		return comp;
				
	}
	
}


























