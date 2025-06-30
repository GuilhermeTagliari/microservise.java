CREATE TABLE tb_order (
    id SERIAL PRIMARY KEY,
    order_date TIMESTAMP NOT NULL,
    customer_id BIGINT NOT NULL
);

CREATE TABLE tb_order_item (
    id SERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    price_at_purchase DOUBLE PRECISION NOT NULL,
    currency_at_purchase varchar(3) not null,
    order_id BIGINT,
    CONSTRAINT fk_order
        FOREIGN KEY(order_id) 
        REFERENCES tb_order(id)
        ON DELETE CASCADE
);
