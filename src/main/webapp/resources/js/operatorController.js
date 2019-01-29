function OperatorService($resource) {
    return $resource('rest/operator/:login/:reqType', {reqType: '@reqType',  login: '@login' });
}

function OperatorCtrl($scope, $http, OperatorService, InfoShareService){
    $scope.login = InfoShareService.getUser();
    alert("start gtting info");
    OperatorService.query({login:$scope.login, reqType: "orders"}, function (value){$scope.orders = value;});
    $scope.drivers = OperatorService.query({login:$scope.login, reqType: "drivers"});

    this.select = function(order){
        alert("ok");
    }
    this.update = function(){
        alert("update");
        OperatorService.query({login:$scope.login, reqType: "orders"}, function (value){$scope.orders = value;});
        $scope.drivers = OperatorService.query({login:$scope.login, reqType: "drivers"});
    }
}


app
    .service('InfoShareService', InfoShareService)
    .factory('OperatorService', OperatorService)
    .controller('OperatorCtrl', OperatorCtrl);