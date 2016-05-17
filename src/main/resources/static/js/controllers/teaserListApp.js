var app=angular.module('teaserListApp',['Constants']);
app.controller("teaserListCtrl",['$scope','teaserListFactory', 
	function($scope, teaserListFactory){
	teaserListFactory.getTeasers().then(function(response){
		$scope.teasers=response;
		$scope.teasers.sort(function(teaser1,teaser2){
			return teaser2.dateDay-teaser1.dateDay;
		})
	});
}]);