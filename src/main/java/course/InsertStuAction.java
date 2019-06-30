package course;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;

public class InsertStuAction extends ActionSupport {
    private int found;
    private String stuID;
    private String stuPwd;
    private String stuName;
    private String stuSex;
    private String stuPhone;
    private String stuRoom;

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
       // String dataBase=request.getParameter("dataBase");
       // String tableName=request.getParameter("tableName");
        stuID=request.getParameter("stuID");
        stuPwd=request.getParameter("stuPwd");
        stuName=request.getParameter("stuName");
        stuSex=request.getParameter("stuSex");
        stuPhone=request.getParameter("stuPhone");
        stuRoom=request.getParameter("stuRoom");
        System.out.println(stuID);
        System.out.println(stuPwd);
        String condition="SELECT * from roomStu"+" where RSNumber='"+stuID+"'";
        System.out.println(condition);

        try {
            //String uri="jdbc:mysql://127.0.0.1/student"+"?"+
            //        "user=root&password=123456&serverTimezone=GMT&characterEncoding=GB2312";
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
                javax.swing.JOptionPane.showMessageDialog(null, "该学生已存在!");
            }
            else {
                String str = "INSERT INTO roomstu" + " VALUES" +
                        "(" + "'" + stuID + "','" + stuPwd + "','" +stuName+"','"+stuSex+"','"+stuPhone+"','"+stuRoom+ "')";
                System.out.println("str为" + str);
                sql.executeUpdate(str);// 添加数据
                rs = sql.executeQuery("SELECT * FROM roomstu");// 得到结果集
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
            }
        }
        catch(SQLException e)
        {
            found=2;
            System.out.println("添加学生失败:"+e.toString());
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
            stuID = request.getParameter("stuID");
            stuPwd = request.getParameter("stuPwd");
            stuName = request.getParameter("stuName");
            stuSex = request.getParameter("stuSex");
            stuPhone = request.getParameter("stuPhone");
            stuRoom = request.getParameter("stuRoom");
        }catch (Exception e)
        {
            System.out.println(e);
        }
        System.out.println(stuID);
        System.out.println(stuName);
        System.out.println(stuSex);
        System.out.println(stuPwd);
        System.out.println(stuPhone);
        if(stuID.length()<1 || stuID.length()>=15 )//1到15
        {
            this.addFieldError("stuID", "账号长度为1到15！");
        }
        else if(stuPwd.length()<1 || stuPwd.length()>11)//1到11
        {
            this.addFieldError("stuPwd", "密码长度为1到11!");
        }
        else if(stuName.length()<2 || stuName.length()>6)//2到6
        {
            this.addFieldError("stuName", "姓名长度为2到6!");
        }
        else if(!stuSex.equals("男") && !stuSex.equals("女"))
        {
            this.addFieldError("stuSex", "性别只能为男或女");
        }
        else if(stuPhone.length()!=11){//长11
            this.addFieldError("stuPhone", "手机号长度为11!");
        }
        else if(stuRoom.length()<4 || stuRoom.length()>8)//4到8
        {
            this.addFieldError("stuRoom", "房间号长度为4到8!");
        }
    }
}
