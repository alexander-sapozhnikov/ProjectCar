import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class Car {

    @EJB
    private String name = "";
    private String eng_name = "";
    private String power ="";
    private String kpp_name = "";
    private String person = "";
    private Integer count_step = 4;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEng_name() {
        return eng_name;
    }

    public void setEng_name(String eng_name) {
        this.eng_name = eng_name;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getKpp_name() {
        return kpp_name;
    }

    public void setKpp_name(String kpp_name) {
        this.kpp_name = kpp_name;
    }

    public Integer getCount_step() {
        return count_step;
    }

    public void setCount_step(Integer count_step) {
        this.count_step = count_step;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }
}
