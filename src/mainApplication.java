import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class mainApplication
{
	/*-------------CONSTANT STRING VALUES FOR THE FRONT INTERFACE------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    public static final String firstTitle =
    		"          _   _                                             ______           \r\n" +
    		"         | | | |                                            | ___ \\          \r\n" +
    		"         | | | | __ _ _ __   ___ ___  _   ___   _____ _ __  | |_/ /_   _ ___ \r\n" +
    		"         | | | |/ _` | '_ \\ / __/ _ \\| | | \\ \\ / / _ \\ '__| | ___ \\ | | / __|\r\n" +
    		"         \\ \\_/ / (_| | | | | (_| (_) | |_| |\\ V /  __/ |    | |_/ / |_| \\__ \\\r\n" +
    		"          \\___/ \\__,_|_| |_|\\___\\___/ \\__,_| \\_/ \\___|_|    \\____/ \\__,_|___/";

    public static final String secondTitle = 
    		"	        ___  ___                                                  _   \r\n" +
    		"	        |  \\/  |                                                 | |  \r\n" +
    		"	        | .  . | __ _ _ __   __ _  __ _  ___ _ __ ___   ___ _ __ | |_ \r\n" +
    		"	        | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '_ ` _ \\ / _ \\ '_ \\| __|\r\n" +
    		"	        | |  | | (_| | | | | (_| | (_| |  __/ | | | | |  __/ | | | |_ \r\n" +
    		"	        \\_|  |_/\\__,_|_| |_|\\__,_|\\__, |\\___|_| |_| |_|\\___|_| |_|\\__|\r\n" +
    		"	                                   __/ |                              \r\n" +
    		"	                                  |___/                               " +
    		"	                                                              \r\n";
    public static final String queryTable =
            "+-------+----------------------------------------------------------------------------+\n" +
            "| Query |                                  Action                                    |\n" +
            "+-------+----------------------------------------------------------------------------+\n" +
            "|   1   | Receive a list of stops between 2 bus stops alongside the associated cost. |\n" +
            "+-------+----------------------------------------------------------------------------+\n" +
            "|   2   | Search for a bus stop by it's full name or by the first few characters.    |\n" +
            "+-------+----------------------------------------------------------------------------+\n" +
            "|   3   | Search for all trips with a given arrival time sorted by Trip ID.          |\n" +
            "+-------+----------------------------------------------------------------------------+\n" +
            "|   4   | Exit the program.                                                          |\n" +
            "+-------+----------------------------------------------------------------------------+";

    /*-------------VANCOUVER BUS MANAGEMENT SYSTEM APPLICATION---------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    public static void main(String[] args) throws IOException
    {
        //First, we print out the the main title with a description which is shown upon startup of the application.
        System.out.println(String.join("", Collections.nCopies(86,"*")));
        System.out.println(firstTitle);
        System.out.println(secondTitle);
        System.out.println(String.join("", Collections.nCopies(86,"*")));
        System.out.println(String.join("", Collections.nCopies(13, " "))
                + "This system provides 4 unique user queries at your disposal.");
        System.out.println(String.join("", Collections.nCopies(12, " "))
                + "Simply enter any query number from the table of queries below.");

        //Now, we can take user input from the user with a scanner.
        Scanner scanner = new Scanner(System.in);
        boolean runApp = true;
        //After query 1 is requested once, we store the BusStopMap object to speed up future requests for it.
        BusStopMap stopMap = null;
        boolean query1RunPrev = false;
        //After query 3 is requested once, we store the Map with stopTime objects to speed up future requests for it.
        Map<String, List<stopTime>> stopTimes = null;
        boolean query3RunPrev = false;

        //Main application runtime loop.
        while(runApp) 
        {
            //Print out the query table at the start, and also after every time query 1-3 is completed.
            System.out.println(queryTable);
            System.out.print("\nEnter your query: ");
            String userInput = scanner.next();
            
            //We can now react to the user query response.
            switch (userInput)
            {
               /**
                * @feature: Part 1 - Find the shortest paths between 2 bus stops entered by the user.
                * @return: The list of stops en route as well as the associated "cost".
                */
                case "1":
                    boolean query1Running = true;
                    //We only want to generate a BusStopMap object once.
                    if(!query1RunPrev)
                    {
                    	//We initialize our BusStopMap object only once, as this query is requested for the first time.
                        stopMap = new BusStopMap("input/stops.txt", "input/stop_times.txt", "input/transfers.txt");
                        query1RunPrev = true;
                    }
                    stopName searchTree = new stopName("input/stops.txt");
                    while(query1Running)
                    {   
                    	//Receive the two necessary inputs
                        System.out.print("\nPlease enter the name of the first stop: ");
                        String query = scanner.next();
                        query += scanner.nextLine();
                        ArrayList<String> results = searchTree.queryNameWithReturn(query);
                        if(results != null) // Check that there was a match
                        {
                            String stopOne = getStop(scanner, results);
                            System.out.print("Please enter the name of the second stop: ");
                            query = scanner.next();
                            query += scanner.nextLine();
                            results = searchTree.queryNameWithReturn(query);
                            //Check that there was a match.
                            if(results != null)
                            {
                                String stopTwo = getStop(scanner, results);
                                try
                                {
                                    //Calculate shortest paths and cost.
                                    stopMap.makePaths(stopOne);
                                    Double cost = stopMap.getCost(stopTwo);
                                    //Check that a path exists.
                                    if(cost != null)
                                    {
                                        stopMap.getStops(stopTwo, cost);
                                    }
                                    else
                                    {   
                                    	//No path found.
                                        System.out.println("No route exists between these two stops");
                                    }
                                }
                                catch(IllegalArgumentException e)
                                {   
                                	//Error handling.
                                    System.out.println("Error: Names not found in BusStopMap");
                                }
                            }
                            else
                            {   
                            	//Error handling.
                                System.out.println("No stops match your search");
                            }
                        }
                        else
                        {   //Error handling.
                            System.out.println("No stops match your search");
                        }
                        query1Running = yesNo(scanner, "bus route");
                    }
                break;
                
               /**
                * @feature: Part 2 - Let the user search for a bus stop by full name or by the first few characters in the name using a ternary search tree (TST).
                * @return: The full stop information for each stop matching the search criteria.
                */
                case "2":
                    boolean runUserQuery2 = true;
                    while (runUserQuery2)
                    {   //Receive the user input.
                        System.out.print("Please enter the name of the bus stop you would like to search for: ");
                        String searchQuery = scanner.next();
                        searchQuery += scanner.nextLine();
                        //Make a TST and calculate output.
                        stopName q2TST = new stopName("input/stops.txt");
                        int returnValue = q2TST.ourTST.get(searchQuery);
                        if (returnValue >= 0)
                        {
                        	//We have a dedicated function to print out the stop information for each stop matching the search criteria.
                        	stopName.printStopNamesMatchingCriteria(q2TST);
                        }
                        else
                        {
                            System.out.println("No search result found, please try again");
                        }
                        runUserQuery2 = yesNo(scanner, "bus stop");
                    }
                break;
                
               /**
                * @feature: Part 3 - Let the user search for all trips with a given arrival time.
                * @return: Full details of all trips matching the criteria sorted by Trip ID.
                */
                case "3":
                    boolean runUserQuery3 = true;
                    //We only want to generate our Map once.
                    if (!query3RunPrev)
                    {
                        //We generate our Map with our dedicated function to generate a hash map of stopTime objects.
                        stopTimes = stopTime.generateHashMapOfStopTimes("input/stop_times.txt");
                        query3RunPrev = true;
                    }
                    while (runUserQuery3)
                    {
                        System.out.print("Input an arrival time in the format 'hh:mm:ss': ");
                        String userArrivalTimeInput = scanner.next();
                        userArrivalTimeInput = userArrivalTimeInput.trim();
                        //We check if the time given by the user is valid with our dedicated function to verify this.
                        if (validTimeFormat(userArrivalTimeInput))
                        {
                            //We have a dedicated function which prints out all the trips with the given arrival time.
                            stopTime.findListOfTripsWithGivenArrivalTime(userArrivalTimeInput, stopTimes);
                            runUserQuery3 = yesNo(scanner, "arrival time");
                        }
                        else
                        {
                            //Error handling to cover edge cases where the user provides an invalid time format.
                            System.out.println("Please enter a valid time.");
                        }
                    }
                break;
                
               /**
                * @feature: Exit the application.
                * @return: Final farewell message before the application exits.
                */
                case "4":
                    System.out.println("\nThank you for using the Vancouver Bus Management System ☺");
                    runApp = false;
                break;
                
                default:
                    //If any other input is given, that means the user has entered an invalid response.
                    System.out.println("Please enter a valid query number.\n");
                break;
            }
        }
        //We are finished taking user input, so we can close the scanner.
        scanner.close();
    }
    
    /*-------------MAIN APPLICATION HELPER METHODS---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    /**
     * Verifies if a given string is in a valid time format for part 3 of the project.
     * @param: A string representing the input we want to verify.
     * @return: A boolean representing whether the input is in a valid time format or not.
     */
    static private boolean validTimeFormat(String s)
    {
        //The input must be exactly 8 characters long
        if (s == null || s.length() != 8)
        {
            return false;
        }
        //Validate time format
        if (s.charAt(2) == ':' && s.charAt(5) == ':')
        {
            String hh = s.substring(0, 2);
            String mm = s.substring(3, 5);
            String ss = s.substring(6, 8);
            int hours, minutes, seconds;
            try
            {
                hours = Integer.parseInt(hh);
                minutes = Integer.parseInt(mm);
                seconds = Integer.parseInt(ss);
                if (hours > -1 && hours < 24 &&
                    minutes > -1 && minutes < 60 &&
                    seconds > -1 && seconds < 60)
                {
                    return true;
                }
            }
            //If an exception occurs when parsing the the strings as integers, the input is not in a valid time format.
            catch (NumberFormatException nfe)
            {
                return false;
            }
        }
        return false;
    }
    
    /**
     * translates user input number into the corresponding stop as displayed in query 1
     * @param scanner The scanner reading the input
     * @param results The displayed search results
     * @return The stop corresponding to the numerical input read by the scanner
     */
    private static String getStop(Scanner scanner, ArrayList<String> results)
    {
        String stop = null;
        if(results.size() > 1)
        {
            System.out.println("Please Choose 1 of the following: ");
            for(int i = 0; i < results.size(); i++)
            {
                System.out.println("" + (i + 1) + ". " + results.get(i));
            }
            boolean firstStopGiven = false;
            while(!firstStopGiven)
            {
                System.out.print("Type in the number of the stop you want to choose: ");
                String s = scanner.next();
                s += scanner.nextLine();
                if(s.matches("[0-9]*")) //If input is some integer
                {
                    int reply = Integer.parseInt(s);
                    if(reply - 1 >= 0 && reply - 1 < results.size())
                    {
                        stop = results.get(reply - 1);
                        firstStopGiven = true;
                    }
                    else
                    {
                        System.out.println("Invalid Input: Please choose use one of the numbers found beside the stops listed above");
                    }
                }
                else
                {
                    System.out.println("Invalid Input: Please use numbers only");
                }
            }
        }
        return stop;
    }
    
    /**
     * Asks the user if they would like to repeat their query
     * @param scanner The scanner currently in use to read user input
     * @param subject The subject of the query the user is looking up
     * @return boolean reflecting user answer
     */
    private static boolean yesNo(Scanner scanner, String subject)
    {
        boolean userQuery = true;
        boolean exitQuery = true;
        while (exitQuery)
        {
            System.out.print("Do you want to search for another " + subject + "? [Y/N]: ");
            String theReply = scanner.next();
            theReply += scanner.nextLine();
            if (theReply.equalsIgnoreCase("N"))
            {
                exitQuery = false;
                userQuery = false;
            }
            else if (theReply.equalsIgnoreCase("Y"))
            {
                exitQuery = false;
            }
            else
            {   //Error handling
                System.out.println("Invalid Input: Please enter \"Y\" if yes or \"N\" if no");
            }
        }
        return userQuery;
    }
}
