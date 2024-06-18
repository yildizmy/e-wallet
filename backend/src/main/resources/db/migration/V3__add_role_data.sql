INSERT INTO public.role (id, "type") VALUES (1, 'ROLE_USER');
INSERT INTO public.role (id, "type") VALUES (2, 'ROLE_ADMIN');

SELECT setval('role_seq', max(id)) FROM public.role;