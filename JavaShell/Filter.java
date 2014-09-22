
import java.util.concurrent.*;
import java.util.*;

/***
 * This abstract class provides blueprint for each command class
 * transform() and manageWork() method need to be override by its child class
 * The Filter class represents a command which runs in a thread, reads input from pipein queue and output to pipeout.
 * @author jinglu
 *
 */
public abstract class Filter implements Runnable{
    protected BlockingQueue<String> in;
    protected BlockingQueue<String> out;
    protected volatile boolean done;
    protected String cmdInfo;
    protected String myDirectory;
    
    /**
     * constructor
     * @param in BlockingQueue input
     * @param out BlockingQueue output
     * @param cmdInfo information of each command
     * @param myDirectory current directory
     */
    public Filter(BlockingQueue<String> in, BlockingQueue<String> out, String cmdInfo, String myDirectory){
    	this.in = in;
    	this.out = out;
    	this.done = false;
    	this.cmdInfo = cmdInfo;
    	this.myDirectory = myDirectory;
    }
    
    /**
     * transform() needed to be override by child class
     * 
     * NOTE:I change the Object transform(Object o) to String transform(String o)
     * because all messages produced and consumed are strings.
     * 
     * @param o String element in the queue
     * @return processed string element
     */
    protected abstract String transform(String o);
    
    
    /**
     * manageWork() needed to be override by child class
     */
    protected abstract void manageWork();
    
    
    /**
     * implement run() method from Thread class
     */
    public void run() {
        	String o = null;
        	//process needed to be completed by grep and lc command
        	if(in != null){ 
        		//keep in loop if not done
        	    while(!this.done){
        	       //take string element from input queue
        	       try{
        	           o = in.take();
        	       }catch(InterruptedException e){
        	           System.out.println("Taking element interrupted...");
        	       }
        	       
        	       //test if input queue reaches the end
        	       //set done=true to end the loop
        	       if(o.equals("endHere")){
        	           this.done = true;
        	           break;
        	       }
        	       //process the string element
        	       o = transform(o);
        	       
        	       //continue if no actual transform and empty output
        	       if(o == null || out == null){
        	    	   continue;
        	       }
        	       //forward processed string element into output queue
        	       try{
        	           out.put(o);      	   
        	       }catch(InterruptedException e){
        	           System.out.println("Putting element interrupted...");
        	      }
        	   }
        	}
        	//method needed to be implemented by each command class
        	try{
        	    manageWork();
        	}catch(NullPointerException e){
        		System.out.println("NullPointerException occurrs...");
        	}
        	
        	//if empty output queue, skip the following step
        	if(out == null){
            	return;
            }
        	//put a sign indicating the end

            try{
            	out.put("endHere");
            }catch(InterruptedException e){
            	System.out.println("Putting end sign interrupted...");
            }
        	
        } 
  
}
