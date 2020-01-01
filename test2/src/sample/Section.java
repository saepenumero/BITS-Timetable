package sample;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Section implements Serializable {
    private short Sec_no;
    private List<String> Sec_instructors= new ArrayList<String>();
    private short Room;
    private String Days;
    private String Hours;

    public short getSec_no() {
        return Sec_no;
    }

    public List<String> getSec_instructors() {
        return Sec_instructors;
    }

    public String getDays() {
        return Days;
    }

    public String getHours() {
        return Hours;
    }

    public String retVerify() {
        return this.getSec_no()+": "+this.getSec_instructors().get(0)+"- "+this.getDays()+" "+this.getHours();
    }

    public short getRoom() {
        return Room;
    }

    public void setSec_no(short Sec_no) {
        this.Sec_no=Sec_no;
    }
    public void setRoom(short Room) {
        this.Room=Room;
    }
    public void setDays(String Days) {
        this.Days=Days;
    }
    public void setHours(String Hours) {
        this.Hours=Hours;
    }
    public void addSec_instructors(String Sec_instructor) {
        Sec_instructors.add(Sec_instructor);
    }
    @Override
    public String toString() {
        return ("Section no: "+Sec_no+"\nSection instructors: \n"+Sec_instructors+"\nRoom: "+Room+"\nDays: "+Days+"\nHours: "+Hours);
    }
}
