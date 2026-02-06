CREATE TABLE IF NOT EXISTS categorias(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    categoria   VARCHAR(50) UNIQUE NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    imagen      VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS productos(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(50) UNIQUE NOT NULL,
    descripcion VARCHAR(200),
    precio      DECIMAL NOT NULL,
    stock       BOOLEAN NOT NULL,
    imagen      VARCHAR(100),
    categoria_id INT,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS users_security (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    username        VARCHAR(50) UNIQUE NOT NULL,
    email           VARCHAR(100) UNIQUE NOT NULL,
    password        VARCHAR(200) NOT NULL,
    administrador   BOOLEAN NOT NULL,
    usuario         BOOLEAN NOT NULL,
    invitado        BOOLEAN NOT NULL,
    activado        BOOLEAN NOT NULL
);

    CREATE TABLE IF NOT EXISTS pedidos(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    pedido          VARCHAR(300) NOT NULL,
    precio_total    DECIMAL NOT NULL,
    tel             VARCHAR(10) NOT NULL,
    direccion       VARCHAR(150) NOT NULL,
    pedido_date     DATE NOT NULL,
    user_id         INT,
    FOREIGN KEY (user_id) REFERENCES users_security(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS lineas_pedido(
    id_pedido       INT,
    id_producto     INT,
    cantidad        INT NOT NULL,
    precio          DECIMAL(5,2) NOT NULL,
    PRIMARY KEY (id_pedido, id_producto)
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id) ON DELETE SET NULL,
    FOREIGN KEY (id_producto) REFERENCES productos(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS mensajes(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    titulo      VARCHAR(30) NOT NULL,
    mensaje     VARCHAR(300) NOT NULL,
    email       VARCHAR(100) NOT NULL,
    post_date   DATE NOT NULL    
);

-- CREATE TABLE IF NOT EXISTS reviews(
--     id          INT AUTO_INCREMENT PRIMARY KEY,
--     review      VARCHAR(300) NOT NULL,
--     post_date   DATE NOT NULL,
--     user_id     INT,
--     FOREIGN KEY (user_id) REFERENCES users_security(id) ON DELETE SET NULL
-- )