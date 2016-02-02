package uk.co.oaktest.messages;

import org.apache.log4j.Logger;
import uk.co.oaktest.fileContents.GetFileContents;
import uk.co.oaktest.messages.interfaces.MessageInterface;
import uk.co.oaktest.messages.json.JsonMessage;
import uk.co.oaktest.messages.xml.XmlMessage;

public class MessageFactory {
    final static Logger logger = Logger.getLogger(MessageFactory.class);

    public static MessageInterface createMessage(String messageContents) {
        MessageInterface message;
        try {
            message = new JsonMessage(messageContents);
        }
        catch (MessageException jsonMessageException) {
            logger.error("Problem with the JSON message: " + jsonMessageException.getMessage());
            return null;
        }

        if (!message.isValid()) {
            try {
                message = new XmlMessage(messageContents);
            }
            catch (MessageException xmlMessageException) {
                logger.error("Problem with the XML message: " + xmlMessageException.getMessage());
                return null;
            }

            if (!message.isValid()) {
                logger.error("Message is not valid JSON or XML; no idea what to do with it");
            }
        }

        return message;
    }
}
