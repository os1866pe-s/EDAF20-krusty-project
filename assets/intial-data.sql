-- Populate the Customers table.

INSERT INTO Customers VALUES
('Bjudkakor AB','Ystad'),
('Finkakor AB','Helsingborg'),
('Gastkakor AB','Hassleholm'),
('Kaffebrod AB','Landskrona'),
('Kalaskakor AB','Trelleborg'),
('Partykakor AB','Kristianstad'),
('Skanekakor AB','Perstorp'),
('Smabrod AB','Malmo');

-- Populate the Cookies table.

INSERT INTO Cookies VALUES 
('Amneris'),
('Berliner'),
('Nut cookie'),
('Nut ring'),
('Tango'),
('Almond delight');

-- Populate the Ingredients table.

INSERT INTO Ingredients(name, amount, unit) VALUES
('Bread crumbs',500000,'g'),
('Butter',500000,'g'),
('Chocolate',500000,'g'),
('Chopped almonds',500000,'g'),
('Cinnamon',500000,'g'),
('Egg whites',500000,'ml'),
('Eggs',500000,'g'),
('Fine-ground nuts',500000,'g'),
('Flour',500000,'g'),
('Ground, roasted nuts',500000,'g'),
('Icing sugar',500000,'g'),
('Marzipan',500000,'g'),
('Potato starch',500000,'g'),
('Roasted, chopped nuts',500000,'g'),
('Sodium bicarbonate',500000,'g'),
('Sugar',500000,'g'),
('Vanilla',500000,'g'),
('Vanilla sugar',500000,'g'),
('Wheat flour',500000,'g');

-- Populate the IngredientsInCookies table.

INSERT INTO IngredientsInCookies VALUES
('Nut ring','Flour', 450),
('Nut ring','Butter',450),
('Nut ring','Icing sugar',190),
('Nut ring','Roasted, chopped nuts',225),
('Nut cookie','Fine-ground nuts',625),
('Nut cookie','Ground, roasted nuts',625),
('Nut cookie','Bread crumbs',125),
('Nut cookie','Sugar',375),
('Nut cookie','Egg whites',350),
('Nut cookie','Chocolate',50),
('Amneris','Marzipan',750),
('Amneris','Butter',250),
('Amneris','Eggs',250),
('Amneris','Potato starch',25),
('Amneris','Wheat flour',25),
('Tango','Butter',200),
('Tango','Sugar',250),
('Tango','Flour',300),
('Tango','Sodium bicarbonate',4),
('Tango','Vanilla',2),
('Almond delight','Butter ',400),
('Almond delight','Sugar',270),
('Almond delight','Chopped almonds',279),
('Almond delight','Flour',400),
('Almond delight','Cinnamon',10),
('Berliner','Flour',350),
('Berliner','Butter',250),
('Berliner','Icing sugar',100),
('Berliner','Eggs',50),
('Berliner','Vanilla sugar',5),
('Berliner','Chocolate',50);