CREATE TABLE IF NOT EXISTS console_app_schema.groups
(  
	
    group_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
	groups_id SERIAL PRIMARY KEY  
 )

TABLESPACE pg_default;

ALTER TABLE IF EXISTS console_app_schema.groups
    OWNER to postgres;