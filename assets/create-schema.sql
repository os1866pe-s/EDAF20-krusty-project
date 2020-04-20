DROP TABLE IF EXISTS Customers;
DROP TABLE IF EXISTS Cookies;
DROP TABLE IF EXISTS IngredientsInCookies;
DROP TABLE IF EXISTS Ingredients;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Pallets;

CREATE TABLE Customers
(
    name TEXT,
    address TEXT,
    PRIMARY KEY (name)
);
CREATE TABLE Cookies
(
    name TEXT,
    PRIMARY KEY (name)
);
CREATE TABLE Ingredients
(
    name TEXT, 
    last_delivered_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_delivered_quantity INTEGER,
    amount INTGER,
    unit TEXT,
    PRIMARY KEY (name)
);
CREATE TABLE IngredientsInCookies
(
    ingredient_name TEXT,
    cookie_name TEXT,
    quantity INTEGER,
    PRIMARY KEY(ingredient_name, cookie_name),
    FOREIGN KEY(ingredient_name) REFERENCES Ingredients(name),
    FOREIGN KEY(cookie_name) REFERENCES Cookies(name)
);
CREATE TABLE Orders
(
    order_number INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    order_made_timestamp DATETIME DEFAULT (date('now')) NOT NULL,
    requested_delivery_timestamp,
    FOREIGN KEY(name) REFERENCES Customers(name)
);
CREATE TABLE Pallets
(
    pallet_number INTEGER PRIMARY KEY AUTOINCREMENT,
    cookie TEXT NOT NULL,
    order_number INTERGER,
    produced_timestamp DATETIME DEFAULT (date('now')) NOT NULL,
    delivered_timestamp TEXT,
    blocked TEXT NOT NULL CHECK(blocked IN ('yes','no')),
    FOREIGN KEY(cookie) REFERENCES Cookies(name),
    FOREIGN KEY(order_number) REFERENCES Orders(order_number)
);