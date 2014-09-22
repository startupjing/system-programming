
import java.io.*;
import java.util.concurrent.*;
/**
 * This class handles ls command
 * @author jinglu
 *
 */
public class LsCmd extends Filter{
	/**
	 * constructor
	 * @param out
	 * @param cmdInfo
	 * @param myDirectory
	 */
    public LsCmd(BlockingQueue<String> out, String cmdInfo, String myDirectory){
    	super(null,out,cmdInfo,myDirectory);
    }
    
    /**
     * no need for ls command
     */
    protected String transform(String o){
    	return null;
    }
    
    /**
     * put file names in current directory to output queue
     */
    protected void manageWork(){
    	try{ 
    		 //list all the files in current directory
    	     File[] files = new File(this.myDirectory).listFiles();
    	     if(files.length != 0){
    	        for(int i=0; i<files.length; i++){
    		        if(files[i].isFile()){
    		        	//get file name
    			        String fileName = files[i].getName();
    			        try{
    				        out.put(fileName);
    			        }catch(InterruptedException e){
    				        System.out.println("Listing files interrupted...");
    			        }
    		        }
    	       }
    	    }else{
    		    return;
    	    }
       }catch(NullPointerException e){
    	    //print message if directory is empty
        	System.out.println("No file in this directory...");
       }
    }

}
