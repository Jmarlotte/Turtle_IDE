package exceptions;

/**
 * Custom exception that is used when the XML file is not formatted as expected.
 */

public class XmlFormatException extends Exception {
    private final String tag;

    public XmlFormatException(String missedTag) {
        super();
        tag = missedTag;
    }

    @Override
    public String getMessage () {
        return String.format("The XML file is not well-structured on the %s tag", tag);
    }
}
