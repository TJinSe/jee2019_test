package course;

import lombok.Getter;
import lombok.Setter;

public class CourseDesign_Bean {
    @Setter @Getter
    String[] columnName;//存放列名
    @Setter @Getter
    String[][] tableRecord=null;//存放查询到的记录
    public CourseDesign_Bean()
    {
        tableRecord=new String[1][1];
        columnName=new String[1];
    }
}
