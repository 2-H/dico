package dico;

import dico.exceptions.DicoClassNotFoundException;
import dico.exceptions.DuplicateAttributesException;
import dico.exceptions.DuplicateClassesException;
import dico.models.Attribute;
import dico.models.ClassModel;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClassFactory {

    public static final ClassFactory Instance = new ClassFactory();

    public static ArrayList<ClassModel> Classess;

    private ClassFactory() {
        Classess = new ArrayList<>();
    }

    public ArrayList<String> GetClassNames() {
        ArrayList<String> list = new ArrayList<String>();
        for (ClassModel cls : Classess) {
            list.add(cls.getName());
        }
        return list;
    }

    public ClassModel GetClass(String name) throws DicoClassNotFoundException {
        for (ClassModel cls : Classess) {
            if (cls.getName().equals(name)) {
                return cls;
            }
        }
        throw new DicoClassNotFoundException("Class Not Found");
    }

    public boolean CheckClass(String name) {
        for (ClassModel cls : Classess) {
            if (cls.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void addDefaultConstructor(ClassModel model, StringBuilder sb) {
        //Start Render Default Constructer
        sb.append("\t")
                .append("public ")
                .append(model.getName())
                .append("(){\n");

        if (model.getParent() != null) {
            sb.append("\tsuper();\n");
        }
        sb.append("\t}")
                .append("\n");
        //End Render Default Constructer    
    }

    public void addConstructorSuperAttributes(ClassModel model, StringBuilder sb) {
        if (model.getParent() != null) {
            addConstructorSuperAttributes(model.getParent(), sb);
        }
        if (model.getAttribute().size() > 0) {
            if (model.getParent() != null) {
                sb.append(", ");
            }
            int i = 0;
            for (Attribute atr : model.getAttribute()) {
                i++;
                sb.append(atr.getName());
                if (i < model.getAttribute().size()) {
                    sb.append(", ");
                }
            }
        }
    }

    public void addConstructorParameters(ClassModel model, StringBuilder sb) {
        if (model.getParent() != null) {
            addConstructorParameters(model.getParent(), sb);
        }
        if (model.getAttribute().size() > 0) {
            if (model.getParent() != null) {
                sb.append(", ");
            }
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
        }
    }

    public void addConstructor(ClassModel model, StringBuilder sb) {
        //Start Render Constructer
        if (model.getAttribute().size() > 0) {
            sb.append("\n");
            sb.append("\t")
                    .append("public ")
                    .append(model.getName())
                    .append("(");

            addConstructorParameters(model, sb); //(id,name...)
            sb.append(") {\n");

            if (model.getParent() != null) {
                sb.append("\t\tsuper(");
                addConstructorSuperAttributes(model.getParent(), sb); //(id,name...)
                sb.append(");\n");
            }

            for (Attribute atr : model.getAttribute()) {
                sb.append("\t\t").append("this.").append(atr.getName())
                        .append(" = ")
                        .append(atr.getName())
                        .append(";\n");
            }
            sb.append("\t}\n\n");
        }
        //End Render Constructer

    }

    public boolean superImplementsComparable(ClassModel model) {
        //If one of the super classes implements comparable no need for writing 'implements Comparable' another time
        model = model.getParent();
        while (model != null) {
            for (Attribute atr : model.getAttribute()) {
                if (atr.isUseInCompareTo()) {
                    return true;
                }
            }
            model = model.getParent();
        }
        return false;
    }

    public void addComparable(ClassModel model, StringBuilder sb) {
        //If non of super classes implements comparable -> add 'implements Comparable'
        if (!superImplementsComparable(model)) {
            sb.append(" implements Comparable ");
        }
    }

    public void addCompareTo(ClassModel model, StringBuilder sb) {
        sb.append("\t@Override\n")
                .append("\tpublic int compareTo(Object other) {\n")
                .append("\t\tif(getClass() != other.getClass()) {\n")
                .append("\t\t\tthrow new ClassCastException();\n")
                .append("\t\t}\n")
                .append("\t\t").append(model.getName()).append(" tmp = (").append(model.getName()).append(") other;\n");
        for (Attribute atr : model.getAttribute()) {
            if (atr.isUseInCompareTo()) {
                if (atr.getType().isObject()) {
                    sb.append("\t\tif (!this.").append(atr.getName()).append(".equals(tmp.").append(atr.getName()).append(")) {\n")
                            .append("\t\t\treturn this.").append(atr.getName()).append(".compareTo(tmp.").append(atr.getName()).append(");\n")
                            .append("\t\t}\n");
                } else {
                    sb.append("\t\tif (!(this.").append(atr.getName()).append(" == tmp.").append(atr.getName()).append(")) {\n")
                            .append("\t\t\treturn this.").append(atr.getName()).append(" - tmp.").append(atr.getName()).append(";\n")
                            .append("\t\t}\n");
                }
            }
        }
        if (superImplementsComparable(model)) {
            sb.append("\t\treturn super.compareTo(tmp);\n");
        } else {
            sb.append("\t\treturn 0;\n");
        }
        sb.append("\t}\n\n");
    }

    public void addHashCode(ClassModel model, StringBuilder sb) {
        //Strart render hashcode
        sb.append("\t@Override\n")
                .append("\tpublic int hashCode() {\n")
                .append("\t\tint prime = 31;\n");
        if (model.getParent() != null) {
            sb.append("\t\tint result = super.hashCode();\n");
        } else {
            sb.append("\t\tint result = 1;\n");
        }
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

        //End render hashcode 
    }

    public void addToString(ClassModel model, StringBuilder sb) {
        //Start render toString method
        sb.append("\t@Override\n")
                .append("\tpublic String toString() {\n")
                .append("\t\treturn ");
        if (model.getParent() != null) {
            sb.append("super.toString()");
        } else {
            sb.append("getClass().getName()");
        }
        if (model.getAttribute().size() > 0) {
            sb.append(" +\"[");
        }
        int j = 0;

        for (Attribute atr : model.getAttribute()) {
            j++;
            sb.append(atr.getName()).append("=\" + ").append(atr.getName()).append(" + \"");
            if (j < model.getAttribute().size()) {
                sb.append(", ");
            }
        }
        if (model.getAttribute().size() > 0) {
            sb.append("]\"");
        }
        sb.append(";");

        sb.append("\t\t\n\t}\n");

        //End rendering toString method
    }

    public void addEquals(ClassModel model, StringBuilder sb) {
        //Start Render equals method
        sb.append("\t@Override\n")
                .append("\tpublic boolean equals(Object obj) {\n");

        if (model.getParent() == null) {
            sb.append("\t\tif (this == obj) {\n")
                    .append("\t\t\t").append("return true;\n\t\t}\n")
                    .append("\t\tif (obj == null) {\n")
                    .append("\t\t\t").append("return false;\n\t\t}\n")
                    .append("\t\t").append("if (getClass() != obj.getClass()) {\n")
                    .append("\t\t\t").append("return false;\n\t\t}\n");
        } else {
            sb.append("\t\tif (!super.equals(obj)) {\n")
                    .append("\t\t\treturn false;\n\t\t}\n");
        }

        sb.append("\t\tfinal ").append(model.getName()).append(" other = (").append(model.getName()).append(") obj;\n");

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
    }

    public void addAttributes(ClassModel model, StringBuilder sb) {
        //Start Render Attributes
        sb.append("\n");
        for (Attribute atr : model.getAttribute()) {
            sb.append("\t").append("private ").append(atr.getType().getName())
                    .append(" ")
                    .append(atr.getName())
                    .append(";\n");
        }
        //End Render Attributes
    }

    public ClassModel GetExisitingClass(String name) {
        for (ClassModel cm1 : ClassFactory.Instance.Classess) {
            if (cm1.getName().equals(name)) {
                return cm1;
            }
        }
        return null;
    }

    public void checkValidity(ClassModel model) throws DuplicateAttributesException, DuplicateClassesException {
        if (GetExisitingClass(model.getName()) != null) {
            throw new DuplicateClassesException(model.getName() + " exists before ");
        }

        if (model.getParent() != null) {
            ArrayList<Attribute> attributesWithSuper = model.getParent().getAttributesWithSuper();
            for (Attribute atd : attributesWithSuper) {
                for (Attribute atd2 : model.getAttribute()) {
                    if (atd2.getName().equals(atd.getName())) {
                        throw new DuplicateAttributesException(atd2.getName() + " exists in the super class ");
                    }
                }
            }
        }
        int count = 0;
        for (Attribute atd : model.getAttribute()) {
            for (Attribute atd2 : model.getAttribute()) {
                if (atd2.getName().equals(atd.getName())) {
                    count++;
                    if (count > 1) {
                        throw new DuplicateAttributesException(atd2.getName() + " is a duplicate attribute .");
                    }
                }
            }
            count = 0;
        }
    }

    public boolean mustAddComparable(ClassModel model) {
        for (Attribute atr : model.getAttribute()) {
            if (atr.isUseInCompareTo()) {
                return true;
            }
        }
        return false;
    }

    /*
    used by GUI to creat class
     */
    public void generateJavaCode(ClassModel model) throws DuplicateAttributesException, DuplicateClassesException {
        //String builder is better than concatination in performance 
        checkValidity(model);
        StringBuilder sb = new StringBuilder();
        sb.append("package dicodemo; \n\n");

        //Render Class
        sb.append("public class ")
                .append(model.getName());
        if (model.getParent() != null) {
            sb.append(" extends ")
                    .append(model.getParent().getName());

        }
        if (mustAddComparable(model)) {
            addComparable(model, sb);
        }
        sb.append(" {")
                .append("\n\n");
        addDefaultConstructor(model, sb);
        addConstructor(model, sb);
        addEquals(model, sb);
        if (mustAddComparable(model)) {
            addCompareTo(model, sb);
        }
        addToString(model, sb);
        addAttributes(model, sb);

        sb.append("\n}\n");
        //End Render Class

        model.setJavaCode(sb.toString());
        Classess.add(model);
    }

    public static void Demo() {
        try {
            ClassModel person = new ClassModel();
            person.setName("Person");
            Attribute atrId = new Attribute("id", TypesFactory.INT, true, true);
            //atrId.setValue(1);
            Attribute atrName = new Attribute("name", TypesFactory.STRING, true, true);
            //atrName.setValue("ali");

            ArrayList<Attribute> list = new ArrayList<>();
            list.add(atrId);
            list.add(atrName);

            person.setAttribute(list);
            ClassFactory.Instance.generateJavaCode(person);
            System.out.println(person.getJavaCode());

            ClassModel manager = new ClassModel();
            manager.setName("Manager");
            Attribute atrSalary = new Attribute("salary", TypesFactory.DOUBLE, true, true);
            //atrSalary.setValue(1500.25);
            ArrayList<Attribute> list2 = new ArrayList<>();
            list2.add(atrSalary);
            manager.setAttribute(list2);
            manager.setParent(person);
            ClassFactory.Instance.generateJavaCode(manager);
            System.out.println(manager.getJavaCode());
            System.out.println(Arrays.toString(manager.getAttributesWithSuper().toArray()));

            ClassModel cto = new ClassModel();
            cto.setName("CTO");
            Attribute atrYears = new Attribute("yearsOfExperience", TypesFactory.INT, true, true);
            //atrYears.setValue(8);
            ArrayList<Attribute> list3 = new ArrayList<>();
            list3.add(atrYears);
            cto.setAttribute(list3);
            cto.setParent(manager);
            ClassFactory.Instance.generateJavaCode(cto);
            System.out.println(cto.getJavaCode());

            System.out.println(Arrays.toString(cto.getAttributesWithSuper().toArray()));
        } catch (DuplicateAttributesException | DuplicateClassesException ex) {
            System.out.println(ex.toString());
        }

    }

    public static void main(String[] args) {

        Demo();

    }

    private Exception DuplicateAttributesException(String unaccepted_attribute_name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
