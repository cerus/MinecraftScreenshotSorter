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

import de.cerus.minecraftscreenshotsorter.language.EnglishLanguage;
import de.cerus.minecraftscreenshotsorter.language.GermanLanguage;
import de.cerus.minecraftscreenshotsorter.language.Language;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.DefaultCaret;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainGui extends JFrame {

    private boolean isSorting = false;
    private Thread thread;
    private JScrollPane scrollPane;
    private Language language;
    private JButton runButton;

    public MainGui(Language language) {
        this.language = language;
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

        JMenu infoMenu = new JMenu(language.getInfoMenu());
        JMenu fileMenu = new JMenu(language.getFileMenu());
        JMenu languageMenu = new JMenu(language.getLanguage());

        JMenuItem about = new JMenuItem(language.getAbout());
        JMenuItem saveConfiguration = new JMenuItem(language.getSaveConfigurationMenuItem());
        JMenuItem loadConfiguration = new JMenuItem(language.getLoadConfigurationMenuItem());
        JMenuItem englishLanguageItem = new JMenuItem("English");
        JMenuItem germanLanguageItem = new JMenuItem("Deutsch");

        about.addActionListener(e -> new AboutGui().setVisible(true));
        englishLanguageItem.addActionListener(e -> {
            if (isSorting) {
                JOptionPane.showMessageDialog(null, "", "", JOptionPane.ERROR_MESSAGE);
                return;
            }

            setVisible(false);
            dispose();
            new MainGui(new EnglishLanguage()).setVisible(true);
        });
        germanLanguageItem.addActionListener(e -> {
            if (isSorting) {
                JOptionPane.showMessageDialog(null, "", "", JOptionPane.ERROR_MESSAGE);
                return;
            }

            setVisible(false);
            dispose();
            new MainGui(new GermanLanguage()).setVisible(true);
        });

        infoMenu.add(about);
        languageMenu.add(englishLanguageItem);
        languageMenu.add(germanLanguageItem);
        fileMenu.add(languageMenu);

        menuBar.add(infoMenu);

        JLabel screenshotDirectoryLabel = new JLabel(language.getScreenshotPathLabel());
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

        JLabel outputDirectoryLabel = new JLabel(language.getOutputPathLabel());
        outputDirectoryLabel.setBounds(5, (screenshotDirectory.getY() + screenshotDirectory.getHeight()) + 10, 390, 20);

        JTextField outputDirectory = new JTextField(System.getProperty("user.dir"));
        outputDirectory.setBounds(5, (outputDirectoryLabel.getY() + outputDirectoryLabel.getHeight()) + 5, 390, 20);

        loadConfiguration.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            for (FileFilter fileFilter : fileChooser.getChoosableFileFilters())
                fileChooser.removeChoosableFileFilter(fileFilter);
            fileChooser.setFileFilter(new FileNameExtensionFilter("MSSCONFIG FILTER", "mssconf"));

            if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                try {
                    Files.readAllLines(fileChooser.getSelectedFile().toPath()).forEach(line -> {
                        if (line.startsWith("screenshot-dir="))
                            screenshotDirectory.setText(line.split("=")[1]);
                        else if (line.startsWith("output-dir="))
                            outputDirectory.setText(line.split("=")[1]);
                    });
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, language.format(language.getFileLoadError(), e1.getMessage()), language.getError(), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        saveConfiguration.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            for (FileFilter fileFilter : fileChooser.getChoosableFileFilters())
                fileChooser.removeChoosableFileFilter(fileFilter);
            fileChooser.setFileFilter(new FileNameExtensionFilter("MSSCONFIG FILTER", "mssconf"));

            if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().endsWith(".mssconf"))
                    file = new File(file.getAbsolutePath() + ".mssconf");

                System.out.println(file.getAbsolutePath());

                try {
                    if (file.exists())
                        file.delete();
                    Files.write(file.toPath(), Arrays.asList("screenshot-dir=" + screenshotDirectory.getText(), "output-dir=" + outputDirectory.getText()), StandardOpenOption.CREATE_NEW);
                    JOptionPane.showMessageDialog(null, language.getConfigSaveSuccess(), language.getSuccess(), JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, language.getConfigSaveError(), language.getError(), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        fileMenu.add(saveConfiguration);
        fileMenu.add(loadConfiguration);
        menuBar.add(fileMenu);

        JButton outputDirectoryChooser = new JButton();
        outputDirectoryChooser.setIcon(new ImageIcon(getClass().getResource("/dir.png")));
        outputDirectoryChooser.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(/*FileSystemView.getFileSystemView().getHomeDirectory()*/new File(System.getProperty("user.dir")));
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                outputDirectory.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        outputDirectoryChooser.setBounds(400, outputDirectory.getY(), 90, 20);

        runButton = new JButton(language.getSortButton());
        runButton.setBounds(5, (outputDirectoryChooser.getY() + outputDirectoryChooser.getHeight()) + 20, 200, 35);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setBounds(210, runButton.getY(), 280, 35);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);

        JTextArea log = new JTextArea("1\n2\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3");
        //log.append("// Log");
        log.setBounds(5, (runButton.getY() + runButton.getHeight()) + 5, 485, 200);
        //log.setBorder(new LineBorder(Color.BLACK));
        log.setBackground(getBackground());
        log.setEditable(false);
        log.setVisible(true);
        DefaultCaret caret = (DefaultCaret) log.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        scrollPane = new JScrollPane(log, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVisible(true);
        //scrollPane.setBounds(5, (runButton.getY() + runButton.getHeight()) + 5, 485, 200);
        scrollPane.setBounds(log.getBounds());
        scrollPane.setBorder(new LineBorder(getBackground()));

        runButton.addActionListener(e -> {
            if (isSorting) {
                thread.interrupt();
                addText(log, language.getSortingInterruptedLog());
                runButton.setText(language.getSortButton());
                progressBar.setValue(0);
                progressBar.setVisible(false);
                isSorting = false;
                return;
            }

            File screenshotDir = new File(screenshotDirectory.getText());
            File outputDir = new File(outputDirectory.getText());

            if (!screenshotDir.exists()) {
                JOptionPane.showMessageDialog(null, language.getScreenshotDirNotExixtsError(), language.getFileNotFound(), JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!outputDir.exists()) {
                JOptionPane.showMessageDialog(null, language.getOutputDirNotExixtsError(), language.getFileNotFound(), JOptionPane.ERROR_MESSAGE);
                return;
            }

            sort(log, progressBar, screenshotDir, outputDir);
            runButton.setText(language.getStopSortingButton());
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

        setJMenuBar(menuBar);
    }

    private void sort(JTextArea log, JProgressBar progressBar, File screenshotDir, File outputDir) {
        thread = new Thread(() -> {
            progressBar.setVisible(true);
            isSorting = true;
            progressBar.setValue(0);
            addText(log, language.getSortingStartedLog());

            List<File> screenshots = Arrays.asList(Objects.requireNonNull(screenshotDir.listFiles()));
            addText(log, language.format(language.getFilesFoundLog(), screenshots.size()));
            progressBar.setMaximum(screenshots.size());

            int successes = 0;
            int failures = 0;
            int ignored = 0;

            long currentMillis = System.currentTimeMillis();
            for (File screenshot : screenshots) {
                if (thread.isInterrupted()) return;
                progressBar.setValue(progressBar.getValue() + 1);
                String name = screenshot.getName();
                if (!name.contains("-") || !name.contains("_") || (name.length() - name.replace(".", "").length()) < 2 || (name.length() - name.replace("-", "").length()) < 2) {
                    addText(log, language.format(language.getFileNameNotMatchLog(), name));
                    //progressBar.setMaximum(progressBar.getMaximum()-1);
                    ignored++;
                } else {
                    addText(log, language.format(language.getFoundFileLog(), name));

                    String year;
                    String month;
                    String day;
                    try {
                        year = name.substring(0, name.indexOf("-"));
                        month = name.substring(name.indexOf("-") + 1, name.indexOf("-") + 3);
                        day = name.substring(name.indexOf("-") + 4, name.indexOf("-") + 6);
                    } catch (Exception e) {
                        addText(log, language.format(language.getFileNameNotMatchLog(), name));
                        return;
                    }
                    if (!year.matches("\\d+") || !month.matches("\\d+") || !day.matches("\\d+")) {
                        addText(log, language.format(language.getFileNameNotMatchLog(), name));
                        return;
                    }

                    File yearDir = new File(outputDir.getAbsolutePath() + "\\" + language.getYear() + " " + year);
                    if (!yearDir.exists())
                        addText(log, language.format(language.getTryingCreateFolderYearLog(), year, yearDir.mkdirs()));
                    File monthDir = new File(yearDir.getAbsolutePath() + "\\" + language.getMonth() + " " + month);
                    if (!monthDir.exists())
                        addText(log, language.format(language.getTryingCreateFolderMonthLog(), month, year, monthDir.mkdirs()));
                    File dayDir = new File(monthDir.getAbsolutePath() + "\\" + language.getDay() + " " + day);
                    if (!dayDir.exists())
                        addText(log, language.format(language.getTryingCreateFolderDayLog(), day, year, month, dayDir.mkdirs()));
                    File finalFile = new File(dayDir.getAbsolutePath() + "\\" + screenshot.getName());
                    if (finalFile.exists()) {
                        addText(log, language.getFileAlreadySortedLog());
                        ignored++;
                    } else {
                        try {
                            addText(log, language.getTryingToCopyLog());
                            Files.copy(screenshot.toPath(), finalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            addText(log, language.getCopySuccessLog());
                            successes++;
                        } catch (IOException e) {
                            e.printStackTrace();
                            addText(log, language.format(language.getCopyFailureLog(), name));
                            failures++;
                        }
                    }
                }
            }
            addText(log, language.format(language.getDoneLog(), System.currentTimeMillis() - currentMillis, (System.currentTimeMillis() - currentMillis) / 1000, successes, failures, ignored));
            progressBar.setValue(0);
            progressBar.setVisible(false);
            runButton.setText(language.getSortButton());
            isSorting = false;
        });
        thread.start();
    }

    private void addText(JTextArea log, String text) {
        System.out.println(text);
        log.append("\n" + text);
        log.setCaretPosition(log.getDocument().getLength());
    }
}
