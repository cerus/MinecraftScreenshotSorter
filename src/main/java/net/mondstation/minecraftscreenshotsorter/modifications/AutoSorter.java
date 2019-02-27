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

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class AutoSorter {

    private WatchService watchService = FileSystems.getDefault().newWatchService();
    private Configuration configuration;

    private Thread thread;

    public AutoSorter() throws IOException {
    }

    @SuppressWarnings( { "unchecked", "InfiniteLoopStatement" } )
    public boolean activate() {
        if (this.configuration.getScreenshotDir() == null)
            return false;

        try {
            new File(this.configuration.getScreenshotDir()).toPath().register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        thread = new Thread(() -> {
            WatchKey key = null;

            while (true) {
                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (key == null) continue;

                key.pollEvents().forEach(event -> {
                    this.sort(new File(new File(this.configuration.getScreenshotDir()), event.context().toString()));
                });

                key.reset();
            }
        });

        thread.start();

        return true;
    }

    public void deactivate() {
        thread.interrupt();

        try {
            watchService.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sort(final File screenshot) {
        String name = screenshot.getName();
        File outputDir = new File(this.configuration.getOutputDir());
        Language language = this.configuration.getLanguage();

        if (name.contains("-") && name.contains("_") && ( name.length() - name.replace(".", "").length() ) >= 2) {
            String year = name.substring(0, name.indexOf("-"));
            String month = name.substring(name.indexOf("-") + 1, name.indexOf("-") + 3);
            String day = name.substring(name.indexOf("-") + 4, name.indexOf("-") + 6);

            File yearDir = new File(outputDir.getAbsolutePath() + "\\" + language.getYear() + " " + year);
            if (!yearDir.exists())
                yearDir.mkdirs();
            File monthDir = new File(yearDir.getAbsolutePath() + "\\" + language.getMonth() + " " + month);
            if (!monthDir.exists())
                monthDir.mkdirs();
            File dayDir = new File(monthDir.getAbsolutePath() + "\\" + language.getDay() + " " + day);
            if (!dayDir.exists())
                dayDir.mkdirs();
            File finalFile = new File(dayDir.getAbsolutePath() + "\\" + screenshot.getName());

            if (!finalFile.exists()) {
                try {
                    Files.copy(screenshot.toPath(), finalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(final Configuration configuration) {
        this.configuration = configuration;
    }
}
