

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 * @author daniel
 */
public class CodeSnifferXmlLogParser extends XmlLogParser
{
    
    /**
     * loop over node list and return a violation list
     * 
     * @param list
     * @param isWarningList
     */
    private List<Violation> processNodeList(NodeList list, boolean isWarningList)
    {
        List<Violation> violations = new ArrayList<Violation>();
        Violation violation;
        int lineNum = 0;
        for (int i = 0; i < list.getLength(); i++) {
            String message = list.item(i).getTextContent();
            NamedNodeMap nm = list.item(i).getAttributes();
            lineNum = Integer.parseInt(nm.getNamedItem("line").getTextContent()) - 1;
            String rule = nm.getNamedItem("source").getTextContent();
            violation = new Violation(rule, message, lineNum);
            if(isWarningList) {
                violation.makeWarning();
            }
            violations.add(violation);
        }
        
        return violations;
    }
    
    /**
     * parse
     * 
     * @param reader
     * @return XmlLogResult
     */
    @Override
    XmlLogResult parse(Reader reader)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        List<Violation> violations = new ArrayList<Violation>();
        try {
            builder = factory.newDocumentBuilder();
            Document document;
            document = builder.parse(new InputSource(reader));
            
            NodeList errorList = document.getElementsByTagName("error");
            violations = processNodeList(errorList, false);
            
            NodeList warnList = document.getElementsByTagName("warning");
            for (Violation v: processNodeList(warnList, true)) {
                violations.add(v);
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
