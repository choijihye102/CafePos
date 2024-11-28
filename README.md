create table product (
    pro_id number(10,0),
    pro_name varchar2(30),
    pro_price number(10,0),
    pro_type varchar2(30)
    );
    
insert into product
values(1, '아이스 아메리카노', 2500, '1');

insert into product
values(2, '바닐라 라떼', 3500, '1');

insert into product
values(3, '카페라떼', 3500, '1');

insert into product
values(4, '카라멜 마끼아또', 4000, '1');

insert into product
values(5, '토마토 파스타', 7000, '2');

insert into product
values(6, '라구 파스타', 8500, '2');

insert into product
values(7, '파질페스토 파스타', 8000, '2');

insert into product
values(8, '알리오올리오', 8500, '2');

insert into product
values(9, '레몬에이드', 4000, '3');

insert into product
values(10, '블루에이드', 4000, '3');

insert into product
values(11, '사이다', 2000, '3');

insert into product
values(12, '콜라', 2000, '3');

insert into product
values(13, '치즈 케이크', 4000, '4');

insert into product
values(14, '초코 케이크', 4000, '4');

insert into product
values(15, '에그타르트', 2500, '4');

insert into product
values(16, '휘낭시에', 2000, '4');

insert into product
values(17, '리코타샐러드', 2500, '5');

insert into product
values(18, '케이준치킨샐러드', 2500, '5');

insert into product
values(19, '양송이 크림스프', 2500, '5');

insert into product
values(20, '감자튀김', 2500, '5');

commit;
