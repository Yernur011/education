INSERT INTO sso.user_roles(user_id, role_id)
SELECT user_id, (SELECT role_id FROM sso.roles WHERE role_code = 'USER_SSO')
FROM sso.users
    ON CONFLICT DO NOTHING;