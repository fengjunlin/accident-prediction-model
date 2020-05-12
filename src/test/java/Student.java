/**
 * @Description
 * @Author fengjl
 * @Date 2019/7/1 14:14
 * @Version 1.0
 **/
public class Student {
    private String Id;
    private String name;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student(String Id, String name) {
        this.Id = Id;
        this.name = name;
    }

}
