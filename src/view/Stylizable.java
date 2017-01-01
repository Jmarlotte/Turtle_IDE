package view;

/**
 * Classes that implement Stylizable are able to change their appearance based on information sent in a Style object.
 *
 * @author Will Long and James Marlotte
 */
public interface Stylizable {
    /**
     * Change appearance to the specified style.
     *
     * @param style is Style object with appearance information.
     */
    void setStyle(Style style);
}
