----------------------
TowerDefense
# 타워디펜스
+ 게임컨셉
  + 타일 맵기반 타워 디펜스
  + 모든 몬스터를 막아내면 승리
  + 몬스터를 잡고 획득한 재화로 타워를 설치 및 강화
  + 일정 시간마다 몬스터가 몰려오는 BigWave가 존재
# 개발 범위
  + 타워
    + 드래그 앤 드롭으로 설치
    + 광역/슬로우 등 다양한 형태로 공격
    + 재화를 통한 강화 및 구매
  + 몬스터
    + 자동 이동
    + 재화 드랍
    + 체력/이동속도
  + 타일 맵
    + 몬스터가 이동하는 구역
    + 타워를 설치할 수 있는 구역
  + 상점
    + 재화를 소모해서 타워의 성능을 강화
# 예상 게임 실행 흐름

![그림1](https://user-images.githubusercontent.com/42472602/160602676-65c861bd-2f2d-411f-b65d-d8fb11831260.jpg)
- 상점에서 드래그 앤 드랍으로 포탑 설치

![그림2](https://user-images.githubusercontent.com/42472602/160603136-ebef3e9e-2433-46df-b088-4bc40b99fb31.png)
- 일정 주기마다 몬스터 다수 등장

![그림3](https://user-images.githubusercontent.com/42472602/160603237-b2f4c3db-27a6-48ba-a62e-c0883a6e55f7.png)
- 몰려오는 모든 몬스터를 잡으면 게임 클리어

# Class Map
  + int[][] map : 전체 맵과 이동하는 곳 순서를 가지고 있는 2차원 배열
  + Init() : map을 읽어서 화면에 알맞은 타일을 생성한다.
  + Array<Tile> roadTile : 이동하는 타일을 이동하는 순으로 담은 array

# Class Tower
  + Float fireInterval : 공격 주기
  + Fire() : 공격 주기마다 타겟방향으로 Bullet을 발사하는 함수
  + setTarget() : atan2를 이용하여 타겟을 계속해서 바라보기위한 각도를 구한다.
  + 타겟선정은 존재하는 enemy 중에 가장 먼저 생성된 enemy가 타겟이다.

# Class Bullet
  + Power : 데미지
  + setTargetAngle() : 타워가 바라보는 enemy와의 각도를 계산한다.
  + 발사된 방향으로 전진한다.
  + Enemy와 충돌하면 일정 데미지를 주고 사라진다.
  
# Class Enemy
  + Gauge : 체력을 막대형태로 표시
  + Array<Tile> roadTile : 이동하는 타일을 이동하는 순으로 담은 array
  + Void Move() : roadTile에 담겨있는 목적지 순서에 따라서 이동한다. 목적지와의 거리가 일정거리보다 작으면 도착한 것으로 판단하고 다음 목적지로 이동한다.
  + Boolean decreaseLife() : 입은 데미지에 따라서 gauge조절 및 사망 판정
# Class CollisionChecker
  + 매 프레임마다 모든 enemy와 bullet객체의 충돌체크를 진행한다.
  + Class CollisionHelper : 2개의 GameObject 객체에 대하여 BoundingBox충돌 판정을 해주는 클래스
# 상호 작용
  + 타워 -> 발사체 : 일정 시간마다 발사체 생성, 타겟 각도 설정
  + 발사체 -> enemy : 충돌 후 발사체 제거, enemy gauge 감소
# 일정
주차|일정(진행도)|
---|---|
1주차|리소스 수집(80%)|
2주차|타일 맵 구현(90%)|
3주차|이동하는 몬스터 구현(100%)|
4주차|포탑 설치와 공격 구현(50%)|
5주차|몬스터와 포탑간의 충돌 구현(80%)|
6주차|상점 UI 구현|
7주차|인게임 UI 구현|
8주차|사운드 추가|
9주차|완성|
