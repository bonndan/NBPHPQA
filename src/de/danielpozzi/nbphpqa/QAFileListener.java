/*
 * File Listener 
 */
package de.danielpozzi.nbphpqa;

import org.openide.filesystems.FileAttributeEvent;
import org.openide.filesystems.FileChangeListener;
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileRenameEvent;
import java.util.List;

/**
 *
 * @author Daniel Pozzi
 */
public class QAFileListener implements FileChangeListener
{
    private List<XmlLogResult> logResults;
    private QAManager qaManager;

    /**
     * constructor
     * 
     */
    public QAFileListener(QAManager qaManager, List<XmlLogResult> logResults)
    {
        this.qaManager = qaManager;
        this.logResults = logResults;
    }
    
    /**
     * constructor
     * 
     */
    public QAFileListener(QAManager qaManager)
    {
        this.qaManager = qaManager;
    }


    
    @Override
    public void fileFolderCreated(FileEvent fileEvent) {
        
    }

    @Override
    public void fileDataCreated(FileEvent fileEvent) {
        
    }

    /**
     * re-scans file after changing it
     * @param fileEvent
     */
    @Override
    public void fileChanged(FileEvent fileEvent)
    {
        for (XmlLogResult logResult: logResults) {
            for(Violation e: logResult.getViolations()) {
                e.detach();
            }
        }
        
        qaManager.removeFileListener(fileEvent.getFile(), this);
        qaManager.scanFileObject(fileEvent.getFile());
    }

    @Override
    public void fileDeleted(FileEvent fileEvent) {
        
    }

    @Override
    public void fileRenamed(FileRenameEvent fileEvent) {
        
    }

    @Override
    public void fileAttributeChanged(FileAttributeEvent fileEvent) {
        
    }

}
