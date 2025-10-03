insert into workspaces (id, name, created_at)
values (5, 'Workspace 5', NOW())
on conflict (id) do nothing;