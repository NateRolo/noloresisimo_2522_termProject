package ca.bcit.comp2522.gameproject.wordGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a world containing multiple countries.
 * <p>
 * This class manages a collection of countries using a HashMap where
 * the country name is the key and the Country object is the value.
 * Countries are loaded from resource files.
 * </p>
 *
 * @author Nathan O
 * @version 1.0 2025
 */
class World
{
    private final Map<String, Country> countries;

    private static final String COUNTRY_CAPITAL_SEPARATOR = ":";
    private static final int    FACTS_PER_COUNTRY         = 3;
    private static final int    LINES_TO_SKIP_AFTER_FACTS = 3;
    private static final int    COUNTRY_NAME_INDEX        = 0;
    private static final int    CAPITAL_NAME_INDEX        = 1;
    private static final int    FIRST_FACT_OFFSET         = 1;

    private static final int    FIRST_FACT_INDEX          = 0;
    private static final int    SECOND_FACT_INDEX         = 1;
    private static final int    THIRD_FACT_INDEX          = 2;

    private static final String[] RESOURCE_FILES = {"/a.txt",
                                                    "/b.txt",
                                                    "/c.txt",
                                                    "/d.txt",
                                                    "/e.txt",
                                                    "/f.txt",
                                                    "/g.txt",
                                                    "/h.txt",
                                                    "/i.txt",
                                                    "/j.txt",
                                                    "/k.txt",
                                                    "/l.txt",
                                                    "/m.txt",
                                                    "/n.txt",
                                                    "/o.txt",
                                                    "/p.txt",
                                                    "/q.txt",
                                                    "/r.txt",
                                                    "/s.txt",
                                                    "/t.txt",
                                                    "/u.txt",
                                                    "/v.txt",
                                                    "/y.txt",
                                                    "/z.txt"};

    /**
     * Constructs a new World object and loads countries from resource files.
     *
     * @throws IOException if there's an error reading the resource files
     */
    World() throws IOException
    {
        this.countries = new HashMap<>();
        loadCountriesFromAllFiles();
    }

    /* 
     * Loads countries from all resource files.
     */
    private void loadCountriesFromAllFiles() throws IOException
    {
        for(final String file : RESOURCE_FILES)
        {
            loadCountriesFromFile(file);
        }
    }

    /* 
     * Loads countries from a single resource file.
     */
    private void loadCountriesFromFile(final String filePath) throws IOException
    {
        final List<String> lines;

        lines = FileManager.readLinesFromResource(filePath);
        processFileLines(lines);
    }

    /* 
     * Processes lines from a file to create Country objects.
     */
    private void processFileLines(final List<String> lines)
    {
        String   countryName;
        String   capitalName;
        String[] facts;

        for(int lineIndex = 0; lineIndex < lines.size(); lineIndex++)
        {
            final String line;
            final Country country;

            line = lines.get(lineIndex);
            if(line.isEmpty())
            {
                continue;
            }

            if(line.contains(COUNTRY_CAPITAL_SEPARATOR))
            {
                final String[] parts;

                parts       = line.split(COUNTRY_CAPITAL_SEPARATOR);
                countryName = parts[COUNTRY_NAME_INDEX].trim();
                capitalName = parts[CAPITAL_NAME_INDEX].trim();
                facts       = new String[FACTS_PER_COUNTRY];

                for(int factIndex = 0; factIndex < FACTS_PER_COUNTRY; factIndex++)
                {
                    if(lineIndex + factIndex + FIRST_FACT_OFFSET < lines.size())
                    {
                        facts[factIndex] = lines.get(lineIndex + factIndex + FIRST_FACT_OFFSET)
                                                .trim();
                    }
                }

                country = new Country(countryName,
                                      capitalName,
                                      facts[FIRST_FACT_INDEX],
                                      facts[SECOND_FACT_INDEX],
                                      facts[THIRD_FACT_INDEX]);
                addCountry(country);
                lineIndex += LINES_TO_SKIP_AFTER_FACTS;
            }
        }
    }

    /**
     * Adds a country to the world.
     *
     * @param country the country to add
     */
    final void addCountry(final Country country)
    {
        if(country == null)
        {
            throw new IllegalArgumentException("Invalid country:" + country);
        }

        this.countries.put(country.getName(),
                           country);
    }

    /**
     * Gets a country by its name.
     *
     * @param name the name of the country to retrieve
     * @return the Country object if found, null otherwise
     */
    final Country getCountry(final String name)
    {
        if(name == null || name.isBlank())
        {
            throw new IllegalArgumentException("Invalid country name: " + name);
        }

        return countries.get(name);
    }


    /**
     * Returns a random {@code Country} from the list of available countries.
     *
     * @return A randomly selected {@code Country} object.
     */
    final Country getRandomCountry()
    {
        final List<Country> countryList;
        final int randomIndex;
        
        countryList = new ArrayList<>(countries.values());
        randomIndex = (int) (Math.random() * countryList.size());
    
        return countryList.get(randomIndex);
    }

    /**
     * Checks if a country exists in the world.
     *
     * @param name the name of the country to check
     * @return true if the country exists, false otherwise
     */
    final boolean hasCountry(final String name)
    {
        if(name == null || name.isBlank())
        {
            throw new IllegalArgumentException("Invalid country name: " + name);
        }

        return countries.containsKey(name);
    }

    /**
     * Gets the number of countries in the world.
     *
     * @return the number of countries
     */
    final int getCountryCount()
    {
        return countries.size();
    }
}