CREATE TABLE IF NOT EXISTS console_app_schema.students
(
    student_id integer NOT NULL,
    group_id integer NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (student_id, last_name)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS console_app_schema.students
    OWNER to postgres;