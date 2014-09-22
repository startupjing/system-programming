
import java.util.*;
import java.util.concurrent.*;

/**
 * This class handles history command
 * @author jinglu
 *
 */
public class HistoryCmd extends Filter{
    public ArrayList<String> cmdHist;
    /**
     * constructor
     * @param out
     * @param cmdInfo
     * @param myDirectory
     * @param cmdHist
     */
    public HistoryCmd(BlockingQueue out, String cmdInfo, String myDirectory, ArrayList<String> cmdHist){
    	super(null,out,cmdInfo,myDirectory);
    	this.cmdHist = cmdHist;
    }
   
    /**
     * no need for history command
     */
    protected String transform(String o){
    	return null;
    }
    
    /**
     * put element of cmdHist list into output queue
     */
    protected void manageWork(){
    	for(int i=0; i<cmdHist.size(); i++){
    		//get the ith command in the history
    		String histLine ="" + (i+1) + " ";
    		histLine += cmdHist.get(i);
    		try{
    			out.put(histLine);
    		}catch(InterruptedException e){
    			System.out.println("Storing history interrrupted...");
    		}	   		
    	}
    }

}
