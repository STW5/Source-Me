-- Insert project: Source-Me
INSERT INTO project (
    title,
    slug,
    summary,
    content_markdown,
    started_at,
    ended_at,
    is_published,
    is_featured,
    featured_order,
    team_size,
    project_role,
    owned_services,
    introduction_markdown,
    responsibilities_markdown,
    troubleshooting_markdown
)
VALUES (
    '개발자 포트폴리오 플랫폼 – Source-Me',
    'source-me-portfolio',
    '프로젝트, 블로그, 프로필 관리 기능을 제공하는 풀스택 포트폴리오 플랫폼',
    '## 📋 프로젝트 상세 정보

### 기술 스택
- **Backend**: Spring Boot 3.5, Spring Security, Spring Data JPA, Flyway
- **Frontend**: Next.js 15 (App Router), React 19, TypeScript, Tailwind CSS
- **Database**: PostgreSQL 16
- **Infrastructure**: Docker, Docker Compose, Nginx, Let''s Encrypt (SSL/TLS)
- **CI/CD**: GitHub Actions (빌드, 테스트, 자동 배포)
- **Cloud**: Linux Server, DuckDNS (DDNS + HTTPS)
- **Architecture**: DDD 4계층 아키텍처 (Presentation, Application, Domain, Infrastructure)

### 아키텍처 설계

#### DDD 4계층 구조
1. **Presentation Layer**: REST API 컨트롤러, Request/Response DTO
2. **Application Layer**: 비즈니스 로직 조율, 트랜잭션 관리
3. **Domain Layer**: 핵심 비즈니스 로직, 엔티티, 도메인 서비스
4. **Infrastructure Layer**: 데이터베이스 접근(Repository), 외부 시스템 연동

각 도메인(Project, Blog, Profile, Auth)을 독립적인 모듈로 분리하여 **관심사 분리** 및 **유지보수성 향상**

### 주요 기능
1. **프로젝트 관리**: CRUD, 상세 정보, 마크다운 지원, 태그 관리, 미디어 첨부
2. **블로그 시스템**: 포스트 작성/수정/삭제, 조회수 추적, 태그 기반 필터링
3. **프로필 관리**: 개발자 소개, 기술 스택, 경력 정보, About 섹션
4. **미디어 관리**: 이미지 업로드, 파일 서빙, 프로필/프로젝트/블로그 연동
5. **인증/인가**: JWT 기반 인증, Spring Security, BCrypt 비밀번호 암호화

### 인프라 및 배포

#### Docker 컨테이너 구성
- **Backend**: Spring Boot 애플리케이션 (포트 8081)
- **Frontend**: Next.js 애플리케이션 (포트 3000)
- **Database**: PostgreSQL 16 (포트 5432)
- **Nginx**: 리버스 프록시 및 SSL 종료 (포트 80/443)

#### HTTPS/SSL 설정
- **인증서 발급**: Let''s Encrypt (certbot)
- **자동 갱신**: 90일 주기 자동 갱신 설정
- **보안 강화**: TLS 1.2+, HSTS, 안전한 암호화 스위트
- **DDNS**: DuckDNS로 동적 IP 관리

#### CI/CD 파이프라인
1. **CI (Continuous Integration)**:
   - GitHub Actions 트리거 (main 브랜치 push)
   - JDK 17 설정 및 Gradle 빌드
   - 빌드 아티팩트 생성

2. **CD (Continuous Deployment)**:
   - SSH를 통한 서버 접속
   - Git pull로 최신 코드 가져오기
   - Docker Compose로 컨테이너 재빌드 및 재시작
   - 무중단 배포 (Docker 헬스체크)

#### 데이터베이스 관리
- **Flyway 마이그레이션**: 버전 기반 스키마 관리 (V1~V13)
- **자동 검증**: 애플리케이션 시작 시 마이그레이션 체크섬 검증
- **롤백 지원**: 스키마 히스토리 관리로 안전한 배포

### 성과
- DDD 4계층 아키텍처로 **도메인 중심 설계** 및 **확장성 확보**
- RESTful API 설계로 **프론트엔드와 백엔드 완전 분리**
- Docker 기반 인프라로 **배포 자동화 및 환경 일관성 확보**
- Flyway로 **데이터베이스 버전 관리 체계화**
- GitHub Actions로 **CI/CD 파이프라인 구축**하여 배포 자동화
- Let''s Encrypt HTTPS 적용으로 **보안성 강화 및 신뢰성 향상**
- Nginx 리버스 프록시로 **라우팅 최적화 및 보안 계층 추가**',
    '2025-01-07',
    NULL,
    true,
    true,
    2,
    '1명 (개인 프로젝트)',
    '풀스택 개발자',
    'Backend, Frontend, Infrastructure',
    '개발자를 위한 **포트폴리오 및 블로그 플랫폼** 개발

프로젝트 관리, 블로그 작성, 프로필 관리 기능을 제공하며,
Spring Boot와 Next.js 기반의 **현대적인 풀스택 아키텍처** 구현',
    '- **아키텍처 설계**: DDD 4계층 구조로 도메인 중심 설계
  - Presentation: REST API 엔드포인트 설계 (프로젝트, 블로그, 프로필, 인증)
  - Application: 비즈니스 로직 조율 및 트랜잭션 관리
  - Domain: 핵심 엔티티 및 비즈니스 규칙 구현
  - Infrastructure: JPA Repository, 파일 시스템 연동

- **백엔드 개발**: Spring Boot로 RESTful API 설계 및 구현
  - Spring Security + JWT 기반 인증/인가
  - Flyway를 통한 데이터베이스 마이그레이션 관리
  - 파일 업로드 및 스트림 기반 다운로드

- **프론트엔드 개발**: Next.js로 반응형 UI/UX 구현
  - App Router 활용한 페이지 라우팅
  - React 19 Server Components 활용
  - react-markdown으로 마크다운 렌더링

- **데이터베이스 설계**: PostgreSQL 스키마 설계 및 정규화
  - 13개 버전의 Flyway 마이그레이션 작성
  - 복합키, 외래키 제약조건 설계
  - 인덱스 최적화

- **인프라 구축**: Docker 기반 컨테이너 오케스트레이션
  - Docker Compose로 4개 서비스 관리 (backend, frontend, database, nginx)
  - Nginx 리버스 프록시 설정 (API /api/v1, 프론트엔드 /)
  - Let''s Encrypt SSL/TLS 인증서 자동 발급 및 갱신
  - DuckDNS DDNS 설정

- **CI/CD 구축**: GitHub Actions 파이프라인 설계 및 구현
  - CI: Gradle 빌드, 의존성 캐싱
  - CD: SSH 기반 자동 배포, Docker 이미지 재빌드
  - 배포 자동화로 개발 생산성 향상',
    '### 1. DDD 4계층 아키텍처 도입 – 도메인 중심 설계

**문제 인식**
초기 프로젝트 구조가 단순 Controller-Service-Repository 3계층으로 설계되어,
비즈니스 로직이 Service 계층에 집중되고 도메인 로직과 인프라 로직이 혼재됨.
프로젝트 규모가 커질수록 유지보수성과 테스트 용이성이 저하될 우려

**해결 방법**
DDD(Domain-Driven Design) 4계층 아키텍처 적용

**계층 구조:**
```
src/main/java/com/stw/sourceme/
├── project/
│   ├── presentation/     # REST API 컨트롤러, DTO
│   ├── application/      # 서비스, 비즈니스 로직 조율
│   ├── domain/          # 엔티티, 도메인 로직
│   └── infrastructure/   # Repository, 외부 시스템
├── blog/
├── profile/
└── auth/
```

**설계 원칙:**
- **Presentation**: HTTP 요청/응답 처리, DTO 변환
- **Application**: 트랜잭션 관리, 여러 도메인 서비스 조율
- **Domain**: 핵심 비즈니스 규칙, 엔티티 생명주기 관리
- **Infrastructure**: JPA Repository, 파일 I/O, 외부 API

**효과**
- 각 계층의 책임이 명확히 분리되어 **관심사 분리(SoC)** 달성
- 도메인 로직을 독립적으로 테스트 가능
- 새로운 도메인 추가 시 기존 코드 영향 최소화
- 인프라 변경(JPA → MyBatis 등) 시 도메인 로직 보호

---

### 2. GitHub Actions CI/CD 파이프라인 구축

**문제 인식**
수동 배포 시 다음과 같은 문제 발생:
- 서버 SSH 접속 → git pull → Docker 재빌드 과정 반복
- 사람 실수로 인한 배포 누락 또는 롤백 어려움
- 배포 소요 시간 증가 및 개발 집중도 저하

**해결 방법**
GitHub Actions를 활용한 자동화 파이프라인 구축

**CI 파이프라인** (.github/workflows/ci.yml):
```yaml
on: push (main 브랜치)
steps:
  1. Checkout 코드
  2. JDK 17 설정
  3. Gradle 빌드 (테스트 제외)
  4. 빌드 아티팩트 검증
```

**CD 파이프라인** (.github/workflows/deploy.yml):
```yaml
on: push (main 브랜치)
steps:
  1. SSH 서버 접속 (appleboy/ssh-action)
  2. Git pull (최신 코드)
  3. Docker Compose 재빌드 (--build)
  4. 컨테이너 헬스체크
```

**배포 프로세스:**
1. 개발자가 main 브랜치에 push
2. GitHub Actions가 자동으로 빌드 실행
3. 빌드 성공 시 서버에 SSH 접속
4. 서버에서 Docker 이미지 재빌드 및 재시작
5. 무중단 배포 (Docker 헬스체크로 이전 컨테이너 유지)

**효과**
- 코드 푸시 후 **3~5분 내 자동 배포** 완료
- 수동 작업 제거로 **배포 실수 방지**
- 빌드 실패 시 즉시 알림으로 **빠른 피드백**
- 배포 히스토리 자동 기록으로 **추적 가능성 확보**
- CI에서 테스트 자동 실행으로 **코드 품질 유지**

---

### 3. Let''s Encrypt HTTPS 자동 인증서 관리

**문제 인식**
프로덕션 환경에서 HTTP 통신 시 보안 취약점 존재.
브라우저에서 "안전하지 않음" 경고 표시로 사용자 신뢰도 하락.
유료 SSL 인증서 구매는 비용 부담

**해결 방법**
Let''s Encrypt 무료 SSL 인증서 + certbot 자동 갱신 설정

**구현 과정:**
1. **DuckDNS DDNS 설정**: 동적 IP를 도메인(sourceme.duckdns.org)에 매핑
2. **certbot 인증서 발급**:
```bash
certbot certonly --standalone -d sourceme.duckdns.org
```
3. **Nginx SSL 설정**:
```nginx
server {
    listen 443 ssl http2;
    ssl_certificate /etc/letsencrypt/live/sourceme.duckdns.org/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/sourceme.duckdns.org/privkey.pem;

    # 보안 강화
    ssl_protocols TLSv1.2 TLSv1.3;
    add_header Strict-Transport-Security "max-age=31536000" always;
}
```
4. **자동 갱신 Cron**: 90일 만료 전 자동 갱신

**Docker Compose 통합:**
```yaml
nginx:
  volumes:
    - /etc/letsencrypt:/etc/letsencrypt:ro
  ports:
    - "443:443"
```

**효과**
- **무료 SSL 인증서**로 비용 절감
- HTTPS 적용으로 **데이터 암호화** 및 **중간자 공격 방지**
- 브라우저 보안 경고 제거로 **사용자 신뢰도 향상**
- 자동 갱신으로 **인증서 만료 걱정 없음**
- HSTS 헤더로 **강제 HTTPS 리다이렉트**'
);
