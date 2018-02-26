/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.persistence;

import dico.DictionaryFactory;
import dico.models.Attribute;
import dico.models.AttributeValue;
import dico.models.ClassModel;
import dico.models.Dictionary;
import dico.models.ObjectModel;
import dico.models.Triplet;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
 *
 * @author k.shehady
 */
public class exportToXML {

    public static String ExportToXml() throws XMLStreamException, IOException {
        StringWriter stringWriter = new StringWriter();

        XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xMLStreamWriter
                = xMLOutputFactory.createXMLStreamWriter(stringWriter);
        xMLStreamWriter.writeStartDocument();

        //start Dictionaries
        xMLStreamWriter.writeStartElement("Dictionaries");

        for (Dictionary<ObjectModel> dic : DictionaryFactory.Instance.getDictionaries()) {
            //start Dictionary
            xMLStreamWriter.writeStartElement("Dictionary");

            //start Name
            xMLStreamWriter.writeStartElement("Name");
            xMLStreamWriter.writeCharacters(dic.getName());
            xMLStreamWriter.writeEndElement();
            //end Name
            ClassToXML(xMLStreamWriter, dic.getClassModel());

//start Elements
            for (ObjectModel element : dic) {
                System.out.println(element.getVariableName());
                //start Element
                xMLStreamWriter.writeStartElement("DictionaryItem");

                Triplet<ObjectModel> elementPair = dic.getPair(element);              //my pair

                ObjectToXML(xMLStreamWriter, element);

                //start Friends
                xMLStreamWriter.writeStartElement("Friends");
                for (ObjectModel object : elementPair.getFriends()) {
                    //start Friend
                    ObjectToXML(xMLStreamWriter, object);
                    //end Friend
                }
                xMLStreamWriter.writeEndElement();
                //end Friends

                //start Enemies
                xMLStreamWriter.writeStartElement("Enemies");
                for (ObjectModel object : elementPair.getEnemies()) {
                    //start Friend
                    ObjectToXML(xMLStreamWriter, object);
                    //end Enemy
                }
                xMLStreamWriter.writeEndElement();
                //end Enemies

                xMLStreamWriter.writeEndElement();
                //end Element
            }

            //end Elements
            xMLStreamWriter.writeEndElement();
            //end Dictionary    
        }
        xMLStreamWriter.writeEndElement();
        //end Dictionaries
        xMLStreamWriter.writeEndDocument();
        xMLStreamWriter.flush();
        xMLStreamWriter.close();

        String xmlString = stringWriter.getBuffer().toString();

        stringWriter.close();

        //System.out.println(xmlString);
        return xmlString;
    }

    public static void main(String[] myArgs) throws XMLStreamException, IOException, ClassNotFoundException, Exception {

        DictionaryFactory.Demo();

        try {
            // Create file 
            String path = "./src/dico/persistence/demo.xml";
            FileWriter fstream = new FileWriter(path);
            BufferedWriter out = new BufferedWriter(fstream);
            String xmlString = ExportToXml();
            System.out.println(xmlString);

            out.write(xmlString);
            //Close the output stream
            out.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

    }

    public static void ObjectToXML(XMLStreamWriter xMLStreamWriter, ObjectModel object) throws XMLStreamException {
        xMLStreamWriter.writeStartElement("Item");
        xMLStreamWriter.writeStartElement("Variable");
        xMLStreamWriter.writeCharacters(object.getVariableName());
        xMLStreamWriter.writeEndElement();

        xMLStreamWriter.writeStartElement("Object");
        xMLStreamWriter.writeStartElement("Type");
        xMLStreamWriter.writeCharacters(object.getClassModel().getName());
        xMLStreamWriter.writeEndElement();

        /*   if (object.getClassModel().getParent() != null) {
            xMLStreamWriter.writeStartElement("Parent");
            xMLStreamWriter.writeCharacters(object.getClassModel().getParent().getName());
            xMLStreamWriter.writeEndElement();
        }
         */
        for (Attribute at : object.getClassModel().getAttributesWithSuper()) {

            AttributeValue attributeValue = object.getAttributesValues().get(at.getName());

            xMLStreamWriter.writeStartElement("AttributeValue");

            xMLStreamWriter.writeStartElement("Name");
            xMLStreamWriter.writeCharacters(at.getName());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("Type");
            xMLStreamWriter.writeCharacters(at.getType().getName());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("Value");
            xMLStreamWriter.writeCharacters(attributeValue.getValue().toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeEndElement();
        }

        xMLStreamWriter.writeEndElement();

        xMLStreamWriter.writeEndElement();

    }

    public static void ClassToXML(XMLStreamWriter xMLStreamWriter, ClassModel cls) throws XMLStreamException {

        xMLStreamWriter.writeStartElement("Class");
        xMLStreamWriter.writeStartElement("Name");
        xMLStreamWriter.writeCharacters(cls.getName());
        xMLStreamWriter.writeEndElement();

        if (cls.getParent() != null) {
            xMLStreamWriter.writeStartElement("Parent");
            ClassToXML(xMLStreamWriter, cls.getParent());
            xMLStreamWriter.writeEndElement();
        }

        for (Attribute attribute : cls.getAttribute()) {
            xMLStreamWriter.writeStartElement("Attribute");

            xMLStreamWriter.writeStartElement("Name");
            xMLStreamWriter.writeCharacters(attribute.getName().toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("Type");
            xMLStreamWriter.writeCharacters(attribute.getType().getName());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeEndElement();
        }
        xMLStreamWriter.writeEndElement();

    }

}
