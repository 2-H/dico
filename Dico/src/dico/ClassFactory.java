package dico;

import dico.models.Attribute;
import dico.models.ClassModel;
import java.util.ArrayList;

public class ClassFactory {

    public static final ClassFactory Instance = new ClassFactory();

    public static ArrayList<ClassModel> Classess = new ArrayList<>();

    private ClassFactory() {
        Classess = new ArrayList<>();
    }

    public  ArrayList<String> GetClassNames() {
        ArrayList<String> list = new ArrayList<String>();
        for (ClassModel cls : Classess) {
            list.add(cls.getName());
        }
        return list;
    }

    public  ClassModel GetClass(String name) throws ClassNotFoundException {
        for (ClassModel cls : Classess) {
            if(cls.getName().equals(name))
                return cls;
        }
        throw new  ClassNotFoundException("Class Not Found");
    }

    /*
    used by GUI to creat class
     */
    public  String create(ClassModel model) {
        Classess.add(model);
        //String builder is better than concatination in performance 
        StringBuilder sb = new StringBuilder();
        sb.append("package dicodemo; \n\n");

        //add package, import ...
        //Render Class
        sb.append("public class ")
                .append(model.getName());

        //Check if we must implement comparable, in case yes add it to string
       /* boolean comparableFlag = false;
        for (Attribute atr : model.getAttribute()) {
            if (atr.isUseInCompareTo()) {
                comparableFlag = true;
                break;
            }
        }
        if (comparableFlag) {
            //sb.append(" implements Comparable ");
        }*/
        //End check if we must implement comparable

        //extends ...
        sb.append(" {")
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
            sb.append(atr.getType().getName())
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

        //Start Render equals method
        sb.append("\t@Override\n")
                .append("\tpublic boolean equals(Object obj) {\n")
                .append("\t\tif (this == obj) {\n")
                .append("\t\t\t").append("return true;\n\t\t}\n")
                .append("\t\tif (obj == null) {\n")
                .append("\t\t\t").append("return false;\n\t\t}\n")
                .append("\t\t").append("if (getClass() != obj.getClass()) {\n")
                .append("\t\t\t").append("return false;\n\t\t}\n")
                .append("\t\tfinal ").append(model.getName()).append(" other = (").append(model.getName()).append(") obj;\n");

        for (Attribute atr : model.getAttribute()) {
            if (atr.isUseInEquals()) {
                sb.append("\t\t");

                if (!atr.getType().isObject()) {
                    sb.append("if (this.")
                            .append(atr.getName()).append(" != ")
                            .append("other.")
                            .append(atr.getName())
                            .append(") {\n")
                            .append("\t\t\treturn false;\n\t\t}\n");
                } else {
                    sb.append("if (!this.")
                            .append(atr.getName()).append(".equals(")
                            .append("other.")
                            .append(atr.getName())
                            .append(")) {\n")
                            .append("\t\t\treturn false;\n\t\t}\n");
                }
            }

        }
        sb.append("\t\treturn true;\n\t}\n\n");
        //End Render equals method  

        //Add compareTo in case flag is true
        /*sb.append("\t@Override\n")
                .append("public int compareTo(");
        for (Attribute atd : model.getAttribute()) {

        }*/
        //End add compareTo in case flag is true

        //Strart render hashcode
        sb.append("\t@Override\n")
                .append("\tpublic int hashCode() {\n")
                .append("\t\tint prime = 31;\n")
                .append("\t\tint result = 1;\n");
        for (Attribute atr : model.getAttribute()) {
            if (atr.isUseInEquals()) {
                sb.append("\t\tresult = prime * result + (this.")
                        .append(atr.getName());
                if (atr.getType().isObject()) {
                    sb.append(" == null ? 0 : ").append("this.").append(atr.getName()).append(".hashCode());\n");
                } else {
                    sb.append(");\n");
                }
                //.append(" == null) ? 0 : ")
                //.append(atr.getName())

            }
        }

        sb.append("\t\treturn result;\n\t}\n");

        //End rende hashcode
        //Start Render Attributes
        sb.append("\n");
        for (Attribute atr : model.getAttribute()) {
            sb.append("\t").append("private ").append(atr.getType().getName())
                    .append(" ")
                    .append(atr.getName())
                    .append(";\n");
        }
        //End Render Attributes

        sb.append("\n}\n");
        //End Render Class

        return sb.toString();
    }

    public static String CreateDemoClass() {
        ClassModel test = new ClassModel();
        test.setName("Person");
        Attribute atr1 = new Attribute("id", TypesFactory.INT, true, true);
        Attribute atr2 = new Attribute("name", TypesFactory.STRING, true, true);
        Attribute atr3 = new Attribute("gender", TypesFactory.STRING, true, true);
        Attribute atr4 = new Attribute("birthday", TypesFactory.DATE, false, true);
        ArrayList<Attribute> list = new ArrayList<>();
        list.add(atr1);
        list.add(atr2);
        //list.add(atr3);
        //list.add(atr4);

        test.setAttribute(list);

        return ClassFactory.Instance.create(test);
    }

    public static void main(String[] args) {

        System.out.println(CreateDemoClass());

    }
}
