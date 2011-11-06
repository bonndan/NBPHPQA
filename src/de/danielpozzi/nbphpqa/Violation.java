/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.danielpozzi.nbphpqa;

import org.openide.text.Annotation;

/**
 *
 * @author daniel
 */
public class Violation extends Annotation
{
    public static final String TYPE_WARNING = "warning";
    public static final String TYPE_ERROR = "error";
    
    private int priority = 0;
    private String violationMessage = null;
    private int lineNum = 0;
    private String rule = null;
    private String type = TYPE_ERROR;
    
    /**
     * Constructor
     * 
     * @param rule
     * @param msg
     * @param lineNum
     */
    public Violation(String rule, String msg, int lineNum)
    {
        this.rule = rule;
        this.violationMessage = msg;
        this.lineNum = lineNum;
    }
    
    /**
     * Constructor
     * 
     * @param rule
     * @param msg
     * @param lineNum
     * @param priority
     */
    public Violation(String rule, String msg, int lineNum, int priority)
    {
        this.rule = rule;
        this.violationMessage = msg;
        this.lineNum = lineNum;
        this.priority = priority;
    }

    /**
     * 
     */
    public void makeWarning()
    {
        type = TYPE_WARNING;
    }
    

    /**
     * get the line number
     * @return int
     */
    public int getLineNum()
    {
        return lineNum;
    }

    public String getRule()
    {
        return rule;
    }

    @Override
    public String getShortDescription()
    {
        return violationMessage;
    }
    
    public int getPriority()
    {
        return priority;
    }

    /**
     * get annotation type
     * @return String
     */
    @Override
    public String getAnnotationType()
    {
        if(type.equals(TYPE_WARNING)) {
            return "de-danielpozzi-nbphpqa-annotation-warning";
        }
        
        return "de-danielpozzi-nbphpqa-annotation-error";
    }
}
