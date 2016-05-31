angular.module('commentCtrl',['loginFactory']).controller('commentCtrl',['$scope', '$location', 'commentFactory', 'loginFactory',
 function($scope, $location, commentFactory, loginFactory){
  	$scope.id=$location.search().id;
    $scope.user=null;
    loginFactory.checkAuthenticated().then(function(response){
        if (response){
            $scope.authenticated=true;
            loginFactory.getUser().then(function(userReturn){
                $scope.user=userReturn;
            });
        }
        else{
            $scope.authenticated=false;
        }
    });
  	$scope.text="";
  	console.log($scope.id);
  	commentFactory.initStylesArray($scope.id).then(function(response){
  		$scope.styleArray=response;
  	});
  	commentFactory.getComments($scope.id).then(function(response){
  		$scope.comments=response;
  		console.log($scope.comments);
  	});
  	$scope.paginationSelected=function(num){
  		commentFactory.changePage(num);
  		commentFactory.getStyleArray();
  		$scope.currentPage=commentFactory.getCurrentPage();
  	}
  	$scope.currentPage=commentFactory.getCurrentPage();
    $scope.post = function(text){
    	var comment={};
      loginFactory.getUser().then(function(userReturn){
                comment.author=userReturn
      });
    	comment.dateString=getDate();
    	comment.elementId=$scope.id;
    	comment.text=text;
        console.log("author:"+comment.author);
        console.log("elementId:"+comment.elementId);
        console.log("dateString:"+comment.dateString);
        console.log("text:"+comment.text);
        commentFactory.addComments(comment,comment.elementId);
    }
    function getDate(){
    	var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1; //January is 0!
		var yyyy = today.getFullYear();

		if(dd<10) {
    		dd='0'+dd
		} 
		if(mm<10) {
    		mm='0'+mm
		} 
		return today = mm+' '+dd+' '+yyyy;
	}
}]);
