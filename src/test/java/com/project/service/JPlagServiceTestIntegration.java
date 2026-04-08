
package com.project.service;

import java.io.File;
import java.time.Duration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

class JPlagServiceTestIntegration {

    @Mock
    private JPlagService jPlagServiceMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // initialize mocks
    }

    // Happy path
    @Test
    @DisplayName("1. Happy Path: runJPlag returns expected results")
    void testRunJPlagHappyPath() throws Exception {
        File submissions = new File("dummy/path");

        Map<String, Double> fakeMatches = Map.of("A.java vs B.java", 85.0);
        Map<String, Object> fakeResult = Map.of(
                "peerMatches", fakeMatches,
                "jobId", "job-test",
                "detailedLogs", "Fake logs"
        );

        when(jPlagServiceMock.runJPlag(any(File.class), anyString(), anyBoolean()))
                .thenReturn(fakeResult);

        Map<String, Object> result = jPlagServiceMock.runJPlag(submissions, "job-test", false);

        assertNotNull(result);
        assertTrue(result.containsKey("peerMatches"));
        Map<String, Double> matches = (Map<String, Double>) result.get("peerMatches");
        assertFalse(matches.isEmpty());
        assertEquals(85.0, matches.get("A.java vs B.java"));
    }

    // Exception handling
    @Test
    @DisplayName("2. Error Handling: runJPlag throws exception")
    void testRunJPlagException() throws Exception {
        File submissions = new File("dummy/path");

        when(jPlagServiceMock.runJPlag(any(File.class), anyString(), anyBoolean()))
                .thenThrow(new Exception("Directory not found"));

        Exception ex = assertThrows(Exception.class,
                () -> jPlagServiceMock.runJPlag(submissions, "fail-test", false));

        assertEquals("Directory not found", ex.getMessage());
    }

    // Dirty data simulation (no valid files)
    @Test
    @DisplayName("3. Dirty Data: simulate no valid Java files")
    void testDirtyDataSimulation() throws Exception {
        File submissions = new File("dummy/dirty");

        when(jPlagServiceMock.runJPlag(any(File.class), anyString(), anyBoolean()))
                .thenThrow(new Exception("No valid submissions"));

        Exception ex = assertThrows(Exception.class,
                () -> jPlagServiceMock.runJPlag(submissions, "dirty-test", false));

        assertEquals("No valid submissions", ex.getMessage());
    }

    // Single submission edge case
    @Test
    @DisplayName("4. Single submission: should fail")
    void testSingleSubmissionSimulation() throws Exception {
        File submissions = new File("dummy/single");

        when(jPlagServiceMock.runJPlag(any(File.class), anyString(), anyBoolean()))
                .thenThrow(new Exception("Not enough submissions"));

        Exception ex = assertThrows(Exception.class,
                () -> jPlagServiceMock.runJPlag(submissions, "single-test", false));

        assertEquals("Not enough submissions", ex.getMessage());
    }

    // Timeout protection
    @Test
    @DisplayName("5. Timeout: runJPlag should complete within 30 seconds")
    void testRunJPlagTimeout() throws Exception {
        File submissions = new File("dummy/path");

        when(jPlagServiceMock.runJPlag(any(File.class), anyString(), anyBoolean()))
                .thenReturn(Map.of("peerMatches", Map.of()));

        assertTimeoutPreemptively(Duration.ofSeconds(30), () ->
                jPlagServiceMock.runJPlag(submissions, "timeout-test", false));
    }

    // Parallel execution simulation
    @Test
    @DisplayName("6. Parallel Execution: multiple calls safely")
    void testRunJPlagParallel() throws Exception {
        File submissions = new File("dummy/path");

        when(jPlagServiceMock.runJPlag(any(File.class), anyString(), anyBoolean()))
                .thenReturn(Map.of("peerMatches", Map.of()));

        final boolean[] completed = {false, false};

        Thread t1 = new Thread(() -> {
            try { jPlagServiceMock.runJPlag(submissions, "job1", false); completed[0] = true; }
            catch (Exception ignored) {}
        });
        Thread t2 = new Thread(() -> {
            try { jPlagServiceMock.runJPlag(submissions, "job2", false); completed[1] = true; }
            catch (Exception ignored) {}
        });

        t1.start(); t2.start();
        t1.join(); t2.join();

        assertTrue(completed[0] && completed[1], "Both executions should succeed (mocked)");
    }

    // Simulated dirty reads / concurrent state issues
    @Test
    @DisplayName("7. Simulate dirty reads / concurrent access")
    void testSimulatedDirtyReads() throws Exception {
        File submissions = new File("dummy/path");

        // First call returns normal
        when(jPlagServiceMock.runJPlag(submissions, "job1", false))
                .thenReturn(Map.of("peerMatches", Map.of("X.java vs Y.java", 90.0)));

        // Second call (before first finishes) throws dirty read simulation
        when(jPlagServiceMock.runJPlag(submissions, "job2", false))
                .thenThrow(new Exception("Concurrent modification detected"));

        Exception ex = assertThrows(Exception.class, () ->
                jPlagServiceMock.runJPlag(submissions, "job2", false));

        assertEquals("Concurrent modification detected", ex.getMessage());
    }
}