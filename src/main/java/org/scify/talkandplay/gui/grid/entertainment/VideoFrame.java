/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scify.talkandplay.gui.grid.entertainment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import org.scify.talkandplay.gui.helpers.Time;
import org.scify.talkandplay.gui.helpers.UIConstants;
import org.scify.talkandplay.models.User;
import org.scify.talkandplay.models.sensors.KeyboardSensor;
import org.scify.talkandplay.models.sensors.MouseSensor;
import org.scify.talkandplay.models.sensors.Sensor;
import org.scify.talkandplay.services.SensorService;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.DefaultAdaptiveRuntimeFullScreenStrategy;

/**
 *
 * @author christina
 */
public class VideoFrame extends javax.swing.JFrame {

    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private JPanel prevPanel, playPanel, nextPanel, exitPanel;
    private List<JPanel> controlsList;
    private SensorService sensorService;
    private VideoPanel parent;
    private FilesPanel filesPanel;
    private String currentFile;
    private User user;
    private Timer timer;
    private boolean hiddenControls = false;

    public VideoFrame(User user, String currentFile, VideoPanel parent, FilesPanel filesPanel) {
        this.mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        this.sensorService = new SensorService(user);
        this.currentFile = currentFile;
        this.user = user;
        this.parent = parent;
        this.filesPanel = filesPanel;
        this.controlsList = new ArrayList();

        setTitle("Video Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        playerPanel = new javax.swing.JPanel();
        durationSlider = new javax.swing.JSlider();
        startLabel = new javax.swing.JLabel();
        endLabel = new javax.swing.JLabel();
        controlsPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        videoPanel.setLayout(new java.awt.BorderLayout());

        playerPanel.setBackground(new java.awt.Color(255, 255, 255));

        durationSlider.setValue(0);

        startLabel.setText("00:00:00");

        endLabel.setText("00:00:00");

        javax.swing.GroupLayout controlsPanelLayout = new javax.swing.GroupLayout(controlsPanel);
        controlsPanel.setLayout(controlsPanelLayout);
        controlsPanelLayout.setHorizontalGroup(
            controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        controlsPanelLayout.setVerticalGroup(
            controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 107, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout playerPanelLayout = new javax.swing.GroupLayout(playerPanel);
        playerPanel.setLayout(playerPanelLayout);
        playerPanelLayout.setHorizontalGroup(
            playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(playerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(startLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(durationSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(endLabel)
                .addContainerGap())
            .addComponent(controlsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        playerPanelLayout.setVerticalGroup(
            playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(playerPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(endLabel)
                    .addComponent(startLabel)
                    .addComponent(durationSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(controlsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(playerPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(videoPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(videoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initMediaPlayer() {
        mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void playing(MediaPlayer mediaPlayer) {
                mediaPlayerComponent.getMediaPlayer().mute(false);
                mediaPlayerComponent.getMediaPlayer().setVolume(100);
                setPauseButton();
            }

            @Override
            public void paused(MediaPlayer mediaPlayer) {
                setPlayButton();
            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {
                setPlayButton();
            }

            @Override
            public void positionChanged(MediaPlayer mp, float f) {
                int iPos = (int) (f * 100.0);
                durationSlider.setValue(iPos);
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

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.getMediaPlayer().stop();
                mediaPlayerComponent.getMediaPlayer().release();
                e.getWindow().dispose();
            }
        });

        mediaPlayerComponent.getVideoSurface().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor)) {
                    System.out.println("hiddenControls " + hiddenControls);
                    //if (hiddenControls) {
                    System.out.println("clicky");
                    mediaPlayerComponent.getMediaPlayer().pause();
                    playerPanel.setVisible(true);
                    playerPanel.setBackground(Color.yellow);
                    hiddenControls = false;
                    pack();
                    // }
                }
            }
        });

        mediaPlayerComponent.getVideoSurface().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                Sensor sensor = new KeyboardSensor(evt.getKeyCode(), String.valueOf(evt.getKeyChar()), "keyboard");
                if (sensorService.shouldSelect(sensor)) {
                    if (hiddenControls) {
                        playerPanel.setVisible(true);
                        hiddenControls = false;
                    }
                }
            }
        });
    }

    private void initCustomComponents() {
        startLabel.setFont(new Font(UIConstants.getMainFont(), Font.PLAIN, 18));
        endLabel.setFont(new Font(UIConstants.getMainFont(), Font.PLAIN, 18));

        videoPanel.add(mediaPlayerComponent, BorderLayout.CENTER);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mediaPlayerComponent.getMediaPlayer().setFullScreenStrategy(
                new DefaultAdaptiveRuntimeFullScreenStrategy(this)
        );
        mediaPlayerComponent.getMediaPlayer().setFullScreen(true);

        initPlayerButtons();
    }

    private void initPlayerButtons() {
        controlsPanel.setLayout(new GridBagLayout());
        controlsPanel.setBackground(Color.white);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;

        prevPanel = drawButton("Προηγούμενο", getClass().getResource("/org/scify/talkandplay/resources/prev-button.png"));
        playPanel = drawButton("Αναπαραγωγή", getClass().getResource("/org/scify/talkandplay/resources/play-button.png"));
        nextPanel = drawButton("Επόμενο", getClass().getResource("/org/scify/talkandplay/resources/next-button.png"));
        exitPanel = drawButton("Έξοδος", getClass().getResource("/org/scify/talkandplay/resources/exit-icon.png"));

        controlsPanel.add(prevPanel, c);
        c.gridx++;
        controlsPanel.add(playPanel, c);
        c.gridx++;
        controlsPanel.add(nextPanel, c);
        c.gridx++;
        controlsPanel.add(exitPanel, c);

        controlsList.add(prevPanel);
        controlsList.add(playPanel);
        controlsList.add(nextPanel);
        controlsList.add(exitPanel);

        addListeners();
    }

    private JPanel drawButton(String text, URL imageIcon) {
        JLabel label = new JLabel(text);
        label.setBorder(new EmptyBorder(5, 5, 5, 5));
        label.setFont(new Font(UIConstants.getMainFont(), Font.PLAIN, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel icon = new JLabel(new ImageIcon(new ImageIcon(imageIcon).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        icon.setBorder(new EmptyBorder(5, 5, 5, 5));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(Color.decode(UIConstants.getGrey()));
        panel.setPreferredSize(new Dimension(180, 100));
        panel.setMaximumSize(new Dimension(180, 100));
        panel.setMinimumSize(new Dimension(180, 100));
        panel.setBorder((new LineBorder(Color.white, 10)));

        panel.add(label);
        panel.add(icon);
        return panel;
    }

    public void playMedia(String file) {
        currentFile = file;
        mediaPlayerComponent.getMediaPlayer().prepareMedia(file);
        mediaPlayerComponent.getMediaPlayer().parseMedia();

        int secs = (int) (mediaPlayerComponent.getMediaPlayer().getMediaMeta().getLength() / 1000) % 60;
        int mins = (int) ((mediaPlayerComponent.getMediaPlayer().getMediaMeta().getLength() / (1000 * 60)) % 60);
        int hrs = (int) ((mediaPlayerComponent.getMediaPlayer().getMediaMeta().getLength() / (1000 * 60 * 60)) % 24);

        endLabel.setText(Time.getTime(hrs, mins, secs));
        setPauseButton();

        mediaPlayerComponent.getMediaPlayer().playMedia(file);
        setVisible(true);
    }

    public String getFilePath(String fileName) {
        return user.getEntertainmentModule().getVideoModule().getFolderPath() + File.separator + fileName;
    }

    private void setPlayButton() {
        ((JLabel) playPanel.getComponent(0)).setText("Αναπαραγωγή");
        ((JLabel) playPanel.getComponent(1)).setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/org/scify/talkandplay/resources/play-button.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));

    }

    private void setPauseButton() {
        ((JLabel) playPanel.getComponent(0)).setText("Παύση");
        ((JLabel) playPanel.getComponent(1)).setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/org/scify/talkandplay/resources/pause-button.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));

    }

    private void addListeners() {

        prevPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor)) {
                    getPrevious();
                }
            }
        });
        prevPanel.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sensor sensor = new KeyboardSensor(evt.getKeyCode(), String.valueOf(evt.getKeyChar()), "keyboard");
                if (sensorService.shouldSelect(sensor)) {
                    getPrevious();
                }
            }
        });

        nextPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor)) {
                    getNext();
                }
            }
        });
        nextPanel.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sensor sensor = new KeyboardSensor(evt.getKeyCode(), String.valueOf(evt.getKeyChar()), "keyboard");
                if (sensorService.shouldSelect(sensor)) {
                    getNext();
                }
            }
        });

        playPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor) && mediaPlayerComponent.getMediaPlayer().isPlaying()) {
                    mediaPlayerComponent.getMediaPlayer().pause();
                } else {
                    mediaPlayerComponent.getMediaPlayer().play();
                }
            }
        });
        playPanel.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sensor sensor = new KeyboardSensor(evt.getKeyCode(), String.valueOf(evt.getKeyChar()), "keyboard");
                if (sensorService.shouldSelect(sensor) && mediaPlayerComponent.getMediaPlayer().isPlaying()) {
                    mediaPlayerComponent.getMediaPlayer().pause();
                }
            }
        });

        final VideoFrame videoFrame = this;
        exitPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor)) {
                    parent.getTimer().start();
                    videoFrame.dispatchEvent(new WindowEvent(videoFrame, WindowEvent.WINDOW_CLOSING));
                }
            }
        });
        exitPanel.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sensor sensor = new KeyboardSensor(evt.getKeyCode(), String.valueOf(evt.getKeyChar()), "keyboard");
                if (sensorService.shouldSelect(sensor)) {
                    parent.getTimer().start();
                    videoFrame.dispatchEvent(new WindowEvent(videoFrame, WindowEvent.WINDOW_CLOSING));
                }
            }
        });

    }

    private void getPrevious() {
        int selected = filesPanel.getSelected();
        System.out.println(selected);

        if (selected != -1) {
            if (selected == 0) {
                selected = filesPanel.getFileList().size() - 1;
            } else {
                selected--;
            }
            filesPanel.setSelected(selected);
            System.out.println(selected);
            playMedia(getFilePath(filesPanel.getFileList().get(selected)));
        }
    }

    private void getNext() {
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlsPanel;
    private javax.swing.JSlider durationSlider;
    private javax.swing.JLabel endLabel;
    private javax.swing.JPanel playerPanel;
    private javax.swing.JLabel startLabel;
    private javax.swing.JPanel videoPanel;
    // End of variables declaration//GEN-END:variables
}
