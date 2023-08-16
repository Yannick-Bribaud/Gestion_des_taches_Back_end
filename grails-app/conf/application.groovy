

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.gestion.tache.security.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.gestion.tache.security.UserRole'
grails.plugin.springsecurity.authority.className = 'com.gestion.tache.security.Role'

grails.plugin.springsecurity.rest.login.active=true
grails.plugin.springsecurity.rest.login.endpointUrl = '/oauth/login'
grails.plugin.springsecurity.rest.login.useJsonCredentials=true
grails.plugin.springsecurity.useSecurityEventListener = true
grails.plugin.springsecurity.rest.login.usernamePropertyName='username'
grails.plugin.springsecurity.rest.login.passwordPropertyName='password'
grails.plugin.springsecurity.rest.login.failureStatusCode=403
grails.plugin.springsecurity.useSecurityEventListener = true

grails.plugin.springsecurity.rest.token.storage.useGorm = true
grails.plugin.springsecurity.rest.token.storage.gorm.tokenValuePropertyName = 'tokenValue'
grails.plugin.springsecurity.rest.token.storage.gorm.usernamePropertyName = 'username'
grails.plugin.springsecurity.rest.token.storage.gorm.tokenDomainClassName = 'com.gestion.tache.security.AuthentificationToken'
grails.plugin.springsecurity.rest.token.storage.useGrailsCache = false
grails.plugin.springsecurity.rest.token.storage.jwt.secret = '6e98876b-c59e-4d5f-8361-8f1a041d298d'

//grails.plugin.springsecurity.rest.token.storage.jwt.useSignedJwt=true
//grails.plugin.springsecurity.rest.token.storage.jwt.useEncryptedJwt = true
//grails.plugin.springsecurity.rest.token.generation.jwt.algorithm = 'HS256'
//grails.plugin.springsecurity.rest.token.validation.useBearerToken = true

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
		[pattern: '/',               access: ['permitAll']],
		[pattern: '/index',          access: ['permitAll']],
		[pattern: '/error',          access: ['permitAll']],
		[pattern: '/index.gsp',      access: ['permitAll']],
		[pattern: '/shutdown',       access: ['permitAll']],
		[pattern: '/assets/**',      access: ['permitAll']],
		[pattern: '/**/js/**',       access: ['permitAll']],
		[pattern: '/**/css/**',      access: ['permitAll']],
		[pattern: '/**/images/**',   access: ['permitAll']],
		[pattern: '/**/favicon.ico', access: ['permitAll']],
		[pattern: '/**',             access: ['isFullyAuthenticated()']],
		[pattern: '/api/**',         access: ['ROLE_ADMIN']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
		[pattern: '/api/**',filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter'],
		[pattern: '/**', filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter']
]


//grails.plugin.springsecurity.filterChain.chainMap = [
//  [pattern: '/assets/**',      filters: 'none'],
//  [pattern: '/**/js/**',       filters: 'none'],
//  [pattern: '/**/css/**',      filters: 'none'],
//  [pattern: '/**/images/**',   filters: 'none'],
//  [pattern: '/**/favicon.ico', filters: 'none'],
//  [pattern: '/**',             filters: 'JOINED_FILTERS'],
//
//  //Stateless chain for REST API
//  [
//          pattern: '/api/**', filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'
//  ],
//
//  //Traditional chain for form based login
//  [
//          pattern: '/**', filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
//  ]
//]


