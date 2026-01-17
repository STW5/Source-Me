-- Insert sample about sections data to profile table

UPDATE profile
SET
    internships = '[
        {
            "title": "미래내일 일경험(인턴형) – (이지지오 2기) 빛솔루션 인턴십",
            "period": "2025.06.16 – 2025.08.08",
            "description": "지자체(구청) 협업 사업: 옥외간판 데이터 정비 및 양성화/비양성화 분류 지원\n• 메타데이터 매핑: 간판 가로·세로·높이, 주소/점포명을 이미지와 1:1 매칭\n• 품질 검수: 이미지–데이터 불일치(사이즈·주소·점포명) 식별 및 수정 처리"
        }
    ]'::jsonb,

    education = '[
        {
            "institution": "SPARTA 단기 심화 Java 부트캠프",
            "period": "2025.02.03 ~ 2025.05.09 (14주 과정)",
            "activities": [
                "KDT 국비지원 실무 중심 백엔드 양성과정 수료",
                "Java, Spring Boot 기반 MSA 설계",
                "트래픽/장애 대응 중심의 실전 프로젝트 수행",
                "Prometheus 기반 모니터링 도입 및 운영 경험"
            ]
        },
        {
            "institution": "동서대학교",
            "period": "2019.03 ~ 2026.02",
            "major": "소프트웨어학과",
            "minor": "AI 공학",
            "gpa": "3.79 / 4.5",
            "majorGpa": "4.01 / 4.5",
            "activities": [
                "2019년 선후배사랑 학습 공동체 참여",
                "2019년 산학멘토링 프로그램 참여",
                "2023년 룩살(LuxSal) 학습 공동체 활동",
                "2024년 LG Aimers 4기 수료 및 해커톤 참여",
                "2024년 SW중심대학 디지털 경진대회 참가"
            ]
        },
        {
            "institution": "성도고등학교",
            "period": "2016.03 ~ 2019.02",
            "major": "이공계열",
            "activities": [
                "코딩 동아리 활동",
                "C 언어 기본 문법 및 구조 학습을 통해 프로그래밍 기초 습득",
                "간단한 실습을 중심으로 기본 개념 이해에 집중"
            ]
        }
    ]'::jsonb,

    work_history = '[
        {
            "organization": "SAL 연구회",
            "period": "2023.02 ~ 현재",
            "role": "학부 연구생",
            "projects": [
                "연구회 웹사이트 기획 및 개발로 선후배 간 소통 창구 마련",
                "현직 개발자 이사의 주기적 피드백을 통해 개발 완성도 향상"
            ],
            "activities": [
                "정기 스터디 활동",
                "Java 기초부터 AI 최신 기술까지 폭넓은 주제 학습 및 실습 수행",
                "주기적 코드 리뷰와 상호 피드백을 통한 역량 강화"
            ]
        }
    ]'::jsonb,

    publications_patents = '[
        {
            "type": "PUBLICATION",
            "title": "사용자 의도 인식을 위한 KoGPT2 기반 SSVEP 활용 시스템",
            "details": "한국전자통신학회 논문지, 2024 Vol.19, No.06",
            "date": "2024",
            "description": "• KoGPT2 기반 문장 생성 모델 구축 및 전체 파이프라인(입력→전처리→예측→출력) 구현\n• 생성 문장의 자연스러움 및 맥락 안정성 실험적 검증"
        },
        {
            "type": "PATENT",
            "title": "LLM 기반 SSVEP를 활용한 기능적 심상 문장 생성기",
            "details": "출원번호: 10-2024-0139080",
            "date": "2024.10.14",
            "description": "• SSVEP 입력과 키워드를 결합한 LLM 응용 문장 생성 구조 설계\n• 입력→예측→출력 파이프라인을 모듈화 및 재사용 가능한 형태로 구성"
        }
    ]'::jsonb,

    certificates = '[
        {
            "name": "정보처리기사",
            "issuer": "한국산업인력공단",
            "date": "2024"
        },
        {
            "name": "SQLD",
            "issuer": "한국데이터산업진흥원",
            "date": "2024"
        }
    ]'::jsonb
WHERE id = 1;
