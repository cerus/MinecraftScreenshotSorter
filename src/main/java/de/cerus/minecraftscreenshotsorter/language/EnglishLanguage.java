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

package de.cerus.minecraftscreenshotsorter.language;

public class EnglishLanguage extends Language {

    public EnglishLanguage() {
        setInfoMenu("Info");
        setFileMenu("File");
        setSaveConfigurationMenuItem("Save config");
        setLoadConfigurationMenuItem("Load config");
        setScreenshotPathLabel("Path to your screenshot directory:");
        setOutputPathLabel("Path to your output directory:");
        setError("Error");
        setFileLoadError("Could not load file. ({0})");
        setSuccess("Success");
        setConfigSaveSuccess("Config saved!");
        setConfigSaveError("Could not save config.");
        setSortButton("Sort");
        setStopSortingButton("Stop sorting");
        setSortingInterruptedLog("Sorting interrupted");
        setScreenshotDirNotExixtsError("The given screenshot directory does not exist.");
        setOutputDirNotExixtsError("The given output directory does not exist.");
        setSortingStartedLog("Started sorting...");
        setFilesFoundLog("{0} files found");
        setFileNameNotMatchLog("File {0} does not match the standard screenshot naming, ignoring it.");
        setFoundFileLog("Found file {0}");
        setYear("Year");
        setMonth("Month");
        setDay("Day");
        setTryingCreateFolderYearLog("Trying to create directory for year {0} (success: {1})");
        setTryingCreateFolderMonthLog("Trying to create directory for month {0} in year {1} (success: {2})");
        setTryingCreateFolderDayLog("Trying to create directory for day {0} in year {1} and month {2} (success: {3})");
        setFileAlreadySortedLog("File was already sorted in, ignoring it..");
        setTryingToCopyLog("Trying to copy screenshot...");
        setCopySuccessLog("Copied screenshot successfully!");
        setCopyFailureLog("Could not copy screenshot {0}");
        setDoneLog("Done! Took {0} milliseconds ({1} seconds) [{2} successes, {3} failures and {4} ignored files]");
        setAbout("About");
        setLanguage("Language");
    }
}
