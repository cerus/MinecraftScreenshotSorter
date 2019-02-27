package net.mondstation.minecraftscreenshotsorter.modifications;

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

import de.cerus.minecraftscreenshotsorter.language.Language;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;

public class Configuration {

    private Language language;

    private String outputDir;
    private String screenshotDir;
    private boolean autoSortNewScreenshots;

    public Configuration(final Language language) {
        this.language = language;
    }

    public void load(final File selectedFile) {
        HashMap<String, String> data = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile)))) {
            String line;

            while (( line = reader.readLine() ) != null) {
                data.put(line.split("=")[0].trim(), line.split("=")[1].trim());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, language.format(language.getFileLoadError(), e.getMessage()), language.getError(), JOptionPane.ERROR_MESSAGE);
        }

        this.outputDir = data.get("outputDir");
        this.screenshotDir = data.get("screenshotDir");
        this.autoSortNewScreenshots = data.get("autoSortNewScreenshots").equals("true");
    }

    public void save(final File file) {
        try {
            if (file.exists())
                file.delete();

            file.createNewFile();

            PrintWriter writer = new PrintWriter(file);

            writer.println("outputDir = " + this.outputDir);
            writer.println("screenshotDir = " + this.screenshotDir);
            writer.println("autoSortNewScreenshots = " + this.autoSortNewScreenshots);

            writer.close();

            JOptionPane.showMessageDialog(null, language.getConfigSaveSuccess(), language.getSuccess(), JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, language.getConfigSaveError(), language.getError(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(final Language language) {
        this.language = language;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(final String outputDir) {
        this.outputDir = outputDir;
    }

    public String getScreenshotDir() {
        return screenshotDir;
    }

    public void setScreenshotDir(final String screenshotDir) {
        this.screenshotDir = screenshotDir;
    }

    public boolean isAutoSortNewScreenshots() {
        return autoSortNewScreenshots;
    }

    public void setAutoSortNewScreenshots(final boolean autoSortNewScreenshots) {
        this.autoSortNewScreenshots = autoSortNewScreenshots;
    }
}
