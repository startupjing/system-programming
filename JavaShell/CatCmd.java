
import java.util.concurrent.*;

import java.io.*;
import java.util.*;

/**
 * This class handles the cat command
 * @author jinglu
 *
 */
public class CatCmd extends Filter{

    public CatCmd(BlockingQueue out, String cmdInfo, String myDirectory){
    	super(null,out,cmdInfo,myDirectory);
    }
    
    /**
     * no need for cat command
     * @return null
     */
    protected String transform(String o){
    	return null;
    }
    
    /**
     * scan the file and put scanned line into output queue
     */
    protected void manageWork(){
    	//get filename(s)
    	String[] fileName = this.cmdInfo.split(" ");
    	for(int i=1; i<fileName.length; i++){
    		//find the file and create scanner for the file
    		File f = new File(this.myDirectory+"/"+fileName[i]);
    		Scanner scanFile = null;
    		try{
    			scanFile = new Scanner(f);
    		}catch(FileNotFoundException e){
    			System.out.println("File not found...");
    		}
    		//scan the file if possible and put the line into output queue
    		while(scanFile.hasNextLine()){
    			try{
    			    this.out.put(scanFile.nextLine());
    			}catch(InterruptedException e){
    				System.out.println("Storing interrupted...");
    			}
    		}
    	}	
    }

}
     
    


