/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.danielpozzi.nbphpqa;

import org.openide.filesystems.FileObject;
import java.util.ArrayList;
import java.util.List;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.Node;
import org.openide.loaders.DataObject;
import org.openide.text.Line;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.cookies.LineCookie;
import org.openide.util.Exceptions;

/**
 *
 * @author daniel
 */
public class QAManager {

    /**
     * all files that have a listener
     */
    private static List<String> listenedFiles = new ArrayList<String>();
    
    private List<XmlLogResult> logResults = new ArrayList<XmlLogResult>();
    private MessDetector messDetector;
    private CodeSniffer codeSniffer;

    public QAManager() {
        messDetector = new MessDetector();
        codeSniffer = new CodeSniffer();
    }

    /**
     * Copied from org.netbeans.modules.php.project.ui.actions.support.CommandUtils
     * 
     * @param node
     */
    private FileObject getFileObject(Node node) {
        assert node != null;

        FileObject fileObj = node.getLookup().lookup(FileObject.class);

        if (fileObj  != null && fileObj.isValid()) {
            return fileObj;
        }
        DataObject dataObj = node.getCookie(DataObject.class);

        if (dataObj == null) {
            return null;
        }
        fileObj = dataObj.getPrimaryFile();

        if (fileObj != null && fileObj.isValid()) {
            return fileObj;
        }


        return null;
    }
    
    public void scanNode(Node node)
    {
        FileObject fo = getFileObject(node);
        if(fo != null) {
            scanFileObject(fo);
        }
    }
    
    private QAFileListener getListener(QAManager manager, List<XmlLogResult> logResults)
    {
        return new QAFileListener(manager, logResults);
    }
    
    public void removeFileListener(FileObject fo, QAFileListener listener)
    {
        fo.removeFileChangeListener(listener);
        listenedFiles.remove(fo.getPath());
    }
    
    /**
     * add a qa file listener to a php file if none attached
     * 
     */
    private boolean addListener(FileObject fo, QAFileListener listener)
    {
        String name = fo.getPath();
        if(listenedFiles.contains(name)) {
            StatusDisplayer.getDefault().setStatusText(
                "Listener already added to " + name
            );
        } else  {
            fo.addFileChangeListener(listener);
            StatusDisplayer.getDefault().setStatusText("Listener added to " + name);
            listenedFiles.add(name);
        }
        
        return true;
    }
    
    /**
     * scans a file for violations
     * 
     * @param fo 
     */
    public void scanFileObject(FileObject fo)
    {
        if(!fo.getMIMEType().equals("text/x-php5")) {
            StatusDisplayer.getDefault().setStatusText(
                    "Wrong mime type " + fo.getMIMEType().toString()
            );
            return;
        }
        logResults.clear();
        logResults.add(messDetector.execute(fo));
        logResults.add(codeSniffer.execute(fo));
        
        if(!addListener(fo, getListener(this, logResults))) {
            return;
        }
        
        try {
            annotateWithResults(DataObject.find(fo), logResults);
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    /**
     * annotate results on a data object
     * 
     * @param d
     * @param logResults 
     */
    public void annotateWithResults(DataObject d, List<XmlLogResult> logResults) {
        LineCookie cookie = d.getCookie(LineCookie.class);

        Line.Set lineSet = null;
        Line line = null;
        for (XmlLogResult result : logResults) {
            for (int i = 0; i < result.getViolations().size(); i++) {
                lineSet = cookie.getLineSet();
                line = lineSet.getOriginal(result.getViolations().get(i).getLineNum());
                result.getViolations().get(i).attach(line);
            }
        }
    }
}
