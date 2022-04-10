create table users (
    id bigint not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    phone_number varchar(12) not null,
    birth_date date not null
);

create table cars (
    id bigint not null,
    brand varchar(50) not null,
    model varchar(50) not null,
    color varchar(50) not null,
    production_year int not null,
    price double not null,
    is_available bool not null
);

create table requests (
    id bigint not null,
    user_id bigint not null,
    car_id bigint not null,
    request_time timestamp not null,
    foreign key (user_id) references users(id),
    foreign key (car_id) references cars(id)
);
