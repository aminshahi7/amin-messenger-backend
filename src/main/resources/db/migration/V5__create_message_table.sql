
CREATE TABLE messages (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    content TEXT NOT NULL,

    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    conversation_id BIGINT NOT NULL,

    sender_id BIGINT NOT NULL,

    CONSTRAINT fk_message_conversation
        FOREIGN KEY (conversation_id)
        REFERENCES conversations(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_message_sender
        FOREIGN KEY (sender_id)
        REFERENCES users(id)
        ON DELETE RESTRICT,

    INDEX idx_message_conversation (conversation_id),
    INDEX idx_message_sender (sender_id)

);
