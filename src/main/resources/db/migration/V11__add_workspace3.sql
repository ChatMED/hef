insert into workspaces (id, name, created_at)
values (3, 'Workspace 3', NOW())
on conflict (id) do nothing;