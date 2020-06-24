package cn.hgy.week3;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
public class Element {

    private ElementTypeEnum type;
    private String name;

    public Element(ElementTypeEnum type, String name){
        this.name = name;
        this.type = type;
    }

    public ElementTypeEnum getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Component{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
