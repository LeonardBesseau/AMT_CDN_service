INSERT INTO image(id, info)
VALUES (:id, 'default')
ON CONFLICT DO UPDATE SET id=:id;