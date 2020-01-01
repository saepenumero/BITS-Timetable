//TODO: Implement remove course using TableList

package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

public class Controller {
    List<Course> TableList= new ArrayList<Course>();
    @FXML private TableView<Cell> tableView;
    @FXML private TableColumn<Cell, String> HourDay;
    @FXML private TableColumn<Cell, String> Monday;
    @FXML private TableColumn<Cell, String> Tuesday;
    @FXML private TableColumn<Cell, String> Wednesday;
    @FXML private TableColumn<Cell, String> Thursday;
    @FXML private TableColumn<Cell, String> Friday;
    @FXML private TableColumn<Cell, String> Saturday;
    @FXML private ComboBox<String> DepList;
    @FXML private ComboBox<String> CourseList;
    @FXML private ComboBox<String> SectionList;
    @FXML private ComboBox<String> RemoveSectionList;
    @FXML private Button AddCourse;
    @FXML private Button RemoveSection;

    @FXML private void initialize() {
        HourDay.setCellValueFactory(new PropertyValueFactory<Cell, String>("HourDay"));
        Monday.setCellValueFactory(new PropertyValueFactory<Cell, String>("Monday"));
        Tuesday.setCellValueFactory(new PropertyValueFactory<Cell, String>("Tuesday"));
        Wednesday.setCellValueFactory(new PropertyValueFactory<Cell, String>("Wednesday"));
        Thursday.setCellValueFactory(new PropertyValueFactory<Cell, String>("Thursday"));
        Friday.setCellValueFactory(new PropertyValueFactory<Cell, String >("Friday"));
        Saturday.setCellValueFactory(new PropertyValueFactory<Cell, String>("Saturday"));

        tableView.getItems().addAll(new Cell("Hour 1 (8:00-9:00)"),
                new Cell("Hour 2 (9:00-10:00)"),
                new Cell("Hour 3 (10:00-11:00)"),
                new Cell("Hour 4 (11:00-12:00)"),
                new Cell("Hour 5 (12:00-13:00)"),
                new Cell("Hour 6 (13:00-14:00)"),
                new Cell("Hour 7 (14:00-15:00)"),
                new Cell("Hour 8 (15:00-16:00)"),
                new Cell("Hour 9 (16:00-17:00)"),
                new Cell("Hour 10 (17:00-18:00)"));
        //Parser.parse();
        try {
            FileInputStream fis = new FileInputStream("Courses.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Parser.Courses= (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (Exception e) {
            System.out.println("FML"+e);
        }
        for(Course X:Parser.Courses) {
            if(!DepList.getItems().contains(X.getDepartment())) {
                DepList.getItems().add(X.getDepartment());
            }
        }
        DepList.setOnAction(e->{
            CourseList.getItems().clear();
            String tempDep= DepList.getValue();
            for(Course X: Parser.Courses) {
                if(X.getDepartment().equalsIgnoreCase(tempDep)) {
                    CourseList.getItems().add(X.getDepartment()+" "+X.getCode()+": "+X.getTitle());
                }
            }
        });
       CourseList.setOnAction(e-> {
            SectionList.getItems().clear();
            String tempCourse= CourseList.getValue();
            for(Course X:Parser.Courses) {
                if(tempCourse.equalsIgnoreCase(X.getDepartment()+" "+X.getCode()+": "+X.getTitle())) {
                    SectionList.getItems().addAll(X.getSections());
                    break;
                }
            }
        });
       AddCourse.setOnAction(e->{
           StringTokenizer st= new StringTokenizer(SectionList.getValue());
           String tempCourse=CourseList.getValue();

           Course addedCourse= new Course();
           Section addedSection= new Section();
           for(Course X: Parser.Courses) {
               if(tempCourse.equalsIgnoreCase((X.getDepartment()+" "+X.getCode()+": "+X.getTitle()))) {
                   addedCourse=X;
                   break;
               }
           }
           for(Section X: addedCourse.getObjectSections()) {
               if(SectionList.getValue().contains(X.retVerify())) {
                   addedSection= X;
                   break;
               }
           }


           st.nextToken("-");
           st.nextToken(" ");
           List<String> Days= new ArrayList<String>();
           List<String> Hours= new ArrayList<String>();
           String tempDayOrHour= st.nextToken(" ").trim();
           while(!Parser.isInteger(tempDayOrHour)) {
               Days.add(tempDayOrHour);
               tempDayOrHour= st.nextToken().trim();
           }
           Hours.add(tempDayOrHour.trim());
           while(st.hasMoreTokens()) {
               tempDayOrHour = st.nextToken().trim();
               Hours.add(tempDayOrHour);
           }
           boolean clashFlag=false;
           for(String Y:Hours) {
               Cell tempCell=tableView.getItems().get(Integer.parseInt(Y)-1);
               for(String X:Days) {
                   if(X.equalsIgnoreCase("M")) {
                       if(tempCell.getMonday()!=null&&!tempCell.getMonday().equals("")) {
                           clashFlag=true;
                           break;
                       }
                   }
                   else if(X.equalsIgnoreCase("T")) {
                       if(tempCell.getTuesday()!=null&&!tempCell.getTuesday().equals("")) {
                           clashFlag=true;
                           break;
                       }
                   }
                   else if(X.equalsIgnoreCase("W")) {
                       if(tempCell.getWednesday()!=null&&!tempCell.getWednesday().equals("")) {
                           clashFlag=true;
                           break;
                       }
                   }
                   else if(X.equalsIgnoreCase("Th")) {
                       if(tempCell.getThursday()!=null&&!tempCell.getThursday().equals("")) {
                           clashFlag=true;
                           break;
                       }
                   }
                   else if(X.equalsIgnoreCase("F")) {
                       if(tempCell.getFriday()!=null&&!tempCell.getFriday().equals("")) {
                           clashFlag=true;
                           break;
                       }
                   }
                   else {
                       if(tempCell.getSaturday()!=null&&!tempCell.getSaturday().equals("")) {
                           clashFlag=true;
                           break;
                       }
                   }
                   if(clashFlag) {
                       break;
                   }
               }
           }
           if(clashFlag) {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Clash");
               String s = "There is a clash in the time slots.";
               alert.setContentText(s);

               Optional<ButtonType> result = alert.showAndWait();

           }
           else {
               Course courseToBeAdded= new Course();
               courseToBeAdded.setCode(addedCourse.getCode());
               courseToBeAdded.setDepartment(addedCourse.getDepartment());
               courseToBeAdded.setTitle(addedCourse.getTitle());
               courseToBeAdded.addLectureSection(addedSection);
               TableList.add(courseToBeAdded);
               RemoveSectionList.getItems().add(courseToBeAdded.getDepartment()+" "+courseToBeAdded.getCode()+": "+
                       courseToBeAdded.getObjectSections().get(0).getDays()+" "+courseToBeAdded.getObjectSections().get(0).getHours());

               for(String Y:Hours) {
                   Cell tempCell=tableView.getItems().get(Integer.parseInt(Y)-1);
                   for(String X:Days) {
                       if(X.equalsIgnoreCase("M")) {
                           tempCell.setMonday(addedCourse.toString()+"\n"+addedSection.getSec_instructors().get(0)+
                                   "\n"+addedSection.getRoom());
                       }
                       else if(X.equalsIgnoreCase("T")) {
                           tempCell.setTuesday(addedCourse.toString()+"\n"+addedSection.getSec_instructors().get(0)+
                                   "\n"+addedSection.getRoom());
                       }
                       else if(X.equalsIgnoreCase("W")) {
                           tempCell.setWednesday(addedCourse.toString()+"\n"+addedSection.getSec_instructors().get(0)+
                                   "\n"+addedSection.getRoom());
                       }
                       else if(X.equalsIgnoreCase("Th")) {
                           tempCell.setThursday(addedCourse.toString()+"\n"+addedSection.getSec_instructors().get(0)+
                                   "\n"+addedSection.getRoom());
                       }
                       else if(X.equalsIgnoreCase("F")) {
                           tempCell.setFriday(addedCourse.toString()+"\n"+addedSection.getSec_instructors().get(0)+
                                   "\n"+addedSection.getRoom());
                       }
                       else {
                           tempCell.setSaturday(addedCourse.toString()+"\n"+addedSection.getSec_instructors().get(0)+
                                   "\n"+addedSection.getRoom());
                       }
                   }
               }

               tableView.refresh();
           }

       });

       RemoveSection.setOnAction(e-> {
           if (RemoveSectionList.getValue() != null) {
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setTitle("Course Removal");
               String s = "Are you sure you want to remove the course?";
               alert.setContentText(s);

               Optional<ButtonType> result = alert.showAndWait();

               if ((result.isPresent()) && (result.get() == ButtonType.OK)) {

                   //Experimental Code
                   Course removedCourse = TableList.get(RemoveSectionList.getItems().indexOf(RemoveSectionList.getValue()));
                   List<String> Days = new ArrayList<String>();
                   List<String> Hours = new ArrayList<String>();
                   StringTokenizer st = new StringTokenizer(removedCourse.getObjectSections().get(0).getDays());
                   String tempDayOrHour;
                   do {
                       tempDayOrHour = st.nextToken(" ");
                       Days.add(tempDayOrHour.trim());
                   }
                   while (st.hasMoreTokens());
                   st = new StringTokenizer(removedCourse.getObjectSections().get(0).getHours());
                   do {
                       tempDayOrHour = st.nextToken(" ");
                       Hours.add(tempDayOrHour.trim());
                   }
                   while (st.hasMoreTokens());

                   for (String Y : Hours) {
                       Cell tempCell = tableView.getItems().get(Integer.parseInt(Y) - 1);
                       for (String X : Days) {
                           if (X.equalsIgnoreCase("M")) {
                               tempCell.setMonday("");
                           } else if (X.equalsIgnoreCase("T")) {
                               tempCell.setTuesday("");
                           } else if (X.equalsIgnoreCase("W")) {
                               tempCell.setWednesday("");
                           } else if (X.equalsIgnoreCase("Th")) {
                               tempCell.setThursday("");
                           } else if (X.equalsIgnoreCase("F")) {
                               tempCell.setFriday("");
                           } else {
                               tempCell.setSaturday("");
                           }
                       }
                   }

                   TableList.remove(RemoveSectionList.getItems().indexOf(RemoveSectionList.getValue()));
                   RemoveSectionList.getItems().remove(RemoveSectionList.getValue());
                   tableView.refresh();
               }
           }
       });

    }
}
