CREATE TABLE message_dislikes (
    message_id bigint NOT NULL REFERENCES message (id),
    disliked_users_id bigint NOT NULL REFERENCES users (id),
    CONSTRAINT dismessage_id_fk FOREIGN KEY (message_id) REFERENCES message (id),
    CONSTRAINT disliked_users_id_fk FOREIGN KEY (disliked_users_id) REFERENCES users (id),
    PRIMARY KEY (message_id, disliked_users_id)
);