INSERT INTO image(id, info)
VALUES (:id, 'default')
ON CONFLICT (info) DO UPDATE SET id=:id;