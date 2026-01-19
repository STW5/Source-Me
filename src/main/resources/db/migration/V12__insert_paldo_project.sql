-- Insert project: 팔도상회
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
    '실시간 라이브커머스 플랫폼 – 팔도상회',
    'paldo-live-commerce',
    '상품 소개, 채팅, 주문, 결제, AI 피드백까지 포함한 실시간 라이브커머스 플랫폼 개발',
    '## 📋 프로젝트 상세 정보

### 기술 스택
- **Backend**: Spring Boot, Spring Cloud Gateway, Spring Security
- **Database**: PostgreSQL, Redis
- **Message Queue**: Kafka
- **Infrastructure**: Docker, AWS
- **외부 API**: KakaoPay, Google SMTP
- **Testing**: JUnit, JMeter

### 주요 기능
1. **실시간 라이브 스트리밍**: WebRTC 기반 실시간 방송
2. **채팅 시스템**: Kafka를 활용한 실시간 채팅
3. **주문/결제**: 분산 락을 통한 안전한 결제 처리
4. **AI 분석**: 사용자 행동 패턴 분석 및 추천
5. **인증/인가**: JWT 기반 인증 및 Spring Security

### 성과
- 중복 결제 완전 차단으로 **결제 정합성 100% 달성**
- 분산 아키텍처 설계로 **서비스 확장성 확보**
- 자동 재시도 로직으로 **결제 성공률 향상**',
    '2025-04-03',
    '2025-05-08',
    true,
    true,
    1,
    '백엔드 4명',
    '팀장',
    'User, Payment, AI',
    '상품 소개, 채팅, 주문, 결제, AI 피드백까지 포함한 **실시간 라이브커머스 플랫폼** 개발

실시간성, 서비스 확장성, 결제의 안정성과 정합성 확보를 고려한 아키텍처 설계',
    '- 팀장으로서 **개발 진행 상황을 관리**하고, 기술적 이슈 해결을 주도
- User, Payment, AI 분석 등 **주요 도메인의 기능 구현**
- **공통 구조**(Gateway, 인증 등) 설계를 담당',
    '### 1. 중복 결제 방지 – Redisson 분산 락 적용

**문제 인식**  
동일 orderId에 대해 사용자 중복 클릭이나 네트워크 지연이 발생할 경우, 중복 결제 요청이 동시에 들어와 외부 API가 중복 호출되고 데이터 정합성이 깨질 수 있음

**해결 방법**  
Redisson 분산 락으로 동일 주문 ID에 대한 동시 접근 차단

**효과**  
- Redisson 분산락 적용 및 JMeter를 활용한 부하 테스트로 중복 요청을 완전히 방지
- 서비스 데이터 정합성을 확보

---

### 2. 아이디 및 비밀번호 찾기 – 구글 SMTP 이메일 인증

**문제 인식**  
사용자가 아이디나 비밀번호를 잊어버린 경우 본인 인증 후 안전하게 계정 복구할 필요 존재

**해결 방법**  
- 구글 SMTP를 연동해 이메일 인증번호 발송
- Redis에 인증번호 임시 저장
- 인증번호 검증 후 아이디 확인 및 임시 비밀번호 발급 로직 구현

**효과**  
- 보안성 강화 및 사용자 편의성 향상
- 인증 과정 자동화로 계정 복구 효율성 개선

---

### 3. 결제 승인 요청 실패 대응 – RetryTemplate 적용

**문제 인식**  
외부 PG(KakaoPay) 승인 요청 시, 일시적인 네트워크 장애로 승인 실패가 발생할 가능성 존재

**해결 방법**  
Spring RetryTemplate을 적용해 승인 요청에 대한 자동 재시도 로직 구현

**효과**  
- 사용자 개입 없이 승인 안정성 확보
- 테스트 코드를 통해 재시도 로직 정상 작동 검증 완료'
);
