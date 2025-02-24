-- Insert default images
INSERT INTO base64images (id, base64image)
VALUES
    (1, 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA'),
    (2, 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEA'),
    (3, 'data:image/svg+xml;base64,PHN2ZyB4bWxu');


INSERT INTO course (id, title, description, base64images_id, created_by, created_date, last_updated_by, last_updated_date)
VALUES
    (1, 'Mastering Spring Boot', 'Deep dive into Spring Boot framework with practical examples.', 1, 'system', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP),
    (2, 'Go for Java Developers', 'Learn Go by leveraging your existing Java and Spring Boot knowledge.', 2, 'system', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP),
    (3, 'PostgreSQL Essentials', 'Master PostgreSQL database management for scalable applications.', 3, 'system', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP);
