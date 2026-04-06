// // // package com.project.controller;

// // // import com.project.model.PlagiarismResponse;
// // // import com.project.service.FileService;
// // // import com.project.service.JPlagService;
// // // import org.springframework.http.ResponseEntity;
// // // import org.springframework.web.bind.annotation.*;
// // // import org.springframework.web.multipart.MultipartFile;
// // // import java.io.File;
// // // import java.util.*;

// // // @RestController
// // // @RequestMapping("/api/plagiarism")
// // // @CrossOrigin(origins = "*")
// // // public class PlagiarismController {

// // //     private final FileService fileService;
// // //     private final JPlagService jPlagService;

// // //     public PlagiarismController(FileService fileService, JPlagService jPlagService) {
// // //         this.fileService = fileService;
// // //         this.jPlagService = jPlagService;
// // //     }

// // //     @PostMapping("/upload")
// // //     public ResponseEntity<PlagiarismResponse> handleUpload(@RequestParam("file") MultipartFile file) {
// // //         String jobId = "job_" + System.currentTimeMillis();
// // //         try {
// // //             File folder = fileService.extractZip(file, jobId);
// // //             Map<String, Object> result = jPlagService.runJPlag(folder, jobId);

// // //             return ResponseEntity.ok(new PlagiarismResponse(
// // //                 jobId,
// // //                 (Map<String, Double>) result.get("similarities"),
// // //                 (List<String>) result.get("logs"),
// // //                 "Success"
// // //             ));
// // //         } catch (Exception e) {
// // //             return ResponseEntity.internalServerError().body(
// // //                 new PlagiarismResponse(jobId, null, List.of(e.getMessage()), "Error")
// // //             );
// // //         }
// // //     }
// // // }

// // // package com.project.controller;

// // // import com.project.model.PlagiarismResponse;
// // // import com.project.service.FileService;
// // // import com.project.service.JPlagService;
// // // import org.springframework.http.ResponseEntity;
// // // import org.springframework.web.bind.annotation.*;
// // // import org.springframework.web.multipart.MultipartFile;
// // // import java.io.File;
// // // import java.util.*;

// // // @RestController
// // // @RequestMapping("/api/plagiarism")
// // // @CrossOrigin(origins = "*")
// // // public class PlagiarismController {

// // //     private final FileService fileService;
// // //     private final JPlagService jPlagService;

// // //     public PlagiarismController(FileService fileService, JPlagService jPlagService) {
// // //         this.fileService = fileService;
// // //         this.jPlagService = jPlagService;
// // //     }

// // //     @PostMapping("/upload")
// // //     public ResponseEntity<PlagiarismResponse> handleUpload(@RequestParam("file") MultipartFile file) {
// // //         String jobId = "job_" + System.currentTimeMillis();
// // //         try {
// // //             File folder = fileService.extractZip(file, jobId);
            
// // //             // FIX: Added 'false' as the 3rd argument so it doesn't hang the server
// // //             Map<String, Object> result = jPlagService.runJPlag(folder, jobId, false);

// // //             return ResponseEntity.ok(new PlagiarismResponse(
// // //                 jobId,
// // //                 (Map<String, Double>) result.get("peerMatches"),
// // //                 (List<String>) result.get("detailedLogs"),
// // //                 "Success"
// // //             ));
// // //         } catch (Exception e) {
// // //             e.printStackTrace();
// // //             return ResponseEntity.internalServerError().body(
// // //                 new PlagiarismResponse(jobId, null, List.of(e.getMessage()), "Error")
// // //             );
// // //         }
// // //     }

// // //     @GetMapping("/view-report")
// // //     public ResponseEntity<String> viewReport(@RequestParam String jobId) {
// // //         try {
// // //             jPlagService.openReportViewer(jobId);
// // //             return ResponseEntity.ok("Viewer started");
// // //         } catch (Exception e) {
// // //             return ResponseEntity.internalServerError().body(e.getMessage());
// // //         }
// // //     }
// // // }


// // package com.project.controller;

// // import java.io.File;
// // import java.util.List;
// // import java.util.Map;

// // import org.springframework.http.ResponseEntity;
// // import org.springframework.web.bind.annotation.GetMapping;
// // import org.springframework.web.bind.annotation.PostMapping;
// // import org.springframework.web.bind.annotation.RequestMapping;
// // import org.springframework.web.bind.annotation.RequestParam;
// // import org.springframework.web.bind.annotation.RestController;
// // import org.springframework.web.multipart.MultipartFile;

// // import com.project.model.PlagiarismResponse;
// // import com.project.service.FileService;
// // import com.project.service.JPlagService;

// // @RestController
// // @RequestMapping("/api/plagiarism")
// // public class PlagiarismController {

// //     private final FileService fileService;
// //     private final JPlagService jPlagService;

// //     public PlagiarismController(FileService fileService, JPlagService jPlagService) {
// //         this.fileService = fileService;
// //         this.jPlagService = jPlagService;
// //     }

// //     @PostMapping("/upload")
// //     public ResponseEntity<PlagiarismResponse> handleUpload(@RequestParam("file") MultipartFile file) {
// //         String jobId = "job_" + System.currentTimeMillis();
// //         try {
// //             File folder = fileService.extractZip(file, jobId);
            
// //             // Step 1: Run silently (showReport = false)
// //             Map<String, Object> result = jPlagService.runJPlag(folder, jobId, false);

// //             return ResponseEntity.ok(new PlagiarismResponse(
// //                 jobId, 
// //                 (Map<String, Double>) result.get("peerMatches"), 
// //                 (List<String>) result.get("detailedLogs"), 
// //                 "Success"
// //             ));
// //         } catch (Exception e) {
// //             return ResponseEntity.internalServerError().body(new PlagiarismResponse(jobId, null, List.of(e.getMessage()), "Error"));
// //         }
// //     }

// //     @GetMapping("/view-report")
// //     public ResponseEntity<String> viewReport(@RequestParam String jobId) {
// //         try {
// //             // Step 2: Open the viewer manually when the user clicks the button
// //             jPlagService.openReportViewer(jobId); 
// //             return ResponseEntity.ok("Viewer started");
// //         } catch (Exception e) {
// //             return ResponseEntity.internalServerError().body("Failed to open viewer: " + e.getMessage());
// //         }
// //     }
// // }


// package com.project.controller;

// import java.io.File;
// import java.util.List;
// import java.util.Map;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;

// import com.project.model.PlagiarismResponse;
// import com.project.service.FileService;
// import com.project.service.JPlagService;

// @RestController
// @RequestMapping("/api/plagiarism")
// public class PlagiarismController {

//     private final FileService fileService;
//     private final JPlagService jPlagService;

//     public PlagiarismController(FileService fileService, JPlagService jPlagService) {
//         this.fileService = fileService;
//         this.jPlagService = jPlagService;
//     }

//     @PostMapping("/upload")
//     public ResponseEntity<PlagiarismResponse> handleUpload(@RequestParam("file") MultipartFile file) {
//         String jobId = "job_" + System.currentTimeMillis();
//         try {
//             File folder = fileService.extractZip(file, jobId);
            
//             // Fixed: Added the 3rd argument (false)
//             Map<String, Object> result = jPlagService.runJPlag(folder, jobId, false);

//             return ResponseEntity.ok(new PlagiarismResponse(
//                 jobId, 
//                 (Map<String, Double>) result.get("peerMatches"), 
//                 (List<String>) result.get("detailedLogs"), 
//                 "Success"
//             ));
//         } catch (Exception e) {
//             return ResponseEntity.internalServerError().body(
//                 new PlagiarismResponse(jobId, null, List.of(e.getMessage()), "Error")
//             );
//         }
//     }

//     @GetMapping("/view-report")
//     public ResponseEntity<String> viewReport(@RequestParam String jobId) {
//         try {
//             // Fixed: Service now accepts this String jobId correctly
//             jPlagService.openReportViewer(jobId); 
//             return ResponseEntity.ok("Viewer started");
//         } catch (Exception e) {
//             return ResponseEntity.internalServerError().body("Failed: " + e.getMessage());
//         }
//     }
// }

package com.project.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.model.PlagiarismResponse;
import com.project.service.FileService;
import com.project.service.JPlagService;

@RestController
@RequestMapping("/api/plagiarism")
@CrossOrigin("*")
public class PlagiarismController {

    private final FileService fileService;
    private final JPlagService jPlagService;

    public PlagiarismController(FileService fileService, JPlagService jPlagService) {
        this.fileService = fileService;
        this.jPlagService = jPlagService;
    }

    @PostMapping("/upload")
    public ResponseEntity<PlagiarismResponse> upload(@RequestParam("file") MultipartFile file) {

        String jobId = "job_" + System.currentTimeMillis();

        try {
            File folder = fileService.extractZip(file, jobId);

            Map<String, Object> result = jPlagService.runJPlag(folder, jobId, false);

            return ResponseEntity.ok(new PlagiarismResponse(
                    jobId,
                    (Map<String, Double>) result.get("peerMatches"),
                    (List<String>) result.get("detailedLogs"),
                    "Success"
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new PlagiarismResponse(jobId, null, List.of(e.getMessage()), "Error")
            );
        }
    }

    @GetMapping("/view-report")
    public ResponseEntity<String> viewReport(@RequestParam String jobId) {
        try {
            jPlagService.openReportViewer(jobId);
            return ResponseEntity.ok("Viewer started");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}