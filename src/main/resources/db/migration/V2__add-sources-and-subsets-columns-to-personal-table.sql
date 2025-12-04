alter table personal add column sources jsonb;
alter table personal add column subsets jsonb;
alter table personal add column category varchar(255) default '';
