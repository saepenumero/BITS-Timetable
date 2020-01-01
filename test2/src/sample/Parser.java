package sample;

/* TODO: Read this carefully: https://docs.oracle.com/javafx/2/ui_controls/table-view.htm
 * PROGRESS: Parsing done
 * NOTE: Current Timetable does not have common hour
 */
import java.io.File;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.PDFTextStripper;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Parser {

    static List<Course> Courses= new ArrayList<Course>();

    public static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }

    /*public static void parse() {
        try {
            PDDocument pd;
            File f1= new File("C:\\Users\\harsh\\OneDrive\\Desktop\\Timetable_Final.pdf");
            pd=PDDocument.load(f1);
            PDFTextStripper ts= new PDFTextStripper();
            ts.setStartPage(2);
            ts.setEndPage(44);
            ts.setSortByPosition(true);
            String temp= ts.getText(pd);
            Scanner read= new Scanner(temp);
            char sec_type='l';
            while(read.hasNext()) {
                String temp2= read.nextLine();
                if(temp2.contains("II. COURSEWISE TIMETABLE")
                        ||temp2.equals("COM COURSE NO. COURSE TITLE CREDIT INSTRUCTOR-IN-CHARGE/ ROOM DAYS HOURS COMMON COMPRE")
                        ||temp2.equals("S")
                        ||temp2.equals("E")
                        ||temp2.equals("COD     Instructor HOUR DATE &")
                        ||temp2.equals("L P U C")
                        ||temp2.equals("D H R SESSION"))
                    continue;
                else if(isInteger(temp2.substring(0, 1))) {
                    sec_type='l';
                    Course c= new Course();
                    StringTokenizer st1= new StringTokenizer(temp2);
                    st1.nextToken();
                    c.setDepartment(st1.nextToken().trim());
                    c.setCode(st1.nextToken().trim());
                    StringBuffer sb= new StringBuffer();
                    String sbtemp= st1.nextToken().trim();
                    sb.append(sbtemp);
                    int flag=0;
                    while((!sbtemp.trim().equalsIgnoreCase("-"))&&(!isInteger(sbtemp.trim()))) {
                        if(flag==0) {
                            sbtemp= st1.nextToken().trim();
                            flag++;
                            continue;
                        }
                        sb.append(" "+sbtemp);
                        sbtemp= st1.nextToken().trim();
                    }
                    c.setTitle(new String(sb));
                    if(sbtemp.equals("-")) {
                        c.setLec((short) 0);
                    }
                    else {
                        c.setLec(Short.parseShort(sbtemp));
                    }
                    sbtemp= st1.nextToken().trim();
                    if(sbtemp.equals("-")) {
                        c.setPract((short) 0);
                    }
                    else {
                        c.setPract(Short.parseShort(sbtemp));
                    }
                    sbtemp= st1.nextToken().trim();
                    if(sbtemp.equals("-")) {
                        c.setUnit((short) 0);
                    }
                    else {
                        c.setUnit(Short.parseShort(sbtemp));
                    }
                    Section temp_sec= new Section();
                    temp_sec.setSec_no(Short.parseShort(st1.nextToken().trim()));
                    sb= new StringBuffer(st1.nextToken().trim());
                    if(!st1.hasMoreTokens()) {
                        continue;
                    }
                    sbtemp=st1.nextToken().trim();
                    boolean ex=false;
                    while(!isInteger(sbtemp)) {
                        sb.append(" "+sbtemp);
                        if(st1.hasMoreTokens()) {
                            sbtemp= st1.nextToken().trim();
                        }
                        else {
                            ex=true;
                            break;
                        }
                    }
                    if(ex) {
                        continue;
                    }
                    temp_sec.addSec_instructors(new String(sb));
                    temp_sec.setRoom(Short.parseShort(sbtemp));
                    sb= new StringBuffer(st1.nextToken().trim());
                    sbtemp= st1.nextToken().trim();
                    while(!isInteger(sbtemp)) {
                        sb.append(" "+sbtemp);
                        if(st1.hasMoreTokens()) {
                            sbtemp= st1.nextToken().trim();
                        }
                        else {
                            ex=true;
                            break;
                        }
                    }
                    if(ex) {
                        continue;
                    }
                    temp_sec.setDays(new String(sb));
                    sb= new StringBuffer(sbtemp);
                    if(st1.hasMoreTokens()) {
                        sbtemp=st1.nextToken().trim();
                    }
                    while(isInteger(sbtemp)) {
                        sb.append(" "+sbtemp);
                        if(st1.hasMoreTokens()) {
                            sbtemp=st1.nextToken().trim();
                        }
                        else {
                            break;
                        }
                    }
                    temp_sec.setHours(new String(sb));
                    c.addSection(temp_sec, sec_type);
                    c.setCompreDate(sbtemp);
                    if(st1.hasMoreTokens()) {
                        c.setCompreTime(st1.nextToken().trim());
                    }
                    Courses.add(c);
                }
                else {
                    StringTokenizer st1= new StringTokenizer(temp2);
                    String sbtemp= st1.nextToken().trim();
                    if(isInteger(sbtemp)) {
                        boolean ex=false;
                        Section new_sec= new Section();
                        new_sec.setSec_no(Short.parseShort(sbtemp));
                        StringBuffer sb= new StringBuffer(st1.nextToken());
                        sbtemp=st1.nextToken().trim();
                        while(!isInteger(sbtemp)) {
                            sb.append(" "+sbtemp);
                            if(st1.hasMoreTokens()) {
                                sbtemp=st1.nextToken().trim();
                            }
                            else {
                                ex=true;
                                break;
                            }
                        }
                        if(ex) {
                            continue;
                        }
                        new_sec.addSec_instructors(new String(sb));
                        new_sec.setRoom(Short.parseShort(sbtemp));
                        sb= new StringBuffer(st1.nextToken());
                        sbtemp=st1.nextToken().trim();
                        while(!isInteger(sbtemp)) {
                            sb.append(" "+sbtemp);
                            sbtemp=st1.nextToken().trim();
                        }
                        new_sec.setDays(new String(sb));
                        sb= new StringBuffer(sbtemp);
                        while(st1.hasMoreTokens()) {
                            sb.append(" "+st1.nextToken().trim());
                        }
                        new_sec.setHours(new String(sb));
                        Courses.get(Courses.size()-1).addSection(new_sec, sec_type);
                    }
                    else if(sbtemp.equalsIgnoreCase("practical")) {
                        sec_type='p';
                        Section new_sec= new Section();
                        new_sec.setSec_no((short) 1);
                        sbtemp=st1.nextToken().trim();
                        if(isInteger(sbtemp)) {
                            sbtemp=st1.nextToken().trim();
                        }
                        boolean ex=false;
                        StringBuffer sb= new StringBuffer(sbtemp);
                        sbtemp=st1.nextToken().trim();
                        while(!isInteger(sbtemp)) {
                            sb.append(" "+sbtemp);
                            if(st1.hasMoreTokens()) {
                                sbtemp=st1.nextToken().trim();
                            }
                            else {
                                ex=true;
                                break;
                            }
                        }
                        if(ex) {
                            continue;
                        }
                        new_sec.addSec_instructors(new String(sb));
                        new_sec.setRoom(Short.parseShort(sbtemp));
                        sb= new StringBuffer(st1.nextToken());
                        sbtemp=st1.nextToken().trim();
                        while(!isInteger(sbtemp)) {
                            sb.append(" "+sbtemp);
                            sbtemp=st1.nextToken().trim();
                        }
                        new_sec.setDays(new String(sb));
                        sb= new StringBuffer(sbtemp);
                        while(st1.hasMoreTokens()) {
                            sb.append(" "+st1.nextToken().trim());
                        }
                        new_sec.setHours(new String(sb));
                        Courses.get(Courses.size()-1).addSection(new_sec, sec_type);
                    }
                    else if(sbtemp.equalsIgnoreCase("tutorial")) {
                        sec_type='t';


                        Section new_sec= new Section();
                        new_sec.setSec_no((short) 1);
                        sbtemp=st1.nextToken().trim();
                        if(isInteger(sbtemp)) {
                            sbtemp=st1.nextToken().trim();
                        }
                        boolean ex=false;
                        StringBuffer sb= new StringBuffer(sbtemp);
                        sbtemp=st1.nextToken().trim();
                        while(!isInteger(sbtemp)) {
                            sb.append(" "+sbtemp);
                            if(st1.hasMoreTokens()) {
                                sbtemp=st1.nextToken().trim();
                            }
                            else {
                                ex=true;
                                break;
                            }
                        }
                        if(ex) {
                            continue;
                        }
                        new_sec.addSec_instructors(new String(sb));
                        new_sec.setRoom(Short.parseShort(sbtemp));
                        sb= new StringBuffer(st1.nextToken());
                        sbtemp=st1.nextToken().trim();
                        while(!isInteger(sbtemp)) {
                            sb.append(" "+sbtemp);
                            sbtemp=st1.nextToken().trim();
                        }
                        new_sec.setDays(new String(sb));
                        sb= new StringBuffer(sbtemp);
                        while(st1.hasMoreTokens()) {
                            sb.append(" "+st1.nextToken().trim());
                        }
                        new_sec.setHours(new String(sb));
                        Courses.get(Courses.size()-1).addSection(new_sec, sec_type);

                    }
                    else {
                        StringBuffer sb= new StringBuffer(sbtemp);
                        while(st1.hasMoreTokens()) {
                            sb.append(" "+st1.nextToken().trim());
                        }
                        Courses.get(Courses.size()-1).addSecInstructors(new String(sb), sec_type);
                    }
                }
                //System.out.println(temp2);
            }
            read.close();
            pd.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }*/
}
