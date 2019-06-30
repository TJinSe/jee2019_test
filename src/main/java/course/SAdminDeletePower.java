package course;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

public class SAdminDeletePower extends ActionSupport {
    public String execute() throws Exception{
        HttpServletRequest request= ServletActionContext.getRequest();
        String str=request.getParameter("delete");
        if(str.equals("deleteStu"))
        {
            return "deleteStu";
        }
        else if(str.equals("deleteRoom"))
        {
            return "deleteRoom";
        }
        return "input";
    }
}
