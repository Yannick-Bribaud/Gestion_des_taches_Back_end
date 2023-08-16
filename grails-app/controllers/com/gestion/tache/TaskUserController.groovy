package com.gestion.tache

import com.gestion.tache.security.User
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class TaskUserController {

    static responseFormats = ['json']
    List<User>listUsers=new ArrayList<User>();

    TaskUserService taskUserService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {}

    @Secured(['ROLE_CREATOR'])
    def getUserByUsername(String username){
        if(username ==null || username.isEmpty()){
            notFound()
            return
        }
        User user = User.findByUsername(username)
        respond user
    }

    def show(Long id) {
        respond taskUserService.get(id)
    }

    def create() {
        respond new TaskUser(params)
    }

    @Secured(['ROLE_ADMIN'])
    def save(TaskUser taskUser) {
        if (taskUser == null) {
            notFound()
            return
        }

        try {
            taskUserService.save(taskUser)
        } catch (ValidationException e) {
            respond taskUser.errors, view:'create'
            return
        }

         request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'taskUser.label', default: 'TaskUser'), taskUser.id])
                redirect taskUser
            }
            '*' { respond taskUser, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond taskUserService.get(id)
    }

    @Secured(['ROLE_ADMIN'])
    def update(TaskUser taskUser) {
        if (taskUser == null) {
            notFound()
            return
        }

        try {
            taskUserService.save(taskUser)
        } catch (ValidationException e) {
            respond taskUser.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'taskUser.label', default: 'TaskUser'), taskUser.id])
                redirect taskUser
            }
            '*'{ respond taskUser, [status: OK] }
        }
    }
    @Secured(['ROLE_ADMIN'])
    @Transactional
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }
        TaskUser userDeleted =TaskUser.get(id)
        userDeleted.setIsDeleted(true)
        userDeleted.save(flush: true)

        respond userDeleted
        //taskUserService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'taskUser.label', default: 'TaskUser'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'taskUser.label', default: 'TaskUser'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
