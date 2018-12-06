package tsou.cn.lib_base.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/6 0006.
 */

public class JavaBean implements Serializable{

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
