

/**
 * 
 * @author s4492921
 *
 */
class Vertex{
	String name;
	Vertex fatherV;
	double g=0;
	boolean checked = false;
	boolean closed = false;
	//for Astar Algorithm
	//Map<String, Integer> heuristic;
	
	public Vertex(String name){
		this.name=name;
	}
}
