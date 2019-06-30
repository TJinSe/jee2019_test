package course;

import java.sql.*;
import javax.servlet.http.*;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport{
    private String ID;//登录账号
    private String password;//登录密码
    private String person;//登录身份
    private int found;

    Connection con;
    Statement sql;
    ResultSet rs;
    String dbDriver="org.hsqldb.jdbcDriver";
    String dbUrl="jdbc:hsqldb:hsql://localhost";
    String dbUsername="sa";
    String dbPassword="";

    @Override
    public String execute() throws Exception {
        found=0;
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
            found=2;
            System.out.println("驱动异常异常");
        }
        request.setCharacterEncoding("UTF-8");
        ID=request.getParameter("ID");//登录账号
        password=request.getParameter("password");//登录密码
        person=request.getParameter("name");//登录身份
        System.out.println(ID);
        System.out.println(password);
        System.out.println(person);
        if(person.equals("admins"))//管理员登陆
        {
            String condition="SELECT * from superAdmins where SANumber='"+ID+"'";
            System.out.println(condition);
            try {
                //String uri="jdbc:hsqldb:hsql://localhost"+"?"+
                //        "user=sa&password= &serverTimezone=GMT&characterEncoding=GB2312";
                con=DriverManager.getConnection(dbUrl,dbUsername,dbPassword);//连接数据库
                sql=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//获得statement对象
                rs=sql.executeQuery(condition);//查询数据
                //ResultSetMetaData metaData=rs.getMetaData();//得到结果集的元数据对象
                int count=0;
                rs.last();//将游标指向最后一行
                count=rs.getRow();//得到行数
                System.out.println("管理员的count为 "+count);
                rs.beforeFirst();//将游标指向第一行之前
                out:
                if(count!=0)//数据库中查到该管理员
                {
                    int fack=0;
                    while(rs.next())//检验密码是否正确
                    {
                        fack++;
                        String pass=rs.getString("SAPwd");
                        if(pass.equals(password))//正确
                        {
                            con.close();
                            found=1;
                            break out;
                        }
                    }
                    if(fack==count)//未查到密码即错误
                    {
                        con.close();
                        javax.swing.JOptionPane.showMessageDialog(null, "密码错误!");
                    }
                }
                else {
                    javax.swing.JOptionPane.showMessageDialog(null, "尚未注册!");
                }
            }
            catch(SQLException e)
            {
                found=2;
                System.out.println("登录失败:"+e.toString());
            }
        }
        else if(person.equals("room"))//宿管登录
        {
            String condition="SELECT * from roomAdmins where RANumber='"+ID+"'";
            System.out.println(condition);

            try {
                //String uri="jdbc:hsqldb:hsql://localhost"+"?"+
                 //       "user=sa&password= &serverTimezone=GMT&characterEncoding=GB2312";
                con=DriverManager.getConnection(dbUrl,dbUsername,dbPassword);//连接数据库
                sql=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//获得statement对象
                rs=sql.executeQuery(condition);//查询数据
                int count=0;
                rs.last();
                count=rs.getRow();
                System.out.println("宿管的count为"+count);
                rs.beforeFirst();//将游标移到结果集初始位置
                out:
                if(count!=0)//数据库中查到该宿管
                {
                    int fack=0;
                    while(rs.next())//检验密码是否正确
                    {
                        fack++;
                        String pass=rs.getString("RAPwd");
                        if(pass.equals(password))//正确
                        {
                            con.close();
                            found=4;
                            break out;
                        }
                    }
                    System.out.println(fack);
                    if(fack==count)//未查到密码即错误
                    {
                        //默认found=0;
                        con.close();
                        javax.swing.JOptionPane.showMessageDialog(null, "密码错误!");
                        //response.sendRedirect("courseLogin.jsp");
                    }
                }
                else {
                    //默认found=0;
                    javax.swing.JOptionPane.showMessageDialog(null, "尚未注册!");
                }
                System.out.println("123没事了");
            }
            catch(SQLException e)
            {
                found=3;
                System.out.println("宿管登录失败:"+e.toString());
            }
        }
        else if(person.equals("student"))//学生登录
        {
            request.setAttribute("ID", ID);
            String condition="SELECT * from roomStu where RSNumber='"+ID+"'";
            System.out.println(condition);
            Connection con;
            Statement sql;
            ResultSet rs;
            HttpSession session=request.getSession(true);
            try {
                //String uri="jdbc:hsqldb:hsql://localhost"+"?"+
                  //      "user=sa&password= &serverTimezone=GMT&characterEncoding=GB2312";
                con=DriverManager.getConnection(dbUrl,dbUsername,dbPassword);//连接数据库
                sql=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//获得statement对象
                rs=sql.executeQuery(condition);//查询数据
                //ResultSetMetaData metaData=rs.getMetaData();//得到结果集的元数据对象
                int count=0;
                rs.last();
                count=rs.getRow();
                System.out.println("学生的count为"+count);
                rs.beforeFirst();
                out:
                if(count!=0)//数据库中查到该学生
                {
                    int fack=0;
                    while(rs.next())//检验密码是否正确
                    {
                        fack++;
                        String pass=rs.getString("RSPwd");
                        System.out.println("学生密码为 "+pass);
                        if(pass.equals(password))//正确
                        {
                            con.close();
                            session.setAttribute("ID", ID);
                            found=5;
                            break out;
                        }
                    }
                    if(fack==count)//未查到密码即错误
                    {
                        con.close();
                        javax.swing.JOptionPane.showMessageDialog(null, "密码错误!");
                    }
                }
                else {
                    javax.swing.JOptionPane.showMessageDialog(null, "尚未注册!");
                }
            }
            catch(SQLException e)
            {
                found=6;
                System.out.println("学生登录失败:"+e.toString());
            }
        }
        if(found==1)//账号存在
        {
            return "adminsSuc";//管理员登陆成功
        }
        else if(found==2)//注册失败
        {
            return "adminsFail";//转向失败页面
        }
        else if(found==3){
            return "roomFail";//转向到宿管登录失败页面
        }
        else if(found==4){
            return "roomSuc";//宿管登陆成功
        }
        else if(found==5){
            return "stuSuc";//学生登录成功
        }
        else if(found==6){
            return "stuFail";//学生登录失败
        }
        return "input";
    }

    @Override
    public void validate(){
        super.validate();
        HttpServletRequest request=ServletActionContext.getRequest();
        try {
            request.setCharacterEncoding("UTF-8");
            ID=request.getParameter("ID");
            password=request.getParameter("password");
            person=request.getParameter("name");
        }catch (Exception e)
        {
            System.out.println(e);
        }
        System.out.println(ID);
        System.out.println(password);
        System.out.println(person);
        if(ID.length()<1 || ID.length()>=15 )//1到15
        {
            this.addFieldError("ID", "账号长度为1到15！");
        }
        else if(password.length()<1 || password.length()>11)//1到11
        {
            this.addFieldError("password", "密码长度为1到11!");
        }
        else if(person==null)//2到6
        {
            this.addFieldError("name", "登录请选择你的身份！");
        }
    }
}
