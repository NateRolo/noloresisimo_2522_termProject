package ca.bcit.comp2522.gameproject.wordgame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading and writing text files from resources.
 * <p>
 * This class provides static methods to read lines from resource files
 * and write formatted scores to files. It handles file validation and
 * resource management.
 * </p>
 *
 * @author Nathan O
 * @version 1.0 2025
 */
class FileManager
{
    /**
     * Reads lines from a resource file.
     * <p>
     * First attempts to read from the file system. If the file doesn't exist
     * in the file system, attempts to read it from the classpath resources.
     * </p>
     *
     * @param filePath path to the resource file
     * @return List of strings, each representing a line from the file
     * @throws IOException if file cannot be read
     */
    static List<String> readLinesFromResource(final String filePath) throws IOException
    {
        validateFilePath(filePath);

        final List<String>      lines;
        final InputStream       inputStream;
        final InputStreamReader inputStreamReader;
        final BufferedReader    bufferedReader;
        final Path              path;

        path = Paths.get(filePath);
        if(Files.exists(path))
        {
            return Files.readAllLines(path);
        }

        lines       = new ArrayList<>();
        inputStream = FileManager.class.getResourceAsStream(filePath);

        if(inputStream == null)
        {
            throw new IOException("Resource not found: " + filePath);
        }

        inputStreamReader = new InputStreamReader(inputStream,
                                                  StandardCharsets.UTF_8);
        bufferedReader    = new BufferedReader(inputStreamReader);

        try(inputStream; bufferedReader)
        {
            String line;
            while((line = bufferedReader.readLine()) != null)
            {
                lines.add(line);
            }
        }
        return lines;
    }

    /**
     * Writes formatted score data to a file.
     * <p>
     * Creates the file if it doesn't exist or appends to it if it does.
     * </p>
     *
     * @param formattedScore list of strings to write to the file
     * @param file           path to the file where data should be written
     * @throws FileNotFoundException if the file path is invalid
     */
    static void writeToResource(final List<String> formattedScore,
                                final String file) throws FileNotFoundException
    {
        validateFormattedScore(formattedScore);
        validateFilePath(file);

        try
        {
            final Path scorePath;
            scorePath = Paths.get(file);

            Files.write(scorePath,
                        formattedScore,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
        }
        catch(IOException error)
        {
            error.printStackTrace();
        }
    }

    /*
     * Validates that the formatted score list is not null or empty.
     *
     * @param formattedScore the list of score strings to validate
     */
    private static void validateFormattedScore(final List<String> formattedScore)
    {
        if(formattedScore == null || formattedScore.isEmpty())
        {
            throw new IllegalArgumentException("Cannot write null or empty scores to file.");
        }
    }

    /*
     * Validates that the file path is not null, blank, or non-existent.
     *
     * @param file the file path to validate
     */
    private static void validateFilePath(final String file) throws FileNotFoundException
    {
        if(file == null || file.isBlank() || Files.notExists(Paths.get(file)))
        {
            throw new FileNotFoundException("Invalid file path");
        }
    }
}
