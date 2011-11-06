/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.danielpozzi.nbphpqa;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.project.Project;

import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.Sources;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle.Messages;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.awt.StatusDisplayer;
import org.openide.loaders.DataObject;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;
import org.openide.nodes.Node;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.modules.editor.NbEditorUtilities;

@ActionID(category = "PHP",
id = "de.danielpozzi.nbphpqa.ProjectQA")
@ActionRegistration(displayName = "#CTL_ProjectQA")
@ActionReferences({
    @ActionReference(path = "Menu/Source", position = 9300)
})
@Messages("CTL_ProjectQA=Enable PHPQA on this project")
public final class ProjectQA implements ActionListener {

    private final Project context;
    private QAManager qaManager;

    public ProjectQA(Project context) {
        this.context = context;
        qaManager = new QAManager();
    }

    public void actionPerformed(ActionEvent ev) {
        PropertyChangeListener l = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                JTextComponent jtc = EditorRegistry.lastFocusedComponent();
                if (jtc != null) {
                    FileObject fo = NbEditorUtilities.getFileObject(jtc.getDocument());
                    
                    if(fo.getMIMEType().equals("text/x-php5")) {
                        qaManager.scanFileObject(fo);
                    }
                }
            }
        };

        EditorRegistry.addPropertyChangeListener(l);
    }
}
