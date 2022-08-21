# Introduction

스프링 부트 VSCODE 테스트 용 프로젝트입니다.

## QueryDSL 설정하기

QueryDSL를 사용하려면 Q 클래스를 만들어야 하는데 자동으로 생성이 되지 않을 수 있으니 아래 명령어를 통해 직접 생성해줘야 합니다.

```
./gradlew class
```

build/generated/querydsl 폴더에 Q 클래스들이 있으면 OK 입니다.
