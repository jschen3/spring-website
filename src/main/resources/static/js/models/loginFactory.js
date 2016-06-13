angular.module('loginFactory',[])
    .factory('loginFactory',['$q','$http',
    function($q, $http){
        var loginUrl='http://localhost:8080/user';
        var _user;
        var loginFactory = {};
        var _response;
        var _name;
        var _authenticated;
        loginFactory.getUser = function(){
            var defer=$q.defer();
            $http.get(loginUrl).then(function(response){
                _response=response.data;
                if (isJsonString(_response)){
                    _user=_response.userAuthentication.details.name;
                    console.log(_user);
                }
                else{
                    _user=null;
                }
                defer.resolve(_user);
            });
            return defer.promise;
        };
        loginFactory.checkAuthenticated = function(){
            var defer=$q.defer();
            $http.get(loginUrl).then(function(response){
                _response=response.data;
                if (isJsonString(_response)){
                    _authenticated=true;
                }
                else{
                    _authenticated=false;
                }
                defer.resolve(_authenticated);
            },function(response){
                _authenticated=false
                defer.resolve(_authenticated);
            });
            return defer.promise;
        };
        loginFactory.getUser = function(){
            var defer=$q.defer();
            $http.get(loginUrl).then(function(response){
                _response=response.data;
                if (isJsonString(_response)){
                    _user=_response.userAuthentication.details.name;
                    console.log(_user);
                }
                else{
                    _user=null;
                }
                defer.resolve(_user);
            });
            return defer.promise;
        };
        loginFactory.logout = function(){
            $http.post('logout', {}).success(function() {
                _authenticated = false;
                $location.path("/");
            }).error(function(data) {
                console.log("Logout failed")
                _authenticated = false;
            });
        };
        function isJsonString(str) {
            try {
                eval(str);
            } catch (e) {
                return false;
            }
            return true;
        }
        return loginFactory;
}]);
