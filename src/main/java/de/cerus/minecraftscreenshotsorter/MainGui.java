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

package de.cerus.minecraftscreenshotsorter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainGui extends JFrame {

    private boolean isSorting = false;
    private Thread thread;
    private JScrollPane scrollPane;

    public MainGui() {
        initialize();
    }

    public void initialize() {
        setTitle("Minecraft Screenshot Sorter");
        setLayout(null);
        setSize(510, 430);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu infoMenu = new JMenu("Info");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> new AboutGui().setVisible(true));
        infoMenu.add(about);
        menuBar.add(infoMenu);
        setJMenuBar(menuBar);

        JLabel screenshotDirectoryLabel = new JLabel("Path to your screenshots:");
        screenshotDirectoryLabel.setBounds(5, 5, 400, 20);

        JTextField screenshotDirectory = new JTextField(System.getenv("APPDATA") + "\\.minecraft\\screenshots");
        screenshotDirectory.setBounds(5, (screenshotDirectoryLabel.getY() + screenshotDirectoryLabel.getHeight()) + 5, 390, 20);

        JButton screenshotDirectoryChooser = new JButton();
        screenshotDirectoryChooser.setIcon(new ImageIcon(getClass().getResource("/dir.png")));
        screenshotDirectoryChooser.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION)
                screenshotDirectory.setText(fileChooser.getSelectedFile().getAbsolutePath());
        });
        screenshotDirectoryChooser.setBounds(400, screenshotDirectory.getY(), 90, 20);

        JLabel outputDirectoryLabel = new JLabel("Path to your output directory:");
        outputDirectoryLabel.setBounds(5, (screenshotDirectory.getY() + screenshotDirectory.getHeight()) + 10, 390, 20);

        JTextField outputDirectory = new JTextField(System.getProperty("user.dir"));
        outputDirectory.setBounds(5, (outputDirectoryLabel.getY() + outputDirectoryLabel.getHeight()) + 5, 390, 20);

        JButton outputDirectoryChooser = new JButton();
        outputDirectoryChooser.setIcon(new ImageIcon(getClass().getResource("/dir.png")));
        outputDirectoryChooser.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(/*FileSystemView.getFileSystemView().getHomeDirectory()*/new File(System.getProperty("user.dir")));
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION)
                outputDirectory.setText(fileChooser.getSelectedFile().getAbsolutePath());
        });
        outputDirectoryChooser.setBounds(400, outputDirectory.getY(), 90, 20);

        JButton runButton = new JButton("Sort");
        runButton.setBounds(5, (outputDirectoryChooser.getY() + outputDirectoryChooser.getHeight()) + 20, 200, 35);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setBounds(210, runButton.getY(), 280, 35);
        progressBar.setStringPainted(true);

        JTextArea log = new JTextArea();
        log.append("// Log");
        log.setBounds(5, (runButton.getY() + runButton.getHeight()) + 5, 485, 200);
        log.setBorder(new LineBorder(Color.BLACK));
        log.setEnabled(false);
        log.setVisible(true);
        DefaultCaret caret = (DefaultCaret) log.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        scrollPane = new JScrollPane(log, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVisible(true);
        scrollPane.setBounds(5, (runButton.getY() + runButton.getHeight()) + 5, 485, 200);
        scrollPane.setBounds(log.getBounds());

        runButton.addActionListener(e -> {
            if (isSorting) {
                thread.interrupt();
                addText(log, "Sorting interrupted");
                runButton.setText("Sort");
                progressBar.setValue(0);
                isSorting = false;
                return;
            }

            File screenshotDir = new File(screenshotDirectory.getText());
            File outputDir = new File(outputDirectory.getText());

            if (!screenshotDir.exists()) {
                JOptionPane.showMessageDialog(null, "The given screenshot directory does not exist.", "File not found", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!outputDir.exists()) {
                JOptionPane.showMessageDialog(null, "The given output directory does not exist.", "File not found", JOptionPane.ERROR_MESSAGE);
                return;
            }

            sort(log, progressBar, screenshotDir, outputDir);
            runButton.setText("Stop sorting");
        });

        add(screenshotDirectoryLabel);
        add(screenshotDirectory);
        add(screenshotDirectoryChooser);
        add(outputDirectoryLabel);
        add(outputDirectory);
        add(outputDirectoryChooser);
        add(runButton);
        add(progressBar);
        //add(log);
        add(scrollPane);
    }

    private void sort(JTextArea log, JProgressBar progressBar, File screenshotDir, File outputDir) {
        thread = new Thread(() -> {
            isSorting = true;
            progressBar.setValue(0);
            addText(log, "Sorting started...");

            List<File> screenshots = Arrays.asList(Objects.requireNonNull(screenshotDir.listFiles()));
            addText(log, screenshots.size() + " files found");
            progressBar.setMaximum(screenshots.size());

            int successes = 0;
            int failures = 0;
            int ignored = 0;

            long currentMillis = System.currentTimeMillis();
            for (File screenshot : screenshots) {
                if (thread.isInterrupted()) return;
                progressBar.setValue(progressBar.getValue() + 1);
                String name = screenshot.getName();
                if (!name.contains("-") || !name.contains("_") || (name.length() - name.replace(".", "").length()) < 2) {
                    addText(log, "File '" + name + "' does not match the standard screenshot naming, ignoring.");
                    //progressBar.setMaximum(progressBar.getMaximum()-1);
                    ignored++;
                } else {
                    addText(log, "Found file '" + name + "'");

                    String year = name.substring(0, name.indexOf("-"));
                    String month = name.substring(name.indexOf("-") + 1, name.indexOf("-") + 3);
                    String day = name.substring(name.indexOf("-") + 4, name.indexOf("-") + 6);

                    File yearDir = new File(outputDir.getAbsolutePath() + "\\Year " + year);
                    if (!yearDir.exists())
                        addText(log, "Trying to create folder for year " + year + " (success: " + yearDir.mkdirs() + ")");
                    File monthDir = new File(yearDir.getAbsolutePath() + "\\Month " + month);
                    if (!monthDir.exists())
                        addText(log, "Trying to create folder for month " + month + " in year " + year + " (success: " + monthDir.mkdirs() + ")");
                    File dayDir = new File(monthDir.getAbsolutePath() + "\\Day " + day);
                    if (!dayDir.exists())
                        addText(log, "Trying to create folder for day " + day + " in year " + year + " and month " + month + " (success: " + dayDir.mkdirs() + ")");
                    File finalFile = new File(dayDir.getAbsolutePath() + "\\" + screenshot.getName());
                    if (finalFile.exists()) {
                        addText(log, "File was already sorted in, ignoring it..");
                        ignored++;
                    } else {
                        try {
                            addText(log, "Trying to copy screenshot...");
                            Files.copy(screenshot.toPath(), finalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            addText(log, "Screenshot was successfully copied!");
                            successes++;
                        } catch (IOException e) {
                            e.printStackTrace();
                            addText(log, "Could not copy screenshot '" + name + "'");
                            failures++;
                        }
                    }
                }
            }
            addText(log, "Done! Took " + (System.currentTimeMillis() - currentMillis) + " milliseconds (" + ((System.currentTimeMillis() - currentMillis) / 1000) + " seconds) [" + successes + " successes, " + failures + " failures and " + ignored + " ignored files]");
            isSorting = true;
        });
        thread.start();
    }

    private void addText(JTextArea log, String text) {
        System.out.println(text);
        log.append("\n" + text);
        log.setCaretPosition(log.getDocument().getLength());
    }
}
