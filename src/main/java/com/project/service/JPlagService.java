// // // // // package com.project.service;

// // // // // import java.io.BufferedReader;
// // // // // import java.io.File;
// // // // // import java.io.InputStreamReader;
// // // // // import java.nio.file.Files;
// // // // // import java.util.ArrayList;
// // // // // import java.util.HashMap;
// // // // // import java.util.List;
// // // // // import java.util.Map;

// // // // // import org.springframework.stereotype.Service;

// // // // // @Service
// // // // // public class JPlagService {
// // // // //     public Map<String, Object> runJPlag(File submissionsDir, String jobId) throws Exception {
// // // // //         Map<String, Double> similarityMap = new HashMap<>();
// // // // //         List<String> logs = new ArrayList<>();
// // // // //         File outputDir = new File("jplag-output-" + jobId);
        
// // // // //         // Added "-t", "5" to catch smaller similarities
// // // // //         ProcessBuilder pb = new ProcessBuilder(
// // // // //             "java", "-jar", "jplag-6.2.0-jar-with-dependencies.jar",
// // // // //             "-l", "java", 
// // // // //             "-t", "5", 
// // // // //             "-r", outputDir.getAbsolutePath(),
// // // // //             "--shown-comparisons", "10", 
// // // // //             "--overwrite",
// // // // //             submissionsDir.getAbsolutePath()
// // // // //         );

// // // // //         pb.redirectErrorStream(true);
// // // // //         Process process = pb.start();

// // // // //         // Capture logs in real-time
// // // // //         try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
// // // // //             String line;
// // // // //             while ((line = reader.readLine()) != null) {
// // // // //                 logs.add(line);
// // // // //                 System.out.println("[JPlag]: " + line);
// // // // //             }
// // // // //         }
// // // // //         process.waitFor();

// // // // //         // Parse results
// // // // //         File jsonFile = new File(outputDir, "results.json");
// // // // //         if (jsonFile.exists()) {
// // // // //             String content = Files.readString(jsonFile.toPath());
// // // // //             String[] segments = content.split("\\{");
// // // // //             for (String s : segments) {
// // // // //                 if (s.contains("firstSubmission") && s.contains("similarity")) {
// // // // //                     try {
// // // // //                         String f1 = extract(s, "firstSubmission");
// // // // //                         String f2 = extract(s, "secondSubmission");
// // // // //                         // Clean the similarity string to ensure it parses as Double
// // // // //                         String simRaw = extract(s, "similarity").replaceAll("[^0-9.]", "");
// // // // //                         double simValue = Double.parseDouble(simRaw);
                        
// // // // //                         // JPlag 6.x sometimes returns similarity as a 0-1 decimal or 0-100 percentage.
// // // // //                         // If it's < 1, we multiply by 100.
// // // // //                         double percentage = (simValue <= 1.0) ? simValue * 100 : simValue;
                        
// // // // //                         similarityMap.put(f1 + " vs " + f2, percentage);
// // // // //                     } catch (Exception e) {
// // // // //                         System.err.println("Skip parsing segment due to: " + e.getMessage());
// // // // //                     }
// // // // //                 }
// // // // //             }
// // // // //         }
        
// // // // //         Map<String, Object> result = new HashMap<>();
// // // // //         result.put("similarities", similarityMap);
// // // // //         result.put("logs", logs);
// // // // //         return result;
// // // // //     }

// // // // //     private String extract(String b, String k) {
// // // // //         try {
// // // // //             int start = b.indexOf(k) + k.length() + 3;
// // // // //             int end = b.indexOf("\"", start);
// // // // //             if (end == -1 || end < start) {
// // // // //                 end = b.indexOf(",", start);
// // // // //             }
// // // // //             if (end == -1) {
// // // // //                 end = b.indexOf("}", start);
// // // // //             }
// // // // //             return b.substring(start, end).replace("\"", "").trim();
// // // // //         } catch (Exception e) {
// // // // //             return "0";
// // // // //         }
// // // // //     }
// // // // // }

// // // // package com.project.service;

// // // // import java.io.BufferedReader;
// // // // import java.io.File;
// // // // import java.io.IOException;
// // // // import java.io.InputStreamReader;
// // // // import java.nio.file.Files;
// // // // import java.util.ArrayList;
// // // // import java.util.Collections;
// // // // import java.util.HashMap;
// // // // import java.util.List;
// // // // import java.util.Map;
// // // // import java.util.concurrent.TimeUnit;

// // // // import org.springframework.stereotype.Service;

// // // // @Service
// // // // public class JPlagService {
// // // //     public Map<String, Object> runJPlag(File submissionsDir, String jobId) throws Exception {
// // // //         Map<String, Double> similarityMap = new HashMap<>();
// // // //         List<String> logs = Collections.synchronizedList(new ArrayList<>());
// // // //         File outputDir = new File("jplag-output-" + jobId);
        
// // // //         // CRITICAL: "-m" disables the browser pop-up that hangs your tests
// // // //         ProcessBuilder pb = new ProcessBuilder(
// // // //             "java", "-jar", "jplag-6.2.0-jar-with-dependencies.jar",
// // // //             "-l", "java", 
// // // //             "-t", "5", 
// // // //             "-m", "0", // Disable the report viewer popup
// // // //             "-r", outputDir.getAbsolutePath(),
// // // //             "--shown-comparisons", "10", 
// // // //             "--overwrite",
// // // //             submissionsDir.getAbsolutePath()
// // // //         );

// // // //         pb.redirectErrorStream(true);
// // // //         Process process = pb.start();

// // // //         // Drain logs in a separate thread so the process buffer doesn't clog
// // // //         Thread logDrainer = new Thread(() -> {
// // // //             try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
// // // //                 String line;
// // // //                 while ((line = reader.readLine()) != null) {
// // // //                     logs.add(line);
// // // //                     System.out.println("[JPlag]: " + line);
// // // //                 }
// // // //             } catch (IOException ignored) {}
// // // //         });
// // // //         logDrainer.setDaemon(true);
// // // //         logDrainer.start();

// // // //         // Wait max 30s so the test environment doesn't hang forever
// // // //         boolean finished = process.waitFor(30, TimeUnit.SECONDS);
// // // //         if (!finished) {
// // // //             process.destroyForcibly();
// // // //             throw new Exception("JPlag timed out. Check if the JAR is stuck.");
// // // //         }

// // // //         // Parse results
// // // //         File jsonFile = new File(outputDir, "results.json");
// // // //         if (jsonFile.exists()) {
// // // //             String content = Files.readString(jsonFile.toPath());
// // // //             parseJsonManual(content, similarityMap);
// // // //         }
        
// // // //         return Map.of("similarities", similarityMap, "logs", logs);
// // // //     }

// // // //     private void parseJsonManual(String content, Map<String, Double> map) {
// // // //         String[] segments = content.split("\\{");
// // // //         for (String s : segments) {
// // // //             if (s.contains("firstSubmission") && s.contains("similarity")) {
// // // //                 try {
// // // //                     String f1 = extract(s, "firstSubmission");
// // // //                     String f2 = extract(s, "secondSubmission");
// // // //                     String simRaw = extract(s, "similarity").replaceAll("[^0-9.]", "");
// // // //                     double val = Double.parseDouble(simRaw);
// // // //                     map.put(f1 + " vs " + f2, (val <= 1.0) ? val * 100 : val);
// // // //                 } catch (Exception ignored) {}
// // // //             }
// // // //         }
// // // //     }

// // // //     private String extract(String b, String k) {
// // // //         try {
// // // //             int start = b.indexOf(k) + k.length() + 3;
// // // //             int end = b.indexOf("\"", start);
// // // //             if (end == -1 || end < start) end = b.indexOf(",", start);
// // // //             if (end == -1) end = b.indexOf("}", start);
// // // //             return b.substring(start, end).replace("\"", "").trim();
// // // //         } catch (Exception e) { return "0"; }
// // // //     }
// // // // }

// // // package com.project.service;

// // // import java.io.BufferedReader;
// // // import java.io.File;
// // // import java.io.InputStreamReader;
// // // import java.util.ArrayList;
// // // import java.util.Collections;
// // // import java.util.HashMap;
// // // import java.util.List;
// // // import java.util.Map;
// // // import java.util.concurrent.TimeUnit;

// // // import org.springframework.stereotype.Service;

// // // @Service
// // // public class JPlagService {

// // //     public Map<String, Object> runJPlag(File submissionsDir, String jobId, boolean showReport) throws Exception {
// // //         List<String> logs = Collections.synchronizedList(new ArrayList<>());
        
// // //         if (submissionsDir == null || !submissionsDir.exists()) {
// // //             throw new Exception("Directory does not exist.");
// // //         }

// // //         // We use -r to create a specific result file we can find later
// // //         String resultFileName = "jplag-result-" + jobId + ".jplag";

// // //         List<String> command = new ArrayList<>(List.of(
// // //             "java", "-jar", "jplag-6.2.0-jar-with-dependencies.jar",
// // //             submissionsDir.getAbsolutePath(),
// // //             "-l", "java",
// // //             "-t", "5",
// // //             "-r", resultFileName,
// // //             "--mode", "RUN"  // RUN mode analyzes and exits. No freezing!
// // //         ));

// // //         ProcessBuilder pb = new ProcessBuilder(command);
// // //         pb.redirectErrorStream(true);
// // //         Process process = pb.start();

// // //         try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
// // //             String line;
// // //             while ((line = reader.readLine()) != null) {
// // //                 logs.add(line);
// // //                 System.out.println("[JPlag]: " + line);
// // //             }
// // //         }

// // //         boolean finished = process.waitFor(60, TimeUnit.SECONDS);
// // //         if (!finished || process.exitValue() != 0) {
// // //             process.destroyForcibly();
// // //             throw new Exception("JPlag failed. Exit code: " + (finished ? process.exitValue() : "Timeout"));
// // //         }

// // //         // Master Switch: If true (from UI), launch the browser viewer
// // //         if (showReport) {
// // //             openReportViewer(jobId);
// // //         }

// // //         return Map.of("detailedLogs", logs, "jobId", jobId, "peerMatches", new HashMap<>());
// // //     }

// // //     public void openReportViewer(String jobId) throws Exception {
// // //         String resultFileName = "jplag-result-" + jobId + ".jplag";
// // //         File resultFile = new File(resultFileName);

// // //         if (!resultFile.exists()) {
// // //             throw new Exception("Report file not found. Run analysis first.");
// // //         }

// // //         // VIEW mode starts the interactive web server and opens the browser
// // //         new ProcessBuilder("java", "-jar", "jplag-6.2.0-jar-with-dependencies.jar", 
// // //                            "--mode", "VIEW", resultFile.getAbsolutePath()).start();
// // //     }
// // // }


// // package com.project.service;

// // import java.awt.Desktop;
// // import java.io.BufferedReader;
// // import java.io.File;
// // import java.io.InputStreamReader;
// // import java.net.URI;
// // import java.util.ArrayList;
// // import java.util.HashMap;
// // import java.util.List;
// // import java.util.Map;
// // import java.util.concurrent.TimeUnit;

// // import org.springframework.stereotype.Service;

// // @Service
// // public class JPlagService {

// //     public Map<String, Object> runJPlag(File submissionsDir, String jobId, boolean showReport) throws Exception {
// //         List<String> logs = new ArrayList<>();
        
// //         // 1. THE FIX: Force rename files to .java if they are missing extensions
// //         // This stops the "Nothing to parse" error
// //         prepareFilesForParsing(submissionsDir);

// //         File outputDir = new File("jplag-output-" + jobId);
        
// //         // 2. Command with lower threshold just to be safe
// //         ProcessBuilder pb = new ProcessBuilder(
// //             "java", "-jar", "jplag-6.2.0-jar-with-dependencies.jar",
// //             submissionsDir.getAbsolutePath(),
// //             "-l", "java",
// //             "-t", "2", 
// //             "--overwrite",
// //             "-r", outputDir.getAbsolutePath() + ".jplag", // JPlag 6.2 likes the .jplag suffix
// //             "--mode", "RUN"
// //         );

// //         pb.redirectErrorStream(true);
// //         Process process = pb.start();

// //         try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
// //             String line;
// //             while ((line = reader.readLine()) != null) {
// //                 logs.add(line);
// //                 System.out.println("[JPlag]: " + line);
// //             }
// //         }
// //         process.waitFor(30, TimeUnit.SECONDS);

// //         if (showReport) {
// //             openReportViewer(jobId);
// //         }

// //         return Map.of("detailedLogs", logs, "jobId", jobId, "peerMatches", new HashMap<>());
// //     }

// //     // This method ensures JPlag actually sees .java files
// //     private void prepareFilesForParsing(File root) {
// //         File[] students = root.listFiles();
// //         if (students == null) return;
// //         for (File student : students) {
// //             if (student.isDirectory()) {
// //                 File[] files = student.listFiles();
// //                 if (files != null) {
// //                     for (File f : files) {
// //                         // If file is named 'test' or 'Test' without .java, fix it
// //                         if (!f.getName().endsWith(".java")) {
// //                             f.renameTo(new File(f.getParent(), "Test.java"));
// //                         }
// //                     }
// //                 }
// //             }
// //         }
// //     }

// //     public void openReportViewer(String jobId) throws Exception {
// //         File reportFile = new File("jplag-output-" + jobId + ".jplag");
// //         if (!reportFile.exists()) {
// //             System.out.println("Cannot open: Report file missing!");
// //             return;
// //         }

// //         // Start the viewer server
// //         new ProcessBuilder("java", "-jar", "jplag-6.2.0-jar-with-dependencies.jar", 
// //                            "--mode", "VIEW", reportFile.getAbsolutePath()).start();

// //         // Wait for server and open browser
// //         Thread.sleep(3000);
// //         if (Desktop.isDesktopSupported()) {
// //             Desktop.getDesktop().browse(new URI("http://localhost:19145"));
// //         }
// //     }
// // }


// package com.project.service;

// import java.awt.Desktop;
// import java.io.BufferedReader;
// import java.io.File;
// import java.io.InputStreamReader;
// import java.net.URI;
// import java.nio.file.Files;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.concurrent.TimeUnit;
// import java.util.stream.Collectors;
// import java.util.zip.ZipEntry;
// import java.util.zip.ZipFile;

// import org.springframework.stereotype.Service;

// @Service
// public class JPlagService {

//     public Map<String, Object> runJPlag(File submissionsDir, String jobId, boolean showReport) throws Exception {
//         List<String> logs = new ArrayList<>();
//         Map<String, Double> similarityMap = new HashMap<>(); 
        
//         // 1. Sanitize: Force .java extensions and remove package lines
//         prepareFilesForParsing(submissionsDir);

//         // JPlag 6.2.0 produces a .jplag zip file
//         File reportFile = new File("jplag-output-" + jobId + ".jplag");
        
//         // 2. RUN Mode
//         ProcessBuilder pb = new ProcessBuilder(
//             "java", "-jar", "jplag-6.2.0-jar-with-dependencies.jar",
//             submissionsDir.getAbsolutePath(),
//             "-l", "java",
//             "-t", "2", 
//             "--overwrite",
//             "-r", reportFile.getAbsolutePath(),
//             "--mode", "RUN"
//         );

//         pb.redirectErrorStream(true);
//         Process process = pb.start();

//         try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//             String line;
//             while ((line = reader.readLine()) != null) {
//                 logs.add(line);
//                 System.out.println("[JPlag]: " + line);
//             }
//         }
//         process.waitFor(30, TimeUnit.SECONDS);

//         // 3. TABLE FIX: Extract results.json from the .jplag zip to fill the UI table
//         if (reportFile.exists()) {
//             extractSimilarityData(reportFile, similarityMap);
//         }

//         // 4. BROWSER FIX: Open the interactive report if requested
//         if (showReport) {
//             openReportViewer(jobId);
//         }

//         // Return everything to the Controller
//         return Map.of(
//             "detailedLogs", logs, 
//             "jobId", jobId, 
//             "peerMatches", similarityMap // This fills your 'Audit Summary' table
//         );
//     }

//     private void prepareFilesForParsing(File root) {
//         File[] students = root.listFiles();
//         if (students == null) return;
//         for (File student : students) {
//             if (student.isDirectory()) {
//                 File[] files = student.listFiles();
//                 if (files != null) {
//                     for (File f : files) {
//                         File target = f;
//                         // Fix extension
//                         if (!f.getName().toLowerCase().endsWith(".java")) {
//                             target = new File(f.getParent(), "Test.java");
//                             f.renameTo(target);
//                         }
//                         // Remove package lines (JPlag parser fails if package doesn't match folder)
//                         try {
//                             List<String> lines = Files.readAllLines(target.toPath());
//                             List<String> clean = lines.stream()
//                                 .filter(l -> !l.trim().startsWith("package "))
//                                 .collect(Collectors.toList());
//                             Files.write(target.toPath(), clean);
//                         } catch (Exception ignored) {}
//                     }
//                 }
//             }
//         }
//     }

//     private void extractSimilarityData(File zipFile, Map<String, Double> map) {
//         try (ZipFile zip = new ZipFile(zipFile)) {
//             ZipEntry entry = zip.getEntry("results.json");
//             if (entry != null) {
//                 try (BufferedReader reader = new BufferedReader(new InputStreamReader(zip.getInputStream(entry)))) {
//                     String json = reader.lines().collect(Collectors.joining());
//                     parseResultsJson(json, map);
//                 }
//             }
//         } catch (Exception e) {
//             System.err.println("Error reading results.json: " + e.getMessage());
//         }
//     }

//     private void parseResultsJson(String json, Map<String, Double> map) {
//         // Simple manual parse to avoid adding heavy Jackson/Gson dependencies
//         String[] comparisons = json.split("\\{");
//         for (String comp : comparisons) {
//             if (comp.contains("firstSubmission") && comp.contains("similarity")) {
//                 try {
//                     String s1 = extractJsonValue(comp, "firstSubmission");
//                     String s2 = extractJsonValue(comp, "secondSubmission");
//                     String simStr = extractJsonValue(comp, "similarity").replaceAll("[^0-9.]", "");
//                     double sim = Double.parseDouble(simStr);
//                     // JPlag uses 0.0-1.0; convert to 0-100%
//                     double score = (sim <= 1.0) ? sim * 100 : sim;
//                     map.put(s1 + " vs " + s2, score);
//                 } catch (Exception ignored) {}
//             }
//         }
//     }

//     private String extractJsonValue(String chunk, String key) {
//         try {
//             int start = chunk.indexOf(key) + key.length() + 3;
//             int end = chunk.indexOf("\"", start);
//             if (end == -1) end = chunk.indexOf(",", start);
//             return chunk.substring(start, end).replace("\"", "").trim();
//         } catch (Exception e) { return "Unknown"; }
//     }

//     public void openReportViewer(String jobId) throws Exception {
//         File reportFile = new File("jplag-output-" + jobId + ".jplag");
//         if (!reportFile.exists()) return;

//         new ProcessBuilder("java", "-jar", "jplag-6.2.0-jar-with-dependencies.jar", 
//                            "--mode", "VIEW", reportFile.getAbsolutePath()).start();

//         Thread.sleep(2500); 
//         if (Desktop.isDesktopSupported()) {
//             Desktop.getDesktop().browse(new URI("http://localhost:19145"));
//         }
//     }
// }



//PROPER


package com.project.service;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JPlagService {

    private final ObjectMapper mapper = new ObjectMapper();

    public Map<String, Object> runJPlag(File submissionsDir, String jobId, boolean showReport) throws Exception {

        List<String> logs = new ArrayList<>();
        Map<String, Double> similarityMap = new HashMap<>();

        //Clean + prepare files
        prepareFiles(submissionsDir);

        File reportFile = new File("jplag-output-" + jobId + ".jplag");

        // delete old file
        if (reportFile.exists()) reportFile.delete();

        ProcessBuilder pb = new ProcessBuilder(
                "java", "-jar", "jplag-6.2.0-jar-with-dependencies.jar",
                submissionsDir.getAbsolutePath(),
                "-l", "java",
                "-t", "2",
                "--overwrite",
                "-r", reportFile.getAbsolutePath(),
                "--mode", "RUN"
        );

        pb.redirectErrorStream(true);
        Process process = pb.start();

        // capture logs
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logs.add(line);
                System.out.println("[JPlag] " + line);
            }
        }

        boolean finished = process.waitFor(60, TimeUnit.SECONDS);

        if (!finished || process.exitValue() != 0) {
            process.destroyForcibly();
            throw new Exception("JPlag failed or timed out");
        }

        // Extract results
        if (reportFile.exists()) {
            extractResults(reportFile, similarityMap);
        }

        if (showReport) {
            openReportViewer(jobId);
        }

        return Map.of(
                "detailedLogs", logs,
                "peerMatches", similarityMap,
                "jobId", jobId
        );
    }

    //FIXED FILE PREPARATION
    private void prepareFiles(File root) throws IOException {

        Files.walk(root.toPath())
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    try {
                        File file = path.toFile();

                        // ensure .java extension
                        if (!file.getName().endsWith(".java")) {
                            File renamed = new File(file.getParent(), file.getName() + ".java");
                            file.renameTo(renamed);
                            file = renamed;
                        }

                        // remove package lines
                        List<String> lines = Files.readAllLines(file.toPath());
                        List<String> clean = new ArrayList<>();

                        for (String line : lines) {
                            if (!line.trim().startsWith("package ")) {
                                clean.add(line);
                            }
                        }

                        Files.write(file.toPath(), clean);

                    } catch (Exception ignored) {}
                });
    }

    
private void extractResults(File zipFile, Map<String, Double> map) {

    try (ZipFile zip = new ZipFile(zipFile)) {

        Enumeration<? extends ZipEntry> entries = zip.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();

            System.out.println("ENTRY FOUND: " + entry.getName());

            // CASE 1: Individual comparison files (MAIN SOURCE)
            if (entry.getName().contains("comparisons/") && entry.getName().endsWith(".json")) {

                try (InputStream is = zip.getInputStream(entry)) {

                    JsonNode root = mapper.readTree(is);

                    System.out.println("FULL JSON ↓↓↓");
                    System.out.println(root.toPrettyString());

                    if (root.has("similarities")) {

                        String s1 = root.get("firstSubmissionId").asText();
                        String s2 = root.get("secondSubmissionId").asText();

                        double similarity = root.get("similarities").get("AVG").asDouble();

                        System.out.println("MATCH FOUND: " + s1 + " vs " + s2 + " = " + similarity);

                        map.put(s1 + " vs " + s2, similarity * 100);
                    }
                }
            }

            // CASE 2: topComparisons.json (backup / summary)
            else if (entry.getName().equals("topComparisons.json")) {

                try (InputStream is = zip.getInputStream(entry)) {

                    JsonNode array = mapper.readTree(is);

                    System.out.println("TOP COMPARISONS ↓↓↓");
                    System.out.println(array.toPrettyString());

                    if (array.isArray()) {

                        for (JsonNode comp : array) {

                            String s1 = comp.get("firstSubmission").asText();
                            String s2 = comp.get("secondSubmission").asText();

                            double similarity = comp.get("similarities").get("AVG").asDouble();

                            System.out.println("TOP MATCH: " + s1 + " vs " + s2 + " = " + similarity);

                            map.put(s1 + " vs " + s2, similarity * 100);
                        }
                    }
                }
            }
        }

    } catch (Exception e) {
        System.err.println("Error parsing results: " + e.getMessage());
        e.printStackTrace();
    }
}
    public void openReportViewer(String jobId) throws Exception {

        File reportFile = new File("jplag-output-" + jobId + ".jplag");

        if (!reportFile.exists()) return;

        new ProcessBuilder(
                "java", "-jar", "jplag-6.2.0-jar-with-dependencies.jar",
                "--mode", "VIEW",
                reportFile.getAbsolutePath()
        ).start();

        Thread.sleep(3000);

        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI("http://localhost:19145"));
        }
    }
}