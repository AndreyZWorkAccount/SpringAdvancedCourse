You can find Derby DB - in folder "MyDbTest", that DB already contains some values.
You need to change path (jdbc.url=) to DB in application.property file  on yours. (user&password is empty).
If you want generate Exception on web-page, you can set values(for userId, EventId, seat) like: 1,1,1 on Order page and press "Submit".
You can find files for upload in resource folder (auditorium.json, events.json)

Users for security test:
user 		password 		roles
user1		user		BOOKING_MANAGER
user2		user		REGISTERED_USER
user		user		REGISTERED_USER,BOOKING_MANAGER
test		user		REGISTERED_USER,BOOKING_MANAGER

@Transaction demonstration:
1. Go to Order page;
2. Create order with this parameters:
	user id: 2201
	event id: 1501
	seat: 15
3. press "submit"
code steps for creating Order (I chose this order only for demo):
	* create order in DB
	* decrease UserAccount
	* increase EventTicketPrice
4. you will get RuntimeException (because UserAccount info dosn't exist for user Test (id 2201))
5. Go to Orders page, and click Refresh button in Orders List part;
6. If transaction works, you will not see new Order in list.

**Spring core queries:**
**SQL instruction to create DB for that app:**

create table users(id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(40), email varchar(40), birthday date, password varchar(200), roles varchar(200));

create table events(id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(40), startdate TIMESTAMP,  enddate TIMESTAMP, price int, ticketPrice int,rating  varchar(20));

create table auditoriums(id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(40), seatscount int, vipseats varchar(200));

create table orders(id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), user_id int, event_id int, seats int, price int);

create table schedule(id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), event_id int, auditorium_id int);

create table DISCOUNTCOUNTER(id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), user_id int, name varchar(50), count int);

create table EVENTCOUNTER(id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), event_id int, name varchar(50), count int);

create table UserAccounts(id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), user_id int, account int);


**test instruction**
INSERT INTO EVENTCOUNTER (name, event_id, count) VALUES (‘name’,55,2);