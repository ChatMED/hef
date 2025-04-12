insert into workspaces (id, name, created_at)
values (1, 'Workspace 1', NOW())
on conflict (id) do nothing;

alter table answers
    add column if not exists workspace_id bigint references workspaces;

update answers
set workspace_id = 1
where workspace_id is null;


alter table evaluations
    add column if not exists workspace_id bigint references workspaces;

update evaluations
set workspace_id = 1
where workspace_id is null;


alter table models
    add column if not exists workspace_id bigint references workspaces;

update models
set workspace_id = 1
where workspace_id is null;


alter table questions
    add column if not exists workspace_id bigint references workspaces;

update questions
set workspace_id = 1
where workspace_id is null;
