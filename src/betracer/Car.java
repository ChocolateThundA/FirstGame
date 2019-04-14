
package betracer;
import java.util.*;
/**
 * Class used to generate cars
 * @author Programmer Patty
 */
public class Car {
    private String name;
    int acceleration, topSpeed, moves, distance, rate;
    double progress;
    boolean HP, isWinner;
    Random rand = new Random();
    
    public Car(String name){
        this.name = name;
        acceleration = rand.nextInt(10) + 1;
        topSpeed = rand.nextInt(15) + 1;
        rate = 0;
        progress = 0.0;
        HP = true;
        isWinner = false;
        moves = rand.nextInt((90 - 30) + 1) + 30;
        distance = 0;
    }
    public String getName(){
        return name;
    }
    public int getAccel(){
        return acceleration;
    }
    public int getSpeed(){
        return topSpeed;
    }
    public void calcProg(int num, int length){
        Double doubNum = new Double(num);
        Double doubLen = new Double(length);
        progress = doubNum / doubLen;
    }
    public double getProg(){
        return progress;
    }
    public void resetProg(){
        progress = 0;
    }
    public int getMoves(){
        return moves;
    }
    public void adjustMoves(int num){
        moves -= num;
    }
    public void moveDist(int num){
        distance += num;
    }
    public int getDist(){
        return distance;
    }
    public void setRate(int num){
        rate = num;
    }
    public void addRate(int increase){
        rate = rate + increase;
    }
    public int getRate(){
        return rate;
    }
    public void setHP(boolean change){
        HP = change;
    }
    public boolean getHP(){
        return HP;
    }
    public void setWinner(){
        isWinner = true;
    }
    public boolean getIsWinner(){
        return isWinner;
    }
    
    @Override
    public String toString(){
        return "Name: " + name + "Acceleration: " + acceleration + "\nTop Speed: " + topSpeed + "\nMoves: " + moves + "Progress: " + progress + "Distance: " + distance ;
    }
    //methods added in during the black market update
    public void adjustAccel(int num){
        acceleration = num;
    }
    public void adjustTopSpeed(int num){
        topSpeed = num;
    }
    public void changeMoves(int num){
        moves = num;
    }
}