angular.module('commentCtrl',['loginFactory']).controller('commentCtrl',['$scope', '$location', 'commentFactory', 'loginFactory',
 function($scope, $location, commentFactory, loginFactory){
  	$scope.id=$location.search().id;
    $scope.authenticated=false;
    console.log($scope.id);
    loginFactory.checkAuthenticated().then(function(response){
        console.log("response is:"+response);
        if (response){
            $scope.authenticated=true;
            loginFactory.getUser().then(function(userReturn){
                $scope.user=userReturn;
            });
        }
        else{
            $scope.authenticated=false;
        }
        console.log($scope.authenticated);
    });
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
    $scope.post = function(){
      var comment={};
      loginFactory.getUser().then(function(userReturn){
                comment.author=userReturn;
                comment.dateString=getDate();
            	comment.elementId=$scope.id;
            	comment.text=$scope.text;
                console.log("author:"+comment.author);
                console.log("elementId:"+comment.elementId);
                console.log("dateString:"+comment.dateString);
                console.log("text:"+comment.text);
                commentFactory.addComments(comment,comment.elementId).then(function(){
                    commentFactory.getComments($scope.id).then(function(response){
      		                $scope.comments=response;
      		                console.log($scope.comments);
                  	});
                });
                $scope.text='';
      });

  }
    $scope.cancel = function(){
        this.text='';
        $scope.text='';
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
