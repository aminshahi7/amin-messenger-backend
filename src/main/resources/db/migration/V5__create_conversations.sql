CREATE TABLE conversations(
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    contact_id BIGINT NOT NULL,

    PRIMARY KEY (user_id, contact_id),

    CONSTRAINT fk_user_conversations_user
        FOREIGN KEY(user_id)
        REFERENCES users(id)
        ON DELETE RESTRICT,

    CONSTRAINT fk_user_conversations_contact
        FOREIGN KEY (contact_id)
        REFERENCES users(id)
        ON DELETE RESTRICT,
);

CREATE INDEX idx_user_conversations_contact_id
    ON conversations(contact_id)