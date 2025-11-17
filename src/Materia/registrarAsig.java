package Materia;

import java.awt.Color;
import javax.swing.JOptionPane;
import main.DBConnection;

/**
 *
 * @author Mi PC
 */
public class registrarAsig extends javax.swing.JFrame {

    public registrarAsig() {
        initComponents();
    }

    private Asignatura resultado;

    public Asignatura getResultado() {
        return resultado;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        img = new javax.swing.JPanel();
        accBtn = new javax.swing.JPanel();
        accTxt = new javax.swing.JLabel();
        asigName = new javax.swing.JTextField();
        descrpTxt = new javax.swing.JTextField();
        IDAsig = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(980, 180));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        background.setBackground(new java.awt.Color(247, 247, 247));

        img.setBackground(new java.awt.Color(64, 174, 178));

        javax.swing.GroupLayout imgLayout = new javax.swing.GroupLayout(img);
        img.setLayout(imgLayout);
        imgLayout.setHorizontalGroup(
            imgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 367, Short.MAX_VALUE)
        );
        imgLayout.setVerticalGroup(
            imgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 147, Short.MAX_VALUE)
        );

        accBtn.setBackground(new java.awt.Color(64, 174, 178));

        accTxt.setFont(new java.awt.Font("Questrial", 0, 18)); // NOI18N
        accTxt.setForeground(new java.awt.Color(255, 255, 255));
        accTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        accTxt.setText("Registrar");
        accTxt.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                accTxtMouseMoved(evt);
            }
        });
        accTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                accTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                accTxtMouseExited(evt);
            }
        });

        javax.swing.GroupLayout accBtnLayout = new javax.swing.GroupLayout(accBtn);
        accBtn.setLayout(accBtnLayout);
        accBtnLayout.setHorizontalGroup(
            accBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(accTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
        );
        accBtnLayout.setVerticalGroup(
            accBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(accTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        asigName.setBackground(new java.awt.Color(255, 255, 255));
        asigName.setForeground(new java.awt.Color(0, 0, 0));
        asigName.setText("Asignatura");

        descrpTxt.setBackground(new java.awt.Color(255, 255, 255));
        descrpTxt.setForeground(new java.awt.Color(0, 0, 0));
        descrpTxt.setText("Descripción");

        IDAsig.setBackground(new java.awt.Color(255, 255, 255));
        IDAsig.setForeground(new java.awt.Color(0, 0, 0));
        IDAsig.setText("ID de asignatura");

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(asigName, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(IDAsig, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(descrpTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(accBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(accBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(backgroundLayout.createSequentialGroup()
                            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(asigName, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                                .addComponent(IDAsig))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(descrpTxt))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, 180));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void accTxtMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accTxtMouseMoved
        accBtn.setBackground(new Color(38, 114, 116));
    }//GEN-LAST:event_accTxtMouseMoved

    private void accTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accTxtMouseExited
        accBtn.setBackground(new Color(4, 174, 178));
    }//GEN-LAST:event_accTxtMouseExited

    private void accTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accTxtMouseClicked
        String id = IDAsig.getText().trim();
        String nombre = asigName.getText().trim();
        String descripcion = descrpTxt.getText().trim();

        if (id.isEmpty() || nombre.isEmpty() || descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        resultado = DBConnection.registrarAsignaturaEnBD(id, nombre, descripcion);
        if (resultado == null) {
            JOptionPane.showMessageDialog(this, "No se pudo registrar la asignatura.");
            return;
        }

        this.dispose(); // cerrar la ventana si todo salió bien
    }//GEN-LAST:event_accTxtMouseClicked

    private void accTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accTxtMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_accTxtMouseEntered

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(registrarAsig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(registrarAsig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(registrarAsig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(registrarAsig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new registrarAsig().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField IDAsig;
    private javax.swing.JPanel accBtn;
    private javax.swing.JLabel accTxt;
    private javax.swing.JTextField asigName;
    private javax.swing.JPanel background;
    private javax.swing.JTextField descrpTxt;
    private javax.swing.JPanel img;
    // End of variables declaration//GEN-END:variables
}
