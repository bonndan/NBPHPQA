/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.danielpozzi.nbphpqa;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */
public class XmlLogResult
{

    static public XmlLogResult empty()
    {
        return new XmlLogResult(new ArrayList<Violation>());
    }
    
    private List<Violation> violations = null;

    public XmlLogResult(List<Violation> violations)
    {
        this.violations = violations;
    }

    /**
     * get the violations list
     * 
     * @return List
     */
    public List<Violation> getViolations()
    {
        return violations;
    }
}
