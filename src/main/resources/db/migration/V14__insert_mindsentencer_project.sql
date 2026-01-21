-- Insert project: MindSentencer
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
    'KoGPT2 파인튜닝 기반 문장 생성기 – MindSentencer',
    'mindsentencer-kogpt2',
    '뇌파 기반 입력 장치(BCI) 사용자를 위한 KoGPT2 AI 의사소통 보조 시스템',
    '## 📋 프로젝트 상세 정보

### 기술 스택
- **AI/ML**: KoGPT2 (Hugging Face Transformers), PyTorch
- **Data Processing**: Pandas, NumPy
- **Backend**: Python
- **Dataset**: AI-Hub 일상대화 데이터 (약 13만 건)
- **Tools**: Jupyter Notebook, Google Colab

### 주요 기능
1. **키워드 기반 문장 생성**: 사용자가 입력한 키워드를 기반으로 자연스러운 문장 자동 생성
2. **카테고리 분류**: 5개 주제(일상, 질문, 감정, 요청, 인사)로 문맥 인식
3. **실시간 예측**: 파인튜닝된 KoGPT2 모델로 빠른 응답 생성
4. **텍스트 전처리**: Pandas 기반 데이터 정제 및 최적화

### 데이터셋 구축
- **AI-Hub 일상대화 데이터** 약 13만 건 수집
- 5개 카테고리로 분류 (일상대화, 질문, 감정표현, 요청, 인사)
- 불필요한 특수문자, 중복 데이터 제거
- Tokenizer 호환 형식으로 변환

### 모델 파인튜닝
- **베이스 모델**: SKT-AI KoGPT2 (한국어 GPT-2)
- **학습 데이터**: 전처리된 13만 건 일상대화
- **학습 환경**: Google Colab GPU
- **최적화**: 배치 크기, 학습률 튜닝으로 과적합 방지

### 성과
- Pandas 최적화로 **문장 생성 속도 67% 개선**
- KoGPT2 파인튜닝으로 **자연스러운 한국어 문장 생성**
- BCI 사용자의 **입력 효율성 대폭 향상**
- 키워드만으로 **완전한 문장 자동 완성**',
    '2024-04-01',
    '2024-06-30',
    true,
    true,
    3,
    '2명',
    '팀원 (AI 개발)',
    'KoGPT2 문장 생성',
    '뇌파 기반 입력 장치(BCI) 사용자를 위한 **AI 의사소통 보조 시스템** 개발

KoGPT2 모델을 직접 파인튜닝하여 키워드와 카테고리 기반으로 자연스러운 문장을 생성하는
**AI 문장 생성기(MindSentencer)** 구축',
    '- **데이터 수집 및 전처리**: AI-Hub 일상대화 데이터 약 13만 건 수집 및 정제
  - Pandas를 활용한 텍스트 정제 (특수문자, 중복 제거)
  - 5개 카테고리(일상, 질문, 감정, 요청, 인사)로 데이터 분류
  - Tokenizer 호환 형식으로 데이터 변환

- **KoGPT2 모델 파인튜닝**:
  - Hugging Face Transformers 라이브러리 활용
  - 전처리된 13만 건 데이터로 모델 학습
  - 하이퍼파라미터 튜닝 (배치 크기, 학습률, epoch)
  - 과적합 방지를 위한 검증 데이터셋 분리

- **예측 파이프라인 구현**:
  - Tokenizer 로딩 및 입력 텍스트 인코딩
  - 키워드 + 카테고리 조합으로 문맥 생성
  - 모델 추론 및 디코딩
  - 후처리 (문장 완성도 검증, 불필요한 토큰 제거)

- **성능 최적화**:
  - Pandas DataFrame 연산 최적화
  - 배치 처리로 다중 문장 생성 속도 개선
  - 메모리 사용량 최적화',
    '### 1. Pandas 기반 텍스트 정제 최적화 – 67% 속도 개선

**문제 인식**
초기 데이터 전처리 시 13만 건 데이터를 순회하며 정규표현식으로 하나씩 처리.
처리 시간이 약 15분 소요되어 실험 반복 시 병목 발생

**해결 방법**
Pandas 벡터화 연산 활용

**최적화 전**:
```python
# 루프 기반 처리 (느림)
for i in range(len(df)):
    df.loc[i, ''text''] = re.sub(pattern, '''', df.loc[i, ''text''])
```

**최적화 후**:
```python
# 벡터화 연산 (빠름)
df[''text''] = df[''text''].str.replace(pattern, '''', regex=True)
df[''text''] = df[''text''].str.strip()
df = df[df[''text''].str.len() > 5]  # 짧은 문장 필터링
```

**추가 최적화**:
- `apply()` 대신 `.str` 메서드 사용
- `loc[]` 대신 컬럼 단위 연산
- 불필요한 중간 변수 제거
- 메모리 효율적인 데이터 타입 변환

**효과**
- 처리 시간 **15분 → 5분 (67% 개선)**
- 메모리 사용량 **30% 감소**
- 실험 반복 주기 단축으로 **개발 생산성 향상**

---

### 2. KoGPT2 모델 과적합 방지 – 검증 데이터셋 분리

**문제 인식**
초기 학습 시 전체 데이터로 학습하여 Training Loss는 감소하지만,
실제 새로운 키워드 입력 시 부자연스럽거나 학습 데이터를 그대로 출력하는 현상 발생

**해결 방법**
Train/Validation 데이터셋 분리 및 Early Stopping 적용

**구현**:
```python
from sklearn.model_selection import train_test_split

# 8:2 비율로 데이터 분리
train_data, val_data = train_test_split(dataset, test_size=0.2, random_state=42)

# Validation Loss 모니터링
training_args = TrainingArguments(
    evaluation_strategy="epoch",
    save_strategy="epoch",
    load_best_model_at_end=True,
    metric_for_best_model="eval_loss"
)
```

**효과**
- 과적합 방지로 **일반화 성능 향상**
- Validation Loss 기준 **최적 모델 자동 선택**
- 새로운 키워드에 대한 **자연스러운 문장 생성**

---

### 3. 카테고리 기반 문맥 생성 – 생성 품질 향상

**문제 인식**
단순 키워드만 입력 시 문맥 파악이 어려워 부적절한 문장 생성.
예: "병원" 입력 → "병원에 갔다 왔어" vs "병원 어디야?" 구분 불가

**해결 방법**
5개 카테고리 분류로 문맥 힌트 제공

**카테고리 설계**:
1. **일상대화**: "오늘 ~했어", "~에 갔다 왔어"
2. **질문**: "~어디야?", "~언제?"
3. **감정표현**: "기쁘다", "속상하다"
4. **요청**: "~해줘", "~주세요"
5. **인사**: "안녕", "잘 지내?"

**프롬프트 구성**:
```python
prompt = f"[{category}] {keyword}"
# 예: "[질문] 병원" → "병원 어디야?"
# 예: "[일상대화] 병원" → "병원에 다녀왔어"
```

**효과**
- 동일 키워드에 대한 **문맥 인식 문장 생성**
- 사용자 의도에 맞는 **정확한 표현**
- BCI 사용자의 **의사소통 효율성 극대화**'
);
