/*
 *  Copyright (c) 2018 Cerus
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Cerus
 *
 */

package de.cerus.minecraftscreenshotsorter.gui;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AboutGui extends JFrame {

    public AboutGui() {
        initialize();
    }

    private void initialize() {
        setTitle("About");
        setSize(315, 115); // odl width 245, old height 100
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        JTextArea area = new JTextArea("This application was made by Cerus \n and modified by Mondstation. \nGitHub: https://github.com/RealCerus\nDiscord: https://discord.gg/zTDkWcj");
        area.setBounds(5, 5, 385, 185);
        area.setEditable(false);
        area.setBackground(getBackground());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().setVisible(false);
                e.getWindow().dispose();
            }
        });

        add(area);
    }
}
