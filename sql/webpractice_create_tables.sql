-- ============================================================
--  webapp-exercise  データベース初期化スクリプト (PostgreSQL)
--  対象テーブル: department（部門） / employee（社員）
--  採番: シーケンス seq_deptno / seq_empno を使用（アプリのINSERTと対応）
--  文字コード: UTF-8
-- ============================================================

-- ---- 既存オブジェクトの削除（依存順: employee → department）----
drop sequence if exists seq_empno;
drop sequence if exists seq_deptno;
drop table    if exists employee;
drop table    if exists department;

-- ---- 部門テーブル ----
create table department (
    dept_id   integer,
    dept_name varchar(100) not null,
    primary key (dept_id)
);

-- 部門の採番シーケンス（101〜）
create sequence seq_deptno
    start with 101
    increment by 1
    maxvalue 999;

-- ---- 社員テーブル ----
create table employee (
    emp_id        integer,
    emp_name      varchar(100) not null,
    phone_number  varchar(20),
    email_address varchar(100),
    dept_id       integer,
    primary key (emp_id),
    foreign key (dept_id) references department (dept_id)
);

-- 社員の採番シーケンス（1001〜）
create sequence seq_empno
    start with 1001
    increment by 1
    maxvalue 9999;

-- ---- 初期データ: 部門 ----
insert into department values (nextval('seq_deptno'), '人事部');
insert into department values (nextval('seq_deptno'), '企画部');
insert into department values (nextval('seq_deptno'), 'システム開発部');

-- ---- 初期データ: 社員 ----
insert into employee values (nextval('seq_empno'), '山田太郎', '000-1111-2222', 'taro@foo.bar.baz',   101);
insert into employee values (nextval('seq_empno'), '川田次郎', '000-2222-3333', 'jiro@foo.bar.baz',   102);
insert into employee values (nextval('seq_empno'), '海田三郎', '000-3333-4444', 'saburo@foo.bar.baz', 101);