import java.util.*;
import java.lang.*;
import java.io.*;

/**
 * for PQ sorting
 * @author s44929217
 */

public class SGraph {
	
	public static void main(String args[]) throws Exception{
		
		Map<String,Map<String,Edge>> sGraph = new HashMap<String,Map<String,Edge>>();
		Map<String,Vertex> nodeValue = new HashMap<String, Vertex>();
		
		String starttmp,endtmp = null;
        
        ProblemIO pi = new ProblemIO(args[0],args[1],args[2]);
        
        sGraph = pi.readEnvTo();
        
        for (String key : sGraph.keySet()) {
            nodeValue.put(key,new Vertex(key));
        }
        
        List<String> querylist = pi.readQuery();
       
        pi.readInputClose(); 
        
        for (String query : querylist){
        	
        	starttmp = pi.parseQuery(query, sGraph, nodeValue);
        	starttmp ="aa;bb";
        	endtmp = starttmp.substring(starttmp.indexOf(";")+1);
        	starttmp = starttmp.substring(0, starttmp.indexOf(";"));
        	
        	
        	String result = new uniSearch().get(sGraph, nodeValue, starttmp, endtmp);
        	
        	pi.output(args[2],result);
    		
        	pi.removeNode(sGraph,nodeValue,starttmp,endtmp);
        }
	}
	

}
