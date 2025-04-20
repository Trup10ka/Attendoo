USE attendoo;

CREATE TABLE IF NOT EXISTS role
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tag
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    tag_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_status
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_department
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS user
(
    id                     INT PRIMARY KEY AUTO_INCREMENT,
    first_name             VARCHAR(255) NOT NULL,
    last_name              VARCHAR(255) NOT NULL,
    attendoo_username      VARCHAR(255) NOT NULL,
    attendoo_password      VARCHAR(255) NOT NULL,
    email                  VARCHAR(255) NOT NULL,
    phone_number           VARCHAR(255) NOT NULL,
    role_id                INT          NOT NULL,
    default_user_status_id INT          NOT NULL,
    user_department_id     INT          NOT NULL,

    FOREIGN KEY (role_id) REFERENCES role (id),
    FOREIGN KEY (default_user_status_id) REFERENCES user_status (id),
    FOREIGN KEY (user_department_id) REFERENCES user_department (id),

    CHECK (email LIKE '%@%')
);

CREATE TABLE IF NOT EXISTS user_department_mapping
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    user_id            INT NOT NULL,
    user_department_id INT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (user_department_id) REFERENCES user_department (id)
);

CREATE TABLE IF NOT EXISTS user_attendances
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    user_id        INT  NOT NULL,
    user_status_id INT  NOT NULL,
    start_date     DATE NOT NULL,
    end_date       DATE,

    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (user_status_id) REFERENCES user_status (id)
);

CREATE TABLE IF NOT EXISTS proposal
(
    id                      INT PRIMARY KEY AUTO_INCREMENT,
    name                    VARCHAR(255) NOT NULL,
    proposer_id             INT          NOT NULL,
    proposed_id             INT          NOT NULL,
    description             TEXT,
    created_at              DATETIME              DEFAULT CURRENT_TIMESTAMP NOT NULL,
    resolved_at             DATETIME,
    current_user_status_id  INT          NOT NULL,
    proposed_user_status_id INT          NOT NULL,


    FOREIGN KEY (proposer_id) REFERENCES user (id),
    FOREIGN KEY (proposed_id) REFERENCES user (id),
    FOREIGN KEY (current_user_status_id) REFERENCES user_status (id),
    FOREIGN KEY (proposed_user_status_id) REFERENCES user_status (id)
);

CREATE TABLE IF NOT EXISTS proposal_tag
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    proposal_id INT NOT NULL,
    tag_id      INT NOT NULL,

    FOREIGN KEY (proposal_id) REFERENCES proposal (id),
    FOREIGN KEY (tag_id) REFERENCES tag (id)
);
