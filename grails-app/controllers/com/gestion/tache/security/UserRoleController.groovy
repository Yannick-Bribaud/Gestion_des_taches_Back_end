package com.gestion.tache.security

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import org.grails.datastore.mapping.transactions.Transaction

import static org.springframework.http.HttpStatus.*

class UserRoleController {

    static responseFormats = ['json']
    UserRoleService userRoleService
    UserService userService
    List<User>allUserOwners=new ArrayList<User>();

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {}


    @Secured(['ROLE_ADMIN'])
    @Transactional
    def changeUserRole(UserRole userRole){
        if(userRole==null){
            notFound()
            return
        }

        if(UserRole.remove(userRole.getUser(),userRole.getRole())){
                respond NO_CONTENT
                return
        }
        respond NOT_FOUND
    }

    def list(){
        if(userRoleService.list(params)==null){
           notFound()
            return;
        }
        respond userRoleService.list(params)
    }

    def show(Long id) {
        respond userRoleService.get(id)
    }

    @Secured(['ROLE_CREATOR'])
    def listOwnersOnly(){
        allUserOwners.clear()
        List<User> allUsers = User.findAllByIsDeletedAndEnabled(false,true,params)
        Role role = Role.findByAuthority("ROLE_OWNER")

        for(User user in allUsers){
            if(UserRole.exists(user.getId(), role.getId())){
                UserRole result =  UserRole.get(user.getId(), role.getId())
                User owner = result.getUser()
                allUserOwners.add(owner)
            }
        }
        respond allUserOwners;
        allUsers.clear()
    }
    def create() {
        respond new UserRole(params)
    }
    @Secured(['ROLE_ADMIN'])
    def save(UserRole userRole) {
        if (userRole == null) {
            notFound()
            return
        }

        try {
            userRoleService.save(userRole)
        } catch (ValidationException e) {
            respond userRole.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'userRole.label', default: 'UserRole'), userRole.id])
                redirect userRole
            }
            '*' { respond userRole, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond userRoleService.get(id)
    }

    @Secured(['ROLE_ADMIN'])
    def update(UserRole userRole) {
        if (userRole == null) {
            notFound()
            return
        }

        try {
            userRoleService.save(userRole)
        } catch (ValidationException e) {
            respond userRole.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'userRole.label', default: 'UserRole'), userRole.id])
                redirect userRole
            }
            '*'{ respond userRole, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        userRoleService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'userRole.label', default: 'UserRole'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'userRole.label', default: 'UserRole'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
