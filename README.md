스프링 부트 웹 애플리케이션 예제
===

[![Build Status](https://travis-ci.org/djkeh/bootSecurityJPAExample.svg?branch=master)](https://travis-ci.org/djkeh/bootSecurityJPAExample)

자바 + 스프링 부트 2 웹 애플리케이션 예제 프로젝트입니다.

### 스펙

* Java 16
* Gradle `7.1.1`
* Spring Boot `2.5.2`
  * Spring Security
  * Spring Data Jpa
  * Spring Data Rest + HAL Browser
  * Spring Boot Devtools
  * Spring Boot Actuator
  * Thymeleaf
* H2 Database
* QueryDSL `4.4.0`
* Lombok `1.18.20`

### 목표

* 웹 애플리케이션 구현에 다음 기술을 이용한다: Spring Boot 2, Spring Web, JPA
* 테스트를 포함한다
* 회원 정보 도메인을 다룬다
* 다음 기능을 구현한다
  * 회원 정보 추가
  * 회원 목록 조회
  * 회원 로그인
  * 본인 정보 조회
