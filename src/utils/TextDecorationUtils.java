package utils;

/**
 * The {@link TextDecorationUtils} class provides utility methods for coloring
 * console display for the whole application.
 */
public class TextDecorationUtils {
    /**
     * {@link UNDERLINE} constant ANSI escape codes for underline text.
     */
    public static final String UNDERLINE = "\u001B[4m";

    /**
     * {@link BOLD} constant ANSI escape codes for bold text.
     */
    public static final String BOLD = "\u001B[1m";

    /**
     * {@link ITALIC} constant ANSI escape codes for italic text.
     */
    public static final String ITALIC = "\u001B[3m";

    /**
     * {@link RESET_CODE} ANSI reset code.
     */
    public static final String RESET_CODE = "\u001B[0m";

    /**
     * Formats a string to be underlined.
     *
     * @param text the string to be formatted
     * @return the underlined string
     */
    public static String underlineText(String text) {
        return UNDERLINE + text + RESET_CODE;
    }

    /**
     * Formats a string to be bolded.
     *
     * @param text the string to be formatted
     * @return the bolded string
     */
    public static String boldText(String text) {
        return BOLD + text + RESET_CODE;
    }

    /**
     * Formats a string to be italic.
     *
     * @param text the string to be formatted
     * @return the italic string
     */
    public static String italicText(String text) {
        return ITALIC + text + RESET_CODE;
    }
}
