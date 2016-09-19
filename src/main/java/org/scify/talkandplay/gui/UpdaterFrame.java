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
 * Copyright 2016 scify.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.scify.talkandplay.gui;

import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author christina
 */
public class UpdaterFrame extends javax.swing.JFrame {

    private JLabel messageLabel;
    private JLabel messageLabel2;
    private JLabel messageLabel3;
    private String versionNumber;

    public UpdaterFrame(String versionNumber) {
        this.versionNumber = versionNumber;
        initComponents();
        initCustomComponents();
    }

    private void initCustomComponents() {
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        messageLabel = new JLabel();
        messageLabel.setText("Γίνεται ενημέρωση της εφαρμογής (v."+versionNumber+"), παρακαλώ περιμένετε...");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        this.getContentPane().add(messageLabel);

        messageLabel2 = new JLabel();
        messageLabel2.setText("Η διαδικασία μπορεί να διαρκέσει μερικά λεπτά.");
        messageLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel2.setBorder(new EmptyBorder(10, 0, 10, 0));
        this.getContentPane().add(messageLabel2);

        messageLabel3 = new JLabel();
        messageLabel3.setText("Mε την ολοκλήρωση θα χρειαστεί να ανοίξετε την εφαρμογή ξανά");
        messageLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel3.setBorder(new EmptyBorder(10, 0, 10, 0));
        this.getContentPane().add(messageLabel3);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 528, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 287, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
