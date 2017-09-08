/*
4. 수정
  update 테이블명 set 변경할컬럼1 = '변경값'
					,변경할컬럼2 = 변경값
				where 특정컬럼명 = '업데이트할 값'

 - 1175 error : SET SQL_SAFE_UPDATES =0;
*/

update memo set content = '수정된 내용입니다.'
			where name = '개코';

select * from memo where name = '개코';