(function () {
    'use strict';
 
    
    app.factory('UserService', UserService);
 
    
    function UserService($http,$localStorage) {
        var service = {};
 
        service.GetAll = GetAll;
        service.GetById = GetById;
        service.GetByUsername = GetByUsername;
        service.Create = Create;
        service.Update = Update;
        service.Delete = Delete;
 
        return service;
 
        function GetAll() {
            return $http.get('register/').then(handleSuccess, handleError('Error getting all users'));
        }
 
        function GetById(id) {
            return $http.get('register/' + id).then(handleSuccess, handleError('Error getting user by id'));
        }
 
        function GetByUsername(username) {
            return $http.get('register/' + username).then(handleSuccess, handleError('Error getting user by username'));
        }
 
        function Create(user) {
            return $http.post('register/', user).then(handleSuccess, handleError('Error creating user'));
        }
 
        function Update(user) {
            return $http.put('register/' + user.id, user).then(handleSuccess, handleError('Error updating user'));
        }
 
        function Delete(id) {
            return $http.delete('register/' + id).then(handleSuccess, handleError('Error deleting user'));
        }
 
        // private functions
 
        function handleSuccess(res) {
            return res.data;
        }
 
        function handleError(error) {
            return function () {
                return { success: false, message: error };
            };
        }
    }
 
})();