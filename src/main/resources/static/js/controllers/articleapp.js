/*
 * All the methods to control the article page. 
 * Works for any article, the article displayed depends on the id.
 */
var app = angular.module('articleapp', ['Constants']);
app.controller("ArticleCtrl", ['$scope', '$http', '$location', 'articleFactory', function ($scope, $http, $location, articleFactory) {
    $scope.id=$location.search().id;
    console.log($location.search().id);
    /*
     * Retrieves the article information from the articleFactory. The id comes from the url
     * which is stored as the variable $scope.id, inthe above lines. 
     */
    articleFactory.getArticle($scope.id).then(function(response){
    	$scope.article=response;
    });
    /*
     * Retrieves the image append from the $scope.id and articleFactory
     */
    articleFactory.getImageAppend($scope.id).then(function(response){
    	$scope.imageAppend=response;
    });
}]);
