package com.gestion.tache.security

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class RoleController {

    static responseFormats = ['json']

    RoleService roleService
    List<String>listUserRole=new ArrayList<String>()

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond roleService.list(params), model:[roleCount: roleService.count()]
    }

    def list(){
        if(roleService.list(params)==null){
            notFound()
            return;
        }
        respond roleService.list(params)
    }

    @Secured(['ROLE_ADMIN'])
    def get_role_by_authority(String authority){
        Role role = Role.findByAuthority(authority)
        if(role!=null){
            respond role
        }
    }

    @Secured(['ROLE_ADMIN'])
    def getUsersRoles(){
        listUserRole.clear()
        def userList = User.getAll()
        for(User user in userList){
            listUserRole.add(user.getAuthorities().authority)
        }
        respond listUserRole
    }

    @Secured(['ROLE_ADMIN'])
    def show(Long id) {
        respond roleService.get(id)
    }

    def create() {
        respond new Role(params)
    }

    @Secured(['ROLE_ADMIN'])
    def save(Role role) {
        if (role == null) {
            notFound()
            return
        }
        try {
            roleService.save(role)
        } catch (ValidationException e) {
            respond role.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'role.label', default: 'Role'), role.id])
                redirect role
            }
            '*' { respond role, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond roleService.get(id)
    }

    def update(Role role) {
        if (role == null) {
            notFound()
            return
        }

        try {
            roleService.save(role)
        } catch (ValidationException e) {
            respond role.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'role.label', default: 'Role'), role.id])
                redirect role
            }
            '*'{ respond role, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        roleService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'role.label', default: 'Role'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
