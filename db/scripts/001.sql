CREATE TABLE IF NOT EXISTS USERTABLE (
                    userid integer primary key,
                    username varchar(100),
                    userlogin varchar(100) DEFAULT '',
                    useremail varchar(100) DEFAULT '',
                    userimage varchar(255),
                    usercrdate timestamp,
                    usercity varchar(100) DEFAULT 'Saint-Peterburg',
                    usercountry varchar(100) DEFAULT 'Russia'
                    );
INSERT INTO USERTABLE (userid, username, userlogin) VALUES ('1', 'admin', 'admin');
