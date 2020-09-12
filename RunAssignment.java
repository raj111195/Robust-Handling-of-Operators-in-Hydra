package assignment;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

/*Test class*/
public class RunAssignment {

	public static void main(String[] args) throws IOException {

		ArrayList<Dfa> automata = new ArrayList<Dfa>();		
		ArrayList<Dfa> vennDiagram = new ArrayList<Dfa>();		
		ArrayList<Dfa> complementedAutomata = new ArrayList<Dfa>();		
		ArrayList<Dfa> complementedVennDiagram = new ArrayList<Dfa>();	
		ArrayList<Dfa> resultVennDiagram = new ArrayList<Dfa>();		
		ArrayList<String> inputs = new ArrayList<String>();
		ArrayList<ArrayList<Integer>> inputIndexes = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> equations = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<String>> regexList = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> compRegexList = new ArrayList<ArrayList<String>>();
		ArrayList<Integer> counts = new ArrayList<Integer>();
		HashSet<Character> globalAlphabet = new HashSet<Character>();

		automata.add(Dfa.getPhi());
		complementedAutomata.add(Dfa.getPhi());
		ArrayList<Integer> tempEquation1 = new ArrayList<Integer>(); //for 0th equation
		equations.add(tempEquation1);
		counts.add(0);

		Scanner sc = new Scanner(System.in);
			
		System.out.print("Enter the number of cardinality constraints :   ");
		
		int iterations = sc.nextInt(), i=0, j;
		
		long newPowValue = 2 ;
		
		
		System.out.println("\nEnter the cardinality constraints ( String , count ) :  ");
		System.out.println("===================================");
		
		
		while(i<iterations)		
		{
			
		//Regular expression must be entered in a certain form
		//System.out.println("Regular expression entry: (x+y)*xyy should be entered as \n"
		//		+ "(\"x\"|\"y\")*\"x\"\"y\"\"y\" \n");
		
		//Get regular expression input from user
		//System.out.println("===================================");
		
		i++;
		String regular = sc.next();
		
		counts.add(sc.nextInt());
		
		inputs.add(regular);
		
		for(int counter=0; counter< regular.length() ;counter++)
			if(validChar(regular.charAt(counter)))
			globalAlphabet.add(regular.charAt(counter));
		
		ArrayList<Integer> tempEquation = new ArrayList<Integer>();
		equations.add(tempEquation);
		
		}
		
		String allAlpha = "(";
		
		Iterator<Character> value = globalAlphabet.iterator(); 
		  
        // Displaying the values after iterating through the set 
        
        while (value.hasNext()) { 
            allAlpha+=value.next();
            allAlpha+="|";
        }
        
        allAlpha=allAlpha.substring(0, allAlpha.length()-1) + ")*";
        
     //   System.out.println(allAlpha);
    
        
		i=0;
		
		while(i<iterations) {
			
			RegExp r = null;	//Regular Expression
			Nfa nfa = null;		//NFA of regular expression
			Dfa dfa = null;		//DFA constructed from NFA
			Dfa compDfa = null;
			
			String regularExpression = inputs.get(i);

			i++;	
		
		String regExp = sanitize(new StringBuilder(regularExpression), allAlpha);

		//Convert regular expression to expression tree, check for syntax errors first
        try 
        {
        	r = (new RegExp2AST(regExp)).convert(); 
   		  
   		//  	System.out.println("\nNo syntax errors, regular expression converted to expression tree");
   		//  	System.out.println("Fully decomposed regular expression : " + r.decompile()+"\n");  
        
        }catch (ParseException e) 
        {
        	System.out.println("Error at position " + e.getErrorOffset() + " : " + e.getMessage());
        }
        
        
       // System.out.println("===================================");
        //System.out.println("2.Regular Expression to NFA");
        //System.out.println("===================================");
        //Convert RE to NFA
        nfa = r.makeNfa();
       // System.out.println(nfa.toString());
        //System.out.println("===================================\n\n");
        
        
       // System.out.println("===================================");
       // System.out.println("3.NFA to DFA");
       // System.out.println("===================================");
        //Convert NFA to DFA
        dfa = new Dfa(nfa, globalAlphabet);
       // System.out.println(dfa);
        
      
        dfa = Minimizer.minimize(dfa);
        
        //System.out.println("lolol" + dfa.getSize());


        
        
        dfa.setAcceptedWord(FindWord.findWord(dfa));
        dfa.setRegularExpression(regularExpression);
        automata.add(dfa);
      //  CrossProduct.printCrossProduct(dfa);

        
        Nfa.clearNfa();
       FindWord.clear();
        
        compDfa = Complement.complement(dfa);
        compDfa = Minimizer.minimize(compDfa);
        

       // CrossProduct.printCrossProduct(compDfa);

        compDfa.setAcceptedWord(FindWord.findWord(compDfa));
        compDfa.setRegularExpression(regularExpression);
        complementedAutomata.add(compDfa);
             

        FindWord.clear();
    //    System.out.println(dfa2);
        
     //   System.out.println(cross);
        
        //System.out.println("===================================\n");
        
        
        //Test a string using the DFA above
     
	}
	   
		
		vennDiagram.add(automata.get(0));
        vennDiagram.add(automata.get(1));
        
        ArrayList<Integer> eqTemp0 = new ArrayList<Integer>();
        inputIndexes.add(eqTemp0);
        ArrayList<Integer> eqTemp1 = new ArrayList<Integer>();
        eqTemp1.add(1);
        inputIndexes.add(eqTemp1);
        

        
        ArrayList<String> temp0 = new ArrayList<String>();
        regexList.add(temp0);
        compRegexList.add(temp0);
        
        ArrayList<String> temp1 = new ArrayList<String>();
        temp1.add(automata.get(1).getRegularExpression());
        regexList.add(temp1);
        compRegexList.add(temp1);
        
        complementedVennDiagram.add(complementedAutomata.get(0));
        complementedVennDiagram.add(complementedAutomata.get(1));

		
		for(int counter=2; counter<= iterations; counter++) 
        { 
			
			Dfa current = automata.get(counter);
			vennDiagram.add(current);
			
			ArrayList<String> temp3 = new ArrayList<String>();
			temp3.add(current.getRegularExpression());
			regexList.add(temp3);
	        compRegexList.add(temp3);
			
			Dfa complementedCurrent = complementedAutomata.get(counter);
			complementedVennDiagram.add(complementedCurrent);
			
			 ArrayList<Integer> eqTemp2 = new ArrayList<Integer>();
		     eqTemp2.add(counter);
		     inputIndexes.add(eqTemp2);   
			
			for(int copier = 1 ; copier < newPowValue; copier++)
			{
				temp3 = new ArrayList<String>();
				
				for (String already : regexList.get(copier))
		        	temp3.add(already);
				temp3.add(current.getRegularExpression());
				
				regexList.add(temp3);
		        compRegexList.add(temp3);
		        
				//System.out.println("Crossing " + String.valueOf(counter) + "Crossing Venn" + String.valueOf(copier) + " : ");
				
				
				Dfa crossDfa = CrossProduct.computeCrossProduct(current, vennDiagram.get(copier));
			
				
				//	CrossProduct.printCrossProduct(crossDfa);

				crossDfa = Minimizer.minimize(crossDfa);
		        crossDfa.setAcceptedWord(FindWord.findWord(crossDfa));

				
			//	CrossProduct.printCrossProduct(crossDfa);    //   DOOOOOOOOO


				vennDiagram.add(crossDfa);
				
		        FindWord.clear();

		//		System.out.println("Set poda Hahaha" + String.valueOf(copier) + " : ");

				
				Dfa compCrossDfa =  CrossProduct.computeCrossProduct(complementedCurrent, complementedVennDiagram.get(copier));
				 compCrossDfa = Minimizer.minimize( compCrossDfa);

				compCrossDfa.setAcceptedWord(FindWord.findWord(compCrossDfa));
				complementedVennDiagram.add(compCrossDfa);
				

		        FindWord.clear();

			//	System.out.println("Set vaada " + String.valueOf(copier) + " : ");
		        
		        ArrayList<Integer> eqTemp3 = new ArrayList<Integer>();
		        
		        for (Integer already : inputIndexes.get(copier))
		        	eqTemp3.add(already);			
			    eqTemp3.add(counter);
			     inputIndexes.add(eqTemp3);   			

			}
			newPowValue*=2;
        }
			
		resultVennDiagram.add(Dfa.getPhi());
		
		for(int counter = 1; counter <vennDiagram.size()-1 ; counter++)
		{
			Dfa totalCrossDfa =  CrossProduct.computeCrossProduct(vennDiagram.get(counter), complementedVennDiagram.get(complementedVennDiagram.size()-1-counter));
			
			totalCrossDfa = Minimizer.minimize(totalCrossDfa);
			totalCrossDfa.setAcceptedWord(FindWord.findWord(totalCrossDfa));

			FindWord.clear();
			
			if(totalCrossDfa.getStartStateNumber()!=-1)
				for(Integer c : inputIndexes.get(counter))
					equations.get(c).add(counter);
			
			resultVennDiagram.add(totalCrossDfa);
		}
		
		resultVennDiagram.add(vennDiagram.get(vennDiagram.size()-1));

		System.out.println("\n\nVENN DIAGRAM");
		
		for(int counter = 1; counter <resultVennDiagram.size() ; counter++)
		{   System.out.println("\n");
			System.out.println("Set " + String.valueOf(counter) + " : ");
			System.out.println("Regular Set : " + regexList.get(counter));
			System.out.println("Complemented Set : " + regexList.get(regexList.size()-1-counter));
			System.out.print("Intersection is Null? : ");
			if(vennDiagram.get(counter).getStartStateNumber()==-1)
				System.out.print("Yes  ");
				else
					System.out.print("No  ");
			if(complementedVennDiagram.get(complementedVennDiagram.size()-1-counter).getStartStateNumber()==-1)
				System.out.println(", Yes  ");
				else
					System.out.println(",  No  ");

			
			System.out.println("Accepted Word : " + resultVennDiagram.get(counter).getAcceptedWord());
			//System.out.println("Complemented Accepted Word : " + complementedVennDiagram.get(counter).getAcceptedWord());
					
		}
		
		

			System.out.println("\n\nFinal Equations : \n");
		
		for(int counter = 1; counter<equations.size() ; counter++)
		{ 
			//System.out.println(equations.get(counter));
			String e = "";
		
		for (int temp=0; temp < equations.get(counter).size() ; temp++)
			e += "y"+ String.valueOf(equations.get(counter).get(temp)) + "+"; 
		
		e=e.substring(0, e.length()-1);
		
		e+= "=" + String.valueOf(counts.get(counter));
		
		System.out.println(e);
		}
		
	//	CrossProduct.printCrossProduct(resultVennDiagram.get(12));
	//	CrossProduct.printCrossProduct(vennDiagram.get(12));
	//	CrossProduct.printCrossProduct(complementedVennDiagram.get(12));
		//System.out.println(resultVennDiagram.get(1).getStartStateNumber());

				/*for(j = 0; j < iterations ; j++) 
            { 
                 Check if jth bit in the  
                counter is set If set then  
                print jth element from set 
                if((counter & (1 << j)) > 0) 
                    System.out.print(set[j]); 
             
              
          //  System.out.println(); */
    /*    } 
        }
        Dfa cross = CrossProduct.computeCrossProduct(automata.get(1), automata.get(2));
        
        CrossProduct.printCrossProduct(cross);
        
        Dfa mcross = Minimizer.minimize(cross);

		System.out.println("Intersection : ");

       CrossProduct.printCrossProduct(cross);
       

       // CrossProduct.printCrossProduct(Complement.complement(cross));

		//System.out.println("yaay");
        CrossProduct.printCrossProduct(mcross);
        mcross.setAcceptedWord(FindWord.findWord(mcross));
        System.out.println("Accepted word : " + mcross.getAcceptedWord());
        
         System.out.println("Enter a string to test, 0 to exit: \n");
        String testString = sc.next();
        if(!testString.equals("0")){
            do{
                if(mcross.acceptString(testString)){
                	System.out.println("STRING: '"+testString+"' IS ACCEPTED!!\n");
                }
                else{
                	System.out.println("STRING: '"+testString+"' IS REJECTED!!\n");
                }  
                System.out.println("Enter a string to test, 0 to exit: ");
                testString = sc.next();
            }
            while(!testString.equals("0"));
        }
        sc.close();
       
        */
	}

	private static String sanitize(StringBuilder s, String all) {
		// TODO Auto-generated method stub
		
		int index = s.indexOf("%");
	    while (index != -1)
	    {
	        s.replace(index, index+1, all);
	        index += all.length(); // Move to the end of the replacement
	        index = s.indexOf("%", index);
	    }
		
		for(int i=0;i<s.length();i++)
		{
			if(validChar(s.charAt(i)))
			{
				s.insert(i,'\"');
				s.insert(i+2,'\"');
				i=i+2;
			}
		}
		//System.out.println(s.toString());
		return s.toString();
	}

	private static boolean validChar(char c) {
		// TODO Auto-generated method stub
		if(c=='(' || c==')' || c=='*' || c=='|' || c=='?' || c=='+'|| c=='%' )
			return false;
		return true;
	}
}
