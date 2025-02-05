DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS step;
DROP TABLE IF EXISTS project_category;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS project;

CREATE TABLE project (
	project_id INT NOT NULL AUTO_INCREMENT,
	project_name VARCHAR(128) NOT NULL,
	estimated_hours DECIMAL(7,2),
	actual_hours DECIMAL(7,2),
	difficulty INT,
	notes TEXT,
	PRIMARY KEY (project_id)
);

CREATE TABLE category (
	category_id INT NOT NULL AUTO_INCREMENT,
	category_name VARCHAR(128) NOT NULL,
	PRIMARY KEY (category_id)
);

CREATE TABLE project_category (
	project_id INT NOT NULL,
	category_id INT NOT NULL,
	FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE,
	FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE CASCADE,
	UNIQUE KEY (project_id, category_id)
);

CREATE TABLE step (
	step_id INT NOT NULL AUTO_INCREMENT,
	project_id INT NOT NULL,
	step_text TEXT NOT NULL,
	step_order INT NOT NULL,
	PRIMARY KEY (step_id),
	FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE
);

CREATE TABLE material (
	material_id INT NOT NULL AUTO_INCREMENT,
	project_id INT NOT NULL,
	material_name VARCHAR(128) NOT NULL,
	num_required INT,
	cost DECIMAL(7,2),
	PRIMARY KEY (material_id),
	FOREIGN KEY (project_id) REFERENCES project (project_id)
);

INSERT INTO project (project_name, estimated_hours, actual_hours, difficulty, notes)
VALUES ('Build a bird house', '3', '3.5', '4', 'build a basic bird house');

INSERT INTO material (project_id, material_name, num_required, cost)
VALUES 
	(3, 'planks of wood', 4, 20.00),
	(3, 'glue', 1, 5),
	(3, 'hammer', 1, null),
	(3, 'nails', 20, 3.99);

INSERT INTO step (project_id, step_text, step_order)
VALUES
	(3, 'Cut planks of wood to size', 1),
	(3, 'Glue the sides of the house together', 2),
	(3, 'Secure the sides using hammer and nails', 3),
	(3, 'Let dry for at least 4 hours before hanging', 4);

INSERT INTO category (category_id, category_name)
VALUES
	(3, 'Woodworking'),
	(4, 'Crafts'),
	(5, 'Garden');

INSERT INTO project_category (project_id, category_id)
VALUES
	(3, 3),
	(3, 4),
	(3, 5);