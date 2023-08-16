package com.gestion.tache

import grails.gorm.services.Service

@Service(TaskUser)
interface TaskUserService {

    TaskUser get(Serializable id)

    List<TaskUser> list(Map args)

    Long count()

    void delete(Serializable id)

    TaskUser save(TaskUser taskUser)



}