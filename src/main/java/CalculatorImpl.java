import javax.ejb.Stateless;

@Stateless
public class CalculatorImpl {

    public double add(double x, double y) {
        return x + y;
    }

}