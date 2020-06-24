package cn.hgy.week3;

import java.util.LinkedList;
import java.util.List;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
public class Form extends Element {

    private List<Frame> frames = new LinkedList<>();
    private List<Element> elements = new LinkedList<>();

    public Form(String name) {
        super(ElementTypeEnum.WIN_FORM, name);
    }


    public Form addFrame(Frame frame) {
        this.frames.add(frame);
        return this;
    }

    public Form addElement(Element element) {
        this.elements.add(element);
        return this;
    }

    @Override
    public String toString() {
        return "Form{" +
                "frames=" + frames +
                ", elements=" + elements +
                '}';
    }
}
