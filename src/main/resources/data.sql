INSERT INTO categorias (id, categoria, descripcion, imagen) VALUES (1, 'ice_cream', 'Es un alimento congelado que por lo general se hace de productos lácteos tales como leche o crema.', './ice_creams.png');
INSERT INTO categorias (id, categoria, descripcion, imagen) VALUES (2, 'smoothies', 'Es una bebida cremosa no alcohólica preparada a base de trozos y zumos de fruta, concentrados o congelados, mezclados tradicionalmente con productos lácteos, hielo o helado.', './smoothies.png');
INSERT INTO categorias (id, categoria, descripcion, imagen) VALUES (3, 'milkshakes', 'El milkshake se basa en helado y leche, mientras que el batido se centra en frutas, verduras y líquidos más ligeros como el yogur o la leche vegetal.', './milkshakes.png');

ALTER TABLE categorias ALTER COLUMN id RESTART WITH (SELECT COALESCE(MAX(id), 0) + 1 FROM categorias);


INSERT INTO productos (id, nombre, descripcion, precio, stock, imagen, categoria_id) VALUES (1, '1-ice_cream', 'descripcion', 4.99, true, './1-ice_cream.png', 1);
INSERT INTO productos (id, nombre, descripcion, precio, stock, imagen, categoria_id) VALUES (2, '2-ice_cream', 'descripcion', 3.99, true, './2-ice_cream.png', 1);
INSERT INTO productos (id, nombre, descripcion, precio, stock, imagen, categoria_id) VALUES (3, '3-ice_cream', 'descripcion', 2.99, false, './3-ice_cream.png', 1);

INSERT INTO productos (id, nombre, descripcion, precio, stock, imagen, categoria_id) VALUES (4, '1-smoothie', 'descripcion', 4.59, true, './1-smoothie.png', 2);
INSERT INTO productos (id, nombre, descripcion, precio, stock, imagen, categoria_id) VALUES (5, '2-smoothie', 'descripcion', 3.59, false, './2-smoothie.png', 2);
INSERT INTO productos (id, nombre, descripcion, precio, stock, imagen, categoria_id) VALUES (6, '3-smoothie', 'descripcion', 2.59, true, './3-smoothie.png', 2);

INSERT INTO productos (id, nombre, descripcion, precio, stock, imagen, categoria_id) VALUES (7, '1-milkshake', 'descripcion', 4.00, false, './1-milkshake.png', 3);
INSERT INTO productos (id, nombre, descripcion, precio, stock, imagen, categoria_id) VALUES (8, '2-milkshake', 'descripcion', 3.00, true, './2-milkshake.png', 3);
INSERT INTO productos (id, nombre, descripcion, precio, stock, imagen, categoria_id) VALUES (9, '3-milkshake', 'descripcion', 2.00, false, './3-milkshake.png', 3);

ALTER TABLE productos ALTER COLUMN id RESTART WITH (SELECT COALESCE(MAX(id), 0) + 1 FROM productos);


INSERT INTO users_security (id, username, email, password, administrador, usuario, invitado, activado) VALUES (1,'reis', 'reis@balmis.com', '$2a$10$fzcGgF.8xODz7ptkmZC.OeX1Kj5GDI//FhW2sG0vlshW6ZAKJky0e', true,  true,  false, true);      -- password: 5678
INSERT INTO users_security (id, username, email, password, administrador, usuario, invitado, activado) VALUES (2,'vadim', 'vadim@balmis.com', '$2a$10$ayw3FCBIkupFt5n9lrmJQe9XZMJhZiNCjaoOkXo/Ba0KZgymO01ce', false,  true,  false, true);   -- password: 1234
INSERT INTO users_security (id, username, email, password, administrador, usuario, invitado, activado) VALUES (3,'admin', 'admin@balmis.com', '$2a$10$fzcGgF.8xODz7ptkmZC.OeX1Kj5GDI//FhW2sG0vlshW6ZAKJky0e', true,  true,  false, true);    -- password: 5678
INSERT INTO users_security (id, username, email, password, administrador, usuario, invitado, activado) VALUES (4,'user',  'user@balmis.com',  '$2a$10$ayw3FCBIkupFt5n9lrmJQe9XZMJhZiNCjaoOkXo/Ba0KZgymO01ce', false, true,  false, true);    -- password: 1234

ALTER TABLE users_security ALTER COLUMN id RESTART WITH (SELECT COALESCE(MAX(id), 0) + 1 FROM users_security);


INSERT INTO pedidos (id, pedido, precio_total, tel, direccion, pedido_date, user_id) VALUES (1, '1-ice_cream x 3-ice_cream', 12.97, '123456789', 'Mi direccion', '2015-12-01', 1);
INSERT INTO pedidos (id, pedido, precio_total, tel, direccion, pedido_date, user_id) VALUES (2, '1-smoothie x 2-milkshake', 43.77, '123456789', 'Mi direccion', '2015-12-01', 2);
INSERT INTO pedidos (id, pedido, precio_total, tel, direccion, pedido_date, user_id) VALUES (3, '2-milkshake x 3-ice_cream', 5.99, '123456789', 'Mi direccion', '2015-12-01', 3);
INSERT INTO pedidos (id, pedido, precio_total, tel, direccion, pedido_date, user_id) VALUES (4, '1-ice_cream x 3-ice_cream', 12.97, '123456789', 'Mi direccion', '2015-12-01', 1);
INSERT INTO pedidos (id, pedido, precio_total, tel, direccion, pedido_date, user_id) VALUES (5, '1-smoothie x 2-milkshake', 43.77, '123456789', 'Mi direccion', '2015-12-01', 2);
INSERT INTO pedidos (id, pedido, precio_total, tel, direccion, pedido_date, user_id) VALUES (6, '2-milkshake x 3-ice_cream', 5.99, '123456789', 'Mi direccion', '2015-12-01', 3);

ALTER TABLE pedidos ALTER COLUMN id RESTART WITH (SELECT COALESCE(MAX(id), 0) + 1 FROM pedidos);

INSERT INTO lineas_pedido (id_pedido, id_producto, cantidad, precio) VALUES (1, 1, 2, 4.99);
INSERT INTO lineas_pedido (id_pedido, id_producto, cantidad, precio) VALUES (1, 3, 1, 2.99);

INSERT INTO lineas_pedido (id_pedido, id_producto, cantidad, precio) VALUES (2, 4, 3, 4.59);
INSERT INTO lineas_pedido (id_pedido, id_producto, cantidad, precio) VALUES (2, 8, 10, 3.00);

INSERT INTO lineas_pedido (id_pedido, id_producto, cantidad, precio) VALUES (3, 8, 1, 3.00);
INSERT INTO lineas_pedido (id_pedido, id_producto, cantidad, precio) VALUES (3, 3, 1, 2.99);

INSERT INTO lineas_pedido (id_pedido, id_producto, cantidad, precio) VALUES (4, 1, 2, 4.99);
INSERT INTO lineas_pedido (id_pedido, id_producto, cantidad, precio) VALUES (4, 3, 1, 2.99);
INSERT INTO lineas_pedido (id_pedido, id_producto, cantidad, precio) VALUES (4, 2, 2, 4.99);
INSERT INTO lineas_pedido (id_pedido, id_producto, cantidad, precio) VALUES (4, 4, 1, 2.99);

INSERT INTO lineas_pedido (id_pedido, id_producto, cantidad, precio) VALUES (5, 4, 3, 4.59);

INSERT INTO lineas_pedido (id_pedido, id_producto, cantidad, precio) VALUES (6, 1, 1, 3.00);
INSERT INTO lineas_pedido (id_pedido, id_producto, cantidad, precio) VALUES (6, 3, 1, 2.99);

-- ALTER TABLE lineas_pedido ALTER COLUMN id RESTART WITH (SELECT COALESCE(MAX(id), 0) + 1 FROM lineas_pedido);


INSERT INTO mensajes (id, titulo, mensaje, email, post_date) VALUES (1, '1-Titulo', 'Mi1 Mensaje', 'example@gmail.com', '2025-11-01');
INSERT INTO mensajes (id, titulo, mensaje, email, post_date) VALUES (2, '2-Titulo', 'Mi2 Mensaje', 'example@gmail.com', '2025-11-01');
INSERT INTO mensajes (id, titulo, mensaje, email, post_date) VALUES (3, '3-Titulo', 'Mi3 Mensaje', 'example@gmail.com', '2025-11-01');

ALTER TABLE mensajes ALTER COLUMN id RESTART WITH (SELECT COALESCE(MAX(id), 0) + 1 FROM mensajes);


INSERT INTO reviews (id, review, review_date, user_id) VALUES (1, 'This is very good', '2025-11-01', 1);
INSERT INTO reviews (id, review, review_date, user_id) VALUES (2, 'Indeed it is', '2025-11-01', 2);
INSERT INTO reviews (id, review, review_date, user_id) VALUES (3, 'Could have been better', '2025-11-01', 3);

ALTER TABLE reviews ALTER COLUMN id RESTART WITH (SELECT COALESCE(MAX(id), 0) + 1 FROM reviews);