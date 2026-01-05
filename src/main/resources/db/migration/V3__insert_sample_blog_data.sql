-- Insert sample blog posts
INSERT INTO blog_post (title, slug, summary, content_markdown, status, published_at, created_at, updated_at)
VALUES
    ('첫 번째 블로그 글', 'first-blog-post', '블로그를 시작하며 첫 번째 글을 작성합니다.',
     '# 첫 번째 블로그 글입니다

안녕하세요! 블로그를 시작하게 되었습니다.

앞으로 개발 관련 이야기들을 공유하려고 합니다.

## 다룰 주제
- Spring Boot
- React
- 클라우드 아키텍처

잘 부탁드립니다!', 'PUBLISHED', NOW(), NOW(), NOW()),

    ('Spring Boot JPA 최적화 팁', 'spring-boot-jpa-optimization', 'JPA를 사용할 때 성능 최적화 방법을 알아봅니다.',
     '# Spring Boot JPA 최적화 팁

JPA를 사용하면서 성능 최적화를 위해 알아야 할 내용들을 정리했습니다.

## 1. N+1 문제 해결
- Fetch Join 사용
- @EntityGraph 활용
- Batch Size 설정

## 2. 읽기 전용 쿼리 최적화
- @Transactional(readOnly = true) 사용
- DTO Projection 활용

## 3. 커넥션 풀 설정
- HikariCP 설정 최적화

더 자세한 내용은 다음 글에서 다루겠습니다.', 'PUBLISHED', NOW(), NOW(), NOW()),

    ('React 상태 관리 비교', 'react-state-management', 'Redux, Zustand, Recoil 등 다양한 상태 관리 라이브러리를 비교합니다.',
     '# React 상태 관리 라이브러리 비교

React 프로젝트에서 사용할 수 있는 여러 상태 관리 라이브러리를 비교해봅니다.

## Redux
- 장점: 검증된 라이브러리, 강력한 개발 도구
- 단점: Boilerplate 코드가 많음

## Zustand
- 장점: 간단한 API, 작은 번들 사이즈
- 단점: Redux만큼 강력하지 않음

## Recoil
- 장점: React스러운 API
- 단점: 아직 실험적인 기능

프로젝트 규모와 요구사항에 맞춰 선택하는 것이 중요합니다.', 'PUBLISHED', NOW(), NOW(), NOW()),

    ('작성 중인 글', 'draft-post', '아직 작성 중인 초안입니다.',
     '# 작성 중...

이 글은 아직 작성 중입니다.', 'DRAFT', NULL, NOW(), NOW());
