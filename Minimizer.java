package assignment;


import java.util.ArrayList;


public class Minimizer {
	
			
			// constructs a new DFA object from a file as per the in class specs
	    /*
	     * format of the input file:
	     * IntegerN - the number of terminal characters in the machine
	     * Terminal * N - all of the terminals separated by a space
	     * NumStates - number of states in the machine
	     *  - next is NumStates * IntegerN rows of transitions
	     * StartState TerminalTransitionChar EndState
	     * NumFinalStates - number of final states
	     * FinalStates - the final states of the machine separated by spaces
	     *
	     * Check the minmachine.txt file for a complete minimizable example.
	     */
		
		
	public static Dfa minimize( Dfa old )	
	{
		if(old.getStartStateNumber()==-1)
			return Dfa.getPhi();

		if(old.getUniversalDfaStatus())
			return old;

		 if(old.getStartStateNumber()==-1)
			 return Dfa.getPhi();
		 
		 if(old.getSize()==0)
			 return Dfa.getPhi();
		 
		 if(old.alphabetArray.size()==0)
			 return Dfa.getPhi();
		
		Dfa minDfa = new Dfa();

		 int numstates, numterminals;
			int oldTransTable[][];
			 ArrayList<Character> oldAlphabetArray = new ArrayList<Character>();	//Alphabet of the NFA and thus the DFA

				ArrayList<Integer> oldFinalStates = new ArrayList<Integer>();	 
				ArrayList<Integer> newFinalStates = new ArrayList<Integer>();	 
				ArrayList<Integer> newNonFinalStates = new ArrayList<Integer>();	 
			 
			 numstates = old.getSize() ;
			 
			
			for(Character c : old.alphabetArray)
				oldAlphabetArray.add(c);	
			
			for(Integer c : old.acceptStatesPos)
				oldFinalStates.add(c);
			
			oldTransTable = old.getTransTable();
			numstates = old.getSize();
			numterminals = old.alphabetArray.size();
					
			boolean mark[][] = new boolean[old.getSize()][old.getSize()];
			// the list of reachable states
			boolean reachable[] = new boolean[old.getSize()];
			// a flag for various uses throughout the method
			boolean flag = true;
			
			// remove all unreachable states from consideration altogether
			// assuming we can always reach q0
			reachable[old.getStartStateNumber()] = true;
			boolean visited[] = new boolean[numstates];
			// queue-less bfs!
			while ( flag )	{
				flag = false;
				for ( int state = 0; state < numstates; state++ )	{
					if ( reachable[state] && !visited[state] )	{
						visited[state] = true;
						flag = true;
						for ( int term = 0; term < numterminals; term++ )	{
						//	System.out.println(String.valueOf(state) +"   " + String.valueOf(term));
							reachable[ oldTransTable[state][term] ] = true;
						}
					}// end if
				}// endfor state
			}// end while
			
			// find all pairs of states (p,q) such that final[p] != final[q]
			for ( int statenum = 0; statenum < numstates; statenum++ )	{
				if ( !reachable[statenum] )	continue;
				for ( int statenum2 = 0; statenum2 < numstates; statenum2++ )	{
					if ( !reachable[statenum2] )	continue;
					if ( oldFinalStates.contains(statenum) != oldFinalStates.contains(statenum2) )
						mark[statenum][statenum2] = true;
					else
						mark[statenum][statenum2] = false;
				}
			}
			
			// find all pairs (p,q) and mark them as distinguishable
			// i am looking at oldTransTable[p] foreach terminal and oldTransTable[q], if both
			// states are distinct, then mark these
			flag = true;
			// continue until no mark has been made
			while ( flag )	{
				flag = false;
				for ( int x = 0; x < numstates; x++ )	{
					if ( !reachable[x] )	continue;
					for ( int y = 0; y < numstates; y++ )	{
						if ( x == y )	continue;
						for ( int t = 0; t < numterminals; t++ )	{
							if ( mark[ oldTransTable[x][t] ][ oldTransTable[y][t] ] && !mark[x][y] )	{
								mark[x][y] = true;
								flag = true;
							}
						}// end terminal
					}// endfor second state
				}// endfor first state
			}// end while
			
			int minstates[] = new int[numstates];
			for ( int state = 0; state < numstates; state++ )	{
				visited[state] = false;
				if ( !reachable[state] )
					minstates[state] = -1;
				else
					minstates[state] = state;
			}
			
			// extract the distinguishable states
			for ( int state = 0; state < numstates; state++ )	{
				if ( minstates[state] == -1 || visited[state] )	continue;
				for ( int p = 0; p < numstates; p++ )	{
					if ( p == state || minstates[p] == -1 )	continue;
					if ( !mark[state][p] && !visited[p] )	{
						minstates[p] = state;
						visited[p] = true;
					}
				}// endfor p
			}// endfor state
			
			// set minstate to have all of the new minimized state numbers
			int unique = 0;
			for ( int x = 0; x < minstates.length; x++ )	{
				if ( minstates[x] == x && minstates[x] != -1 )	{
					for ( int y = 0; y < minstates.length; y++ )	{
						if ( minstates[y] == x )	{
							minstates[y] = unique;
						}
					}
					unique++;
				}
			}
			
			// rearrange all the oldTransTables using the minstate array into a new oldTransTable array
			int newTransTable[][] = new int[numstates][numterminals];
			int minstatecounter = 0;
			int oldStartStateNumber = old.getStartStateNumber();
			int newStartStateNumber=-1;
			for ( int state = 0; state < numstates; state++ )	{
				// find all accessible vertecies and vertecies that have not been counted already
				if ( minstates[state] != -1 && minstatecounter <= minstates[state] )	{
					for ( int t = 0; t < numterminals; t++ )	{
						newTransTable[minstatecounter][t] = minstates[ oldTransTable[state][t] ];
						if(state == oldStartStateNumber )
							newStartStateNumber = minstatecounter;
					}
					if ( oldFinalStates.contains(state) )	{
						newFinalStates.add(minstatecounter);
					}
					else
						newNonFinalStates.add(minstatecounter);
					minstatecounter++;
				}
			}
			
			oldTransTable = newTransTable;
			numstates = minstatecounter;
			
			if(newFinalStates.size()==0)
				return Dfa.getPhi();
			
	//		if(newStartStateNumber==-1)
		//		return CrossProduct.getPhi();
			
			minDfa.setTransTable(newTransTable);
			minDfa.setStates(null);
			minDfa.setStartStateNumber(newStartStateNumber);
			minDfa.setSize(minstatecounter);
			minDfa.setAlphabetArray(old.alphabetArray);
			minDfa.setAcceptStatePos(newFinalStates);
			minDfa.setNonAcceptStatesPos(newNonFinalStates);
			
		return minDfa;
	}
	

/*
public String toString()	{
	StringBuffer buf = new StringBuffer();
	buf.append ( numterminals + "\n" );
	for ( int i = 0; i < numterminals; i++ )
		buf.append ( terminals[i] + " " );
	buf.deleteCharAt ( buf.length() - 1 );
	buf.append ( "\n" + numstates + "\n" );
	for ( int i = 0; i < numstates; i++ )	{
		for ( int j = 0; j < numterminals; j++ )	{
			buf.append ( i + " " + terminals[j] + " " + oldTransTable[i][j] + "\n" );
		}
	}
	buf.append ( numfinalstates + "\n" );
	for ( int i = 0; i < numstates; i++ )
		if ( finalstates[i] == true )
			buf.append ( i + " " );
	buf.deleteCharAt ( buf.length() - 1 );
	return buf.toString();
}

		

			String s = "States |  \t";
		   for(char alpha:oldAlphabetArray){
			   s+= alpha+"  ";
		   } 
		   s+="\n-------|-----------------------------------------------------------------------"+"\n";
		   for(int i=0;i<minstatecounter;i++){
			   if(i<10)
				   s+=i+"      |\t";
			   else
				   s+=i+"     |\t";
			   
			   for(int j=0; j<oldAlphabetArray.size();j++){
				   s+=newTransTable[i][j]+"  ";
			   }
			   if(newFinalStates.contains(i))
				   s+= "\t ACCEPT STATE";
			   if(i==newStartStateNumber)
				   s+="\t START";
			   s+="\n";
		   }
		 
		   System.out.println(s);
		   */



}
