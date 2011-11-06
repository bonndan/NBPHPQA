/*
 * PHP QA Tools Integration
 * 
 */
package de.danielpozzi.nbphpqa;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;
import org.openide.cookies.EditorCookie;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle.Messages;
import org.openide.filesystems.FileObject;
import org.openide.windows.TopComponent;
import org.openide.nodes.Node;
import org.openide.loaders.DataObject;
import java.util.logging.Level;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;


@ActionID(category = "PHP",
id = "de.danielpozzi.nbphpqa.NBPHPQA")
@ActionRegistration(displayName = "#CTL_NBPHPQA")
@ActionReferences({
    @ActionReference(path = "Menu/Source", position = 9200),
    @ActionReference(path = "Editors/text/x-php5/Popup", position = 4300)
})
@Messages("CTL_NBPHPQA=Enable PHPQA on this file")
public final class NBPHPQA implements ActionListener {

    private final EditorCookie context;
    private QAManager qaManager;
    private static final Logger logger = Logger.getLogger(NBPHPQA.class.getName());

    /**
     * 
     * @param EditorCookie context 
     */
    public NBPHPQA(EditorCookie context) {
        this.context = context;
        qaManager = new QAManager();
        logger.log(Level.INFO, "NBPHPQA started");
    }

    /**
     * 
     * @param ev 
     */
    @Override
    public void actionPerformed(ActionEvent ev) {

        Node[] activatedNodes = TopComponent.getRegistry().getActivatedNodes();
        if (activatedNodes.length != 1) {
            return;
        }
        qaManager.scanNode(activatedNodes[0]);
    }
}
