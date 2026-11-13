CREATE TABLE user_contacts(
    user_id BIGINT NOT NULL,
    contact_id BIGINT NOT NULL,

    PRIMARY KEY (user_id, contact_id),

    CONSTRAINT fk_user_contacts_user
        FOREIGN KEY(user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_user_contacts_contact
        FOREIGN KEY (contact_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT ch_not_self_contant
        CHECK (user_id <> contact_is)
);

CREATE INDEX idx_user_contacts_contact_id
    ON user_contacts(contact_id)