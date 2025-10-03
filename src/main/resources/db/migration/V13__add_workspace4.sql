insert into workspaces (id, name, created_at)
values (4, 'Workspace 4', NOW())
on conflict (id) do nothing;