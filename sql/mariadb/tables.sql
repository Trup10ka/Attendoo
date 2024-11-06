CREATE TABLE IF NOT EXISTS role (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tags (
  id INT PRIMARY KEY AUTO_INCREMENT,
  tag_ame VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS employee_status (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS employee_department(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS employee(
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    attendoo_username VARCHAR(255) NOT NULL,
    attendoo_password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    role_id INT NOT NULL,
    default_worker_status_id INT NOT NULL,
    worker_department_id INT NOT NULL,

    FOREIGN KEY (role_id) REFERENCES role(id),
    FOREIGN KEY (default_worker_status_id) REFERENCES employee_status(id),
    FOREIGN KEY (worker_department_id) REFERENCES employee_department(id),

    CHECK (email LIKE '%@%')
);

CREATE TABLE IF NOT EXISTS proposal(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    attendoo_proposal_id INT NOT NULL,
    description TEXT,
    created_at DATETIME DEFAULT CURDATE(),
    resolved_at DATETIME,
    current_worker_status_id INT NOT NULL,
    proposed_worker_status_id INT NOT NULL,


    FOREIGN KEY (current_worker_status_id) REFERENCES employee_status(id),
    FOREIGN KEY (proposed_worker_status_id) REFERENCES employee_status(id)
);

CREATE TABLE IF NOT EXISTS proposal_tags(
    proposal_id INT NOT NULL,
    tag_id INT NOT NULL,

    FOREIGN KEY (proposal_id) REFERENCES proposal(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id)
);

-- Finish writing the SQL statements to create the lsat table status of employee_status
