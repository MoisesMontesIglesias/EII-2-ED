package P2.grafos;

import java.text.DecimalFormat;


public class Grafos <T>{

	private double[][] weights;
	private boolean[][] edges;
	private T[] nodes;
	private int size;
	
	/*Algoritmo Floyd*/
	/****Matrices A y P****/
	//private double [][] a;
	//private int [][] p;
	
	/*Cadena recorrido profundidad*/
	//String cadenaDFS;
	
	@SuppressWarnings("unchecked")
	public Grafos (int dimension) {
		//Sale un warning porque el compilador no sabe el 
		this.weights = new double[dimension][dimension];
		this.edges = new boolean[dimension][dimension];
		this.nodes = (T[]) new Object[dimension];
		this.size = 0;
	}
	
	public int getSize() {
		return size;	
	}
	
	public int getNode(T node) {
		if(node == null) {
			return -1;
		}
		for (int i = 0; i < size; i++) {
			if(nodes[i].equals(node)) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean addNode(T node) {
		if(existsNode(node)) {
			return false;
		} else if(node == null) {
			throw new NullPointerException();
		}else if(size > nodes.length){
			throw new FullStructureException(node);
		}else {
			nodes[size] = node;
			for (int i = 0; i <= size; i++) {
				weights[i][size] = 0;
				weights[size][i] = 0;
				edges[i][size] = false;
				edges[size][i] = false;
			}
			size++;
			return true;
		}
	}
	
	public boolean existsNode(T node) {
		if (getNode(node) != -1) {
			return true;
		}
		return false;
	}
	
	public boolean removeNode(T node) {
		if(node == null) {
			throw new NullPointerException();
		}
		if(existsNode(node) == false) {
			return false;
		}
		int pos = getNode(node);
		size--;
		nodes[pos] = nodes[size];
		for(int i = 0; i < getSize(); i++) {
			edges[i][pos] = edges[size][i];
			edges[pos][i] = edges[i][size];
			weights[i][pos] = weights[size][i];
			weights[pos][i] = weights[i][size];
		}
		return true;
	}
	
	
	public boolean addEdge(T source, T target, double weight) {
		if(existsNode(source) == false) {
			throw new ElementNotPresentException(source);
		}
		if(existsNode(target) == false) {
			throw new ElementNotPresentException(target);
		}
		if(weight < 0) {
			throw new IllegalArgumentException();
		}
		if(existsEdge(source, target) == false) {
			weights[getNode(source)][getNode(target)] = weight;
			edges[getNode(source)][getNode(target)] = true;
			return true;
		} else if(existsEdge(source, target) == true) {
			return false;
		}
		return false;
	}
	
	public boolean existsEdge(T source, T target) {
		if(existsNode(source) == false || existsNode(target) == false) {
			return false;
		} else {
			return edges[getNode(source)][getNode(target)];
		}
	}
	
	public double getEdge(T source, T target) {
		if(existsNode(source) == false) {
			throw new ElementNotPresentException(source);
		}
		if(existsNode(target) == false) {
			throw new ElementNotPresentException(target);
		}
		if(existsEdge(source, target) == false) {
			return -1;
		} 
		return weights[getNode(source)][getNode(target)];
	}
	
	public boolean removeEdge(T source , T target) {
		if(existsNode(source) == false) {
			throw new ElementNotPresentException(source);
		}
		if(existsNode(target) == false) {
			throw new ElementNotPresentException(target);
		}
		if(existsEdge(source, target) == false) {
			return false;
		}else {
			edges[getNode(source)][getNode(target)] = false;
			weights[getNode(source)][getNode(target)] = 0;
			return true;
		}
	}
	
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.##");
		String cadena = "";
		cadena += "NODOS\n";
		for (int i = 0; i < size; i++) {
			cadena += nodes[i].toString() + "\t";
		}
		cadena += "\n\nARISTAS\n";
		for (int i = 0; i <size; i++) {
			for (int j = 0; j < size; j++) {
					if (edges[i][j])
						cadena += "T\t";
					else
						cadena += "F\t";
			}
			cadena += "\n";
		}
		cadena += "\nPESOS\n";
		for (int i = 0; i <size; i++) {
			for (int j = 0; j < size; j++) {
				cadena += (edges[i][j]?df.format(weights[i][j]):"-") + "\t";
			}
			cadena += "\n";
		}
		return cadena;
	}
	
}
