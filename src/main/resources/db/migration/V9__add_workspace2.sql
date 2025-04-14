insert into workspaces (id, name, created_at)
values (2, 'Workspace 2', NOW())
on conflict (id) do nothing;