package org.scify.talkandplay.gui.grid.entertainment;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.scify.talkandplay.gui.helpers.Time;
import org.scify.talkandplay.gui.helpers.UIConstants;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

/**
 *
 * @author christina
 */
public class MediaPlayerPanel extends javax.swing.JPanel {

    private AudioMediaPlayerComponent audioPlayer;
    private JPanel parent;

    /**
     * Creates new form MediaPlayerPanel
     */
    public MediaPlayerPanel(JPanel parent) {
        this.parent = parent;
        this.audioPlayer = new AudioMediaPlayerComponent();
        initComponents();
        initAudioPlayer();
        initCustomComponents();
    }

    private void initAudioPlayer() {

        mediaSlider.setEnabled(false);
        audioPlayer.getMediaPlayer().mute(false);
        audioPlayer.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {

            @Override
            public void opening(MediaPlayer mediaPlayer) {
            }

            @Override
            public void playing(MediaPlayer mediaPlayer) {
                audioPlayer.getMediaPlayer().mute(false);
                // audioPlayer.getMediaPlayer().setVolume(100);
            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {
                if (parent instanceof MusicPanel) {
                    // String nextFile = ((MusicPanel) parent).getNextFile();
                    // ((MusicPanel) parent).setSelected();
                    //  playMedia(((MusicPanel) parent).getFilePath(nextFile));
                }
            }

            @Override
            public void positionChanged(MediaPlayer mp, float f) {
                int iPos = (int) (f * 100.0);
                mediaSlider.setValue(iPos);
            }

            @Override
            public void timeChanged(MediaPlayer mediaPlayer, final long newTime) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        startLabel.setText(String.format("%s", Time.formatTime(newTime)));
                    }
                });
            }
        });

        /*
         gridFrame.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
         audioPlayer.getMediaPlayer().stop();
         audioPlayer.getMediaPlayer().stop();
         e.getWindow().dispose();
         }
         });*/
    }

    private void initCustomComponents() {

        startLabel.setFont(new Font(UIConstants.getMainFont(), Font.PLAIN, 18));
        endLabel.setFont(new Font(UIConstants.getMainFont(), Font.PLAIN, 18));
    }

    public void playMedia(String path) {
        System.out.println("Now playing " + path);
        mediaSlider.setValue(0);
        audioPlayer.getMediaPlayer().prepareMedia(path);
        audioPlayer.getMediaPlayer().parseMedia();

        int secs = (int) (audioPlayer.getMediaPlayer().getMediaMeta().getLength() / 1000) % 60;
        int mins = (int) ((audioPlayer.getMediaPlayer().getMediaMeta().getLength() / (1000 * 60)) % 60);
        int hrs = (int) ((audioPlayer.getMediaPlayer().getMediaMeta().getLength() / (1000 * 60 * 60)) % 24);

        endLabel.setText(Time.getTime(hrs, mins, secs));
        audioPlayer.getMediaPlayer().playMedia(path);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mediaPlayerPanel = new javax.swing.JPanel();
        mediaSlider = new javax.swing.JSlider();
        startLabel = new javax.swing.JLabel();
        endLabel = new javax.swing.JLabel();

        mediaPlayerPanel.setBackground(new java.awt.Color(255, 255, 255));

        mediaSlider.setToolTipText("");
        mediaSlider.setValue(0);

        startLabel.setText("00:00:00");

        endLabel.setText("00:00:00");

        javax.swing.GroupLayout mediaPlayerPanelLayout = new javax.swing.GroupLayout(mediaPlayerPanel);
        mediaPlayerPanel.setLayout(mediaPlayerPanelLayout);
        mediaPlayerPanelLayout.setHorizontalGroup(
            mediaPlayerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mediaPlayerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(startLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mediaSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(endLabel)
                .addContainerGap())
        );
        mediaPlayerPanelLayout.setVerticalGroup(
            mediaPlayerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mediaPlayerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mediaPlayerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mediaSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startLabel)
                    .addComponent(endLabel))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mediaPlayerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mediaPlayerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public AudioMediaPlayerComponent getAudioPlayer() {
        return this.audioPlayer;
    }

    public boolean isPlaying() {
        return audioPlayer.getMediaPlayer().isPlaying();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel endLabel;
    private javax.swing.JPanel mediaPlayerPanel;
    private javax.swing.JSlider mediaSlider;
    private javax.swing.JLabel startLabel;
    // End of variables declaration//GEN-END:variables
}
