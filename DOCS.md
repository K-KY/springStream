# API/Controller

## VideoController
| API         | 메소드 | 인수 | 설명 |
|-------------|-----|----|----|
| videos      |GET| String|인수로 받은 경로의 hls 탐색
| videos/file |GET|String|인수로 받은 경로의 파일 탐색
| videos/dir  |GET|String|인수로 받은 경로의 디렉토리 탐색

### Method
| 메서드     | 인수 | 설명 |
|---------|--|-|
| getBody |List<String>|ResponseEntity를 반환하는 편의상 만들어놓은 메서드

# config
## FileManagerConfig
- 최대한 스프링 의존성을 빼고 싶었으나 내부 경로를 환경변수로 사용하는데 복잡도가 증가해서 빈으로 등록

|빈| 반환          | 설명                        |
|-|-------------|---------------------------|
|fileManager| FileManager | 지정된 경로의 파일/디렉토리를 탐색하는 클래스 |


## WebConfig


|빈| 반환          | 설명                               |
|-|-------------|----------------------------------|
|corsConfigurer| WebMvcConfigurer | cors 설정 클래스, 시큐리티 도입시 유지하거나 제거예정 |


# Container
- 도커 컨테이너 기반 으로 동작함
```shell
docker build --platform linux/amd64 -t rlarbdud/spring-stream:tag .
```
```shell
 docker run -d -p 8080:8080 -e BASE_STORE=/shared \
  -v /loc:/shared \
   --name spring-stream3 image-name
```

- 볼롬 마운트 경로는 바뀌어도 상관 없음
  - 단 컨테이너 내부 경로는 환경변수도 같이 바꿔야함

