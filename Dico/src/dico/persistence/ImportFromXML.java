/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.persistence;

import dico.ClassFactory;
import dico.DictionaryFactory;
import dico.Pool;
import dico.TypesFactory;
import dico.compiler.DicoCompilerIntiator;
import dico.exceptions.ComplierFailedException;
import dico.exceptions.DuplicateAttributesException;
import dico.exceptions.DuplicateClassesException;
import dico.exceptions.ObjectNotFoundException;
import dico.models.Attribute;
import dico.models.AttributeValue;
import dico.models.ClassModel;
import dico.models.Type;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

    public static void Import(String path) {
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList dictionaries = doc.getElementsByTagName("Dictionary");
            System.out.println("----------------------------");

            for (int dicIndex = 0; dicIndex < dictionaries.getLength(); dicIndex++) {

                // DictionaryFactory.Instance.cre
                Node dictionary = dictionaries.item(dicIndex);
                System.out.println("\nDictionary :" + dictionary.getNodeName());

                if (dictionary.getNodeType() == Node.ELEMENT_NODE) {
                    Element dictionaryElement = (Element) dictionary;
                    String dictionaryName = GetText(dictionaryElement, "Name");

                    ClassModel dictionaryClass = CreateClass(dictionaryElement);

                    DictionaryFactory.Instance.createDictionary(dictionaryClass, dictionaryName);

                    NodeList dictionaryItems = dictionaryElement.getElementsByTagName("DictionaryItem");
                    for (int itemIndex = 0; itemIndex < dictionaryItems.getLength(); itemIndex++) {
                        Node dictionaryItemNode = dictionaryItems.item(itemIndex);
                        System.out.println("\nDictionaryItem :" + dictionaryItemNode.getNodeName());

                        Element dictionaryItemElement = (Element) dictionaryItemNode;
                        Node object = dictionaryItemElement.getElementsByTagName("Item").item(0);
                        Element objectElement = (Element) object;
                        String variableName = GetText(objectElement, "Variable");

                        String typeName = GetText(objectElement, "Type");
                        ClassModel objectClass = ClassFactory.Instance.GetExisitingClass(typeName);

                        Map<String, AttributeValue> attributesValues = new HashMap<>();

                        NodeList objectAttributes = objectElement.getElementsByTagName("AttributeValue");
                        for (int attributeIndex = 0; attributeIndex < objectAttributes.getLength(); attributeIndex++) {
                            Node classAttributeNode = objectAttributes.item(attributeIndex);
                            System.out.println("\nAttributeValue :" + classAttributeNode.getNodeName());
                            Element attributeElement = (Element) classAttributeNode;
                            String name = GetText(attributeElement, "Name");
                            String type = GetText(attributeElement, "Type");
                            Type t = TypesFactory.Instance.Get(type);
                            if (t == null) {
                                ///////////////////////////////////t = new Type(type, "", null, true);
                            }
                            Attribute at = objectClass.getAttribute(name);
                            AttributeValue atv = new AttributeValue(at);
                            String v = GetText(attributeElement, "Value");
                            if (v != null && !"".equals(v)) {
                                Object value = Pool.Instance.GetValue(at, v);
                                atv.setValue(value);
                            }
                            attributesValues.put(at.getName(), atv);
                        }

                        Pool.Instance.createObject(objectClass, variableName,attributesValues);

                        Node friends = dictionaryItemElement.getElementsByTagName("Friends").item(0);
                        Node Enemies = dictionaryItemElement.getElementsByTagName("Enemies").item(0);

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Import("./src/dico/persistence/demo.xml");
    }

    public static ClassModel CreateClass(Element e) throws DuplicateAttributesException, DuplicateClassesException, ComplierFailedException, ClassNotFoundException, ObjectNotFoundException {
        Node objectClass = e.getElementsByTagName("Class").item(0);
        Element objectClassElement = (Element) objectClass;
        String className = GetText(objectClassElement, "Name");
        ClassModel cls = ClassFactory.Instance.GetExisitingClass(className);

        if (cls == null) {
            System.out.println("Create Class " + className);
            cls = new ClassModel();
            cls.setName(className);
            NodeList parenttags = e.getElementsByTagName("Parent");
            if (parenttags.getLength() > 0) {
                Element parentElement = (Element) parenttags.item(0);
                ClassModel parent = CreateClass(parentElement);
                cls.setParent(parent);
            }

            ArrayList<Attribute> attributes = new ArrayList<Attribute>();
            NodeList classAttributes = objectClassElement.getElementsByTagName("Attribute");
            for (int attributeIndex = 0; attributeIndex < classAttributes.getLength(); attributeIndex++) {
                Node classAttributeNode = classAttributes.item(attributeIndex);
                System.out.println("\nAttribute :" + classAttributeNode.getNodeName());
                Element attributeElement = (Element) classAttributeNode;
                String name = GetText(attributeElement, "Name");
                String type = GetText(attributeElement, "Type");
                Type t = TypesFactory.Instance.Get(type);
                if (t == null) {
                    ///////////////////////////////////t = new Type(type, "", null, true);
                }

                Attribute at = cls.getAttribute(name);
                if (at == null) {
                    at = new Attribute(name, t, true, true);
                    attributes.add(at);
                }
                //AttributeValue atv = new AttributeValue(at);

                //String v = GetText(attributeElement, "Value");
                // if (v != null && !"".equals(v)) {
                //    Object value = Pool.Instance.GetValue(at, v);
                //atv.setValue(value);
                //}
            }
            cls.setAttribute(attributes);
            ClassFactory.Instance.generateJavaCode(cls);
            DicoCompilerIntiator.Instance.CreateAndComplieFiles();
        }
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
