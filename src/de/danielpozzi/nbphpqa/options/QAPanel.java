/*
 * Options panel
 */
package de.danielpozzi.nbphpqa.options;

import de.danielpozzi.nbphpqa.NBPHPQA;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.openide.util.NbPreferences;

final class QAPanel extends JPanel implements DocumentListener {

    private final QAOptionsPanelController controller;


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        optionCodeSnifferStandard = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        optionCodeSnifferExecutable = new javax.swing.JTextField();

        optionCodeSnifferStandard.setText(org.openide.util.NbBundle.getMessage(QAPanel.class, "QAPanel.optionCodeSnifferStandard.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(QAPanel.class, "QAPanel.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(QAPanel.class, "QAPanel.jLabel2.text")); // NOI18N

        optionCodeSnifferExecutable.setText(org.openide.util.NbBundle.getMessage(QAPanel.class, "QAPanel.optionCodeSnifferExecutable.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(67, 67, 67)
                        .addComponent(optionCodeSnifferExecutable, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(94, 94, 94)
                        .addComponent(optionCodeSnifferStandard, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(optionCodeSnifferExecutable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(optionCodeSnifferStandard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(198, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    void load() {
        optionCodeSnifferStandard.setText(
            NbPreferences.forModule(NBPHPQA.class).get("nbphpqaCodesnifferStandard", "Zend")
        );
        optionCodeSnifferExecutable.setText(
            NbPreferences.forModule(NBPHPQA.class).get("nbphpqaCodesnifferExecutable", "/usr/bin/phpcs")
        );
    }

    void store() {
        NbPreferences.forModule(NBPHPQA.class).put("nbphpqaCodesnifferStandard",
            optionCodeSnifferStandard.getText()
        );
        NbPreferences.forModule(NBPHPQA.class).put("nbphpqaCodesnifferExecutable",
            optionCodeSnifferExecutable.getText()
        );
    }

    boolean valid() {
        if(optionCodeSnifferStandard.getText().length() == 0)
            return false;
        
        return true;
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField optionCodeSnifferExecutable;
    private javax.swing.JTextField optionCodeSnifferStandard;
    // End of variables declaration//GEN-END:variables

    
    QAPanel(QAOptionsPanelController controller) {
        this.controller = controller;
        initComponents();
        optionCodeSnifferStandard.getDocument().addDocumentListener(this);
        optionCodeSnifferExecutable.getDocument().addDocumentListener(this);
    }
    
    @Override
    public void changedUpdate(DocumentEvent de) {
        controller.changed();
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        controller.changed();
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        controller.changed();
    }
}
