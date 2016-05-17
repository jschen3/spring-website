var app=angular.module('teaserApp', ['Constants']);
app.controller("TeaserCtrl",['$scope','$http','$location','teaserFactory',
 	function($scope,$http,$location, teaserFactory){
 		$scope.id=$location.search().id;
 		console.log($scope.id);
		$scope.showSolution=false;
		$scope.buttonText="Show Solution"
		$scope.toggleSolution=function(){
			Prism.highlightAll();
			if ($scope.showSolution===false){
				$scope.buttonText="Hide Solution"
				$scope.showSolution=true;
			}
			else{
				$scope.buttonText="Show Solution";
				$scope.showSolution=false;
			}
		}
		teaserFactory.getTeaser($scope.id).then(function(response){
			$scope.teaser=response;
			console.log($scope.teaser);
			Prism.highlightAll();
		});
		
		$scope.checkType = function(type, templateType){
			if (type===templateType)
				return true;
			else{
				return false;
			}
		}
}]);

