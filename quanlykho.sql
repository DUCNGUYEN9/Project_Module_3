create database quanlykho ;
use quanlykho ;
-- table product
create table product(
	product_id char(5) primary key,
    product_name varchar(150) not null unique,
    manufacturer varchar(200) not null,
    created date,
    batch smallint not null,
    quantity int not null default 0,
    product_status bit default 1
);
-- table employee
create table employee(
	emp_id char(5) primary key,
    emp_name varchar(100) not null unique,
    birth_of_date date,
    email varchar(100) not null,
    phone varchar(100) not null,
    address text not null,
    emp_status smallint not null
);
-- alter table employee drop index emp_name;
-- table account
create table account(
	acc_id int primary key auto_increment,
    user_name varchar(30) not null unique,
    acc_password varchar(30) not null,
    permission bit default 1,
    emp_id char(5) not null unique,
    foreign key(emp_id) references employee(emp_id),
    acc_status bit default 1
);
-- table bill
create table bill(
	bill_id bigint primary key auto_increment,
    bill_code varchar(10) not null,
    bill_type bit not null,
    emp_id_created char(5) not null,
    foreign key(emp_id_created) references employee(emp_id),
    created date,
    emp_id_auth char(5),
    foreign key(emp_id_auth) references employee(emp_id),
    auth_date date,
    bill_status smallint not null default 0
);
alter table bill add unique(bill_code);
-- create trigger for created_bill
delimiter //
drop trigger if exists before_insert_create_bill;
create trigger before_insert_create_bill before insert on bill for each row
begin
	if NEW.created is null then
		set NEW.created = current_date();
	end if ;
end //
delimiter ;
delimiter //
-- drop trigger if exists before_update_create_bill;
create trigger before_update_create_bill before update on bill for each row
begin
		set NEW.created = current_date();
end //
delimiter ;
-- table bill_detail
create table bill_detail(
	bill_detail_id bigint primary key auto_increment,
    bill_id bigint not null,
    foreign key(bill_id) references bill(bill_id),
    product_id char(5) not null,
    foreign key(product_id) references product(product_id),
    quantity int not null check(quantity > 0),
    price float not null check(price > 0)
);

-- *******PRODUCT MANAGEMENT******
-- display
delimiter //
 -- drop procedure if exists get_all_product;
create procedure get_all_product(
	page_number int,
    page_size int
)
begin
    declare offset_records int;
    -- calculate the offset
    set offset_records = (page_number - 1) * page_size;
    -- retrieve the records
	select * from product
    order by product_name asc
    limit offset_records,page_size;
end //
delimiter ;
-- get total
delimiter //
create procedure get_total_product(
    out total_records int
)
begin
    -- get the total number of records
    select count(*) into total_records from product;
end //
delimiter ;
-- insert
delimiter //
drop procedure if exists insert_product;
create procedure insert_product(
	productid char(5),
    productname varchar(150),
    p_manufacturer varchar(200),
    -- p_created date,
    p_batch smallint,
    productstatus bit
)
begin
	insert into product(product_id,product_name,manufacturer,batch,product_status)
    values(productid,productname,p_manufacturer,p_batch,productstatus);
end //
delimiter ;
-- validate validate_product_id
delimiter //
drop procedure validate_product_id;
create procedure validate_product_id(
    in productid char(5),
    out message text
)
begin
    declare product_count int;
    -- Count the number of rows with the given productid
    select count(*) into product_count
    from product
    where product_id = productid;
    if product_count > 0 then
        set message = 'Tên này đã tồn tại,Vui lòng nhập lại !';
    else
        set message = 'OK';
    end if;
end //
delimiter ;
call validate_product_id('P0092',@mes);
select @mes
-- validate_product_name 
delimiter //
-- drop procedure validate_product_name;
create procedure validate_product_name(
    in productname varchar(150),
    out message text
)
begin
    declare product_count int;
    -- Count the number of rows with the given productid
    select count(*) into product_count
    from product
    where product_name = productname;
    if product_count > 0 then
        set message = 'Tên này đã tồn tại,Vui lòng nhập lại !';
    else
        set message = 'OK';
    end if;
end //
delimiter ;
-- check_product_status 
delimiter //
-- drop procedure check_product_status;
create procedure check_product_status(
    in productname varchar(150),
    out message text
)
begin
    declare product_count int;
    -- Count the number of rows with the given productid
    select count(*) into product_count
    from product
    where product_status = 0;
    if product_count > 0 then
        set message = 'Sản phẩm này hiện tại không hoạt động ,Vui lòng nhập lại !';
    else
        set message = 'OK';
    end if;
end //
delimiter ;
call check_product_status('P0001',@a);
select @a
-- create trigger for created
delimiter //
create trigger before_insert_product_created before insert on product for each row
begin
	if NEW.created is null then
        set NEW.created = current_date() ;
	end if ;
end //
delimiter ;
-- check_exists_id
delimiter //
create procedure check_exists_id_product(
	productid char(5)
)
begin
	select * from product
    where product_id = productid ;
end //
delimiter ;
-- update status
delimiter //
drop procedure if exists update_product;
create procedure update_product(
	productid char(5),
    productname varchar(150),
    p_manufacturer varchar(200),
    -- p_created date,
    p_batch smallint,
    productstatus bit
)
begin
	update product
    set product_name = productname,
		manufacturer = p_manufacturer,
        created = current_date(),
        batch = p_batch,
        product_status = productstatus
	where product_id = productid ;
end //
delimiter ;
-- search name
delimiter //
create procedure search_product_name(
	productname varchar(150)
)
begin
    declare name_search varchar(152);
    set name_search = concat("%",productname,"%");
    select *
    from product
    where product_name like name_search ;
end //
delimiter ;
-- ******EMPLOYEE MANAGEMENT*****
-- display
delimiter //
 -- drop procedure if exists get_all_employee;
create procedure get_all_employee(
	page_number int,
    page_size int
)
begin
    declare offset_records int;
    -- calculate the offset
    set offset_records = (page_number - 1) * page_size;
    -- retrieve the records
	select * from employee
    order by emp_name asc
    limit offset_records,page_size;
end //
delimiter ;
-- get total
delimiter //
create procedure get_total_employee(
    out total_records int
)
begin
    -- get the total number of records
    select count(*) into total_records from employee;
end //
delimiter ;
call get_total_employee(@total);
select @total;
call get_all_employee(1,10);

-- insert
delimiter //
create procedure insert_employee(
	empid char(5),
    empname varchar(100),
    birthofdate date,
    empemail varchar(100),
    empphone varchar(100),
    empaddress text,
    empstatus smallint
)
begin
	insert into employee
    values(empid,empname,birthofdate,empemail,empphone,empaddress,empstatus);
end //
delimiter ;
call insert_employee('E0005','nguyen van d',"1999-10-10",'duc2@gmail.com','0987654321','sai gon',1);
-- validate_employee_id
delimiter //
drop procedure validate_employee_id;
create procedure validate_employee_id(
    in empid char(5),
    out message text
)
begin
    declare employee_count int;
    -- Count the number of rows with the given productid
    select count(*) into employee_count
    from employee
    where emp_id = empid;
    if employee_count > 0 then
        set message = 'Mã này đã tồn tại,Vui lòng nhập lại !';
    else
        set message = 'OK';
    end if;
end //
delimiter ;
call validate_employee_id('E0002',@a);
select @a
-- check_exists_id
delimiter //
create procedure check_exists_id(
	empid char(5)
)
begin
	select * from employee
    where emp_id = empid ;
end //
delimiter ;
-- update status
delimiter //
drop procedure if exists update_status_employee;
create procedure update_status_employee(
	empid char(5),
    empname varchar(100),
    birthday date,
    e_email varchar(100),
    e_phone varchar(100),
    e_address text,
    empstatus smallint
)
begin
	update employee
    set emp_name = empname,
		birth_of_date = birthday,
        email = e_email,
        phone = e_phone,
        address = e_address,
		emp_status = empstatus
	where emp_id = empid ;
end //
delimiter ;
call get_all_employee(1,10);
call update_status_employee('E0001',2)
-- search name
delimiter //
create procedure search_employee_name(
	empname varchar(100)
)
begin
    declare name_search varchar(102);
    set name_search = concat("%",empname,"%");
    select *
    from employee
    where emp_name like name_search ;
end //
delimiter ;
-- search id
delimiter //
create procedure search_employee_id(
	empid char(5)
)
begin
    declare name_search char(7);
    set name_search = concat("%",empid,"%");
    select *
    from employee
    where emp_id like name_search ;
end //
delimiter ;
call search_employee_id("1");
-- create trigger account auto block 
-- Xóa trigger cũ
drop trigger if exists before_update_status;
delimiter //
create trigger before_update_status before update on account for each row
begin
    -- Lấy emp_id của nhân viên liên quan đến tài khoản bị cập nhật
    declare emp_value char(5);
    select emp_id into emp_value from account where acc_id = NEW.acc_id;
    -- Kiểm tra trạng thái của nhân viên và cập nhật trạng thái tài khoản
    if (select emp_status from employee where emp_id = emp_value) in (1, 2) then
        set NEW.acc_status = 0;
    end if;
end //
delimiter ;
-- Tạo trigger mới
-- sau khi cập nhật bảng employee thì bảng account tự động cập nhật 
delimiter //
-- drop trigger if exists after_update_employee;
create trigger after_update_employee after update on employee for each row
begin
    -- Cập nhật trạng thái tài khoản của các tài khoản liên quan
    update account a
    set a.acc_status = 0
    where a.emp_id = NEW.emp_id and NEW.emp_status in (1,2);
end //
delimiter ;

-- *******ACCOUNT MANAGEMENT*****
-- display
delimiter //
create procedure get_all_account(
	page_number int,
    page_size int
)
begin
    declare offset_records int;
    -- calculate the offset
    set offset_records = (page_number - 1) * page_size;
    -- retrieve the records
	select * from account
    limit offset_records,page_size;
end //
delimiter ;
call get_all_account(1,10);
-- get total
delimiter //
create procedure get_total_account(
    out total_records int
)
begin
    -- get the total number of records
    select count(*) into total_records from account;
end //
delimiter ;
-- insert
delimiter //
-- drop procedure if exists insert_account;
create procedure insert_account(
    username varchar(30),
    accpassword varchar(30),
    accpermission bit,
    empid char(5),
    accstatus bit
)
begin
	insert into account(user_name,acc_password,permission,emp_id,acc_status)
    values(username,accpassword,accpermission,empid,accstatus);
end //
delimiter ;
call insert_account('duc131','2312313',1,'E0006',1);
-- validate_user_name
delimiter //
-- drop procedure validate_user_name;
create procedure validate_user_name(
    in username varchar(30),
    out message text
)
begin
    declare employee_count int;
    -- Count the number of rows with the given productid
    select count(*) into employee_count
    from account
    where user_name = username;
    if employee_count > 0 then
        set message = 'Tên này đã tồn tại,Vui lòng nhập lại !';
    else
        set message = 'OK';
    end if;
end //
delimiter ;
-- validate_acc_emp_id
delimiter //
-- drop procedure validate_acc_emp_id;
create procedure validate_acc_emp_id(
    in empid char(5),
    out message text
)
begin
    declare employee_count int;
    -- Count the number of rows with the given productid
    select count(*) into employee_count
    from account
    where emp_id = empid;
    if employee_count > 0 then
        set message = 'Tên này đã tồn tại,Vui lòng nhập lại !';
    else
        set message = 'OK';
    end if;
end //
delimiter ;
-- update status
delimiter //
 drop procedure if exists update_status_account;
create procedure update_status_account(
	accid int,
    accstatus bit
)
begin
	update account
    set acc_status = accstatus
	where acc_id = accid ;
end //
delimiter ;

call update_status_account(6,1);
-- check_exists_id
delimiter //
drop procedure if exists check_exists_id_account;
create procedure check_exists_id_account(
	accid int
)
begin
	select * from account
    where acc_id = accid ;
end //
delimiter ;
-- search name
delimiter //
create procedure search_account_name(
	accname varchar(100)
)
begin
    declare name_search varchar(102);
    set name_search = concat("%",accname,"%");
    select *
    from account a join employee e on a.emp_id = e.emp_id
    where e.emp_name like name_search ;
end //
delimiter ;
call search_account_name('duc');
-- search username
delimiter //
-- drop procedure if exists search_account_username;
create procedure search_account_username(
	accname varchar(30)
)
begin
    declare name_search varchar(32);
    set name_search = concat("%",accname,"%");
    select *
    from account
    where user_name like name_search ;
end //
delimiter ;
call search_account_username('duc');
-- check status
delimiter //
drop procedure if exists check_status_acc_emp;
create procedure check_status_acc_emp(
	accid int
)
begin
    select *
    from account a join employee e on a.emp_id = e.emp_id
    where e.emp_status = 0 and a.acc_id = accid ;
end //
delimiter ;
-- *******RECEIPT MANAGEMENT******
-- display
delimiter //
create procedure get_all_receipt(
	page_number int,
    page_size int
)
begin
    declare offset_records int;
    -- calculate the offset
    set offset_records = (page_number - 1) * page_size;
    -- retrieve the records
	select * from bill
    where bill_type = 1
    limit offset_records,page_size;
end //
delimiter ;
-- get total
delimiter //
create procedure get_total_receipt(
    out total_records int
)
begin
    -- get the total number of records
    select count(*) into total_records from bill
    where bill_type = 1;
end //
delimiter ;
-- insert
delimiter //
drop procedure if exists insert_receipt;
create procedure insert_receipt(
    billcode varchar(10),
    empidcreated char(5)
)
begin
	insert into bill(bill_code,emp_id_created,bill_type)
    values(billcode,empidcreated,1);
end //
delimiter ;
call insert_receipt('R0100','E0001');
call get_all_receipt_detail(1,10);
-- display detail bill
delimiter //
drop procedure if exists get_all_receipt_detail;
create procedure get_all_receipt_detail(
	page_number int,
    page_size int
)
begin
    declare offset_records int;
    -- calculate the offset
    set offset_records = (page_number - 1) * page_size;
    -- retrieve the records
	select * from bill_detail bd join bill b on bd.bill_id = b.bill_id
    where b.bill_type = 1
    limit offset_records,page_size;
end //
delimiter ;
-- get total
delimiter //
drop procedure if exists get_total_receipt_detail;
create procedure get_total_receipt_detail(
    out total_records int
)
begin
    -- get the total number of records
    select count(*) into total_records from bill_detail bd join bill b on bd.bill_id = b.bill_id
    where b.bill_type = 1;
end //
delimiter ;
-- update bill
delimiter //
 drop procedure if exists update_receipt;
create procedure update_receipt(
	billid bigint,
    billcode varchar(10),
    empidcreated char(5)
)
begin
	update bill 
    set bill_code = billcode,
		emp_id_created = empidcreated
	where bill_id = billid and bill_status <> 2;
end //
delimiter ;
-- check_exists_id
delimiter //
drop procedure if exists check_exists_id_bill;
create procedure check_exists_id_bill(
	billid bigint
)
begin
	select * from bill
    where bill_id = billid and bill_type = 1 ;
end //
delimiter ;
-- check_exists_id_status
delimiter //
create procedure check_exists_id_status_bill(
	billid bigint
)
begin
	select * from bill
    where bill_id = billid and bill_status = 0 or bill_status = 1 ;
end //
delimiter ;
-- approve_receipt
delimiter //
 drop procedure if exists approve_receipt;
create procedure approve_receipt(
	billid bigint,
    empidauth char(5)
)
begin
	update bill b join bill_detail bd on b.bill_id = bd.bill_id
    join product  p on bd.product_id = p.product_id
    set b.bill_status = 2,
		emp_id_auth = empidauth,
        auth_date = current_date(),
        p.quantity = p.quantity + bd.quantity
    where b.bill_id = billid and b.bill_status = 0 ;
end //
delimiter ;
call approve_receipt(1,'E2222');
-- approve_bill
delimiter //
 -- drop procedure if exists approve_bill;
create procedure approve_bill(
	billid bigint,
    empidauth char(5)
)
begin
	update bill b join bill_detail bd on b.bill_id = bd.bill_id
    join product  p on bd.product_id = p.product_id
    set b.bill_status = 2,
		emp_id_auth = empidauth,
        auth_date = current_date(),
        p.quantity = p.quantity - bd.quantity
    where b.bill_id = billid and b.bill_status = 0 ;
end //
delimiter ;
call approve_bill(16,'E2222');
--
delimiter //
-- drop trigger if exists before_update_add_quantity_in_product;
create trigger before_update_add_quantity_in_product before update on bill for each row
begin
	if NEW.emp_id_auth is not null then
		set NEW.auth_date = current_date();
	end if ;
end //
delimiter ;
-- validate_quantity
delimiter //
-- drop procedure validate_quantity;
create procedure validate_quantity(
     qtt int,
     productid char(5),
    out message text
)
begin
    declare counts int;
    -- Count the number of rows with the given productid
    select count(*) into counts
    from product
    where quantity < qtt and product_id =productid;
    if counts > 0 then
        set message = 'Số lượng bạn nhập lớn hơn trong kho, Vui lòng nhập lại !';
    else
        set message = 'OK';
    end if;
end //
delimiter ;

-- validate_receipt_id
delimiter //
drop procedure validate_receipt_id;
create procedure validate_receipt_id(
    in billid bigint,
    out message text
)
begin
    declare employee_count int;
    -- Count the number of rows with the given productid
    select count(*) into employee_count
    from bill
    where bill_status = 2 and bill_id = billid;
    if employee_count > 0 then
        set message = 'Mã này đã được duyệt,Vui lòng nhập lại !';
    else
        set message = 'OK';
    end if;
end //
delimiter ;
-- validate_acc_id
-- kiểm tra mã nhân viên quyền trạng thai acc và emp 
delimiter //
drop procedure validate_acc_id;
create procedure validate_acc_id(
    in empid char(5),
    out message text
)
begin
    declare employee_count int;
    -- Count the number of rows with the given productid
    select count(*) into employee_count
    from account a join employee e on a.emp_id = e.emp_id
    where a.emp_id = empid and a.permission = 0 and  acc_status = 1 and e.emp_status = 0;
    if employee_count > 0 then
        set message = 'OK';
    else
        set message = 'Nhân viên này bị hạn chế quyền, Vui lòng nhập lại !';
    end if;
end //
delimiter ;
call validate_acc_id('E2222',@message);
select @message
-- validate_emp_status
-- kiểm tra mã nhân viên quyền trạng thai emp 
delimiter //
drop procedure validate_emp_status;
create procedure validate_emp_status(
    in empid char(5),
    out message text
)
begin
    declare employee_count int;
    -- Count the number of rows with the given productid
    select count(*) into employee_count
    from account a join employee e on a.emp_id = e.emp_id
    where a.emp_id = empid and e.emp_status = 0 and a.acc_status = 1;
    if employee_count > 0 then
        set message = 'OK';
    else
        set message = 'Nhân viên này bị hạn chế quyền, Vui lòng nhập lại !';
    end if;
end //
delimiter ;
call validate_emp_status('E2222',@message);
select @message
-- search id
delimiter //
create procedure search_receipt_id(
	billid bigint
)
begin
    select *
    from bill
    where bill_id = billid ;
end //
delimiter ;
-- insert insert_receipt_detail
delimiter //
drop procedure if exists insert_receipt_detail;
create procedure insert_receipt_detail(
    billid bigint,
    productid char(5),
    bdquantity int,
    bdprice float
)
begin
	insert into bill_detail(bill_id,product_id,quantity,price)
    values(billid,productid,bdquantity,bdprice);
end //
delimiter ;
call insert_receipt_detail(17,'P1002',200,2000);
-- validate_receipt_code
delimiter //
 drop procedure validate_receipt_code;
create procedure validate_receipt_code(
     billcode char(5),
    out message text
)
begin
    declare counts int;
    -- Count the number of rows with the given productid
    select count(*) into counts
    from bill
    where bill_code = billcode;
    if counts > 0 then
        set message = 'Mã code này đã tồn tại, Vui lòng nhập lại !';
    else
        set message = 'OK';
    end if;
end //
delimiter ;
-- validate_bill_detail_approved
delimiter //
  drop procedure validate_bill_detail_approved;
create procedure validate_bill_detail_approved(
     billid char(5),
    out message text
)
begin
    declare counts int;
    -- Count the number of rows with the given productid
    select count(*) into counts
    from bill
    where bill_id = billid and bill_status = 2;
    if counts > 0 then
        set message = 'Đã duyệt';
    else
        set message = 'Chưa duyệt';
    end if;
end //
delimiter ;

-- *******BILL MANAGEMENT********
-- display
delimiter //
create procedure get_all_bill(
	page_number int,
    page_size int
)
begin
    declare offset_records int;
    -- calculate the offset
    set offset_records = (page_number - 1) * page_size;
    -- retrieve the records
	select * from bill
    where bill_type = 0
    limit offset_records,page_size;
end //
delimiter ;
-- get total
delimiter //
create procedure get_total_bill(
    out total_records int
)
begin
    -- get the total number of records
    select count(*) into total_records from bill
    where bill_type = 0;
end //
delimiter ;
-- get total
delimiter //
 drop procedure if exists get_total_bill_detail;
create procedure get_total_bill_detail(
    out total_records int
)
begin
    -- get the total number of records
    select count(*) into total_records from bill_detail bd join bill b on bd.bill_id = b.bill_id
    where b.bill_type = 0 and b.bill_status <> 2;
end //
delimiter ;
-- insert
delimiter //
drop procedure if exists insert_bill;
create procedure insert_bill(
    billcode varchar(10),
    empidcreated char(5)
)
begin
	insert into bill(bill_code,emp_id_created,bill_type)
    values(billcode,empidcreated,0);
end //
delimiter ;
call get_all_employee(1,10);

-- check id
delimiter //
create procedure get_exists_id_detail(
	billid bigint
)
begin
    select *
    from bill b JOIN bill_detail AS bd ON b.bill_id = bd.bill_id
    JOIN product AS p ON bd.product_id = p.product_id
    where b.bill_id = bill_id_param
        AND b.bill_status = 0 and p.quantity > bd.quantity;
end //
delimiter ;
-- update bill
delimiter //
-- drop procedure if exists update_receipt;
create procedure update_bill_detail(
	billid bigint,
    billcode varchar(10),
    empidcreated char(5)
)
begin
	update bill 
    set bill_code = billcode,
		emp_id_created = empidcreated
	where bill_id = billid and bill_status = 0 or bill_status = 1 ;
end //
delimiter ;
-- check_exists_id
delimiter //
create procedure check_exists_id_detail(
	billid bigint
)
begin
	select * from bill
    where bill_id = billid and bill_type = 0;
end //
delimiter ;
-- check_exists_id_status
delimiter //
create procedure check_exists_id_status_bill(
	billid bigint
)
begin
	select * from bill
    where bill_id = billid and bill_status = 0 or bill_status = 1 ;
end //
delimiter ;
-- update_receipt_detail
-- chưa hoàn thành
delimiter //
drop procedure if exists update_receipt_detail;
create procedure update_receipt_detail(
	billid bigint,
    productid char(5),
    bquantity int,
    bprice float,
    out message text
)
begin
	declare counts int;
	select count(*) into counts 
    from bill_detail
    where bill_id = billid and product_id = productid ;
    if counts > 0 then
		update bill_detail
        set quantity = bquantity,
			price = bprice;
		set message = 'OK';
            
    else
		set message = 'no update';
    end if;
end //
delimiter ;
-- approve_bill 
delimiter //
create procedure approve_bill(
	billid bigint,
    empidauth char(5)
)
begin
	update bill
    set bill_status = 2,
		emp_id_auth = empidauth,
        auth_date = current_date()
    where bill_id = billid and bill_status = 0 ;
end //
delimiter ;
-- insert
delimiter //
-- drop procedure if exists insert_bill_detail;
create procedure insert_bill_detail(
    billcode varchar(10),
    empidcreated char(5)
)
begin
	insert into bill(bill_code,emp_id_created,bill_type)
    values(billcode,empidcreated,0);
end //
delimiter ;
-- display detail bill
delimiter //
drop procedure if exists get_all_bill_detail;
create procedure get_all_bill_detail(
	page_number int,
    page_size int
)
begin
    declare offset_records int;
    -- calculate the offset
    set offset_records = (page_number - 1) * page_size;
    -- retrieve the records
	select * from bill_detail bd join bill b on bd.bill_id = b.bill_id
    where b.bill_type = 0
    limit offset_records,page_size;
end //
delimiter ;
call get_all_bill_detail(1,10);
-- search id
delimiter //
create procedure search_bill_id(
	billid bigint
)
begin
    select *
    from bill
    where bill_id = billid and bill_type = 0;
end //
delimiter ;
-- insert
delimiter //
drop procedure if exists insert_detail;
create procedure insert_detail(
    billid bigint,
    productid char(5),
    bdquantity int,
    bdprice float
)
begin
	insert into bill_detail(bill_id,product_id,quantity,price)
    values(billid,productid,bdquantity,bdprice);
end //
delimiter ;
call insert_detail(2,'P0003',21,200);
-- update
delimiter //
create procedure update_detail(
	billid bigint,
    bdquantity int,
    bdprice float
)
begin
	update bill
    set quantity = bdquantity,
        price = bdprice
    where bill_id = billid and bill_status = 0 ;
end //
delimiter ;
-- validate_receipt_status
delimiter //
drop procedure if exists validate_receipt_status;
create procedure validate_receipt_status(
     num bigint,
    out message text
)
begin
    declare counts int;
    -- Count the number of rows with the given productid
    select count(*) into counts
    from bill
    where bill_id = num and bill_status = 2 and bill_type = 1;
    if counts > 0 then
		 set message = 'Mã phiếu bạn nhập đã được duyệt,Vui lòng chọn phiếu khác !';
    else
		set message = 'OK';
    end if;
end //
delimiter ;
-- validate_bill_status
delimiter //
-- drop procedure if exists validate_bill_status;
create procedure validate_bill_status(
     num bigint,
    out message text
)
begin
    declare counts int;
    -- Count the number of rows with the given productid
    select count(*) into counts
    from bill
    where bill_id = num and bill_status = 2 and bill_type = 0;
    if counts > 0 then
		 set message = 'Mã phiếu bạn nhập đã được duyệt,Vui lòng chọn phiếu khác !';
    else
		set message = 'OK';
    end if;
end //
delimiter ;
-- validate_product_status
delimiter //
 drop procedure if exists validate_product_status;
create procedure validate_product_status(
     productid char(5),
    out message text
)
begin
    declare counts int;
    -- Count the number of rows with the given productid
    select count(*) into counts
    from product
    where product_id = productid and product_status = 0;
    if counts > 0 then
		 set message = 'Mã sản phẩm bạn nhập không hoạt động, Vui lòng chọn sản phẩm khác !';
    else
		set message = 'OK';
    end if;
end //
delimiter ;
-- validate_receipt_id_exists
delimiter //
drop procedure if exists validate_receipt_id_exists;
create procedure validate_receipt_id_exists(
     num bigint,
    out message text
)
begin
    declare counts int;
    -- Count the number of rows with the given productid
    select count(*) into counts
    from bill
    where bill_id = num and bill_type = 1;
    if counts = 0 then
		 set message = 'no exists';
    else
		set message = 'OK';
    end if;
end //
delimiter ;
call validate_receipt_id_exists(23,@a);
select @a
-- validate_bill_id_exists
delimiter //
-- drop procedure if exists validate_bill_id_exists;
create procedure validate_bill_id_exists(
     num bigint,
    out message text
)
begin
    declare counts int;
    -- Count the number of rows with the given productid
    select count(*) into counts
    from bill
    where bill_id = num and bill_type = 0;
    if counts = 0 then
		 set message = 'no exists';
    else
		set message = 'OK';
    end if;
end //
delimiter ;
-- validate_product_id_exists
delimiter //
  drop procedure if exists validate_product_id_exists;
create procedure validate_product_id_exists(
     productid char(5),
    out message text
)
begin
    declare counts int;
    -- Count the number of rows with the given productid
    select count(*) into counts
    from product
    where product_id = productid;
    if counts = 0 then
		 set message = 'no exists';
    else
		set message = 'OK';
    end if;
end //
delimiter ;
call validate_product_id_exists('P0003',@a);
select @a
-- ********REPORT MANAGEMENT*******
-- statistics auth_date
delimiter //
-- drop procedure if exists statistics_expense;
create procedure statistics_expense(
	 date_bill date,
    out sum_expense float
)
begin
	select sum(bd.price * bd.quantity) into sum_expense
	from bill b join bill_detail bd on b.bill_id = bd.bill_id
	where b.bill_type = 1 and auth_date = date_bill;
	end //
delimiter ;
call statistics_expense('1998-01-01',@sum_expense);
select @sum_expense;
-- statistics auth_date time
delimiter //
 drop procedure if exists statistics_expense_time;
create procedure statistics_expense_time(
	 date_start date,
     date_end date,
     out sum_expense float
)
begin
	select sum(bd.price * bd.quantity) into sum_expense
	from bill b join bill_detail bd on b.bill_id = bd.bill_id
	where b.bill_type = 1 and b.auth_date between date_start and date_end;
	end //
delimiter ;
call statistics_expense_time('1997-01-01','1998-02-01',@sum_expense);
select @sum_expense;
-- statistics statistics_profit 
delimiter //
-- drop procedure if exists statistics_profit;
create procedure statistics_profit(
	 date_bill date,
    out sum_expense float
)
begin
	select sum(bd.price * bd.quantity) into sum_expense
	from bill b join bill_detail bd on b.bill_id = bd.bill_id
	where b.bill_type = 0 and auth_date = date_bill ;
	end //
delimiter ;
call statistics_profit('2023-10-06',@sum_expense);
select @sum_expense;
-- statistics statistics_profit time
delimiter //
 -- drop procedure if exists statistics_expense_time;
create procedure statistics_profit_time(
	 date_start date,
     date_end date,
     out sum_expense float
)
begin
	select sum(bd.price * bd.quantity) into sum_expense
	from bill b join bill_detail bd on b.bill_id = bd.bill_id
	where b.bill_type = 0 and b.auth_date between date_start and date_end;
	end //
delimiter ;
call statistics_expense_time('1997-01-01','1998-02-01',@sum_expense);
select @sum_expense;
-- statistics employ status
delimiter //
-- drop procedure if exists statistics_employee;
create procedure statistics_employee(
)
begin
	select emp_status,count(emp_id) as emp_number
	from employee
    group by emp_status;
end //
delimiter ;
-- statistics statistics_import_many time
delimiter //
-- drop procedure if exists statistics_import_many;
create procedure statistics_import_many(
	 date_start date,
     date_end date,
     out productname varchar(150)
)
begin
	select p.product_name into productname
    from bill b join bill_detail bd on b.bill_id = bd.bill_id
				join product p on bd.product_id = p.product_id
    where bill_type = 1 and bill_status = 2 and auth_date between date_start and date_end
    group by p.product_id
    having sum(bd.quantity)
    order by sum(bd.quantity) desc
    limit 1;
	end //
delimiter ;
call statistics_import_many('2023-9-10','2023-10-10',@productname);
select @productname;
-- statistics statistics_import_least time
delimiter //
  drop procedure if exists statistics_import_least;
create procedure statistics_import_least(
	 date_start date,
     date_end date,
     out productname varchar(150)
)
begin
	select p.product_name into productname
    from bill b join bill_detail bd on b.bill_id = bd.bill_id
				join product p on bd.product_id = p.product_id
    where bill_type = 1 and bill_status = 2 and auth_date between date_start and date_end
    group by p.product_id
    having sum(bd.quantity)
    order by sum(bd.quantity) asc
    limit 1;
	end //
delimiter ;
call statistics_import_least('1997-01-01','2023-12-01',@productname);
select @productname;
-- statistics statistics_import_least time
delimiter //
  drop procedure if exists statistics_export_many;
create procedure statistics_export_many(
	 date_start date,
     date_end date,
     out productname varchar(150)
)
begin
	select p.product_name into productname
    from bill b join bill_detail bd on b.bill_id = bd.bill_id
				join product p on bd.product_id = p.product_id
    where bill_type = 0 and bill_status = 2 and auth_date between date_start and date_end
    group by p.product_id
    having sum(bd.quantity)
    order by sum(bd.quantity) desc
    limit 1;
	end //
delimiter ;
call statistics_import_many('1997-01-01','2023-12-01',@productname);
select @productname;
-- statistics statistics_import_least time
delimiter //
 drop procedure if exists statistics_export_least;
create procedure statistics_export_least(
	 date_start date,
     date_end date,
     out productname varchar(150)
)
begin
		select p.product_name into productname
    from bill b join bill_detail bd on b.bill_id = bd.bill_id
				join product p on bd.product_id = p.product_id
    where bill_type = 0 and bill_status = 2 and auth_date between date_start and date_end
    group by p.product_id
    having sum(bd.quantity)
    order by sum(bd.quantity) asc
    limit 1;
	end //
delimiter ;
call statistics_import_many('1997-01-01','2023-12-01',@productname);
select @productname;
-- ********WAREHOUSE MANAGEMENT******
-- display get_user_receipt_status
delimiter //
create procedure get_user_receipt_status(
	page_number int,
    page_size int,
    billstatus smallint
)
begin
    declare offset_records int;
    -- calculate the offset
    set offset_records = (page_number - 1) * page_size;
    -- retrieve the records
	select * from bill
    where bill_id in
    (select b.bill_id
    from employee e join account a on e.emp_id = a.emp_id
					join bill b on e.emp_id = b.emp_id_created
	where a.permission = 1 and b.bill_type = 1 and b.bill_status = billstatus)
    limit offset_records,page_size;
end //
delimiter ;
call get_user_receipt_status(1,10,2);
-- get_total_user_receipt_status
delimiter //
drop procedure if exists get_total_user_receipt_status;
create procedure get_total_user_receipt_status(
    out total_records int,
    billstatus smallint
)
begin
    -- get the total number of records
    select count(*) into total_records from bill
     where bill_id in
    (select b.bill_id
    from employee e join account a on e.emp_id = a.emp_id
					join bill b on e.emp_id = b.emp_id_created
	where a.permission = 1 and b.bill_type = 1 and b.bill_status = billstatus);
end //
delimiter ;

-- display get_user_bill_status
delimiter //
create procedure get_user_bill_status(
	page_number int,
    page_size int,
    billstatus smallint
)
begin
    declare offset_records int;
    -- calculate the offset
    set offset_records = (page_number - 1) * page_size;
    -- retrieve the records
	select * from bill
    where bill_id in
    (select b.bill_id
    from employee e join account a on e.emp_id = a.emp_id
					join bill b on e.emp_id = b.emp_id_created
	where a.permission = 1 and b.bill_type = 0 and b.bill_status = billstatus)
    limit offset_records,page_size;
end //
delimiter ;
call get_user_bill_status(1,10,2);
-- get total get_total_user_bill_status
delimiter //
drop procedure if exists get_total_user_bill_status;
create procedure get_total_user_bill_status(
    out total_records int,
    billstatus smallint
)
begin
    -- get the total number of records
    select count(*) into total_records from bill
     where bill_id in
    (select b.bill_id
    from employee e join account a on e.emp_id = a.emp_id
					join bill b on e.emp_id = b.emp_id_created
	where a.permission = 1 and b.bill_type = 0 and b.bill_status = billstatus);
end //
delimiter ;
-- *****Login********
-- user name
delimiter //
 drop procedure if exists login_user_name;
create procedure login_user_name(
	username varchar(30),
    out num varchar(10)
)
begin
	select permission into num
    from account
    where user_name = username and acc_status = 1 ;
end //
delimiter ;
call login_user_name('nguyen1',@a);
select @a
-- login_password
delimiter //
create procedure login_password(
	passwords varchar(30)
)
begin
	select *
    from account
    where acc_password = passwords;
end //
delimiter ;
call login_password(123456);
-- *******USER************
-- validate_acc_id_user
-- kiểm tra mã nhân viên quyền trạng thai acc và emp 
delimiter //
drop procedure validate_acc_id_user;
create procedure validate_acc_id_user(
    in empid char(5),
    out message text
)
begin
    declare employee_count int;
    -- Count the number of rows with the given productid
    select count(*) into employee_count
    from account a join employee e on a.emp_id = e.emp_id
    where a.emp_id = empid and a.permission = 1 and  acc_status = 1 and e.emp_status = 0;
    if employee_count > 0 then
        set message = 'OK';
    else
        set message = 'Mã bạn nhập không hợp lệ, Vui lòng nhập lại !';
    end if;
end //
delimiter ;
