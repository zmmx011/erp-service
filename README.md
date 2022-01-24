## erp-service
영림원ERP RESTful API Service

해당 서비스는 ERP의 미들웨어 목적으로 개발되었습니다.

### Build
`./gradlew jibDockerBuild` 명령으로 Gradle 빌드 및 Docker 이미지 생성을 진행합니다.
### Start
`docker-compose up -d` 명령으로 생성된 이미지를 실행합니다.

## Database
Mariadb 100.100.10.177:3306/invenia_erp

## Kafka Listener
이 서비스는 Kafka Listener 역할을 수행합니다.

Kafka 주소 : http://100.100.10.174:9092

### User Consumer
erp_TCAUser Topic을 구독하여 인사 정보를 실시간으로 Keycloak 인증서버에 전송합니다.

또한 수신한 인사 정보를 MSA DB에 저장합니다.

erp_TCAUser Topic은 Confluent Connect를 이용하여 자동 생성하며 Connect 생성은 아래와 같습니다.

```http
POST /connectors HTTP/1.1
Host: 100.100.10.174:8083
Content-Type: application/json
Content-Length: 583

{
    "name": "erp-user-connect",
    "config": {
        "connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector",
        "connection.url":"jdbc:sqlserver://100.100.10.192:14233;database=INVENIA",
        "connection.user":"genuine",
        "connection.password":"Invenia209#",
        "mode": "timestamp+incrementing",
        "incrementing.column.name": "UserSeq",
        "timestamp.column.name" : "LastDateTime",
        "validate.non.null": false,
        "table.whitelist":"_TCAUser",
        "topic.prefix" : "erp",
        "tasks.max": "1"
    }
}
```

### Vacation Consumer
erp_TPRWkEmpVacApp Topic을 구독하여 휴가 정보를 실시간으로 Mattermost 메신저 서버에 전송합니다.

erp_TPRWkEmpVacApp Topic은 Confluent Connect를 이용하여 자동 생성하며 Connect 생성은 아래와 같습니다.

```http
POST /connectors HTTP/1.1
Host: 100.100.10.174:8083
Content-Type: application/json
Content-Length: 577

{
    "name": "erp-vacation",
    "config": {
        "connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector",
        "connection.url":"jdbc:sqlserver://100.100.10.192:14233;database=INVENIA",
        "connection.user":"genuine",
        "connection.password":"Invenia209#",
        "mode": "incrementing",
        "incrementing.column.name": "VacSeq",
        "validate.non.null": false,
        "table.whitelist":"_TPRWkEmpVacApp",
        "topic.prefix" : "erp",
        "tasks.max": "1",
        "numeric.mapping": "best_fit_eager_double"
    }
}
```

