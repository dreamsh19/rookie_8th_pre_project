# rookie_8th_pre_project

구멍가게 코딩단, "코드로 배우는 스프링 부트 웹 프로젝트"을 학습하기 위한 레포입니다.

- /guestbook : Part.2 단일 엔티티를 이용하는 방명록 프로젝트 (완료)
- /mreview : Part.4 다대다 관계를 이용하는 영화리뷰 프로젝트 (진행중)



#### DB 설정

1. docker 및 docker-compose 설치
2.  아래와 같이 `docker-compose.yml` 작성 

```yml
version: "3.1"
services:
    test_mysql:
        container_name: test_mariadb
        image: mariadb
        restart: always
        environment:
          MYSQL_DATABASE: bootex
          MYSQL_ROOT_PASSWORD: password
          MYSQL_ROOT_HOST: '%'
        ports:
          - 3306:3306
        volumes:
          - $HOME/temp/test.sql:/test.sql
```

3. `$ docker-compose up -d`  실행

4.  `$ docker ps` 로 `test_mariadb` 컨테이너가 실행 중인지 확인

5. MySQL Workbench와 같은 GUI 툴을 이용하여 root로 port 3306에 연결

6. ```sql
   create user 'bootuser'@'%' identified by 'bootuser1234'
   grant all privileges on bootex.* to 'bootuser'@'%'
   ```

   쿼리를 이용하여 유저 생성 및 권한 부여

   (해당 user, password는 application_properties에서 변경 가능)