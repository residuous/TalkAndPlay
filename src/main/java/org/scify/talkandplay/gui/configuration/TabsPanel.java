package org.scify.talkandplay.gui.configuration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.scify.talkandplay.gui.helpers.UIConstants;
import org.scify.talkandplay.models.User;

public class TabsPanel extends javax.swing.JPanel {

    private User user;
    private ConfigurationPanel parent;
    private CommunicationTab communicationPanel;
    private EntertainmentPanel entertainmentPanel;

    public TabsPanel(User user, ConfigurationPanel parent) {
        this.user = user;
        this.parent = parent;

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

        tabsPanel = new javax.swing.JTabbedPane();

        setBackground(new java.awt.Color(255, 255, 255));

        tabsPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 774, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initCustomComponents() {

        communicationPanel = new CommunicationTab(user, parent);
        entertainmentPanel = new EntertainmentPanel(user, parent);

        tabsPanel.addTab("Επικοινωνία", communicationPanel);
        tabsPanel.addTab("Ψυχαγωγία", entertainmentPanel);
        tabsPanel.addTab("Παιχνίδια", null);

        tabsPanel.setSelectedIndex(0);
        tabsPanel.setForegroundAt(0, Color.white);
        tabsPanel.setFont(new Font(UIConstants.getMainFont(), Font.PLAIN, 18));

        tabsPanel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                for (int i = 0; i < tabsPanel.getTabCount(); i++) {
                    tabsPanel.setForegroundAt(i, Color.decode(UIConstants.getMainColor()));
                }
                tabsPanel.setForegroundAt(tabsPanel.getSelectedIndex(), Color.white);

                if (tabsPanel.getSelectedIndex() == 0) {
                    parent.showInfoPanel();
                }
                if (tabsPanel.getSelectedIndex() == 1) {
                    parent.hideInfoPanel();
                }
            }
        });
    }

    public void redrawCategoriesList() {
        communicationPanel.redrawCategoriesList();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane tabsPanel;
    // End of variables declaration//GEN-END:variables
}