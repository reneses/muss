INSERT INTO images (original_id, object_id, is_primary)
VALUES
    (1001, 1, TRUE),
    (1002, 1, FALSE);
INSERT INTO image_sizes (image_id, width, height, url, label)
VALUES
    (1, 200, 100, 'image1.ie_a', 'a'),
    (1, 300, 200, 'image1.ie_b', 'b'),
    (2, 300, 200, 'image2.ie_a', 'a'),
    (2, 300, 200, 'image2.ie_b', 'b');

INSERT INTO roles (original_id, name, display_name, `date`, url)
VALUES
    (1001, 'role1', 'Role 1', '2001', 'role1.ie'),
    (1002, 'role2', 'Role 2', '2002', 'role2.ie');
INSERT INTO participants (original_id, name, `date`, url)
VALUES
    (1001, 'participant1', '3001', 'participant1.ie'),
    (1002, 'participant2', '3002', 'participant2.ie');
INSERT INTO participations (object_id, role_id, participant_id)
VALUES
    (1, 1, 1),
    (1, 2, 2);
INSERT INTO ch_objects (original_id, title, credit_line, gallery_text, `date`, medium, description)
VALUES
    (101, 'object1', 'credit1', 'gallery1', '2001', 'medium1', 'description1'),
    (102, 'object2', 'credit2', 'gallery2', '2002', 'medium2', 'description2')
