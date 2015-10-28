package utils;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by dmitri on 28.10.15.
 */
public class ResourceFactory {

    private static class ResourceFactoryHolder {
        private final static ResourceFactory instance = new ResourceFactory();
    }

    public static ResourceFactory getInstance() {
        return ResourceFactoryHolder.instance;
    }

    public Object getResourceObject(String pathToFile) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            SaxHandler handler = new SaxHandler();
            saxParser.parse(pathToFile, handler);

            return handler.getObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
