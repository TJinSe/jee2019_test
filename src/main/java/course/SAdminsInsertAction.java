package course;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

public class SAdminsInsertAction extends ActionSupport {
    public String execute() throws Exception{
        HttpServletRequest request= ServletActionContext.getRequest();
        String str=request.getParameter("sub");
        if(str.equals("insertStu"))
        {
            return "insertStu";
        }
        else if(str.equals("insertRoom"))
        {
            return "insertRAdmin";
        }
        return "input";
    }
}
