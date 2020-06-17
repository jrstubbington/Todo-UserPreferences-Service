CREATE TABLE preferences (
    ID bigint NOT NULL AUTO_INCREMENT,
    UUID binary(16) NOT NULL,
    NAME varchar(20) NOT NULL,
--    DESCRIPTION varchar() NOT NULL,
    DEFAULT_VALUE boolean DEFAULT false,
    DATE_CREATED timestamp(0) DEFAULT NOW(0),
    PRIMARY KEY (ID),
    UNIQUE KEY UK_uuid (UUID),
    UNIQUE KEY UK_name (NAME)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE user_preferences (
    ID bigint NOT NULL AUTO_INCREMENT,
    USER_UUID binary(16) NOT NULL,
    PREFERENCE_UUID binary(16) NOT NULL,
    USER_VALUE boolean DEFAULT false,
    DATE_CREATED timestamp(0) DEFAULT NOW(0),
    PRIMARY KEY (ID),
    UNIQUE KEY UK_preferenceUnique (USER_UUID, PREFERENCE_UUID)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

ALTER TABLE user_preferences ADD FOREIGN KEY (PREFERENCE_UUID) REFERENCES preferences(UUID);