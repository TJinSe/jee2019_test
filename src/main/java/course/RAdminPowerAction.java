package course;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

public class RAdminPowerAction extends ActionSupport {
    public String execute() throws Exception{
        HttpServletRequest request= ServletActionContext.getRequest();
        String str=request.getParameter("submit");
        if(str.equals("selectStu"))
        {
            return "selectStu";
        }
        else if(str.equals("selectRA"))
        {
            return "selectRA";
        }
        else if(str.equals("updateStu"))
        {
            return "updateStu";
        }
        else if(str.equals("insertStu"))
        {
            return "insertStu";

        }
        return "input";
    }
}
