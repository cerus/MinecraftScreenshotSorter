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

import java.text.MessageFormat;

public abstract class Language {

    private String infoMenu;
    private String fileMenu;
    private String saveConfigurationMenuItem;
    private String loadConfigurationMenuItem;
    private String screenshotPathLabel;
    private String outputPathLabel;
    private String fileLoadError;
    private String configSaveError;
    private String configSaveSuccess;
    private String sortButton;
    private String stopSortingButton;
    private String sortingInterruptedLog;
    private String screenshotDirNotExixtsError;
    private String outputDirNotExixtsError;
    private String fileNotFound;
    private String sortingStartedLog;
    private String filesFoundLog;
    private String fileNameNotMatchLog;
    private String foundFileLog;
    private String year;
    private String month;
    private String day;
    private String tryingCreateFolderYearLog;
    private String tryingCreateFolderMonthLog;
    private String tryingCreateFolderDayLog;
    private String fileAlreadySortedLog;
    private String tryingToCopyLog;
    private String copySuccessLog;
    private String copyFailureLog;
    private String doneLog;
    private String error;
    private String success;
    private String about;
    private String language;

    public String getInfoMenu() {
        return infoMenu;
    }

    public void setInfoMenu(String infoMenu) {
        this.infoMenu = infoMenu;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getFileMenu() {
        return fileMenu;
    }

    public void setFileMenu(String fileMenu) {
        this.fileMenu = fileMenu;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSaveConfigurationMenuItem() {
        return saveConfigurationMenuItem;
    }

    public void setSaveConfigurationMenuItem(String saveConfigurationMenuItem) {
        this.saveConfigurationMenuItem = saveConfigurationMenuItem;
    }

    public String getLoadConfigurationMenuItem() {
        return loadConfigurationMenuItem;
    }

    public void setLoadConfigurationMenuItem(String loadConfigurationMenuItem) {
        this.loadConfigurationMenuItem = loadConfigurationMenuItem;
    }

    public String getScreenshotPathLabel() {
        return screenshotPathLabel;
    }

    public void setScreenshotPathLabel(String screenshotPathLabel) {
        this.screenshotPathLabel = screenshotPathLabel;
    }

    public String getOutputPathLabel() {
        return outputPathLabel;
    }

    public void setOutputPathLabel(String outputPathLabel) {
        this.outputPathLabel = outputPathLabel;
    }

    public String getFileLoadError() {
        return fileLoadError;
    }

    public void setFileLoadError(String fileLoadError) {
        this.fileLoadError = fileLoadError;
    }

    public String getConfigSaveError() {
        return configSaveError;
    }

    public void setConfigSaveError(String configSaveError) {
        this.configSaveError = configSaveError;
    }

    public String getConfigSaveSuccess() {
        return configSaveSuccess;
    }

    public void setConfigSaveSuccess(String configSaveSuccess) {
        this.configSaveSuccess = configSaveSuccess;
    }

    public String getSortButton() {
        return sortButton;
    }

    public void setSortButton(String sortButton) {
        this.sortButton = sortButton;
    }

    public String getStopSortingButton() {
        return stopSortingButton;
    }

    public void setStopSortingButton(String stopSortingButton) {
        this.stopSortingButton = stopSortingButton;
    }

    public String getSortingInterruptedLog() {
        return sortingInterruptedLog;
    }

    public void setSortingInterruptedLog(String sortingInterruptedLog) {
        this.sortingInterruptedLog = sortingInterruptedLog;
    }

    public String getScreenshotDirNotExixtsError() {
        return screenshotDirNotExixtsError;
    }

    public void setScreenshotDirNotExixtsError(String screenshotDirNotExixtsError) {
        this.screenshotDirNotExixtsError = screenshotDirNotExixtsError;
    }

    public String getOutputDirNotExixtsError() {
        return outputDirNotExixtsError;
    }

    public void setOutputDirNotExixtsError(String outputDirNotExixtsError) {
        this.outputDirNotExixtsError = outputDirNotExixtsError;
    }

    public String getFileNotFound() {
        return fileNotFound;
    }

    public void setFileNotFound(String fileNotFound) {
        this.fileNotFound = fileNotFound;
    }

    public String getSortingStartedLog() {
        return sortingStartedLog;
    }

    public void setSortingStartedLog(String sortingStartedLog) {
        this.sortingStartedLog = sortingStartedLog;
    }

    public String getFilesFoundLog() {
        return filesFoundLog;
    }

    public void setFilesFoundLog(String filesFoundLog) {
        this.filesFoundLog = filesFoundLog;
    }

    public String getFileNameNotMatchLog() {
        return fileNameNotMatchLog;
    }

    public void setFileNameNotMatchLog(String fileNameNotMatchLog) {
        this.fileNameNotMatchLog = fileNameNotMatchLog;
    }

    public String getFoundFileLog() {
        return foundFileLog;
    }

    public void setFoundFileLog(String foundFileLog) {
        this.foundFileLog = foundFileLog;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTryingCreateFolderYearLog() {
        return tryingCreateFolderYearLog;
    }

    public void setTryingCreateFolderYearLog(String tryingCreateFolderYearLog) {
        this.tryingCreateFolderYearLog = tryingCreateFolderYearLog;
    }

    public String getTryingCreateFolderMonthLog() {
        return tryingCreateFolderMonthLog;
    }

    public void setTryingCreateFolderMonthLog(String tryingCreateFolderMonthLog) {
        this.tryingCreateFolderMonthLog = tryingCreateFolderMonthLog;
    }

    public String getTryingCreateFolderDayLog() {
        return tryingCreateFolderDayLog;
    }

    public void setTryingCreateFolderDayLog(String tryingCreateFolderDayLog) {
        this.tryingCreateFolderDayLog = tryingCreateFolderDayLog;
    }

    public String getFileAlreadySortedLog() {
        return fileAlreadySortedLog;
    }

    public void setFileAlreadySortedLog(String fileAlreadySortedLog) {
        this.fileAlreadySortedLog = fileAlreadySortedLog;
    }

    public String getTryingToCopyLog() {
        return tryingToCopyLog;
    }

    public void setTryingToCopyLog(String tryingToCopyLog) {
        this.tryingToCopyLog = tryingToCopyLog;
    }

    public String getCopySuccessLog() {
        return copySuccessLog;
    }

    public void setCopySuccessLog(String copySuccessLog) {
        this.copySuccessLog = copySuccessLog;
    }

    public String getCopyFailureLog() {
        return copyFailureLog;
    }

    public void setCopyFailureLog(String copyFailureLog) {
        this.copyFailureLog = copyFailureLog;
    }

    public String getDoneLog() {
        return doneLog;
    }

    public void setDoneLog(String doneLog) {
        this.doneLog = doneLog;
    }

    public String format(String toFormat, Object... objects) {
        return MessageFormat.format(toFormat, objects);
    }

}
