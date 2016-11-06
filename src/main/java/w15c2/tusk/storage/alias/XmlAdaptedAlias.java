package w15c2.tusk.storage.alias;

import javax.xml.bind.annotation.XmlElement;

import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.model.Alias;

//@@author A0143107U
/**
 * JAXB-friendly version of the Alias.
 */
public class XmlAdaptedAlias {

    @XmlElement(required = true)
    private String shortcut;
    @XmlElement(required = true)
    private String sentence;
    
    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedAlias() {}


    /**
     * Converts a given Alias into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAlias
     */
    public XmlAdaptedAlias(Alias source) {
    	shortcut = source.getShortcut().toString();
    	sentence = source.getSentence().toString();
    }

    /**
     * Converts this jaxb-friendly adapted alias object into the model's Alias object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted alias
     */
    public Alias toModelType() throws IllegalValueException {
        return new Alias(shortcut, sentence);
    }
}
