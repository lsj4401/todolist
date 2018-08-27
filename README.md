# todolist
build : gradle 4.8

framework : Spring boot 2.0.4.RELEASE

unit-test : spock 1.1

DB : h2

view : angularjs 1.7, freemarker, jquery

문제해겨 전략
TDD 기반으로 요구사항의 기능을 구현했습니다.
할일 목록 완료시 일감의 참조 사항을 확인하기 위해 나에게 참조가 걸린 모든 할일 목록을 무식하게 찾아가는 방법을 사용했습니다.

# build
```
$ ./gradlew clean build
```

# start 
```
$ java -jar ./build/libs/todolist-0.0.1-SNAPSHOT.jar
```

# use
```
http://localhost:8080/view
```

