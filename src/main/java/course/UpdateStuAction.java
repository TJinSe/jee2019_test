package course;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;

public class UpdateStuAction extends ActionSupport {
    private String number;
    private String password;
    private String name;
    private String sex;
    private String phone;
    private String room;
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
        number=request.getParameter("number");
        password=request.getParameter("password");
        name=request.getParameter("name");
        sex=request.getParameter("sex");
        phone=request.getParameter("phone");
        room=request.getParameter("room");
        System.out.println(number);
        System.out.println(password);
        String condition="SELECT * from roomStu"+" where RSNumber='"+number+"'";
        System.out.println(condition);

        try {
            //String uri="jdbc:mysql://127.0.0.1/student"+"?"+
              //      "user=root&password=123456&serverTimezone=GMT&characterEncoding=GB2312";
            con=DriverManager.getConnection(dbUrl,dbUsername,dbPassword);//连接数据库
            sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);//获得statement对象
            rs=sql.executeQuery(condition);//查询数据
            int count=0;
            rs.last();//将游标指向结果集最后一行
            count=rs.getRow();//得到行数
            System.out.println(count);
            rs.beforeFirst();//将游标指向结果集第一行之前
            if(count==0)
            {
                javax.swing.JOptionPane.showMessageDialog(null, "该学生不存在!");
            }
            else {
                String str="UPDATE roomStu SET RSPwd='"+password+"',RSName='"+
                        name+"',RSSex='"+sex+"',RSPhone='"+phone+"',RSRoom='"+room+"' "+"WHERE RSNumber='"+number+"'";
                System.out.println("str为"+str);
                sql.executeUpdate(str);
                rs=sql.executeQuery("SELECT * FROM roomstu");
                ResultSetMetaData metaData=rs.getMetaData();
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
            System.out.println("修改失败:"+e.toString());
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
            number=request.getParameter("number");
            password=request.getParameter("password");
            name=request.getParameter("name");
            sex=request.getParameter("sex");
            phone=request.getParameter("phone");
            room=request.getParameter("room");
        }catch (Exception e)
        {
            System.out.println(e);
        }
        System.out.println(number);
        System.out.println(password);
        System.out.println(name);
        System.out.println(sex);
        System.out.println(phone);
        if(number.length()<1 || number.length()>=15 )//1到15
        {
            this.addFieldError("number", "账号长度为1到15！");
        }
        else if(password.length()<1 || password.length()>11)//1到11
        {
            this.addFieldError("password", "密码长度为1到11!");
        }
        else if(name.length()<2 || name.length()>6)//2到6
        {
            this.addFieldError("name", "姓名长度为2到6!");
        }
        else if(!sex.equals("男") && !sex.equals("女"))
        {
            this.addFieldError("sex", "性别只能为男或女");
        }
        else if(phone.length()!=11){//长11
            this.addFieldError("phone", "手机号长度为11!");
        }
        else if(room.length()<4 || room.length()>8)//4到8
        {
            this.addFieldError("room", "房间号长度为4到8!");
        }
    }
}
