function DriverService($resource) {
    return $resource('rest/driver/:login/:reqType?orderID=:orderID',
        {reqType: '@reqType',  login: '@login', orderID: '@orderID' });
}

function DriverCtrl($scope, $http, DriverService, InfoShareService){
    $scope.login = InfoShareService.getUser();
    $scope.selected = false;
    DriverService.query({login:$scope.login, reqType: "oldOrders"}, function (value){$scope.oldOrders = value;});
    $scope.newOrders = DriverService.query({login:$scope.login, reqType: "newOrders"});
    $scope.pay= false;
    $scope.select = function(order){
        $scope.selected = true;
        $scope.order = order;
        alert("Selected! Need to accept");
    }
    $scope.update = function(){
        //alert("update");
        DriverService.query({login:$scope.login, reqType: "oldOrders"}, function (value){$scope.oldOrders = value;});
        $scope.newOrders = DriverService.query({login:$scope.login, reqType: "newOrders"});

    }
    $scope.accept = function(){
        if ($scope.selected == true) {
            DriverService.query({login: $scope.login, reqType: "accept", orderID: $scope.order}, function (value) {
                $scope.OK = value;

            });
            if($scope.OK.toString() === "true") {
                $scope.pay = true;
                $scope.update();
                alert("Accepted!");
            }
        }

    }
    $scope.decline = function(){
        if ($scope.selected == true) {
            DriverService.query({login: $scope.login, reqType: "decline", orderID: $scope.order}, function (value) {
                $scope.OK = value;
            });
            if ($scope.OK.toString() === "true") {
                $scope.update();
                alert("Declined!");
            }
        }
    }

}


app
    .factory('DriverService', DriverService)
    .controller('DriverCtrl', DriverCtrl);