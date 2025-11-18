package Materia;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Mi PC
 */
public class video extends javax.swing.JPanel {

    private String videoTitulo;
    private String videoDescripcion;
    private int videoNumero;
    private Runnable onEliminarListener;
    
    // Constructor modificado para recibir datos
    public video(String titulo, String descripcion, int numero) {
        initComponents();
        this.videoTitulo = titulo;
        this.videoDescripcion = descripcion;
        this.videoNumero = numero;
        
        // Configurar los datos en la interfaz
        videoName.setText(titulo + " - " + String.format("%02d", numero));
        setListeners();
    }
    
    private void setListeners() {
        // Listener para el botón de eliminar
        deletevidTxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                eliminarVideo();
            }
        });
        
        deletevidBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                eliminarVideo();
            }
        });
        
        // Listener para el botón de play (puedes agregar funcionalidad después)
        playBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                // Aquí puedes agregar la funcionalidad de reproducir video
                JOptionPane.showMessageDialog(video.this, 
                    "Reproduciendo: " + videoTitulo + "\nDescripción: " + videoDescripcion);
            }
        });
    }
    
    private void eliminarVideo() {
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro que desea eliminar el video:\n" + videoTitulo + "?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION && onEliminarListener != null) {
            onEliminarListener.run();
        }
    }
    
    // Método para establecer el listener de eliminación
    public void setOnEliminarListener(Runnable listener) {
        this.onEliminarListener = listener;
    }
    
    // Getters para los datos del video
    public String getVideoTitulo() {
        return videoTitulo;
    }
    
    public String getVideoDescripcion() {
        return videoDescripcion;
    }
    
    public int getVideoNumero() {
        return videoNumero;
    }

    // ... el resto de tu código generado por NetBeans sin cambios
    // [Todo el código generado por el Form Editor se mantiene igual]

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        videoPanel = new javax.swing.JPanel();
        playBtn = new javax.swing.JLabel();
        deletevidBtn = new javax.swing.JPanel();
        deletevidTxt = new javax.swing.JLabel();
        videoName = new javax.swing.JLabel();

        background.setBackground(new java.awt.Color(255, 255, 255));
        background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        videoPanel.setBackground(new java.awt.Color(15, 96, 99));
        videoPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        playBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/playIcon.png"))); // NOI18N
        videoPanel.add(playBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 180, 60));

        deletevidBtn.setBackground(new java.awt.Color(223, 91, 91));

        deletevidTxt.setFont(new java.awt.Font("Questrial", 0, 17)); // NOI18N
        deletevidTxt.setForeground(new java.awt.Color(255, 255, 255));
        deletevidTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        deletevidTxt.setText("x");
        deletevidTxt.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                deletevidTxtMouseMoved(evt);
            }
        });
        deletevidTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deletevidTxtMouseExited(evt);
            }
        });

        javax.swing.GroupLayout deletevidBtnLayout = new javax.swing.GroupLayout(deletevidBtn);
        deletevidBtn.setLayout(deletevidBtnLayout);
        deletevidBtnLayout.setHorizontalGroup(
            deletevidBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
            .addGroup(deletevidBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(deletevidBtnLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(deletevidTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        deletevidBtnLayout.setVerticalGroup(
            deletevidBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
            .addGroup(deletevidBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(deletevidBtnLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(deletevidTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        videoPanel.add(deletevidBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, -1, -1));

        background.add(videoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, 100));

        videoName.setFont(new java.awt.Font("Questrial", 0, 17)); // NOI18N
        videoName.setForeground(new java.awt.Color(0, 0, 0));
        videoName.setText("Video");
        background.add(videoName, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void deletevidTxtMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletevidTxtMouseMoved
        deletevidBtn.setBackground(new Color(191,40,40));
    }//GEN-LAST:event_deletevidTxtMouseMoved

    private void deletevidTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletevidTxtMouseExited
        deletevidBtn.setBackground(new Color(223,91,91));
    }//GEN-LAST:event_deletevidTxtMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JPanel deletevidBtn;
    private javax.swing.JLabel deletevidTxt;
    private javax.swing.JLabel playBtn;
    private javax.swing.JLabel videoName;
    private javax.swing.JPanel videoPanel;
    // End of variables declaration//GEN-END:variables
}
