
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
        int winner, racerBet;
        String name, divide = "---------------------", command;
        boolean playerExist;
        double bet, minimumBet;
        
        //declare Scanner
        Scanner input = new Scanner(System.in);
        
        //declare files
        File playerData = new File("C:\\BetRacer\\playerData.txt");
        
        //delare player
        Player player = new Player();
        
        
        //TEST CODE//
        /*degreeOfRace = degreeGenerate();
        lengthOfTrack = makeTrack(degreeOfRace);
        winner = race(lengthOfTrack);
        System.out.println("The winner is...\nRacer " + winner);*/
        
        ////MAIN CODE///-----------------------------------///MAIN CODE////
        System.out.println("-----Betting Racer-----");
        System.out.println("--game developed by: \nProgrammer Patty");
        System.out.println("-------------------------\n");
        //first segement of the main is used to establish player data. Code will take in a username and check to see if
        //the player exists. If so, it will load all that data. If not it will start a new data line.
        System.out.print("What is your name? (only one word) : ");
        name = input.next();
        
        //this code is for creating a new user in case the one entered is not found in the file.
        playerExist = loadFile(name, playerData, player);
        if (playerExist == false){
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
            
            //new user has been added. Let's show them their data...
            System.out.println("Since you're new here, you should see your data!");
            System.out.println(divide);
        } else {
            System.out.println("Welcome back " + player.getName() + "!\nHere are your current stats!\n" + divide);
        }
        System.out.println(player.toString());
        //now that all the player data is set we can start the game...
         //intializing menu.. user can see some options here and make choice
        System.out.println("What would you like to do! Command list below...");
        System.out.println("'/stats' : see your stats\n'/race' : bet on a race\n'/quit' : leave the game\n'/help' : gives a list of the commands\n---");
        System.out.print("Command: ");command = input.next();
        System.out.println(divide);
        do{   
            //big structure to test the input.
            //first... check stats. pretty easy
            if(command.compareTo("/stats") == 0){
                System.out.println(player.toString());
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
                if (minimumBet <= player.getMoney()){
                    boolean cont = false;
                    do{
                        System.out.println("Minimum buy in for degree " + degreeOfRace + ": " + minimumBet);
                        System.out.print("What is your monetary bet?: "); bet = input.nextDouble();
                        if (bet < minimumBet || bet > player.getMoney()){
                            System.out.println("Your bet is too low or you don't have enough.\nMinimum bet must equal to or over: " + minimumBet);
                        } else if (bet >= minimumBet) {
                            cont = true;
                            player.alterMoney(-bet);
                        }
                    } while (cont == false);
                    System.out.print("Bet on a car...\nEnter a number 1-6: "); racerBet = (int)input.nextDouble();
                    cont = false;
                    do{
                        if (racerBet < 1 || racerBet > 6){
                            System.out.print("Did you read? Try a number between 1 and 6: ");
                            racerBet = (int)input.nextDouble();
                        } else {
                            cont = true;
                        }
                    } while (cont == false);
                    System.out.println(divide + "\n Let the race begin!!");
                    lengthOfTrack = makeTrack(degreeOfRace);
                    winner = race(lengthOfTrack);
                    System.out.println("Racer " + winner + " is the winner!");
                    //next will be checking to see if you picked the correct car
                    double winnings = 0;
                    if(winner == racerBet){
                        switch(degreeOfRace){
                            case 0:
                                player.alterMoney(2 * bet);
                                winnings = 2 * bet;
                                break;
                            case 1:
                                player.alterMoney(3 * bet);
                                winnings = 3 * bet;
                                break;
                            case 2:
                                player.alterMoney(4 * bet);
                                winnings = 4 * bet;
                                break;
                            case 3:
                                player.alterMoney(5 * bet);
                                winnings = 5 * bet;
                                break;
                        }
                        player.addWins();
                        System.out.println("You won " + winnings);
                    } else{
                        System.out.println("You lost " + bet);
                        player.addLoss();
                    }
                    System.out.println(divide);
                } else {
                    System.out.println("Sorry... you don't have enough for the race today.\n" + divide );
                }
                    
            } else if (command.compareTo("/help") == 0){
                System.out.println(":::COMMAND LIST:::");
                System.out.println("type /stats to bring up your profile stats\ntype /race to race\ntype /quit to leave the game\ntype /help to see this again!");
                System.out.println(divide);
            } else {
                System.out.println("Maybe try '/help' to see the commands. Try one of those!");
                System.out.println(divide);
            }
            if (player.getMoney() < 1.0){
                command = "/quit";
                System.out.println("You lose!");
                break;
            }
            System.out.print("Command: "); command = input.next();
            
        } while (command.compareTo("/quit") != 0);
        System.out.println(divide);
        System.out.println("Thanks for playing!\nIf you have suggestions or inquiries email me at BetRacer@gmail.com");
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
    public static int race(int length) throws InterruptedException{
        //making the racers here this way they change everytime
        Car racer1 = new Car("Racer 1"); boolean racer1HP = true; int racer1SPD = 0;
        Car racer2 = new Car("Racer 2"); boolean racer2HP = true; int racer2SPD = 0;
        Car racer3 = new Car("Racer 3"); boolean racer3HP = true; int racer3SPD = 0;
        Car racer4 = new Car("Racer 4"); boolean racer4HP = true; int racer4SPD = 0;
        Car racer5 = new Car("Racer 5"); boolean racer5HP = true; int racer5SPD = 0;
        Car racer6 = new Car("Racer 6"); boolean racer6HP = true; int racer6SPD = 0;
        
        //declare a boolean so the race continues to go
        boolean winner = false;
        //declare an int to keep track of cycles and a way to keep track of winner
        int turns = 0, winnerNum = 0;
        //declaring a string to print out later
        String racerProgress;
        //declare an arraylist that stores the progress doubles of the finishers
        double[] finishers = new double[6];
        
        
        do{
            turns += 1;
            //----------------------------racer1-------------------------------//
            if(racer1HP == true){
                //first part calculates how far they move; cannot exceed max speed
                racer1SPD += racer1.getAccel();
                if (racer1SPD > racer1.getSpeed()){
                    racer1SPD = racer1.getSpeed();
                }
                //second part checks to see if they finished the race, also calculates what percent they have left
                racer1.moveDist(racer1SPD);
                racer1.calcProg(racer1.getDist(), length);
                if (racer1.getDist() >= length){
                    winner = true;
                    finishers[0] = racer1.getProg();
                    
                }
                //third part checks to see if driver is out of gas(moves)
                racer1.adjustMoves(racer1SPD);
                if (racer1.getMoves() <= 0){
                    racer1HP = false;
                }   
            }
            //---------------------------racer2-------------------------------//
            if(racer2HP == true){
                //first part calculates how far they move; cannot exceed max speed
                racer2SPD += racer2.getAccel();
                if (racer2SPD > racer2.getSpeed()){
                    racer2SPD = racer2.getSpeed();
                }
                //second part checks to see if they finished the race, also calculates what percent they have left
                racer2.moveDist(racer2SPD);
                racer2.calcProg(racer2.getDist(), length);
                if (racer2.getDist() >= length){
                    winner = true;
                    finishers[1] = racer2.getProg();
                }
                //third part checks to see if driver is out of gas(moves)
                racer2.adjustMoves(racer2SPD);
                if (racer2.getMoves() <= 0){
                    racer2HP = false;
                }    
            } 
           //---------------------------racer3-------------------------------//
           if(racer3HP == true){
                //first part calculates how far they move; cannot exceed max speed
                racer3SPD += racer3.getAccel();
                if (racer3SPD > racer3.getSpeed()){
                    racer3SPD = racer3.getSpeed();
                }
                //second part checks to see if they finished the race, also calculates what percent they have left
                racer3.moveDist(racer3SPD);
                racer3.calcProg(racer3.getDist(), length);
                if (racer3.getDist() >= length){
                    winner = true;
                    finishers[2] = racer3.getProg();
                }
                //third part checks to see if driver is out of gas(moves)
                racer3.adjustMoves(racer3SPD);
                if (racer3.getMoves() <= 0){
                    racer3HP = false;
                }    
            }
           //---------------------------racer4-------------------------------//
           if(racer4HP == true){
                //first part calculates how far they move; cannot exceed max speed
                racer4SPD += racer4.getAccel();
                if (racer4SPD > racer4.getSpeed()){
                    racer4SPD = racer4.getSpeed();
                }
                //second part checks to see if they finished the race, also calculates what percent they have left
                racer4.moveDist(racer4SPD);
                racer4.calcProg(racer4.getDist(), length);
                if (racer4.getDist() >= length){
                    winner = true;
                    finishers[3] = racer4.getProg();
                }
                //third part checks to see if driver is out of gas(moves)
                racer4.adjustMoves(racer4SPD);
                if (racer4.getMoves() <= 0){
                    racer4HP = false;
                }    
           }
           //---------------------------racer5-------------------------------//
           if(racer5HP == true){
                //first part calculates how far they move; cannot exceed max speed
                racer5SPD += racer5.getAccel();
                if (racer5SPD > racer5.getSpeed()){
                    racer5SPD = racer5.getSpeed();
                }
                //second part checks to see if they finished the race, also calculates what percent they have left
                racer5.moveDist(racer5SPD);
                racer5.calcProg(racer5.getDist(), length);
                if (racer5.getDist() >= length){
                    winner = true;
                    finishers[4] = racer5.getProg();
                }
                //third part checks to see if driver is out of gas(moves)
                racer5.adjustMoves(racer5SPD);
                if (racer5.getMoves() <= 0){
                    racer5HP = false;
                }    
           }
          //---------------------------racer6-------------------------------//
          if(racer6HP == true){
                //first part calculates how far they move; cannot exceed max speed
                racer6SPD += racer6.getAccel();
                if (racer6SPD > racer6.getSpeed()){
                    racer6SPD = racer6.getSpeed();
                }
                //second part checks to see if they finished the race, also calculates what percent they have left
                racer6.moveDist(racer6SPD);
                racer6.calcProg(racer6.getDist(), length);
                if (racer6.getDist() >= length){
                    winner = true;
                    finishers[5] = racer6.getProg();
                }
                //third part checks to see if driver is out of gas(moves)
                racer6.adjustMoves(racer6SPD);
                if (racer6.getMoves() <= 0){
                    racer6HP = false;
                }    
           }
          
          //next segement of the method will display the racers progress through the race
          racerProgress = String.format("::Race Stats(turn %d)::\nRacer 1: %-5.2f Racer 2: %-5.2f Racer 3: %-5.2f\nRacer 4: %-5.2f Racer 5: %-5.2f Racer 6: %-5.2f", turns, racer1.getProg(), racer2.getProg(), racer3.getProg(), racer4.getProg(), racer5.getProg(),racer6.getProg());
          System.out.println(racerProgress + "\n");
          
          //this segment here calulates the winner of the cars that finish... in case of ties of course
          if(winner == true){
              double highest = finishers[0];
              for (int i = 0; i < finishers.length; i++) {
                  if (finishers[i] > highest){
                      highest = finishers[i];
                  }
              }
              int numIndex = indexOf(finishers, highest);
              winnerNum = numIndex + 1;
          }
          
          //this segment of code is incase none of the cars reach the finish line
          if (racer1HP == false && racer2HP == false && racer3HP == false && racer4HP == false && racer5HP == false && racer6HP == false){
              ArrayList<Double> percents = new ArrayList();
              percents.add(racer1.getProg()); percents.add(racer2.getProg()); percents.add(racer3.getProg()); percents.add(racer4.getProg()); percents.add(racer5.getProg()); percents.add(racer6.getProg()); 
              double max = percents.get(0);
              for (int i = 0; i < percents.size(); i++) {
                  if(percents.get(i) > max){
                      max = percents.get(i);
                  }
              }
              int num = percents.indexOf(max) + 1;
              winnerNum = num;
              winner = true;
          }
          //this little bit here will  allow the user to read the data and then the code will continue
          Thread.sleep(2000);
          //code shoud continue until race finishes
        } while (winner == false);
        
      return winnerNum;  
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
}
