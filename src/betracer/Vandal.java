
package betracer;
import java.util.*;
/**
 * A class used to create and store info of the Black Market Vandal
 * @author Programmer Patty
 */
public class Vandal {
    private double payment;
    private Car target;
    private boolean tell;
    
    private Random rand = new Random();
    public Vandal(){
        payment = 0;
        //the other two items will be set with methods
    }
    
    //METHODS
    public void setPayment(double money){
        payment = money;
    }
    public double getPayment(){
        return payment;
    }
    public void setTarget(Car car){
        target = car;
    }
    public Car getTarget(){
        return target;
    }
    public void setTell(){
        int number = rand.nextInt(100) + 1;
        if(number <= 5){
            tell = true;
        } else {
            tell = false;
        }
    }
    public boolean getTell(){
        return tell;
    }
}
