package sample;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Course implements Serializable {
    private String Department;
    private String Code;
    private String Title;
    private short Lec;
    private short Pract;
    private short Unit;
    private String CompreDate;
    private String CompreTime;
    List<Section> LectureSections = new ArrayList<Section>();
    List<Section> PracticalSections = new ArrayList<Section>();
    List<Section> TutorialSections = new ArrayList<Section>();

    public String getCode() {
        return Code;
    }

    public List<String> getSections() {
        List<String> temp= new ArrayList<String>();
        for(Section X:LectureSections) {
            temp.add("L"+X.getSec_no()+": "+X.getSec_instructors().get(0)+"- "+X.getDays()+" "+X.getHours());
        }
        for(Section X:PracticalSections) {
            temp.add("P"+X.getSec_no()+": "+X.getSec_instructors().get(0)+"- "+X.getDays()+" "+X.getHours());
        }
        for(Section X:TutorialSections) {
            temp.add("T"+X.getSec_no()+": "+X.getSec_instructors().get(0)+"- "+X.getDays()+" "+X.getHours());
        }
        return temp;
    }

    public List<Section> getObjectSections() {
        List<Section> temp= new ArrayList<Section>();
        temp.addAll(LectureSections);
        temp.addAll(PracticalSections);
        temp.addAll(TutorialSections);
        return temp;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setLec(short Lec) {
        this.Lec = Lec;
    }

    public void setPract(short Pract) {
        this.Pract = Pract;
    }

    public void setUnit(short Unit) {
        this.Unit = Unit;
    }

    public void setCompreDate(String CompreDate) {
        this.CompreDate = CompreDate;
    }

    public void setCompreTime(String CompreTime) {
        this.CompreTime = CompreTime;
    }

    public void addSection(Section section, char flag) {
        if (flag == 'l') {
            addLectureSection(section);
        } else if (flag == 't') {
            addTutorialSection(section);
        } else {
            addPracticalSection(section);
        }
    }

    public void addLectureSection(Section section) {
        LectureSections.add(section);
    }

    public void addPracticalSection(Section section) {
        PracticalSections.add(section);
    }

    public void addTutorialSection(Section section) {
        TutorialSections.add(section);
    }

    public void addSecInstructors(String Sec_instructor, char flag) {
        if (flag == 'l') {
            LectureSections.get(LectureSections.size() - 1).addSec_instructors(Sec_instructor);
        } else if (flag == 't') {
            TutorialSections.get(TutorialSections.size() - 1).addSec_instructors(Sec_instructor);
        } else {
            PracticalSections.get(PracticalSections.size() - 1).addSec_instructors(Sec_instructor);
        }
    }

    public String getDepartment() {
        return Department;
    }

    public String getTitle() {
        return Title;
    }

    @Override
    public String toString() {
        return (Department+" "+Code+":"+"\n"+Title);
    }


}
