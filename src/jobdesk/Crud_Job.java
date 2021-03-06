/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jobdesk;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Userpc
 */
public class Crud_Job extends DBkoneksi {

    public DefaultComboBoxModel picmodel = new DefaultComboBoxModel();
    //private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    public static String usm = "";
    public static String psm = "";
    public static String mem="";
    
    String[] titles = new String[]{"No", "Job Description", "Pic", "Request", "Target", "Finish", "Priorty Number", "Remark/Status", "Aprove", "Status Selesai", "Date Creation","Job Num"};
    public DefaultTableModel model = new DefaultTableModel(titles, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;

        }
    };

    public void updateusrRec(String usr, String pw) {

        try {
            preparedStatement = connect.prepareStatement("update " + user_helper.TB_NAME + " set "
                    + user_helper.KEY_PWD + "=AES_ENCRYPT(?,?)" + " where "
                    + user_helper.KEY_USER + "=?");

            String s = "0736";


            preparedStatement.setString(1, pw);
            preparedStatement.setBytes(2, s.getBytes());
            preparedStatement.setString(3, usr);

            preparedStatement.executeUpdate();


            JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");

        } catch (SQLException ex) {
            Logger.getLogger(Crud_Job.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateJobRec(String jobnum, String jobdesc, String pic, String request, String target, String finish, int priortynumber, String remark_status) {

        try {
            preparedStatement = connect.prepareStatement("update " + job_helper.TB_NAME + " set "
                    + job_helper.KEY_JOBDESC + "=?," + job_helper.KEY_PIC + "= ?,"
                    + job_helper.KEY_REQ + "=?," + job_helper.KEY_TARGET + "= ?,"
                    + job_helper.KEY_FINISH + "=?," + job_helper.KEY_PRIORTY + "= ?,"
                    + job_helper.KEY_REMARK_STATUS + "=?"
                    + " where " + job_helper.KEY_JOB_NUM + "=?");

            preparedStatement.setString(1, jobdesc);
            preparedStatement.setString(2, pic);
            preparedStatement.setString(3, request);
            preparedStatement.setString(4, target);
            preparedStatement.setString(5, finish);
            preparedStatement.setInt(6, priortynumber);
            preparedStatement.setString(7, remark_status);
            preparedStatement.setString(8, jobnum);

            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");

        } catch (SQLException ex) {
            Logger.getLogger(Crud_Job.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    ///like filter
//      public void updateJobRec(String dateCreation, String jobdesc, String pic, String request, String target, String finish, int priortynumber, String remark_status) {
//
//        try {
//            preparedStatement = connect.prepareStatement("update " + job_helper.TB_NAME + " set "
//                    + job_helper.KEY_JOBDESC + "=?," + job_helper.KEY_PIC + "= ?,"
//                    + job_helper.KEY_REQ + "=?," + job_helper.KEY_TARGET + "= ?,"
//                    + job_helper.KEY_FINISH + "=?," + job_helper.KEY_PRIORTY + "= ?,"
//                    + job_helper.KEY_REMARK_STATUS + "=?"
//                    + " where " + job_helper.KEY_DATE_CREATION + " like ?");
//
//            preparedStatement.setString(1, jobdesc);
//            preparedStatement.setString(2, pic);
//            preparedStatement.setString(3, request);
//            preparedStatement.setString(4, target);
//            preparedStatement.setString(5, finish);
//            preparedStatement.setInt(6, priortynumber);
//            preparedStatement.setString(7, remark_status);
//            preparedStatement.setString(8, "%" + dateCreation + "%");
//
//            preparedStatement.executeUpdate();
//
//            JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");
//
//        } catch (SQLException ex) {
//            Logger.getLogger(Crud_Job.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
    
    
    
    
    public void readRec(String username) throws SQLException {

         preparedStatement = connect.prepareStatement("select * from " + job_helper.TB_NAME
                + " where " + job_helper.KEY_USERNAME + "=?");

       // preparedStatement.setString(1, "%" + tgl + "%");
       
        preparedStatement.setString(1, username);
        
         ResultSet resultSet = preparedStatement.executeQuery();
         
        int i = 0;

        while (resultSet.next()) {

            i++;

            String no = String.valueOf(i);
            String jobdesc = resultSet.getString(job_helper.KEY_JOBDESC);
            String pic = resultSet.getString(job_helper.KEY_PIC);
            String req = resultSet.getString(job_helper.KEY_REQ);
            String tar = resultSet.getString(job_helper.KEY_TARGET);
            String fin = resultSet.getString(job_helper.KEY_FINISH);
            String pnum = resultSet.getString(job_helper.KEY_PRIORTY);
            String remark = resultSet.getString(job_helper.KEY_REMARK_STATUS);
            String aprove = resultSet.getString(job_helper.KEY_STAT_APROVE);
            String statselesai = resultSet.getString(job_helper.KEY_STAT_SELESAI);
            String tglcreation = resultSet.getString(job_helper.KEY_DATE_CREATION);
            int jobnum = resultSet.getInt(job_helper.KEY_JOB_NUM);
            model.addRow(new Object[]{no, jobdesc, pic, req, tar, fin, pnum, remark, aprove, statselesai, tglcreation,jobnum});

        }
    }

    public void readRecStat(String stat,String username, int opt) throws SQLException {

        if (opt == 0) {
            preparedStatement = connect.prepareStatement("select * from " + job_helper.TB_NAME
                    + " where " + job_helper.KEY_STAT_SELESAI + "=? and "+ job_helper.KEY_USERNAME + "=?");

            preparedStatement.setString(1, stat);
            preparedStatement.setString(2, username);
            
            ResultSet resultSet = preparedStatement.executeQuery();


            int i = 0;

            while (resultSet.next()) {

                i++;

                String no = String.valueOf(i);
                String jobdesc = resultSet.getString(job_helper.KEY_JOBDESC);
                String pic = resultSet.getString(job_helper.KEY_PIC);
                String req = resultSet.getString(job_helper.KEY_REQ);
                String tar = resultSet.getString(job_helper.KEY_TARGET);
                String fin = resultSet.getString(job_helper.KEY_FINISH);
                String pnum = resultSet.getString(job_helper.KEY_PRIORTY);
                String remark = resultSet.getString(job_helper.KEY_REMARK_STATUS);
                String aprove = resultSet.getString(job_helper.KEY_STAT_APROVE);
                String statselesai = resultSet.getString(job_helper.KEY_STAT_SELESAI);
                String tglcreation = resultSet.getString(job_helper.KEY_DATE_CREATION);
                int jobnum = resultSet.getInt(job_helper.KEY_JOB_NUM);
                model.addRow(new Object[]{no, jobdesc, pic, req, tar, fin, pnum, remark, aprove, statselesai, tglcreation,jobnum});

            }
        } else {
            preparedStatement = connect.prepareStatement("select * from " + job_helper.TB_NAME
                    + " where " + job_helper.KEY_STAT_APROVE + "=? and "+ job_helper.KEY_USERNAME + "=?");

            preparedStatement.setString(1, stat);
            preparedStatement.setString(2, username);
            
            ResultSet resultSet = preparedStatement.executeQuery();


            int i = 0;

            while (resultSet.next()) {

                i++;

                String no = String.valueOf(i);
                String jobdesc = resultSet.getString(job_helper.KEY_JOBDESC);
                String pic = resultSet.getString(job_helper.KEY_PIC);
                String req = resultSet.getString(job_helper.KEY_REQ);
                String tar = resultSet.getString(job_helper.KEY_TARGET);
                String fin = resultSet.getString(job_helper.KEY_FINISH);
                String pnum = resultSet.getString(job_helper.KEY_PRIORTY);
                String remark = resultSet.getString(job_helper.KEY_REMARK_STATUS);
                String aprove = resultSet.getString(job_helper.KEY_STAT_APROVE);
                String statselesai = resultSet.getString(job_helper.KEY_STAT_SELESAI);
                String tglcreation = resultSet.getString(job_helper.KEY_DATE_CREATION);
 int jobnum = resultSet.getInt(job_helper.KEY_JOB_NUM);
                model.addRow(new Object[]{no, jobdesc, pic, req, tar, fin, pnum, remark, aprove, statselesai, tglcreation,jobnum});

            }
        }

    }

    public void readRec(String tgl,String username) throws SQLException {



        preparedStatement = connect.prepareStatement("select * from " + job_helper.TB_NAME
                + " where " + job_helper.KEY_DATE_CREATION + "=? and "+ job_helper.KEY_USERNAME+ "=?");

       // preparedStatement.setString(1, "%" + tgl + "%");
        preparedStatement.setString(1, tgl);
        preparedStatement.setString(2, username);
        ResultSet resultSet = preparedStatement.executeQuery();


        int i = 0;

        while (resultSet.next()) {

            i++;

            String no = String.valueOf(i);
            String jobdesc = resultSet.getString(job_helper.KEY_JOBDESC);
            String pic = resultSet.getString(job_helper.KEY_PIC);
            String req = resultSet.getString(job_helper.KEY_REQ);
            String tar = resultSet.getString(job_helper.KEY_TARGET);
            String fin = resultSet.getString(job_helper.KEY_FINISH);
            String pnum = resultSet.getString(job_helper.KEY_PRIORTY);
            String remark = resultSet.getString(job_helper.KEY_REMARK_STATUS);
            String aprove = resultSet.getString(job_helper.KEY_STAT_APROVE);
            String statselesai = resultSet.getString(job_helper.KEY_STAT_SELESAI);
            String tglcreation = resultSet.getString(job_helper.KEY_DATE_CREATION);
             int jobnum = resultSet.getInt(job_helper.KEY_JOB_NUM);
            model.addRow(new Object[]{no, jobdesc, pic, req, tar, fin, pnum, remark, aprove, statselesai, tglcreation,jobnum});

        }
    }

    Crud_Job() throws Exception {
        ConDb();
    }

    public void Cek(String pl) throws SQLException {


        preparedStatement = connect.prepareStatement("select *, CAST(AES_DECRYPT(pwd_x, '0736') AS CHAR(255)) xcd from " + user_helper.TB_NAME + " where "
                + user_helper.KEY_USER + " = ?");

        preparedStatement.setString(1, pl);


        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            psm = resultSet.getString("xcd");
            mem= resultSet.getString("position");
            
            System.out.println(psm);
        }

        if (!psm.isEmpty() && mem.equals("mem")) {

            usm = pl;

//            StrPr m = new StrPr();
//
//            m.setCredentials(pl, tmp);


        }

    }

    public void SaveJob(String jobdesc, String pic, String request, String target, String finish, int priortynumber, String remark_status, String status_aprove, String status_selesai, String username, String date_creation) {

        try {
            preparedStatement = connect.prepareStatement("insert into " + job_helper.TB_NAME + " (" + job_helper.KEY_JOBDESC + "," + job_helper.KEY_PIC
                    + "," + job_helper.KEY_REQ + "," + job_helper.KEY_TARGET + "," + job_helper.KEY_FINISH + "," + job_helper.KEY_PRIORTY + "," + job_helper.KEY_REMARK_STATUS
                    + "," + job_helper.KEY_STAT_APROVE + "," + job_helper.KEY_STAT_SELESAI + "," + job_helper.KEY_USERNAME + "," + job_helper.KEY_DATE_CREATION + ") "
                    + " values (?,?,?,?,?,?,?,?,?,?,?)");

            preparedStatement.setString(1, jobdesc);
            preparedStatement.setString(2, pic);
            preparedStatement.setString(3, request);
            preparedStatement.setString(4, target);
            preparedStatement.setString(5, finish);
            preparedStatement.setInt(6, priortynumber);
            preparedStatement.setString(7, remark_status);
            preparedStatement.setString(8, status_aprove);
            preparedStatement.setString(9, status_selesai);
            preparedStatement.setString(10, username);
            preparedStatement.setString(11, date_creation);
            preparedStatement.execute();


            JOptionPane.showMessageDialog(null, "Data Tersimpan");
        } catch (SQLException ex) {
            Logger.getLogger(Crud_Job.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public void readRecPIC() throws SQLException {



        statement = connect.createStatement();

        resultSet = statement.executeQuery("Select * from tb_pic");

        while (resultSet.next()) {

            //String pic = resultSet.getString("pic");

            //model.addRow(new Object[]{id_sheet, master_name});
            picmodel.addElement(resultSet.getString("pic"));
        }

        connect.close();
    }

    public void DelUserRec(String usr) throws SQLException {

        preparedStatement = connect.prepareStatement("delete from " + user_helper.TB_NAME + " where " + user_helper.KEY_USER + "=?");

        preparedStatement.setString(1, usr);

        preparedStatement.execute();


    }

    public void DelRec(String jobnum) throws SQLException {

        preparedStatement = connect.prepareStatement("delete from " + job_helper.TB_NAME + " where " + job_helper.KEY_JOB_NUM + "=?");

        preparedStatement.setString(1, jobnum);

        preparedStatement.execute();


    }

    public void DelRecAll(String tgl,String username) throws SQLException {

       if(CekDataSelesai(tgl,username)>0){ 
          JOptionPane.showMessageDialog(null, "Data Tidak bisa di hapus semua karena ada data yang sudah selesai! ");
       }
       else{
        preparedStatement = connect.prepareStatement("delete from " + job_helper.TB_NAME
                + " where " + job_helper.KEY_DATE_CREATION + " like ? and "+job_helper.KEY_USERNAME+"=?");

        preparedStatement.setString(1, "%" + tgl + "%");
        preparedStatement.setString(2, username);
        
        preparedStatement.execute();
       }

    }

     public int CekDataSelesai(String tgl,String username) throws SQLException {

           preparedStatement = connect.prepareStatement("select ifnull(count(jobactive.status_selesai), 0) AS sudahselesai from " + job_helper.TB_NAME
                + " where " + job_helper.KEY_STAT_SELESAI + "='ok' and jobactive.username<>'root' and "+job_helper.KEY_DATE_CREATION+" like ? and "+ job_helper.KEY_USERNAME+"=?");
        
        
        preparedStatement.setString(1, "%" + tgl + "%");
        preparedStatement.setString(2, username);
        
        ResultSet resultSet = preparedStatement.executeQuery();


        int i = 0;

        
        while (resultSet.next()) {

            
           i = resultSet.getInt(v_result_helper.KEY_SELESAI);

        }
           

         return i;
    }
    
    
    public void CetakAll(String username) throws JRException {

        Map map = new HashMap();
//        //File f=new File("sunlogo.jpg");
//       
//        ImageIcon gto = new ImageIcon(getClass().getResource("sunlogo.jpg"));
//        
////        File ops=new File(getClass().getResourceAsStream("sunlogo.jpg").getPath());
////        System.out.println(ops.getAbsolutePath());
//        //"D://Data Works//Apps Project//JOBDesk//build//classes//jobdesk//"
//        
         map.put("username",username);
        
        InputStream is = null;
        is = getClass().getResourceAsStream("report1.jasper");

        JasperReport jr = (JasperReport) JRLoader.loadObject(is);

        JasperPrint jp = JasperFillManager.fillReport(jr, map, connect);

        JasperViewer.viewReport(jp, false);

    }

    public void CetakPerTgl(String tgl,String username) throws JRException {



//        InputStream is = null;
//        is = getClass().getResourceAsStream("report2.jasper");
//
//        //set parameters
//                    Map map = new HashMap();
//                    map.put("tgl_r", tgl);
//        
//        JasperReport jr = (JasperReport) JRLoader.loadObject(is);
//
//        JasperPrint jp = JasperFillManager.fillReport(jr, map, connect);
//
//        JasperViewer.viewReport(jp, false);


//        URL url = getClass().getResource("report2.jrxml");
//        File rpt = new File(url.getPath());

        InputStream is = null;
        is = getClass().getResourceAsStream("report2.jrxml");

        //set parameters
        Map map = new HashMap();
        map.put("tgl_r", tgl);
        map.put("username",username);
        
       // map.put("Imgpath",null);

        JasperReport jr = JasperCompileManager.compileReport(is);

        JasperPrint jp = JasperFillManager.fillReport(jr, map, connect);

        JasperViewer.viewReport(jp, false);

    }
}
