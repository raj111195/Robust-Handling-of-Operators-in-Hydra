package assignment;

public class Union {


public static Nfa union(Nfa lft, Nfa rgt)
{
	
	Nfa altNfa = new Nfa();
	//Add new START and ACCEPT states...
	NfaState accept = new NfaState(null,null,'1',Nfa.GnumStates++);
	NfaState start = new NfaState(lft.start,rgt.start,'0','0',Nfa.GnumStates++);
	//With epsilon transitions to START of left and right expressions
	altNfa.setStart(start);
	lft.accept.next1 = accept;
	lft.accept.symbol = '0';
	rgt.accept.next1 = accept;
	rgt.accept.symbol = '0';
	altNfa.setAccept(accept);
	//2 new states added(START and ACCEPT)
	altNfa.setNumStates(lft.getNumStates()+rgt.getNumStates()+2);
	return altNfa;
	
}

public static Dfa unionDfa(Dfa lft, Dfa rgt)
{
	/*Nfa nfa = new Nfa();
    NfaState accept = new NfaState(null,null,'1',Nfa.GnumStates++);
    NfaState start = new NfaState(accept,null,lft.oldNfa.,upper,Nfa.GnumStates++,true);
    nfa.setStart(start);
    nfa.setAccept(accept);
    nfa.setNumStates(2);   
	
	return new Dfa((union(lft.getOldNfa(),rgt.oldNfa)));*/
	
	return lft;
}
	
	
}
