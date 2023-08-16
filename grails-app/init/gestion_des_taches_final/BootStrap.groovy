package gestion_des_taches_final

import com.gestion.tache.Task
import com.gestion.tache.TaskUser
import com.gestion.tache.security.Role
import com.gestion.tache.security.User
import com.gestion.tache.security.UserRole
import com.gestion.tache.security.UserRoleService
import grails.gorm.transactions.Transactional

class BootStrap {

    def init = { servletContext ->
        populateUserAndRole()
    }

    @Transactional
    def populateUserAndRole(){

        Role undefined = new Role(authority: "ROLE_UNDEFINED").save(flush:true)
        Role adminRole = new Role(authority: "ROLE_ADMIN").save(flush: true)
        Role creatorRole = new Role(authority: "ROLE_CREATOR").save(flush: true)
        Role ownerRole = new Role(authority: "ROLE_OWNER").save(flush: true)

        User admin = new User(username: "admin",password: "admin").save(flush: true)
        TaskUser owner = new TaskUser(username: "oscar",password: "facile",firstName: "oscar",lastName: "wild").save(flush:true)
        TaskUser creator = new TaskUser(username: "max",password: "sensoft",firstName: "max",lastName: "well").save(flush:true)
        TaskUser owners = new TaskUser(username: "JohnDoe",password: "passer123",firstName: "John",lastName: "Doe").save(flush:true)

        UserRole.create(admin,adminRole)
        UserRole.create(owner,ownerRole)
        UserRole.create(creator,creatorRole)
        UserRole.create(owners,ownerRole)

        UserRole.withSession {
            it.flush()
            it.clear()
        }

        Task task_1 = new Task(nameTask: "Gestion des bibliothèque",dateTaskCreated: new Date(), dateTaskMade: new Date(), descriptionTask: "mise en place d'une plateforme de gestion de livres electroniques ",owner : owner,creator: creator).save(flush : true)
        Task task_2 = new Task(nameTask: "Formation sur un module grails",dateTaskCreated: new Date(), dateTaskMade: new Date(), descriptionTask:"Documentation sur le GORM ",owner :owner, creator: creator).save(flush : true)
        Task task_3 = new Task(nameTask: "Groovy ",dateTaskCreated: new Date(), dateTaskMade: new Date(), descriptionTask: "Apprendre les bases du langage groovy ",owner : owner, creator: creator).save(flush : true)
    }


    def destroy = {}

}
