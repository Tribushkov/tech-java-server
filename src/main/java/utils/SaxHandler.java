package utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by dmitri on 28.10.15.
 */


public class SaxHandler extends DefaultHandler {

    private String element = null;
    private Object object = null;
    private ReflectionHelper reflectionHelper = new ReflectionHelper();

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start document");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("End document ");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        String classname = "class";
        if(!qName.equals(classname)){
            element = qName;
        }
        else{
            String className = attributes.getValue(0);
            System.out.println("Class name: " + className);
            object = ReflectionHelper.createInstance(className);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        element = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(element != null) {
            String value = new String(ch, start, length);
            System.out.println(element + " = " + value);
            reflectionHelper.setFieldValue(object, element, value);
        }
    }

    public Object getObject(){
        return object;
    }
}
