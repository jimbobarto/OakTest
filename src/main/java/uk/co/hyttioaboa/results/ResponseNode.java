package uk.co.hyttioaboa.results;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class ResponseNode {
    final static Logger logger = Logger.getLogger(ResponseNode.class);

    String name;
    Integer nodeStatus = 0;
    ArrayList<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();
    ArrayList<ResponseNode> childNodes = new ArrayList<ResponseNode>();
    ResponseNode parentNode = null;

    public ResponseNode(String nodeName) {
        //TODO: add in timestamps
        this(nodeName, 101, "Node created");
    }

    public ResponseNode(String nodeName, Integer newStatus, String newMessage) {
        this.name = nodeName;
        addMessage(newStatus, newMessage);
    }

    public void addMessage(Integer status, String message) {
        ResponseMessage createMessage = new ResponseMessage(status, message);
        this.responseMessages.add(createMessage);

        aggregateStatus(status);

        logger.info(this.name + " (" + status + "): " + message);
    }

    public void addMessage(Integer status, String message, String stackTrace) {
        addMessage(status, message);

        ResponseMessage createMessage = new ResponseMessage(150, stackTrace);
        this.responseMessages.add(createMessage);
        aggregateStatus(150);
    }

    public void addMessage(Integer status, Throwable exception) {
        addMessage(status, exception.getMessage());

        // ExceptionUtils.getStackTrace will get the stack trace as a string. See http://stackoverflow.com/questions/1149703/how-can-i-convert-a-stack-trace-to-a-string for details.
        ResponseMessage createMessage = new ResponseMessage(150, ExceptionUtils.getStackTrace(exception));
        this.responseMessages.add(createMessage);
        aggregateStatus(150);
    }

    public void setParentNode(ResponseNode parent) {
        this.parentNode = parent;
    }

    public ResponseNode createChildNode(String childName) {
        return createChildNode(childName, 101, "Node created");
    }

    public ResponseNode createChildNode(String childName, Integer childStatus, String childMessage) {
        ResponseNode childNode = new ResponseNode(childName, childStatus, childMessage);
        childNode.setParentNode(this);
        this.childNodes.add(childNode);

        return childNode;
    }

    public Integer aggregateStatus(Integer status) {
        if (status > this.nodeStatus) {
            this.nodeStatus = status;
        }

        if (this.parentNode != null) {
            this.parentNode.aggregateStatus(status);
        }

        return this.nodeStatus;
    }

    public Integer getStatus() {
        return this.nodeStatus;
    }

    public void end() {
        addMessage(111, "Node finished");
    }
}
