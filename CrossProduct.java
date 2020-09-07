package assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CrossProduct {

public static Dfa computeCrossProduct( Dfa a, Dfa b)
{

	Dfa res = new Dfa();
	
	if(a.getStartStateNumber()==-1 || b.getStartStateNumber()==-1)
		return Dfa.getPhi();
	
	if(a.getUniversalDfaStatus() && b.getUniversalDfaStatus())
		return Dfa.getUniversalDfa();
	else if(a.getUniversalDfaStatus())
		return Dfa.copyDfa(b);
	else if(b.getUniversalDfaStatus())
		return Dfa.copyDfa(a);
	
    ArrayList<Character> aAlphabetArray = new ArrayList<Character>();	//Alphabet of the NFA and thus the DFA
    ArrayList<Character> bAlphabetArray= new ArrayList<Character>();	//Alphabet of the NFA and thus the DFA
    ArrayList<Integer> finalStates= new ArrayList<Integer>();	//Alphabet of the NFA and thus the DFA
    ArrayList<Integer> nonFinalStates= new ArrayList<Integer>();	//Alphabet of the NFA and thus the DFA
    
	
int count=0;

//System.out.println("a.states.size()" + a.states.size());
//System.out.println("b.states.size()" + b.states.size());

Map<String, Integer> tempTrans = new HashMap<String, Integer>();

int startStateNumber;

	for(Character c : a.alphabetArray)
		aAlphabetArray.add(c);
	//for(Character c : b.alphabetArray)
	//	bAlphabetArray.add(c);
	
//	aAlphabetArray.retainAll(bAlphabetArray);
	
	//if(aAlphabetArray.size()==0)
		//return getPhi();
	
	//System.out.println("common alpha : ");

//	System.out.println(aAlphabetArray);
	//System.out.println(a.states.size()+1);
	
	if(aAlphabetArray.size()==0 || a.getSize()==0)
		return Dfa.getPhi();

	
	int [][]	resTransTable = new int[(a.getSize())*(b.getSize())][aAlphabetArray.size()];
	
	//System.out.println(a.alphabetArray);
	
	//System.out.println("common alpha : ");
	//System.out.println(aAlphabetArray);
	
	for(int i=0;i<a.getSize();i++)
		for(int j=0;j<b.getSize();j++)
		{
			String s="";
			s+=String.valueOf(i)+String.valueOf(j);
			tempTrans.put(s, count);
			
		//	System.out.println(count + s);
			
			if(a.acceptStatesPos.contains(i) && b.acceptStatesPos.contains(j))
			finalStates.add(count);
			else
			nonFinalStates.add(count);
		//	System.out.println(s + "  " + count);

			count++;
		}
	
	startStateNumber = tempTrans.get(String.valueOf(a.getStartStateNumber()) + String.valueOf(b.getStartStateNumber()) );	
	count=0;
	
	for(int i=0;i<a.getSize();i++)
	{		for(int j=0;j<b.getSize();j++)
		{
			for(int k=0;k<aAlphabetArray.size();k++)
				{
						int aOffset = a.alphabetArray.indexOf(aAlphabetArray.get(k));
						int bOffset = b.alphabetArray.indexOf(aAlphabetArray.get(k));
						String s="";
						
						//		System.out.println(aOffset + "  " + bOffset);

						s+=String.valueOf(a.transTable[i][aOffset])+String.valueOf(b.transTable[j][bOffset]);
						
				//		System.out.println("trans" + s);
					//			System.out.println("t"  + count + "t" + k);	
						
						resTransTable[count][k]=tempTrans.get(s);
						
						//System.out.println("poda2" + tempTrans.get(s));	
						
						
				}
			count++;

		}
	}
	
	
	//	System.out.println("table");	

/*	
	for(int i=0;i<count;i++)
	{		for(int j=0;j<aAlphabetArray.size();j++)
	{ 						//System.out.print("poda");	
		System.out.print(resTransTable[i][j] + "\t");
	}System.out.println(" ");
	}

	public void printCross()
*/
	
	res.setTransTable(resTransTable);
	res.setStates(null);
	res.setStartStateNumber(startStateNumber);
	res.setSize(count);
	res.setAlphabetArray(aAlphabetArray);
	res.setAcceptStatePos(finalStates);
	res.setNonAcceptStatesPos(nonFinalStates);
	
	return res;
	
}


public static void printCrossProduct(Dfa cross)
{

	String s = "States |  \t";
   for(char alpha: cross.alphabetArray){
	   s+= alpha+"  ";
   } 
   s+="\n-------|-----------------------------------------------------------------------"+"\n";
   for(int i=0;i<cross.size;i++){
	   if(i<10)
		   s+=i+"      |\t";
	   else
		   s+=i+"     |\t";
	   
	   for(int j=0; j<cross.alphabetArray.size();j++){
		   s+=cross.transTable[i][j]+"  ";
	   }
	   if(cross.acceptStatesPos.contains(i))
		   s+= "\t ACCEPT STATE";
	   if(i==cross.startStateNumber)
		   s+="\t START";
	   s+="\n";
   }
   s+="Word : "+cross.getAcceptedWord();

   System.out.println(s);
   
}
	
}
