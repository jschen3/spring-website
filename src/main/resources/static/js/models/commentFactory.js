angular.module('commentApp').factory('commentFactory', ['$q','$http',
    function($q,$http){
        var commentUrl="http://localhost:8080/comments"
        var _comments;
        var commentFactory = {};
        commentFactory.getComments = function(id){
            var defer=$q.defer();
            $http.get(commentUrl+"/"+id).then(function(response){
                _comments=response.data;
                defer.resolve(_comments);
            });
            return defer.promise;
        };
        commentFactory.addComments = function(comment, id){
                var defer=$q.defer();
                $http.post(commentUrl+"/"+id, comment).then(function(){
                    defer.resolve();
                });
                return defer.promise;
        };
        return commentFactory;
}]);
