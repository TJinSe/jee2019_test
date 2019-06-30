package course;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;

public class RoomAdminServlet extends ActionSupport {
    private String roomID;
    private String roomPwd;
    private String roomName;
    private String roomSex;
    private String roomPhone;
    private int fack;

    Connection con;
    Statement sql;
    ResultSet rs;

    String dbDriver="org.hsqldb.jdbcDriver";
    String dbUrl="jdbc:hsqldb:hsql://localhost";
    String dbUsername="sa";
    String dbPassword="";
    public String execute() throws Exception{
        fack=0;//表示失败
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
            fack=2;
            System.out.println("驱动异常异常");
        }
        request.setCharacterEncoding("UTF-8");
        roomID=request.getParameter("roomID");
        roomPwd=request.getParameter("roomPwd");
        roomName=request.getParameter("roomName");
        roomSex=request.getParameter("roomSex");
        roomPhone=request.getParameter("roomPhone");
        System.out.println(roomID);
        System.out.println(roomPwd);
        String condition="SELECT * from roomadmins"+" where RANumber='"+roomID+"'";
        System.out.println(condition);

        try {
            //String uri="jdbc:mysql://127.0.0.1/student"+"?"+
              //      "user=root&password=123456&serverTimezone=GMT&characterEncoding=GB2312";
            con=DriverManager.getConnection(dbUrl,dbUsername,dbPassword);//连接数据库
            sql=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//获得statement对象
            rs=sql.executeQuery(condition);//查询数据
            int count=0;
            rs.last();//将游标指向最后一行
            count=rs.getRow();//得到行数
            rs.beforeFirst();//将游标指向第一行之前
            if(count!=0)
            {
                javax.swing.JOptionPane.showMessageDialog(null, "该宿管员已存在!");
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
                fack=1;
                //RequestDispatcher dispatcher = request.getRequestDispatcher("showRecord.jsp");
                //dispatcher.forward(request, response);
            }
        }
        catch(SQLException e)
        {
            fack=2;
            System.out.println("添加记录失败:"+e.toString());
        }
        if(fack==1)//账号存在
        {
            return SUCCESS;//显示所有数据页面
        }
        else if(fack==2)//注册失败
        {
            return "fail";//转向失败页面
        }
        return "input";//fack=0,返回注册页面
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
            this.addFieldError("roomID", "账号长度为1到15！");
        }
        else if(roomPwd.length()<1 || roomPwd.length()>11)//1到11
        {
            this.addFieldError("roomPwd", "密码长度为1到11!");
        }
        else if(roomName.length()<2 || roomName.length()>6)//2到6
        {
            this.addFieldError("roomName", "姓名长度为2到6!");
        }
        else if(!roomSex.equals("男") && !roomSex.equals("女"))
        {
            this.addFieldError("roomSex", "性别只能为男或女");
        }
        else if(roomPhone.length()!=11){//长11
            this.addFieldError("roomPhone", "手机号长度为11!");
        }
    }
}
