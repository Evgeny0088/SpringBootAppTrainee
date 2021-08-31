CREATE TABLE subscriptions (
    channel_id bigint NOT NULL REFERENCES users (id),
    subscriber bigint NOT NULL REFERENCES users (id),
    CONSTRAINT channel_id_fk FOREIGN KEY (channel_id) REFERENCES users (id),
    CONSTRAINT subscriber_fk FOREIGN KEY (subscriber) REFERENCES users (id),
    PRIMARY KEY (channel_id, subscriber)
);