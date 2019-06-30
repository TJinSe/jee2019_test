package course;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class RegisterServlet extends ActionSupport {
    public String execute() throws Exception {
        //1、用ServletActionContext获取request
        HttpServletRequest request=ServletActionContext.getRequest();
        //2、调用request里面的方法得到结果
        String person=request.getParameter("person");
        System.out.println("得到的数字为:"+person);
        if(person.equals("4"))
        {
            return "suc4";
        }
        else if(person.equals("5"))
        {
            return "suc5";
        }
        else
        {
            return "suc6";
        }
    }
}
