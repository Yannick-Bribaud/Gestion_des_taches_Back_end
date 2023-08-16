package com.gestion.tache

import com.gestion.tache.security.User
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import org.hibernate.exception.ConstraintViolationException

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

class TaskController {

    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    TaskService taskService
    List<TaskUser>listOwners=new ArrayList<TaskUser>()
    List<TaskUser>listCreators=new ArrayList<TaskUser>()
    List<Task>listTask = new ArrayList<Task>()

    def index(Integer max) {}

    @Secured(['ROLE_CREATOR','ROLE_OWNER'])
    def getOwnersTasks(){
        listOwners.clear()
        def listObj =  Task.findAllByIsActive(true)
        for(Task task in listObj) {
            if(!task.getOwner().getIsDeleted()){
                listOwners.add(task.getOwner())
            }
        }
        respond listOwners;
    }

    @Secured(['ROLE_CREATOR','ROLE_OWNER'])
    def getCreatorTasks(){
        listCreators.clear()
        def listObj =  Task.findAllByIsActive(true)
        for(Task task in listObj){
            if(!task.getOwner().getIsDeleted()){
                listCreators.add(task.getCreator())
            }
        }
        respond listCreators;
    }

    @Secured(['ROLE_CREATOR','ROLE_OWNER'])
    def list(){
        listTask.clear()
        if(taskService.list(params)==null){
            notFound()
            return;
        }
            def tasks = Task.findAllByIsActive(true, params)
            for(Task task in tasks){
                if(!task.getOwner().getIsDeleted()){
                    listTask.add(task)
                }
            }
         respond listTask,model: [taskCount : taskService.count()];
      }


        def show(Long id) {
            respond taskService.get(id)
        }

        def create() {
            respond new Task(params)
        }

        @Secured(['ROLE_CREATOR'])
        def save(Task task) {
            if (task == null) {
                notFound()
                return
            }

            try {
                taskService.save(task)
            } catch (ValidationException e) {
                respond task.errors, view:'create'
                return
            }
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'task.label', default: 'Task'), task.id])
                    redirect task
                }
                '*' { respond task, [status: CREATED] }
            }
        }

    def edit(Long id) {
        respond taskService.get(id)
    }

    @Secured(['ROLE_CREATOR'])
    def update(Task task) {
        if (task == null) {
            notFound()
            return
        }

        try {
            taskService.save(task)
        } catch (ValidationException e) {
            respond task.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'task.label', default: 'Task'), task.id])
                redirect task
            }
            '*'{ respond task, [status: OK] }
        }
    }
    @Secured(['ROLE_CREATOR'])
    @Transactional
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }
        Task task=Task.get(id)
        task.setIsActive(false)
        task.save(flush: true)

        respond task

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'task.label', default: 'Task'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'task.label', default: 'Task'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
