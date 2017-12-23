package dico;

import dico.models.ClassModel;

public class ClassFactory {

    /*
    used by GUI to creat class
     */
    public static String create(ClassModel model) {
        StringBuilder sb = new StringBuilder();
        sb.append("public class ")
                .append(model.getName())
                .append(" { \n");

        sb.append("}");
        
        //System.out.println(sb.toString());
        
        return sb.toString();
    }

    public static void main(String[] args) {
        ClassModel test = new ClassModel("Employee");
        ClassFactory.create(test);

    }
}
