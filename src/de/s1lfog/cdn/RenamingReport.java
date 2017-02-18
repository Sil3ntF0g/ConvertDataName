package de.s1lfog.cdn;

public class RenamingReport {
    private final long numberRenamedFiles;
    private final long numberFilesNotRenamed;

    public RenamingReport(final long numberRenamedFiles, final long numberFilesNotRenamed) {
        this.numberRenamedFiles = numberRenamedFiles;
        this.numberFilesNotRenamed = numberFilesNotRenamed;
    }

    public String generateReport() {
        final StringBuilder builder = new StringBuilder();

        builder.append(numberRenamedFiles);
        builder.append(" files successfully renamed.");

        if (numberFilesNotRenamed > 0) {
            builder.append("\n");
            builder.append(numberFilesNotRenamed);
            builder.append(" files could not be renamed.");
        }

        return builder.toString();
    }
}
