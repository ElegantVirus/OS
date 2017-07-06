/**
*Operaciniu sistemu projektas
*Autores :
*Evelina Bujyte
*Anastasija Kiseliova
*Matematine informatika
*3 kursas
*2017
**/
package os;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

public class ProgramIn extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public ProgramIn() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        program_name = new javax.swing.JTextField();
        enterButton = new javax.swing.JButton();
        program = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        program_name.setForeground(new java.awt.Color(204, 204, 204));
        program_name.setText("name");
        program_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                program_nameKeyPressed(evt);
            }
        });

        enterButton.setLabel("ENTER");

        program.setForeground(new java.awt.Color(204, 204, 204));
        program.setText("DATA-10000200080REZULTATASYRA:00CODELR01AD02HALT");
        program.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                programActionPerformed(evt);
            }
        });
        program.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                programKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(program_name, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(enterButton))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(program, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(program_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(program, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(enterButton)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        enterButton.getAccessibleContext().setAccessibleName("enterButton");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void program_nameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_program_nameKeyPressed
        // TODO add your handling code here:
        program_name.setForeground(Color.black);
    }//GEN-LAST:event_program_nameKeyPressed

    private void programActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_programActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_programActionPerformed

    private void programKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_programKeyPressed
        // TODO add your handling code here:
        program.setForeground(Color.black);
    }//GEN-LAST:event_programKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton enterButton;
    private javax.swing.JTextField program;
    private javax.swing.JTextField program_name;
    // End of variables declaration//GEN-END:variables
}