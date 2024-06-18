INSERT INTO public."type" (id, "name", description) VALUES (1, 'Transfer', 'Transfer type');
INSERT INTO public."type" (id, "name", description) VALUES (2, 'Payment', 'Payment type');
INSERT INTO public."type" (id, "name", description) VALUES (3, 'Shopping', 'Shopping type');

SELECT setval('type_seq', max(id)) FROM public."type";