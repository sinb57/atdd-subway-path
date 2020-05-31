# 지하철 정보 관련 서비스

## 기능 목록
### 실습 - 지하철 노선도 조회

- [x] 모든 지하철 노선과 지하철역 목록을 조회

### 시나리오

### Feature: 전체 지하철 노선도 정보 조회
```
  Scenario: 지하철 노선도 정보 조회를 한다.
    Given 지하철역이 여러 개 추가되어있다.
    And 지하철 노선이 여러 개 추가되어있다.
    And 지하철 노선에 지하철역이 여러 개 추가되어있다.
    
    When 지하철 노선도 전체 조회 요청을 한다.
    
    Then 지하철 노선도 전체를 응답 받는다.
```

## 1단계

- [x] HTTP 캐시 적용하기

## 2단계

- [x] 최단 거리 경로 조회

### 시나리오

### Feature: 지하철 최단 거리 경로 조회
```
  Scenario: 지하철 최단 거리 경로를 조회한다.
    Given 여러 개의 노선에 여러 개의 지하철역이 추가되어있다.
    
    When 시작역과 도착역의 최단 거리 조회를 요청한다.
    
    Then 시작역과 도착역의 최단 거리를 응답 받는다.
```

## 3단계

- [x] 예외처리
    - 출발역과 도착역이 같은 경우
    - 출발역과 도착역이 연결되어 있지 않은 경우
    - 존재하지 않은 출발역이나 도착역을 조회할 경우

## 4단계

- [x] 최소시간 경로 조회

### 시나리오

### Feature: 지하철 최소 시간 경로 조회
```
  Scenario: 지하철 최소 시간 경로를 조회한다.
    Given 여러 개의 노선에 여러 개의 지하철역이 추가되어있다.
    
    When 시작역과 도착역의 최소 시간 조회를 요청한다.
    
    Then 시작역과 도착역의 최소 시간을 응답 받는다.
```