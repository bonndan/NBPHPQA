/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.danielpozzi.nbphpqa;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.extexecution.ExternalProcessBuilder;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.FileObject;

/**
 *
 * @author benny
 */
public class MessDetector {

    /**
     * report type must be xml
     */
    public static final String PARAM_REPORT = "xml";
    /**
     * shell script to execute
     */
    private String shellScript = "/usr/bin/phpmd";
    /**
     * by default all rulesets are used
     */
    private String rulesets = "codesize,design,naming,unusedcode";
    
    private static final Logger logger = Logger.getLogger(NBPHPQA.class.getName());


    public boolean isEnabled() throws Exception {
        if (shellScript == null || !new File(shellScript).exists()) {
            logger.log(Level.WARNING, shellScript + " not present");
            throw new Exception("no shell script");
        }

        return true;
    }

    private ExternalProcessBuilder getBuilder(FileObject fo, File parent)
    {
        ExternalProcessBuilder externalProcessBuilder;

        externalProcessBuilder = new ExternalProcessBuilder(this.shellScript)
            .workingDirectory(parent)
            .addArgument(fo.getNameExt())
            .addArgument(PARAM_REPORT)
            .addArgument(this.rulesets);
        
        return externalProcessBuilder;
    }
    
    /**
     * 
     * @param fo
     * @return XmlLogResult
     */
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

        MessDetectorXmlLogParser parser = new MessDetectorXmlLogParser();
        XmlLogResult rs = parser.parse(
                new ProcessExecutor().execute(getBuilder(fo, parent))
        );

        return rs;
    }


}
