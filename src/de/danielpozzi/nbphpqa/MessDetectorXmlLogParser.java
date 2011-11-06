/*
 * Parse a phpmd xml log result for errors
 * 
 */

package de.danielpozzi.nbphpqa;


import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.Exceptions;

import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;

/**
 *
 * @author benny
 * @author Daniel Pozzi
 */
public class MessDetectorXmlLogParser extends XmlLogParser {

    @Override
    XmlLogResult parse(Reader reader)
    {
        List<Violation> violations = new ArrayList<Violation>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document;
            document = builder.parse(new InputSource(reader));
            NodeList ndList = document.getElementsByTagName("violation");
            int lineNum = 0;
            for (int i = 0; i < ndList.getLength(); i++) {
                String message = ndList.item(i).getTextContent();
                NamedNodeMap nm = ndList.item(i).getAttributes();
                lineNum = Integer.parseInt(nm.getNamedItem("beginline").getTextContent()) - 1;
                String rule = nm.getNamedItem("rule").getTextContent();
                violations.add(new Violation(rule, message, lineNum));
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (ParserConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        } catch(SAXParseException ex) {
            Exceptions.printStackTrace(ex);
        } catch (SAXException ex) {
            Exceptions.printStackTrace(ex);
        }

        return new XmlLogResult(violations);
    }
}
