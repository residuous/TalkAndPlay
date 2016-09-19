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
package org.scify.talkandplay.gui.grid;

import java.awt.GridBagConstraints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.scify.talkandplay.gui.helpers.UIConstants;
import org.scify.talkandplay.models.User;
import org.scify.talkandplay.services.UserService;

public class GridFrame extends javax.swing.JFrame {

    private User user;
    private UserService userService;
    private GridBagConstraints c;

    public GridFrame(String userName) throws IOException {
        this.userService = new UserService();
        this.user = userService.getUser(userName);

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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        contentPane = new javax.swing.JPanel();
        gridPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Talk&Play");

        contentPane.setBackground(new java.awt.Color(255, 255, 255));
        contentPane.setPreferredSize(new java.awt.Dimension(800, 720));

        gridPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout contentPaneLayout = new javax.swing.GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gridPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPaneLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(gridPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentPane, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initCustomComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        UIConstants.getInstance().setHeight(getHeight());
        UIConstants.getInstance().setWidth(getWidth());

        setIconImage((new ImageIcon(getClass().getResource("/org/scify/talkandplay/resources/tp_logo_mini.png"))).getImage());
        gridPanel.setLayout(new BoxLayout(gridPanel, BoxLayout.LINE_AXIS));
        gridPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridPanel panel = new GridPanel(user, this);
        pack();
    }

    public void clearGrid() {
        gridPanel.removeAll();
    }

    public void addGrid(JPanel panel) {
        gridPanel.add(panel, c);
    }

    /*
     configureButton.setEnabled(false);

     ConfigurationFrame configurationFrame = new ConfigurationFrame(this);
     configurationFrame.setLocationRelativeTo(null);
     configurationFrame.setVisible(true);
     configurationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     configurationFrame.addWindowListener(new WindowAdapter() {
     @Override
     public void windowClosing(WindowEvent we) {
     configureButton.setEnabled(true);
     }
     });
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPane;
    private javax.swing.JPanel gridPanel;
    private javax.swing.JPopupMenu jPopupMenu1;
    // End of variables declaration//GEN-END:variables
}
