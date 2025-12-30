package com.stw.sourceme.project.controller;

import com.stw.sourceme.common.ApiResponse;
import com.stw.sourceme.project.controller.dto.ProjectCreateRequest;
import com.stw.sourceme.project.controller.dto.ProjectListResponse;
import com.stw.sourceme.project.controller.dto.ProjectResponse;
import com.stw.sourceme.project.controller.dto.ProjectUpdateRequest;
import com.stw.sourceme.project.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectListResponse>>> getAllProjects() {
        List<ProjectListResponse> projects = projectService.getAllProjects();
        return ResponseEntity.ok(ApiResponse.success(projects));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectById(@PathVariable Long id) {
        ProjectResponse project = projectService.getProjectById(id);
        return ResponseEntity.ok(ApiResponse.success(project));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(
            @Valid @RequestBody ProjectCreateRequest request) {
        ProjectResponse project = projectService.createProject(request);
        return ResponseEntity.ok(ApiResponse.success(project));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectUpdateRequest request) {
        ProjectResponse project = projectService.updateProject(id, request);
        return ResponseEntity.ok(ApiResponse.success(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
