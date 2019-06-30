package course;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

public class SPAdminPowerAction extends ActionSupport {
    public String execute() throws Exception{
        HttpServletRequest request= ServletActionContext.getRequest();
        String str=request.getParameter("name");
        if(str.equals("insert"))
        {
            return "SAdminInsert";
        }
        else if(str.equals("delete"))
        {
            return "SAdminDelete";
        }
        else if(str.equals("select"))
        {
            return "SAdminSelect";
        }
        else if(str.equals("update"))
        {
            return "SAdminUpdate";
        }
        return "input";
    }
}
