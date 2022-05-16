CREATE SCHEMA [18118010];
GO

create table [18118010].log_18118010
(
    id             int identity
        constraint log_18118010_pk
            primary key,
    table_name     nvarchar(255) not null,
    operation_type nvarchar(255) not null,
    operation_time datetime2    not null
)
go

create unique index log_18118010_id_uindex
    on [18118010].log_18118010 (id)
go


create table [18118010].locations
(
    id                  nvarchar(255) not null
        primary key,
    created_on_18118010 datetime2    not null,
    updated_on_18118010 datetime2    not null,
    address             nvarchar(255) not null,
    city                nvarchar(255) not null,
    country             nvarchar(255) not null,
    latitude            float        not null,
    longitude           float        not null
)
go

CREATE TRIGGER [18118010].TR_LOCATIONS_LOG
    ON [18118010].locations
    AFTER INSERT, UPDATE
AS
BEGIN
    DECLARE @Operation as nvarchar(255);
    SET @Operation = (CASE WHEN EXISTS(SELECT * FROM INSERTED)
                         AND EXISTS(SELECT * FROM DELETED)
                        THEN 'UPDATE'
                        WHEN EXISTS(SELECT * FROM INSERTED)
                        THEN 'INSERT'
                    END)

    INSERT INTO [18118010].log_18118010 (table_name, operation_type, operation_time) VALUES ('locations', @Operation, GETDATE())
END;
go


create table [18118010].photos
(
    id                  nvarchar(255) not null
        primary key,
    created_on_18118010 datetime2    not null,
    updated_on_18118010 datetime2    not null,
    is_assigned         bit          not null,
    is_uploaded         bit          not null,
    url                 nvarchar(255) not null
)
go

CREATE TRIGGER [18118010].TR_PHOTOS_LOG
    ON [18118010].photos
    AFTER INSERT, UPDATE
AS
BEGIN
    DECLARE @Operation as nvarchar(255);
    SET @Operation = (CASE WHEN EXISTS(SELECT * FROM INSERTED)
                         AND EXISTS(SELECT * FROM DELETED)
                        THEN 'UPDATE'
                        WHEN EXISTS(SELECT * FROM INSERTED)
                        THEN 'INSERT'
                    END)

    INSERT INTO [18118010].log_18118010 (table_name, operation_type, operation_time) VALUES ('photos', @Operation, GETDATE())
END;
go

create table [18118010].roles
(
    id                  nvarchar(255) not null
        primary key,
    created_on_18118010 datetime2    not null,
    updated_on_18118010 datetime2    not null,
    name                nvarchar(255)
)
go

CREATE TRIGGER [18118010].TR_ROLES_LOG
    ON [18118010].roles
    AFTER INSERT, UPDATE
AS
BEGIN
    DECLARE @Operation as nvarchar(255);
    SET @Operation = (CASE WHEN EXISTS(SELECT * FROM INSERTED)
                         AND EXISTS(SELECT * FROM DELETED)
                        THEN 'UPDATE'
                        WHEN EXISTS(SELECT * FROM INSERTED)
                        THEN 'INSERT'
                    END)

    INSERT INTO [18118010].log_18118010 (table_name, operation_type, operation_time) VALUES ('roles', @Operation, GETDATE())
END;
go

create table [18118010].users
(
    id                  nvarchar(255) not null
        primary key,
    created_on_18118010 datetime2    not null,
    updated_on_18118010 datetime2    not null,
    email               nvarchar(255) not null
        constraint UK_6dotkott2kjsp8vw4d0m25fb7
            unique,
    first_name          nvarchar(255) not null,
    last_name           nvarchar(255) not null,
    password            nvarchar(255) not null,
    phone_number        nvarchar(255) not null
        constraint UK_9q63snka3mdh91as4io72espi
            unique
)
go

create table [18118010].cars
(
    id                  nvarchar(255)   not null
        primary key,
    created_on_18118010 datetime2      not null,
    updated_on_18118010 datetime2      not null,
    make                nvarchar(255)   not null,
    mileage             bigint         not null,
    model               nvarchar(255)   not null,
    price_per_day       numeric(19, 2) not null,
    status              nvarchar(255),
    transmission        nvarchar(255),
    year                int            not null,
    location_id         nvarchar(255)
        constraint FKotatcltd9hda79r9y01u1y1es
            references [18118010].locations,
    owner_id            nvarchar(255)
        constraint FKm5ibu05fg8g81fo6491puswuu
            references [18118010].users
)
go

CREATE TRIGGER [18118010].TR_CARS_LOG
    ON [18118010].cars
    AFTER INSERT, UPDATE
AS
BEGIN
    DECLARE @Operation as nvarchar(255);
    SET @Operation = (CASE WHEN EXISTS(SELECT * FROM INSERTED)
                         AND EXISTS(SELECT * FROM DELETED)
                        THEN 'UPDATE'
                        WHEN EXISTS(SELECT * FROM INSERTED)
                        THEN 'INSERT'
                    END)

    INSERT INTO [18118010].log_18118010 (table_name, operation_type, operation_time) VALUES ('cars', @Operation, GETDATE())
END;
go

create table [18118010].cars_photos
(
    car_id    nvarchar(255) not null
        constraint FKi7psu8eq35e7vk3n1ehate887
            references [18118010].cars,
    photos_id nvarchar(255) not null
        constraint UK_l24px4c0rc8os3koiqudisr03
            unique
        constraint FK7o87hcmr6y2renk0jlf5qfqj6
            references [18118010].photos
)
go

CREATE TRIGGER [18118010].TR_CARS_PHOTOS_LOG
    ON [18118010].cars_photos
    AFTER INSERT, UPDATE
AS
BEGIN
    DECLARE @Operation as nvarchar(255);
    SET @Operation = (CASE WHEN EXISTS(SELECT * FROM INSERTED)
                         AND EXISTS(SELECT * FROM DELETED)
                        THEN 'UPDATE'
                        WHEN EXISTS(SELECT * FROM INSERTED)
                        THEN 'INSERT'
                    END)

    INSERT INTO [18118010].log_18118010 (table_name, operation_type, operation_time) VALUES ('cars_photos', @Operation, GETDATE())
END;
go

create table [18118010].rentals
(
    id                  nvarchar(255)   not null
        primary key,
    created_on_18118010 datetime2      not null,
    updated_on_18118010 datetime2      not null,
    rented_from         date           not null,
    rented_to           date           not null,
    status              nvarchar(255),
    renter_id           nvarchar(255)
        constraint FKifio6cht3fecproeq439t9mgl
            references [18118010].users,
    days                bigint         not null,
    price_per_day       numeric(19, 2) not null,
    total_price         numeric(19, 2) not null,
    car_id              nvarchar(255)
        constraint FKb3vpbdnk78p1epicm7a7urvfh
            references [18118010].cars
)
go

CREATE TRIGGER [18118010].TR_RENTALS_LOG
    ON [18118010].rentals
    AFTER INSERT, UPDATE
AS
BEGIN
    DECLARE @Operation as nvarchar(255);
    SET @Operation = (CASE WHEN EXISTS(SELECT * FROM INSERTED)
                         AND EXISTS(SELECT * FROM DELETED)
                        THEN 'UPDATE'
                        WHEN EXISTS(SELECT * FROM INSERTED)
                        THEN 'INSERT'
                    END)

    INSERT INTO [18118010].log_18118010 (table_name, operation_type, operation_time) VALUES ('rentals', @Operation, GETDATE())
END;
go

CREATE TRIGGER [18118010].TR_USERS_LOG
    ON [18118010].users
    AFTER INSERT, UPDATE
AS
BEGIN
    DECLARE @Operation as nvarchar(255);
    SET @Operation = (CASE WHEN EXISTS(SELECT * FROM INSERTED)
                         AND EXISTS(SELECT * FROM DELETED)
                        THEN 'UPDATE'  -- Set Action to Updated.
                        WHEN EXISTS(SELECT * FROM INSERTED)
                        THEN 'INSERT'  -- Set Action to Insert.
                    END)

    INSERT INTO [18118010].log_18118010 (table_name, operation_type, operation_time) VALUES ('users', @Operation, GETDATE())
END;
go

create table [18118010].users_roles
(
    user_id  nvarchar(255) not null
        constraint FK2o0jvgh89lemvvo17cbqvdxaa
            references [18118010].users,
    roles_id nvarchar(255) not null
        constraint FKa62j07k5mhgifpp955h37ponj
            references [18118010].roles,
    primary key (user_id, roles_id)
)
go

CREATE TRIGGER [18118010].TR_USERS_ROLES_LOG
    ON [18118010].users_roles
    AFTER INSERT, UPDATE
AS
BEGIN
    DECLARE @Operation as nvarchar(255);
    SET @Operation = (CASE WHEN EXISTS(SELECT * FROM INSERTED)
                         AND EXISTS(SELECT * FROM DELETED)
                        THEN 'UPDATE'
                        WHEN EXISTS(SELECT * FROM INSERTED)
                        THEN 'INSERT'
                    END)

    INSERT INTO [18118010].log_18118010 (table_name, operation_type, operation_time) VALUES ('users_roles', @Operation, GETDATE())
END;
go

INSERT INTO [18118010].roles (id, created_on_18118010, updated_on_18118010, name)
VALUES  ('0a195582-3052-42bb-ae99-71b1fcd07357', '2022-05-06 19:49:07.4054440', '2022-05-06 19:49:07.4058070', 'ROLE_ADMIN'),
        ('39b60592-f31d-4a0f-86bf-b80f9ced986f', '2022-05-06 19:49:07.4106850', '2022-05-06 19:49:07.4107020', 'ROLE_MODERATOR'),
        ('e97e23e5-4c23-4d5f-a7cd-2658ac3a2114', '2022-05-06 19:49:07.4112740', '2022-05-06 19:49:07.4112870', 'ROLE_USER');
go
