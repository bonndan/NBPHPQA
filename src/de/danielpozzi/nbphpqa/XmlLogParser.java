/*
 * XML log parser
 */
package de.danielpozzi.nbphpqa;

import java.io.File;
import java.io.Reader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */
public abstract class XmlLogParser {

    XmlLogResult parse(File fo) {
        if (fo == null || fo.exists() == false) {
            return createEmptyResult();
        }

        try {
            return parse(new InputStreamReader(new FileInputStream(fo)));
        } catch (FileNotFoundException e) {
            return createEmptyResult();
        }
    }
    
    private XmlLogResult createEmptyResult()
    {
        List<Violation> violations = new ArrayList<Violation>();

        return new XmlLogResult(violations);
    }
    
    /**
     * this must be implemented by the parsers
     * 
     * @param reader
     * @return 
     */
    abstract XmlLogResult parse(Reader reader);
}
