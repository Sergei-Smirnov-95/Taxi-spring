function DriverService($resource) {
    return $resource('rest/driver/:login/:reqType', {reqType: '@reqType',  login: '@login' });
}

function DriverCtrl($scope, $http, DriverrService, InfoShareService){
    $scope.login = InfoShareService.getUser();
    this.selected = false;
    alert("start gtting info");
    DriverService.query({login:$scope.login, reqType: "orders"}, function (value){$scope.orders = value;});
    $scope.drivers = DriverService.query({login:$scope.login, reqType: "drivers"});

    this.select = function(order){
        this.selected = true;
        this.order = order;
        alert("need accept");
    }
    this.update = function(){
        alert("update");
        DriverService.query({login:$scope.login, reqType: "orders"}, function (value){$scope.orders = value;});
        $scope.drivers = DriverService.query({login:$scope.login, reqType: "drivers"});
    }
}


app
    .service('InfoShareService', InfoShareService)
    .factory('DriverService', DriverService)
    .controller('DriverCtrl', DriverCtrl);