# Database Documentation

## Overview
This application now uses Room persistence library to store chat group data locally on the device.

## Database Structure

### Tables

1. **contacts**
   - `id` (Primary Key, Auto-generated)
   - `name` (Contact name)
   - `phone` (Phone number)

2. **groups**
   - `id` (Primary Key, Auto-generated) 
   - `group_name` (Group name)

3. **group_contact_join**
   - `id` (Primary Key, Auto-generated)
   - `group_id` (Foreign Key to groups table)
   - `contact_id` (Foreign Key to contacts table)

### Relationships
- Groups and Contacts have a many-to-many relationship through the `group_contact_join` table
- When a group or contact is deleted, related join records are automatically removed (CASCADE)

## Data Access Layer

### Repositories
- `ContactRepository`: Manages contact CRUD operations
- `GroupRepository`: Manages groups and group-member relationships

### DAOs
- `ContactDao`: Database access for contacts
- `GroupDao`: Database access for groups and relationships

## Features
- Real-time updates using LiveData
- Automatic database initialization with default contacts
- Persistent storage across app launches
- Repository pattern for clean architecture
- Async database operations to avoid blocking UI

## Usage
- App automatically creates default contacts on first launch
- Create groups by selecting contacts in SelectMembersActivity
- View all created groups in ContactListActivity
- All data persists between app sessions