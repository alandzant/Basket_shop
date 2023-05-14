import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Settings {
    boolean Load;
    boolean Save;
    boolean Log;

    //
    String fileNameLoad;
    String formatLoad;

    //

    String fileNameSave;
    String formatSave;

    //

    String fileNameLog;

    public Settings(File setFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(setFile);

        Element rootElement = document.getDocumentElement();

        Element loadSet = (Element) rootElement.getElementsByTagName("load").item(0);
        Element saveSet = (Element) rootElement.getElementsByTagName("save").item(0);
        Element logSet = (Element) rootElement.getElementsByTagName("log").item(0);



        Load = Boolean.parseBoolean(loadSet.getElementsByTagName("enabled").item(0).getTextContent());
        fileNameLoad = loadSet.getElementsByTagName("fileName").item(0).getTextContent();
        formatLoad = loadSet.getElementsByTagName("format").item(0).getTextContent();


        Save = Boolean.parseBoolean(saveSet.getElementsByTagName("enabled").item(0).getTextContent());
        fileNameSave = saveSet.getElementsByTagName("fileName").item(0).getTextContent();
        formatSave = saveSet.getElementsByTagName("format").item(0).getTextContent();

        Log = Boolean.parseBoolean(logSet.getElementsByTagName("enabled").item(0).getTextContent());
        fileNameLog = logSet.getElementsByTagName("fileName").item(0).getTextContent();


    }
}
