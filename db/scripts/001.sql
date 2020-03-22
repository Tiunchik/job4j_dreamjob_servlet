CREATE TABLE IF NOT EXISTS USERTABLE (
                    userid integer primary key,
                    username varchar(100),
                    userlogin varchar(100) DEFAULT '',
                    useremail varchar(100) DEFAULT '',
                    usercrdate date);
