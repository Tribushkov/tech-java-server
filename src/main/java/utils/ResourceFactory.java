package utils;

import org.jetbrains.annotations.Nullable;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/**
 * Created by dmitri on 28.10.15.
 */
public class ResourceFactory {

    private static class ResourceFactoryHolder {
        private static final ResourceFactory INSTANCE = new ResourceFactory();
    }

    public static ResourceFactory getInstance() {
        return ResourceFactoryHolder.INSTANCE;
    }

    @Nullable
    public Object getResourceObject(String pathToFile) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            SaxHandler handler = new SaxHandler();
            saxParser.parse(pathToFile, handler);

            return handler.getObject();

        } catch (IOException | ParserConfigurationException | SAXException | RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

}
