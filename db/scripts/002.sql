CREATE TABLE IF NOT EXISTS USERROLLS
(
    userid_role  integer REFERENCES usertable (userid) ON DELETE CASCADE,
    userrole     varchar(10) CHECK ((userrole = 'user') OR (userrole = 'admin')),
    userpassword varchar(20),
    UNIQUE (userid_role)
);
INSERT INTO USERROLLS (userid_role, userrole, userpassword) VALUES ('1', 'admin', 'admin');