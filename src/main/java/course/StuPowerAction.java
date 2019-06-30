package course;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

public class StuPowerAction extends ActionSupport {
    public String execute() throws Exception{
        HttpServletRequest request= ServletActionContext.getRequest();
        String str=request.getParameter("submit");
        if(str.equals("select"))
        {
            return "stuSelect";
        }
        else if(str.equals("update"))
        {
            return "stuUpdateMe";
        }
        return "input";
    }
}
