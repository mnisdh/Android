/*
1. 테이블 생성
	create table 테이블명(
		컬럼명1 컬럼타입 옵션1 옵션2,
        컬럼명2 컬럼타입,
        컬럼명3 컬럼타입,
	);
    
 - 자동증가값 : primary key not null auto_increment

*/

create table memo (
	`no` int primary key not null auto_increment,
    `name` varchar(100),
    `content` text,
    `datetime` datetime
    );
