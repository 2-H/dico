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
        //String builder is better than concatination in performance https://stackoverflow.com/questions/1532461/stringbuilder-vs-string-concatenation-in-tostring-in-java
        StringBuilder sb = new StringBuilder();

        //add package, import ...
        
        //Render Class
        sb.append("public class ")
                .append(model.getName())
                //extends ...
                .append(" {")
                .append("\n\n");

        //Start Render Default Constructer
        sb.append("\t")
                .append("public ")
                .append(model.getName())
                .append("(){\n").append("\t}")
                .append("\n");
        //End Render Default Constructer        

        //Start Render Constructer
        sb.append("\n");
        sb.append("\t")
                .append("public ")
                .append(model.getName())
                .append("(");
        int i = 0;
        for (Attribute atr : model.getAttribute()) {
            i++;
            sb.append(atr.getType())
                    .append(" ")
                    .append(atr.getName());
            if (i < model.getAttribute().size()) {
                sb.append(", ");
            }
        }
        sb.append(") {\n");

        for (Attribute atr : model.getAttribute()) {
            sb.append("\t\t").append("this.").append(atr.getName())
                    .append(" = ")
                    .append(atr.getName())
                    .append(";\n");
        }
        sb.append("\t}\n\n");
        //End Render Constructer

        //Start Render Attributes
        for (Attribute atr : model.getAttribute()) {
            sb.append("\t").append("private ").append(atr.getType())
                    .append(" ")
                    .append(atr.getName())
                    .append(";\n");
        }
        //End Render Attributes

        sb.append("\n}");
        //End Render Class

        return sb.toString();
    }

    public static void main(String[] args) {
//        ClassModel test = new ClassModel();
//        test.setName("Employee");
//        Attribute atr1 = new Attribute("id", Type.INT);
//        Attribute atr2 = new Attribute("name", Type.STRING);
//        Attribute atr3 = new Attribute("gender", Type.STRING);
//        Attribute atr4 = new Attribute("birthday", Type.DATE);
//        ArrayList<Attribute> list = new ArrayList<>();
//        list.add(atr1);
//        list.add(atr2);
//        list.add(atr3);
//        list.add(atr4);
//
//        test.setAttribute(list);
//
//        String text = ClassFactory.create(test);
//        System.out.println(text);

    }
}
