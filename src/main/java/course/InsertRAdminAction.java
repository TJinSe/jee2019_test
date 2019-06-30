package course;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;

public class InsertRAdminAction extends ActionSupport {
    private String roomID;
    private String roomPwd;
    private String roomName;
    private String roomSex;
    private String roomPhone;
    private int found;

    Connection con;
    Statement sql;
    ResultSet rs;

    String dbDriver="org.hsqldb.jdbcDriver";
    String dbUrl="jdbc:hsqldb:hsql://localhost";
    String dbUsername="sa";
    String dbPassword="";

    public String execute() throws Exception{
        found=0;
        HttpServletRequest request= ServletActionContext.getRequest();
        CourseDesign_Bean resultBean=null;
        try {
            resultBean=(CourseDesign_Bean)request.getAttribute("resultBean");
            if(resultBean==null)
            {
                resultBean=new CourseDesign_Bean();
                request.setAttribute("resultBean", resultBean);//创建javaBean对象
            }
        }
        catch(Exception exp)
        {
            resultBean=new CourseDesign_Bean();
            request.setAttribute("resultBean", resultBean);//创建javaBean对象
        }
        try {
            Class.forName(dbDriver);//加载驱动程序
        }
        catch(Exception e) {
            found=2;
            System.out.println("驱动异常异常");
        }
        request.setCharacterEncoding("UTF-8");
        //String dataBase=request.getParameter("dataBase");
        //String tableName=request.getParameter("tableName");
        String roomID=request.getParameter("roomID");
        String roomPwd=request.getParameter("roomPwd");
        String roomName=request.getParameter("roomName");
        String roomSex=request.getParameter("roomSex");
        String roomPhone=request.getParameter("roomPhone");
        System.out.println(roomID);
        System.out.println(roomPwd);
        String condition="SELECT * from roomadmins"+" where RANumber='"+roomID+"'";
        System.out.println(condition);

        try {
            //String uri="jdbc:mysql://127.0.0.1/student"+"?"+
                    //"user=root&password=123456&serverTimezone=GMT&characterEncoding=GB2312";
            con=DriverManager.getConnection(dbUrl,dbUsername,dbPassword);//连接数据库
            sql=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//获得statement对象
            rs=sql.executeQuery(condition);//查询数据
            //ResultSetMetaData metaData=rs.getMetaData();//得到结果集的元数据对象
            int count=0;
            rs.last();//将游标指向最后一行
            count=rs.getRow();//得到行数
            rs.beforeFirst();//将游标指向第一行之前
            if(count!=0)
            {
                javax.swing.JOptionPane.showMessageDialog(null, "该宿管员已存在!");
                //response.sendRedirect("insertRAdmin.jsp");
            }
            else {
                String str = "INSERT INTO roomadmins" + " VALUES" + "(" +
                        "'" + roomID + "','" + roomPwd + "','"+ roomName + "','"+roomSex+"','"+roomPhone+"')";
                sql.executeUpdate(str);// 添加数据
                rs = sql.executeQuery("SELECT * FROM roomadmins");// 得到结果集
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();// 得到结果集的列数
                String[] columnName = new String[columnCount];
                for (int i = 0; i < columnName.length; i++) {
                    columnName[i] = metaData.getColumnName(i + 1);// 得到列名
                }
                resultBean.setColumnName(columnName);// 更新javaBean数据模型
                rs.last();// 将游标指向结果集最后一行
                int rowNumber = rs.getRow();// 得到列数
                String[][] tableRecord = resultBean.getTableRecord();
                tableRecord = new String[rowNumber][columnCount];
                rs.beforeFirst();// 将游标指向第一行之前
                int i = 0;
                while (rs.next()) {
                    for (int k = 0; k < columnCount; k++)
                        tableRecord[i][k] = rs.getString(k + 1);
                    i++;
                }
                resultBean.setTableRecord(tableRecord);// 更新javaBean数据模型
                con.close();
                found=1;
                //RequestDispatcher dispatcher = request.getRequestDispatcher("showRecord.jsp");
                //dispatcher.forward(request, response);
            }
        }
        catch(SQLException e)
        {
            found=2;
            System.out.println("添加记录失败:"+e.toString());
            //fail(request,response,"添加记录失败:"+e.toString());
        }
        if(found==1)
        {
            return "suc";
        }
        else if(found==2)
        {
            return "fail";
        }
        return "input";
    }

    @Override
    public void validate(){
        super.validate();
        HttpServletRequest request=ServletActionContext.getRequest();
        try {
            request.setCharacterEncoding("UTF-8");
            roomID=request.getParameter("roomID");
            roomPwd=request.getParameter("roomPwd");
            roomName=request.getParameter("roomName");
            roomSex=request.getParameter("roomSex");
            roomPhone=request.getParameter("roomPhone");
        }catch (Exception e)
        {
            System.out.println(e);
        }
        System.out.println(roomID);
        System.out.println(roomPwd);
        System.out.println(roomName);
        System.out.println(roomSex);
        System.out.println(roomPhone);
        if(roomID.length()<1 || roomID.length()>=15 )//1到15
        {
            this.addFieldError("roomID", "添加失败，账号长度为1到15！");
        }
        else if(roomPwd.length()<1 || roomPwd.length()>11)//1到11
        {
            this.addFieldError("roomPwd", "添加失败，密码长度为1到11!");
        }
        else if(roomName.length()<2 || roomName.length()>6)//2到6
        {
            this.addFieldError("roomName", "添加失败，姓名长度为2到6!");
        }
        else if(!roomSex.equals("男") && !roomSex.equals("女"))
        {
            this.addFieldError("roomSex", "添加失败，性别只能为男或女");
        }
        else if(roomPhone.length()!=11){//长11
            this.addFieldError("roomPhone", "添加失败，手机号长度为11!");
        }
    }
}
