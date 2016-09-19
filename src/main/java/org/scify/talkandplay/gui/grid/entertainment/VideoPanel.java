/**
* Copyright 2016 SciFY
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.scify.talkandplay.gui.grid.entertainment;

import java.awt.Image;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.scify.talkandplay.gui.grid.BaseMediaPanel;
import org.scify.talkandplay.gui.grid.GridFrame;
import org.scify.talkandplay.gui.grid.selectors.Selector;
import org.scify.talkandplay.gui.helpers.FileExtensions;
import org.scify.talkandplay.models.User;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class VideoPanel extends BaseMediaPanel {

    private EmbeddedMediaPlayerComponent mediaPlayerComponent;

    private JPanel playPanel;
    private VideoFrame videoFrame;

    public VideoPanel(User user, GridFrame parent) {
        super(user, parent,
                user.getEntertainmentModule().getVideoModule().getFolderPath(),
                FileExtensions.getVideoExtensions());
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        filesPanel = new FilesPanel(user, files, this);

        initComponents();
        initCustomComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 517, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 353, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initCustomComponents() {

        initLayout();
        boolean isEmpty = isEmpty(user.getEntertainmentModule().getVideoModule().getFolderPath());

        if (isEmpty) {
            drawEmpty();
        } else {

            add(filesPanel, c);
            mediaPlayerPanel.getAudioPlayer().getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
                @Override
                public void finished(MediaPlayer mediaPlayer) {
                    setPlayButton();
                }

                @Override
                public void playing(MediaPlayer mediaPlayer) {
                    setPauseButton();
                }

                @Override
                public void paused(MediaPlayer mediaPlayer) {
                    setPlayButton();
                }
            });
        }

        revalidate();
        repaint();
        parent.clearGrid();
        parent.addGrid(this);
        parent.revalidate();
        parent.repaint();

        if (isEmpty) {
            selector.setList(controlsList);
            selector.start();
        } else {
            selector.setList(filesPanel.getPanelList());
            selector.start();
        }
    }

    public void playFile(String fileName) {
        selector.cancel();
        videoFrame = new VideoFrame(user, null, this, filesPanel);

        /*  mediaPlayerComponent.getMediaPlayer().setFullScreenStrategy(
         new DefaultAdaptiveRuntimeFullScreenStrategy(videoFrame) {
         @Override
         protected void beforeEnterFullScreen() {
         System.out.println("fullscreen");
         videoFrame.hidePanel();
         videoFrame.pack();
         }

         @Override
         protected void afterExitFullScreen() {
         System.out.println("exit fullscreen");
         videoFrame.showPanel();
         videoFrame.pack();
         }
         });
         */
        videoFrame.playMedia(getFilePath(fileName));
    }

    public Selector getSelector() {
        return selector;
    }

    public List<JPanel> getControlsList() {
        return controlsList;
    }

    public String getFilePath(String fileName) {
        return user.getEntertainmentModule().getVideoModule().getFolderPath() + fileName;
    }

    private void setPlayButton() {
        ((JLabel) playPanel.getComponent(0)).setText("Αναπαραγωγή");
        ((JLabel) playPanel.getComponent(1)).setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/org/scify/talkandplay/resources/play-button.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));

    }

    private void setPauseButton() {
        ((JLabel) playPanel.getComponent(0)).setText("Παύση");
        ((JLabel) playPanel.getComponent(1)).setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/org/scify/talkandplay/resources/pause-button.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));

    }

    public EmbeddedMediaPlayerComponent getMediaPlayerComponent() {
        return this.mediaPlayerComponent;
    }

    public EmbeddedMediaPlayer getMediaPlayer() {
        return this.mediaPlayerComponent.getMediaPlayer();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
