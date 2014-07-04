/*
 * Только для внутреннего пользования Web-Pixel.PRO
 */
package webpixelmanager;
/**
 * @author Konstantin Soyma
 */
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
//import com.mysql.jdbc.*;

public class WebPixelManager {
    /**
     * @param args the command line arguments
     */
    //Класс Проекты, инициируется через .set(...)
    public static class Project {
        public int id = 0;
        public String Project = null;
        public String www = null;
        public float cost = 0;
        public String login = null;
        public String password = null;
        public String hosting = null;
        public java.util.Date startDate = null;
        public java.util.Date Deadline = null;
        public String git = null;
        public String Description = null;
        public String Log = null;
        public Project set (int vId, String vProject, String vWWW,float vCost, 
                String vLogin, String vPassword,String vHosting, String vStartDate,
                String vEndDate,String vGIT, String vDescript, String vLog){
            this.id = vId;
            this.Project = vProject;
            this.www = vWWW;
            this.cost = vCost;
            this.login = vLogin;
            this.password = vPassword;
            this.hosting = vHosting;
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat ("yyyy-MM-dd");
            try{
                this.startDate = df.parse(vStartDate);
            } catch (Exception e){
                System.out.println(e);
                this.startDate = null;
            };
            try{
                this.Deadline = df.parse(vEndDate);
            } catch (Exception e){
                System.out.println(e);
                this.Deadline = null;
            };
            this.Description = vDescript;
            this.Log = vLog;
            this.git = vGIT;
            return this;
        };
    };
    //Класс клиенты, инициируется через .set(...)
    public static class Costumer{
        public int id = 0;
        public String firstname= null;
        public String secondname= null;
        public String midlename= null;
        public String phone = null;
        public String email = null;
        public String skype = null;
        public Costumer set(int vId, String vFname, String vSname, String vMname,
                String vTel, String vMail, String vSkype){
            this.id = vId;
            this.firstname = vFname;
            this.secondname = vSname;
            this.midlename = vMname;
            this.phone = vTel;
            this.email = vMail;
            this.skype = vSkype;
            return this;        
        }
    };
    //Переменные для работы с БД
    public static String db_host = "jdbc:mysql://web-pixel.pro/bh47794_db?zeroDateTimeBehavior=convertToNull";
    public static String db_login = "bh47794_diego";
    public static String db_pass = "d18031992";
    public static ArrayList<Project> GetDB = new ArrayList<Project>();
    //Основной код
    public static void main(String[] args) {
        //Подключение к БД
        Connection baza = null;
        System.out.println( "=> connecting:" );
        DefaultListModel ProjectListModel = new DefaultListModel();
        DefaultListModel ContactListModel = new DefaultListModel();
        try {
            baza = DriverManager.getConnection(db_host, db_login, db_pass);
            System.out.println( "OK" );
        //Получение данных из БД
            Statement st = baza.createStatement();
            ResultSet rs1=st.executeQuery("SELECT * FROM WebPixel_Projects;");
            while (rs1.next()) {
                ProjectListModel.addElement( rs1.getString("Project"));
                GetDB.add(new Project().set(rs1.getInt("id"),rs1.getString("Project"),
                        rs1.getString("www"), rs1.getFloat("cost"),rs1.getString("login"),
                        rs1.getString("password"),rs1.getString("hosting"),rs1.getString("startDate"),
                        rs1.getString("Deadline"),rs1.getString("git"),rs1.getString("Description"),
                        rs1.getString("WorkLog")));
            }
            ResultSet rs2=st.executeQuery("SELECT * FROM WebPixel_Clients;");
            while (rs2.next()) {
                ContactListModel.addElement( rs2.getString("Contact_Name"));
            }
            st.close();
        } catch (Exception e) {
            System.out.print(e);
        }
        MainXWindow MF = new MainXWindow();
        ProjectListModel.addElement("Новый проект");
        ContactListModel.addElement("Новый контакт");
        MF.ProjectsList.setModel(ProjectListModel);
        MF.ContactList.setModel(ContactListModel);
        MF.setVisible(true);
        

    }
    
}
