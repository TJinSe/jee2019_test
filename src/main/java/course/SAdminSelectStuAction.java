package course;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;

public class SAdminSelectStuAction extends ActionSupport {
    private int found;

    Connection con;
    Statement sql;
    ResultSet rs;

    String dbDriver="org.hsqldb.jdbcDriver";
    String dbUrl="jdbc:hsqldb:hsql://localhost";
    String dbUsername="sa";
    String dbPassword="";

    public String execute() throws Exception{
        found=0;//表示失败
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
        String condition="SELECT * from roomstu";
        System.out.println(condition);

        try {
            //String uri="jdbc:mysql://127.0.0.1/student"+"?"+
              //      "user=root&password=123456&serverTimezone=GMT&characterEncoding=GB2312";
            con= DriverManager.getConnection(dbUrl,dbUsername,dbPassword);//连接数据库
            sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);//获得statement对象
            rs = sql.executeQuery(condition);// 查询数据
            ResultSetMetaData metaData=rs.getMetaData();//得到结果集的元数据对象
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
        catch(SQLException e)
        {
            found=2;
            System.out.println("管理员查看学生信息失败:"+e.toString());
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
}
