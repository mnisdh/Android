/*
3. 읽기
	select 컬럼1, 컬럼2, 컬럼3 from 테이블명 where 조건절
    
 - 전체 데이터 가져올때는 where 조건절 생략
 
 - 조건절 사용
	1. 정확한 단어 매칭
		where name ='지코'
	2. 포함되는 단어 검색
		where name like '%검색어%'
	3. 값의 범위를 검색
		where datetime between '2017-01-01' and '2017-08-31'

*/

select name, content, datetime from memo;
select * from memo;