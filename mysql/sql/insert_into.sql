/*
2. 입력
	insert into 테이블명( 컬럼1, 컬럼2, 컬럼3)
	values ('문자열', 222, now());

 - 자동증가 컬럼에는 값을 넣지 않는다
*/

insert into memo( name, content, datetime)
values('지코', '내용을 여기', now());

insert into memo( name, content, datetime)
values('딘', '여기', now());

insert into memo( name, content, datetime)
values('개코', '여기', now());

insert into memo( name, content, datetime)
values('우원재', '내용을', now());

commit;