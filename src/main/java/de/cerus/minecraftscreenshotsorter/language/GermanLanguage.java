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

public class GermanLanguage extends Language {

    public GermanLanguage() {
        setInfoMenu("Info");
        setFileMenu("Datei");
        setSaveConfigurationMenuItem("Konfiguration speichern");
        setLoadConfigurationMenuItem("Konfiguration laden");
        setScreenshotPathLabel("Pfad zu deinem Screenshot Verzeichnis:");
        setOutputPathLabel("Pfad zu deinem Ausgabe Verzeichnis:");
        setError("Fehler");
        setFileLoadError("Konnte Datei nicht laden. ({0})");
        setSuccess("Erfolgreich");
        setConfigSaveSuccess("Konfiguration gespeichert!");
        setConfigSaveError("Konnte Konfiguration nicht speichern.");
        setSortButton("Sortieren");
        setStopSortingButton("Stoppe sortieren");
        setSortingInterruptedLog("Sortieren unterbrochen");
        setScreenshotDirNotExixtsError("Das Screenshot Verzeichnis existiert nicht.");
        setOutputDirNotExixtsError("Das Screenshot Verzeichnis existiert nicht.");
        setSortingStartedLog("Sortieren gestartet...");
        setFilesFoundLog("{0} Dateien gefunden");
        setFileNameNotMatchLog("Dateiname {0} entspricht nicht dem Standard Screenshot Namen, ignoriere Datei.");
        setFoundFileLog("Datei {0} gefunden");
        setYear("Jahr");
        setMonth("Monat");
        setDay("Tag");
        setTryingCreateFolderYearLog("Versuche Verzeichnis für Jahr {0} zu erstellen (erfolgreich: {1})");
        setTryingCreateFolderMonthLog("Versuche Verzeichnis für Monat {0} in Jahr {1} zu erstellen (erfolgreich: {2})");
        setTryingCreateFolderDayLog("Versuche Verzeichnis für Tag {0} in Jahr {1} und Monat {2} zu erstellen (erfolgreich: {3})");
        setFileAlreadySortedLog("Datei wurde bereits einsortiert, ignoriere Datei..");
        setTryingToCopyLog("Versuche Screenshot zu kopieren...");
        setCopySuccessLog("Screenshot wurde erfolgreich kopiert!");
        setCopyFailureLog("Konnte screenshot {0} nicht kopieren");
        setDoneLog("Fertig! Die Operation hat {0} Millisekunden ({1} Sekunden) gedauert [{2} Erfolge, {3} Misserfolge und {4} ignorierte Dateien]");
        setAbout("Über");
        setLanguage("Sprache");
    }
}
