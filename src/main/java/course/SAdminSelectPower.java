package course;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

public class SAdminSelectPower extends ActionSupport {
    public String execute() throws Exception{
        HttpServletRequest request= ServletActionContext.getRequest();
        String str=request.getParameter("select");
        if(str.equals("selectStu"))
        {
            return "selectStu";
        }
        else if(str.equals("selectRAdmins"))
        {
            return "SASelectRAdmins";
        }
        else if(str.equals("selectSAdmins"))
        {
            return "selectSAdmins";
        }
        return "input";
    }
}
