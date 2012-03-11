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
	
	/* Retorna o primeiro nó */
	public DNode getFirst() throws IllegalStateException {
		if (isEmpty()) throw new IllegalStateException ("Não há discografias cadastradas");
		return cabeca.getNext();
	}
	
	/* Retorna o último nó */
	public DNode getLast() throws IllegalStateException {
		if (isEmpty()) throw new IllegalStateException ("Não há discografias cadastradas"); 
		return cauda.getPrev(); 
	}
	
	/* Retorna o antecessor de uma dada discografia */
	public DNode getPrev (DNode v) throws IllegalArgumentException {
		if (v == cabeca) throw new IllegalArgumentException ("Não há antecessores");
		return v.getPrev();
	}
	
	/* Retorna o sucessor de uma discografia dada */
		public DNode getNext (DNode v) throws IllegalArgumentException {
		if (v == cauda) throw new IllegalArgumentException ("Não há sucessores");
		return v.getNext();
	}
	
	/* Add a discografia dada z antes de v. */
	public void addBefore(DNode v, DNode z) throws IllegalArgumentException{
		DNode u = getPrev(v); 
		z.setPrev(u);
		z.setNext(v); 
		v.setPrev(z); 
		u.setNext(z); 
		size++; 
	}
	
	/* Add a discografia dada z após v. Gera erro se v é cauda */
	public void addAfter(DNode v, DNode z) {
		DNode w = getNext(v); 
		z.setPrev(v);
		z.setNext(w); 
		w.setPrev(z); 
		v.setNext(z); 
		size++; 
	}
	
	/*Insere uma nova discografia no inicio da lista*/
	public void addFirst(DNode v){
		addAfter(cabeca,v); 
	}
	
	/* Insere uma nova discografia no fim da lista */
	public void addLast(DNode v){
		addBefore(cauda,v); 
	}	
}