
package betracer;

/**
 * Class used to generate players and store their data during game
 * @author Programmer Patty
 */
public class Player {
    private String name; 
    private int wins, losses, racerBet, sponsor;
    private double money;
    
    public Player(){
        name = "";
        wins = 0; losses = 0;
        money = 5000.00;
        racerBet = 0;
        sponsor = 0;
    }
    
    //methods
    public void setName(String word){
        name = word;
    }
    public String getName(){
        return name;
    }
    public int getLosses(){
        return losses;
    }
    public void addLoss(){
        losses += 1;
    }
    public void setLoss(int num){
        losses = num;
    }
    public int getWins(){
        return wins;
    }
    public void addWins(){
        wins += 1;
    }
    public void setWins(int num){
        wins = num;
    }
    public double getMoney(){
        return money;
    }
    public void alterMoney(double num){
        money = money + num;
    }
    public void setMoney(double num){
        money = num;
    }
    @Override
    public String toString(){
        return String.format("Username: %s\nMoney: %.2f\nWins: %d    Losses: %d\nSponsored Driver: %d", name, money, wins, losses, sponsor);
    }
    public void setBet(int num){
        racerBet = num;
    }
    public int getBet(){
        return racerBet;
    }
    //next few methods are part of the sponsor update
    //they will allow a player to sponsor a driver 
    public void setSponsor(int num){
        sponsor = num;
    }
    public int getSponsor(){
        return sponsor;
    }
}
