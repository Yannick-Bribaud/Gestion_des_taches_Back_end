package com.gestion.tache

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class TaskUserSpec extends Specification implements DomainUnitTest<TaskUser> {

    def setup() {
    }

    def cleanup() {
    }

    void "test TaskUser.username can not be nullable"(){

    }

    void "test TaskUser.firstName can not be nullable"(){
        expect :
        new TaskUser(firstName:'John').validate(['firstName'])
    }

    void "test TaskUser.firstName can not be blank"(){
        expect :
        new TaskUser(firstName:'John').validate(['firstName'])
    }

    void "test TaskUser.lastName can not be nullable"(){
        expect :
        new TaskUser(lastName:'Doe').validate(['lastName'])
    }

    void "test TaskUser.lastName can not be blank"(){
        expect :
        new TaskUser(lastName:'Doe').validate(['lastName'])
    }



}
