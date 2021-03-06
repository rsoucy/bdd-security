package net.continuumsecurity.steps;

import net.continuumsecurity.Config;
import net.continuumsecurity.Port;
import net.continuumsecurity.scanner.PortScanner;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.*;
import org.jbehave.core.model.ExamplesTable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class InfrastructureSteps {
    Logger log = Logger.getLogger(InfrastructureSteps.class);
    String targetHost;
    PortScanner portScanner;
    List<Port> portScanResults;
    List<Integer> selectedPorts;
    List<Integer> expectedPorts;

    @Given("the target host name <host>")
    public void setTargetHost(@Named("host") String hostname) throws MalformedURLException {
        targetHost = hostname;
    }

    @When("TCP ports from $from to $to are scanned using $threads threads and a timeout of $timeout milliseconds")
    public void scanPorts(int from, int to, int threads, int timeout) throws ExecutionException, InterruptedException {
        portScanner = new PortScanner(targetHost,from,to,threads,timeout);
        portScanResults = portScanner.scan();
    }

    @When("the $state ports are selected")
    public void selectPorts(String state) {
        selectedPorts = new ArrayList<Integer>();
        for (Port result : portScanResults) {
            if (result.getState().equals(Port.State.fromString(state))) {
                selectedPorts.add(result.getNumber());
            }
        }
    }

    @Then("the ports should be <ports_open>")
    public void checkPortStates(@Named("ports_open") String csvPorts) {
        expectedPorts = new ArrayList<Integer>();
        for (String portAsString : csvPorts.split(",")) {
            expectedPorts.add(Integer.parseInt(portAsString));
        }
        assertThat("Only the expected ports are open",selectedPorts, containsInAnyOrder(expectedPorts.toArray(new Integer[expectedPorts.size()])));
    }

    public List<Integer> getSelectedPorts() {
        return selectedPorts;
    }
}
