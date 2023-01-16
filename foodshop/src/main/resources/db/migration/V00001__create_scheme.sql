-- схема базы данных
CREATE TABLE dishes (
                          id bigserial PRIMARY KEY,
                          title varchar(255) NOT NULL,
                          composition varchar(255) NOT NULL ,
                          description text,
                          price real NOT NULL ,
                          time_to_ready real NOT NULL
);

CREATE TABLE orders (
                          id bigserial PRIMARY KEY,
                          address varchar(255)  NOT NULL,
                          status boolean default false,
                          final_cost real NOT NULL
);


CREATE TABLE dishes_orders (
                                   dish_id bigint REFERENCES dishes(id),
                                   order_id bigint REFERENCES orders(id),
                                   PRIMARY KEY (dish_id, order_id)
);
