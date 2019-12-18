import javax.ejb.Local;

@Local
public interface Calculator {
    double add(double x, double y);
}