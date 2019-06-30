package course;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.*;
import javax.servlet.http.*;

public class StuSetServlet extends ActionSupport{
    private String stuID;//1到15位
    private String stuPwd;//1到11位
    private String stuName;//2到6位
    private String stuSex;//1位
    private String stuPhone;//11位
    private String stuRoom;//4到8位
    private int fack;

    String dbDriver="org.hsqldb.jdbcDriver";
    String dbUrl="jdbc:hsqldb:hsql://localhost";
    String dbUsername="sa";
    String dbPassword="";
    public String execute() throws Exception {

        fack=0;//表示失败
        HttpServletRequest request=ServletActionContext.getRequest();
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
        stuID=request.getParameter("stuID");
        stuPwd=request.getParameter("stuPwd");
        stuName=request.getParameter("stuName");
        stuSex=request.getParameter("stuSex");
        stuPhone=request.getParameter("stuPhone");
        stuRoom=request.getParameter("stuRoom");
        System.out.println(stuID);
        System.out.println(stuName);
        String condition="SELECT * from roomStu"+" where RSNumber='"+stuID+"'";
        System.out.println(condition);
        Connection con;
        Statement sql;
        ResultSet rs;
        try {
            //String uri="jdbc:mysql://localhost/student"+"?"+
                    //"user=root&password=123456&serverTimezone=GMT&characterEncoding=GB2312";
            con=DriverManager.getConnection(dbUrl,dbUsername,dbPassword);//连接数据库
            sql=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//获得statement对象
            rs=sql.executeQuery(condition);//查询数据
            int count=0;
            rs.last();//将游标指向最后一行
            count=rs.getRow();//得到行数
            rs.beforeFirst();//将游标指向第一行之前
            if(count!=0)
                javax.swing.JOptionPane.showMessageDialog(null, "该学生已注册!");
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
                int rowNumber = rs.getRow();// 得到行数
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

                fack=1;//表示插入成功
            }
        }
        catch(SQLException e)
        {
            fack=2;
            System.out.println("学生注册失败！");
            System.out.println(e);
        }
        if(fack==1)//账号存在
        {
            return SUCCESS;
        }
        else if(fack==2)//注册失败
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

            //javax.swing.JOptionPane.showMessageDialog(null, "账号不空!");
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
