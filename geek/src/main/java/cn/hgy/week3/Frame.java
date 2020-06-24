package cn.hgy.week3;

import java.util.LinkedList;
import java.util.List;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
public class Frame extends Element {

    private List<Element> elements = new LinkedList<>();

    public Frame(String name) {
        super(ElementTypeEnum.FRAME, name);
    }

    public Frame addElement(Element element){
        this.elements.add(element);
        return this;
    }

    @Override
    public String toString() {
        return "Frame{" +
                "elements=" + elements +
                '}';
    }
}
