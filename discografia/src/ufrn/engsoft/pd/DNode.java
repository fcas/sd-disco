package ufrn.engsoft.pd;

public class DNode {

	/*Nome da discografia a ser armazenado*/

	protected String discografia; 
	protected DNode next, prev;
	
	public DNode (String e, DNode p, DNode n) {
		discografia = e; 
		prev = p; 
		next = n; 
	}
	
	public String getDiscografia() { return discografia; }
	
	public DNode getPrev(){ return prev; }
	
	public DNode getNext(){ return next; }
	
	public void setDiscografia(String newDisco){ discografia = newDisco; }
	
	public void setPrev(DNode newPrev){	prev = newPrev; }
	
	public void setNext(DNode newNext){	next = newNext;	}

}
