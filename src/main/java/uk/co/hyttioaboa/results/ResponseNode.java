package uk.co.hyttioaboa.results;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.hyttioaboa.constants.Status;

import java.util.ArrayList;
import java.util.Iterator;

public class ResponseNode {
    final static Logger logger = Logger.getLogger(ResponseNode.class);

    String name;
    Integer nodeStatus = 0;
    ArrayList<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();
    ArrayList<ResponseNode> childNodes = new ArrayList<ResponseNode>();
    ResponseNode parentNode = null;

    public ResponseNode(String nodeName) {
        //TODO: add in timestamps
        this(nodeName, Status.NODE_CREATED.getValue(), "Node created");
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

        ResponseMessage createMessage = new ResponseMessage(Status.STACK_TRACE_ADDED.getValue(), stackTrace);
        this.responseMessages.add(createMessage);
        aggregateStatus(Status.STACK_TRACE_ADDED.getValue());
    }

    public void addMessage(Integer status, Throwable exception) {
        addMessage(status, exception.getMessage());

        // ExceptionUtils.getStackTrace will get the stack trace as a string. See http://stackoverflow.com/questions/1149703/how-can-i-convert-a-stack-trace-to-a-string for details.
        ResponseMessage createMessage = new ResponseMessage(Status.STACK_TRACE_ADDED.getValue(), ExceptionUtils.getStackTrace(exception));
        this.responseMessages.add(createMessage);
        aggregateStatus(Status.STACK_TRACE_ADDED.getValue());
    }

    public void setParentNode(ResponseNode parent) {
        this.parentNode = parent;
    }

    public ResponseNode createChildNode(String childName) {
        return createChildNode(childName, Status.NODE_CREATED.getValue(), "Node created");
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

    public JSONObject createReport() {
        JSONObject report = new JSONObject();
        try {
            report.put("name", this.name);
            report.put("status", this.nodeStatus);

            JSONArray messages = new JSONArray();
            Iterator<ResponseMessage> messageIterator = this.responseMessages.iterator();
            while (messageIterator.hasNext()) {
                ResponseMessage currentMessage = messageIterator.next();
                JSONObject messageObject = new JSONObject();
                messageObject.put("status", currentMessage.getStatus());
                messageObject.put("message", currentMessage.getMessage());
                messages.put(messageObject);
            }
            report.put("messages", messages);

            JSONArray children = new JSONArray();
            Iterator<ResponseNode> childrenIterator = this.childNodes.iterator();
            while (childrenIterator.hasNext()) {
                JSONObject childObject = childrenIterator.next().createReport();
                children.put(childObject);
            }
            report.put("children", children);
        }
        catch (JSONException ex) {
            throw new Error(ex);
        }

        return report;
    }

    public Integer getStatus() {
        return this.nodeStatus;
    }

    public void end() {
        addMessage(Status.NODE_FINISHED.getValue(), "Node finished");
    }
}
