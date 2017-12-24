/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.compiler;

import dico.ClassFactory;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
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

/**
 * Dynamic java class compiler and executer  <br>
 * Demonstrate how to compile dynamic java source code, <br>
 * instantiate instance of the class, and finally call method of the class <br>
 *
 * http://www.beyondlinux.com
 *
 * @author david 2011/07
 *
 */
public class DynamicCompiler {

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
    public static void runIt(String className) {
        // Create a File object on the root of the directory
        // containing the class file
        File file = new File(classOutputFolder);

        try {
            // Convert File to a URL
            URL url = file.toURL(); // C:/classes
            URL[] urls = new URL[]{url};

            // Create a new class loader with the directory
            ClassLoader loader = new URLClassLoader(urls);

            // Load in the class; Class.childclass should be located in
            // the directory C:
            Class thisClass = loader.loadClass(className);

            Class[] params = {int.class, String.class};
            //Object paramsObj[] = {1, 4};
            //Object instance = thisClass.newInstance();
            Constructor constructor = thisClass.getConstructor(params);
            Object ali = constructor.newInstance(1, "ali");

            Object kassem = constructor.newInstance(2, "kassem");

            Class paramsMethold[] = {Object.class};//thisClass
            Method equalsMethold = thisClass.getDeclaredMethod("equals", paramsMethold);

            // run the equals() method on the instance:
            Object o = equalsMethold.invoke(ali, kassem);

            System.out.println("ali.equals(kassem) ==> " + (boolean) o);
        } catch (MalformedURLException | ClassNotFoundException e) {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String className = "dicodemo.Person";

        /*
        String contents = "package dico;"
                + "public class Person { "
                + "private int id;"
                + "private String name;"

                + "public  Person(int id, String name) { "
                + "   this.id=id;"
                + "   this.name=name;"
                + "   System.out.println(name + \" is initialized \"); "
                + "} "
                + "  public boolean equals(Object other) { "
                + "    Person obj=(Person)other; "
                + "    return obj.id==this.id; "
                + "  } "
                + "} ";
         */
        
        String test = ClassFactory.CreateDemoClass();
        
        System.out.println(test);
        //1.Construct an in-memory java source file from your dynamic code
        JavaFileObject file = getJavaFileObject(className, test);
        Iterable<? extends JavaFileObject> files = Arrays.asList(file);

        //2.Compile your files by JavaCompiler
        compile(files);

        //3.Load your class by URLClassLoader, then instantiate the instance, and call method by reflection
        runIt(className);
    }
}
