package cn.hgy.week3;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
public class Main {

    public static void main(String[] args) {
        Form winForm = new Form("WINDOW窗口");
        // logo
        winForm.addElement(new Element(ElementTypeEnum.PICTURE, "logo"));
        // frame
        Frame frame = new Frame("FRAME1");
        frame.addElement(new Element(ElementTypeEnum.LABEL, "用户名"))
                .addElement(new Element(ElementTypeEnum.TEXT_BOX, "用户名"))
                .addElement(new Element(ElementTypeEnum.LABEL, "密码"))
                .addElement(new Element(ElementTypeEnum.PASSWORD_BOX, "密码"))
                .addElement(new Element(ElementTypeEnum.CHECK_BOX, "复选框"))
                .addElement(new Element(ElementTypeEnum.LABEL, "记住用户名"))
                .addElement(new Element(ElementTypeEnum.LINK_LABEL, "忘记密码"));
        // 添加到form
        winForm.addFrame(frame)
                .addElement(new Element(ElementTypeEnum.BUTTON, "登录"))
                .addElement(new Element(ElementTypeEnum.BUTTON, "注册"));

        System.out.println(winForm);
    }
}
