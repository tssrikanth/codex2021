/*
 * TSLib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */

package cloud.thoughtspotstaging.champagne.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import cloud.thoughtspotstaging.champagne.TSClient;
import cloud.thoughtspotstaging.champagne.exceptions.ApiException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SessionControllerTest extends BaseControllerTest {

    /**
     * Client instance.
     */
    private static TSClient client;
    
    /**
     * Controller instance (for all tests).
     */
    private static SessionController controller;

    /**
     * Setup test class.
     */
    @BeforeClass
    public static void setUpClass() {
        client = createConfiguration();
        controller = client.getSessionController();
    }

    /**
     * Tear down test class.
     */
    @AfterClass
    public static void tearDownClass() {
        controller = null;
    }

    /**
     * Test case for login.
     * @throws Throwable exception if occurs.
     */
    @Test
    public void testLogin() throws Exception {
        // Parameters for the API call
        String contentType = "application/json";
        String accept = "application/json";
        String xRequestedBy = "ThoughtSpot";
        String username = "tsadmin";//"srikanth.jandhyala@thoughtspot.com";
        String password = "admin";//"Test@1986";
        String accessLevel = "FULL";
        long expiry = 5;

        // Set callback and perform API call
        try {
            controller.login(username, password, accessLevel, expiry, contentType, accept, xRequestedBy);
        } catch (ApiException e) {
            // Empty block
        }

        // Test whether the response is null
        assertNotNull("Response is null", 
                httpResponse.getResponse());
        // Test response code
        assertEquals("Status is not 200",
                200, httpResponse.getResponse().getStatusCode());
    }

    /**
     * Test case for logout.
     * @throws Throwable exception if occurs.
     */
    @Test
    public void testLogout() throws Exception {
        // Parameters for the API call
        String contentType = "application/json";
        String accept = "application/json";
        String xRequestedBy = "ThoughtSpot";
        String username = "srikanth.jandhyala@thoughtspot.com";
        String password = "Test@1986";
        Boolean rememberme = false;

        // Set callback and perform API call
        try {
            controller.logout(contentType, accept, xRequestedBy);
        } catch (ApiException e) {
            // Empty block
        }

        // Test whether the response is null
        assertNotNull("Response is null", 
                httpResponse.getResponse());
        // Test response code
        assertEquals("Status is not 200", 
                200, httpResponse.getResponse().getStatusCode());

    }

}
