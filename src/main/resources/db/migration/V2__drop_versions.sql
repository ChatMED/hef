alter table questions
    drop constraint if exists questions_version_id_fkey;
alter table questions
    drop column if exists version_id;

drop table if exists versions;