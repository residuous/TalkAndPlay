package org.scify.talkandplay.gui.grid;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.scify.talkandplay.gui.helpers.GuiHelper;
import org.scify.talkandplay.models.Category;
import org.scify.talkandplay.models.User;
import org.scify.talkandplay.models.sensors.MouseSensor;
import org.scify.talkandplay.models.sensors.Sensor;
import org.scify.talkandplay.services.CategoryService;
import org.scify.talkandplay.services.SensorService;
import org.scify.talkandplay.services.UserService;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class CommunicationPanel extends javax.swing.JPanel {

    private User user;
    private Timer timer;
    private int selectedImage;
    private List<JPanel> panelList;
    private GridLayout gridLayout;
    private GridFrame parent;

    private CategoryService categoryService;
    private UserService userService;
    private SensorService sensorService;

    private AudioMediaPlayerComponent audioPlayer;
    private Category rootCategory;
    private Category currentCategory;
    private GuiHelper guiHelper;
    private int rows, columns, grid;
    private int stopped = 0;

    protected final int BORDER_SIZE = 5;
    protected final int IMAGE_PADDING = 10;

    public CommunicationPanel(String userName, GridFrame parent) throws IOException {
        this.categoryService = new CategoryService();
        this.userService = new UserService();
        this.audioPlayer = new AudioMediaPlayerComponent();
        this.user = userService.getUser(userName);
        this.sensorService = new SensorService(this.user);
        this.rootCategory = categoryService.getCategoriesWithRootParent(user);
        this.currentCategory = new Category();
        this.parent = parent;
        this.guiHelper = new GuiHelper();

        initComponents();
        initAudioPlayer();
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

        imagesPanel = new javax.swing.JPanel();

        javax.swing.GroupLayout imagesPanelLayout = new javax.swing.GroupLayout(imagesPanel);
        imagesPanel.setLayout(imagesPanelLayout);
        imagesPanelLayout.setHorizontalGroup(
            imagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 535, Short.MAX_VALUE)
        );
        imagesPanelLayout.setVerticalGroup(
            imagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imagesPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imagesPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initAudioPlayer() {
        final CommunicationPanel currentPanel = this;
        audioPlayer.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void playing(MediaPlayer mediaPlayer) {
                //audioPlayer.getMediaPlayer().setVolume(100);
                audioPlayer.getMediaPlayer().mute(false);
            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {
                if (currentCategory.getSubCategories().size() > 0) {
                    currentPanel.showNextGrid(currentCategory);
                } else {
                    setTimer();
                }
            }
        });

        parent.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                audioPlayer.getMediaPlayer().stop();
                audioPlayer.getMediaPlayer().stop();
                e.getWindow().dispose();
            }
        });
    }

    private void initCustomComponents() throws IOException {
        imagesPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
        gridLayout = new GridLayout();
        gridLayout.setHgap(IMAGE_PADDING);
        gridLayout.setVgap(IMAGE_PADDING);

        drawImages(rootCategory);
    }

    private void drawImages(Category category) throws IOException {
        selectedImage = 0;
        imagesPanel.removeAll();
        panelList = new ArrayList<>();
        int emptiesCount = 0;

        setGrid(category);

        if (category.getSubCategories().size() > 0) {
            //show only the num of images that fit the grid
            if (category.getSubCategories().size() >= grid) {
                int i;
                for (i = stopped; i < (grid + stopped - 2); i++) {
                    if (i > category.getSubCategories().size() - 1) {
                        emptiesCount++;
                    } else {
                        imagesPanel.add(createCategoryItem(category.getSubCategories().get(i)));
                    }
                }
                if (i <= category.getSubCategories().size() - 1) {
                    stopped = i;
                    imagesPanel.add(createMoreItem(category));
                } else {
                    stopped = 0;
                }
            } else {
                for (Category childCategory : category.getSubCategories()) {
                    imagesPanel.add(createCategoryItem(childCategory));
                }
            }
        } else if (category.getTiles().size() > 0) {
            //draw the tile images
        }

        //if parent is null, display the first menu
        if (emptiesCount == 0 && null == category.getParentCategory()) {
            imagesPanel.add(createBackItem(category, true));
        } else if (emptiesCount == 0 && null != category.getParentCategory()) {
            imagesPanel.add(createBackItem(category, false));
        }

        //check if there's empty space that should be filled with
        //mock JLabels in order to keep the grid size
        if (emptiesCount > 0) {
            imagesPanel.add(createLessItem(category));
            for (int i = 0; i < emptiesCount; i++) {
                imagesPanel.add(new JLabel());
            }
        }
        setTimer();

        imagesPanel.revalidate();
        imagesPanel.repaint();
        parent.add(imagesPanel);
        parent.revalidate();
        parent.repaint();
    }

    /**
     * Create the JPanel that holds a category
     *
     * @param category
     * @return
     * @throws IOException
     */
    private JPanel createCategoryItem(final Category category) throws IOException {
        JPanel panel = guiHelper.createImagePanel(category.getImage(), category.getName(), parent);
        panelList.add(panel);
        imagesPanel.add(panel);

        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor)) {
                    timer.cancel();
                    currentCategory = category;
                    audioPlayer.getMediaPlayer().playMedia(category.getSound());
                }
            }
        });

        return panel;
    }

    /**
     * Create the JPanel that holds the back button
     *
     * @param category
     * @return
     * @throws IOException
     */
    private JPanel createBackItem(final Category category, final boolean isRoot) throws IOException {
        JPanel panel = guiHelper.createResourceImagePanel((new ImageIcon(getClass().getResource("/org/scify/talkandplay/resources/back-icon.png"))), "Πίσω", parent);
        panelList.add(panel);
        imagesPanel.add(panel);

        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor)) {
                    timer.cancel();
                    stopped = 0;
                    if (isRoot) {
                        parent.repaintMenu(imagesPanel);
                    } else if (!isRoot && category.getParentCategory() == null) {
                        try {
                            drawImages(rootCategory);
                        } catch (IOException ex) {
                            Logger.getLogger(CommunicationPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        try {
                            drawImages(category.getParentCategory());
                        } catch (IOException ex) {
                            Logger.getLogger(CommunicationPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
        return panel;
    }

    /**
     * Create the JPanel that holds the more button
     *
     * @param category
     * @return
     * @throws IOException
     */
    private JPanel createMoreItem(final Category category) throws IOException {
        JPanel panel = guiHelper.createResourceImagePanel((new ImageIcon(getClass().getResource("/org/scify/talkandplay/resources/more-icon.png"))), "Περισσότερα", parent);
        panelList.add(panel);
        imagesPanel.add(panel);

        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor)) {
                    timer.cancel();
                    try {
                        drawImages(category);
                    } catch (IOException ex) {
                        Logger.getLogger(CommunicationPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        return panel;
    }

    /**
     * Create the JPanel that holds the less button
     *
     * @param category
     * @return
     * @throws IOException
     */
    private JPanel createLessItem(final Category category) throws IOException {
        JPanel panel = guiHelper.createResourceImagePanel((new ImageIcon(getClass().getResource("/org/scify/talkandplay/resources/less-icon.png"))), "Λιγότερα", parent);
        panelList.add(panel);
        imagesPanel.add(panel);

        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sensor sensor = new MouseSensor(evt.getButton(), evt.getClickCount(), "mouse");
                if (sensorService.shouldSelect(sensor)) {
                    timer.cancel();
                    try {
                        drawImages(category);
                    } catch (IOException ex) {
                        Logger.getLogger(CommunicationPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        return panel;
    }

    private void showNextGrid(Category category) {
        try {
            drawImages(category);
        } catch (IOException ex) {
            Logger.getLogger(CommunicationPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Set the grid dimensions. Rows is always set to 0, because if rows>0, the
     * columns are ignored
     *
     * @param category
     */
    private void setGrid(Category category) {
        if (category != null) {
            rows = category.getRows();
            columns = category.getColumns();
            grid = rows * columns;
        } else {
            rows = user.getConfiguration().getDefaultGridRow();
            columns = user.getConfiguration().getDefaultGridColumn();
            grid = rows * columns;
        }
        gridLayout.setColumns(columns);
        gridLayout.setRows(0);
        imagesPanel.setLayout(gridLayout);
    }

    private void setTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (selectedImage == 0) {
                    panelList.get(panelList.size() - 1).setBorder(null);
                    panelList.get(selectedImage).setBorder(BorderFactory.createLineBorder(Color.BLUE, BORDER_SIZE));
                    selectedImage++;
                } else if (selectedImage == panelList.size() - 1) {
                    panelList.get(selectedImage - 1).setBorder(null);
                    panelList.get(selectedImage).setBorder(BorderFactory.createLineBorder(Color.BLUE, BORDER_SIZE));
                    selectedImage = 0;
                } else if (selectedImage < panelList.size() - 1 && selectedImage > 0) {
                    panelList.get(selectedImage - 1).setBorder(null);
                    panelList.get(selectedImage).setBorder(BorderFactory.createLineBorder(Color.BLUE, BORDER_SIZE));
                    selectedImage++;
                }
            }
        }, user.getConfiguration().getRotationSpeed() * 1000, user.getConfiguration().getRotationSpeed() * 1000);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel imagesPanel;
    // End of variables declaration//GEN-END:variables

}
