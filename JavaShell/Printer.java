
import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.*;


/* * This class creates a printer and print output of each command
 * either to terminal or output to a file
 * @author jinglu
 *
 */
public class Printer extends Filter{
    public String targetOutput = null;
    /**
     * constructor
     * @param content
     * @param cmdReceive
     * @param myDirectory
     */
    public Printer(BlockingQueue<String> content, String cmdReceive, String myDirectory){
    	super(content,null,cmdReceive,myDirectory);
    	if(cmdReceive != ""){
    		targetOutput = cmdReceive;
    	}

    }
	
     
   /**
    * print string element to console or write it to a file
    * @param o string element
    * @return null
    */
    protected String transform(String o){
    	if(targetOutput == null){
    		System.out.println(o);
    	//the following allows the user to specify a file to output
    	}else {
    		//use FileWriter to write text to the user-specified file
    		try{
    			FileWriter writer = new FileWriter(this.myDirectory+"/"+targetOutput,true);
    			writer.write(o+"\n");
    			writer.close();
    		}catch(IOException e){
    			System.out.println("Writing file wrong...");
    		}		
    	}
    	return null;
    }
    
    /**
     * no need for printer
     */
    protected void manageWork(){
    
    }
    
}
