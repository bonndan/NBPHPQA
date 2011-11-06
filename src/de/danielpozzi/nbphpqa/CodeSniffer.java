/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.danielpozzi.nbphpqa;

import java.io.File;
import java.io.Reader;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.netbeans.api.extexecution.ExternalProcessBuilder;
import org.openide.cookies.LineCookie;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Exceptions;
import org.openide.loaders.DataObject;
import org.openide.text.Line;
import org.openide.filesystems.FileObject;
import org.openide.util.NbPreferences;

/**
 *
 * @author dan
 */
public class CodeSniffer implements QATool
{

    /**
     * report type must be xml
     */
    public static final String PARAM_REPORT = "--report=xml";
    /**
     * shell script to execute
     */
    private String shellScript = "/usr/bin/phpcs";
    /**
     * by default all rulesets are used
     */
    private String standard;
    
    private static final Logger logger = Logger.getLogger(NBPHPQA.class.getName());


    public boolean isEnabled() throws Exception {
        if (shellScript == null || !new File(shellScript).exists()) {
            logger.log(Level.WARNING, "{0} not present", shellScript);
            throw new Exception("no shell script");
        }

        standard = "--standard=" + NbPreferences.forModule(NBPHPQA.class)
            .get("nbphpqaCodesnifferStandard", "Zend");
        return true;
    }

    private ExternalProcessBuilder getBuilder(FileObject fo, File parent)
    {
        ExternalProcessBuilder externalProcessBuilder;

        externalProcessBuilder = new ExternalProcessBuilder(this.shellScript)
            .workingDirectory(parent)
            .addArgument(fo.getNameExt())
            .addArgument(PARAM_REPORT)
            .addArgument(this.standard);
        
        return externalProcessBuilder;
    }
    
    /**
     * 
     * @param fo
     * @return 
     */
    @Override
    public XmlLogResult execute(FileObject fo) {
        final File parent = FileUtil.toFile(fo.getParent());

        try {
            if (parent == null || this.isEnabled() == false) {
                logger.log(Level.INFO, "parent is null or not enabled");
                return XmlLogResult.empty();
            }
        } catch (Exception exc) {
            logger.log(Level.INFO, "Exception caught, returning empty result");
            return XmlLogResult.empty();
        }

        CodeSnifferXmlLogParser parser = new CodeSnifferXmlLogParser();
        XmlLogResult rs = parser.parse(
                new ProcessExecutor().execute(getBuilder(fo, parent))
        );

        return rs;
    }
 
}
