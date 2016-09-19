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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scify.talkandplay.gui.grid.entertainment;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.scify.talkandplay.gui.grid.selectors.ButtonSelector;
import org.scify.talkandplay.gui.grid.selectors.ManualButtonSelector;
import org.scify.talkandplay.gui.grid.selectors.MouseSelector;
import org.scify.talkandplay.gui.grid.selectors.Selector;
import org.scify.talkandplay.gui.helpers.Time;
import org.scify.talkandplay.gui.helpers.UIConstants;
import org.scify.talkandplay.models.User;
import org.scify.talkandplay.models.sensors.KeyboardSensor;
import org.scify.talkandplay.models.sensors.MouseSensor;
import org.scify.talkandplay.models.sensors.Sensor;
import org.scify.talkandplay.services.SensorService;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 *
 * @author christina
 */
public class VideoFrame extends javax.swing.JFrame {

    private EmbeddedMediaPlayer mediaPlayer;
    private JPanel emptyPanel;
    private VideoPanel parent;
    private FilesPanel filesPanel;
    private String currentFile;
    private User user;
    private Selector selector;
    private PlayerPanel playerPanel;
    private SensorService sensorService;

    public VideoFrame(User user, String currentFile, VideoPanel parent, FilesPanel filesPanel) {
        this.mediaPlayer = parent.getMediaPlayer();
        this.currentFile = currentFile;
        this.user = user;
        this.parent = parent;
        this.filesPanel = filesPanel;
        this.emptyPanel = new JPanel();
        this.sensorService = new SensorService(user);

        if (user.getConfiguration().getSelectionSensor() instanceof MouseSensor) {
            this.selector = new MouseSelector(null, user.getConfiguration().getRotationSpeed() * 1000, user.getConfiguration().getRotationSpeed() * 1000);
        } else if (user.getConfiguration().getNavigationSensor() != null) {
            this.selector = new ManualButtonSelector(user, null, user.getConfiguration().getRotationSpeed() * 1000, user.getConfiguration().getRotationSpeed() * 1000);
        } else {
            this.selector = new ButtonSelector(null, user.getConfiguration().getRotationSpeed() * 1000, user.getConfiguration().getRotationSpeed() * 1000);
        }

        this.playerPanel = new PlayerPanel(sensorService);

        setTitle("Video Player");
        setVisible(false);
        setIconImage((new ImageIcon(getClass().getResource("/org/scify/talkandplay/resources/tp_logo_mini.png"))).getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        initComponents();
        initMediaPlayer();
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

        videoPanel = new javax.swing.JPanel();
        wrapperPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        videoPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout wrapperPanelLayout = new javax.swing.GroupLayout(wrapperPanel);
        wrapperPanel.setLayout(wrapperPanelLayout);
        wrapperPanelLayout.setHorizontalGroup(
            wrapperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        wrapperPanelLayout.setVerticalGroup(
            wrapperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(videoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addComponent(wrapperPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(videoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wrapperPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initMediaPlayer() {

        mediaPlayer.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void playing(MediaPlayer mediaPlayer) {
                mediaPlayer.mute(false);
                mediaPlayer.setVolume(100);
                playerPanel.setPauseButton();
            }

            @Override
            public void paused(MediaPlayer mediaPlayer) {
                playerPanel.setPlayButton();
            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {
                playerPanel.setPlayButton();
            }

            @Override
            public void positionChanged(MediaPlayer mp, float f) {
                int iPos = (int) (f * 100.0);
                playerPanel.setDurationSlider(iPos);
            }

            @Override
            public void timeChanged(MediaPlayer mediaPlayer, final long newTime) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        playerPanel.setStartLabelText(String.format("%s", Time.formatTime(newTime)));
                    }
                });
            }
        });

        videoPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor)) {
                    if (mediaPlayer.isFullScreen()) {
                        mediaPlayer.setFullScreen(false);
                    }
                }
            }
        });
        videoPanel.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sensor sensor = new KeyboardSensor(evt.getKeyCode(), String.valueOf(evt.getKeyChar()), "keyboard");
                if (sensorService.shouldSelect(sensor)) {
                    if (mediaPlayer.isFullScreen()) {
                        mediaPlayer.setFullScreen(false);
                    }
                }
            }
        });
    }

    private void initCustomComponents() {
        videoPanel.add(parent.getMediaPlayerComponent(), BorderLayout.CENTER);

        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
        wrapperPanel.add(playerPanel);

        emptyPanel.setSize(playerPanel.getWidth(), playerPanel.getHeight());

        addListeners();
    }

    public void playMedia(String file) {
        currentFile = file;
        mediaPlayer.prepareMedia(file);
        mediaPlayer.parseMedia();

        int secs = (int) (mediaPlayer.getMediaMeta().getLength() / 1000) % 60;
        int mins = (int) ((mediaPlayer.getMediaMeta().getLength() / (1000 * 60)) % 60);
        int hrs = (int) ((mediaPlayer.getMediaMeta().getLength() / (1000 * 60 * 60)) % 24);

        playerPanel.setEndLabel(Time.getTime(hrs, mins, secs));
        playerPanel.setPauseButton();
        mediaPlayer.playMedia(file);
        setVisible(true);

        selector.setDefaultBackgroundColor(UIConstants.grey);
        selector.setList(playerPanel.getControlPanels());
        selector.start();
    }

    private void addListeners() {

        playerPanel.getPrevPanel().addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor)) {
                    getPrevious();
                }
            }
        });
        playerPanel.getPrevPanel().addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sensor sensor = new KeyboardSensor(evt.getKeyCode(), String.valueOf(evt.getKeyChar()), "keyboard");
                if (sensorService.shouldSelect(sensor)) {
                    getPrevious();
                }
            }
        });

        playerPanel.getNextPanel().addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor)) {
                    getNext();
                }
            }
        });
        playerPanel.getNextPanel().addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sensor sensor = new KeyboardSensor(evt.getKeyCode(), String.valueOf(evt.getKeyChar()), "keyboard");
                if (sensorService.shouldSelect(sensor)) {
                    getNext();
                }
            }
        });

        playerPanel.getPlayPanel().addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor) && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else if (sensorService.shouldSelect(sensor) && !mediaPlayer.isPlaying()) {
                    mediaPlayer.play();
                }
            }
        });
        playerPanel.getPlayPanel().addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sensor sensor = new KeyboardSensor(evt.getKeyCode(), String.valueOf(evt.getKeyChar()), "keyboard");
                if (sensorService.shouldSelect(sensor) && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else if (sensorService.shouldSelect(sensor) && !mediaPlayer.isPlaying()) {
                    mediaPlayer.play();
                }
            }
        });

        /* playerPanel.getFullscreenPanel().addMouseListener(new MouseAdapter() {
         public void mouseClicked(java.awt.event.MouseEvent evt) {
         Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
         if (sensorService.shouldSelect(sensor)) {
         videoPanel.setFocusable(true);
         mediaPlayer.toggleFullScreen();
         }
         }
         });
         playerPanel.getFullscreenPanel().addKeyListener(new KeyAdapter() {
         public void keyPressed(java.awt.event.KeyEvent evt) {
         Sensor sensor = new KeyboardSensor(evt.getKeyCode(), String.valueOf(evt.getKeyChar()), "keyboard");
         if (sensorService.shouldSelect(sensor)) {
         videoPanel.setFocusable(true);
         mediaPlayer.toggleFullScreen();
         }
         }
         });*/
        playerPanel.getExitPanel().addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor)) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.play();
                    }
                    selector.cancel();
                    mediaPlayer.stop();
                    parent.getSelector().start();
                    dispose();
                }
            }
        });
        playerPanel.getExitPanel().addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sensor sensor = new KeyboardSensor(evt.getKeyCode(), String.valueOf(evt.getKeyChar()), "keyboard");
                if (sensorService.shouldSelect(sensor)) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.play();
                    }                    
                    selector.cancel();
                    mediaPlayer.stop();
                    parent.getSelector().start();
                    dispose();
                }
            }
        });
    }

    private void getPrevious() {
        selector.cancel();
        int selected = filesPanel.getSelected();

        if (selected != -1) {
            if (selected == 0) {
                selected = filesPanel.getFileList().size() - 1;
            } else {
                selected--;
            }
            filesPanel.setSelected(selected);
            playMedia(getFilePath(filesPanel.getFileList().get(selected)));
        }
    }

    private void getNext() {
        selector.cancel();
        int selected = filesPanel.getSelected();
        if (selected != -1) {
            if (selected == filesPanel.getFileList().size() - 1) {
                selected = 0;
            } else {
                selected++;
            }
            filesPanel.setSelected(selected);
            playMedia(getFilePath(filesPanel.getFileList().get(selected)));
        }
    }

    public String getFilePath(String fileName) {
        return user.getEntertainmentModule().getVideoModule().getFolderPath() + File.separator + fileName;
    }

    /*  public void showPanel() {
     wrapperPanel.remove(emptyPanel);
     playerPanel.setVisible(true);
     emptyPanel.setVisible(false);
     }

     public void hidePanel() {
     emptyPanel.setBackground(Color.yellow);
     wrapperPanel.add(emptyPanel);
     emptyPanel.setVisible(true);
     playerPanel.setVisible(false);
     }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel videoPanel;
    private javax.swing.JPanel wrapperPanel;
    // End of variables declaration//GEN-END:variables
}
