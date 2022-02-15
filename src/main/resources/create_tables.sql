drop schema if exists adproject;
create database adproject;

use adproject;

create table admin
(
    id        int auto_increment
        primary key,
    email     varchar(255) null,
    name      varchar(255) null,
    password  varchar(255) null,
    user_name varchar(255) null
);

create table role
(
    role_id int auto_increment
        primary key,
    type    varchar(255) null
);

create table user
(
    user_id              int auto_increment
        primary key,
    date_of_birth        date         null,
    email                varchar(255) null,
    enabled              bit          not null,
    gender               varchar(255) null,
    height               double       null,
    imageurl             varchar(255) null,
    name                 varchar(255) null,
    password             varchar(255) null,
    profile_pic          varchar(64)  null,
    reset_password_token varchar(255) null,
    username             varchar(255) null,
    weight               double       null
);

create table goal
(
    id               int auto_increment
        primary key,
    end_date         date                                                      null,
    goal_description varchar(255)                                              null,
    start_date       date                                                      null,
    status           enum ('STARTED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED') null,
    target_count     int                                                       not null,
    total_meal_count int                                                       not null,
    author_user_id   int                                                       null,
    constraint FKrluy60d2dnu2y56mwkv9ugo6y
        foreign key (author_user_id) references user (user_id)
);

create table meal_entry
(
    id             int auto_increment
        primary key,
    description    varchar(255)                                  null,
    feeling        enum ('CRYING', 'PENSIVE', 'HAPPY', 'JOYFUL') null,
    flagged        bit                                           not null,
    imageurl       varchar(255)                                  null,
    time_stamp     datetime(6)                                   null,
    title          varchar(255)                                  null,
    track_score    int                                           not null,
    visibility     bit                                           not null,
    author_user_id int                                           null,
    goal_id        int                                           null,
    constraint FKjeefyqy8fgbt4lbslstfx9kby
        foreign key (author_user_id) references user (user_id),
    constraint FKq0r8klx8kt066npvwnpou3ucl
        foreign key (goal_id) references goal (id)
);

create table comment
(
    id             int auto_increment
        primary key,
    caption        varchar(255) null,
    author_user_id int          null,
    meal_entry_id  int          null,
    constraint FKjbon6u8vulx1dvp1ictjl1iko
        foreign key (meal_entry_id) references meal_entry (id),
    constraint FKnht0vru4ssmwa3mevinkfre72
        foreign key (author_user_id) references user (user_id)
);

create table friend_request
(
    id                int auto_increment
        primary key,
    status            varchar(255) null,
    recipient_user_id int          null,
    sender_user_id    int          null,
    constraint FK7og8hvhcvhosjnykd49mv5ymx
        foreign key (sender_user_id) references user (user_id),
    constraint FKs9lmkfpaxyfewgui9ffktdmh2
        foreign key (recipient_user_id) references user (user_id)
);

create table meal_entry_likers
(
    meal_entry_id int not null,
    user_id       int not null,
    constraint FK18e2qq2wdbr8amnkm9sv6r1b4
        foreign key (meal_entry_id) references meal_entry (id),
    constraint FK6m4g7795yxarkei4e9xnbbq8w
        foreign key (user_id) references user (user_id)
);

create table report
(
    id               int auto_increment
        primary key,
    comments         varchar(255)                 null,
    date_reported    date                         null,
    date_resolved    date                         null,
    reason           varchar(255)                 null,
    status           enum ('PENDING', 'RESOLVED') null,
    meal_entry_id    int                          null,
    reporter_user_id int                          null,
    resolved_by_id   int                          null,
    constraint FK4lbxnfqtsq3dex9wf809m1enq
        foreign key (meal_entry_id) references meal_entry (id),
    constraint FKk4bjnc6w18m3jo33giqukaaml
        foreign key (resolved_by_id) references admin (id),
    constraint FKn64sd5p2ql3abexm8ht1vhi80
        foreign key (reporter_user_id) references user (user_id)
);

create table session
(
    id           int auto_increment
        primary key,
    user_user_id int null,
    constraint FK5gxr4rnqn1eomj5pumnmef4tj
        foreign key (user_user_id) references user (user_id)
);

create table user_goals
(
    user_user_id int not null,
    goals_id     int not null,
    goals_order  int not null,
    primary key (user_user_id, goals_order),
    constraint UK_cwppyulvi3dqp8s5t2a25m3qc
        unique (goals_id),
    constraint FKg9y4ln1bj3b25av0qrnadv7te
        foreign key (user_user_id) references user (user_id),
    constraint FKj7d0fujvou4ixelbnksnpgdnk
        foreign key (goals_id) references goal (id)
);

create table users_roles
(
    user_id int not null,
    role_id int not null,
    primary key (user_id, role_id),
    constraint FKgd3iendaoyh04b95ykqise6qh
        foreign key (user_id) references user (user_id),
    constraint FKt4v0rrweyk393bdgt107vdx0x
        foreign key (role_id) references role (role_id)
);

