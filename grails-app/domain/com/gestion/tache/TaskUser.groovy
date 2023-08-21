package com.gestion.tache

import com.gestion.tache.security.User
import groovy.transform.ToString

class TaskUser extends User {

    String firstName
    String lastName

    static constraints = {
        firstName nullable: false,blank: false
        lastName nullable: false,blank:  false
    }
    static mapping = {
    }

    @Override
    String toString() {
        return "${firstName} ${lastName}"
    }
}
