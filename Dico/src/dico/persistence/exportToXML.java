/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.persistence;

import dico.DictionaryFactory;
import dico.models.Attribute;
import dico.models.Dictionary;
import dico.models.ObjectModel;
import dico.models.Pair;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
 * @author k.shehady
 */
public class exportToXML {

    public static void main(String[] myArgs) throws XMLStreamException, IOException, ClassNotFoundException, Exception {

        DictionaryFactory.Demo();

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

            //start Elements
            xMLStreamWriter.writeStartElement("Elements");
            for (ObjectModel element : dic) {
                System.out.println(element.getVariableName());
                //start Element
                xMLStreamWriter.writeStartElement("Element");

                Pair<ObjectModel> elementPair = dic.getPair(element);              //my pair

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
            xMLStreamWriter.writeEndElement();
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

        System.out.println(xmlString);
    }

    public static void ObjectToXML(XMLStreamWriter xMLStreamWriter, ObjectModel object) throws XMLStreamException {
        xMLStreamWriter.writeStartElement("Object");

        xMLStreamWriter.writeStartElement("Class");
        xMLStreamWriter.writeCharacters(object.getClassModel().getName());
        xMLStreamWriter.writeEndElement();

        xMLStreamWriter.writeStartElement("Variable");
        xMLStreamWriter.writeCharacters(object.getVariableName());
        xMLStreamWriter.writeEndElement();

        for (Attribute attribute : object.getClassModel().getAttributesWithSuper()) {
            xMLStreamWriter.writeStartElement("Attribute");

            xMLStreamWriter.writeStartElement("Name");
            xMLStreamWriter.writeCharacters(attribute.getName().toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("Type");
            xMLStreamWriter.writeCharacters(attribute.getType().getName());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("Value");
            xMLStreamWriter.writeCharacters(attribute.getValue().toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeEndElement();
        }

        xMLStreamWriter.writeEndElement();

    }
}
