package com.gestion.tache

import grails.gorm.transactions.Transactional
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

@Transactional
class TaskSpec extends Specification implements DomainUnitTest<Task> {

    void "test Task.nameTask length can not be less than 5 characters"(){
        expect :
        new Task(nameTask:'no less than 5 char').validate(['nameTask'])
    }

    void "test Task.nameTask length can not be more than 30 characters"(){
        expect :
        new Task(nameTask:'no more than 30 characters').validate(['nameTask'])
    }

    void "test Task.dateTaskCreated can not be nullable"(){
        expect :
        new Task(dateTaskCreated:'2023-07-28').validate(['dateTaskCreated'])
    }

    void "test Task.dateTaskCreated can not be blank"(){
        expect :
        new Task(dateTaskCreated:'2023-07-28').validate(['dateTaskCreated'])
    }

    void "test Task.dateTaskMade can not be nullable"(){
        expect :
        new Task(dateTaskMade:'2023-07-28').validate(['dateTaskMade'])
    }

    void "test Task.dateTaskMade can not be blank"(){
        expect :
        new Task(dateTaskMade:'2023-07-28').validate(['dateTaskMade'])
    }

    void "test Task.descriptionTask length can not be less than 5 characters"(){
        expect :
        new Task(descriptionTask:'not less than 5 char').validate(['descriptionTask'])
    }

    void "test Task.descriptionTask length can not be more than 150 characters"(){
        expect:
        new Task(descriptionTask:'No more than 150 char,' +
                'No more than 150 char,' +
                'No more than 150 char,' +
                'No more than 150 char,' +
                'No more than 150 char,' +
                'No more than 150 char.').validate(['descriptionTask'])
    }

    void "test default value of Task.isActive"(){
        expect: " we expect to get true"
        assert new Task().getIsActive()==true
    }

    void "test Task.owner can not be nullable"(){
        expect:"we expect to get validation"
         assert new Task(owner: new TaskUser(firstName:"John", lastName: "Doe")).validate(['owner'])
    }

    void "test Task.owner with nullable value"(){
        expect:"we expect to get no validation"
        assert new Task(owner: null).validate(['owner'])
    }

    void "test Task.creator can not be nullable"(){
        expect:"we expect to get validation"
        assert new Task(creator: new TaskUser(firstName:"John", lastName: "Doe")).validate(['creator'])
    }

    void "test Task.creator with nullable value"(){
        expect:"we expect to get no validation"
        assert new Task(creator: null).validate(['creator'])
    }

}
