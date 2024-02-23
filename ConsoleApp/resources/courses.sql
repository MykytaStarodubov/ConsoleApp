CREATE TABLE IF NOT EXISTS console_app_schema.courses
(
    course_id integer NOT NULL,
    course_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    course_description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT courses_pkey PRIMARY KEY (course_id, course_name, course_description)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS console_app_schema.courses
    OWNER to postgres;