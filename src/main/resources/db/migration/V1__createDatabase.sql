create table user(
	uuid char(36) not null,
    username varchar(20) not null,
	password varchar(120) not null,
    full_name varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    phone_number varchar(11) not null,
    email varchar(50) not null,
    address varchar(255) not null,
    avatar varchar(255) not null,
    token varchar(255) ,
    token_creation_date timestamp,
    primary key(uuid),
    unique(email,username)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4;

create table component(
	uuid char(36) not null,
    name varchar(255) not null,
    code varchar(255) not null,
    icon varchar(255) not null,
    primary key(uuid)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4;

create table user_component(
	user_uuid char(36) not null,
    component_uuid char(36) not null,
    foreign key(user_uuid) references user(uuid),
    foreign key(component_uuid) references component(uuid)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4;

create table attachment(
	uuid char(36) not null,
    name varchar(255) not null,
    physical_path varchar(255) not null,
    content_type varchar(255) not null,
    title varchar(255) not null,
    parent_id char(36) not null,
    primary key(uuid),
    foreign key(parent_id) references user(uuid),
    foreign key(parent_id) references component(uuid)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4;

create table attachment_resource(
	uuid char(36) not null,
    name varchar(255) not null,
    physical_path varchar(255) not null,
    content_type varchar(255) not null,
    title varchar(255) not null,
    primary key(uuid)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4;
