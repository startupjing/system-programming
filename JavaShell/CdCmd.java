

/**
 * This class handles cd command
 * @author jinglu
 *
 */
public class CdCmd {
    public String currentDir;
    public String targetDir;
    /**
     * constructor
     * @param currentDir
     * @param cmd
     */
    public CdCmd(String currentDir, String cmd){
    	this.currentDir = currentDir;
    	String[] list = cmd.split(" ");
    	try{
    	    this.targetDir = list[1];
    	}catch(ArrayIndexOutOfBoundsException e){
    		System.out.println("index out of bounds...");
    	}
    }
    
    /**
     * change the current directory to place that user enters
     * @return current directory
     */
    public String moveDir(){
    	try{
    	    if(targetDir.equals("..")){
    		    this.currentDir = currentDir.substring(0,currentDir.lastIndexOf("/"));
    	    }else if(targetDir.equals(".") || targetDir.equals("")){
    		    this.currentDir += "";
    	    }else {
    		    this.currentDir = this.currentDir + "/" + targetDir;
    	    }
    	}catch(NullPointerException e){
    		System.out.println("Stay in the current working directory..");
    	}
    	return this.currentDir;
    }
}
