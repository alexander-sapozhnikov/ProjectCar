import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

/**
 * Вспомогательный класс для удобства работы
 * с данными person, kpp, engine
 *
 */

@ManagedBean
public class Capsule {

    @EJB
    private String name;
    private int index;

    public Capsule(String name, int index) {
        this.name = name;
        this.index = index;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
