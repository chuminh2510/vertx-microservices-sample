CREATE TABLE IF NOT EXISTS public.users
(
    id serial NOT NULL,
    age integer,
    email character varying(120) COLLATE pg_catalog."default",
    name character varying(80) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
    );

ALTER TABLE IF EXISTS public.users
    OWNER to "postgres";

INSERT INTO public.users( name, email, age)
VALUES ('User 1', 'user1@gmail', 20),
       ('User 2', 'user2@gmail', 30),
       ('User 3', 'user3@gmail', 30),
       ('User 4', 'user4@gmail', 50),
       ('User 5', 'user5@gmail', 40);
