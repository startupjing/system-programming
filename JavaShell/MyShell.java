
import java.util.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.Scanner;
import java.io.*;

/**
 * This main class creates a simple command line environment interacting with file system.
 * Thread, inter-thread communication, and producer-consumer idea are used in the implementation.
 * @author jinglu
 *
 */
public class MyShell {
	/**
	 * myDirectory stores the current directory that user is in
	 * cmdHistory records user command history
	 * outputHist stores previous output from command
	 */
	static String myDirectory = null;
    static ArrayList<String> cmdHistory = new ArrayList<String>();

    /**
     * The main method reads user command and creates corresponding thread
     * @param args
     */
    public static void main(String[] args){
    	//create a empty file in current working directory, find current directory and delete the file
    	File getDirFile = new File("useless");
    	myDirectory = getDirFile.getAbsolutePath();
    	myDirectory = myDirectory.substring(0,myDirectory.lastIndexOf("/"));
        myDirectory += "/src/javaShell";
    	getDirFile.delete();
    	
    	//print instruction for the program
    	printInfo();
    	
    	//read-eval-print loop starts
    	while(true){ 
    		//print the prompt
    		System.out.print(myDirectory+">>");
    		
    		//initialize blockingqueue in and out
    	    BlockingQueue<String> pipeout = null;
    	    BlockingQueue<String> pipein = null;
    	    
    	    //scanLine reads user command line
    		Scanner scanLine = new Scanner(System.in);
    		String readLine = "";
    		readLine = scanLine.nextLine();
    		cmdHistory.add(readLine);
    		
    		//if user enter !n command, get the command number from cmdHistory
    		//then replace the readLine with the nth command from history
    		if(readLine.startsWith("!")){
    		   int cmdNo = Integer.parseInt(readLine.substring(1,2));
    		   readLine = cmdHistory.get(cmdNo-1);
    		}
    		
    		//split the command line by ">"
    		String[] arrowSplit = readLine.split(">");
    		String cmdSend = "";
    		String cmdReceive = "";
    		
    		//decide if the output is needed to display to a file
    		//or print on the terminal
    		//cmdReceive stores the output file name if needed
    		if(arrowSplit.length > 1){
    			cmdSend += arrowSplit[0];
    			cmdReceive += arrowSplit[1];
    		}else {
    			cmdSend += arrowSplit[0];
    		}
    		
    		//split command(s) into several commands by "|"
    		String[] cmds = cmdSend.split("\\| ");
    		
    		//traverse all the command in the list cmds
    		for(int i=0; i<cmds.length; i++){
    			//split commands by " " to get command name
    			String[] oneCmd = cmds[i].split(" ");
    			String cmdName = oneCmd[0];
    			String cmdInput = "";
    			//store command parameters if exist
    			if(oneCmd.length>1){
    				cmdInput += oneCmd[1];
    			}
    			//exit/quit command
    			if(cmdName.equals("exit") || cmdName.equals("quit")){ 
    				System.out.println("Quiting command line program....");
    				System.exit(0);
    			}else if((cmdName.equals("cat"))){	
					pipeout = new LinkedBlockingQueue<String>();
    				CatCmd runCat = new CatCmd(pipeout,cmds[i],myDirectory);
    				Thread tCat = new Thread(runCat);
    				tCat.start();
    			//grep command
    			}else if(cmdName.equals("grep")){
    				pipein = pipeout;
    				pipeout = new LinkedBlockingQueue<String>();
    				GrepCmd runGrep = new GrepCmd(pipein,pipeout,cmds[i],myDirectory);
    				Thread tGrep = new Thread(runGrep);
    				tGrep.start();
    			//cd command
    			}else if(cmdName.equals("cd")){
    				CdCmd runCd = new CdCmd(myDirectory,cmds[i]);
    				myDirectory = runCd.moveDir();
    			//pwd command
    			}else if(cmdName.equals("pwd")){
    				pipeout = new LinkedBlockingQueue<String>();
    				PwdCmd runPwd = new PwdCmd(pipeout,cmds[i],myDirectory);
    				Thread tPwd = new Thread(runPwd);
    				tPwd.start();
    			//ls command
    			}else if(cmdName.equals("ls")){
    				pipeout = new LinkedBlockingQueue<String>();
    				LsCmd runLs = new LsCmd(pipeout,cmds[i],myDirectory);
    				Thread tLs = new Thread(runLs);
    				tLs.start();
    			//lc command
    			}else if(cmdName.equals("lc")){
    				pipein = pipeout;
    				pipeout = new LinkedBlockingQueue<String>();
    				LcCmd runLc = new LcCmd(pipein,pipeout,cmds[i],myDirectory);
    				Thread tLc = new Thread(runLc);
    				tLc.start();
    			//history command
    			}else if(cmdName.equals("history")){
    				pipeout = new LinkedBlockingQueue<String>();
    				HistoryCmd runHistory = new HistoryCmd(pipeout,cmds[i],myDirectory,cmdHistory);
    				Thread tHistory = new Thread(runHistory);
    				tHistory.start();
    			//invalid command situation, print error message
    			}else{
    				System.out.println("Command not recognized, please enter valid command...");
    			}
    		}
    		
    	    //create a printer to print result of each command
    		//output result to a file if needed
    	    Printer printer = new Printer(pipeout,cmdReceive,myDirectory);
    	    Thread tPrinter = new Thread(printer);
    	    tPrinter.start();
            try{
        	    tPrinter.join();
            }catch(InterruptedException e){
        	    System.out.println("Printer interrupted...");
            }
    	}
    }
    
    
    /**
     * print the instructions of this command line program
     */
    public static void printInfo(){
    	System.out.println("Welcome to Command Line!");
    	System.out.println("The following commands are available in this environment: ");
    	System.out.println("*cat file1 file2 ...*: output the contents of file.");
    	System.out.println("*grep searchString*: display contents containing the string.");
    	System.out.println("*lc*: display the number of lines of piped input.");
    	System.out.println("*history*: display the history of user commands.");
    	System.out.println("*!n*: execute the nth command in the history.");
    	System.out.println("*pwd*: display current working directory.");
    	System.out.println("*ls*: list all the files in current working directory.");
    	System.out.println("*cd*: change to another directory.");
    	System.out.println("*exit or quit*.");
    	System.out.println("Start you command here...");
    }
  
}

    	
    		
    		
	
    			
    			
    			
    			
    			
    			
	