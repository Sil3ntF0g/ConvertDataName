package de.s1lfog.cdn;

import javax.swing.*;
import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author s1lfog
 */
public class MusicRenamer {
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String PATH_SEPARATOR = System.getProperty("file.separator");

    public static void main(String[] args) {
        new MusicRenamer();
    }

    public MusicRenamer() {
        final File[] selected = getSelectedFilesFromChooser();
        final RenamingReport report = renameFiles(selected);

        JOptionPane.showMessageDialog(null, report.generateReport());
    }

    private RenamingReport renameFiles(final File[] selected) {
        long numberRenamedFiles = 0;
        long numberExceptions = 0;

        for (final File musicFile : selected) {
            if (renameSingleFile(musicFile)) {
                numberRenamedFiles++;
            } else {
                numberExceptions++;
            }
        }

        return new RenamingReport(numberRenamedFiles, numberExceptions);
    }

    private boolean renameSingleFile(final File musicFile) {
        boolean isDot = false;
        int indexFilename = 0;
        String name = musicFile.getName();

        for (int index = 0; index < name.length(); index++) {
            final String c = String.valueOf(name.charAt(index));

            // EONumber
            if (c.equals(".")) {
                isDot = true;
                break;
            }
            // Check if char is number
            try (final Scanner sc = new Scanner(c)) {
                sc.nextInt();
                indexFilename++;
            } catch (InputMismatchException e) {
                // No Number
                break;
            }
        }

        if (isDot && indexFilename > 0) {
            final String newPath = getPathFromFile(musicFile);

            String path = musicFile.getPath();
            path = path.substring(0, path.length() - musicFile.getName().length());
            String newName = musicFile.getName().substring(indexFilename + 2, musicFile.getName().length());
            File tmp = new File(path + newName);
            if (musicFile.renameTo(tmp)) {
                return true;
            }
        }
        return true;
    }

    private File[] getSelectedFilesFromChooser() {
        final JFileChooser fc = new JFileChooser();

        fc.setCurrentDirectory(new File(USER_HOME + PATH_SEPARATOR + "Music"));
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(true);
        fc.showOpenDialog(null);

        return fc.getSelectedFiles();
    }

    private String getPathFromFile(final File musicFile) throws NullPointerException {
        if (musicFile == null) {
            throw new NullPointerException("Musicfile should not be null!");
        }

        final String oldPath = musicFile.getPath();
        final String oldFilename = musicFile.getName();

        return oldPath.substring(0, oldPath.length() - oldFilename.length());
    }
}