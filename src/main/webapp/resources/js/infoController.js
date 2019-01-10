function UserService($resource) {
    return $resource('rest/:userType/:login', { login: '@login', userType: '@userType' });
}

app
    .factory('UserService', UserService)
    .controller('InfoController', InfoController);