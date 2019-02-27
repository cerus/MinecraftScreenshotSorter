package net.mondstation.minecraftscreenshotsorter;

/*
 * Copyright (c) 2019 Mondstation
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
 *
 * Contributors:
 *  Mondstation
 */

import de.cerus.minecraftscreenshotsorter.gui.AboutGui;
import de.cerus.minecraftscreenshotsorter.gui.MainGui;
import de.cerus.minecraftscreenshotsorter.language.EnglishLanguage;
import de.cerus.minecraftscreenshotsorter.language.GermanLanguage;
import net.mondstation.minecraftscreenshotsorter.modifications.AutoSorter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ModificationLauncher {

    private static ModificationLauncher instance;

    private AutoSorter sorter;

    private MainGui mainGui;
    private MainGui germanMainGui;
    private AboutGui aboutGui;
    private Image icon;

    public static ModificationLauncher getInstance() {
        return instance;
    }

    public static void setInstance(final ModificationLauncher instance) {
        ModificationLauncher.instance = instance;
    }

    public void launch() throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.icon = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("tray.png")));
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.mainGui = new MainGui(new EnglishLanguage());
        this.aboutGui = new AboutGui();

        if (SystemTray.isSupported()) {
            this.setupTrayIcon();
        } else {
            this.mainGui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }

        this.mainGui.setIconImage(this.icon);

        JMenuBar menuBar = this.mainGui.getJMenuBar();
        JMenu autoSortMenu = new JMenu("Auto Sort new Screenshots");
        JMenuItem yesItem = new JMenuItem("Yes");
        JMenuItem noItem = new JMenuItem("No");

        autoSortMenu.add(yesItem);
        autoSortMenu.add(noItem);

        autoSortMenu.addActionListener(event -> {
            if (event.getSource() == yesItem) {
                if (this.mainGui.getConfiguration().isAutoSortNewScreenshots())
                    return;

                this.mainGui.getConfiguration().setAutoSortNewScreenshots(true);
                this.sorter.activate();
            }

            if (event.getSource() == noItem) {
                if (!this.mainGui.getConfiguration().isAutoSortNewScreenshots())
                    return;

                this.mainGui.getConfiguration().setAutoSortNewScreenshots(false);
                this.sorter.deactivate();
            }

            if (this.mainGui.getConfiguration().isAutoSortNewScreenshots()) {
                if (this.sorter.activate())
                    return;

                JOptionPane.showMessageDialog(null, "Please set the screenshot directory.", "", JOptionPane.ERROR_MESSAGE);
            } else {
                this.sorter.deactivate();
            }
        });

        menuBar.add(autoSortMenu);
        this.mainGui.setJMenuBar(menuBar);

        this.mainGui.setVisible(true);

        Executors.newSingleThreadExecutor().execute(() -> {
            this.germanMainGui = new MainGui(new GermanLanguage());
            this.germanMainGui.setIconImage(this.icon);

            JMenuBar germanMenuBar = this.germanMainGui.getJMenuBar();
            JMenu germanAutoSortMenu = new JMenu("Automatische Sortierung von neuen Screenshots");
            JMenuItem jaItem = new JMenuItem("Ja");
            JMenuItem neinItem = new JMenuItem("Nein");

            germanAutoSortMenu.add(jaItem);
            germanAutoSortMenu.add(neinItem);

            germanAutoSortMenu.addActionListener(event -> {
                if (event.getSource() == jaItem) {
                    if (this.mainGui.getConfiguration().isAutoSortNewScreenshots())
                        return;

                    this.mainGui.getConfiguration().setAutoSortNewScreenshots(true);
                    this.sorter.activate();
                }

                if (event.getSource() == neinItem) {
                    if (!this.mainGui.getConfiguration().isAutoSortNewScreenshots())
                        return;

                    this.mainGui.getConfiguration().setAutoSortNewScreenshots(false);
                    this.sorter.deactivate();
                }

                if (this.mainGui.getConfiguration().isAutoSortNewScreenshots()) {
                    if (this.sorter.activate())
                        return;

                    JOptionPane.showMessageDialog(null, "Bitte setze den Screenshot Ordner.", "", JOptionPane.ERROR_MESSAGE);
                } else {
                    this.sorter.deactivate();
                }
            });

            germanMenuBar.add(germanAutoSortMenu);

            this.germanMainGui.setJMenuBar(germanMenuBar);
        });

        this.sorter = new AutoSorter();
        this.sorter.setConfiguration(this.mainGui.getConfiguration());
        this.sorter.activate();

        setInstance(this);
    }

    private void setupTrayIcon() {
        TrayIcon trayIcon = new TrayIcon(this.icon);

        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Minecraft Screenshot Sorter");
        trayIcon.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(final MouseEvent event) {
                if (event.getClickCount() != 2)
                    return;

                if (!mainGui.isVisible())
                    mainGui.setVisible(true);
            }
        });

        PopupMenu popupMenu = new PopupMenu();
        MenuItem showItem = new MenuItem("Show");

        showItem.addActionListener( event -> {
            if (this.germanMainGui.isVisible())
                return;

            if (!this.mainGui.isVisible())
                this.mainGui.setVisible(true);
        });

        popupMenu.add(showItem);
        popupMenu.addSeparator();

        MenuItem exitItem = new MenuItem("Exit");

        exitItem.addActionListener(event -> {
            if (this.mainGui.getConfiguration().isAutoSortNewScreenshots()) {
                this.sorter.deactivate();
            }

            if (this.mainGui.isVisible())
                this.mainGui.setVisible(false);

            if (this.aboutGui.isVisible())
                this.aboutGui.setVisible(false);

            if (this.mainGui.getThread() != null) {
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                executor.schedule(() -> System.exit(0), 3, TimeUnit.MINUTES);
                executor.shutdown();
            }

            System.exit(0);
        });

        popupMenu.add(exitItem);

        trayIcon.setPopupMenu(popupMenu);
        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public MainGui getMainGui() {
        return mainGui;
    }

    public MainGui getGermanMainGui() {
        return germanMainGui;
    }
}
