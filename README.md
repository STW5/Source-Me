# Source-Me Backend

개발자 포트폴리오 플랫폼 Source-Me의 백엔드 API 서버입니다.

## 프로젝트 개요

Source-Me는 개발자들이 자신의 프로젝트와 블로그를 효과적으로 관리하고 공유할 수 있는 포트폴리오 플랫폼입니다.

### 주요 기능

- 사용자 인증 및 권한 관리 (JWT 기반)
- 프로필 관리
- 프로젝트 포트폴리오 관리
- 블로그 포스트 작성 및 관리
- 태그 시스템
- 미디어 파일 업로드 및 관리

## 기술 스택

### Backend
- **Framework**: Spring Boot 3.5.10
- **Language**: Java 17
- **Build Tool**: Gradle
- **Database**: PostgreSQL 16
- **ORM**: Spring Data JPA + Hibernate
- **Migration**: Flyway
- **Authentication**: Spring Security + JWT
- **Validation**: Spring Validation

### DevOps
- **Containerization**: Docker & Docker Compose
- **Reverse Proxy**: Nginx
- **Database Migration**: Flyway
- **Monitoring**: Prometheus + Grafana
- **Metrics**: Spring Boot Actuator, Node Exporter, PostgreSQL Exporter

## 아키텍처

프로젝트는 레이어드 아키텍처 패턴을 따르며, 다음과 같은 구조로 구성되어 있습니다:

```
src/main/java/com/stw/sourceme/
├── auth/               # 인증/인가
│   ├── controller/     # REST API 컨트롤러
│   ├── service/        # 비즈니스 로직
│   ├── entity/         # JPA 엔티티
│   ├── repository/     # 데이터 접근
│   ├── filter/         # JWT 필터
│   └── util/           # 유틸리티
├── blog/               # 블로그
│   ├── controller/
│   ├── service/
│   ├── entity/
│   └── repository/
├── profile/            # 사용자 프로필
│   ├── controller/
│   ├── service/
│   ├── entity/
│   │   └── vo/         # Value Objects
│   └── repository/
├── project/            # 프로젝트 관리
│   ├── controller/
│   ├── service/
│   ├── entity/
│   └── repository/
├── tag/                # 태그 시스템
│   ├── controller/
│   ├── service/
│   ├── entity/
│   └── repository/
├── media/              # 미디어 파일 관리
│   ├── controller/
│   ├── service/
│   ├── entity/
│   └── repository/
└── common/             # 공통 (Security, Exception 등)
```

### 레이어 설명

- **Controller**: REST API 엔드포인트 및 요청/응답 DTO 처리
- **Service**: 비즈니스 로직 및 트랜잭션 관리
- **Entity**: JPA 엔티티 및 도메인 모델
- **Repository**: 데이터베이스 접근 (Spring Data JPA)

## 모니터링 시스템

Source-Me는 Prometheus와 Grafana를 활용한 실시간 모니터링 시스템을 제공합니다.

### 모니터링 스택

- **Prometheus**: 메트릭 수집 및 저장
- **Grafana**: 시각화 대시보드
- **Spring Boot Actuator**: 애플리케이션 메트릭 제공
- **PostgreSQL Exporter**: 데이터베이스 메트릭 수집
- **Node Exporter**: 시스템 메트릭 수집

### 접속 정보

- **Grafana Dashboard**: `http://your-server:3001`
  - 기본 로그인: `admin / admin` (최초 접속 시 변경 권장)
- **Prometheus**: `http://your-server:9090`

### 모니터링 메트릭

"Source-Me Application Overview" 대시보드에서 다음 메트릭을 실시간으로 확인할 수 있습니다:

#### 애플리케이션 성능
- **Application Uptime**: 애플리케이션 가동 시간
- **Average Response Time**: 평균 API 응답 시간
- **HTTP Request Rate**: 초당 HTTP 요청 수
- **HTTP Response Time by Endpoint**: 엔드포인트별 응답 시간 분석

#### JVM 메트릭
- **JVM Heap Usage**: 힙 메모리 사용률 (게이지)
- **JVM Memory Usage**: 메모리 영역별 사용량 (Heap/Non-Heap)
- **JVM Threads**: 활성 스레드 및 데몬 스레드 수
- **CPU Usage**: 프로세스 CPU 사용률

#### 데이터베이스
- **Active DB Connections**: 활성 데이터베이스 커넥션 수
- **Database Connection Pool**: HikariCP 커넥션 풀 상태 (Active/Idle/Total)
- **PostgreSQL Metrics**: 쿼리 성능, 트랜잭션 통계

#### 시스템 리소스
- **Disk Usage**: 디스크 사용량
- **Network I/O**: 네트워크 트래픽
- **System Load**: 시스템 부하

### 알림 설정 (권장)

프로덕션 환경에서는 다음 임계값에 대한 알림 설정을 권장합니다:

- API 응답 시간 > 2초
- HTTP 5xx 에러율 > 1%
- DB 커넥션 풀 사용률 > 80%
- JVM Heap 메모리 > 85%
- 디스크 사용량 > 90%

## 시작하기

### 사전 요구사항

- Java 17 이상
- Docker & Docker Compose
- Gradle (선택사항, gradlew 사용 가능)

### 로컬 개발 환경 실행

#### 1. Docker Compose로 전체 스택 실행 (권장)

```bash
# 모든 서비스 시작 (PostgreSQL, Backend, Frontend, Nginx, Prometheus, Grafana)
docker-compose up -d

# 로그 확인
docker-compose logs -f backend

# 특정 서비스만 재시작
docker-compose restart backend

# 서비스 중지
docker-compose down

# 볼륨까지 삭제 (데이터 초기화)
docker-compose down -v
```

실행 중인 서비스:
- Backend: http://localhost:8081
- Frontend: http://localhost:3000
- Grafana: http://localhost:3001
- Prometheus: http://localhost:9090
- PostgreSQL: localhost:5432

#### 2. 로컬에서 백엔드만 실행

```bash
# PostgreSQL만 Docker로 실행
docker-compose up -d postgres

# 백엔드 애플리케이션 빌드 및 실행
./gradlew clean build
./gradlew bootRun
```

### 데이터베이스 마이그레이션

Flyway가 자동으로 데이터베이스 마이그레이션을 처리합니다. 마이그레이션 파일은 `src/main/resources/db/migration/` 디렉토리에 위치합니다.

## API 문서

서버 실행 후 다음 URL에서 API를 확인할 수 있습니다:

- **Base URL**: `http://localhost:8081/api/v1`
- **Health Check**: `http://localhost:8081/actuator/health` (설정된 경우)

### 주요 API 엔드포인트

- `/api/v1/auth/*` - 인증 관련 API
- `/api/v1/profiles/*` - 프로필 관리 API
- `/api/v1/projects/*` - 프로젝트 관리 API
- `/api/v1/blogs/*` - 블로그 관리 API
- `/api/v1/tags/*` - 태그 관리 API
- `/api/v1/media/*` - 미디어 파일 관리 API

## 빌드 및 테스트

```bash
# 프로젝트 빌드
./gradlew clean build

# 테스트 실행
./gradlew test

# 테스트 없이 빌드
./gradlew clean build -x test
```

## 프로덕션 배포

### Docker 이미지 빌드

```bash
# 이미지 빌드
docker build -t sourceme-backend .

# 이미지 실행
docker run -p 8081:8081 \
  -e DB_URL=jdbc:postgresql://host.docker.internal:5432/sourceme \
  -e DB_USERNAME=sourceme_user \
  -e DB_PASSWORD=sourceme_password \
  sourceme-backend
```

### Docker Compose로 전체 스택 배포

```bash
# 프로덕션 환경 변수 설정 후
docker-compose up -d

# 스케일링 (필요시)
docker-compose up -d --scale backend=3
```

## 프로젝트 구성

### 주요 디렉토리

```
Source-Me/
├── src/
│   ├── main/
│   │   ├── java/           # Java 소스 코드
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-local.yml
│   │       └── db/migration/    # Flyway 마이그레이션 스크립트
│   └── test/               # 테스트 코드
├── grafana/                # Grafana 설정
│   └── provisioning/
│       ├── datasources/    # 데이터 소스 설정 (Prometheus)
│       └── dashboards/     # 대시보드 자동 프로비저닝
├── prometheus/             # Prometheus 설정
│   └── prometheus.yml      # 메트릭 수집 타겟 설정
├── uploads/                # 업로드된 파일 저장 디렉토리
├── nginx/                  # Nginx 설정 파일
├── init-scripts/           # 데이터베이스 초기화 스크립트
├── Dockerfile              # Backend Dockerfile
├── docker-compose.yml      # Docker Compose 설정
├── build.gradle            # Gradle 빌드 설정
└── .env                    # 환경 변수 (git에서 제외)
```

## 기여하기

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 라이선스

이 프로젝트는 개인 프로젝트입니다.

## 연락처

- GitHub: [@stw](https://github.com/stw)
- Project Link: [https://github.com/stw/Source-Me](https://github.com/stw/Source-Me)

## 참고 자료

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Flyway Documentation](https://flywaydb.org/documentation/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
