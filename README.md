#SNSCLONE

###개발 환경
- Lang&Framwork : spring boot 2.4.1
- DB: H2 DB
- 사용한 기능들
    - spring security(인증)
    - jwt 토큰 인증 방식 사용
    - lombok 모델 관리를 위하여 사용
    
- 프로젝트 구조
    - global(각 기능에서 공통적으로 사용될 만한 기능들을 묶어 패키지 안에 포함시켰습니다.)
        - config: Spring의 설정을 모아두었습니다.
        - exception: 예외처리 관련 클래스들을 모아두었습니다.
        - filter: Web에서 들어오는 요청을 필터링 하는 클래스가 있습니다
        - model: 공통적으로 사용되는 코드, 모델들을 모아두었습니다.
        - provider: 각종 서비스를 제공하는 클래스를 모아두었습니다.
    - 기능별 패키지
        - controller: 상호작용을 위한 api클래스를 모아두었습니다.
        - domain: db와 1:1매치되는 클래스를 모아두었습니다.
        - dto: 데이터 교환용 클래스를 모아두었습니다.
        - repository: 데이터 저장소를 모아두었습니다
        - service: 로직처리를 위한 클래스를 모아 두었습니다.
- 기타
    - 현 프로젝트의 구조는 단일형 프로젝트로 생성하였습니다.
    - 인증의 경우 추 후 MSA를 고려하여 따로 분리 되어있습니다.
    - 로컬 개발 및 테스트를 위하여 CORS 설정이 되어있습니다(WebSecurityConfig, JWTFilter)
    - port:8080
    - h2 DB SCHEMA는 {root}/ddl에 있습니다.
    

    
    
    
      
### 로컬 실행 방법(Mac or Linux)
  ````
  1. cd {PROJECT ROOT} 프로젝트 최상단 경로로 이동   
  2. ./gradlew bootRun 해당 명령어를 사용하여 스프링부트가동
  ````

### 로컬 실행 방법(Window)
  ````
  1. cd {PROJECT ROOT} 프로젝트 최상단 경로로 이동   
  2. ./gradlew.bat bootRun 해당 명령어를 사용하여 스프링부트가동
  ````



