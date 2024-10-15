--DROP TABLE IF EXISTS usuarios;
CREATE TABLE IF NOT EXISTS postgres.usuarios(
	id INTEGER NOT NULL UNIQUE,
	names VARCHAR (100) NOT NULL,
	email VARCHAR (100) NOT NULL UNIQUE,
	registation_date DATE,
	id_order INTEGER,
	PRIMARY KEY (id)
);