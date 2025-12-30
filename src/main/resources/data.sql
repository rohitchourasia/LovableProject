-- Insert a single user for initialization/testing.
-- The password_hash and avatar_url fields are explicitly set to NULL.
INSERT INTO users (
    name,
    email,
    password_hash,
    created_at,
    updated_at,
    deleted_at
) VALUES (
    'Test User',
    'test.user@lovable.com',
    NULL, -- Setting password_hash to NULL
    NULL, -- Setting avatar_url to NULL
    -- Use the SQL function CURRENT_TIMESTAMP for the timestamp fields,
    -- as this SQL runs outside of Hibernate's management.
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    NULL
);