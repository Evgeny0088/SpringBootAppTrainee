CREATE TABLE message_likes (
    message_id bigint NOT NULL REFERENCES message (id),
    liked_users_id bigint NOT NULL REFERENCES users (id),
    CONSTRAINT message_id_fk FOREIGN KEY (message_id) REFERENCES message (id),
    CONSTRAINT liked_users_id_fk FOREIGN KEY (liked_users_id) REFERENCES users (id),
    PRIMARY KEY (message_id, liked_users_id)
);