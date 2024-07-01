INSERT INTO customers VALUES (1005, 'Tim Zhang');
COMMIT;

UPDATE customers set name = 'Maggie Luo' where id = 1005;
COMMIT;

DELETE FROM customers WHERE id = 1005;
COMMIT;
