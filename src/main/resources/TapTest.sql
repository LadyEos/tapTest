CREATE TABLE subjects (
  id integer PRIMARY KEY auto_increment,
  name varchar(255),
  file_order integer,
  program_id integer null,
  created_at timestamp DEFAULT (now())
);

CREATE TABLE programs (
  id integer PRIMARY KEY auto_increment,
  name varchar(255),
  program_key varchar(255) UNIQUE,
  passing_score integer,
  created_at timestamp DEFAULT (now())
);

CREATE TABLE settings (
  id integer PRIMARY KEY auto_increment,
  global_passing_score integer,
  created_at timestamp DEFAULT (now())
);

ALTER TABLE subjects ADD FOREIGN KEY (program_id) REFERENCES programs (id);
