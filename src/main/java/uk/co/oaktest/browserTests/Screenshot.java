package uk.co.oaktest.browserTests;

import org.apache.http.client.methods.HttpUriRequest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.containers.Container;
import uk.co.oaktest.requests.Request;
import uk.co.oaktest.requests.RequestException;
import uk.co.oaktest.results.ResponseNode;
import uk.co.oaktest.results.Uuid;

import java.io.File;

public class Screenshot {

    private File screenshotFile;
    private ResponseNode reporterNode;
    private String screenshotName;

    public Screenshot(WebDriver driver, ResponseNode reporterNode, Integer resultId, Integer screenshotStatus) {
        this.reporterNode = reporterNode;

        takeScreenshot(driver, resultId, screenshotStatus);
    }

    public Screenshot(Container container, ResponseNode reporterNode, Integer screenshotStatus) {
        this.reporterNode = reporterNode;

        WebDriver driver = container.getDriver();
        Integer resultId = container.getResultId();

        takeScreenshot(driver, resultId, screenshotStatus);
    }

    private void takeScreenshot(WebDriver driver, Integer resultId, Integer screenshotStatus) {
        String uuid = new Uuid().toString();
        String screenshotName = uuid + "_" + resultId.toString();

        takeScreenshot(driver, screenshotStatus, screenshotName);
    }

    private void takeScreenshot(WebDriver driver, Integer screenshotStatus) {
        String screenshotName = new Uuid().toString();
        takeScreenshot(driver, screenshotStatus, screenshotName);
    }

    private void takeScreenshot(WebDriver driver, Integer screenshotStatus, String screenshotName) {
        this.reporterNode.addMessage(screenshotStatus, screenshotName);

        this.screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        this.screenshotName = screenshotName;
    }

    public boolean delete() {
        return this.screenshotFile.delete();
    }

    public void upload(String url) {
        Request request = new Request();

        try {
            HttpUriRequest screenshotRequest = request.createRequest("POST", url + screenshotName, this.screenshotFile, "screenshot");
            Integer status = request.execute(screenshotRequest);
        }
        catch (RequestException requestException) {
            this.reporterNode.addMessage(Status.BASIC_ERROR.value(), requestException);
        }

    }

    public void cleanUpload(String url) {
        upload(url);
        delete();
    }

    public String getName() {
        return this.screenshotName;
    }
}
