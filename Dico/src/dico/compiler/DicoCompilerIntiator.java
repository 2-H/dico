/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.compiler;

/**
 *
 * @author Me
 */
import dico.ClassFactory;
import dico.ObjectFactory;
import static dico.ObjectFactory.Objects;
import dico.exceptions.ComplierFailedException;
import dico.exceptions.ObjectCreationException;
import dico.models.Attribute;
import dico.models.ClassModel;
import dico.models.ObjectModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
//https://stackoverflow.com/questions/21544446/how-do-you-dynamically-compile-and-load-external-java-classes

public class DicoCompilerIntiator {

    private static final HashMap<String, Class> classess = new HashMap();

    public static final DicoCompilerIntiator Instance = new DicoCompilerIntiator();
    public URLClassLoader Loader;

    private DicoCompilerIntiator() {
    }

    public File createJavaFiles(String filename, String javaCode) {
        File javaFile = new File("dicodemo/" + filename + ".java");
        if (javaFile.getParentFile().exists() || javaFile.getParentFile().mkdirs()) {
            Writer writer = null;
            try {
                writer = new FileWriter(javaFile);
                writer.write(javaCode);
                writer.flush();
            } catch (IOException ex) {
                Logger.getLogger(DicoCompilerIntiator.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                }
            }
        }
        return javaFile;
    }

    public Class getClassByReflection(String className) throws ClassNotFoundException {
        Class reflectionClass = (Class) classess.get(className);
        if (reflectionClass == null) {
            ClassLoader loader = getClassLoader();
            reflectionClass = loader.loadClass("dicodemo." + className);
            classess.put(className, reflectionClass);
            System.out.println("Creating new Class " + className);

        } else {
            System.out.println("Read from Hashmap " + className);
        }
        return reflectionClass;
    }

    public ObjectModel createObject(ClassModel model, String variablename) throws ObjectCreationException {

        ObjectModel objectModel = new ObjectModel();
        objectModel.setVariableName(variablename);
        objectModel.setClassModel(model);

        try {

            Class thisClass = getClassByReflection(model.getName());

            objectModel.setClassReference(thisClass);

            ArrayList<Class> constructorParamsTypes = new ArrayList<>();
            for (Attribute atr : model.getAttributesWithSuper()) {
                constructorParamsTypes.add(atr.getType().getClassName());
            }
            // Class[] params =new {int.class,String.class} id name
            Class[] paramsTypes = constructorParamsTypes.toArray(new Class[constructorParamsTypes.size()]);
            //System.out.println(Arrays.toString(params));

            //Object instance = thisClass.newInstance();
            Constructor constructor = thisClass.getConstructor(paramsTypes);

            ArrayList<Object> constructorParamValues = new ArrayList<>();
            for (Attribute atr : model.getAttributesWithSuper()) {
                constructorParamValues.add(atr.getValue());
            }

            //Object ali = constructor.newInstance(1, "ali");
            Object[] paramsValues = constructorParamValues.toArray(new Object[constructorParamValues.size()]);

            Object instance = constructor.newInstance(paramsValues);
            objectModel.setInstance(instance);

            //Class paramsMethold[] = {Object.class};//thisClass
            //Method equalsMethold = thisClass.getDeclaredMethod("equals", paramsMethold);
            // run the equals() method on the instance:
            //Object o = equalsMethold.invoke(ali, kassem);
            //System.out.println("ali.equals(kassem) ==> " + (boolean) o);
        } catch (Exception ex) {
            throw new ObjectCreationException(ex.getMessage());
        }
        return objectModel;
    }

    public String InvokeToString(ObjectModel object) {
        try {
            Method method = object.getClassReference().getDeclaredMethod("toString", null);
            Object str = method.invoke(object.getInstance());
            String text = str.toString();
            System.out.println(text);
            return text;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "error";
    }

    public boolean InvokeEquals(ObjectModel object1, ObjectModel object2) {
        try {
            Class paramsMethold[] = {Object.class};//thisClass
            Method equalsMethod = object1.getClassReference().getDeclaredMethod("equals", paramsMethold);
            Object o = equalsMethod.invoke(object1.getInstance(), object2.getInstance());
            System.out.println("object1.equals(object2) ==> " + (boolean) o);
            return (boolean) o;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean CompileFiles(ArrayList<File> javaFiles) {
        try {
            MyDiagnosticListener c = new MyDiagnosticListener();

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(c, null, null);

            // This sets up the class path that the compiler will use.
            // I've added the .jar file that contains the DoStuff interface within in it...
            List<String> optionList = new ArrayList<String>();
            optionList.add("-classpath");
            optionList.add(System.getProperty("java.class.path") + ";dist/InlineCompiler.jar");

            Iterable<? extends JavaFileObject> compilationUnit
                    = fileManager.getJavaFileObjectsFromFiles(javaFiles);
            JavaCompiler.CompilationTask task = compiler.getTask(
                    null,
                    fileManager,
                    c,
                    optionList,
                    null,
                    compilationUnit);

            if (task.call()) {
                return true;
            }
            fileManager.close();
        } catch (IOException exp) {
            exp.printStackTrace();
        }
        return false;

    }

    public class MyDiagnosticListener implements DiagnosticListener<JavaFileObject> {

        public void report(Diagnostic<? extends JavaFileObject> diagnostic) {

            System.out.println("Line Number->" + diagnostic.getLineNumber());
            System.out.println("code->" + diagnostic.getCode());
            System.out.println("Message->"
                    + diagnostic.getMessage(Locale.ENGLISH));
            System.out.println("Source->" + diagnostic.getSource());
            System.out.println(" ");
        }
    }

    public URLClassLoader getClassLoader() {
        if (Loader != null) {
            return Loader;
        }
        try {
            // Create a new custom class loader, pointing to the directory that contains the compiled
            // classes, this should point to the top of the package structure!
            Loader = new URLClassLoader(new URL[]{new File("./").toURI().toURL()});
        } catch (IOException exp) {
            exp.printStackTrace();
        }
        return Loader;
    }

    public void CreateAndComplieFiles() throws ComplierFailedException {
        ArrayList<File> files = new ArrayList<>();

        for (ClassModel cls : ClassFactory.Instance.Classess) {
            files.add(createJavaFiles(cls.getName(), cls.getJavaCode()));
        }

        boolean success = CompileFiles(files);
        if (!success) {
            throw new ComplierFailedException("Complier Failed ");
        }
    }

    public static void main(String[] args) {
        ClassFactory.CreateDemoClass();
        try {
            DicoCompilerIntiator.Instance.CreateAndComplieFiles();

            for (ClassModel cls : ClassFactory.Instance.Classess) {
                ObjectModel object = DicoCompilerIntiator.Instance.createObject(cls, "variable");
                DicoCompilerIntiator.Instance.InvokeToString(object);
            }
        } catch (ComplierFailedException | ObjectCreationException ex) {
            Logger.getLogger(DicoCompilerIntiator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
