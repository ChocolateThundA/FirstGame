
package betracer;
import java.util.*; import java.io.*;

/**
 * A quick little game of chance.
 * @author Programmer Patty
 */
public class BetRacer {

    /**
     * declaring variables and what not.
     * Ask player name - search text file for name
     *  if name there - load money and statistics
     *  else write to file the name
     */
    public static void main(String[] args) throws FileNotFoundException, InterruptedException, IOException {
        //Declare variables
        int lengthOfTrack;
        int degreeOfRace;
        int racerBet;
        int playerCount = 0;
        String name, divide = "---------------------", command;
        boolean playerExist;
        double bet, minimumBet;
        double[] racerProgs;
        
        //declare Scanner
        Scanner input = new Scanner(System.in);
        
        //declare files
        File playerData = new File("playerData.txt");
        
        //delare player
        Player player = new Player();
        Player player2 = new Player();
        
        ////MAIN CODE///-----------------------------------///MAIN CODE////
        System.out.println("-----Betting Racer-----");
        System.out.println("--game developed by: \nProgrammer Patty\nCreated: 3/15/2019");
        System.out.println("-------------------------\n");
        //first segement of the main is used to establish player data. Code will take in a username and check to see if
        //the player exists. If so, it will load all that data. If not it will start a new data line.
        //UPDATE: now we ask to see if two people would like to play!
        System.out.print("One[1] or Two[2] players: "); int firstInt = (int)input.nextDouble();
        boolean moveOn = false;
        if(firstInt != 1 && firstInt != 2){
            do{
                System.out.print("Try again: ");
                firstInt = (int)input.nextDouble();
                if(firstInt == 1 || firstInt == 2){
                    moveOn = true;
                }
            } while (moveOn == false);
        }
        //intialize only one player. Check if they exist
        if(firstInt == 1){
            System.out.print("What is your name? (only one word) : "); name = input.next();
             playerExist = loadFile(name, playerData, player);
             //if they don't exist create them and put them on the save file
            if (playerExist == false){
                newcomer(player, name, playerData);
                System.out.println("Let's look at your stats for the first time!\n" + divide);
            } else {
                System.out.println("Here's your stats!\n" + player.toString() + divide);
            }
            playerCount = 1;
        //intialize both players and check if they exist.    
        } else if (firstInt == 2){
            System.out.print("Player 1 name: "); name = input.next();
            playerExist = loadFile(name, playerData, player);
            if (playerExist == false){
                newcomer(player, name, playerData);
            }
            System.out.print("Player 2 name: "); name = input.next();
            playerExist = loadFile(name, playerData, player2);
            if (playerExist == false){
                newcomer(player2, name, playerData);
            }
            System.out.println("Here are your stats!: \n");
            System.out.println(player.toString() + "\n" + divide + "\n" + player2.toString());
            playerCount = 2;
        }

        //now that all the player data is set we can start the game...
         //intializing menu.. user can see some options here and make choice
        System.out.println("What would you like to do! Command list below...");
        System.out.println("'/stats' : see your stats\n'/race' : bet on a race\n'/quit' : leave the game\n'/help' : gives a list of the commands\n'/scores' : take a look at the top local players\n---");
        System.out.print("Command: ");command = input.next();
        System.out.println(divide);
        do{   
            //big structure to test the input.
            //first... check stats. pretty easy
            if(command.compareTo("/stats") == 0){
                System.out.println(player.toString());
                //if player 2 exists print their stats too!
                if(playerCount == 2){
                    System.out.println(divide + "\n" + player2.toString());
                }
                System.out.println(divide);
                
            } else if (command.compareTo("/race") == 0){
                //this is the big part. The whole game if you will.
                degreeOfRace = degreeGenerate();
                switch(degreeOfRace){
                    case 0:
                        minimumBet = 1.0;
                        break;
                    case 1:
                        minimumBet = 100.00;
                        break;
                    case 2:
                        minimumBet = 250.00;
                        break;
                    case 3:
                        minimumBet = 500.00;
                        break;
                    default:
                        minimumBet = 1.0;
                        break;
                }
                System.out.println("Up next is a degree " + degreeOfRace + " race.");
                //add in ability to choose if you want to do that race//
                if (minimumBet <= player.getMoney()){
                    System.out.print("Do you wish to participate in this race. Minimum payment will be: " + minimumBet + "\n[1]yes [any other number]no: ");
                    int reply = (int)input.nextDouble();
                    if (reply == 1){
                        boolean cont = false;
                        do{
                            System.out.println("Minimum buy in for degree " + degreeOfRace + ": " + minimumBet);
                            System.out.print("What is your monetary bet?: "); bet = input.nextDouble();
                            if (bet < minimumBet || bet > player.getMoney()){
                                System.out.println("Your bet is too low or you don't have enough.");
                            } else if (bet >= minimumBet) {
                                cont = true;
                                player.alterMoney(-bet);
                            }
                        } while (cont == false);
                        System.out.print("Bet on a car...\nEnter a number 1-6: "); racerBet = (int)input.nextDouble();
                        player.setBet(racerBet);
                        cont = false;
                        do{
                            if (player.getBet() < 1 || player.getBet() > 6){
                                System.out.print("Did you read? Try a number between 1 and 6: ");
                                player.setBet((int)input.nextDouble());
                            } else {
                                cont = true;
                            }
                        } while (cont == false);
                        System.out.println(divide);
                        lengthOfTrack = makeTrack(degreeOfRace);
                        racerProgs = race(lengthOfTrack, player);
                        //method calcs first second and third!
                        results(racerProgs, player.getBet(), player, degreeOfRace, bet);
                    
                        System.out.println(divide);
                    } else {
                        System.out.println("Oh. well please come again soon!\n" + divide);
                    }
                } else {
                    System.out.println("Sorry... you don't have enough for the race today.\n" + divide );
                }    
            } else if (command.compareTo("/help") == 0){
                System.out.println(":::COMMAND LIST:::");
                System.out.println("type /stats to bring up your profile stats\ntype /race to race\ntype /quit to leave the game\ntype /help to see this again!");
                System.out.println(divide);
            } else if(command.compareTo("/quit") == 0) {
                break;
            } else if(command.compareTo("/scores") == 0) {
                leaderBoard(playerData, player);
                System.out.println(divide);
            } else {
                System.out.println("Did not enter a valid command. Try '/help' to see a list of valid commands.");
            }
            if (player.getMoney() < 1.0){
                System.out.println("You lose!");
                break;
            }
            System.out.print("Command: "); command = input.next();
            
        } while (command.compareTo("/quit") != 0);
        //game saves then checks to see if they have money. if not it deletes the save
        saveGame(playerData, player);
        
        if(player.getMoney() < 1){
            String line = player.getName() + " " + player.getMoney() + " "  + player.getWins() + " " + player.getLosses();
            deleteSave(line, playerData);
            System.out.println("Since you lost... your save file has been deleted. Better luck nest time!");
        }
        System.out.println(divide);
        System.out.println("Thanks for playing!\nIf you have suggestions or inquiries email me at BetRacerGame@gmail.com");
        
        
    }
    //METHODS//
    
    //method used to produc the track length for the cars
    public static int makeTrack(int degree){
        Random rn = new Random();
        int length;
        switch(degree){
            case 0:
                length = rn.nextInt((28 - 10) + 1) + 10;
                break;
            case 1:
                length = rn.nextInt((48 - 28) + 1) + 28;
                break;
            case 2:
                length = rn.nextInt((68 - 48) + 1) + 48;
                break;
            case 3:
                length = rn.nextInt((88 - 68) + 1) + 68;
                break;
            default:
                length = 0;
                break;
        }
        return length;
    }
    
    //Method used to produce the degree of the race
    //helps with minimum buy in and track length
    public static int degreeGenerate(){
        Random rn = new Random();
        int num = rn.nextInt(4);
        return num;
    }
    
    //Method used to do the race
    //should calculate a winner, simulate the race
    @SuppressWarnings("empty-statement")
    public static double[] race(int length, Player player) throws InterruptedException{
        
        //making the racers here this way they change everytime
        Car racer1 = new Car("Racer 1");
        Car racer2 = new Car("Racer 2"); 
        Car racer3 = new Car("Racer 3"); 
        Car racer4 = new Car("Racer 4"); 
        Car racer5 = new Car("Racer 5"); 
        Car racer6 = new Car("Racer 6"); 
        
        //making a scanner for user input
        Scanner input = new Scanner(System.in);
        //declare a boolean so the race continues to go
        boolean winner = false;
        //declare an int to keep track of cycles and a way to keep track of winner
        int turns = 0;
        //declaring a string to print out later
        String racerProgress;
        //declare an arraylist that stores the progress doubles of the finishers
        double[] finisherProgs = new double[6];
        
        //going to add a way to observe the drivers at a cost
        System.out.print("Would you like to observe 3 drivers?. Cost is 25 for all three\nType 'yes' or 'no'-- Answer: ");
        String answer = input.next();
        Car racer;
        int count = 1;
        if(answer.compareTo("yes") == 0){
            player.alterMoney(-25);
            int num;
            do{
                System.out.print("Which car do you want to look at[1-6]: "); num = (int)input.nextDouble();
                if(num < 1 || num > 6){
                    boolean cont = false;
                    do{
                        System.out.print("Sorry, try number between 1 and 6: "); num = (int)input.nextDouble();
                        if (num > 1 && num < 6){
                            cont = true;
                        }
                    } while (cont == false);
                }
                //getting the racer;
                switch(num){
                    case 1:
                        racer = racer1;
                        break;
                    case 2:
                        racer = racer2;
                        break;
                    case 3:
                        racer = racer3;
                        break;
                    case 4:
                        racer = racer4;
                        break;
                    case 5:
                        racer = racer5;
                        break;
                    case 6:
                        racer = racer6;
                        break;
                    default:
                        racer = racer1; //shouldn't ever happen
                }
                //then a method will execute that observes the racer
                observeCar(racer);
                count += 1;
            }while(count <= 3);
            //finally ask if the player would like to change their bet
            boolean cont = false;
            do {
                System.out.print("Would you like to change your bet?\n[1]yes [0]no: "); num = (int)input.nextDouble();
                if(num == 1){
                    System.out.print("To which car?: ");
                    player.setBet((int)input.nextDouble());
                    System.out.println("-------------");
                    cont = true;
                } else if(num == 0){
                    System.out.println("Goodluck!!");
                    System.out.println("--------------");
                    cont = true;
                } else {
                    System.out.println("Try a number 1 or 0");
                }
            } while (cont == false);
        }
        System.out.println("::Let the race begin!::");
        do{
            turns += 1;
            //----------------------------racer1-------------------------------//
            if(racer1.getHP() == true){
              racerUpdate(racer1, length);
              if(racer1.getIsWinner() == true){
                  winner = true;
              }
            }
            //---------------------------racer2-------------------------------//
            if(racer2.getHP() == true){
                racerUpdate(racer2, length);
                if(racer2.getIsWinner() == true){
                    winner = true;
                }
            } 
           //---------------------------racer3-------------------------------//
           if(racer3.getHP() == true){
                racerUpdate(racer3, length);
                if(racer3.getIsWinner() == true){
                    winner = true;
                }
            }
           //---------------------------racer4-------------------------------//
           if(racer4.getHP() == true){
                racerUpdate(racer4, length);
                if(racer4.getIsWinner() == true){
                    winner = true;
                }   
           }
           //---------------------------racer5-------------------------------//
           if(racer5.getHP() == true){
                racerUpdate(racer5, length);
                if(racer5.getIsWinner() == true){
                    winner = true;
                }    
           }
          //---------------------------racer6-------------------------------//
          if(racer6.getHP() == true){
                racerUpdate(racer6, length);
                if(racer6.getIsWinner() == true){
                    winner = true;
                }   
           }
          
          //next segement of the method will display the racers progress through the race
          racerProgress = String.format("::Race Stats(turn %d)::\nRacer 1: %-5.2f Racer 2: %-5.2f Racer 3: %-5.2f\nRacer 4: %-5.2f Racer 5: %-5.2f Racer 6: %-5.2f", turns, racer1.getProg(), racer2.getProg(), racer3.getProg(), racer4.getProg(), racer5.getProg(),racer6.getProg());
          System.out.println(racerProgress + "\n");
          
          //this little bit here will  allow the user to read the data and then the code will continue
          if (turns <= 20){
            Thread.sleep(2000);
          } else {
            Thread.sleep(500);
          }
          //check to see if all racers have stopped
          if(racer1.getHP() == false && racer2.getHP() == false && racer3.getHP() == false && racer4.getHP() == false && racer5.getHP() == false && racer6.getHP() == false){
              winner = true;
          }
   
          //code should continue until race finishes
        } while (winner == false);
        finisherProgs[0] = racer1.getProg(); finisherProgs[1] = racer2.getProg(); finisherProgs[2] = racer3.getProg();
        finisherProgs[3] = racer4.getProg(); finisherProgs[4] = racer5.getProg(); finisherProgs[5] = racer6.getProg();
        
      return finisherProgs;  
    }
    
    //method used to calculate the finishing postions of the racers, calc you money 
    public static void results(double[] results, int bet, Player player, int degree, double moneyBet){
        //first segement calcs first
        double winnings;
        double firstProg = results[0];
        int first;
        for (int i = 0; i < results.length; i++) {
            if(results[i] == firstProg && i+1 == bet){
                firstProg = results[i];
            } else if(results[i] > firstProg){
                firstProg = results[i];
            }
        }
        first = indexOf(results, firstProg) + 1;
        results[first - 1] = 0.0;
        //next we calculate second
        double secondProg = 0.0;
        int second;
        for (int i = 0; i < results.length; i++) {
            if(results[i] == secondProg && i+1 == bet){
                secondProg = results[i];
            } else if(results[i] > secondProg){
                secondProg = results[i];
            }
        }
        second = indexOf(results, secondProg) + 1;
        results[second - 1] = 0.0;
        //next we calc third
        double thirdProg = 0.0;
        int third;
         for (int i = 0; i < results.length; i++) {
            if(results[i] == thirdProg && i+1 == bet){
                thirdProg = results[i];
            } else if(results[i] > thirdProg){
                thirdProg = results[i];
            }
        }
        third = indexOf(results, thirdProg) + 1;
        results[third - 1] = 0.0;
        
        //next we calculate player winnings or better yet losings
        if(bet == first){
            winnings = calcFirst(degree, moneyBet);
            player.alterMoney(winnings);
            player.addWins();
            System.out.println("FIRST PLACE! You won: " + winnings);
        } else if (bet == second){
            winnings = calcSecond(degree, moneyBet);
            player.addLoss();
            player.alterMoney(winnings);
            System.out.println("You got second. You won: " + winnings);
        } else if (bet == third){
            winnings = calcThird(degree, moneyBet);
            player.alterMoney(winnings);
            player.addLoss();
            System.out.println("You got third. You won: " + winnings);
        }else if (results[player.getBet()] > 1.0){
            winnings = calcFinish(degree);
            player.alterMoney(winnings);
            player.addLoss();
            System.out.println("You lost, however your car finished the race! You got back: " + winnings);
        } else {
            player.addLoss();
            System.out.println("You lost: " + moneyBet);
        }
    }
    
    //method used to check if you have played before under the name
    public static boolean loadFile(String name, File playerdata, Player player) throws FileNotFoundException{
        Scanner reader = new Scanner(playerdata);
        boolean exists = false;
        
        while (reader.hasNext() && exists == false){
            ArrayList data = new ArrayList();
            data.add(reader.next()); data.add(reader.nextDouble()); data.add(reader.nextInt()); data.add(reader.nextInt());
            if (data.get(0).equals(name) == true){
                player.setName(name);
                player.setMoney((double) data.get(1));
                player.setWins((int) data.get(2));
                player.setLoss((int) data.get(3));
                exists = true;
            }
            reader.nextLine();   
        }
        reader.close();
        return exists;
    }
    
    //quick method to get the index of an item in an array
    public static int indexOf(double[] array, double object){
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == object){
                index = i;
            }
        }
        return index;
    }
    
    //method used to save your game data if you still have money when you quit
    public static void saveGame(File playerdata, Player player) throws FileNotFoundException{
        ArrayList<String> incomingData = new ArrayList();
        Scanner dataFile = new Scanner(playerdata);
        while(dataFile.hasNext()){
            String sentence = dataFile.nextLine();
            incomingData.add(sentence);
        }
        dataFile.close();
        PrintWriter output = new PrintWriter(playerdata);
        for (int i = 0; i < incomingData.size(); i++) {
            String[] lineArray = incomingData.get(i).split(" ");
            if(lineArray[0].equals(player.getName()) == true){
                output.println(player.getName() + " " + player.getMoney() + " " + player.getWins() + " " + player.getLosses());
            } else {
                output.println(incomingData.get(i));
            }
        }
        output.close();
        System.out.println("::Game Saved::");
    }
    
    //this next method will be used to determine if you have money left. If not it will delete your save file.
    //code taken from stack overflow on how to delete a line from a text file.
    public static void deleteSave(String lineToRemove, File playerdata) throws FileNotFoundException, IOException{
        File tempPlayerData = new File("tempFile.txt");
        
        BufferedReader reader = new BufferedReader(new FileReader(playerdata));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempPlayerData));
        String currentLine;
        
        while((currentLine = reader.readLine()) != null){
            //trim new line when comparing lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
            writer.write(currentLine + "\n");
        }
        writer.close();
        reader.close();
        playerdata.delete();
        boolean successful = tempPlayerData.renameTo(playerdata);
    }
    public static double calcFirst(int degree, double moneyBet){
        double winnings;
        switch(degree){
            case 0:
                winnings = moneyBet * 2;
                break;
            case 1:
                winnings = moneyBet * 3;
                break;
            case 2:
                winnings = moneyBet * 4;
                break;
            case 3:
                winnings = moneyBet * 6;
                break;
            default:
                winnings = 0.0;
                break;
        }
        return winnings;
    }
    public static double calcSecond(int degree, double moneyBet){
        double winnings;
        switch(degree){
            case 0:
                winnings = moneyBet;
                break;
            case 1:
                winnings = moneyBet / 2;
                break;
            case 2:
                winnings = moneyBet / 2;
                break;
            case 3:
                winnings = moneyBet / 2;
                break;
            default:
                winnings = 0.0;
                break;
        }
        return winnings;
    }
    public static double calcThird(int degree, double moneyBet){
        double winnings;
        switch(degree){
            case 0:
                winnings = moneyBet /2;
                break;
            case 1:
                winnings = moneyBet / 3;
                break;
            case 2:
                winnings = moneyBet / 3;
                break;
            case 3:
                winnings = moneyBet / 4;
                break;
            default:
                winnings = 0.0;
                break;
        }
        return winnings;
    }
    public static double calcFinish(int degree){
        double winnings;
        switch(degree){
            case 0:
                winnings = 20;
                break;
            case 1:
                winnings = 40;
                break;
            case 2:
                winnings = 80;
                break;
            case 3:
                winnings = 160;
                break;
            default:
                winnings = 0.0;
                break;
        }
        return winnings;
    }
    //method used to display leaderboard
    public static void leaderBoard(File playerData, Player player)throws FileNotFoundException{
        Scanner fileReader = new Scanner(playerData);
        Set<Double> scores = new HashSet();
        ArrayList<String> dataLines = new ArrayList();
        
        //gather all money totals from players
        while (fileReader.hasNext()){
            fileReader.next();
            scores.add(fileReader.nextDouble());
            fileReader.nextLine();
        }
        fileReader.close();
        //put the doubles in order from greatest to least
        List<Double> sortedScores = new ArrayList(scores);
        Collections.sort(sortedScores);
        Collections.reverse(sortedScores);
        
        //store all the lines
        Scanner fileReader2 = new Scanner(playerData);
        while (fileReader2.hasNext()){
            dataLines.add(fileReader2.nextLine());
        }
        fileReader2.close();
        
        //compare top ten scores to the lines in string Array list and print out those people.
        System.out.println("--::Top 10 leaderboard::--");
        if (sortedScores.size() >= 10){
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < dataLines.size(); j++) {
                    String[] lineArray = dataLines.get(j).split(" ");
                    if (lineArray[1].compareTo(Double.toString(sortedScores.get(i))) == 0) {
                        System.out.printf("%2d: %10s -- %10s\n",i + 1 ,lineArray[0], lineArray[1]);
                    }
                }
            }
        } else if (sortedScores.size() < 10){
            for (int i = 0; i < sortedScores.size(); i++) {
                for (int j = 0; j < dataLines.size(); j++) {
                    String[] lineArray = dataLines.get(j).split(" ");
                    if (lineArray[1].compareTo(Double.toString(sortedScores.get(i))) == 0) {
                        System.out.printf("%2d: %10s -- %10s\n",i + 1 ,lineArray[0], lineArray[1]);
                    }
                }
            }
        }
        System.out.println("NOTE: Leaderboard updates after game is exited.\nYour current score is: " + player.getMoney());
    }
    //method used to observe a certain car
    public static void observeCar(Car racer){
        //check all the car features
        String speedCheck = checkSpeed(racer.getSpeed());
        String accelCheck = checkAccel(racer.getAccel());
        String moveCheck = checkMoves(racer.getMoves());
        //print out the data
        System.out.println(racer.getName() + "---\n" + speedCheck + "\n" + accelCheck + "\n" + moveCheck + "\n------");
        
    }
    //used in observeCar
    public static String checkSpeed(int speed){
        String statement;
        if (speed >= 1 && speed <=5){
            statement = "-doesnt't look all to fast.";
        } else if (speed > 5 && speed <= 10){
            statement = "--looks somewhat fast."; 
        } else if (speed > 10 && speed <= 15){
            statement = "---looks very fast.";
        } else {
            statement = "uhhh... an error occured?";
        }
        return statement;
    }
    //used in observeCar
    public static String checkAccel(int accel){
        String statement;
        if (accel >= 1 && accel <= 3){
            statement = "-will be very slow off the line.";
        } else if(accel > 3 && accel <=6){
            statement = "--has reasonable speed off the line.";
        } else if (accel > 6 && accel <= 10){
            statement = "---is very quick off the line.";
        } else {
            statement = "uhhh... an error occured?";
        }
        return statement;
    }
    //used in observeCar
    public static String checkMoves(int moves){
        String statement;
        if(moves >= 30 && moves < 50){
            statement = "-looks pretty busted. Might not last long.";
        } else if (moves >= 50 && moves < 70){
            statement = "--looks decent. Should last pretty long.";
        } else if (moves >= 70 && moves <= 90){
            statement = "---looks very intact. Won't break down for awhile.";
        } else {
             statement = "uhhh... an error occured?";
        }
        return statement;
    }
    
    //used to clean up the race method. Moves the cars, changes HP when needed and calcualtes RacerProg
    public static void racerUpdate(Car racer, int length){
       //first part calculates how far they move; cannot exceed max speed
       racer.addRate(racer.getAccel());
       if (racer.getRate() > racer.getSpeed()){
            racer.setRate(racer.getSpeed());
        }
        //second part checks to see if they finished the race, also calculates what percent they have left
        racer.moveDist(racer.getRate());
        racer.calcProg(racer.getDist(), length);
        if (racer.getProg() >= 1.0){
            racer.setWinner();
        }
        //third part checks to see if driver is out of gas(moves)
        racer.adjustMoves(racer.getRate());
        if (racer.getMoves() <= 0){
           racer.setHP(false);
        }
    }
    //used for when a new user enters. Writes the end of the text file. 
    public static void newcomer(Player player, String name, File playerData) throws IOException{
        player.setName(name);
            System.out.println("Ahh.. a newcomer. Welcome " + name + "!");
            
            //code here taken from journalDev.com over how to add to the end of a file
            FileWriter fr = new FileWriter(playerData, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter output = new PrintWriter(br);
            output.println(player.getName() + " " + player.getMoney() + " " + player.getWins() + " " + player.getLosses());
            output.close();
            br.close();
            fr.close();
    }
}    
