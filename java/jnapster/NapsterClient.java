/*
 * NapsterClient.java
 *
 * jNapster Library
 * By Harikrishnan Varma <varma@teil.soft.net>
 * Copyright 1999 Harikrishnan Varma
 *
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package jnapster;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;
import jnapster.logging.*;
import gnu.getopt.*;
import gnu.getopt.LongOpt;
import gnu.getopt.Getopt;
import gnu.*;
import javax.swing.*;
import util.miJList;
/**
 * NapsterClient.java
 *
 * Implements a command line Napster Client
 *
 * Created: Mon Dec 13 09:57:47 1999
 *
 * @author  Harikrishnan Varma
 * @version 0.1
 */
public class NapsterClient extends Thread {
    LineNumberReader in;
    String           cmdLine;

    public String user;
    public String password;
    public String server;
    public int    port = 6666;
    public boolean terminado=true;

    NapsterSession napsterSession;
    NapsterProfile napsterProfile;

    public Vector  songs;
    Vector  transfers;
    boolean debug = false;
    miJList sout;

    /**
     * Constructs a Napster Client object
     */
    public NapsterClient() throws IOException {
     	in = new LineNumberReader(new InputStreamReader(System.in));
    }/* end of NapsterClient() */
    public NapsterClient(String cmdLine) throws IOException
    {
       this();
       this.cmdLine=cmdLine;
    }
    public void run() {
       terminado=false;
  	    sout.addItem("Logging in to " + server + ":" + port);
  	    try {
        		napsterSession.login(server, port);
  	    } catch (NapsterException e) {
        		sout.addItem("Could not login " + e);
       }

       while (true) {
          handle(cmdLine);
          cmdLine=null;
          if (cmdLine.toUpperCase()=="QUIT") {
             break;
          }
          while (cmdLine==null) {
             try {
                this.sleep(1000);// a dormir 1 segundo
             } catch (Exception ex) {}   
          }
       }
       terminado=true;
    }

    /**
     * Prints out the version of the Napster Client
     */
    public void version() {
	System.out.println("jNapster Client v0.1");
    } /* end of version() */

    /**
     * Prints out the commands available for Napster Client
     */
    public void help() {
       System.out.println("Recognized commands:");
       System.out.println("help          - what you see now");
       System.out.println("debug         - toggle debug state");
       System.out.println("quit          - Exit the client");
       System.out.println("exit          - Exit the client");
       System.out.println("find <song>   - Find specified song");
       System.out.println("browse <user> - List songs in user's library");
       System.out.println("jobs          - List song transfers");
       System.out.println("list          - List retrieved song list");
       System.out.println("get <number>  - Download <number>th song from list");
    } /* end of help() */


    /**
     * If debug is true, log messages to screen, else suppress debug
     * messages
     */
    public void debug() {
	if (debug) {
	    LogManager.instance().register(ScreenLogger.register());
	} else {
	    LogManager.instance().register(NullLogger.register());
	} /* end of if-else */	
    } /* end of debug() */
    

    /**
     * Prompt user for username and password
     */
    public void login() {	
       System.out.print("Enter User: ");
       try {
           user = in.readLine();
           System.out.print("Enter Password: ");
           password = in.readLine();
       } catch (IOException e) {
           System.out.println("Could not get user input");
           System.exit(-1);
       } /* end of try-catch */
    } /* end of login() */

    /**
     * Start the main command line loop for taking input from user
     * and handling the command
     */
    public boolean comienzo(miJList sout) {
       String mensaje="";

       this.sout=sout;
       System.out.println("jNapster Client v0.1");
       System.out.println("Copyright (c), 1999 Harikrishnan Varma");
       System.out.println();
      	debug();
       if(user == null) {
           System.out.print("Enter User: ");

           try {
              user = in.readLine();
           } catch (IOException e) {
              sout.addItem("Could not read user input");
              return false;
           } /* end of try-catch */
       } /* end of if */

       if(password == null) {
           System.out.print("Enter Password: ");

           try {
              password = in.readLine();
           } catch (IOException e) {
              sout.addItem("Could not read user input");
              return false;
           } /* end of try-catch */
       } /* end of if */
       napsterProfile = new NapsterProfile(user, password);
       napsterSession = new NapsterSession(napsterProfile);
       if (server == null) {
          sout.addItem(mensaje+="Logging in to optimal server (this may take a while)");
          try {
             napsterSession.login();
          } catch (NapsterException e) {
             sout.addItem(mensaje+="Could not login " + e);
             return false;
          } /* end of try-catch */
      } else {
          return false;
      } /* end of if-else */
     	sout.addItem("Login successful");
      return true;
    } /* end of start() */

    /**
     * Handle the specified command
     */
     public void setCmdLine(String cmdLine) {
        this.cmdLine=cmdLine;
     }
    public void handle(String cmdLine) {
      	cmdLine = cmdLine.trim();
      	StringTokenizer stringTokenizer = new StringTokenizer(cmdLine, " ");
      	int count = stringTokenizer.countTokens();

       if (count == 0) {
           return;
       } /* end of if */

       String cmd = stringTokenizer.nextToken();
       String param = null;

       if (cmd.equalsIgnoreCase("LIST")) {
           if (songs == null || songs.size() == 0) {
              System.out.println("No songs in the list");
              return;
           } /* end of if */
           for (int i = 0; i < songs.size(); i++) {
              NapsterSong napsterSong = (NapsterSong) songs.elementAt(i);
              System.out.println("[" + i + "]" + napsterSong);
           } /* end of for */
           return;
       } else if (cmd.equalsIgnoreCase("BROWSE")) {
           if (count == 1) {
             System.out.println("Missing parameter");
             return;
           } /* end of if */
           param = stringTokenizer.nextToken();
           param = param.trim();
           try {
              songs = napsterSession.getSongList(param);
           } catch (NapsterException e) {
              System.out.println("Error retrieving song list");
              return;
           } /* end of try-catch */
           if (songs == null) {
              System.out.println(param + " has empty song list");
              return;
           } /* end of if */
           for (int i = 0; i < songs.size(); i++) {
              NapsterSong napsterSong = (NapsterSong) songs.elementAt(i);
              System.out.println("[" + i + "]" + napsterSong);
           } /* end of for */
      	    return;
       } else if (cmd.equalsIgnoreCase("DEBUG")) {
           debug = !debug;
           debug();
           System.out.println("Debugging " + ((debug)?"ON":"OFF"));
           return;
       } else if (cmd.equalsIgnoreCase("QUIT")
           || cmd.equalsIgnoreCase("EXIT")) {
           System.out.println("Logging out");
           try {
              napsterSession.logout();
           } catch (NapsterException e) {
              System.out.println("Could not log out");
           } /* end of try-catch */
           return;
       } else if (cmd.equalsIgnoreCase("HELP")) {
           help();
           return;
       } else if(cmd.equalsIgnoreCase("JOBS")) {
           transfers = napsterSession.getTransfers();
           if (transfers == null || transfers.size() == 0) {
              System.out.println("No jobs active");
              return;
           } /* end of if */
           for (int i = 0; i < transfers.size(); i++) {
              NapsterTransfer napsterTransfer =
                  (NapsterTransfer) transfers.elementAt(i);

              System.out.println("[" + i + "]" + napsterTransfer);
           } /* end of for */
           return;
       } else if (cmd.equalsIgnoreCase("GET")) {
           if (count == 1) {
              System.out.println("Missing parameter");
              return;
           } /* end of if */
           param = stringTokenizer.nextToken();
           if (songs == null || songs.size() == 0) {
              System.out.println("No songs listed");
              return;
           } /* end of if */
           int index = 0;
           try {
              index = Integer.parseInt(param);
           } catch (NumberFormatException e) {
              System.out.println("Invalid song number, choose 0.."
             + (songs.size() - 1));
              return;
           } /* end of try-catch */
           if (index < 0 || index > (songs.size() - 1)) {
              System.out.println("Invalid song number, choose 0.."
             + (songs.size() - 1));
              return;
           } /* end of if */
           napsterSession.downloadSong((NapsterSong) songs.elementAt(index));
           return;
       } else if (cmd.equalsIgnoreCase("FIND")) {
           if (count == 1) {
              System.out.println("Missing parameter");
              return;
           } /* end of if */
           param = stringTokenizer.nextToken();
           param = param.trim();

          /*
           * Handle song names double quoted and with spaces in the middle
           */
	          String song = null;
           if (param.startsWith("\"")) {
              /*Remove the leading double quote */
              param = param.substring(1, param.length());
              song = param;
              while (stringTokenizer.hasMoreTokens()) {
                  param = stringTokenizer.nextToken();
                  /*
                   * We need only the string within quotes
                   * Stop when we get the ending double quote
                   */
                  param = param.trim();
                  song += " " + param;
                  if (param.endsWith("\"")) {
                     /* Remove the trailing double quote */
                     song = song.substring(0, song.length() - 1);
                     break;
                  } /* end of if */
              } /* end of while */
           } else {
             song = param;
           }/* end of if-else */
           /* If the user encloses a single word within double
              quotes, handle it here*/
           if (song.endsWith("\"")) {
              song = song.substring(0, song.length() - 1);
           } /* end of if */
           try {
              songs = napsterSession.findSongs(song);
           } catch (NapsterException e) {
              LogManager.instance().info("No Songs found");
              return;
           } /* end of try - catch */
           for (int i = 0; i < songs.size(); i++) {
              NapsterSong napsterSong = (NapsterSong) songs.elementAt(i);
              System.out.println("[" + i + "]" + napsterSong);
           } /* end of for */
       } else {
           System.out.println("Unrecognized command");
       }/* end of if-else */

    } /* end of handle() */


    /**
     * Print the usage options
     */
    public void usage() {
       System.out.println("Usage: " + getClass().getName() + " <options>");
       System.out.println("Options:");
       System.out.println("--help              print this message");
       System.out.println("--version           print the version of " + getClass().getName());
       System.out.println("--debug             print debug messages");
       System.out.println("--server=SERVER     specify SERVER for login");
       System.out.println("--port=PORT         specify PORT for login");
       System.out.println("--user=USER         specify user name for login");
       System.out.println("--password=PASSWORD specify password for user");
       System.out.println();
    } /* end of usage() */

    /**
     * Process the command line arguments
     */
    public void processCmdLine(String[] cmdLine) {
       LongOpt[] longOpts = new LongOpt[6];
       longOpts[0] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h');
       longOpts[1] = new LongOpt("debug", LongOpt.NO_ARGUMENT, null, 'd');
       longOpts[2] = new LongOpt("server", LongOpt.REQUIRED_ARGUMENT, null, 's');
       longOpts[3] = new LongOpt("user", LongOpt.REQUIRED_ARGUMENT, null, 'u');
       longOpts[4] = new LongOpt("password", LongOpt.REQUIRED_ARGUMENT, null, 'p');
       longOpts[4] = new LongOpt("port", LongOpt.REQUIRED_ARGUMENT, null, 'c');
       longOpts[5] = new LongOpt("version", LongOpt.NO_ARGUMENT, null, 'v');
       Getopt g = new Getopt(getClass().getName(), cmdLine,
               "c:s:u:p:d::h::v::", longOpts);
       String arg;
       int c;
       while ((c = g.getopt()) != -1) {
           switch (c) {
           case 'h':
              usage();
              System.exit(0);
           case 'v':
              version();
              System.exit(0);
           case 's':
              server = g.getOptarg();
              break;
           case 'c':
              try {
                  port = Integer.parseInt(g.getOptarg());
              } catch (NumberFormatException e) {
                  port = 6666;
              } /* end of try-catch */
              break;
           case 'u':
              user = g.getOptarg();
              break;
           case 'p':
              password = g.getOptarg();
              break;
           case 'd':
              debug = true;
              break;
           case '?':
              usage();
              System.exit(0);
              break;
           } /* end of switch */
       } /* end of while */

    } /* end of processCmdLine() */

    /**
     * Entry point
     */
    public static void main(String[] args) {

       jnapster.NapsterClient napsterClient = null;

       try {
           napsterClient = new NapsterClient();
       } catch (IOException e) {
           System.out.println("ERROR: Could not instantiate input reader");
           System.exit(0);
       }
       if (args.length == 0) {
          napsterClient.login();
       } else {
           napsterClient.processCmdLine(args);
       }
       if(!napsterClient.comienzo(new miJList())) {
           System.out.println("Could not login");
           System.exit(0);
       }
    }
}
