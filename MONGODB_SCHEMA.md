# MongoDB Database Schema for TimeTable App

## Database: `timetable_db`

### Collection 1: `teachers`

**Document Structure:**
```json
{
  "_id": "ObjectId",
  "id": "UUID string",
  "name": "Teacher Name",
  "email": "teacher@dpu.ac.in",
  "password": "hashed_password",
  "department": "Computer Science",
  "uniqueCode": "ABC123",
  "active": true,
  "createdAt": "2024-01-01T00:00:00Z",
  "updatedAt": "2024-01-01T00:00:00Z"
}
```

**Indexes:**
- `email` (unique)
- `uniqueCode` (unique)
- `id` (unique)
- `active` (for filtering active teachers)

### Collection 2: `lectures`

**Document Structure:**
```json
{
  "_id": "ObjectId",
  "id": "UUID string",
  "name": "Data Structures and Algorithms",
  "room": "Classroom 409",
  "teacherName": "AK",
  "teacherCode": "ABC123",
  "startTime": "8:40",
  "endTime": "9:40",
  "dayOfWeek": "Monday",
  "active": true,
  "createdAt": "2024-01-01T00:00:00Z",
  "updatedAt": "2024-01-01T00:00:00Z"
}
```

**Indexes:**
- `teacherCode` (for quick lookup)
- `dayOfWeek` (for day-wise queries)
- `id` (unique)
- `active` (for filtering active lectures)

## Relationships

### Teacher → Lectures (One-to-Many)
- Each teacher can have multiple lectures
- Lectures reference teacher via `teacherCode`
- When teacher is deleted, all their lectures are soft deleted (marked as inactive)

### Student Access
- Students access lectures using teacher's `uniqueCode`
- No direct student collection needed (simple access model)

## Sample Data

### Teachers Collection
```json
[
  {
    "id": "t001",
    "name": "AK",
    "email": "ak@dpu.ac.in",
    "password": "password123",
    "department": "Computer Science",
    "uniqueCode": "AK",
    "active": true
  },
  {
    "id": "t002", 
    "name": "CS",
    "email": "cs@dpu.ac.in",
    "password": "password123",
    "department": "Computer Science",
    "uniqueCode": "CS",
    "active": true
  },
  {
    "id": "t003",
    "name": "IS",
    "email": "is@dpu.ac.in",
    "password": "password123",
    "department": "Information Systems",
    "uniqueCode": "IS",
    "active": true
  }
]
```

### Lectures Collection
```json
[
  {
    "id": "l001",
    "name": "DSA",
    "room": "Classroom 409",
    "teacherName": "AK",
    "teacherCode": "AK",
    "startTime": "8:40",
    "endTime": "9:40",
    "dayOfWeek": "Monday",
    "active": true
  },
  {
    "id": "l002",
    "name": "IOT",
    "room": "Classroom 409",
    "teacherName": "CS",
    "teacherCode": "CS",
    "startTime": "9:40",
    "endTime": "10:40",
    "dayOfWeek": "Monday",
    "active": true
  },
  {
    "id": "l003",
    "name": "STATISTICS",
    "room": "Classroom 414",
    "teacherName": "IS",
    "teacherCode": "IS",
    "startTime": "10:45",
    "endTime": "11:45",
    "dayOfWeek": "Monday",
    "active": true
  }
]
```

## API Endpoints

The app uses MongoDB Data API (REST API) for all operations:

### Base URL
```
https://data.mongodb-api.com/app/{app-id}/endpoint/data/v1/action/
```

### Common Operations

#### 1. Insert Document
```http
POST /insertOne
Content-Type: application/json
api-key: {your-api-key}

{
  "dataSource": "Main",
  "database": "timetable_db",
  "collection": "teachers",
  "document": {
    "id": "t001",
    "name": "AK",
    "email": "ak@dpu.ac.in",
    "password": "password123",
    "department": "Computer Science",
    "uniqueCode": "AK",
    "active": true
  }
}
```

#### 2. Find Documents
```http
POST /find
Content-Type: application/json
api-key: {your-api-key}

{
  "dataSource": "Main",
  "database": "timetable_db",
  "collection": "teachers",
  "filter": {
    "active": true
  }
}
```

#### 3. Update Document
```http
POST /updateOne
Content-Type: application/json
api-key: {your-api-key}

{
  "dataSource": "Main",
  "database": "timetable_db",
  "collection": "teachers",
  "filter": {
    "id": "t001"
  },
  "update": {
    "$set": {
      "name": "Updated Name",
      "active": false
    }
  }
}
```

## Data Validation

### Teacher Validation
- `name`: Required, string
- `email`: Required, valid email format, unique
- `password`: Required, string (min 6 characters)
- `department`: Required, string
- `uniqueCode`: Required, string (max 6 characters), unique
- `active`: Boolean, default true

### Lecture Validation
- `name`: Required, string
- `room`: Required, string
- `teacherName`: Required, string
- `teacherCode`: Required, string, must reference existing teacher
- `startTime`: Required, time format (HH:MM)
- `endTime`: Required, time format (HH:MM)
- `dayOfWeek`: Required, one of: Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
- `active`: Boolean, default true

## Security Considerations

1. **Password Hashing**: Passwords should be hashed before storage
2. **API Key Security**: API keys should be stored securely
3. **Input Validation**: All inputs should be validated before storage
4. **Access Control**: Implement proper access control mechanisms
5. **Data Encryption**: Sensitive data should be encrypted at rest

## Performance Optimization

1. **Indexing**: Proper indexes on frequently queried fields
2. **Pagination**: Implement pagination for large datasets
3. **Caching**: Cache frequently accessed data
4. **Connection Pooling**: Reuse database connections
5. **Query Optimization**: Use efficient queries and projections

## Backup and Recovery

1. **Regular Backups**: Set up automated backups
2. **Point-in-Time Recovery**: Enable point-in-time recovery
3. **Data Export**: Regular data exports for disaster recovery
4. **Monitoring**: Monitor database performance and health

## Migration Strategy

1. **Version Control**: Maintain schema versions
2. **Backward Compatibility**: Ensure backward compatibility during migrations
3. **Rollback Plan**: Have rollback procedures ready
4. **Testing**: Test migrations in staging environment first
