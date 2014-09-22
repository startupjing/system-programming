
import java.util.concurrent.*;
/**
 * This class handles lc command
 * @author jinglu
 *
 */

public class LcCmd extends Filter{
    public int countingLine;
    /**
     * constructor
     * @param in
     * @param out
     * @param cmdInfo
     * @param myDirectory
     */
    public LcCmd(BlockingQueue<String> in, BlockingQueue<String> out, String cmdInfo, String myDirectory){
    	super(in,out,cmdInfo,myDirectory);
    	this.countingLine = 0;
    }
    
    /**
     * increment countingLine after executing transform() method every time
     * @param o string element in the queue
     * @return null
     */
    protected String transform(String o){
    	this.countingLine += 1;
    	return null;
    }
    
    /**
     * put counting result to output queue
     */
    protected void manageWork(){
    	try{
    		//convert int to string
    		String count = Integer.toString(countingLine);
    		out.put(count);
    	}catch(InterruptedException e){
    		System.out.println("Counting interrupted...");
    	}
    	if(out == null){
    		return;
    	}
    }
    

}
