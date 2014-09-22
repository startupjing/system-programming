
import java.util.concurrent.BlockingQueue;
import java.util.*;

/**
 * This class handles grep command
 * @author jinglu
 *
 */
public class GrepCmd extends Filter{
	public GrepCmd(BlockingQueue<String> in, BlockingQueue<String> out, String cmdInfor, String myDirectory){
		super(in,out,cmdInfor,myDirectory);
	}
	
	/**
	 * search the contend for lines that contain the searchString
	 * @param o string element in the queue
	 * @return o the line that contains searchString
	 */
	protected String transform(String o){
		String[] grepSplit = cmdInfo.split(" ");
		String searchString = grepSplit[grepSplit.length-1];
		if(o.contains(searchString)){
			return o;
		}else{
			return null;
		}
	}
	
	/**
	 * no need for grep command
	 */
	protected void manageWork(){
		
	}

}
