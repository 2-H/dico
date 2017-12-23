package dico;

import dico.models.Attribute;
import dico.models.ClassModel;
import dico.models.Type;
import java.util.ArrayList;

public class ClassFactory {

    /*
    used by GUI to creat class
     */
    public static String create(ClassModel model) {
        StringBuilder sb = new StringBuilder();
        sb.append("public class ")
                .append(model.getName())
                .append(" { \n")
                .append("\n");

        for (Attribute atr : model.getAttribute()) {
            sb.append("\t").append("private ").append(atr.getType())
                    .append(" ")
                    .append(atr.getName())
                    .append(";\n");
        }

        sb.append("\n}");
        return sb.toString();
    }

    public static void main(String[] args) {
        ClassModel test = new ClassModel();
        test.setName("Employee");
        Attribute atr1 = new Attribute("id", Type.INT);
        Attribute atr2 = new Attribute("name", Type.STRING);
        Attribute atr3 = new Attribute("gender", Type.STRING);
        Attribute atr4 = new Attribute("birthday", Type.DATE);
        ArrayList<Attribute> list = new ArrayList<>();
        list.add(atr1);
        list.add(atr2);
        list.add(atr3);
        list.add(atr4);

        test.setAttribute(list);

        String text = ClassFactory.create(test);
        System.out.println(text);

    }
}
