function DriverService($resource) {
    return $resource('rest/driver/:login/:reqType?orderID=:orderID',
        {reqType: '@reqType', login: '@login', orderID: '@orderID'});
}

function DriverCtrl($scope, $http, DriverService, InfoShareService) {
    $scope.needPay = false;
    $scope.selected = false;
    $scope.login = InfoShareService.getUser();
    DriverService.query({login: $scope.login, reqType: "oldOrders"}, function (value) {
        $scope.oldOrders = value;
    });
    $scope.newOrders = DriverService.query({login: $scope.login, reqType: "newOrders"});
    $scope.select = function (order) {
        $scope.selected = true;
        $scope.order = order;
        alert("Selected! Need to accept");
    };
    $scope.update = function () {
        alert("update");
        DriverService.query({login: $scope.login, reqType: "oldOrders"}, function (value) {
            $scope.oldOrders = value;
        });
        $scope.newOrders = DriverService.query({login: $scope.login, reqType: "newOrders"});

    };
    $scope.accept = function () {
        if ($scope.selected == true) {
            $scope.result = DriverService.query({login: $scope.login, reqType: "accept", orderID: $scope.order});
            if ($scope.result) {
                //$scope.pay = true;
                $scope.needPay  = !$scope.needPay ;
                $scope.selected = false;
                $scope.update();
                alert("Accepted!");
            }
        }

    };
    $scope.decline = function () {
        if ($scope.selected == true) {
            $scope.result = DriverService.query({login: $scope.login, reqType: "decline", orderID: $scope.order});
            if ($scope.result) {
                $scope.update();
                alert("Declined!");
            }

        }
    };
    $scope.pay = function () {
        if (!$scope.length) {
            alert("input length");
        } else if (!$scope.time) {
            alert("input time");
        } else {
            /*$http.get('rest/driver/' + $scope.login + '/pay?length=' + $scope.length + '&time=' + $scope.time)
                .then(function (response) {*/

            $scope.needPay = !$scope.needPay ;
            $scope.length="";
            $scope.time="";
            alert("Paid!");
            // });
        }
    };

}


app
    .service('InfoShareService', InfoShareService)
    .factory('DriverService', DriverService)
    .controller('DriverCtrl', DriverCtrl);