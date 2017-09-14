import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class uniSearch {
	
	private String result;

	
	public String get(Map<String,Map<String,Edge>> sGraph,Map<String,Vertex> nodeValue,String start, String end){
		
		Queue<Vertex> openQueue = new PriorityQueue<Vertex>(11, new VertexComparator());
		Set<Vertex> closedList = new HashSet<Vertex>();
		
		openQueue.add(nodeValue.get(start));
		
		Iterator ittr = nodeValue.entrySet().iterator();
		while (ittr.hasNext()) {
			Map.Entry entry = (Map.Entry) ittr.next();
			String key = (String) entry.getKey();
			Vertex vTmp = nodeValue.get(key);
			vTmp.checked=false;
			vTmp.closed=false;
		}
		
		while (openQueue.size()!=0){
			Vertex tmp = openQueue.peek();
			
			if (tmp.equals(nodeValue.get(end))){
				double l = tmp.g;
				String result = sGraph.get(tmp.name).get(tmp.fatherV.name).name;
				while(tmp.fatherV.fatherV!=null){
					tmp=tmp.fatherV;
					result = sGraph.get(tmp.name).get(tmp.fatherV.name).name+"-"+tmp.name+"-"+result;
					
				}
				result = l +";"+result;
			}
			
			
			//closedList.add(tmp);
			tmp.closed=true;
			openQueue.poll();
			
			Iterator iter = sGraph.get(tmp.name).entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				Vertex addTmp = nodeValue.get(key);
				Edge val = (Edge) entry.getValue();
				
				//if (!closedList.contains(addTmp)){
				if (!addTmp.closed){
    				
					if (!addTmp.checked){
					//if(!openQueue.contains(addTmp)){	
					addTmp.g= tmp.g+val.length;
						addTmp.fatherV=tmp;
						openQueue.add(addTmp);
						addTmp.checked=true;
					}
					 else
						if (addTmp.g > (tmp.g+val.length)){
							addTmp.g= tmp.g+val.length;
							addTmp.fatherV=tmp;
						}
				
				
				if (openQueue.size()==0)
				{
					result = "no-path";
				}
				
			}
			}
		}
		
		return this.result;
		
	}
	
}
