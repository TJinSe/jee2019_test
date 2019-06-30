package course;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

public class SAdminUpdatePower extends ActionSupport {
    public String execute() throws Exception{
        HttpServletRequest request= ServletActionContext.getRequest();
        String str=request.getParameter("update");
        if(str.equals("updateStu"))
        {
            return "updateStu";
        }
        else if(str.equals("updateRAdmin"))
        {
            return"updateRAdmin";
        }
        return "input";
    }
}
