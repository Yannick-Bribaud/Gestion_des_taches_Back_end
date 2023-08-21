package com.gestion.tache

class Task implements Serializable {

    String nameTask
    Date dateTaskCreated
    Date dateTaskMade
    String descriptionTask
    boolean isActive = true

    static belongsTo = [owner: TaskUser, creator:TaskUser]

    static mapping = {
        id column: 'id'
        version true
        autoTimestamp false
        nameTask column: 'name_task'
        dateTaskCreated column: 'date_task_created'
        dateTaskMade column : 'date_task_made'
        descriptionTask column : 'description_task'
        owner lazy: false
        creator lazy : false
    }

    static constraints = {
        nameTask size: 5..50, unique: true
        dateTaskCreated nullable: false
        dateTaskMade nullable : false
        descriptionTask size: 5..150
        owner nullable: false
        creator nullable: false
    }

    @Override
    String toString() {
        return "${nameTask} ${descriptionTask} ${dateTaskCreated} ${owner}"
    }
}