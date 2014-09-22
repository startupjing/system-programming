
import java.util.concurrent.*;
/**
 * This class handles pwd command
 * @author jinglu
 *
 */
public class PwdCmd extends Filter{
	/**
	 * constructor
	 * @param out
	 * @param cmdInfo
	 * @param myDirectory
	 */
    public PwdCmd(BlockingQueue<String> out, String cmdInfo, String myDirectory){
    	super(null,out,cmdInfo,myDirectory);
    }
    
    /**
     * no need for pwd command
     */
    protected String transform(String o){
    	return null;
    }
    
    /**
     * put current directory info to output queue
     */
    protected void manageWork(){
    	try{
    		out.put(this.myDirectory);
    		if(out == null){
    			return;
    		}
    		out.put("endHere");
    	}catch(InterruptedException e){
    		System.out.println("Managing output interrupted...");
    	}
    }
}
