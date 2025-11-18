package Materia;

import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent; // ¡Importante! Usaremos este componente simplificado.

import javax.swing.*;
import java.awt.*;

public class PanelReproductor extends JPanel {

    // 1. Declarar el componente de reproductor de VLCJ
    private EmbeddedMediaPlayerComponent mediaPlayerComponent;

    public PanelReproductor() {
        // Inicializar el componente VLCJ
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        
        // Configurar el layout del panel para que el reproductor ocupe todo el espacio
        setLayout(new BorderLayout());
        add(mediaPlayerComponent, BorderLayout.CENTER);
        
        // Opcional: Configuración inicial del panel (puede ser gris, etc.)
        setBackground(Color.BLACK);
        
        // Llamada a initComponents (generado por el IDE), aunque ya lo hemos hecho
        // manualmente con BorderLayout.
        // initComponents(); // Comentar o borrar si se usa BorderLayout
    }

    // 2. Método público para reproducir el video
    public void reproducirVideo(String mediaPath) {
        if (mediaPath == null || mediaPath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error: La ruta del video es inválida.", "Error de Reproducción", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener el reproductor interno y reproducir el medio
        EmbeddedMediaPlayer player = mediaPlayerComponent.mediaPlayer();
        
        // Detener y limpiar cualquier medio anterior
        if (player.status().isPlaying()) {
            player.controls().stop();
        }
        
        // Reproducir el nuevo medio (la ruta del archivo)
        player.media().play(mediaPath);
    }
    
    // Opcional: Método para detener la reproducción si es necesario
    public void detenerVideo() {
        if (mediaPlayerComponent != null && mediaPlayerComponent.mediaPlayer().status().isPlaying()) {
            mediaPlayerComponent.mediaPlayer().controls().stop();
        }
    }
    
    // Opcional: Liberar recursos de VLCJ al cerrar la aplicación
    public void liberarRecursos() {
        if (mediaPlayerComponent != null) {
            mediaPlayerComponent.release();
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 799, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 449, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
