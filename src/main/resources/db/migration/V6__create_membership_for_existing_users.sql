insert into memberships (user_id, workspace_id, next_question_id, current_question_id)
select id, 1, next_question_id, current_question_id
from hef_users;

alter table hef_users
    drop constraint if exists hef_users_next_question_id_fkey;
alter table hef_users
    drop constraint if exists hef_users_current_question_id_fkey;
alter table hef_users
    drop column if exists next_question_id;
alter table hef_users
    drop column if exists current_question_id;