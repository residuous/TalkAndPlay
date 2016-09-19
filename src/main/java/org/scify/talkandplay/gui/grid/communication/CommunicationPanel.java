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
package org.scify.talkandplay.gui.grid.communication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.scify.talkandplay.gui.grid.BaseGridPanel;
import org.scify.talkandplay.gui.grid.GridFrame;
import org.scify.talkandplay.gui.grid.tiles.TileAction;
import org.scify.talkandplay.gui.helpers.UIConstants;
import org.scify.talkandplay.models.Category;
import org.scify.talkandplay.models.Tile;
import org.scify.talkandplay.models.User;
import org.scify.talkandplay.services.CategoryService;
import org.scify.talkandplay.services.SensorService;

public class CommunicationPanel extends BaseGridPanel {

    private User user;

    private CategoryService categoryService;
    private SensorService sensorService;

    private Category rootCategory;

    final CommunicationPanel currentPanel = this;

    private int grid;
    private int stopped = 0;

    public CommunicationPanel(User user, GridFrame parent) throws IOException {
        super(user, parent);
        this.categoryService = new CategoryService();
        this.user = user;
        this.sensorService = new SensorService(this.user);
        this.rootCategory = categoryService.getCategoriesWithRootParent(user);
        this.currentCategory = new Category();

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
            .addGap(0, 535, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initCustomComponents() throws IOException {
        setBorder(new EmptyBorder(0, 10, 10, 10));
        initLayout();
        c.gridx = -1;
        c.gridy = 0;

        drawImages(rootCategory);
    }

    /**
     * Draw the categories and their sub categories.
     *
     * @param category
     * @throws IOException
     */
    private void drawImages(Category category) throws IOException {
        if (category.getRows() == null || category.getColumns() == null) {
            UIConstants.getInstance().setRows(user.getConfiguration().getDefaultGridRow());
            UIConstants.getInstance().setColumns(user.getConfiguration().getDefaultGridColumn());
        } else {
            UIConstants.getInstance().setRows(category.getRows());
            UIConstants.getInstance().setColumns(category.getColumns());
        }

        removeAll();
        currentCategory = category;
        c.gridx = -1;
        c.gridy = 0;
        panelList = new ArrayList();

        setGrid(category);

        if (category.getSubCategories().size() > 0) {
            //show only the num of images that fit the grid
            if (category.getSubCategories().size() >= grid) {
                int i;
                for (i = stopped; i < (grid + stopped - 2); i++) {
                    if (i < category.getSubCategories().size() && category.getSubCategories().get(i).isEnabled()) {
                        JPanel panel = createCategoryItem(category.getSubCategories().get(i));
                        add(panel, c);
                        setGrid(category);
                        panelList.add(panel);
                        tileList.add(new Tile(panel, false));
                    }
                }

                stopped = i;
                JPanel panel = createMoreItem(category);
                add(panel, c);
                setGrid(category);
                panelList.add(panel);
                tileList.add(new Tile(panel, false));

                if (i >= category.getSubCategories().size()) {
                    stopped = 0;
                }
            } else {
                for (Category childCategory : category.getSubCategories()) {
                    if (childCategory.isEnabled()) {
                        JPanel panel = createCategoryItem(childCategory);
                        add(panel, c);
                        setGrid(category);
                        panelList.add(panel);
                        tileList.add(new Tile(panel, false));
                    }
                }
            }
        }

        //if parent is null, display the first menu
        if (null == category.getParentCategory()) {
            JPanel panel = createBackItem(category, true);
            add(panel, c);
            setGrid(category);
            panelList.add(panel);
            tileList.add(new Tile(panel, false));
        } else {
            JPanel panel = createBackItem(category, false);
            add(panel, c);
            setGrid(category);
            panelList.add(panel);
            tileList.add(new Tile(panel, false));
        }

        fillWithEmpties();

        revalidate();
        repaint();

        parent.clearGrid();
        parent.addGrid(this);
        parent.revalidate();
        parent.repaint();

        selector.setList(panelList);
        selector.start();
    }

    /**
     * Create the JPanel that holds a category
     *
     * @param category
     * @return
     * @throws IOException
     */
    private JPanel createCategoryItem(final Category category) throws IOException {
        JPanel panel = tileCreator.create(category.getName(),
                category.getImage(),
                category.getSound(),
                new TileAction() {
                    @Override
                    public void act() {
                        selector.cancel();
                        currentCategory = category;
                    }

                    @Override
                    public void audioFinished() {
                        if (currentCategory.getSubCategories().size() > 0) {
                            currentPanel.showNextGrid(currentCategory);
                        } else {
                            selector.setList(panelList);
                            selector.start();
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
        JPanel panel = tileCreator.create("Πίσω",
                null,
                getClass().getResource("/org/scify/talkandplay/resources/back-icon.png"),
                null,
                new TileAction() {
                    @Override
                    public void act() {
                        selector.cancel();
                        stopped = 0;
                        if (isRoot) {
                            showMainMenu();
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

                    @Override
                    public void audioFinished() {
                        return;
                    }

                    @Override
                    public boolean mute() {
                        return true;
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
        JPanel panel = tileCreator.create("Περισσότερα",
                null,
                getClass().getResource("/org/scify/talkandplay/resources/more-icon.png"),
                null,
                new TileAction() {
                    @Override
                    public void act() {
                        selector.cancel();
                        try {
                            drawImages(category);
                        } catch (IOException ex) {
                            Logger.getLogger(CommunicationPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    @Override
                    public void audioFinished() {
                        return;
                    }

                    @Override
                    public boolean mute() {
                        return true;
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
     * Set the grid bag constraints for a certain category
     *
     * @param category
     */
    private void setGrid(Category category) {
        int rows, columns;
        if (category == null || category.getRows() == null || category.getColumns() == null) {
            rows = user.getConfiguration().getDefaultGridRow();
            columns = user.getConfiguration().getDefaultGridColumn();
        } else {
            rows = category.getRows();
            columns = category.getColumns();
        }
        grid = rows * columns;

        if (c.gridx == (columns - 1)) {
            c.gridx = 0;
            c.gridy++;
        } else {
            c.gridx++;
        }
        /* if (c.gridy == (columns - 1)) {
         c.gridy++;
         }*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
