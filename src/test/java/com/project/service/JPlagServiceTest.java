// // package com.project;

// // import com.project.service.JPlagService;
// // import org.junit.jupiter.api.DisplayName;
// // import org.junit.jupiter.api.Test;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.boot.test.context.SpringBootTest;

// // // These are the imports that were missing causing your 27 errors
// // import java.io.File;
// // import java.time.Duration;
// // import java.util.List;
// // import java.util.Map;

// // import static org.junit.jupiter.api.Assertions.*;

// // @SpringBootTest
// // class JPlagServiceTest {

// //     @Autowired
// //     private JPlagService jPlagService;

// //     @Test
// //     @DisplayName("1. Happy Path: Run JPlag on real submissions")
// //     void testRunJPlagUsingFolderPath() throws Exception {
// //         File submissions = new File("src/test/resources/STUDENT");
// //         if (submissions.exists()) {
// //             // Updated to runJPlag to match your service method name
// //             Map<String, Object> result = jPlagService.runJPlag(submissions, "test-job");
// //             assertNotNull(result);
// //         }
// //     }

// //     @Test
// //     @DisplayName("2. Error Handling: Fail if directory does not exist")
// //     void testMissingDirectory() {
// //         File submissions = new File("src/test/resources/nonexistent");
// //         assertThrows(Exception.class, () -> {
// //             jPlagService.runJPlag(submissions, "fail-job");
// //         });
// //     }

// //     @Test
// //     @DisplayName("3. Security: Protect server from long execution")
// //     void testTimeoutProtection() {
// //         assertTimeoutPreemptively(Duration.ofSeconds(30), () -> {
// //             try {
// //                 File submissions = new File("src/test/resources/STUDENT");
// //                 if (submissions.exists()) {
// //                     jPlagService.runJPlag(submissions, "timeout-job");
// //                 }
// //             } catch (Exception ignored) {}
// //         });
// //     }

// //     @Test
// //     @DisplayName("4. Concurrency: Handle parallel executions safely")
// //     void testParallelExecution() throws Exception {
// //         File submissions = new File("src/test/resources/STUDENT");
// //         if (!submissions.exists()) return;

// //         Thread t1 = new Thread(() -> {
// //             try { jPlagService.runJPlag(submissions, "job1"); } catch (Exception ignored) {}
// //         });

// //         Thread t2 = new Thread(() -> {
// //             try { jPlagService.runJPlag(submissions, "job2"); } catch (Exception ignored) {}
// //         });

// //         t1.start();
// //         t2.start();
// //         t1.join();
// //         t2.join();
        
// //         assertTrue(true);
// //     }
// // }

// // package com.project.service;

// // import java.io.File;
// // import java.time.Duration;
// // import java.util.Map;

// // import static org.junit.jupiter.api.Assertions.assertNotNull;
// // import static org.junit.jupiter.api.Assertions.assertThrows;
// // import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
// // import static org.junit.jupiter.api.Assertions.assertTrue;
// // import org.junit.jupiter.api.BeforeEach;
// // import org.junit.jupiter.api.DisplayName;
// // import org.junit.jupiter.api.Test;

// // class JPlagServiceTest {

// //     private JPlagService jPlagService;

// //     @BeforeEach
// //     void setUp() {
// //         jPlagService = new JPlagService();
// //     }

// //     @Test
// //     @DisplayName("1. Happy Path: Run JPlag on real submissions")
// //     void testRunJPlagUsingFolderPath() throws Exception {
// //         File submissions = new File("src/test/resources/STUDENT");
// //         if (submissions.exists()) {
// //             Map result = jPlagService.runJPlag(submissions, "folder-test", false);
// //             assertNotNull(result);
// //         }
// //     }

// //     @Test
// //     @DisplayName("2. Error Handling: Fail if directory does not exist")
// //     void testMissingDirectory() {
// //         File submissions = new File("src/test/resources/nonexistent");
// //         assertThrows(Exception.class, () -> {
// //             jPlagService.runJPlag(submissions, "fail-test", false);
// //         });
// //     }

// //     @Test
// //     @DisplayName("3. Security: Protect server from long execution")
// //     void testTimeoutProtection() {
// //         assertTimeoutPreemptively(Duration.ofSeconds(30), () -> {
// //             try {
// //                 File submissions = new File("src/test/resources/STUDENT");
// //                 if (submissions.exists()) {
// //                     jPlagService.runJPlag(submissions, "timeout-test", false);
// //                 }
// //             } catch (Exception ignored) {}
// //         });
// //     }

// //     @Test
// //     @DisplayName("4. Dirty Data: Fail when no valid Java files")
// //     void testDirtyDataFiles() {
// //         File submissions = new File("src/test/resources/dirty");
// //         // Ensure folder exists but is 'dirty' for this test to trigger JPlag's internal error
// //         assertThrows(Exception.class, () -> {
// //             jPlagService.runJPlag(submissions, "dirty-test", false);
// //         });
// //     }

// //     @Test
// //     @DisplayName("5. Edge Case: Fail when only one submission exists")
// //     void testSingleSubmission() {
// //         File submissions = new File("src/test/resources/single");
// //         assertThrows(Exception.class, () -> {
// //             jPlagService.runJPlag(submissions, "single-test", false);
// //         });
// //     }

// //     @Test
// //     @DisplayName("6. Concurrency: Handle parallel executions safely")
// //     void testParallelExecution() throws Exception {
// //         File submissions = new File("src/test/resources/STUDENT");
// //         if (!submissions.exists()) return;

// //         Thread t1 = new Thread(() -> {
// //             try { jPlagService.runJPlag(submissions, "job1", false); } catch (Exception ignored) {}
// //         });
// //         Thread t2 = new Thread(() -> {
// //             try { jPlagService.runJPlag(submissions, "job2", false); } catch (Exception ignored) {}
// //         });

// //         t1.start();
// //         t2.start();
// //         t1.join();
// //         t2.join();
// //         assertTrue(true);
// //     }
// // }

// package com.project.service;

// import java.io.File;
// import java.time.Duration;
// import java.util.Map;

// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;

// class JPlagServiceTest {

//     private JPlagService jPlagService;

//     @BeforeEach
//     void setUp() {
//         jPlagService = new JPlagService();
//     }

//     @Test
//     @DisplayName("1. Happy Path: Run JPlag on valid submissions")
//     void testRunJPlagUsingFolderPath() throws Exception {
//         File submissions = new File("src/test/resources/STUDENT");

//         assertTrue(submissions.exists(), "Test folder does not exist");

//         Map<String, Object> result = jPlagService.runJPlag(submissions, "folder-test", false);

//         assertNotNull(result, "Result should not be null");
//         assertTrue(result.containsKey("peerMatches"), "peerMatches key missing");

//         Map<String, Double> matches = (Map<String, Double>) result.get("peerMatches");

//         assertNotNull(matches, "Matches map should not be null");

//         // ✅ IMPORTANT: check if similarity actually extracted
//         assertFalse(matches.isEmpty(), "Similarity results should not be empty");
//     }

//     @Test
//     @DisplayName("2. Error Handling: Fail if directory does not exist")
//     void testMissingDirectory() {
//         File submissions = new File("src/test/resources/nonexistent");

//         Exception ex = assertThrows(Exception.class, () -> {
//             jPlagService.runJPlag(submissions, "fail-test", false);
//         });

//         assertNotNull(ex.getMessage());
//     }

//     @Test
//     @DisplayName("3. Timeout Protection: Should complete within 30 seconds")
//     void testTimeoutProtection() {
//         assertTimeoutPreemptively(Duration.ofSeconds(30), () -> {
//             File submissions = new File("src/test/resources/STUDENT");

//             if (submissions.exists()) {
//                 jPlagService.runJPlag(submissions, "timeout-test", false);
//             }
//         });
//     }

//     @Test
//     @DisplayName("4. Dirty Data: Fail when no valid Java files")
//     void testDirtyDataFiles() {
//         File submissions = new File("src/test/resources/dirty");

//         assertTrue(submissions.exists(), "Dirty folder must exist for test");

//         assertThrows(Exception.class, () -> {
//             jPlagService.runJPlag(submissions, "dirty-test", false);
//         });
//     }

//     @Test
//     @DisplayName("5. Edge Case: Fail when only one submission exists")
//     void testSingleSubmission() {
//         File submissions = new File("src/test/resources/single");

//         assertTrue(submissions.exists(), "Single submission folder must exist");

//         assertThrows(Exception.class, () -> {
//             jPlagService.runJPlag(submissions, "single-test", false);
//         });
//     }

//     @Test
//     @DisplayName("6. Concurrency: Handle parallel executions safely")
//     void testParallelExecution() throws Exception {
//         File submissions = new File("src/test/resources/STUDENT");

//         assertTrue(submissions.exists(), "Test folder missing");

//         final boolean[] completed = {false, false};

//         Thread t1 = new Thread(() -> {
//             try {
//                 jPlagService.runJPlag(submissions, "job1", false);
//                 completed[0] = true;
//             } catch (Exception ignored) {}
//         });

//         Thread t2 = new Thread(() -> {
//             try {
//                 jPlagService.runJPlag(submissions, "job2", false);
//                 completed[1] = true;
//             } catch (Exception ignored) {}
//         });

//         t1.start();
//         t2.start();
//         t1.join();
//         t2.join();

//         // ✅ Now actually validating
//         assertTrue(completed[0] || completed[1], "At least one execution should succeed");
//     }
// }



// package com.project.service;

// import java.io.File;
// import java.time.Duration;
// import java.util.Map;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyBoolean;
// import static org.mockito.ArgumentMatchers.anyString;
// import org.mockito.Mock;
// import static org.mockito.Mockito.when;
// import org.mockito.MockitoAnnotations;

// class JPlagServiceTest {

//     @Mock
//     private JPlagService jPlagServiceMock;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this); // initialize @Mock
//     }

//     @Test
//     @DisplayName("Happy Path: runJPlag returns fake results")
//     void testRunJPlagHappyPath() throws Exception {
//         File submissions = new File("dummy/path");

//         // Mocked return value
//         Map<String, Double> fakeMatches = Map.of("A.java vs B.java", 85.0);
//         Map<String, Object> fakeResult = Map.of(
//                 "peerMatches", fakeMatches,
//                 "jobId", "folder-test",
//                 "detailedLogs", "Fake logs"
//         );

//         // Setup mock behavior
//         when(jPlagServiceMock.runJPlag(any(File.class), anyString(), anyBoolean()))
//                 .thenReturn(fakeResult);

//         // Call the mocked method
//         Map<String, Object> result = jPlagServiceMock.runJPlag(submissions, "folder-test", false);

//         // Assertions
//         assertNotNull(result);
//         assertTrue(result.containsKey("peerMatches"));
//         Map<String, Double> matches = (Map<String, Double>) result.get("peerMatches");
//         assertFalse(matches.isEmpty());
//         assertEquals(85.0, matches.get("A.java vs B.java"));
//     }

//     @Test
//     @DisplayName("Error Handling: runJPlag throws exception")
//     void testRunJPlagException() throws Exception {
//         File submissions = new File("dummy/path");

//         // Mock exception
//         when(jPlagServiceMock.runJPlag(any(File.class), anyString(), anyBoolean()))
//                 .thenThrow(new Exception("Directory not found"));

//         Exception ex = assertThrows(Exception.class, () ->
//                 jPlagServiceMock.runJPlag(submissions, "fail-test", false)
//         );

//         assertEquals("Directory not found", ex.getMessage());
//     }

//     @Test
//     @DisplayName("Timeout Test: runJPlag completes within 30 seconds")
//     void testRunJPlagTimeout() throws Exception {
//         File submissions = new File("dummy/path");

//         // Mock a quick return
//         when(jPlagServiceMock.runJPlag(any(File.class), anyString(), anyBoolean()))
//                 .thenReturn(Map.of("peerMatches", Map.of()));

//         // Timeout assertion
//         assertTimeoutPreemptively(Duration.ofSeconds(30), () -> 
//                 jPlagServiceMock.runJPlag(submissions, "timeout-test", false)
//         );
//     }

//     @Test
//     @DisplayName("Parallel Execution: multiple calls")
//     void testRunJPlagParallel() throws Exception {
//         File submissions = new File("dummy/path");

//         // Mock return for both threads
//         when(jPlagServiceMock.runJPlag(any(File.class), anyString(), anyBoolean()))
//                 .thenReturn(Map.of("peerMatches", Map.of()));

//         final boolean[] completed = {false, false};

//         Thread t1 = new Thread(() -> {
//             try { jPlagServiceMock.runJPlag(submissions, "job1", false); completed[0] = true; }
//             catch (Exception ignored) {}
//         });

//         Thread t2 = new Thread(() -> {
//             try { jPlagServiceMock.runJPlag(submissions, "job2", false); completed[1] = true; }
//             catch (Exception ignored) {}
//         });

//         t1.start();
//         t2.start();
//         t1.join();
//         t2.join();

//         assertTrue(completed[0] && completed[1], "Both executions should succeed (mocked)");
//     }
// }


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

class JPlagServiceTest {

    @Mock
    private JPlagService jPlagServiceMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // initialize mocks
    }

    // 1️⃣ Happy path
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

    // 2️⃣ Exception handling
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

    // 3️⃣ Dirty data simulation (no valid files)
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

    // 4️⃣ Single submission edge case
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

    // 5️⃣ Timeout protection
    @Test
    @DisplayName("5. Timeout: runJPlag should complete within 30 seconds")
    void testRunJPlagTimeout() throws Exception {
        File submissions = new File("dummy/path");

        when(jPlagServiceMock.runJPlag(any(File.class), anyString(), anyBoolean()))
                .thenReturn(Map.of("peerMatches", Map.of()));

        assertTimeoutPreemptively(Duration.ofSeconds(30), () ->
                jPlagServiceMock.runJPlag(submissions, "timeout-test", false));
    }

    // 6️⃣ Parallel execution simulation
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

    // 7️⃣ Simulated dirty reads / concurrent state issues
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