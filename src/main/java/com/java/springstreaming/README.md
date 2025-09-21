# 동영상 스트리밍 서비스

## todo
- 영상을 HLS로 변환 -> 후순위

- HLS 서빙 -> nginx ****
  - nginx로 hls를 서빙 하려는 이유
    - 정적 파일 서빙은 nginx가 mvc나 webflux보다 효율이 좋음
- 시청 기록 저장 
  - nginx로 서빙하는경우 클라이언트에서 별도의 요청을 받아야함


### 백엔드의 역할
- hls를 단순히 서빙만 하는거라면 백엔드 불필요
  - Nginx로 다 해결 가능

### 백엔드가 제공 할 것
- 요청한 영상의 hls가 없는경우 hls 생성
  - ffmpeg 호출 로직 필요
- 시청 기록
- 영상 목록
- 로그인
- 허용된 사용자 목록
  - 우선 백엔드 거치고 인증 토큰 발급
  - 인증 토큰으로 Nginx 검증?
    - 이게 되는지 안되는지 모름
#### DB
- 영상의 경로를 UUID로 변경 
  - DB에 UUID = 영상 제목으로 매핑
    - 정적 파일이라서 경로만 맞으면 그냥 열려있음 허용된 사용자만 조회 가능하도록
      - Nginx 설정으로도 가능할텐데 조심해서 나쁠거 없음
      - 

### 영상 출력 (프론트)
- video.js랑 hls.js가 있음
- 둘 다 써봤는데 hls.js가 깔끔한 편, 기본적으로 배속도 제공


```
docker run -d \
  --name hls-player \
  -p 8080:80 \
  -v /mnt/data/:/usr/share/nginx/html:ro \
  nginx:latest
```
- 호스트 컴퓨터 경로와 컨테이너 내부를 볼륨으로 연결 : 리드 온리
  - 해당 경로에 html을 작성 해놨기 때문에 /파일이름으로 접속 가능
-
````html
<!DOCTYPE html>
<!--hls js-->
<html lang="ko">
<head>
  <meta charset="utf-8" />
  <title>HLS.js Test</title>
  <script src="https://cdn.jsdelivr.net/npm/hls.js@latest"></script>
  <style>body{font-family:sans-serif;padding:20px} video{width:80%;max-width:960px;}</style>
</head>
<body>
  <h1>HLS.js Player</h1>
  <video id="video" controls autoplay></video>
  <script>
    const src = '/hls/1/output.m3u8'; // HLS 경로는 동적으로 생성
    const video = document.getElementById('video');

    if (Hls.isSupported()) {
      const hls = new Hls();
      hls.loadSource(src);
      hls.attachMedia(video);
      hls.on(Hls.Events.ERROR, (e, data) => console.log('HLS error:', data));
    } else if (video.canPlayType('application/vnd.apple.mpegurl')) {
      // Safari 등 네이티브 HLS
      video.src = src;
    } else {
      document.body.insertAdjacentHTML('beforeend','<p>이 브라우저는 HLS를 직접 재생할 수 없습니다.</p>');
    }
  </script>
</body>
</html>
````