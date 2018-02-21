/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.persistence;

import dico.ClassFactory;
import dico.DictionaryFactory;
import dico.ObjectFactory;
import dico.TypesFactory;
import dico.compiler.DicoCompilerIntiator;
import dico.exceptions.DuplicateAttributesException;
import dico.exceptions.DuplicateClassesException;
import dico.models.Attribute;
import dico.models.ClassModel;
import dico.models.ObjectModel;
import dico.models.Type;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 *
 * @author User
 */
public class ImportFromXML {

    public static void main(String[] args) {

        try {
            File inputFile = new File("./src/dico/persistence/demo.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList dictionaries = doc.getElementsByTagName("Dictionary");
            System.out.println("----------------------------");

            ArrayList<ObjectModel> objectsList = new ArrayList<ObjectModel>();

            for (int dicIndex = 0; dicIndex < dictionaries.getLength(); dicIndex++) {

                // DictionaryFactory.Instance.cre
                Node dictionary = dictionaries.item(dicIndex);
                System.out.println("\nDictionary :" + dictionary.getNodeName());

                if (dictionary.getNodeType() == Node.ELEMENT_NODE) {
                    Element dictionaryElement = (Element) dictionary;
                    String dictionaryName = GetText(dictionaryElement, "Name");

                    ClassModel dictionaryClass = GetClass(dictionaryElement,true);
                    CreateParents(dictionaryClass, dictionaryElement);

                    DictionaryFactory.Instance.createDictionary(dictionaryClass, dictionaryName);

                    NodeList dictionaryItems = dictionaryElement.getElementsByTagName("DictionaryItem");
                    for (int itemIndex = 0; itemIndex < dictionaryItems.getLength(); itemIndex++) {
                        Node dictionaryItemNode = dictionaryItems.item(itemIndex);
                        System.out.println("\nDictionaryItem :" + dictionaryItemNode.getNodeName());
                        ObjectModel objectmodel = new ObjectModel();
                        Element dictionaryItemElement = (Element) dictionaryItemNode;
                        Node object = dictionaryItemElement.getElementsByTagName("Object").item(0);
                        Element objectElement = (Element) object;
                        String variableName = GetText(objectElement, "Variable");

                        objectmodel.setVariableName(variableName);
                        ClassModel objectClass = GetClass(objectElement,false);
                        objectmodel.setClassModel(objectClass);
                        objectsList.add(objectmodel);

                        Node friends = dictionaryItemElement.getElementsByTagName("Friends").item(0);
                        Node Enemies = dictionaryItemElement.getElementsByTagName("Enemies").item(0);

                    }
                }
            }

            DicoCompilerIntiator.Instance.CreateAndComplieFiles();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void CreateParents(ClassModel cls, Element e) throws DuplicateAttributesException, DuplicateClassesException {
        if (cls.getParent() != null) {
            Element parentElement = (Element) e.getElementsByTagName("Parent").item(0);
            ClassModel parent = GetClass(parentElement,true);
            if (!ClassFactory.Instance.CheckClass(parent.getName())) {
                CreateParents(cls.getParent(), parentElement);
            }
        }
        System.out.println("cls " + cls.getName());
        ClassFactory.Instance.generateJavaCode(cls);
    }

    public static ClassModel GetClass(Element e , boolean dictionary) {
        Node objectClass = e.getElementsByTagName("Class").item(0);
        Element objectClassElement = (Element) objectClass;
        String className = GetText(objectClassElement, "Name");
        NodeList parentElement = objectClassElement.getElementsByTagName("Parent");
        //String parentClassName = GetText(objectClassElement, "Parent");

        ClassModel cls = new ClassModel();

        cls.setName(className);

        /*
        if (parentElement.getLength() != 0 && dictionary) {
            Node p = parentElement.item(0);
            ClassModel clsParent = new ClassModel();
            String parentClassName=GetText((Element)parentElement, "");
            clsParent.setName(parentClassName);
            cls.setParent(clsParent);
        }*/

        ArrayList<Attribute> attributes = new ArrayList<Attribute>();
        NodeList classAttributes = objectClassElement.getElementsByTagName("Attribute");
        for (int attributeIndex = 0; attributeIndex < classAttributes.getLength(); attributeIndex++) {
            Node classAttributeNode = classAttributes.item(attributeIndex);
            System.out.println("\nAttribute :" + classAttributeNode.getNodeName());
            Element attributeElement = (Element) classAttributeNode;
            String name = GetText(attributeElement, "Name");
            String type = GetText(attributeElement, "Type");
            String value = GetText(attributeElement, "Value");
            Type t = TypesFactory.Instance.Get(type);
            if (t == null) {
                t = new Type(type, "", null, true);
            }
            Attribute at = new Attribute(name, t, true, true);
            at.setValue(value);
            attributes.add(at);
        }
        cls.setAttribute(attributes);
        return cls;
    }

    public static String GetText(Element e, String tag) {
        NodeList list = e
                .getElementsByTagName(tag);

        if (list.getLength() > 0) {
            String value = list.item(0)
                    .getTextContent();
            System.out.println(tag + " : " + value);
            return value;
        }
        return null;
    }
}
