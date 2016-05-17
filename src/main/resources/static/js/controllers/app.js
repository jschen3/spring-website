/*
 * All the methods to for the index.html page. 2 major parts 1 for the slide carousel 
 * and another for the articles found on the index page. 
 */
var app=angular.module('app',['ngAnimate','ui.bootstrap', 'Constants']);
/*
 *  Slide carousel controller.
 */
angular.module('app').controller('CarouselCtrl', ['$scope', 'SLIDE_URL', 'slideFactory', 
 function($scope, slideUrl, slideFactory){
    /*
     * Calls the slideFactory get slides function and sets the variable $scope.slides to the response.
     * $scope.slides is used to generate the html page.
     */
    slideFactory.getSlides().then(function(response){
        $scope.slides=response;
    });

}]);
/*
 * Article list controller 
 */
angular.module('app').controller('articleCtrl', ['$scope', '$http', 'ARTICLE_URL', 'articleListFactory', 
    function($scope, $http, articleUrl, articleListFactory){
    /*
     * Calls the articleList to init the pagination controls style and format.
     */
    articleListFactory.initStyleArray().then(function(response){
        $scope.styleArray=response;
        console.log($scope.styleArray);
    });
    /*
     * Calls the articleFactory to retrieve the list of articles.
     */
    articleListFactory.getArticles().then(function(response){
        $scope.articles=response;
        console.log($scope.articles);
    });
    /*
     * Controls the articles displayed on the screen by the pagination controls. 
     */
    $scope.paginationSelected = function(num){
        articleListFactory.changePage(num);
        articleListFactory.getStyleArray();
        $scope.currentPage=articleListFactory.getCurrentPage();
    };
    /*
     * Just a UI element will update to make work later.
     */
    $scope.currentPage=articleListFactory.getCurrentPage();
    $scope.months=[{
        url: "",
        month:"January Articles"
    },
    {
        url: "",
        month: "February Articles"
    }];
}]);
angular.module('app').controller("home", ['$http', '$location', '$scope', function($http, $location, $scope) {
    $http.get("http://localhost:8080/user").then(function(data) {
        console.log(data);
        if (IsJsonString(data)){
        	$scope.user = data.userAuthentication.details.name;
        	console.log($scope.user);
        	$scope.authenticated = true;
        }        
    });
    $scope.logout = function() {
        $http.post('logout', {}).success(function() {
            $scope.authenticated = false;
            $location.path("/");
        }).error(function(data) {
            console.log("Logout failed")
            $scope.authenticated = false;
        });
    };
    function IsJsonString(str) {
        try {
            eval(str);
        } catch (e) {
            return false;
        }
        return true;
    }
}]);