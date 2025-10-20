CREATE TABLE car (
    car_brand VARCHAR(255),
    car_model VARCHAR(255),
    price BIGINT,
    PRIMARY KEY(car_brand, car_model)
);