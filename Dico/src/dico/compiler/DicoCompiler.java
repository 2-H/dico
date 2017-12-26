/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.compiler;

import dico.ClassFactory;
import dico.models.Attribute;
import dico.models.ClassModel;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import sun.reflect.ConstructorAccessor;

public class DicoCompiler {

    /**
     * where shall the compiled class be saved to (should exist already)
     */
    private static String classOutputFolder = "C:/";

    public static class MyDiagnosticListener implements DiagnosticListener<JavaFileObject> {

        public void report(Diagnostic<? extends JavaFileObject> diagnostic) {

            System.out.println("Line Number->" + diagnostic.getLineNumber());
            System.out.println("code->" + diagnostic.getCode());
            System.out.println("Message->"
                    + diagnostic.getMessage(Locale.ENGLISH));
            System.out.println("Source->" + diagnostic.getSource());
            System.out.println(" ");
        }
    }

    /**
     * java File Object represents an in-memory java source file <br>
     * so there is no need to put the source file on hard disk *
     */
    public static class InMemoryJavaFileObject extends SimpleJavaFileObject {

        private String contents = null;

        public InMemoryJavaFileObject(String className, String contents) throws Exception {
            super(URI.create("string:///" + className.replace('.', '/')
                    + Kind.SOURCE.extension), Kind.SOURCE);
            this.contents = contents;
        }

        public CharSequence getCharContent(boolean ignoreEncodingErrors)
                throws IOException {
            return contents;
        }
    }

    /**
     * Get a simple Java File Object ,<br>
     * It is just for demo, content of the source code is dynamic in real use
     * case
     */
    private static JavaFileObject getJavaFileObject(String filename, String content) {
        JavaFileObject so = null;
        try {
            so = new InMemoryJavaFileObject(filename, content);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return so;
    }

    /**
     * compile your files by JavaCompiler
     */
    public static void compile(Iterable<? extends JavaFileObject> files) {
        //get system compiler:
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        // for compilation diagnostic message processing on compilation WARNING/ERROR
        MyDiagnosticListener c = new MyDiagnosticListener();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(c,
                Locale.ENGLISH,
                null);
        //specify classes output folder
        Iterable options = Arrays.asList("-d", classOutputFolder);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager,
                c, options, null,
                files);
        Boolean result = task.call();
        if (result == true) {
            System.out.println("Succeeded");
        }
    }

    /**
     * run class from the compiled byte code file by URLClassloader
     */
    public static ClassLoader loadClassesIntoMemory() {
        File file = new File(classOutputFolder);
        ClassLoader loader = null;
        try {
            // Convert File to a URL
            URL url = file.toURL();
            URL[] urls = new URL[]{url};

            loader = new URLClassLoader(urls);
            return loader;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return loader;
    }

    public static void createObject(ClassLoader loader, ClassModel model) {
        try {
            Class thisClass = loader.loadClass("dicodemo." + model.getName());

            //  ;
            ArrayList<Class> constructorParams = new ArrayList<>();
            for (Attribute atr : model.getAttribute()) {
                constructorParams.add(atr.getType().getClassName());
            }
            // Class[] params =new {int.class,String.class} id name
            Class[] params = constructorParams.toArray(new Class[constructorParams.size()]);

            //Object instance = thisClass.newInstance();
            Constructor constructor = thisClass.getConstructor(params);

            ArrayList<Class> constructorParamValues = new ArrayList<>();
            for (Attribute atr : model.getAttribute()) {
                Class type = (atr.getType().getClassName());
                System.out.println(type);
                System.out.println(atr.getValueString());
                constructorParamValues.add(type.getClass().cast(atr.getValueString()));
            }

            //Object ali = constructor.newInstance(1, "ali");
            Class[] paramsValues = constructorParamValues.toArray(new Class[constructorParamValues.size()]);

            Object kassem = constructor.newInstance(paramsValues);

            Method toStringKassem = thisClass.getDeclaredMethod("toString", null);
            Object str = toStringKassem.invoke(kassem);

            System.out.println(str.toString());

            //Class paramsMethold[] = {Object.class};//thisClass
            //Method equalsMethold = thisClass.getDeclaredMethod("equals", paramsMethold);
            // run the equals() method on the instance:
            //Object o = equalsMethold.invoke(ali, kassem);
            //System.out.println("ali.equals(kassem) ==> " + (boolean) o);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void testCTO(ClassLoader loader, String className) {
        try {
            Class thisClass = loader.loadClass(className);

            Class[] params = {int.class, String.class};
            //Object paramsObj[] = {1, 4};
            //Object instance = thisClass.newInstance();
            Constructor constructor = thisClass.getConstructor(params);
            Object ali = constructor.newInstance(1, "ali");

            Object kassem = constructor.newInstance(2, "kassem");

            Method toStringKassem = thisClass.getDeclaredMethod("toString", null);
            Object str = toStringKassem.invoke(kassem);

            System.out.println(str.toString());

            Class paramsMethold[] = {Object.class};//thisClass
            Method equalsMethold = thisClass.getDeclaredMethod("equals", paramsMethold);

            // run the equals() method on the instance:
            Object o = equalsMethold.invoke(ali, kassem);

            System.out.println("ali.equals(kassem) ==> " + (boolean) o);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ClassFactory.CreateDemoClass();

        //1.Construct an in-memory java source file from your dynamic code
        ArrayList<JavaFileObject> files = new ArrayList<>();//Arrays.asList(file);

        for (ClassModel cls : ClassFactory.Instance.Classess) {
            JavaFileObject file = getJavaFileObject(cls.getName(), cls.getJavaCode());
            files.add(file);
        }
        //2.Compile your files by JavaCompiler
        compile(files);

        ClassLoader loader = loadClassesIntoMemory();
        //3.Load your class by URLClassLoader, then instantiate the instance, and call method by reflection
        createObject(loader, ClassFactory.Instance.Classess.get(0));
    }
}
