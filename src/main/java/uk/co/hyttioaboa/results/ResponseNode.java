package uk.co.hyttioaboa.results;

import java.util.ArrayList;

/**
 * Created by jamesbartlett on 03/12/15.
 */
public class ResponseNode {
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

        System.out.println(this.name + " (" + status + "): " + message);
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
