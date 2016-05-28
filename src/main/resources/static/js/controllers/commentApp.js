var app=angular.module('commentApp',[]);
app.controller('CommentCtrl',['$scope', 'commentFactory', function($scope, commentFactory){
    $scope.comment = {"author":"", "elementId":"", "dateString":"", "text":""};
    $scope.post = function(author, elementId, dateString, text){
        console.log("author:"+author);
        console.log("elementId:"+elementId);
        console.log("dateString:"+dateString);
        console.log("text:"+text);
        var comment={"author": author, "elementId":elementId, "dateString":dateString, "text":text};
        commentFactory.addComments(comment,elementId);
    }


}]);
