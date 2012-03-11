package ufrn.engsoft.pd;

import ufrn.engsoft.pd.DNode; 

public class DList {

	protected int size; 
	protected DNode cabeca, cauda; 
	
	/* Constrói uma lista vazia */
	public DList (){
		size = 0; 
		cabeca = new DNode(null,null,null);
		cauda = new DNode (null,cabeca,null);
		cabeca.setNext(cauda); 
	}
	
	/* Retorna o tamanho da lista */
	public int size(){ return size; }
	
	public boolean isEmpty(){ return (size==0);}
	
	/*Retorna o primeiro nó*/
	public DNode getFirst() throws IllegalStateException {
		if (isEmpty()) throw new IllegalStateException ("Não há discografias cadastradas");
		return cabeca.getNext();
	}
	
	/*Retorna o último nó*/
	
	public DNode getLast() throws IllegalStateException {
		if (isEmpty()) throw new IllegalStateException ("Não há discografias cadastradas"); 
		return cauda.getPrev(); 
	}
	
	
	
}
