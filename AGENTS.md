# AGENTS.md - Source-Me Backend Development Guide

This file contains guidelines and commands for agentic coding agents working on the Source-Me Spring Boot backend project.

## Build & Development Commands

### Core Commands
```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun

# Clean and build
./gradlew clean build

# Run tests
./gradlew test

# Run a single test class
./gradlew test --tests "com.stw.sourceme.SourceMeApplicationTests"

# Run tests with coverage
./gradlew test jacocoTestReport

# Check for dependency updates
./gradlew dependencyUpdates
```

### Database Commands
```bash
# Run Flyway migrations
./gradlew flywayMigrate

# Validate Flyway migrations
./gradlew flywayValidate

# Show migration info
./gradlew flywayInfo
```

## Project Architecture

### Package Structure
```
com.stw.sourceme/
├── auth/           # Authentication & JWT
├── blog/           # Blog posts & media
├── common/         # Shared components
├── media/          # File upload system
├── profile/        # Site profile
├── project/        # Portfolio projects
└── tag/            # Tag management
```

### Layer Architecture
- **Controller**: REST API endpoints with `@RequestMapping("/api/v1/*")`
- **Service**: Business logic with `@Transactional`
- **Repository**: JPA data access
- **Entity**: JPA entities with Lombok
- **DTO**: Request/Response objects

## Code Style Guidelines

### Import Organization
```java
// 1. Java standard libraries
import java.time.LocalDateTime;
import java.util.List;

// 2. Third-party libraries (Spring, Jakarta, Lombok)
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 3. Project imports (com.stw.sourceme)
import com.stw.sourceme.common.BaseEntity;
import com.stw.sourceme.auth.entity.User;
```

### Naming Conventions
- **Classes**: PascalCase (e.g., `BlogPostService`, `MediaFileController`)
- **Methods**: camelCase (e.g., `createBlogPost`, `uploadFile`)
- **Variables**: camelCase (e.g., `blogPostId`, `mediaFile`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_FILE_SIZE`)
- **Packages**: lowercase with dots (e.g., `com.stw.sourceme.blog.service`)

### Entity Guidelines
```java
@Entity
@Table(name = "blog_post")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BlogPost extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    
    // Use @PrePersist for custom initialization
    @PrePersist
    protected void onCreate() {
        // Custom logic
    }
}
```

### Service Guidelines
```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogPostService {
    
    private final BlogPostRepository blogPostRepository;
    
    @Transactional
    public BlogPost createBlogPost(BlogPostCreateRequest request) {
        // Business logic here
    }
    
    // Use @Transactional(readOnly = true) for query methods
    public List<BlogPost> getPublishedPosts() {
        return blogPostRepository.findByStatusOrderByPublishedAtDesc("PUBLISHED");
    }
}
```

### Controller Guidelines
```java
@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
public class BlogPostController {
    
    private final BlogPostService blogPostService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<BlogPostResponse>> createBlogPost(
            @Valid @RequestBody BlogPostCreateRequest request) {
        BlogPostResponse response = blogPostService.createBlogPost(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BlogPostResponse>> getBlogPost(@PathVariable Long id) {
        BlogPostResponse response = blogPostService.getBlogPost(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
```

### DTO Guidelines
```java
// Request DTOs
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostCreateRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must be less than 200 characters")
    private String title;
    
    @NotBlank(message = "Content is required")
    private String contentMarkdown;
}

// Response DTOs
@Getter
@Builder
public class BlogPostResponse {
    private Long id;
    private String title;
    private String summary;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

## Error Handling

### Custom Exceptions
```java
// Use BusinessException for business logic errors
throw new BusinessException("Blog post not found");

// Use ResourceNotFoundException for missing resources
throw new ResourceNotFoundException("BlogPost", id);
```

### Validation
- Use `@Valid` on request DTOs in controllers
- Add validation annotations to DTO fields
- Custom validation messages for user-friendly errors

## Database Guidelines

### Flyway Migrations
- Prefix with `V` (e.g., `V1__init_schema.sql`)
- Use descriptive names with double underscore separator
- Add comments for tables and columns
- Use `BIGSERIAL` for primary keys
- Add proper foreign key constraints

### JPA Best Practices
- Use `@GeneratedValue(strategy = GenerationType.IDENTITY)` for PostgreSQL
- Use `fetch = FetchType.LAZY` for relationships
- Add proper indexes for query performance
- Use `@Column` annotations with explicit names and constraints

## Security Guidelines

### JWT Implementation
- Use `io.jsonwebtoken` library (version 0.12.3)
- Store JWT secrets in environment variables
- Implement proper token validation
- Use BCrypt for password hashing

### File Upload Security
- Validate file types and sizes
- Use UUID for file names to prevent path traversal
- Store files outside web root
- Implement proper access controls

## Testing Guidelines

### Test Structure
```java
@ExtendWith(MockitoExtension.class)
class BlogPostServiceTest {
    
    @Mock
    private BlogPostRepository blogPostRepository;
    
    @InjectMocks
    private BlogPostService blogPostService;
    
    @Test
    @DisplayName("Should create blog post successfully")
    void createBlogPost_Success() {
        // Test implementation
    }
}
```

### Test Coverage
- Aim for 80%+ coverage on service layer
- Test business logic thoroughly
- Mock external dependencies
- Use `@DisplayName` for descriptive test names

## Configuration

### Environment Variables
- `DB_URL`: PostgreSQL connection URL
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password
- `JWT_SECRET`: JWT signing secret
- `UPLOAD_DIR`: File upload directory
- `MAX_FILE_SIZE`: Maximum file size in bytes

### Profiles
- `local`: Local development
- `dev`: Development environment
- `prod`: Production environment

## API Standards

### Response Format
All APIs return `ApiResponse<T>` with consistent structure:
```json
{
  "success": true,
  "data": { ... },
  "message": "Optional success message",
  "errorCode": "Error code for failures"
}
```

### HTTP Status Codes
- `200 OK`: Successful operations
- `201 Created`: Resource created
- `400 Bad Request`: Validation errors
- `401 Unauthorized`: Authentication required
- `403 Forbidden`: Insufficient permissions
- `404 Not Found`: Resource not found
- `500 Internal Server Error`: Server errors

### URL Patterns
- Use `/api/v1/` prefix for all APIs
- Use plural nouns for collections (e.g., `/blog-posts`)
- Use resource IDs in path (e.g., `/blog-posts/{id}`)
- Use query parameters for filtering and pagination

## Performance Guidelines

### Database Optimization
- Use proper indexes for frequent queries
- Avoid N+1 query problems with `@EntityGraph`
- Use pagination for large result sets
- Consider caching for frequently accessed data

### File Handling
- Implement streaming for large file uploads
- Use appropriate buffer sizes
- Consider CDN for static files in production
- Implement file cleanup for unused uploads

## Development Workflow

1. **Feature Development**: Create feature branch from `main`
2. **Database Changes**: Create Flyway migration before code changes
3. **Testing**: Write tests for new functionality
4. **Code Review**: Ensure adherence to guidelines
5. **Integration**: Test with frontend if applicable
6. **Deployment**: Use proper environment configurations

## Common Patterns

### Repository Queries
```java
// Use method names for simple queries
List<BlogPost> findByStatusOrderByPublishedAtDesc(String status);

// Use @Query for complex queries
@Query("SELECT b FROM BlogPost b WHERE b.status = :status AND b.title LIKE %:keyword%")
List<BlogPost> findByStatusAndTitleContaining(@Param("status") String status, @Param("keyword") String keyword);
```

### Service Transaction Management
```java
// Use @Transactional for write operations
@Transactional
public BlogPost updateBlogPost(Long id, BlogPostUpdateRequest request) {
    BlogPost blogPost = blogPostRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("BlogPost", id));
    blogPost.update(request.getTitle(), request.getContent());
    return blogPostRepository.save(blogPost);
}

// Use @Transactional(readOnly = true) for read operations
@Transactional(readOnly = true)
public List<BlogPost> getPublishedPosts() {
    return blogPostRepository.findByStatusOrderByPublishedAtDesc("PUBLISHED");
}
```

Remember to always follow these guidelines when working on the Source-Me backend project!