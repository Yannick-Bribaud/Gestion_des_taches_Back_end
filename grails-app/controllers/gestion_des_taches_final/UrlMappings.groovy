package gestion_des_taches_final

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        get "/api/$controller/list"(action: "list")
        get "/api/$controller/ownerlist"(action: "listOwnersOnly")
        get "/api/$controller/listOwners"(action: "getOwnersTasks")
        get "/api/$controller/listCreators"(action: "getCreatorTasks")
        get "/api/$controller/listRoleByUser"(action: "getUsersRoles")
        get "/api/$controller/authority/$authority(.$format)?"(action: "get_role_by_authority")
        get "/api/$controller/creator/$username(.$format)?"(action: "getUserByUsername")
        get "/api/$controller/select/$id(.$format)?"(action: "show")
        post "/api/$controller/create(.$format)?"(action: "save")
        post "/api/$controller/changeUserRole(.$format)?"(action: "changeUserRole")
        put "/api/$controller/update(.$format)?"(action: "update")
        delete "/api/$controller/delete/$id(.$format)?"(action: "delete")

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
