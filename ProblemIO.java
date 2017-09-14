import java.util.*;
import java.io.*;
import java.lang.*;

public class ProblemIO {
	
	private String URL_Input;
	private String URL_Output;
	private String URL_Environment;
	
	private FileInputStream environmentInput;
	private BufferedReader bufferedReader;
	private FileInputStream queryInput;
	private BufferedReader bufferedReader2;
	
	private Map<String,String> edgeV = new HashMap<String,String>();
	private Map<String, Double> edgeL = new HashMap<String, Double>();
	private Map<String, Double> edgeN = new HashMap<String, Double>();
    
	
	String str = null;
    String roadName = null;
    String start=null;
    String end=null;
    double roadLength;
    double nLots;
    
    String[] tmpA;
	String sNum;
	double startNum;
	String starttmp;
	double endNum;
	String endtmp;
	double d;
	String sot;
	double iv,jv;
	
	public ProblemIO(String s1,String s2, String s3){
		this.URL_Input=s2;
		this.URL_Environment=s1;
		this.URL_Output=s3;
	}
	
	/**
	 * Environment constructed
	 * @param sGraph
	 * @param nodeValue
	 * @throws IOException
	 */
	public void readEnvTo(Map<String,Map<String,Edge>> sGraph, Map<String,Vertex> nodeValue) 
			throws IOException
	{
		FileInputStream environmentInput = new FileInputStream(URL_Environment);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(environmentInput));  
        
        while((str = bufferedReader.readLine()) != null)  
        {         
        	roadName = str.substring(0, str.indexOf(";")).trim();
        	str = str.substring(str.indexOf(";")+1).trim();
        	start =str.substring(0, str.indexOf(";")).trim();
        	str = str.substring(str.indexOf(";")+1).trim();
        	end =str.substring(0, str.indexOf(";")).trim();
        	str = str.substring(str.indexOf(";")+1).trim();
        	roadLength = Double.parseDouble(str.substring(0, str.indexOf(";")).trim());
        	str = str.substring(str.indexOf(";")+1).trim();
        	nLots = Double.parseDouble(str.trim());
        	
        	edgeV.put(roadName, start+";"+end);
        	edgeL.put(roadName, roadLength);
        	edgeN.put(roadName,nLots);
        	
        	if (!nodeValue.containsKey(start)){
        		nodeValue.put(start, new Vertex(start));
        		sGraph.put(start, new HashMap<String,Edge>());
        	}
        	
        	if (!nodeValue.containsKey(end)){
        		nodeValue.put(end, new Vertex(end));
        		sGraph.put(end, new HashMap<String,Edge>());
        	}
        	
        	sGraph.get(start).put(end, new Edge(roadName,roadLength));
        	sGraph.get(end).put(start, new Edge(roadName,roadLength));
        	
        }	
        
        environmentInput.close();
        bufferedReader.close();
	}
	
	/**
	 * read the inputing query file
	 * @return a list of String as every line of query file
	 * @throws IOException
	 */
	public List<String> readQuery() throws IOException{
		
		List<String> querylist = new ArrayList<String>();
		queryInput = new FileInputStream(this.URL_Input);
	    BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(queryInput));  
		
	    while((str = bufferedReader2.readLine()) != null) {
	    	querylist.add(str);
	    }
	    
	    return querylist; 
	}
	
	/**
	 * parse the inputing string, and add starting and ending locations as nodes
	 * @param query
	 * @param sGraph
	 * @param nodeValue
	 * @return
	 * @throws IOException
	 */
	public String parseQuery(String query,Map<String,Map<String,Edge>> sGraph, Map<String,Vertex> nodeValue)
	throws IOException{	
        	
			tmpA = query.split(";");
        	if(tmpA.length!=2)
        		return "" ;
        	
        	start = tmpA[0].trim();
        	sNum="";
        	int i=0;
        		while(start.charAt(i)>='0' && start.charAt(i)<='9'){
        			sNum=sNum+start.charAt(i);
        			i++;
        		}
        	startNum = Double.parseDouble(sNum);
        	starttmp =start.substring(i);
        	
        	sNum="";
        	i=0;
        	end = tmpA[1].trim();
        		while(end.charAt(i)>='0' && end.charAt(i)<='9'){
        			sNum=sNum+end.charAt(i);
        			i++;
        		}
        	endNum = Double.parseDouble(sNum);
        	endtmp = end.substring(i);
        	
        	// operate a copied graph
        	
        	nodeValue.put(starttmp, new Vertex(starttmp));
        	nodeValue.put(endtmp, new Vertex(endtmp));
        	
        	sGraph.put(starttmp, new HashMap<String,Edge>());
        	sGraph.put(endtmp, new HashMap<String,Edge>());
        	
        	tmpA=edgeV.get(starttmp).split(";");
        	iv = edgeL.get(starttmp).doubleValue()/edgeN.get(starttmp).doubleValue();
        	iv=(2*(Math.ceil(startNum/2))*iv)-iv;
        	jv =edgeL.get(starttmp).doubleValue() - iv;
        	sGraph.get(starttmp).put(tmpA[0],new Edge(starttmp,iv));
        	sGraph.get(starttmp).put(tmpA[1], new Edge(starttmp,jv));
        	sGraph.get(tmpA[0]).put(starttmp,new Edge(starttmp,iv));
        	sGraph.get(tmpA[1]).put(starttmp, new Edge(starttmp,jv));
        	String flagS1 = tmpA[0];
        	String flagS2 = tmpA[1];
        	
        	iv=edgeL.get(endtmp).doubleValue()/edgeN.get(endtmp).doubleValue();
        	iv=(2*(Math.ceil(endNum/2))*iv) -iv;
        	jv =edgeL.get(endtmp).doubleValue() - iv;
        	tmpA=edgeV.get(endtmp).split(";");
        	sGraph.get(endtmp).put(tmpA[0], new Edge(endtmp,iv));
        	sGraph.get(endtmp).put(tmpA[1], new Edge(endtmp,jv));
        	sGraph.get(tmpA[0]).put(endtmp,new Edge(endtmp,iv));
        	sGraph.get(tmpA[1]).put(endtmp, new Edge(endtmp,jv));
        	
        	return starttmp+";"+endtmp;
        }
	    
	/**
	 * close the input stream
	 * @throws IOException
	 */
	public void readInputClose() throws IOException {
		
		 queryInput.close();
		 bufferedReader2.close();
	}
	
	/**
	 * output the result appending to the result file
	 * @param url
	 * @param result
	 * @throws IOException
	 */
	public void output(String url,String result) throws IOException {
		FileOutputStream resultOutput = new FileOutputStream(url,true);
		
		resultOutput.write(result.getBytes());
		
		resultOutput.close();
	}
	
	/**
	 * removing the starting & ending nodes
	 * @param s
	 * @param nodeValue
	 * @param start
	 * @param end
	 */
	public void removeNode(Map<String,Map<String,Edge>> s, Map<String,Vertex> nodeValue,  String start, String end){
		
		s.remove(start);
		s.remove(end);
		s.get(start).remove(end);
		s.get(end).remove(end);
		s.get(start).remove(start);
		s.get(end).remove(start);
		
		nodeValue.remove(start);
		nodeValue.remove(end);
		
		//return true;
	}
	
	
	

}
